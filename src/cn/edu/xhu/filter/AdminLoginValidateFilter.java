package cn.edu.xhu.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import cn.edu.xhu.consts.ResponseConsts;
import cn.edu.xhu.domain.ResponseJson;

@WebFilter(urlPatterns = { "/admin/listusers", "/admin/forbiduser", "/admin/listhousesofuser",
		"/admin/listtradeinfosofuser/", "/admin/listunqualifiedhouses", "/admin/permithouse" })
public class AdminLoginValidateFilter implements Filter {
	protected FilterConfig filterConfig;

	@Override
	public void destroy() {
		filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		HttpSession session = request.getSession();
		response.setContentType("text/plain;charset=utf-8");
		if ((session.getAttribute("admin_login") == null)) {
			ResponseJson<String> json = new ResponseJson<String>();
			json.setState(ResponseConsts.STATE_FIVE);
			json.setMessage(ResponseConsts.MESSAGE_FIVE_ADMIN);
			response.getWriter().print(new Gson().toJson(json));
			return;
		}
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
	}

}
