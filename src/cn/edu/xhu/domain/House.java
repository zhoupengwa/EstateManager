package cn.edu.xhu.domain;

import java.io.Serializable;

public class House implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;// ���ݱ��
	private int userid;// ӵ���߱��
	private String name;// չʾ��
	private String kind;// ��������[����][����][����]
	private String area;// ������Ȧ
	private String village;// ����С��
	private String address;// �����ַ
	private int size;// �������
	private String type;// ����
	private boolean traded;// ���׷�1-�ѽ��ס���0-δ���ס�
	private boolean qualified;// ��˺ϸ��1-��˺ϸ񡿡�0-���ϸ�
	private String contact;// ��ϵ��
	private String phone;// ��ϵ��ʽ
	private int price;// �۸�
	private String time;// ����ʱ��
	private String image;// ͼƬ

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
