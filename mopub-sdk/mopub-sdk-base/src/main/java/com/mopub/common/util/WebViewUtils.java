package com.mopub.common.util;

import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class WebViewUtils {
	
	public static void noCacheWebView(WebView webView) {
		WebSettings settings = webView.getSettings();
		settings.setDomStorageEnabled(true);
		settings.setLoadWithOverviewMode(true);
		settings.setUseWideViewPort(true);
		settings.setSupportZoom(false);
		settings.setBuiltInZoomControls(false);
		settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
	}
	
	public static void noCacheWebViewIn(View root) {
		WebView webView = Views.findView(root, WebView.class);
		if (webView != null) {
			noCacheWebView(webView);
		}
	}
	
}
