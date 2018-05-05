package cn.edu.xhu.consts;

import java.sql.Connection;
import java.text.DateFormat;

public class ThreadLocalConsts {
	public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final ThreadLocal<DateFormat> DATE_THREADLOCAL = new ThreadLocal<DateFormat>();
	public static final ThreadLocal<Connection> CONNECTION_THREADLOCAL = new ThreadLocal<Connection>();
}
