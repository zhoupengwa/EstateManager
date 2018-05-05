
package cn.edu.xhu.domain;

import java.io.Serializable;

public class Image implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;// 照片ID
	private int type;// 【1-房屋照片】【0-房产证】
	private int houseid;// 房屋ID
	private String path;// 照片路径

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
