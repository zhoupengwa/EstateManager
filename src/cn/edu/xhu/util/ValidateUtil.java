package cn.edu.xhu.util;

import java.util.HashMap;
import java.util.Map;

public class ValidateUtil {
	/**
	 * 通过正则表达式进行格式验证工具，返回包含错误数据的Map,若返回值为空，则验证通过
	 * 
	 * @param map
	 * @param keys
	 * @return
	 */
	public static Map<String, String> validate(Map<String, String> map, String[] keys) {
		Map<String, String> errorMap = new HashMap<String, String>();
		for (String key : keys) {
			String value = map.get(key);
			switch (key) {
			case "username":
				if (value.equals("username"))
					errorMap.put(key, value);
				break;
			case "password":
				if (value.matches("\\d*")) {// 密码不能只为数字
					errorMap.put(key, value);
				}
				break;
			default:
			}
		}
		return errorMap;
	}
}
