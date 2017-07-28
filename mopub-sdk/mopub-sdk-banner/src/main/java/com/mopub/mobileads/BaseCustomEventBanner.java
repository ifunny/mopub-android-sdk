package com.mopub.mobileads;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * CustomEventBanner is a base class for custom events that support banners. By implementing
 * subclasses of CustomEventBanner, you can enable the MoPub SDK to natively support a wider
 * variety of third-party ad networks, or execute any of your application code on demand.
 * 
 * At runtime, the MoPub SDK will find and instantiate a CustomEventBanner subclass as needed
 * and invoke its loadAd() method.
 */
public abstract class BaseCustomEventBanner extends CustomEventBanner {
	
	@NonNull
	protected CustomEventBannerListener mCustomEventBannerListener;
	protected final List<String> mRequiredExtras;
	
	public BaseCustomEventBanner() {
		mRequiredExtras = new ArrayList<>();
		fillRequiredExtras(mRequiredExtras);
	}
	
	/*
		 * When the MoPub SDK receives a response indicating it should load a custom event, it will send
		 * this message to your custom event class. Your implementation of this method can either load
		 * a banner ad from a third-party ad network, or execute any application code. It must also
		 * notify the provided CustomEventBanner.Listener Object of certain lifecycle events.
		 *
		 * The localExtras parameter is a Map containing additional custom data that is set within
		 * your application by calling MoPubView.setLocalExtras(Map<String, Object>). Note that the
		 * localExtras Map is a copy of the Map supplied to setLocalExtras().
		 *
		 * The serverExtras parameter is a Map containing additional custom data configurable on the
		 * MoPub website that you want to associate with a given custom event request. This data may be
		 * used to pass dynamic information, such as publisher IDs, without changes in application code.
		 */
	@Override
	protected final void loadBanner(Context context,
	                                CustomEventBannerListener customEventBannerListener, Map<String, Object> localExtras,
	                                Map<String, String> serverExtras) {
		mCustomEventBannerListener = customEventBannerListener;
		if (!checkExtras(serverExtras, mRequiredExtras)) {
			mCustomEventBannerListener.onBannerFailed(MoPubErrorCode.ADAPTER_CONFIGURATION_ERROR);
			return;
		}
		
		onLoadBanner(context, localExtras, serverExtras);
	}
	
	protected abstract void onLoadBanner(Context context, Map<String, Object> localExtras,
	                                     Map<String, String> serverExtras);
	
	
	protected abstract void fillRequiredExtras(List<String> requiredExtras);
	
	protected boolean checkExtras(Map<String, String> extras, List<String> requiredList) {
		for (String requiredExtra : requiredList) {
			if (!extras.containsKey(requiredExtra)) {
				return false;
			}
		}
		return true;
	}
	
}
