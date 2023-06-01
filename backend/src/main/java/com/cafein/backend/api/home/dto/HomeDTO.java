package com.cafein.backend.api.home.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;

public class HomeDTO {

	@Getter
	public static class Request {

		@NotNull
		private String local;

		@NotNull
		private String pageNo;

		@NotNull
		private String sortBy;

		@Email
		private String memberEmail;
	}

	@Getter @Builder
	public static class Response {

		private String name;
		private String address;
		private String commentReviewCount;
		private String status;
		private String averageCongestion;

		@Override
		public String toString() {
			return "Response{" +
				"name='" + name + '\'' +
				", address='" + address + '\'' +
				", commentReviewCount='" + commentReviewCount + '\'' +
				", status='" + status + '\'' +
				", averageCongestion='" + averageCongestion + '\'' +
				'}';
		}
	}
}
