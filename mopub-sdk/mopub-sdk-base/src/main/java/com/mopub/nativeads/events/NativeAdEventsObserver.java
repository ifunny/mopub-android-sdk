package com.mopub.nativeads.events;

import android.text.TextUtils;

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

	private String handleTierName(NativeAdType adType, String tierName) {
		if (TextUtils.isEmpty(tierName)) {
			return adType.name();
		}
		return tierName;
	}

	public void onAdClicked(NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdClicked(adType, handleTierName(adType, tierName));
		}
	}

	public void onAdImpressed(NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdImpressed(adType, handleTierName(adType, tierName));
		}
	}

	public void onAdRequested(NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdRequested(adType, handleTierName(adType, tierName));
		}
	}

	public void onAdLoaded(NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdLoadSuccess(adType, handleTierName(adType, tierName));
		}
	}

	public void onAdCanceledByTimeout(NativeAdType adType, String tierName) {
		for (NativeAdEventsListener listener : listeners) {
			listener.onNativeAdCanceledByTimeout(adType, handleTierName(adType, tierName));
		}
	}
}
