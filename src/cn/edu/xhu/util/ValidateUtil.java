package cn.edu.xhu.util;

import java.util.HashMap;
import java.util.Map;

public class ValidateUtil {
	/**
	 * ͨ��������ʽ���и�ʽ��֤���ߣ����ذ����������ݵ�Map,������ֵΪ�գ�����֤ͨ��
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
				if (value.matches("\\d*")) {// ���벻��ֻΪ����
					errorMap.put(key, value);
				}
				break;
			default:
			}
		}
		return errorMap;
	}
}
