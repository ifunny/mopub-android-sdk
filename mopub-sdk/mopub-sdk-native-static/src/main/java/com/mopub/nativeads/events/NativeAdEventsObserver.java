package com.mopub.nativeads.events;

import com.mopub.nativeads.CustomEventNative;
import com.mopub.nativeads.NativeErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shad on 14.10.15.
 */
public class NativeAdEventsObserver {
	private static NativeAdEventsObserver instance;
	
	public static NativeAdEventsObserver instance() {
		if (instance == null) {
			synchronized (NativeAdEventsObserver.class) {
				if (instance == null) {
					instance = new NativeAdEventsObserver();
				}
			}
		}
		return instance;
	}
	
	private final List<NativeAdEventsListener> listeners;
	
	public NativeAdEventsObserver() {
		listeners = new ArrayList<>(4);
	}
	
	public void addListener(NativeAdEventsListener listener) {
		listeners.add(listener);
	}
	
	public void removeListener(NativeAdEventsListener listener) {
		listeners.remove(listener);
	}
	
	public void onAdClicked(CustomEventNative eventNative, NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdClicked(eventNative, adType, tierName);
		}
	}
	
	public void onAdImpressed(CustomEventNative eventNative, NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdImpressed(eventNative, adType, tierName);
		}
	}
	
	public void onAdRequested(CustomEventNative eventNative, NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdRequested(eventNative, adType, tierName);
		}
	}
	
	public void onAdLoaded(CustomEventNative eventNative, NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdLoadSuccess(eventNative, adType, tierName);
		}
	}
	
	public void onAdCanceledByTimeout(CustomEventNative eventNative, NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdCanceledByTimeout(eventNative, adType, tierName);
		}
	}
	
	public void onAdNetworkFailed(CustomEventNative eventNative, NativeAdType adType, String tierName, NativeErrorCode errorCode) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdNetworkFailed(eventNative, adType, tierName, errorCode);
		}
	}
	
	public void onAdFailed() {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdFailed();
		}
	}
}
