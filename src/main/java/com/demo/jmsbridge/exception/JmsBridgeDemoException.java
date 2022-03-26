package com.demo.jmsbridge.exception;

public class JmsBridgeDemoException extends RuntimeException {

	private static final long serialVersionUID = 1799795790012289972L;

	public JmsBridgeDemoException() {
		super();
	}

	public JmsBridgeDemoException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public JmsBridgeDemoException(String message, Throwable cause) {
		super(message, cause);
	}

	public JmsBridgeDemoException(String message) {
		super(message);
	}

	public JmsBridgeDemoException(Throwable cause) {
		super(cause);
	}

}
