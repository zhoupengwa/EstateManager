package cn.edu.xhu.dao;

import java.util.List;

import cn.edu.xhu.domain.User;
import cn.edu.xhu.exception.UserDaoOperatException;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class UserDao extends BaseDao {
	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 * @throws UserDaoOperatException
	 */
	public boolean addUser(User user) throws UserDaoOperatException {
		String sql = "insert into tb_user values (null,?,?,?,?,?,?,?,?)";
		try {
			Object[] params = { user.getName(), user.getSex(), user.getPassword(), user.getPhone(), user.getAddress(),
					true, ThreadLocalDateUtil.formatDate(), ThreadLocalDateUtil.formatDate() };
			int result = super.update(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new UserDaoOperatException("用户添加失败!");
		}
		return false;
	}

	/**
	 * 禁用用户
	 * 
	 * @param user
	 * @return
	 * @throws UserDeleteFailedException
	 */
	public boolean forbidUserById(int id) throws UserDaoOperatException {
		String sql = "update tb_user set user_state=0, gmt_motified=? where id=?";
		try {
			Object[] params = { ThreadLocalDateUtil.formatDate(), id };
			int result = super.update(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new UserDaoOperatException("用户禁用失败!");
		}
		return false;
	}

	/**
	 * 修改密码
	 * 
	 * @param password
	 * @param newPassword
	 * @param id
	 * @return
	 * @throws UserDaoOperatException
	 */
	public boolean updatePassword(String password, String newPassword, int id) throws UserDaoOperatException {
		String sql = "update tb_user set user_password=?, gmt_motified=? where user_password=? and id=?";
		Object[] params = { newPassword, ThreadLocalDateUtil.formatDate(), password, id };
		try {
			int count = super.update(sql, params);
			if (count == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new UserDaoOperatException("用户修改密码失败!");
		}
		return false;

	}

	/**
	 * 修改用户信息
	 * 
	 * @param user
	 * @return
	 * @throws UserDaoOperatException
	 */
	public boolean updateUser(User user) throws UserDaoOperatException {
		String sql = "update tb_user set user_name=?,user_sex=?,user_address=?,gmt_motified=? where id=?";
		try {
			Object[] params = { user.getName(), user.getSex(), user.getAddress(), ThreadLocalDateUtil.formatDate(),
					user.getId() };
			int result = super.update(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new UserDaoOperatException("更新用户信息失败!");
		}
		return false;
	}

	/**
	 * 根据电话查找一个用户
	 * 
	 * @param phone
	 * @return
	 * @throws UserDaoOperatException
	 */
	public User queryUserByPhone(String phone) throws UserDaoOperatException {
		User user = null;
		String sql = "select id,user_name,user_sex,user_password,user_phone,user_address,user_state from tb_user where user_phone=?";
		try {
			Object[] params = { phone };
			user = super.query(sql, params, User.class).get(0);
		} catch (Exception e) {
			throw new UserDaoOperatException("用户查询失败!");
		}
		return user == null ? null : user;
	}

	/**
	 * 根据电话检测用户是否存在
	 * 
	 * @param phone
	 * @return
	 * @throws UserDaoOperatException
	 */
	public boolean checkUserByPhone(String phone) throws UserDaoOperatException {
		String sql = "select count(*) as count from tb_user where user_phone=?";
		try {
			Object[] params = { phone };
			int result = super.count(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new UserDaoOperatException("通过电话查找用户失败!");
		}
		return false;
	}

	/**
	 * 根据id查找用户
	 * 
	 * @param phone
	 * @return
	 * @throws UserDaoOperatException
	 */
	public User queryUserById(int id) throws UserDaoOperatException {
		User user = null;
		String sql = "select id,user_name,user_sex,user_password,user_phone,user_address,user_state from tb_user where id=?";
		try {
			Object[] params = { id };
			user = super.query(sql, params, User.class).get(0);
		} catch (Exception e) {
			//e.printStackTrace();
			throw new UserDaoOperatException("用户查询失败!");
		}
		return user == null ? null : user;
	}

	public boolean checkUserPermission(String phone) throws UserDaoOperatException {
		String sql = "select count(*) as count from tb_user where user_phone=? and user_state=?";
		try {
			Object[] params = { phone, true };
			int result = super.count(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new UserDaoOperatException("查找用户失败!");
		}
		return false;
	}

	/**
	 * 根据电话、密码检测用户是否存在
	 * 
	 * @param phone
	 * @param password
	 * @return
	 * @throws UserDaoOperatException
	 */
	public boolean checkUser(String phone, String password) throws UserDaoOperatException {
		String sql = "select count(*) as count from tb_user where user_phone=? and user_password=?";
		try {
			Object[] params = { phone, password };
			int result = super.count(sql, params);
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			throw new UserDaoOperatException("查找用户失败!");
		}
		return false;
	}

	/**
	 * 查询所有用户
	 * 
	 * @return
	 * @throws UserQueryException
	 */
	public List<User> queryUsers() throws UserDaoOperatException {
		List<User> users = null;
		String sql = "select id,user_name,user_sex,user_password,user_phone,user_address,user_state from tb_user";
		try {
			users = super.query(sql, null, User.class);
		} catch (Exception e) {
			throw new UserDaoOperatException("查询所有用户失败！");
		}
		return users == null ? null : users;
	}

	public boolean checkUserById(int id) throws UserDaoOperatException {
		String sql = "select count(*) as count from tb_user where id=?";
		try {
			int result = super.count(sql, new Object[] { id });
			if (result == 1) {
				return true;
			}
		} catch (Exception e) {
			//e.printStackTrace();
			throw new UserDaoOperatException("根据Id查询用户失败");
		}
		return false;
	}
}
