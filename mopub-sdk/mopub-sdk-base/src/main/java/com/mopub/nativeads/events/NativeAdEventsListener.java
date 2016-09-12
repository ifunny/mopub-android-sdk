package com.mopub.nativeads.events;

/**
 * Created by Shad on 14.10.15.
 */
public interface NativeAdEventsListener {
	void onNativeAdClicked(NativeAdType adType, String tierName);

	void onNativeAdImpressed(NativeAdType adType, String tierName);

	void onNativeAdRequested(NativeAdType adType, String tierName);

	void onNativeAdLoadSuccess(NativeAdType adType, String tierName);
}
