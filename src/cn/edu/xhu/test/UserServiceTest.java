package cn.edu.xhu.test;

import java.util.ArrayList;
import java.util.List;

import cn.edu.xhu.domain.House;
import cn.edu.xhu.domain.Image;
import cn.edu.xhu.domain.User;
import cn.edu.xhu.service.UserService;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class UserServiceTest {

	// ע��
	public void testRegister() {
		User user = new User();
		user.setPhone("15000000007");
		user.setName("С��");
		user.setPassword("5555");
		String json = new UserService().registerUser(user);
		System.out.println(json);
	}

	// ��¼
	public void testLogin() {
		// String json = new UserService().login("18483638748", "123456789",ne);
		// System.out.println(json);
	}

	// �鿴������Ϣ
	public void testLookInfo() {

		String json = new UserService().lookInfo(3);
		System.out.println(json);
	}

	// �޸ĸ�����Ϣ
	public void testUpdateInfo() {
		User user = new User();
		user.setId(3);
		user.setPhone("184836387411");
		user.setName("������");
		user.setPassword("123456");
		user.setAddress("�Ĵ���Ԫ");
		user.setSex("Ů");
		String json = new UserService().updateInfo(user);
		System.out.println(json);
	}

	// ��������
	public void testRelaseHouse() {
		House house = new House();
		house.setUserid(1);
		house.setName("��ܰ");
		house.setKind("����");
		house.setArea("��⾭��Ȧ");
		house.setVillage("������ѧ����Է");
		house.setAddress("�����������ѧ");
		house.setSize(80);
		house.setContact("С��");
		house.setPhone("18483638748");
		house.setPrice(120);
		house.setTime(ThreadLocalDateUtil.formatDate());
		String json = new UserService().relaseHouse(house);
		System.out.println(json);
	}

	// �޸ķ�����Ϣ
	public void testUpdateHouse() {
		House house = new House();
		house.setId(3);
		house.setName("��԰����");
		house.setArea("ۯ�ؾ���Ȧ");
		house.setVillage("������ѧ����Է");
		house.setAddress("�����������ѧ");
		house.setSize(80);
		house.setContact("С��");
		house.setPhone("18483638748");
		house.setPrice(120);
		house.setTime(ThreadLocalDateUtil.formatDate());
		String json = new UserService().updateHouseInfo(house);
		System.out.println(json);
	}

	// �鿴�ѷ����ķ���
	public void testListHouses() {
		UserService service = new UserService();
		String json = service.listPrettyHouses();
		System.out.println(json);
	}

	// �鿴�Լ������ķ���
	public void testListHousesByUserId() {
		UserService service = new UserService();
		int uesrId = 1;
		String json = service.listHousesByUserId(uesrId);
		System.out.println(json);
	}

	// �ϴ�ͼƬ
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

	// ��ѯĳ�����ݵ�ͼƬ
	public void testQueryImagesByHouseId() {
		UserService service = new UserService();
		String json = service.queryImagesByHouseId(2);
		System.out.println(json);
	}

	// ɾ��ĳ��ͼƬ
	public void deleteImageById() {
		// UserService service = new UserService();
		// String json = service.deleteImageById(2);
		// System.out.println(json);
	}

	// �¼ܷ���
	public void deleteHouse() {
		// UserService service = new UserService();
		// String json = service.deleteHouse(1);
		// System.out.println(json);
	}

	// ����/�ⷿ
	public void testRequestDeal() {
		// UserService service = new UserService();
		// String json = service.requestDeal(4, 1, 2);
		// System.out.println(json);
	}

//	// �鿴���׼�¼
//	public void testQueryTradeInfo() {
//		UserService service = new UserService();
//		String json = service.queryTradeInfosByUserId(1);
//		System.out.println(json);
//	}

	// �鿴�����¼
	public void testQueryBuyInfo() {
		UserService service = new UserService();
		String json = service.queryBuyInfosByUserId(2);
		System.out.println(json);
	}

	// �鿴���ۼ�¼
	public void testQuerySellInfo() {
		UserService service = new UserService();
		String json = service.querySellInfosByUserId(2);
		System.out.println(json);
	}
}
