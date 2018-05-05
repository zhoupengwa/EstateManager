package cn.edu.xhu.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import cn.edu.xhu.consts.ResponseConsts;
import cn.edu.xhu.dao.HouseDao;
import cn.edu.xhu.dao.ImageDao;
import cn.edu.xhu.dao.TradeInfoDao;
import cn.edu.xhu.dao.UserDao;
import cn.edu.xhu.domain.House;
import cn.edu.xhu.domain.Image;
import cn.edu.xhu.domain.ResponseJson;
import cn.edu.xhu.domain.TradeInfo;
import cn.edu.xhu.domain.User;
import cn.edu.xhu.exception.HouseDaoOperatException;
import cn.edu.xhu.exception.ImageDaoException;
import cn.edu.xhu.exception.TradeInfoDaoException;
import cn.edu.xhu.exception.UserDaoOperatException;
import cn.edu.xhu.util.JdbcUtil;
import cn.edu.xhu.util.ThreadLocalDateUtil;

public class UserService {

	/**
	 * �û�ע��
	 * 
	 * @param user
	 * @throws UserDaoOperatException
	 */
	public String registerUser(User user) {
		ResponseJson<User> response = new ResponseJson<User>();
		if (user == null || user.getName() == null || user.getPassword() == null || user.getPhone() == null) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		UserDao dao = new UserDao();
		try {
			if (!dao.checkUserByPhone(user.getPhone())) {
				if (dao.addUser(user)) {
					response.setState("0");
					response.setMessage("ע��ɹ�");
					return new Gson().toJson(response);
				} else {
					response.setState(ResponseConsts.STATE_TWO);
					response.setMessage(ResponseConsts.MESSAGE_TWO);
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("�û��Ѵ���");
				return new Gson().toJson(response);
			}
		} catch (UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * �û���¼
	 * 
	 * @param phone
	 * @param password
	 * @throws UserDaoOperatException
	 */
	public String login(String phone, String password, HttpSession session) {
		ResponseJson<String> response = new ResponseJson<String>();
		if (phone == null || password == null) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		UserDao dao = new UserDao();
		try {
			if (dao.checkUser(phone, password)) {
				if (dao.checkUserPermission(phone)) {
					int userId = dao.queryUserByPhone(phone).getId();
					session.setAttribute("user_id", userId);
					response.setState("0");
					response.setMessage("��¼�ɹ�");
					// httpServletResponse.addCookie(new Cookie("token",
					// session.getId()));
					return new Gson().toJson(response);
				} else {
					response.setState("6");
					response.setMessage("�û�������");
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("�˺Ż��������");
				return new Gson().toJson(response);
			}
		} catch (Exception e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * �û�ע����¼
	 * 
	 * @param session
	 */
	public void logout(HttpSession session) {
		session.setAttribute("user_id", null);
	}

	/**
	 * �޸�����
	 * 
	 * @param password
	 * @param newPassword
	 * @param id
	 * @param session
	 * @return
	 */
	public String updatePassword(String password, String newPassword, int id, HttpSession session) {
		ResponseJson<String> response = new ResponseJson<String>();
		if (id == 0 || password == null || newPassword == null) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
		}
		UserDao dao = new UserDao();
		try {
			if (dao.updatePassword(password, newPassword, id)) {
				response.setState("0");
				response.setMessage("�޸ĳɹ�");
				session.removeAttribute("user_id");
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("�޸�ʧ��");
				return new Gson().toJson(response);
			}
		} catch (UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ��ѯ������Ϣ
	 * 
	 * @param phone
	 * @throws UserDaoOperatException
	 */
	public String lookInfo(int id) {
		ResponseJson<User> response = new ResponseJson<User>();
		if (id == 0) {
			response.setState("1");
			response.setMessage("�û�������");
			return new Gson().toJson(response);
		}
		UserDao dao = new UserDao();
		try {
			if (dao.checkUserById(id)) {
				List<User> userList = new ArrayList<User>();
				User user = dao.queryUserById(id);
				userList.add(user);
				response.setState("0");
				response.setMessage("��ѯ�ɹ�");
				response.setResultData(userList);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("�û�������!");
				return new Gson().toJson(response);
			}

		} catch (UserDaoOperatException e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}

	}

	/**
	 * �޸ĸ�����Ϣ
	 * 
	 * @param user
	 * @throws UserDaoOperatException
	 */
	public String updateInfo(User user) {
		ResponseJson<String> response = new ResponseJson<String>();
		if (user == null || user.getId() == 0 || user.getName() == null) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		UserDao dao = new UserDao();
		try {
			if (dao.checkUserById(user.getId())) {
				if (dao.updateUser(user)) {
					response.setState("0");
					response.setMessage("�޸ĸ�����Ϣ�ɹ�");
					return new Gson().toJson(response);
				} else {
					response.setState(ResponseConsts.STATE_TWO);
					response.setMessage(ResponseConsts.MESSAGE_TWO);
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("�û���Ϣ������");
				return new Gson().toJson(response);
			}
		} catch (UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ��ӷ�����Ϣ
	 * 
	 * @param house
	 * @param images
	 * @throws HouseDaoOperatException
	 */
	public String relaseHouse(House house) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (house == null || house.getUserid() == 0 || house.getName() == null || house.getKind() == null
				|| house.getVillage() == null || house.getAddress() == null || house.getContact() == null
				|| house.getPhone() == null || house.getPrice() == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		} else {
			UserDao dao = new UserDao();
			try {
				JdbcUtil.beginTransaction();
				if (dao.checkUserById(house.getUserid())) {
					HouseDao houseDao = new HouseDao();
					int houseId = houseDao.getMaxId() + 1;
					house.setId(houseId);
					house.setTime(ThreadLocalDateUtil.formatDate());
					if (houseDao.addHouse(house)) {
						List<House> houseList = new ArrayList<>();
						House newHouse = houseDao.queryHouseById(houseId);
						houseList.add(newHouse);
						response.setState("0");
						response.setMessage("������Ϣ��ӳɹ�");
						response.setResultData(houseList);
					} else {
						response.setState(ResponseConsts.STATE_TWO);
						response.setMessage(ResponseConsts.MESSAGE_TWO);
					}
				} else {
					response.setState("1");
					response.setMessage("�û������ڣ��޷����");
				}
				JdbcUtil.commitTransaction();
			} catch (Exception e) {
				try {
					JdbcUtil.cancelTransaction();
					response.setState(ResponseConsts.STATE_THREE);
					response.setMessage(ResponseConsts.MESSAGE_THREE);
				} catch (SQLException e1) {
					response.setState(ResponseConsts.STATE_THREE);
					response.setMessage(ResponseConsts.MESSAGE_THREE);
				}
			}

		}
		return new Gson().toJson(response);
	}

	/**
	 * �޸ķ�����Ϣ[id��userid��kind��area��traded��qualified��time�����޸�]
	 * 
	 * @param house
	 * @throws HouseDaoOperatException
	 */
	public String updateHouseInfo(House house) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (house == null || house.getId() == 0 || house.getName() == null || house.getVillage() == null
				|| house.getAddress() == null || house.getContact() == null || house.getPhone() == null
				|| house.getPrice() == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		HouseDao houseDao = new HouseDao();
		try {
			if (houseDao.checkHouseById(house.getId())) {
				if (houseDao.updateHouseById(house)) {
					response.setState("0");
					response.setMessage("������Ϣ�޸ĳɹ�");
					return new Gson().toJson(response);
				} else {
					response.setState(ResponseConsts.STATE_TWO);
					response.setMessage(ResponseConsts.MESSAGE_TWO);
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("������Ϣ������");
				return new Gson().toJson(response);
			}
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * �鿴�ѷ���������˺ϸ�ķ�����Ϣ
	 */
	public String listPrettyHouses() {
		HouseDao dao = new HouseDao();
		ResponseJson<House> response = new ResponseJson<House>();
		try {
			List<House> houses = dao.queryPrettyHouses();
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ͨ���۸����
	public String listPrettyHousesByPrice(int startPrice, int endPrice) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (startPrice == 0 || endPrice == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryPrettyHousesByPrice(startPrice, endPrice);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ͨ���������
	public String listPrettyHousesBySize(int startSize, int endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (startSize == 0 || endSize == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHousesBySize(startSize, endSize);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ͨ�����Ͳ���
	public String listPrettyHousesByType(String type) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (type == null) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByType(type);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ͨ�����Ͳ���
	public String listPrettyHousesByKind(String kind) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (kind == null) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByKind(kind);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ���ύ������ѯ�����ͣ����ͣ��۸�[��->��]�����[��->��]���۳�״̬��Ĭ�����򣬰�����ʱ���������µ�����ǰ�棩
	public String queryHouseByParameterAndOrderByDefault(String type, String kind, String startPrice, String endPrice,
			String startSize, String endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			System.out.println(type);
			System.out.println(kind);
			System.out.println(startPrice + "  -  " + endPrice);
			System.out.println(startSize + "  -  " + endSize);
			List<House> houses = dao.queryHouseByParameterOrderByDefault(type, kind, startPrice, endPrice, startSize,
					endSize);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ���ύ������ѯ�����ͣ����ͣ��۸�[��->��]�����[��->��]���۳�״̬���۸�����
	public String queryHouseByParameterAndOrderByPriceUp(String type, String kind, String startSize, String endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByParameterOrderByPriceUp(type, kind, startSize, endSize);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ���ύ������ѯ�����ͣ����ͣ��۸�[��->��]�����[��->��]���۳�״̬���۸���
	public String queryHouseByParameterAndOrderByPriceDown(String type, String kind, String startSize, String endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByParameterOrderByPriceDown(type, kind, startSize, endSize);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ���ύ������ѯ�����ͣ����ͣ��۸�[��->��]�����[��->��]���۳�״̬���������
	public String queryHouseByParameterAndOrderBySizeUp(String type, String kind, String startSize, String endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByParameterOrderBySizeUp(type, kind, startSize, endSize);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ���ύ������ѯ�����ͣ����ͣ��۸�[��->��]�����[��->��]���۳�״̬���������
	public String queryHouseByParameterAndOrderBySizeDown(String type, String kind, String startSize, String endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByParameterOrderBySizeDown(type, kind, startSize, endSize);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ��������
	public String searchHouses(String parameter) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.searchHouse(parameter);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// ��ѯǰ���䷿��
	public String queryThreeImages() {
		ResponseJson<Image> response = new ResponseJson<Image>();
		HouseDao dao = new HouseDao();
		try {
			List<Image> images = dao.queryThreeImages();
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(images);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}

	}

	/**
	 * ����ID���ҷ�����Ϣ
	 * 
	 * @param houseId
	 * @return
	 */
	public String queryHouseById(String houseId) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (houseId == null) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		HouseDao dao = new HouseDao();
		House hosue;
		try {
			if (dao.checkHouseById(Integer.parseInt(houseId))) {
				hosue = dao.queryHouseById(Integer.parseInt(houseId));
				List<House> houses = new ArrayList<>();
				houses.add(hosue);
				response.setState(ResponseConsts.STATE_ZERO);
				response.setState("��ѯ�ɹ�");
				response.setResultData(houses);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setState("���ݲ�����");
				return new Gson().toJson(response);
			}

		} catch (Exception e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}

	}

	/**
	 * �������
	 * 
	 * @param orderParameter
	 * @param desc
	 * @return
	 */
	public String listPrettyHouseOrderByParameter(String orderParameter, boolean desc) {
		HouseDao dao = new HouseDao();
		ResponseJson<House> response = new ResponseJson<House>();
		try {
			List<House> houses = dao.queryPrettyHousesByOrderParameter(orderParameter, desc);
			response.setState("0");
			response.setMessage("��ѯ�ɹ�");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * �г��Լ������ķ�����Ϣ
	 * 
	 * @return
	 * 
	 */
	public String listHousesByUserId(int userId) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (userId == 0) {
			response.setState("1");
			response.setMessage("�û�������");
			return new Gson().toJson(response);
		}
		UserDao userDao = new UserDao();
		try {
			if (userDao.checkUserById(userId)) {
				HouseDao houseDao = new HouseDao();
				List<House> houses = houseDao.queryHousesByUserId(userId);
				response.setState("0");
				response.setMessage("��ѯ�ɹ�");
				response.setResultData(houses);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("�û�������");
				return new Gson().toJson(response);
			}

		} catch (HouseDaoOperatException | UserDaoOperatException e) {
			// e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ����id��ѯ������Ϣ
	 * 
	 * @param id
	 * @return
	 */
	public String lookHouseInfo(int id) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		if (id == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		try {
			if (dao.checkHouseById(id)) {
				House house = dao.queryHouseById(id);
				List<House> houseList = new ArrayList<>();
				houseList.add(house);
				response.setState("0");
				response.setMessage("��ѯ�ɹ�");
				response.setResultData(houseList);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("������Ϣ������");
				return new Gson().toJson(response);
			}
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ͼƬ�ϴ�
	 * 
	 * @param houseId
	 * @param image
	 * @throws ImageDaoException
	 */
	public String uploadImage(int houseId, List<Image> images) {
		ResponseJson<Image> response = new ResponseJson<Image>();
		List<Image> resultList = new ArrayList<Image>();
		if (images == null || houseId == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		try {
			JdbcUtil.beginTransaction();
			HouseDao houseDao = new HouseDao();
			houseDao.addHouseImage(houseId, images.get(0).getPath());
			ImageDao imageDao = new ImageDao();
			for (Image image : images) {
				if (imageDao.addImage(image)) {
					resultList.add(image);
				}
			}
			response.setState("0");
			String message = "�ϴ��ɹ�";
			response.setMessage(message);
			response.setResultData(resultList);
			JdbcUtil.commitTransaction();
		} catch (Exception e) {
			try {
				JdbcUtil.cancelTransaction();
			} catch (SQLException e1) {
				response.setState(ResponseConsts.STATE_THREE);
				response.setMessage(ResponseConsts.MESSAGE_THREE);
			}
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
		}
		return new Gson().toJson(response);
	}

	/**
	 * ����houseId��ѯͼƬ
	 * 
	 * @param houseId
	 * @return
	 */
	public String queryImagesByHouseId(int houseId) {
		ResponseJson<Image> response = new ResponseJson<Image>();
		if (houseId == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		ImageDao dao = new ImageDao();
		try {
			List<Image> images = dao.queryImagesByHouseId(houseId);
			response.setState("0");
			response.setMessage("��ȡ�ɹ�");
			response.setResultData(images);
			return new Gson().toJson(response);
		} catch (ImageDaoException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ����ͼƬidɾ��
	 * 
	 * @param id
	 * @return
	 */
	public String deleteImage(int id, int houseId, int userId) {
		ResponseJson<String> response = new ResponseJson<String>();
		if (id == 0 || houseId == 0 || userId == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		ImageDao imageDao = new ImageDao();
		HouseDao houseDao = new HouseDao();
		try {
			if (houseDao.checkHouseByUserIdAndHouseId(houseId, userId)) {
				if (imageDao.checkImageByIdAndHouseId(id, houseId) && houseDao.resetHouseImage(houseId)) {
					if (imageDao.deleteImageById(id)) {
						response.setState("0");
						response.setMessage("ɾ���ɹ�");
						return new Gson().toJson(response);
					} else {
						response.setState(ResponseConsts.STATE_TWO);
						response.setMessage(ResponseConsts.MESSAGE_TWO);
						return new Gson().toJson(response);
					}
				} else {
					response.setState("1");
					response.setMessage("ͼƬ������");
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("ͼƬ������");
				return new Gson().toJson(response);
			}

		} catch (ImageDaoException | HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * �¼ܷ���
	 * 
	 * @param id
	 * @return
	 */
	public String deleteHouse(int id, int userId) {
		ResponseJson<String> response = new ResponseJson<String>();
		if (id == 0 || userId == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		} else {
			HouseDao houseDao = new HouseDao();
			try {
				JdbcUtil.beginTransaction();
				if (houseDao.checkHouseByIdAndUserId(id, userId)) {
					ImageDao imageDao = new ImageDao();
					if (imageDao.checkImagesByHouseId(id)) {
						imageDao.deleteImageByHouseId(id);
					}
					if (houseDao.deleteHouseById(id)) {
						response.setState("0");
						response.setMessage("ɾ���ɹ�");
					}
				} else {
					response.setState("1");
					response.setMessage("���ݲ�����");
				}
				JdbcUtil.commitTransaction();
			} catch (Exception e) {
				try {
					JdbcUtil.cancelTransaction();
					response.setState(ResponseConsts.STATE_THREE);
					response.setMessage(ResponseConsts.MESSAGE_THREE);
				} catch (SQLException e1) {
					response.setState(ResponseConsts.STATE_THREE);
					response.setMessage(ResponseConsts.MESSAGE_THREE);
				}
			}
		}
		return new Gson().toJson(response);
	}

	/**
	 * ������[����/�ⷿ]
	 * 
	 * @param houseId
	 * @param buyerId
	 * @param sellerId
	 * @return
	 */
	public String requestDeal(int houseId, int buyerId) {
		ResponseJson<String> response = new ResponseJson<String>();
		HouseDao houseDao = new HouseDao();
		try {
			JdbcUtil.beginTransaction();
			if (houseDao.checkHouseById(houseId)) {
				int sellerId = houseDao.queryHouseById(houseId).getUserid();
				if (sellerId == buyerId) {
					response.setState("6");
					response.setMessage("���ܹ����Լ����ϼ�");
				} else {
					if (houseDao.checkPrettyHouseById(houseId, sellerId)) {
						TradeInfoDao tradeInfoDao = new TradeInfoDao();
						if (tradeInfoDao.addTradeInfo(houseId, sellerId, buyerId, ThreadLocalDateUtil.formatDate())
								&& houseDao.changeHouseTradeState(houseId)) {
							response.setState("0");
							response.setMessage("���׳ɹ���");
						} else {
							response.setState(ResponseConsts.STATE_TWO);
							response.setMessage(ResponseConsts.MESSAGE_TWO);
						}
					} else {
						response.setState("1");
						response.setMessage("������Ϣ������");
					}
				}
			} else {
				response.setState("1");
				response.setMessage("������Ϣ������");
			}
			JdbcUtil.commitTransaction();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JdbcUtil.cancelTransaction();
			} catch (SQLException e1) {
				response.setState(ResponseConsts.STATE_THREE);
				response.setMessage(ResponseConsts.MESSAGE_THREE);
			}
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
		}
		return new Gson().toJson(response);
	}
	//
	// /**
	// * ��ѯ���˵Ľ��׼�¼[�����������������]
	// *
	// * @param userId
	// * @return
	// */
	// public String queryTradeInfosByUserId(int userId) {
	// ResponseJson<TradeInfo> response = new ResponseJson<TradeInfo>();
	// if (userId == 0) {
	// response.setState(ResponseConsts.STATE_TWO);
	// response.setMessage(ResponseConsts.MESSAGE_TWO);
	// return new Gson().toJson(response);
	// }
	// TradeInfoDao tradeInfoDao = new TradeInfoDao();
	// UserDao userDao = new UserDao();
	// try {
	// if (userDao.checkUserById(userId)) {
	// List<TradeInfo> tradeInfos =
	// tradeInfoDao.queryTradeInfosByUserId(userId);
	// response.setState("0");
	// response.setMessage("��ѯ�ɹ�");
	// response.setResultData(tradeInfos);
	// return new Gson().toJson(response);
	// } else {
	// response.setState("1");
	// response.setMessage("�û�������");
	// return new Gson().toJson(response);
	// }
	//
	// } catch (TradeInfoDaoException | UserDaoOperatException e) {
	// response.setState(ResponseConsts.STATE_THREE);
	// response.setMessage(ResponseConsts.MESSAGE_THREE);
	// return new Gson().toJson(response);
	// }
	// }

	/**
	 * ��ѯ�Լ��Ĺ����¼
	 * 
	 * @param userId
	 * @return
	 */
	public String queryBuyInfosByUserId(int userId) {
		ResponseJson<TradeInfo> response = new ResponseJson<TradeInfo>();
		if (userId == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		TradeInfoDao dao = new TradeInfoDao();
		try {
			if (new UserDao().checkUserById(userId)) {
				List<TradeInfo> tradeInfos = dao.queryBuyInfosByUserId(userId);
				for (TradeInfo tradeInfo : tradeInfos) {
					User buyer = new UserDao().queryUserById(userId);
					User seller = new UserDao().queryUserById(tradeInfo.getSellerid());
					House house = new HouseDao().queryHouseById(tradeInfo.getHouseid());
					tradeInfo.setBuyer(buyer);
					tradeInfo.setSeller(seller);
					tradeInfo.setHouse(house);
				}
				response.setState("0");
				response.setMessage("��ѯ�ɹ�");
				response.setResultData(tradeInfos);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("�û�������");
				return new Gson().toJson(response);
			}

		} catch (TradeInfoDaoException | UserDaoOperatException | HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ��ѯ�Լ����۳���¼
	 * 
	 * @param userId
	 * @return
	 */
	public String querySellInfosByUserId(int userId) {
		ResponseJson<TradeInfo> response = new ResponseJson<TradeInfo>();
		if (userId == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		TradeInfoDao dao = new TradeInfoDao();
		try {
			if (new UserDao().checkUserById(userId)) {
				List<TradeInfo> tradeInfos = dao.querySellInfosByUserId(userId);
				for (TradeInfo tradeInfo : tradeInfos) {
					User buyer = new UserDao().queryUserById(tradeInfo.getBuyerid());
					User seller = new UserDao().queryUserById(userId);
					House house = new HouseDao().queryHouseById(tradeInfo.getHouseid());
					tradeInfo.setBuyer(buyer);
					tradeInfo.setSeller(seller);
					tradeInfo.setHouse(house);
				}
				response.setState("0");
				response.setMessage("��ѯ�ɹ�");
				response.setResultData(tradeInfos);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("�û�������");
				return new Gson().toJson(response);
			}

		} catch (TradeInfoDaoException | UserDaoOperatException | HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}
}
