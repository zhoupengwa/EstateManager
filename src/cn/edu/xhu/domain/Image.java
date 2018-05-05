
package cn.edu.xhu.domain;

import java.io.Serializable;

public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;// ��ƬID
	private int type;// ��1-������Ƭ����0-����֤��
	private int houseid;// ����ID
	private String path;// ��Ƭ·��

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getHouseid() {
		return houseid;
	}

	public void setHouseid(int houseid) {
		this.houseid = houseid;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
