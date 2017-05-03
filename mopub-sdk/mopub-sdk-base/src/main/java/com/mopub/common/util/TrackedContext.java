package com.mopub.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by Shad on 17/04/2017.
 */
public class TrackedContext extends ContextWrapper {
	private WeakReference<Activity> activityRef;
	
	public TrackedContext(Context base) {
		super(base);
	}
	
	public void attachActivityContext(@NonNull Activity activity) {
		activityRef = new WeakReference<>(activity);
	}
	
	@Nullable
	public Activity getActivityContext() {
		if (activityRef == null) {
			return null;
		}
		
		return activityRef.get();
	}
	
	@Override
	public void startActivity(Intent intent) {
		markIntent(intent);
		Context context = getBaseContext();
		if (!(context instanceof Activity)) {
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		}
		super.startActivity(intent);
	}
	
	@Override
	public void startActivity(Intent intent, Bundle options) {
		markIntent(intent);
		Context context = getBaseContext();
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
