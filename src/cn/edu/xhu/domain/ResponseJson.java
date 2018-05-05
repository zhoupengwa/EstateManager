package cn.edu.xhu.domain;

import java.io.Serializable;
import java.util.List;

public class ResponseJson<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private String state;
	private String message;
	private List<T> resultData;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<T> getResultData() {
		return resultData;
	}

	public void setResultData(List<T> resultData) {
		this.resultData = resultData;
	}

}
