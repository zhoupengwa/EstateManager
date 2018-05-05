package cn.edu.xhu.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import cn.edu.xhu.util.CommUtils;
import cn.edu.xhu.util.JdbcUtil;

/**
 * Dao��ͨ�ò�������ȡ���ӣ�����Ԥ������䣬ִ��sql(��ѯ�����)
 * 
 * @author
 *
 */
public class BaseDao {
	/**
	 * ���²�������������ɾ����
	 * 
	 * @param sql
	 * @param params
	 * @returnӰ�������
	 */
	public int update(String sql, Object[] params) throws Exception {
		PreparedStatement pstm = null;
		Connection conn = JdbcUtil.getConnection();
		int count = 0;
		pstm = JdbcUtil.getPerpareStatement(conn, sql);
		if (pstm != null) {
			JdbcUtil.setParams(pstm, params);
		}
		count = pstm.executeUpdate();
		JdbcUtil.close(pstm, conn, null);
		return count;
	}

	/**
	 * ��ѯ
	 * 
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws Exception
	 */
	public <T> List<T> query(String sql, Object[] params, Class<T> clazz) throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		T object = null;
		Connection conn = JdbcUtil.getConnection();
		List<T> list = new ArrayList<T>();
		pstm = JdbcUtil.getPerpareStatement(conn, sql);
		if (pstm != null) {
			JdbcUtil.setParams(pstm, params);
		}
		rs = pstm.executeQuery();
		ResultSetMetaData rsMeta = rs.getMetaData();
		int columnCount = rsMeta.getColumnCount();
		while (rs.next()) {
			object = clazz.newInstance();
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsMeta.getColumnName(i);
				Object columnValue = rs.getObject(i);
				// System.out.println(CommUtils.changeName(columnName) + " " +
				// columnValue);
				BeanUtils.setProperty(object, CommUtils.changeName(columnName), columnValue);
			}
			list.add(object);
		}

		JdbcUtil.close(pstm, conn, rs);

		return list;
	}

	/**
	 * ����ͳ��
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int count(String sql, Object[] params) throws Exception {
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Connection conn = JdbcUtil.getConnection();
		pstm = JdbcUtil.getPerpareStatement(conn, sql);
		if (pstm != null) {
			JdbcUtil.setParams(pstm, params);
			rs = pstm.executeQuery();
			rs.next();
			int count = rs.getInt("count");
			return count;
		}
		JdbcUtil.close(pstm, conn, rs);
		return 0;
	}
}
