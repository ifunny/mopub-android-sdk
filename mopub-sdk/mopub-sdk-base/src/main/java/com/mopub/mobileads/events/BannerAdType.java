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
	Millennial("Millennial"),
	Inneractive("Inneractive"),
	Amazon("Amazon"),
	InMobi("InMobi"),
	Flurry("Flurry"),
	Mobfox("Mobfox");

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
			case "com.mopub.mobileads.AmazonBanner": {
				return Amazon;
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
			case "com.mopub.mobileads.MillennialBanner": {
				return Millennial;
			}
			case "com.mopub.mobileads.FlurryCustomEventBanner": {
				return Flurry;
			}
			case "com.mopub.mobileads.InMobiBannerCustomEvent": {
				return InMobi;
			}
			case "com.mopub.mobileads.HtmlBanner": {
				return MopubHTML;
			}
			case "com.mopub.mobileads.MraidBanner": {
				return MopubMRAID;
			}
			case "com.mopub.mobileads.MobfoxBanner": {
				return Mobfox;
			}
			default: {
				return MopubHTML;
			}
		}
	}
}
