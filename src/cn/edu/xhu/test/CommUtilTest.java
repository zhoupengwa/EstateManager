package cn.edu.xhu.test;

import java.util.HashMap;
import java.util.Map;

import org.apache.tomcat.util.codec.DecoderException;
import org.apache.tomcat.util.codec.EncoderException;
import org.junit.Test;

import cn.edu.xhu.domain.User;
import cn.edu.xhu.util.CommUtils;

public class CommUtilTest {

	public void test1() {
		Map<String, String> map = new HashMap<>();
		map.put("name", "÷‹≈Ù");
		map.put("password", "20");
		try {
			User user = CommUtils.convertMapToBean(null, User.class);
			System.out.println(user.getName());
			System.out.println(user.getPassword());
			System.out.println(user.getPhone());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void test2() {
		System.out.println(CommUtils.changeName("seller_id"));
	}

	public void test3() {
		System.out.println(CommUtils.getUuidToken());

	}

	public void test4() {

		try {
			System.out.println(CommUtils.encodeToBase64(1 + ""));
		} catch (EncoderException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test5() {
		try {
			System.out.println(CommUtils.decodeFromBase64("MQ=="));
		} catch (DecoderException e) {
			e.printStackTrace();
		}
	}
}
