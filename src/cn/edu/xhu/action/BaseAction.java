package cn.edu.xhu.action;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cn.edu.xhu.consts.ResponseConsts;
import cn.edu.xhu.domain.ResponseJson;

/**
 * ��ȡ�û���action���󣬲��Ҹ�������������ü̳и��������ķ���
 * 
 * @author
 *
 */
public class BaseAction extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			String path = request.getRequestURI();
			String remove = path.substring(0, path.lastIndexOf("/") + 1);
			String action = path.replace(remove, "").toLowerCase();
			Method method = this.getClass().getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
			method.invoke(this, request, response);
		} catch (Exception e) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FOUR);
			json.setMessage(ResponseConsts.MESSAGE_FOUR);
			response.getWriter().print(new Gson().toJson(json));
			 e.printStackTrace();
		}
	}

//	 ����GET����
	 @Override
	 protected void doGet(HttpServletRequest request, HttpServletResponse
	 response)
	 throws ServletException, IOException {
	 doPost(request, response);
	 }
}
