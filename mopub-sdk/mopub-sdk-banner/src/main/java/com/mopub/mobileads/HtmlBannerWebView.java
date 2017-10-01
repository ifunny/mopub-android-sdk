package com.mopub.mobileads;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mopub.common.AdReport;
import com.mopub.mobileads.events.MopubAdCreativeId;

import static com.mopub.mobileads.CustomEventBanner.CustomEventBannerListener;

public class HtmlBannerWebView extends BaseHtmlWebView {
	public static final String EXTRA_AD_CLICK_DATA = "com.mopub.intent.extra.AD_CLICK_DATA";
	
	public HtmlBannerWebView(Context context, AdReport adReport) {
		super(context, adReport);
	}
	
	public void init(CustomEventBannerListener customEventBannerListener, boolean isScrollable, String redirectUrl, String clickthroughUrl,
	                 String dspCreativeId) {
		super.init(isScrollable);
		
		setWebViewClient(
				new HtmlWebViewClient(new HtmlBannerWebViewListener(customEventBannerListener, dspCreativeId), this, clickthroughUrl,
						redirectUrl,
						dspCreativeId));
	}
	
	static class HtmlBannerWebViewListener implements HtmlWebViewListener {
		private final CustomEventBannerListener mCustomEventBannerListener;
		@Nullable
		private final String mDspCreativeId;
		
		public HtmlBannerWebViewListener(CustomEventBannerListener customEventBannerListener, @Nullable String dspCreativeId) {
			mCustomEventBannerListener = customEventBannerListener;
			mDspCreativeId = dspCreativeId;
		}
		
		@Override
		public void onLoaded(BaseHtmlWebView htmlWebView) {
			MopubAdCreativeId mopubAdCreativeId = null;
			if (!TextUtils.isEmpty(mDspCreativeId)) {
				mopubAdCreativeId = new MopubAdCreativeId(mDspCreativeId);
			}
			mCustomEventBannerListener.onBannerLoaded(htmlWebView, mopubAdCreativeId);
		}
		
		@Override
		public void onFailed(MoPubErrorCode errorCode) {
			mCustomEventBannerListener.onBannerFailed(errorCode);
		}
		
		@Override
		public void onClicked() {
			mCustomEventBannerListener.onBannerClicked();
		}
		
		@Override
		public void onCollapsed() {
			mCustomEventBannerListener.onBannerCollapsed();
		}
		
	}
}
