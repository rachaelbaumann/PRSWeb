package com.prs.util;

public class JsonResponse {
	
	private int code;
	private String message;
	private Object data;
	private Object error;
	public static final String SUCCESS = "Success!";
	public static final String FAILURE = "Failure!";
	
	public JsonResponse() {
		this(0);
	}
	
	public JsonResponse(int code) {
		this(code, code == 0 ? SUCCESS : FAILURE);
	}
	
	public JsonResponse(Object data) {
		this(0, SUCCESS, data, null);
	}
	
	public JsonResponse(int code, String message) {
		this(code, message, null, null);
	}
	
	public JsonResponse(int code, String message, Object data, Object error) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
		this.error = error;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Object getError() {
		return error;
	}

	public void setError(Object error) {
		this.error = error;
	}
	
	public static JsonResponse getInstance(Object d) {
		return new JsonResponse(0, SUCCESS, d, null);
	}
	
	public static JsonResponse getErrorInstance(String m, Exception e) {
		return new JsonResponse(-1, m, null, e);
	}
	
}