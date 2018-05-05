package cn.edu.xhu.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;

import com.google.gson.Gson;

import cn.edu.xhu.consts.ResponseConsts;
import cn.edu.xhu.domain.House;
import cn.edu.xhu.domain.Image;
import cn.edu.xhu.domain.ResponseJson;
import cn.edu.xhu.domain.User;
import cn.edu.xhu.exception.FileUploadOperatException;
import cn.edu.xhu.service.UserService;
import cn.edu.xhu.util.CommUtils;
import cn.edu.xhu.util.FileUploadUtil;

@WebServlet(urlPatterns = { "/user/*" })
public class UserAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		UserService service = new UserService();
		String phone = request.getParameter("phone");
		String password = request.getParameter("password");
		String json = service.login(phone, password, request.getSession());
		response.getWriter().print(json);
	}

	/**
	 * 注销登录
	 * 
	 * @param request
	 * @param response
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		new UserService().logout(request.getSession());
	}

	/**
	 * 注册
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, String[]> map = request.getParameterMap();
		UserService service = new UserService();
		try {
			User user = CommUtils.convertMapToBean(map, User.class);
			String json = service.registerUser(user);
			response.getWriter().print(json);
		} catch (Exception e) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FOUR);
			json.setMessage(ResponseConsts.MESSAGE_FOUR);
			response.getWriter().print(new Gson().toJson(json));
		}
	}

	/**
	 * 修改密码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updatepassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = (int) request.getSession().getAttribute("user_id");
		if (id == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		String password = request.getParameter("password");
		String newPassword = request.getParameter("new_password");
		String json = new UserService().updatePassword(password, newPassword, id, request.getSession());
		response.getWriter().print(json);
	}

	/**
	 * 查询个人信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void lookinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = (int) request.getSession().getAttribute("user_id");
		if (id == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		String json = new UserService().lookInfo(id);
		response.getWriter().print(json);
	}

	/**
	 * 修改个人信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updateinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int id = (int) request.getSession().getAttribute("user_id");
		if (id == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		Map<String, String[]> map = request.getParameterMap();
		try {
			User user = CommUtils.convertMapToBean(map, User.class);
			user.setId(id);
			String json = new UserService().updateInfo(user);
			response.getWriter().print(json);
		} catch (Exception e) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.MESSAGE_FOUR);
			json.setMessage(ResponseConsts.MESSAGE_FOUR);
			response.getWriter().print(new Gson().toJson(json));
		}
	}

	/**
	 * 发布房屋
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void relasehouse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("user_id");
		if (userid == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		Map<String, String[]> map = request.getParameterMap();
		try {
			House house = CommUtils.convertMapToBean(map, House.class);
			house.setUserid(userid);
			String json = new UserService().relaseHouse(house);
			response.getWriter().print(json);
		} catch (Exception e) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FOUR);
			json.setMessage(ResponseConsts.MESSAGE_FOUR);
			response.getWriter().print(new Gson().toJson(json));
		}
	}

	/**
	 * 根据ID查询房屋信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void lookhouseinfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String houseId = request.getParameter("house_id");
		UserService service = new UserService();
		String json = service.queryHouseById(houseId);
		response.getWriter().print(json);
	}

	/**
	 * 修改房屋信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void updatehouse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("user_id");
		if (userid == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		Map<String, String[]> map = request.getParameterMap();
		try {
			House house = CommUtils.convertMapToBean(map, House.class);
			house.setUserid(userid);
			String json = new UserService().updateHouseInfo(house);
			response.getWriter().print(json);
		} catch (Exception e) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FOUR);
			json.setMessage(ResponseConsts.MESSAGE_FOUR);
			response.getWriter().print(new Gson().toJson(json));
		}
	}

	/**
	 * 用户查看已发布可交易房产信息(默认按时间升序)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listprettyhouses(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = new UserService().listPrettyHouses();
		response.getWriter().print(json);
	}

	// 通过价格查找
	public void listprettyhousesbysetprice(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String startPrice = request.getParameter("start_price");
		String endPrice = request.getParameter("end_price");
		String json = new UserService().listPrettyHousesByPrice(Integer.parseInt(startPrice),
				Integer.parseInt(endPrice));
		response.getWriter().print(json);
	}

	// 通过面积查找
	public void listprettyhousesbysetsize(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String startSize = request.getParameter("start_size");
		String endSize = request.getParameter("end_size");
		String json = new UserService().listPrettyHousesBySize(Integer.parseInt(startSize), Integer.parseInt(endSize));
		response.getWriter().print(json);
	}

	// 通过户型查找
	public void listprettyhousesbysettype(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String type = request.getParameter("type");
		String json = new UserService().listPrettyHousesByType(type);
		response.getWriter().print(json);
	}

	// 通过户型查找
	public void listprettyhousesbysetkind(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String kind = request.getParameter("kind");
		String json = new UserService().listPrettyHousesByKind(kind);
		response.getWriter().print(json);
	}

	// 默认排序带参数
	public void listprettyhousesbyparameterandorderbydefault(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		String kind = request.getParameter("kind");
		String startPrice = request.getParameter("start_price");
		String endPrice = request.getParameter("end_price");
		String startSize = request.getParameter("start_size");
		String endSize = request.getParameter("end_size");
		String json = new UserService().queryHouseByParameterAndOrderByDefault(type, kind, startPrice, endPrice,
				startSize, endSize);
		response.getWriter().print(json);
	}

	// 价格升序 带参数
	public void listprettyhousesbyparameterandorderbypriceup(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		String kind = request.getParameter("kind");
		String startSize = request.getParameter("start_size");
		String endSize = request.getParameter("end_size");
		String json = new UserService().queryHouseByParameterAndOrderByPriceUp(type, kind, startSize, endSize);
		response.getWriter().print(json);
	}

	// 价格降序 带参数
	public void listprettyhousesbyparameterandorderbypricedown(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		String kind = request.getParameter("kind");
		String startSize = request.getParameter("start_size");
		String endSize = request.getParameter("end_size");
		String json = new UserService().queryHouseByParameterAndOrderByPriceDown(type, kind, startSize, endSize);
		response.getWriter().print(json);
	}

	// 面积升序 带参数
	public void listprettyhousesbyparameterandorderbysizeup(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		String kind = request.getParameter("kind");
		String startPrice = request.getParameter("start_price");
		String endPrice = request.getParameter("end_price");
		String json = new UserService().queryHouseByParameterAndOrderBySizeUp(type, kind, startPrice, endPrice);
		response.getWriter().print(json);
	}

	// 面积降序 带参数
	public void listprettyhousesbyparameterandorderbysizedown(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		String kind = request.getParameter("kind");
		String startPrice = request.getParameter("start_price");
		String endPrice = request.getParameter("end_price");
		String json = new UserService().queryHouseByParameterAndOrderBySizeDown(type, kind, startPrice, endPrice);
		response.getWriter().print(json);
	}

	// 搜索房屋
	public void searchhouses(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String parameter = request.getParameter("parameter");
		String json = new UserService().searchHouses(parameter);
		response.getWriter().print(json);
	}

	// 查询三间房屋
	public void listthreeimages(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = new UserService().queryThreeImages();
		response.getWriter().print(json);
	}

	/**
	 * 按时间降序
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listprettyhousesbytimedesc(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String json = new UserService().listPrettyHouseOrderByParameter("house_time", true);
		response.getWriter().print(json);
	}

	/**
	 * 按价格升序
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listprettyhousesbyprice(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = new UserService().listPrettyHouseOrderByParameter("house_price", false);
		response.getWriter().print(json);
	}

	/**
	 * 按价格降序
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listprettyhousesbypricedesc(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String json = new UserService().listPrettyHouseOrderByParameter("house_price", true);
		response.getWriter().print(json);
	}

	/**
	 * 按面积升序
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listprettyhousesbysize(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = new UserService().listPrettyHouseOrderByParameter("house_size", false);
		response.getWriter().print(json);
	}

	/**
	 * 按面积降序
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listprettyhousesbysizedesc(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String json = new UserService().listPrettyHouseOrderByParameter("house_size", true);
		response.getWriter().print(json);
	}

	/**
	 * 查看自己发布的房屋信息
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listmyhouses(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("user_id");
		if (userid == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		String json = new UserService().listHousesByUserId(userid);
		response.getWriter().print(json);
	}

	/**
	 * 上传图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void uploadimage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("user_id");
		if (userid == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		try {
			FileUploadUtil.upload(userid, request, response);
		} catch (Exception e) {
			ResponseJson<Image> json = new ResponseJson<>();
			json.setState("2");
			if (e instanceof FileUploadOperatException) {
				json.setMessage(e.toString());
				response.getWriter().print(new Gson().toJson(json));
			} else if (e instanceof SizeLimitExceededException) {
				json.setMessage("文件大小不满足");
				response.getWriter().print(new Gson().toJson(json));
			} else {
				json.setMessage("上传过程异常");
				response.getWriter().print(new Gson().toJson(json));
			}
		}
	}

	/**
	 * 查看房屋对应的图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listimages(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String houseId = request.getParameter("house_id");
		String json = new UserService().queryImagesByHouseId(Integer.parseInt(houseId));
		response.getWriter().print(json);
	}

	/**
	 * 删除图片
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deleteimage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("user_id");
		if (userid == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		String imageId = request.getParameter("image_id");
		String houseId = request.getParameter("house_id");
		String json = new UserService().deleteImage(Integer.parseInt(imageId), Integer.parseInt(houseId), userid);
		response.getWriter().print(json);
	}

	/**
	 * 下架房屋
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void deletehouse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("user_id");
		if (userid == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		String houseId = request.getParameter("house_id");
		String json = new UserService().deleteHouse(Integer.parseInt(houseId), userid);
		response.getWriter().print(json);

	}

	/**
	 * 请求交易
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void requestdeal(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("user_id");
		if (userid == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		String houseId = request.getParameter("house_id");
		String json = new UserService().requestDeal(Integer.parseInt(houseId), userid);
		response.getWriter().print(json);
	}

	// /**
	// * 查看自己的交易信息
	// *
	// * @param request
	// * @param response
	// * @throws IOException
	// */
	// public void listtradeinfos(HttpServletRequest request,
	// HttpServletResponse response) throws IOException {
	// int userid = (int) request.getSession().getAttribute("user_id");
	// if (userid == 0) {
	// ResponseJson<String> json = new ResponseJson<String>();
	// json.setState(ResponseConsts.STATE_FIVE);
	// json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
	// response.getWriter().print(new Gson().toJson(json));
	// return;
	// }
	// String json = new UserService().queryTradeInfosByUserId(userid);
	// response.getWriter().print(json);
	// }

	/**
	 * 用户查看购买记录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listbuyinfos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("user_id");
		if (userid == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		String json = new UserService().queryBuyInfosByUserId(userid);
		response.getWriter().print(json);
	}

	/**
	 * 用户查看售出记录
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listsellinfos(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int userid = (int) request.getSession().getAttribute("user_id");
		if (userid == 0) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_USER);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		String json = new UserService().querySellInfosByUserId(userid);
		response.getWriter().print(json);
	}
}
