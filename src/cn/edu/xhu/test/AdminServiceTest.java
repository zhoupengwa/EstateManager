package cn.edu.xhu.test;

import org.junit.Test;

import cn.edu.xhu.domain.Admin;
import cn.edu.xhu.exception.AdminDaoOperatException;
import cn.edu.xhu.service.AdminService;

public class AdminServiceTest {
	/**
	 * ��ӹ���Ա���ݲ����ţ�
	 */
	public void testAdd() {
		Admin admin = new Admin();
		admin.setName("����");
		admin.setPassword("123456789");
		try {
			new AdminService().add(admin);
		} catch (AdminDaoOperatException e) {
			e.printStackTrace();
		}
	}

	// ��¼
	public void login() {
		// String name = "����";
		// String password = "123456789";
		// String json = new AdminService().login(name, password);
		// System.out.println(json);
	}

	// �г������û�
	public void listUsers() {
		String json = new AdminService().listUsers();
		System.out.println(json);
	}

	// �鿴������Ϣ
	public void lookInfo() {
		// String json = new AdminService().lookInfo("18483638748");
		// System.out.println(json);
	}

	// �����û�
	public void forbidUser() {
		String json = new AdminService().forbidUserById(3);
		System.out.println(json);
	}

	// ��˷���
	public void testPermitHouse() {
		String json = new AdminService().permitHouse(4);
		System.out.println(json);
	}

	// �г����з���
	public void listListHouseInfo() {
		String json = new AdminService().listHouseByUserId(1);
		System.out.println(json);
	}

	@Test
	// �г����н�����Ϣ
	public void listTradeInfo() {
		String json = new AdminService().queryTradeInfosByUserId(1);
		System.out.println(json);
	}

}
