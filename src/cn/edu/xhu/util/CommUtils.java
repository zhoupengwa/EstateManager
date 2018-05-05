package cn.edu.xhu.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.tomcat.util.codec.DecoderException;
import org.apache.tomcat.util.codec.EncoderException;
import org.apache.tomcat.util.codec.binary.Base64;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CommUtils {

	/**
	 * 将Map的值设置到相应对象的属性中，并返回该对象，注意，只支持8中基本数据类型，要求bean中不存在等Date型
	 * 
	 * @param map
	 * @param clazz
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static <K, V, T> T convertMapToBean(Map<K, V> map, Class<T> clazz)
			throws InvocationTargetException, IllegalAccessException, InstantiationException {
		T object = clazz.newInstance();
		Set<Map.Entry<K, V>> entrySet = map.entrySet();
		for (Map.Entry<K, V> entry : entrySet) {
			K key = entry.getKey();
			@SuppressWarnings("unchecked")
			V[] v = (V[]) entry.getValue();
			for (V value : v) {
				BeanUtils.setProperty(object, String.valueOf(key), value);// 使用Apache的BeanUtils设置属性
			}
		}
		return object;
	}

	/**
	 * 加密
	 * 
	 * @param content
	 * @return
	 * @throws EncoderException
	 */
	public static String encodeToBase64(String content) throws EncoderException {
		String base64 = new String(new Base64().encode(content.getBytes()));
		return base64 == null ? null : base64;

	}

	/**
	 * 解密
	 * 
	 * @param base64
	 * @return
	 * @throws DecoderException
	 */
	public static String decodeFromBase64(String base64) throws DecoderException {
		String content = new String(new Base64().decode(base64.getBytes()));
		return content == null ? null : content;
	}

	/**
	 * 得到UUID唯一标识
	 * 
	 * @return
	 */
	public static String getUuidToken() {
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}

	/**
	 * 将数据库中的列名的名字与改变成为javaBean中的属性名字
	 * 
	 * @param name
	 * @return
	 */
	public static String changeName(String name) {
		String result = name;
		if (name.equals("house_id")) {
			result = "houseid";
		} else if (name.startsWith("ho") || name.startsWith("im") || name.startsWith("us") || name.equals("ad")) {
			result = name.substring(name.indexOf("_") + 1, name.length());
		} else {
			switch (name) {
			case "seller_id":
				result = "sellerid";
				break;
			case "buyer_id":
				result = "buyerid";
				break;
			case "trade_time":
				result = "time";
				break;
			default:
			}
		}
		return result;
	}

}
