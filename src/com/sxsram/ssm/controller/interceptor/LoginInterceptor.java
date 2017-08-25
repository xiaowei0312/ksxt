package com.sxsram.ssm.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sxsram.ssm.entity.User;

@Controller
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 获取请求的url
		String url = request.getRequestURI().toString();
		if (request.getQueryString() != null)
			url += ("?" + request.getQueryString());

		//MDC.put("userId","unlogin");    
		// 判断url是否是公开 地址（实际使用时将公开 地址配置配置文件中）
		// 这里公开地址是登陆提交的地址
		if (url.indexOf("login") > 0 || url.indexOf("regist") > 0  || url.indexOf("logOut") > 0 || url.indexOf("Regist") > 0
				|| url.indexOf("error") > 0 || url.indexOf("imageVerifyCode") > 0
				|| url.indexOf("userNearbyStores") > 0)
			return true;
	
		// 获取session
		HttpSession session = request.getSession();

		// 从session中取出用户身份信息
		User sessionUser = (User) session.getAttribute("user");
		
		//MDC.put("userId",sessionUser.getId());
		//MDC.put("userName",sessionUser.getUsername());
		if (sessionUser != null) {
			return true;
		}

		// 执行这里表示用户身份需要认证，跳转登陆页面
		session.setAttribute("loginInterceptedReqUrl", url);
		request.getRequestDispatcher("/user/loginPage.action").forward(request, response);
		return false;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}
}