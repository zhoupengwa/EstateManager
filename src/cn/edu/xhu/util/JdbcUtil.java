package cn.edu.xhu.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.edu.xhu.consts.JdbcConsts;
import cn.edu.xhu.consts.ThreadLocalConsts;

/**
 * 连接数据库，提供获取连接对象
 * 
 * @author
 *
 */
public class JdbcUtil {

	/**
	 * 获取连接
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection conn = null;
		if (ThreadLocalConsts.CONNECTION_THREADLOCAL.get() != null) {
			conn = ThreadLocalConsts.CONNECTION_THREADLOCAL.get();
		} else {
			Class.forName(JdbcConsts.DRIVER);
			conn = DriverManager.getConnection(JdbcConsts.URL, JdbcConsts.USERNAME, JdbcConsts.PASSWORD);
		}
		return conn == null ? null : conn;
	}

	/**
	 * 获取预处理语句对象
	 * 
	 * @param sql
	 * @return
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static PreparedStatement getPerpareStatement(Connection conn, String sql)
			throws ClassNotFoundException, SQLException {
		PreparedStatement pstm = null;
		if (conn != null && sql != null) {
			pstm = conn.prepareStatement(sql);
		}
		return pstm == null ? null : pstm;
	}

	/**
	 * 给预处理语句设置参数
	 * 
	 * @param pstm
	 * @param params
	 * @throws SQLException
	 */
	public static void setParams(PreparedStatement pstm, Object[] params) throws SQLException {
		if (params != null && params.length > 0) {
			for (int i = 1; i <= params.length; i++) {
				pstm.setObject(i, params[i - 1]);
			}
		}
	}

	/**
	 * 开启事物
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void beginTransaction() throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		ThreadLocalConsts.CONNECTION_THREADLOCAL.set(conn);
		conn.setAutoCommit(false);
	}

	/**
	 * 回滚
	 * 
	 * @throws SQLException
	 */
	public static void cancelTransaction() throws SQLException {
		Connection conn = ThreadLocalConsts.CONNECTION_THREADLOCAL.get();
		if (conn != null) {
			conn.rollback();
			conn.close();
			ThreadLocalConsts.CONNECTION_THREADLOCAL.remove();
		}
	}

	/**
	 * 关闭事物
	 * 
	 * @throws SQLException
	 */
	public static void commitTransaction() throws SQLException {
		Connection conn = ThreadLocalConsts.CONNECTION_THREADLOCAL.get();
		if (conn != null) {
			conn.commit();
			conn.close();
			ThreadLocalConsts.CONNECTION_THREADLOCAL.remove();
		}

	}

	/**
	 * 关闭预处理对象、连接对象、结果集对象
	 * 
	 * @param pstm
	 * @param conn
	 * @param rs
	 * @throws SQLException
	 */
	public static void close(PreparedStatement pstm, Connection conn, ResultSet rs) throws SQLException {
		if (rs != null) {
			rs.close();
		}
		if (pstm != null) {
			pstm.close();
		}
		if (ThreadLocalConsts.CONNECTION_THREADLOCAL.get() == null) {
			if (conn != null) {
				conn.close();
			}
		}

	}
}
