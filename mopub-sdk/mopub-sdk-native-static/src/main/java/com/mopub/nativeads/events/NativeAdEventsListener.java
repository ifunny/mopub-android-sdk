package com.mopub.nativeads.events;

import com.mopub.nativeads.CustomEventNative;
import com.mopub.nativeads.NativeErrorCode;

/**
 * Created by Shad on 14.10.15.
 */
public interface NativeAdEventsListener {
	void onNativeAdClicked(CustomEventNative eventNative, NativeAdType adType, String tierName);
	
	void onNativeAdImpressed(CustomEventNative eventNative, NativeAdType adType, String tierName);
	
	void onNativeAdRequested(CustomEventNative eventNative, NativeAdType adType, String tierName);
	
	void onNativeAdLoadSuccess(CustomEventNative eventNative, NativeAdType adType, String tierName);
	
	void onNativeAdCanceledByTimeout(CustomEventNative eventNative, NativeAdType adType, String tierName);
	
	void onNativeAdNetworkFailed(CustomEventNative eventNative, NativeAdType adType, String tierName, NativeErrorCode errorCode);
	
	void onNativeAdFailed();
}
