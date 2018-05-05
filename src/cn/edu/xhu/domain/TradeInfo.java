package cn.edu.xhu.domain;

import java.io.Serializable;

public class TradeInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;// ID
	private int houseid;// 房屋编号
	private int sellerid;// 卖方ID
	private int buyerid;// 买方ID
	private String time;// 交易时间
	private House house;// 房屋
	private User seller;// 买家
	private User buyer;// 卖家

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getHouseid() {
		return houseid;
	}

	public void setHouseid(int houseid) {
		this.houseid = houseid;
	}

	public int getSellerid() {
		return sellerid;
	}

	public void setSellerid(int sellerid) {
		this.sellerid = sellerid;
	}

	public int getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(int buyerid) {
		this.buyerid = buyerid;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

}
