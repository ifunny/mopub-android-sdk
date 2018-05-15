//@formatter:off
package com.mopub.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.mopub.common.DataKeys;
import com.mopub.common.MoPub.BrowserAgent;
import com.mopub.common.util.DateAndTime;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String TIER_NAME = "name";
    private static final String MARKER_OFFSET = "marker_offset";
    private static final String ROTATION_RATE = "rotation_rate";
    private static final String TIER_NAME_REGEX = "\"funcorp_tier_name\"=\"([^\"]+)\"";
    private static final String MARKER_OFFSET_REGEX = "\"funcorp_tier_marker_offset\"=\"(\\d+)\"";
    private static final String ROTATION_RATE_REGEX = "\"funcorp_rotation_rate\"=\"(\\d+)\"";
    private static final Pattern tierNamePattern = Pattern.compile(TIER_NAME_REGEX, Pattern.CASE_INSENSITIVE);
    private static final Pattern markerOffsetPattern = Pattern.compile(MARKER_OFFSET_REGEX, Pattern.CASE_INSENSITIVE);
    private static final Pattern rotationRatePattern = Pattern.compile(ROTATION_RATE_REGEX, Pattern.CASE_INSENSITIVE);

    @Nullable
    private final String mAdType;

    @Nullable
    private final String mAdUnitId;

    @Nullable
    private final String mFullAdType;
    @Nullable
    private final String mNetworkType;

    @Nullable
    private final String mRewardedVideoCurrencyName;
    @Nullable
    private final String mRewardedVideoCurrencyAmount;
    @Nullable
    private final String mRewardedCurrencies;
    @Nullable
    private final String mRewardedVideoCompletionUrl;
    @Nullable
    private final Integer mRewardedDuration;
    private final boolean mShouldRewardOnClick;

    @Nullable
    private final String mRedirectUrl;
    @Nullable
    private final String mClickTrackingUrl;
    @Nullable
    private final String mImpressionTrackingUrl;
    @Nullable
    private final String mFailoverUrl;
    @Nullable
    private final String mRequestId;

    @Nullable
    private final Integer mWidth;
    @Nullable
    private final Integer mHeight;
    @Nullable
    private final Integer mAdTimeoutDelayMillis;
    @Nullable
    private final Integer mRefreshTimeMillis;
    @Nullable
    private final String mDspCreativeId;

    private final boolean mScrollable;

    @Nullable
    private final String mResponseBody;
    @Nullable
    private final JSONObject mJsonBody;

    @Nullable
    private final String mCustomEventClassName;
    @Nullable
    private final BrowserAgent mBrowserAgent;
    @NonNull
    private final Map<String, String> mServerExtras;

    private final long mTimestamp;

    @Nullable
    private final String tierName;

    @Nullable
    private  Integer markerOffset;
    
    @Nullable
    private Integer rotationRate;

    private AdResponse(@NonNull Builder builder) {
        mAdType = builder.adType;
        mAdUnitId = builder.adUnitId;
        mFullAdType = builder.fullAdType;
        mNetworkType = builder.networkType;

        mRewardedVideoCurrencyName = builder.rewardedVideoCurrencyName;
        mRewardedVideoCurrencyAmount = builder.rewardedVideoCurrencyAmount;
        mRewardedCurrencies = builder.rewardedCurrencies;
        mRewardedVideoCompletionUrl = builder.rewardedVideoCompletionUrl;
        mRewardedDuration = builder.rewardedDuration;
        mShouldRewardOnClick = builder.shouldRewardOnClick;

        mRedirectUrl = builder.redirectUrl;
        mClickTrackingUrl = builder.clickTrackingUrl;
        mImpressionTrackingUrl = builder.impressionTrackingUrl;
        mFailoverUrl = builder.failoverUrl;
        mRequestId = builder.requestId;
        mWidth = builder.width;
        mHeight = builder.height;
        mAdTimeoutDelayMillis = builder.adTimeoutDelayMillis;
        mRefreshTimeMillis = builder.refreshTimeMillis;
        mDspCreativeId = builder.dspCreativeId;
        mScrollable = builder.scrollable;
        mResponseBody = builder.responseBody;
        mJsonBody = builder.jsonBody;
        mCustomEventClassName = builder.customEventClassName;
        mBrowserAgent = builder.browserAgent;
        mServerExtras = builder.serverExtras;
        mTimestamp = DateAndTime.now().getTime();

        String stringOffset;
        String stringRate;
        if (mServerExtras.containsKey(DataKeys.HTML_RESPONSE_BODY_KEY)) {
            String data = mServerExtras.get(DataKeys.HTML_RESPONSE_BODY_KEY);

            tierName = getFirstMatchedGroup(data, tierNamePattern);
            stringOffset = getFirstMatchedGroup(data, markerOffsetPattern);
            stringRate = getFirstMatchedGroup(data, rotationRatePattern);
        }
        else {
            tierName = mServerExtras.get(TIER_NAME);
            stringOffset = mServerExtras.get(MARKER_OFFSET);
            stringRate = mServerExtras.get(ROTATION_RATE);
        }

        markerOffset = stringToInt(stringOffset);
        rotationRate = stringToInt(stringRate);
        
    }

    private String getFirstMatchedGroup(String data, Pattern pattern) {
        try {
            Matcher matcher = pattern.matcher(data);
            if (matcher.find()) {
                return matcher.group(1);
            }
            return null;
        } catch (Exception e) {

            return null;
        }
    }


    private Integer stringToInt(String number) {
        try {
            return TextUtils.isEmpty(number) ? null : Integer.valueOf(number);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public boolean hasJson() {
        return mJsonBody != null;
    }

    @Nullable
    public JSONObject getJsonBody() {
        return mJsonBody;
    }

    @Nullable
    public String getStringBody() {
        return mResponseBody;
    }

    @Nullable
    public String getAdType() {
        return mAdType;
    }

    @Nullable
    public String getFullAdType() {
        return mFullAdType;
    }

    @Nullable
    public String getAdUnitId() {
        return mAdUnitId;
    }

    @Nullable
    public String getNetworkType() {
        return mNetworkType;
    }

    @Nullable
    public String getRewardedVideoCurrencyName() {
        return mRewardedVideoCurrencyName;
    }

    @Nullable
    public String getRewardedVideoCurrencyAmount() {
        return mRewardedVideoCurrencyAmount;
    }

    @Nullable
    public String getRewardedCurrencies() {
        return mRewardedCurrencies;
    }

    @Nullable
    public String getRewardedVideoCompletionUrl() {
        return mRewardedVideoCompletionUrl;
    }

    @Nullable
    public Integer getRewardedDuration() {
        return mRewardedDuration;
    }

    public boolean shouldRewardOnClick() {
        return mShouldRewardOnClick;
    }

    @Nullable
    public String getRedirectUrl() {
        return mRedirectUrl;
    }

    @Nullable
    public String getClickTrackingUrl() {
        return mClickTrackingUrl;
    }

    @Nullable
    public String getImpressionTrackingUrl() {
        return mImpressionTrackingUrl;
    }

    @Nullable
    public String getFailoverUrl() {
        return mFailoverUrl;
    }

    @Nullable
    public String getRequestId() {
        return mRequestId;
    }

    public boolean isScrollable() {
        return mScrollable;
    }

    @Nullable
    public Integer getWidth() {
        return mWidth;
    }

    @Nullable
    public Integer getHeight() {
        return mHeight;
    }

    @Nullable
    public Integer getAdTimeoutMillis() {
        return mAdTimeoutDelayMillis;
    }

    @Nullable
    public Integer getRefreshTimeMillis() {
        return mRefreshTimeMillis;
    }

    @Nullable
    public String getDspCreativeId() {
        return mDspCreativeId;
    }

    @Nullable
    public String getCustomEventClassName() {
        return mCustomEventClassName;
    }

    @Nullable
    public BrowserAgent getBrowserAgent() { return mBrowserAgent; }

    @NonNull
    public Map<String, String> getServerExtras() {
        // Strings are immutable, so this works as a "deep" copy.
        return new TreeMap<String, String>(mServerExtras);
    }

    public long getTimestamp() {
        return mTimestamp;
    }

    @Nullable
    public String getTierName() {
        return tierName;
    }

    @Nullable
    public Integer getMarkerOffset() {
        return markerOffset;
    }
    
    @Nullable
    public Integer getRotationRate() {
	    return rotationRate;
    }

    public Builder toBuilder() {
        return new Builder()
                .setAdType(mAdType)
                .setNetworkType(mNetworkType)
                .setRewardedVideoCurrencyName(mRewardedVideoCurrencyName)
                .setRewardedVideoCurrencyAmount(mRewardedVideoCurrencyAmount)
                .setRewardedCurrencies(mRewardedCurrencies)
                .setRewardedVideoCompletionUrl(mRewardedVideoCompletionUrl)
                .setRewardedDuration(mRewardedDuration)
                .setShouldRewardOnClick(mShouldRewardOnClick)
                .setRedirectUrl(mRedirectUrl)
                .setClickTrackingUrl(mClickTrackingUrl)
                .setImpressionTrackingUrl(mImpressionTrackingUrl)
                .setFailoverUrl(mFailoverUrl)
                .setDimensions(mWidth, mHeight)
                .setAdTimeoutDelayMilliseconds(mAdTimeoutDelayMillis)
                .setRefreshTimeMilliseconds(mRefreshTimeMillis)
                .setDspCreativeId(mDspCreativeId)
                .setScrollable(mScrollable)
                .setResponseBody(mResponseBody)
                .setJsonBody(mJsonBody)
                .setCustomEventClassName(mCustomEventClassName)
                .setBrowserAgent(mBrowserAgent)
                .setServerExtras(mServerExtras);
    }

    public static class Builder {
        private String adType;
        private String adUnitId;
        private String fullAdType;
        private String networkType;

        private String rewardedVideoCurrencyName;
        private String rewardedVideoCurrencyAmount;
        private String rewardedCurrencies;
        private String rewardedVideoCompletionUrl;
        private Integer rewardedDuration;
        private boolean shouldRewardOnClick;

        private String redirectUrl;
        private String clickTrackingUrl;
        private String impressionTrackingUrl;
        private String failoverUrl;
        private String requestId;

        private Integer width;
        private Integer height;
        private Integer adTimeoutDelayMillis;
        private Integer refreshTimeMillis;
        private String dspCreativeId;

        private boolean scrollable = false;

        private String responseBody;
        private JSONObject jsonBody;

        private String customEventClassName;
        private BrowserAgent browserAgent;

        private Map<String, String> serverExtras = new TreeMap<String, String>();

        public Builder setAdType(@Nullable final String adType) {
            this.adType = adType;
            return this;
        }

        public Builder setAdUnitId(@Nullable final String adUnitId) {
            this.adUnitId = adUnitId;
            return this;
        }

        public Builder setFullAdType(@Nullable final String fullAdType) {
            this.fullAdType = fullAdType;
            return this;
        }

        public Builder setNetworkType(@Nullable final String networkType) {
            this.networkType = networkType;
            return this;
        }

        public Builder setRewardedVideoCurrencyName(
                @Nullable final String rewardedVideoCurrencyName) {
            this.rewardedVideoCurrencyName = rewardedVideoCurrencyName;
            return this;
        }

        public Builder setRewardedVideoCurrencyAmount(
                @Nullable final String rewardedVideoCurrencyAmount) {
            this.rewardedVideoCurrencyAmount = rewardedVideoCurrencyAmount;
            return this;
        }

        public Builder setRewardedCurrencies(@Nullable final String rewardedCurrencies) {
            this.rewardedCurrencies = rewardedCurrencies;
            return this;
        }

        public Builder setRewardedVideoCompletionUrl(
                @Nullable final String rewardedVideoCompletionUrl) {
            this.rewardedVideoCompletionUrl = rewardedVideoCompletionUrl;
            return this;
        }

        public Builder setRewardedDuration(@Nullable final Integer rewardedDuration) {
            this.rewardedDuration = rewardedDuration;
            return this;
        }

        public Builder setShouldRewardOnClick(final boolean shouldRewardOnClick) {
            this.shouldRewardOnClick = shouldRewardOnClick;
            return this;
        }

        public Builder setRedirectUrl(@Nullable final String redirectUrl) {
            this.redirectUrl = redirectUrl;
            return this;
        }

        public Builder setClickTrackingUrl(@Nullable final String clickTrackingUrl) {
            this.clickTrackingUrl = clickTrackingUrl;
            return this;
        }

        public Builder setImpressionTrackingUrl(@Nullable final String impressionTrackingUrl) {
            this.impressionTrackingUrl = impressionTrackingUrl;
            return this;
        }

        public Builder setFailoverUrl(@Nullable final String failoverUrl) {
            this.failoverUrl = failoverUrl;
            return this;
        }

        public Builder setRequestId(@Nullable final String requestId) {
            this.requestId = requestId;
            return this;
        }

        public Builder setDimensions(@Nullable final Integer width,
                @Nullable final Integer height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder setAdTimeoutDelayMilliseconds(@Nullable final Integer adTimeoutDelayMilliseconds) {
            this.adTimeoutDelayMillis = adTimeoutDelayMilliseconds;
            return this;
        }

        public Builder setRefreshTimeMilliseconds(@Nullable final Integer refreshTimeMilliseconds) {
            this.refreshTimeMillis = refreshTimeMilliseconds;
            return this;
        }

        public Builder setScrollable(@Nullable final Boolean scrollable) {
            this.scrollable = scrollable == null ? this.scrollable : scrollable;
            return this;
        }

        public Builder setDspCreativeId(@Nullable final String dspCreativeId) {
            this.dspCreativeId = dspCreativeId;
            return this;
        }

        public Builder setResponseBody(@Nullable final String responseBody) {
            this.responseBody = responseBody;
            return this;
        }

        public Builder setJsonBody(@Nullable final JSONObject jsonBody) {
            this.jsonBody = jsonBody;
            return this;
        }

        public Builder setCustomEventClassName(@Nullable final String customEventClassName) {
            this.customEventClassName = customEventClassName;
            return this;
        }

        public Builder setBrowserAgent(@Nullable final BrowserAgent browserAgent) {
            this.browserAgent = browserAgent;
            return this;
        }

        public Builder setServerExtras(@Nullable final Map<String, String> serverExtras) {
            if (serverExtras == null) {
                this.serverExtras = new TreeMap<String, String>();
            } else {
                this.serverExtras = new TreeMap<String, String>(serverExtras);
            }
            return this;
        }

        public AdResponse build() {
            return new AdResponse(this);
        }
    }
}
