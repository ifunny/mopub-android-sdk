package com.mopub.mobileads.events;

/**
 * Created by Shad on 14.10.15.
 */
public enum BannerAdType {
	AdMob("AdMob"),
	AdMobECPM("AdMobECPM"),
	MopubHTML("MoPubHTML"),
	MopubMRAID("MoPubMRAID"),
	Facebook("Facebook"),
	Inneractive("Inneractive"),
	InMobi("InMobi"),
	OpenX("OpenX");
	
	private String name;
	
	BannerAdType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public static BannerAdType fromAdapterClass(String className) {
		if (className == null) {
			return MopubHTML;
		}
		switch (className) {
			case "com.mopub.mobileads.AdMobGeneric": {
				return AdMob;
			}
			case "com.mopub.mobileads.AdMobWCPMFloor": {
				return AdMobECPM;
			}
			case "com.mopub.mobileads.FacebookBanner": {
				return Facebook;
			}
			case "com.mopub.mobileads.GooglePlayServicesBanner": {
				return AdMob;
			}
			case "com.mopub.mobileads.InneractiveBanner": {
				return Inneractive;
			}
			case "com.mopub.mobileads.OpenXBanner": {
				return OpenX;
			}
			case "com.mopub.mobileads.InMobiBannerCustomEvent": {
				return InMobi;
			}
			case "com.mopub.mobileads.HtmlBanner": {
				return MopubHTML;
			}
			case "com.mopub.mraid.MraidBanner": {
				return MopubMRAID;
			}
			default: {
				return MopubHTML;
			}
		}
	}
}
