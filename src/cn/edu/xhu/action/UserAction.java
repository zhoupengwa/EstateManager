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
	 * ��¼
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
	 * ע����¼
	 * 
	 * @param request
	 * @param response
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		new UserService().logout(request.getSession());
	}

	/**
	 * ע��
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
	 * �޸�����
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
	 * ��ѯ������Ϣ
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
	 * �޸ĸ�����Ϣ
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
	 * ��������
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
	 * ����ID��ѯ������Ϣ
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
	 * �޸ķ�����Ϣ
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
	 * �û��鿴�ѷ����ɽ��׷�����Ϣ(Ĭ�ϰ�ʱ������)
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listprettyhouses(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = new UserService().listPrettyHouses();
		response.getWriter().print(json);
	}

	// ͨ���۸����
	public void listprettyhousesbysetprice(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String startPrice = request.getParameter("start_price");
		String endPrice = request.getParameter("end_price");
		String json = new UserService().listPrettyHousesByPrice(Integer.parseInt(startPrice),
				Integer.parseInt(endPrice));
		response.getWriter().print(json);
	}

	// ͨ���������
	public void listprettyhousesbysetsize(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String startSize = request.getParameter("start_size");
		String endSize = request.getParameter("end_size");
		String json = new UserService().listPrettyHousesBySize(Integer.parseInt(startSize), Integer.parseInt(endSize));
		response.getWriter().print(json);
	}

	// ͨ�����Ͳ���
	public void listprettyhousesbysettype(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String type = request.getParameter("type");
		String json = new UserService().listPrettyHousesByType(type);
		response.getWriter().print(json);
	}

	// ͨ�����Ͳ���
	public void listprettyhousesbysetkind(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String kind = request.getParameter("kind");
		String json = new UserService().listPrettyHousesByKind(kind);
		response.getWriter().print(json);
	}

	// Ĭ�����������
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

	// �۸����� ������
	public void listprettyhousesbyparameterandorderbypriceup(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		String kind = request.getParameter("kind");
		String startSize = request.getParameter("start_size");
		String endSize = request.getParameter("end_size");
		String json = new UserService().queryHouseByParameterAndOrderByPriceUp(type, kind, startSize, endSize);
		response.getWriter().print(json);
	}

	// �۸��� ������
	public void listprettyhousesbyparameterandorderbypricedown(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		String kind = request.getParameter("kind");
		String startSize = request.getParameter("start_size");
		String endSize = request.getParameter("end_size");
		String json = new UserService().queryHouseByParameterAndOrderByPriceDown(type, kind, startSize, endSize);
		response.getWriter().print(json);
	}

	// ������� ������
	public void listprettyhousesbyparameterandorderbysizeup(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		String kind = request.getParameter("kind");
		String startPrice = request.getParameter("start_price");
		String endPrice = request.getParameter("end_price");
		String json = new UserService().queryHouseByParameterAndOrderBySizeUp(type, kind, startPrice, endPrice);
		response.getWriter().print(json);
	}

	// ������� ������
	public void listprettyhousesbyparameterandorderbysizedown(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String type = request.getParameter("type");
		String kind = request.getParameter("kind");
		String startPrice = request.getParameter("start_price");
		String endPrice = request.getParameter("end_price");
		String json = new UserService().queryHouseByParameterAndOrderBySizeDown(type, kind, startPrice, endPrice);
		response.getWriter().print(json);
	}

	// ��������
	public void searchhouses(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String parameter = request.getParameter("parameter");
		String json = new UserService().searchHouses(parameter);
		response.getWriter().print(json);
	}

	// ��ѯ���䷿��
	public void listthreeimages(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = new UserService().queryThreeImages();
		response.getWriter().print(json);
	}

	/**
	 * ��ʱ�併��
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
	 * ���۸�����
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
	 * ���۸���
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
	 * ���������
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
	 * ���������
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
	 * �鿴�Լ������ķ�����Ϣ
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
	 * �ϴ�ͼƬ
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
				json.setMessage("�ļ���С������");
				response.getWriter().print(new Gson().toJson(json));
			} else {
				json.setMessage("�ϴ������쳣");
				response.getWriter().print(new Gson().toJson(json));
			}
		}
	}

	/**
	 * �鿴���ݶ�Ӧ��ͼƬ
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
	 * ɾ��ͼƬ
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
	 * �¼ܷ���
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
	 * ������
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
	// * �鿴�Լ��Ľ�����Ϣ
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
	 * �û��鿴�����¼
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
	 * �û��鿴�۳���¼
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
