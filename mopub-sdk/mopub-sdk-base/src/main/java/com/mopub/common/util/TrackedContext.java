package com.mopub.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by Shad on 17/04/2017.
 */
public class TrackedContext extends ContextWrapper {
	private Context context;
	
	public TrackedContext(Context base) {
		super(base);
		this.context = base;
	}
	
	@Override
	public void startActivity(Intent intent) {
		markIntent(intent);
		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		super.startActivity(intent);
	}
	
	@Override
	public void startActivity(Intent intent, Bundle options) {
		markIntent(intent);
		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		super.startActivity(intent, options);
	}
	
	private static final String AD_TRACKED_INTENT = "AD_TRACKED_INTENT";
	
	public static void markIntent(Intent intent) {
		intent.putExtra(AD_TRACKED_INTENT, true);
	}
	
	public static boolean isIntentMarked(Intent intent) {
		return intent.hasExtra(AD_TRACKED_INTENT);
	}
}
