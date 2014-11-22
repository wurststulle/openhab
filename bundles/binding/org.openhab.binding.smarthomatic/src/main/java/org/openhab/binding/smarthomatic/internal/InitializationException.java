package org.openhab.binding.smarthomatic.internal;

public class InitializationException extends Exception {

	private static final long serialVersionUID = 3215690312124168361L;

	public InitializationException(String msg) {
		super(msg);
	}

	public InitializationException(Throwable cause) {
		super(cause);
	}

	public InitializationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
