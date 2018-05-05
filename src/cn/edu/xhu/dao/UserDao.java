package cn.edu.xhu.dao;

import java.util.List;

import cn.edu.xhu.domain.User;
import cn.edu.xhu.exception.UserDaoOperatException;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class UserDao extends BaseDao {
	/**
	 * ����û�
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
			throw new UserDaoOperatException("�û����ʧ��!");
		}
		return false;
	}

	/**
	 * �����û�
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
			throw new UserDaoOperatException("�û�����ʧ��!");
		}
		return false;
	}

	/**
	 * �޸�����
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
			throw new UserDaoOperatException("�û��޸�����ʧ��!");
		}
		return false;

	}

	/**
	 * �޸��û���Ϣ
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
			throw new UserDaoOperatException("�����û���Ϣʧ��!");
		}
		return false;
	}

	/**
	 * ���ݵ绰����һ���û�
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
			throw new UserDaoOperatException("�û���ѯʧ��!");
		}
		return user == null ? null : user;
	}

	/**
	 * ���ݵ绰����û��Ƿ����
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
			throw new UserDaoOperatException("ͨ���绰�����û�ʧ��!");
		}
		return false;
	}

	/**
	 * ����id�����û�
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
			throw new UserDaoOperatException("�û���ѯʧ��!");
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
			throw new UserDaoOperatException("�����û�ʧ��!");
		}
		return false;
	}

	/**
	 * ���ݵ绰���������û��Ƿ����
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
			throw new UserDaoOperatException("�����û�ʧ��!");
		}
		return false;
	}

	/**
	 * ��ѯ�����û�
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
			throw new UserDaoOperatException("��ѯ�����û�ʧ�ܣ�");
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
			throw new UserDaoOperatException("����Id��ѯ�û�ʧ��");
		}
		return false;
	}
}
