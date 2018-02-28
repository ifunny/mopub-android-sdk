package com.mopub.nativeads;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ViewBinder {
    public final static class Builder {
        private final int layoutId;
        private int titleId;
        private String defaultTitle;
        private int textId;
        private String defaultText;
        private int callToActionId;
        private int mainImageId;
        private int iconImageId;
        @DrawableRes private int defaultIconImageDrawableId;
        private int privacyInformationIconImageId;
        @NonNull private Map<String, Integer> extras = Collections.emptyMap();

        public Builder(final int layoutId) {
            this.layoutId = layoutId;
            this.extras = new HashMap<String, Integer>();
        }

        @NonNull
        public final Builder titleId(final int titleId) {
            this.titleId = titleId;
            return this;
        }

        @NonNull
        public final Builder defaultTitle(final String defaultTitle) {
            this.defaultTitle = defaultTitle;
            return this;
        }

        @NonNull
        public final Builder textId(final int textId) {
            this.textId = textId;
            return this;
        }

        @NonNull
        public final Builder defaultText(final String defaultText) {
            this.defaultText = defaultText;
            return this;
        }

        @NonNull
        public final Builder callToActionId(final int callToActionId) {
            this.callToActionId = callToActionId;
            return this;
        }

        @NonNull
        public final Builder mainImageId(final int mediaLayoutId) {
            this.mainImageId = mediaLayoutId;
            return this;
        }

        @NonNull
        public final Builder iconImageId(final int iconImageId) {
            this.iconImageId = iconImageId;
            return this;
        }
        @NonNull
        public final Builder defaultIconDrawableId(@DrawableRes final int iconDrawableResourceId) {
            defaultIconImageDrawableId = iconDrawableResourceId;
            return this;
        }

        @NonNull
        public final Builder privacyInformationIconImageId(final int privacyInformationIconImageId) {
            this.privacyInformationIconImageId = privacyInformationIconImageId;
            return this;
        }

        @NonNull
        public final Builder addExtras(final Map<String, Integer> resourceIds) {
            this.extras = new HashMap<String, Integer>(resourceIds);
            return this;
        }

        @NonNull
        public final Builder addExtra(final String key, final int resourceId) {
            this.extras.put(key, resourceId);
            return this;
        }

        @NonNull
        public final ViewBinder build() {
            return new ViewBinder(this);
        }
    }

    final int layoutId;
    final int titleId;
    final String defaultTitle;
    final int textId;
    final String defaultText;
    final int callToActionId;
    final int mainImageId;
    final int iconImageId;
    @DrawableRes final int defaultIconImageDrawableId;
    final int privacyInformationIconImageId;
    @NonNull final Map<String, Integer> extras;

    private ViewBinder(@NonNull final Builder builder) {
        this.layoutId = builder.layoutId;
        this.titleId = builder.titleId;
        this.defaultTitle = builder.defaultTitle;
        this.textId = builder.textId;
        this.defaultText = builder.defaultText;
        this.callToActionId = builder.callToActionId;
        this.mainImageId = builder.mainImageId;
        this.iconImageId = builder.iconImageId;
        this.defaultIconImageDrawableId = builder.defaultIconImageDrawableId;
        this.privacyInformationIconImageId = builder.privacyInformationIconImageId;
        this.extras = builder.extras;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public int getTitleId() {
        return titleId;
    }
    
    public String getDefaultTitle() {
        return defaultTitle;
    }

    public int getTextId() {
        return textId;
    }
    
    public String getDefaultText() {
        return defaultText;
    }

    public int getCallToActionId() {
        return callToActionId;
    }

    public int getMainImageId() {
        return mainImageId;
    }

    public int getIconImageId() {
        return iconImageId;
    }

    @DrawableRes
    public int getDefaultIconImageDrawableId() {
        return defaultIconImageDrawableId;
    }

    public int getPrivacyInformationIconImageId() {
        return privacyInformationIconImageId;
    }

    @NonNull
    public Map<String, Integer> getExtras() {
        return extras;
    }
}
