package com.admin.common.vo;

public class JsonResponseVO {
	private String status;
	private String redirectUrl;
	private String css;
	private String message;
	private String jsonString;
	
	public JsonResponseVO(String status, String message) {
		this.status = status;
		this.message = message;
	}

	public JsonResponseVO(String status) {
		this.status = status;
	}
	
	public JsonResponseVO() {
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
	public String getCss() {
		return css;
	}
	public void setCss(String css) {
		this.css = css;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
}
