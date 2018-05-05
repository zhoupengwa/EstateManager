package cn.edu.xhu.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import cn.edu.xhu.consts.ResponseConsts;
import cn.edu.xhu.dao.AdminDao;
import cn.edu.xhu.dao.HouseDao;
import cn.edu.xhu.dao.TradeInfoDao;
import cn.edu.xhu.dao.UserDao;
import cn.edu.xhu.domain.Admin;
import cn.edu.xhu.domain.House;
import cn.edu.xhu.domain.ResponseJson;
import cn.edu.xhu.domain.TradeInfo;
import cn.edu.xhu.domain.User;
import cn.edu.xhu.exception.AdminDaoOperatException;
import cn.edu.xhu.exception.HouseDaoOperatException;
import cn.edu.xhu.exception.TradeInfoDaoException;
import cn.edu.xhu.exception.UserDaoOperatException;

public class AdminService {
	/**
	 * ��¼
	 * 
	 * @param name
	 * @param password
	 * @return
	 * @throws AdminDaoOperatException
	 */
	public String login(String name, String password, HttpSession session) {
		ResponseJson<User> response = new ResponseJson<User>();
		if (name == null || password == null) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		AdminDao dao = new AdminDao();
		try {
			if (dao.checkAdmin(name, password)) {
				response.setState("0");
				String message = "��¼�ɹ�";
				response.setMessage(message);
				session.setAttribute("admin_login", "true");
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				String message = "�˺Ż��������";
				response.setMessage(message);
				return new Gson().toJson(response);
			}
		} catch (AdminDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}

	}

	/**
	 * ��ѯ�����û�
	 * 
	 * @param <T>
	 * 
	 * @return
	 */
	public String listUsers() {
		UserDao dao = new UserDao();
		ResponseJson<User> response = new ResponseJson<User>();
		try {
			List<User> resultList = new ArrayList<User>();
			resultList = dao.queryUsers();
			response.setState("0");
			String message = "��ѯ�ɹ�";
			response.setMessage(message);
			response.setResultData(resultList);
			return new Gson().toJson(response);
		} catch (UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * �����û�
	 * 
	 * @param id
	 * @return
	 */
	public String forbidUserById(int id) {
		ResponseJson<String> response = new ResponseJson<String>();
		if (id == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		UserDao dao = new UserDao();
		try {
			if (dao.forbidUserById(id)) {
				response.setState("0");
				String message = "���óɹ�";
				response.setMessage(message);
				return new Gson().toJson(response);
			} else {
				response.setState(ResponseConsts.STATE_TWO);
				response.setMessage(ResponseConsts.MESSAGE_TWO);
				return new Gson().toJson(response);
			}
		} catch (UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ��ѯ�û��ķ��ݷ�����¼
	 * 
	 * @param userId
	 */
	public String listHouseByUserId(int userId) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (userId == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		HouseDao dao = new HouseDao();
		try {
			if (new UserDao().checkUserById(userId)) {
				List<House> houseList = dao.queryHousesByUserId(userId);
				response.setState("0");
				response.setMessage("��ѯ�ɹ�");
				response.setResultData(houseList);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("�û�������");
				return new Gson().toJson(response);
			}

		} catch (HouseDaoOperatException | UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ��ѯ���˵Ľ��׼�¼[�����������������]
	 * 
	 * @param userId
	 * @return
	 */
	public String queryTradeInfosByUserId(int userId) {
		ResponseJson<TradeInfo> response = new ResponseJson<TradeInfo>();
		if (userId == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		TradeInfoDao dao = new TradeInfoDao();
		try {
			if (new UserDao().checkUserById(userId)) {
				List<TradeInfo> tradeInfos = dao.queryTradeInfosByUserId(userId);
				response.setState("0");
				response.setMessage("��ѯ�ɹ�");
				response.setResultData(tradeInfos);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("�û�������");
				return new Gson().toJson(response);
			}

		} catch (TradeInfoDaoException | UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ��ѯδ��˵ķ�����Ϣ
	 * 
	 * @return
	 */
	public String listUnQualifiedHouses() {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryUnQualifiedHouses();
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
	 * ��˷���
	 * 
	 * @param id
	 * @return
	 */
	public String permitHouse(int id) {
		ResponseJson<String> response = new ResponseJson<String>();
		if (id == 0) {
			response.setState(ResponseConsts.STATE_TWO);
			response.setMessage(ResponseConsts.MESSAGE_TWO);
			return new Gson().toJson(response);
		}
		HouseDao dao = new HouseDao();
		try {
			if (dao.checkHouseById(id)) {
				if (dao.permitHouse(id)) {
					response.setState("0");
					response.setMessage("��˳ɹ�");
					return new Gson().toJson(response);
				} else {
					response.setState(ResponseConsts.STATE_TWO);
					response.setMessage(ResponseConsts.MESSAGE_TWO);
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("���ݲ�����");
				return new Gson().toJson(response);
			}

		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * ��ӹ���Ա���ݲ����ţ�
	 * 
	 * @param admin
	 * @return
	 * @throws AdminDaoOperatException
	 */
	public boolean add(Admin admin) throws AdminDaoOperatException {
		if (admin == null) {
			throw new AdminDaoOperatException("����Ա��Ϣ����Ϊnull");
		}
		AdminDao dao = new AdminDao();
		try {
			if (!dao.checkAdmin(admin.getName(), admin.getPassword())) {
				dao.addAdmin(admin);
				System.out.println("��ӳɹ�!");
			} else {
				System.out.println("����Ա�Ѿ�����!");
			}
		} catch (AdminDaoOperatException e) {
			throw new RuntimeException(e);
		}
		return false;
	}
}
