package com.mopub.nativeads.events;

import com.mopub.nativeads.NativeErrorCode;

/**
 * Created by Shad on 14.10.15.
 */
public interface NativeAdEventsListener {
	void onNativeAdClicked(NativeAdType adType, String tierName);

	void onNativeAdImpressed(NativeAdType adType, String tierName);

	void onNativeAdRequested(NativeAdType adType, String tierName);

	void onNativeAdLoadSuccess(NativeAdType adType, String tierName);

	void onNativeAdCanceledByTimeout(NativeAdType adType, String tierName);

	void onNativeAdNetworkFailed(NativeAdType adType, String tierName, NativeErrorCode errorCode);

	void onNativeAdFailed();
}
