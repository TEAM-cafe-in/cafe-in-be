package com.cafein.backend.api.member.dto;

public interface CafeInfoViewedByMemberProjection {

	String getCafeName();
	String getAddress();
	String getCommentReviewCount();

	void setCafeName(String cafeName);
	void setAddress(String address);
	void setCommentReviewCount(String commentReviewCount);
}
