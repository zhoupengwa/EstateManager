package cn.edu.xhu.dao;

import cn.edu.xhu.domain.Admin;
import cn.edu.xhu.exception.AdminDaoOperatException;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class AdminDao extends BaseDao {
	/**
	 * 根据姓名，密码检查管理员是否存在
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @throws AdminDaoOperatException
	 */
	public boolean checkAdmin(String name, String password) throws AdminDaoOperatException {
		String sql = "select count(*) as count from tb_admin where admin_name=? and admin_password=?";
		try {
			Object[] params = { name, password };
			int count = super.count(sql, params);
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new AdminDaoOperatException("查找管理员失败!");
		}
		return false;
	}

	/**
	 * 添加管理员
	 * 
	 * @param admin
	 * @return
	 * @throws AdminDaoOperatException
	 */
	public boolean addAdmin(Admin admin) throws AdminDaoOperatException {
		String sql = "insert into tb_admin values (null,?,?,?,?)";
		Object[] params = { admin.getName(), admin.getPassword(), ThreadLocalDateUtil.formatDate(),
				ThreadLocalDateUtil.formatDate() };
		try {
			int result = super.update(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new AdminDaoOperatException("管理员添加失败!");
		}
		return false;
	}
}
