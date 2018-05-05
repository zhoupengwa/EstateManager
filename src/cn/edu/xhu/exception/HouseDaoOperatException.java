package cn.edu.xhu.exception;

public class HouseDaoOperatException extends Exception {
	private static final long serialVersionUID = 1L;
	String mMessage = null;

	public HouseDaoOperatException(String message) {
		super(message);
		mMessage = message;
	}

	@Override
	public String toString() {
		return mMessage;
	}
}
