package cn.edu.xhu.exception;

public class AdminDaoOperatException extends Exception {
	private static final long serialVersionUID = 1L;
	String mMessage = null;

	public AdminDaoOperatException(String message) {
		super(message);
		mMessage = message;
	}

	@Override
	public String toString() {
		return mMessage;
	}
}
