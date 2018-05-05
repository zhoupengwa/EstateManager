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
	 * 用户注册
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
					response.setMessage("注册成功");
					return new Gson().toJson(response);
				} else {
					response.setState(ResponseConsts.STATE_TWO);
					response.setMessage(ResponseConsts.MESSAGE_TWO);
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("用户已存在");
				return new Gson().toJson(response);
			}
		} catch (UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 用户登录
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
					response.setMessage("登录成功");
					// httpServletResponse.addCookie(new Cookie("token",
					// session.getId()));
					return new Gson().toJson(response);
				} else {
					response.setState("6");
					response.setMessage("用户被禁用");
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("账号或密码错误");
				return new Gson().toJson(response);
			}
		} catch (Exception e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 用户注销登录
	 * 
	 * @param session
	 */
	public void logout(HttpSession session) {
		session.setAttribute("user_id", null);
	}

	/**
	 * 修改密码
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
				response.setMessage("修改成功");
				session.removeAttribute("user_id");
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("修改失败");
				return new Gson().toJson(response);
			}
		} catch (UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 查询个人信息
	 * 
	 * @param phone
	 * @throws UserDaoOperatException
	 */
	public String lookInfo(int id) {
		ResponseJson<User> response = new ResponseJson<User>();
		if (id == 0) {
			response.setState("1");
			response.setMessage("用户不存在");
			return new Gson().toJson(response);
		}
		UserDao dao = new UserDao();
		try {
			if (dao.checkUserById(id)) {
				List<User> userList = new ArrayList<User>();
				User user = dao.queryUserById(id);
				userList.add(user);
				response.setState("0");
				response.setMessage("查询成功");
				response.setResultData(userList);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("用户不存在!");
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
	 * 修改个人信息
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
					response.setMessage("修改个人信息成功");
					return new Gson().toJson(response);
				} else {
					response.setState(ResponseConsts.STATE_TWO);
					response.setMessage(ResponseConsts.MESSAGE_TWO);
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("用户信息不存在");
				return new Gson().toJson(response);
			}
		} catch (UserDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 添加房屋信息
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
						response.setMessage("房屋信息添加成功");
						response.setResultData(houseList);
					} else {
						response.setState(ResponseConsts.STATE_TWO);
						response.setMessage(ResponseConsts.MESSAGE_TWO);
					}
				} else {
					response.setState("1");
					response.setMessage("用户不存在，无法添加");
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
	 * 修改房产信息[id、userid、kind、area、traded、qualified、time不能修改]
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
					response.setMessage("房屋信息修改成功");
					return new Gson().toJson(response);
				} else {
					response.setState(ResponseConsts.STATE_TWO);
					response.setMessage(ResponseConsts.MESSAGE_TWO);
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("房屋信息不存在");
				return new Gson().toJson(response);
			}
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 查看已发布、且审核合格的房屋信息
	 */
	public String listPrettyHouses() {
		HouseDao dao = new HouseDao();
		ResponseJson<House> response = new ResponseJson<House>();
		try {
			List<House> houses = dao.queryPrettyHouses();
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

	// 通过价格查找
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
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 通过面积查找
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
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 通过户型查找
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
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 通过类型查找
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
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 按提交参数查询，户型，类型，价格[低->高]，面积[低->高]，售出状态（默认排序，按发布时间排序，最新的排在前面）
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
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 按提交参数查询，户型，类型，价格[低->高]，面积[低->高]，售出状态（价格升序）
	public String queryHouseByParameterAndOrderByPriceUp(String type, String kind, String startSize, String endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByParameterOrderByPriceUp(type, kind, startSize, endSize);
			response.setState("0");
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 按提交参数查询，户型，类型，价格[低->高]，面积[低->高]，售出状态（价格降序）
	public String queryHouseByParameterAndOrderByPriceDown(String type, String kind, String startSize, String endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByParameterOrderByPriceDown(type, kind, startSize, endSize);
			response.setState("0");
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 按提交参数查询，户型，类型，价格[低->高]，面积[低->高]，售出状态（面积升序）
	public String queryHouseByParameterAndOrderBySizeUp(String type, String kind, String startSize, String endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByParameterOrderBySizeUp(type, kind, startSize, endSize);
			response.setState("0");
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 按提交参数查询，户型，类型，价格[低->高]，面积[低->高]，售出状态（面积升序）
	public String queryHouseByParameterAndOrderBySizeDown(String type, String kind, String startSize, String endSize) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.queryHouseByParameterOrderBySizeDown(type, kind, startSize, endSize);
			response.setState("0");
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 搜索房屋
	public String searchHouses(String parameter) {
		ResponseJson<House> response = new ResponseJson<House>();
		HouseDao dao = new HouseDao();
		try {
			List<House> houses = dao.searchHouse(parameter);
			response.setState("0");
			response.setMessage("查询成功");
			response.setResultData(houses);
			return new Gson().toJson(response);
		} catch (Exception e) {
			e.printStackTrace();
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	// 查询前三间房屋
	public String queryThreeImages() {
		ResponseJson<Image> response = new ResponseJson<Image>();
		HouseDao dao = new HouseDao();
		try {
			List<Image> images = dao.queryThreeImages();
			response.setState("0");
			response.setMessage("查询成功");
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
	 * 根据ID查找房屋信息
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
				response.setState("查询成功");
				response.setResultData(houses);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setState("房屋不存在");
				return new Gson().toJson(response);
			}

		} catch (Exception e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}

	}

	/**
	 * 排序查找
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
	 * 列出自己发布的房屋信息
	 * 
	 * @return
	 * 
	 */
	public String listHousesByUserId(int userId) {
		ResponseJson<House> response = new ResponseJson<House>();
		if (userId == 0) {
			response.setState("1");
			response.setMessage("用户不存在");
			return new Gson().toJson(response);
		}
		UserDao userDao = new UserDao();
		try {
			if (userDao.checkUserById(userId)) {
				HouseDao houseDao = new HouseDao();
				List<House> houses = houseDao.queryHousesByUserId(userId);
				response.setState("0");
				response.setMessage("查询成功");
				response.setResultData(houses);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("用户不存在");
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
	 * 根据id查询房产信息
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
				response.setMessage("查询成功");
				response.setResultData(houseList);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("房屋信息不存在");
				return new Gson().toJson(response);
			}
		} catch (HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 图片上传
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
			String message = "上传成功";
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
	 * 根据houseId查询图片
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
			response.setMessage("获取成功");
			response.setResultData(images);
			return new Gson().toJson(response);
		} catch (ImageDaoException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 根据图片id删除
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
						response.setMessage("删除成功");
						return new Gson().toJson(response);
					} else {
						response.setState(ResponseConsts.STATE_TWO);
						response.setMessage(ResponseConsts.MESSAGE_TWO);
						return new Gson().toJson(response);
					}
				} else {
					response.setState("1");
					response.setMessage("图片不存在");
					return new Gson().toJson(response);
				}
			} else {
				response.setState("1");
				response.setMessage("图片不存在");
				return new Gson().toJson(response);
			}

		} catch (ImageDaoException | HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 下架房产
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
						response.setMessage("删除成功");
					}
				} else {
					response.setState("1");
					response.setMessage("房屋不存在");
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
	 * 请求交易[购买/租房]
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
					response.setMessage("不能购买自己的上架");
				} else {
					if (houseDao.checkPrettyHouseById(houseId, sellerId)) {
						TradeInfoDao tradeInfoDao = new TradeInfoDao();
						if (tradeInfoDao.addTradeInfo(houseId, sellerId, buyerId, ThreadLocalDateUtil.formatDate())
								&& houseDao.changeHouseTradeState(houseId)) {
							response.setState("0");
							response.setMessage("交易成功！");
						} else {
							response.setState(ResponseConsts.STATE_TWO);
							response.setMessage(ResponseConsts.MESSAGE_TWO);
						}
					} else {
						response.setState("1");
						response.setMessage("房屋信息不存在");
					}
				}
			} else {
				response.setState("1");
				response.setMessage("房屋信息不存在");
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
	// * 查询个人的交易记录[包括购买的与卖出的]
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
	// response.setMessage("查询成功");
	// response.setResultData(tradeInfos);
	// return new Gson().toJson(response);
	// } else {
	// response.setState("1");
	// response.setMessage("用户不存在");
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
	 * 查询自己的购买记录
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
				response.setMessage("查询成功");
				response.setResultData(tradeInfos);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("用户不存在");
				return new Gson().toJson(response);
			}

		} catch (TradeInfoDaoException | UserDaoOperatException | HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}

	/**
	 * 查询自己的售出记录
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
				response.setMessage("查询成功");
				response.setResultData(tradeInfos);
				return new Gson().toJson(response);
			} else {
				response.setState("1");
				response.setMessage("用户不存在");
				return new Gson().toJson(response);
			}

		} catch (TradeInfoDaoException | UserDaoOperatException | HouseDaoOperatException e) {
			response.setState(ResponseConsts.STATE_THREE);
			response.setMessage(ResponseConsts.MESSAGE_THREE);
			return new Gson().toJson(response);
		}
	}
}
