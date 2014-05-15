package com.huchcode.train.android.sample.chap2.domain;

import java.util.Date;

public class Twit {
	private long id;
	private String contents;
	private String authorId;
	private String profileImageUrl;
	private Date published;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

}
