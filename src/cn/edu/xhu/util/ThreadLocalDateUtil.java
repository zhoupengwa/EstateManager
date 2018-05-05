package cn.edu.xhu.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.edu.xhu.consts.ThreadLocalConsts;

/**
 * 
 * @author
 *
 */
public class ThreadLocalDateUtil {

	/**
	 * 得到线程安全的日期时间
	 * 
	 * @return
	 */
	public static DateFormat getDateFormat() {
		DateFormat df = ThreadLocalConsts.DATE_THREADLOCAL.get();
		if (df == null) {
			df = new SimpleDateFormat(ThreadLocalConsts.DATE_FORMAT);
			ThreadLocalConsts.DATE_THREADLOCAL.set(df);
		}
		return df;
	}

	public static String formatDate() {
		Date date = new Date();
		return getDateFormat().format(date);
	}

	public static Date parse(String strDate) throws ParseException {
		return getDateFormat().parse(strDate);
	}
}
