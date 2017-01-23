package com.mopub.nativeads;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;

import com.mopub.common.DataKeys;
import com.mopub.common.event.EventDetails;
import com.mopub.common.logging.MoPubLog;
import com.mopub.nativeads.events.NativeAdEventsObserver;
import com.mopub.nativeads.factories.CustomEventNativeFactory;
import com.mopub.network.AdResponse;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.WeakHashMap;


final class CustomEventNativeAdapter {
    private CustomEventNativeAdapter() {}

    private static final int MESSAGE_TIMEOUT = 738;
    private static final int TIMEOUT = 10000;
    private static Handler handler = new Handler(Looper.getMainLooper(),new HandlerCallback());
    private static Map<ListenerWrapper,CustomEventNative> runningEvents = new WeakHashMap<>();



    public static void loadNativeAd(@NonNull final Context context,
            @NonNull final Map<String, Object> localExtras,
            @NonNull final AdResponse adResponse,
            @NonNull final CustomEventNative.CustomEventNativeListener customEventNativeListener) {

        final CustomEventNative customEventNative;
        String customEventNativeClassName = adResponse.getCustomEventClassName();

        MoPubLog.d("Attempting to invoke custom event: " + customEventNativeClassName);
        try {
            customEventNative = CustomEventNativeFactory.create(customEventNativeClassName);
        } catch (Exception e) {
            MoPubLog.w("Failed to load Custom Event Native class: " + customEventNativeClassName);
            customEventNativeListener.onNativeAdFailed(NativeErrorCode.NATIVE_ADAPTER_NOT_FOUND);
            return;
        }
        if (adResponse.hasJson()) {
            localExtras.put(DataKeys.JSON_BODY_KEY, adResponse.getJsonBody());
        }

        final EventDetails eventDetails = adResponse.getEventDetails();
        if (eventDetails != null) {
            localExtras.put(DataKeys.EVENT_DETAILS, eventDetails);
        }

        localExtras.put(DataKeys.CLICK_TRACKING_URL_KEY, adResponse.getClickTrackingUrl());

        // Custom event classes can be developed by any third party and may not be tested.
        // We catch all exceptions here to prevent crashes from untested code.

        ListenerWrapper wrapper = new ListenerWrapper(customEventNativeListener);
        Message message = Message.obtain();
        message.what=MESSAGE_TIMEOUT;
        message.obj = new WeakReference<>(wrapper);
        runningEvents.put(wrapper,customEventNative);
        handler.sendMessageDelayed(message,TIMEOUT);

        try {
            customEventNative.loadNativeAd(
                    context,
                    wrapper,
                    localExtras,
                    adResponse.getServerExtras()
            );
        } catch (Exception e) {
            MoPubLog.w("Loading custom event native threw an error.", e);
            wrapper.onNativeAdFailed(NativeErrorCode.NATIVE_ADAPTER_NOT_FOUND);
        }
    }

    private static class ListenerWrapper implements CustomEventNative.CustomEventNativeListener{
        private CustomEventNative.CustomEventNativeListener listener;

        public ListenerWrapper(CustomEventNative.CustomEventNativeListener listener) {
            this.listener = listener;
        }

        @Override
        public void onNativeAdLoaded(BaseNativeAd nativeAd) {
	        CustomEventNative eventNative = runningEvents.get(this);
            if (eventNative != null){
                NativeAdEventsObserver.instance().onAdLoaded(eventNative.getNativeAdType(),eventNative.getTierName());
                listener.onNativeAdLoaded(nativeAd);
                runningEvents.remove(this);
            }
        }

        @Override
        public void onNativeAdFailed(NativeErrorCode errorCode) {
	        CustomEventNative eventNative = runningEvents.get(this);
            if (eventNative != null){
                listener.onNativeAdFailed(errorCode);
                NativeAdEventsObserver.instance().onAdNetworkFailed(eventNative.getNativeAdType(),eventNative.getTierName(),errorCode);
                runningEvents.remove(this);
            }
        }

        private void cancelEvent(){
	        CustomEventNative eventNative = runningEvents.get(this);
            if (eventNative != null){
                listener.onNativeAdFailed(NativeErrorCode.NETWORK_TIMEOUT);
	            NativeAdEventsObserver.instance().onAdCanceledByTimeout(eventNative.getNativeAdType(),eventNative.getTierName());
                runningEvents.remove(this);
            }
        }
    }

    private static class HandlerCallback implements Handler.Callback{
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == MESSAGE_TIMEOUT){
                if (msg.obj instanceof WeakReference) {
                    WeakReference<ListenerWrapper> reference = (WeakReference<ListenerWrapper>) msg.obj;
	                ListenerWrapper wrapper = reference.get();
	                if (wrapper != null) {
		                wrapper.cancelEvent();
	                }
                }
                return true;
            }
            return false;
        }
    }
}
