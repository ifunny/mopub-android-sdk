package com.mopub.mobileads;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.mopub.common.AdReport;
import com.mopub.common.Constants;
import com.mopub.common.Preconditions;
import com.mopub.common.logging.MoPubLog;
import com.mopub.common.util.ReflectionTarget;
import com.mopub.mobileads.CustomEventBanner.CustomEventBannerListener;
import com.mopub.mobileads.events.AdCreativeIdBundle;
import com.mopub.mobileads.factories.CustomEventBannerFactory;

import java.util.Map;
import java.util.TreeMap;

import static com.mopub.common.DataKeys.AD_HEIGHT;
import static com.mopub.common.DataKeys.AD_REPORT_KEY;
import static com.mopub.common.DataKeys.AD_WIDTH;
import static com.mopub.common.DataKeys.BROADCAST_IDENTIFIER_KEY;
import static com.mopub.mobileads.MoPubErrorCode.ADAPTER_NOT_FOUND;
import static com.mopub.mobileads.MoPubErrorCode.NETWORK_TIMEOUT;
import static com.mopub.mobileads.MoPubErrorCode.UNSPECIFIED;

public class CustomEventBannerAdapter implements CustomEventBannerListener {
	public static final int DEFAULT_BANNER_TIMEOUT_DELAY = Constants.SIX_SECONDS_MILLIS;
	private boolean mInvalidated;
	private MoPubView mMoPubView;
	private Context mContext;
	private CustomEventBanner mCustomEventBanner;
	private Map<String, Object> mLocalExtras;
	private Map<String, String> mServerExtras;
	
	private final Handler mHandler;
	private final Runnable mTimeout;
	private boolean mStoredAutorefresh;
	
	public CustomEventBannerAdapter(@NonNull MoPubView moPubView, Context context,
	                                @NonNull String className,
	                                @NonNull Map<String, String> serverExtras,
	                                long broadcastIdentifier,
	                                @Nullable AdReport adReport) {
		Preconditions.checkNotNull(serverExtras);
		mHandler = new Handler();
		mMoPubView = moPubView;
		mContext = context;
		mTimeout = new Runnable() {
			@Override
			public void run() {
				onBannerTimed();
			}
		};
		
		MoPubLog.d("Attempting to invoke custom event: " + className);
		try {
			mCustomEventBanner = CustomEventBannerFactory.create(className);
		} catch (Exception exception) {
			MoPubLog.d("Couldn't locate or instantiate custom event: " + className + ".");
			mMoPubView.loadFailUrl(ADAPTER_NOT_FOUND);
			return;
		}
		
		// Attempt to load the JSON extras into mServerExtras.
		mServerExtras = new TreeMap<String, String>(serverExtras);
		
		mLocalExtras = mMoPubView.getLocalExtras();
		if (mMoPubView.getLocation() != null) {
			mLocalExtras.put("location", mMoPubView.getLocation());
		}
		mLocalExtras.put(BROADCAST_IDENTIFIER_KEY, broadcastIdentifier);
		mLocalExtras.put(AD_REPORT_KEY, adReport);
		mLocalExtras.put(AD_WIDTH, mMoPubView.getAdWidth());
		mLocalExtras.put(AD_HEIGHT, mMoPubView.getAdHeight());
	}
	
	@ReflectionTarget
	void loadAd() {
		if (isInvalidated() || mCustomEventBanner == null) {
			return;
		}
		
		mHandler.postDelayed(mTimeout, getTimeoutDelayMilliseconds());
		
		// Custom event classes can be developed by any third party and may not be tested.
		// We catch all exceptions here to prevent crashes from untested code.
		try {
			mCustomEventBanner.loadBanner(mContext, this, mLocalExtras, mServerExtras);
		} catch (Exception e) {
			MoPubLog.d("Loading a custom event banner threw an exception.", e);
			onBannerFailed(MoPubErrorCode.INTERNAL_ERROR);
		}
	}
	
