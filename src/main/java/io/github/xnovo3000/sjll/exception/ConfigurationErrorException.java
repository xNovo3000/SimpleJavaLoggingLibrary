package io.github.xnovo3000.sjll.exception;

import org.json.JSONException;

public class ConfigurationErrorException extends Exception {
	
	public ConfigurationErrorException(JSONException e) {
		super(e);
	}
	
	public ConfigurationErrorException(String message) {
		super(message);
	}
	
}
