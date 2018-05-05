package cn.edu.xhu.test;

import org.junit.Test;

import cn.edu.xhu.domain.Admin;
import cn.edu.xhu.exception.AdminDaoOperatException;
import cn.edu.xhu.service.AdminService;

public class AdminServiceTest {
	/**
	 * 添加管理员（暂不开放）
	 */
	public void testAdd() {
		Admin admin = new Admin();
		admin.setName("李婷");
		admin.setPassword("123456789");
		try {
			new AdminService().add(admin);
		} catch (AdminDaoOperatException e) {
			e.printStackTrace();
		}
	}

	// 登录
	public void login() {
		// String name = "李婷";
		// String password = "123456789";
		// String json = new AdminService().login(name, password);
		// System.out.println(json);
	}

	// 列出所有用户
	public void listUsers() {
		String json = new AdminService().listUsers();
		System.out.println(json);
	}

	// 查看个人信息
	public void lookInfo() {
		// String json = new AdminService().lookInfo("18483638748");
		// System.out.println(json);
	}

	// 禁用用户
	public void forbidUser() {
		String json = new AdminService().forbidUserById(3);
		System.out.println(json);
	}

	// 审核房屋
	public void testPermitHouse() {
		String json = new AdminService().permitHouse(4);
		System.out.println(json);
	}

	// 列出所有房屋
	public void listListHouseInfo() {
		String json = new AdminService().listHouseByUserId(1);
		System.out.println(json);
	}

	@Test
	// 列出所有交易信息
	public void listTradeInfo() {
		String json = new AdminService().queryTradeInfosByUserId(1);
		System.out.println(json);
	}

}