	@ReflectionTarget
	void invalidate() {
		if (mCustomEventBanner != null) {
			// Custom event classes can be developed by any third party and may not be tested.
			// We catch all exceptions here to prevent crashes from untested code.
			try {
				mCustomEventBanner.onInvalidate();
			} catch (Exception e) {
				MoPubLog.d("Invalidating a custom event banner threw an exception", e);
			}
		}
		mContext = null;
		mCustomEventBanner = null;
		mLocalExtras = null;
		mServerExtras = null;
		mInvalidated = true;
	}
	
	@ReflectionTarget
	void stop() {
		if (mCustomEventBanner != null) {
			try {
				mCustomEventBanner.onStop();
			} catch (Exception e) {
				MoPubLog.d("Stopping a custom event banner threw an exception", e);
			}
		}
	}
	
	@ReflectionTarget
	void pause() {
		if (mCustomEventBanner != null) {
			try {
				mCustomEventBanner.onPause();
			} catch (Exception e) {
				MoPubLog.d("Stopping a custom event banner threw an exception", e);
			}
		}
	}
	
	@ReflectionTarget
	void resume() {
		if (mCustomEventBanner != null) {
			try {
				mCustomEventBanner.onResume();
			} catch (Exception e) {
				MoPubLog.d("Stopping a custom event banner threw an exception", e);
			}
		}
	}
	
	@ReflectionTarget
	void destroy() {
		cancelTimeout();
	}
	
	boolean isInvalidated() {
		return mInvalidated;
	}
	
	private void cancelTimeout() {
		mHandler.removeCallbacks(mTimeout);
	}
	
	private int getTimeoutDelayMilliseconds() {
//        if (mMoPubView == null
//                || mMoPubView.getAdTimeoutDelay() == null
//                || mMoPubView.getAdTimeoutDelay() < 0) {
//            return DEFAULT_BANNER_TIMEOUT_DELAY;
//        }
//
//        return mMoPubView.getAdTimeoutDelay() * 1000;
		return DEFAULT_BANNER_TIMEOUT_DELAY;
	}
	
	
	/*
	 * CustomEventBanner.Listener implementation
	 */
	@Override
	public void onBannerLoaded(final View bannerView, @Nullable final AdCreativeIdBundle adCreativeIdBundle) {
		if (isInvalidated()) {
			return;
		}
		
		cancelTimeout();
		
		if (mMoPubView != null) {
			mMoPubView.setAdContentView(bannerView);
			mMoPubView.setAdCreativeId(adCreativeIdBundle);
			mMoPubView.nativeAdLoaded();
			if (!(bannerView instanceof HtmlBannerWebView)) {
				mMoPubView.trackNativeImpression();
			}
		}
	}
	
	private void onBannerTimed() {
		MoPubLog.d("Third-party network timed out.");
		onBannerFailed(NETWORK_TIMEOUT);
		invalidate();
	}
	
	@Override
	public void onBannerFailed(MoPubErrorCode errorCode) {
		if (isInvalidated()) {
			return;
		}
		
		if (mMoPubView != null) {
			if (errorCode == null) {
				errorCode = UNSPECIFIED;
			}
			cancelTimeout();
			mMoPubView.loadFailUrl(errorCode);
		}
	}
	
	@Override
	public void onBannerExpanded() {
		if (isInvalidated()) {
			return;
		}
		
		mStoredAutorefresh = mMoPubView.getAutorefreshEnabled();
		mMoPubView.setAutorefreshEnabled(false);
		mMoPubView.adPresentedOverlay();
	}
	
	@Override
	public void onBannerCollapsed() {
		if (isInvalidated()) {
			return;
		}
		
		mMoPubView.setAutorefreshEnabled(mStoredAutorefresh);
		mMoPubView.adClosed();
	}
	
	@Override
	public void onBannerClicked() {
		if (isInvalidated()) {
			return;
		}
		
		if (mMoPubView != null) {
			mMoPubView.registerClick();
		}
	}
	
	@Override
	public void onLeaveApplication() {
		onBannerClicked();
	}
}
