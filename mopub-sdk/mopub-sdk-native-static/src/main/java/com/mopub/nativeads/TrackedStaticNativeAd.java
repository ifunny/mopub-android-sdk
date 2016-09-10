package com.mopub.nativeads;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

/**
 * Created by Shad on 19.07.16.
 */
public abstract class TrackedStaticNativeAd extends StaticNativeAd {

	private final ImpressionTracker impressionTracker;
	private final NativeClickHandler clickHandler;

	public TrackedStaticNativeAd(Context context) {
		impressionTracker = new ImpressionTracker(context);
		clickHandler = new NativeClickHandler(context);
	}

	@Override
	public void prepare(final View view) {
		impressionTracker.addView(view, this);
		clickHandler.setOnClickListener(view, this);
	}

	@Override
	public void recordImpression(final View view) {
		notifyAdImpressed();
	}

	@Override
	public void handleClick(@NonNull View view) {
		notifyAdClicked();
	}

	@Override
	public void clear(View view) {
		impressionTracker.removeView(view);
		clickHandler.clearOnClickListener(view);
	}

	@Override
	public void destroy() {
		impressionTracker.destroy();
	}

	protected ImpressionTracker getImpressionTracker() {
		return impressionTracker;
	}

	protected NativeClickHandler getClickHandler() {
		return clickHandler;
	}
}
