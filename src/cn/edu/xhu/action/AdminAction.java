package cn.edu.xhu.action;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.edu.xhu.service.AdminService;

@WebServlet(urlPatterns = "/admin/*")
public class AdminAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * ��¼
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AdminService service = new AdminService();
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String json = service.login(name, password, request.getSession());
		response.getWriter().print(json);
	}

	/**
	 * �г������û�
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listusers(HttpServletRequest request, HttpServletResponse response) throws IOException {
		AdminService service = new AdminService();
		String json = service.listUsers();
		response.getWriter().print(json);
	}

	/**
	 * �����û�
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void forbiduser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("user_id");
		String json = new AdminService().forbidUserById(Integer.parseInt(userId));
		response.getWriter().print(json);
	}

	/**
	 * �г��û��ķ�����¼
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listhousesofuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("user_id");
		String json = new AdminService().listHouseByUserId(Integer.parseInt(userId));
		response.getWriter().print(json);
	}

	/**
	 * �г��û����׼�¼
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listtradeinfosofuser(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String userId = request.getParameter("user_id");
		String json = new AdminService().queryTradeInfosByUserId(Integer.parseInt(userId));
		response.getWriter().print(json);
	}

	/**
	 * �г�����δ��˷���
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void listunqualifiedhouses(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String json = new AdminService().listUnQualifiedHouses();
		response.getWriter().print(json);
	}

	/**
	 * �û���˷���
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void permithouse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String houseId = request.getParameter("house_id");
		String json = new AdminService().permitHouse(Integer.parseInt(houseId));
		response.getWriter().print(json);
	}
}
