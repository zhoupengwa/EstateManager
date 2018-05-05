package cn.edu.xhu.domain;

import java.io.Serializable;

public class House implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;// 房屋编号
	private int userid;// 拥有者编号
	private String name;// 展示名
	private String kind;// 房屋类型[出售][出租][商铺]
	private String area;// 所在商圈
	private String village;// 具体小区
	private String address;// 具体地址
	private int size;// 房屋面积
	private String type;// 户型
	private boolean traded;// 交易否【1-已交易】【0-未交易】
	private boolean qualified;// 审核合格否【1-审核合格】【0-不合格】
	private String contact;// 联系人
	private String phone;// 联系方式
	private int price;// 价格
	private String time;// 发布时间
	private String image;// 图片

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isTraded() {
		return traded;
	}

	public void setTraded(boolean traded) {
		this.traded = traded;
	}

	public boolean isQualified() {
		return qualified;
	}

	public void setQualified(boolean qualified) {
		this.qualified = qualified;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
