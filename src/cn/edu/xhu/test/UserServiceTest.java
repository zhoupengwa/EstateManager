package cn.edu.xhu.test;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xhu.domain.House;
import cn.edu.xhu.domain.Image;
import cn.edu.xhu.domain.User;
import cn.edu.xhu.service.UserService;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class UserServiceTest {

	// 注册
	public void testRegister() {
		User user = new User();
		user.setPhone("15000000007");
		user.setName("小张");
		user.setPassword("5555");
		String json = new UserService().registerUser(user);
		System.out.println(json);
	}

	// 登录
	public void testLogin() {
		// String json = new UserService().login("18483638748", "123456789",ne);
		// System.out.println(json);
	}

	// 查看个人信息
	public void testLookInfo() {

		String json = new UserService().lookInfo(3);
		System.out.println(json);
	}

	// 修改个人信息
	public void testUpdateInfo() {
		User user = new User();
		user.setId(3);
		user.setPhone("184836387411");
		user.setName("杨艺林");
		user.setPassword("123456");
		user.setAddress("四川广元");
		user.setSex("女");
		String json = new UserService().updateInfo(user);
		System.out.println(json);
	}

	// 发布房屋
	public void testRelaseHouse() {
		House house = new House();
		house.setUserid(1);
		house.setName("德馨");
		house.setKind("出租");
		house.setArea("红光经济圈");
		house.setVillage("西华大学西华苑");
		house.setAddress("红光镇西华大学");
		house.setSize(80);
		house.setContact("小李");
		house.setPhone("18483638748");
		house.setPrice(120);
		house.setTime(ThreadLocalDateUtil.formatDate());
		String json = new UserService().relaseHouse(house);
		System.out.println(json);
	}

	// 修改房屋信息
	public void testUpdateHouse() {
		House house = new House();
		house.setId(3);
		house.setName("怡园商铺");
		house.setArea("郫县经济圈");
		house.setVillage("西华大学西华苑");
		house.setAddress("红光镇西华大学");
		house.setSize(80);
		house.setContact("小李");
		house.setPhone("18483638748");
		house.setPrice(120);
		house.setTime(ThreadLocalDateUtil.formatDate());
		String json = new UserService().updateHouseInfo(house);
		System.out.println(json);
	}

	// 查看已发布的房屋
	public void testListHouses() {
		UserService service = new UserService();
		String json = service.listPrettyHouses();
		System.out.println(json);
	}

	// 查看自己发布的房屋
	public void testListHousesByUserId() {
		UserService service = new UserService();
		int uesrId = 1;
		String json = service.listHousesByUserId(uesrId);
		System.out.println(json);
	}

	// 上传图片
	public void testUploadImage() {
		UserService service = new UserService();
		List<Image> images = new ArrayList<Image>();
		Image image1 = new Image();
		image1.setPath("d:\\11.jpg");
		image1.setType(1);
		Image image2 = new Image();
		image2.setPath("d:\\22.jpg");
		image2.setType(1);
		images.add(image1);
		images.add(image2);
		String json = service.uploadImage(34, images);
		System.out.println(json);
	}

	// 查询某个房屋的图片
	public void testQueryImagesByHouseId() {
		UserService service = new UserService();
		String json = service.queryImagesByHouseId(2);
		System.out.println(json);
	}

	// 删除某张图片
	public void deleteImageById() {
		// UserService service = new UserService();
		// String json = service.deleteImageById(2);
		// System.out.println(json);
	}

	// 下架房屋
	public void deleteHouse() {
		// UserService service = new UserService();
		// String json = service.deleteHouse(1);
		// System.out.println(json);
	}

	// 购买/租房
	public void testRequestDeal() {
		// UserService service = new UserService();
		// String json = service.requestDeal(4, 1, 2);
		// System.out.println(json);
	}

//	// 查看交易记录
//	public void testQueryTradeInfo() {
//		UserService service = new UserService();
//		String json = service.queryTradeInfosByUserId(1);
//		System.out.println(json);
//	}

	// 查看购买记录
	public void testQueryBuyInfo() {
		UserService service = new UserService();
		String json = service.queryBuyInfosByUserId(2);
		System.out.println(json);
	}

	// 查看出售记录
	public void testQuerySellInfo() {
		UserService service = new UserService();
		String json = service.querySellInfosByUserId(2);
		System.out.println(json);
	}
}
