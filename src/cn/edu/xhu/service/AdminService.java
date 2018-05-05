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
	 * 登录
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
				String message = "登录成功";
				response.setMessage(message);
				session.setAttribute("admin_login", "true");
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				String message = "账号或密码错误";
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
	 * 查询所有用户
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
			String message = "查询成功";
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
	 * 禁用用户
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
				String message = "禁用成功";
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
	 * 查询用户的房屋发布记录
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
				response.setMessage("查询成功");
				response.setResultData(houseList);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("用户不存在");
				return new Gson().toJson(response);
			}

		} catch (HouseDaoOperatException | UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 查询个人的交易记录[包括购买的与卖出的]
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
				response.setMessage("查询成功");
				response.setResultData(tradeInfos);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("用户不存在");
				return new Gson().toJson(response);
			}

		} catch (TradeInfoDaoException | UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 查询未审核的房屋信息
	 * 
	 * @return
	 */
	public String listUnQualifiedHouses() {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryUnQualifiedHouses();
			response.setState("0");
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 审核房屋
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
					response.setMessage("审核成功");
					return new Gson().toJson(response);
				} else {
					response.setState(ResponseConsts.STATE_TWO);
					response.setMessage(ResponseConsts.MESSAGE_TWO);
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("房屋不存在");
				return new Gson().toJson(response);
			}

		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 添加管理员（暂不开放）
	 * 
	 * @param admin
	 * @return
	 * @throws AdminDaoOperatException
	 */
	public boolean add(Admin admin) throws AdminDaoOperatException {
		if (admin == null) {
			throw new AdminDaoOperatException("管理员信息不能为null");
		}
		AdminDao dao = new AdminDao();
		try {
			if (!dao.checkAdmin(admin.getName(), admin.getPassword())) {
				dao.addAdmin(admin);
				System.out.println("添加成功!");
			} else {
				System.out.println("管理员已经存在!");
			}
		} catch (AdminDaoOperatException e) {
			throw new RuntimeException(e);
		}
		return false;
	}
}
