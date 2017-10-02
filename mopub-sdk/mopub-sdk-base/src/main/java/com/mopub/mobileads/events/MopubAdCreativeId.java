package com.mopub.mobileads.events;

/**
 * Created by Shad on 30/09/2017.
 */

public class MopubAdCreativeId implements AdCreativeIdBundle {
	
	private String mDspCreativeID;
	
	public MopubAdCreativeId(final String dspCreativeID) {
		mDspCreativeID = dspCreativeID;
	}
	
	
	@Override
	public String toStringReport() {
		return String.format("DSPCreativeId=%s", mDspCreativeID);
	}
	
}
