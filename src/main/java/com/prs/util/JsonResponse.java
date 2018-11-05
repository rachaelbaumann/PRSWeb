package com.prs.util;

/*
 * JsonResponse is a standard response to return from any back end 
 * Controller.  It will return the expected result of the call in the 
 * data element and any success/failure messaging in code, message and error
 */
public class JsonResponse {
	
	// status code:  0 = success, anything else is failure
	private int code;
	// error message
	private String message;
	// data returned from the service call
	private Object data;
	// additional error info, typically the Exception instance
	private Object error;
	public static final String SUCCESS = "Success!";
	public static final String FAILURE = "Failure.";
	
	// Empty constructor, returns success
	public JsonResponse() {
		this(0);
	}
	
	public JsonResponse(int code) {
		this(code, code == 0 ? SUCCESS : FAILURE);
	}
	
	// Successful call, accepting data to be returned
	public JsonResponse(Object data) {
		this(0, SUCCESS, data, null);
	}
	
	// Failure call, accepting code and message
	public JsonResponse(int code, String message) {
		this(code, message, null, null);
	}
	
	// Fully qualified constructor
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
	
	// Return a success instance w/ data result from service call
	public static JsonResponse getInstance(Object d) {
		return new JsonResponse(0, SUCCESS, d, null);
	}
	
	// Return an error instance
	public static JsonResponse getErrorInstance(String m, Exception e) {
		return new JsonResponse(-1, m, null, e);
	}
	
	// Return an error instance
	public static JsonResponse getErrorInstance(String m) {
		return new JsonResponse(-1, m, null, null);
	}

	// Return an error instance
	public static JsonResponse getErrorInstance(Object d, String m) {
		return new JsonResponse(-1, m, null, null);
	}
}


























//package com.prs.util;
//
//public class JsonResponse {
//	
//	private int code;
//	private String message;
//	private Object data;
//	private Object error;
//	public static final String SUCCESS = "Success!";
//	public static final String FAILURE = "Failure!";
//	
//	public JsonResponse() {
//		this(0);
//	}
//	
//	public JsonResponse(int code) {
//		this(code, code == 0 ? SUCCESS : FAILURE);
//	}
//	
//	public JsonResponse(Object data) {
//		this(0, SUCCESS, data, null);
//	}
//	
//	public JsonResponse(int code, String message) {
//		this(code, message, null, null);
//	}
//	
//	public JsonResponse(int code, String message, Object data, Object error) {
//		super();
//		this.code = code;
//		this.message = message;
//		this.data = data;
//		this.error = error;
//	}
//
//	public int getCode() {
//		return code;
//	}
//
//	public void setCode(int code) {
//		this.code = code;
//	}
//
//	public String getMessage() {
//		return message;
//	}
//
//	public void setMessage(String message) {
//		this.message = message;
//	}
//
//	public Object getData() {
//		return data;
//	}
//
//	public void setData(Object data) {
//		this.data = data;
//	}
//
//	public Object getError() {
//		return error;
//	}
//
//	public void setError(Object error) {
//		this.error = error;
//	}
//	
//	public static JsonResponse getInstance(Object d) {
//		return new JsonResponse(0, SUCCESS, d, null);
//	}
//	
//	public static JsonResponse getErrorInstance(String m, Exception e) {
//		return new JsonResponse(-1, m, null, e);
//	}
//	
//}