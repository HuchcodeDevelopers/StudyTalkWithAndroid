package com.huchcode.train.android.sample.chap2.provider;

import java.util.ArrayList;

import com.huchcode.train.android.sample.chap2.domain.Twit;

public class TwitProvider {
	private static TwitProvider instance = new TwitProvider();

	public static TwitProvider getInstance() {
		return instance;
	}

	private ArrayList<Twit> twits;

	private TwitProvider() {
		Twit twit = null;

		twits = new ArrayList<Twit>();
		twit = new Twit();
		twit.setId(1);
		twit.setAuthorId("@oisoo2");
		twit.setContents("Hihihihi");

		twits.add(twit);
		twit = new Twit();
		twit.setId(2);
		twit.setAuthorId("@oisoo2");
		twit.setContents("This is second try");

		twits.add(twit);
		twit = new Twit();
		twit.setId(3);
		twit.setAuthorId("@oisoo2");
		twit.setContents("This is third try");

	}

	public ArrayList<Twit> findTwits() {
		return this.twits;
	}

}
