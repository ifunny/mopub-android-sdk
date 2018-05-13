package com.mopub.common.util;

import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewUtils {
	
	public static void noCacheWebView(WebView webView) {
		WebSettings settings = webView.getSettings();
		settings.setDomStorageEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setAppCacheEnabled(false);
	}
	
}
