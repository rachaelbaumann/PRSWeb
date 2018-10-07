package com.prs.util;

public class PRSMaintenanceReturn {
	private String result;
	private String message;
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";
	
	public PRSMaintenanceReturn() {
		result = "Initialized result, not yet set.";
		message = "Initialized message, not yet set.";
	}
	
	public PRSMaintenanceReturn(String result, String message) {
		super();
		this.result = result;
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static PRSMaintenanceReturn getMaintReturn(Object obj) {
		PRSMaintenanceReturn r = new PRSMaintenanceReturn();
		if (obj!=null) {
			r.setResult(SUCCESS);
			r.setMessage(obj.getClass().getSimpleName() + " maintenance success");
		} else {
			r.setResult(FAILURE);
			// Can't use obj.getClass() because obj is null
			r.setMessage("Not found");
		}
		return r;

	}

	/*
	 * Get a maintenance return obj and include an error message
	 */
	public static PRSMaintenanceReturn getMaintReturnError(Object obj, String msg) {
		PRSMaintenanceReturn r = new PRSMaintenanceReturn();
		String errMsg = "";
		r.setResult(FAILURE);
		if (obj != null) {
			errMsg = (obj.getClass().getSimpleName() + " maintenance error: ");
		}
		else {
			// Can't use obj.getClass() because obj is null
			errMsg = "Maintenance error: ";			
		}
		errMsg += msg;
		r.setMessage(errMsg);
		return r;

	}
}