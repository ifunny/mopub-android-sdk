package com.mopub.common.util;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewUtils {
	
	public static void noCacheWebView(WebView webView) {
		WebSettings settings = webView.getSettings();
		settings.setDomStorageEnabled(true);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		settings.setAppCacheEnabled(false);
	}
	
	public static void noCacheWebViewIn(View root) {
		WebView webView = Views.findView(root, WebView.class);
		if (webView != null) {
			noCacheWebView(webView);
		}
	}
	
}
