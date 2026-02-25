package jp.co.sss.equipment.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jp.co.sss.equipment.filter.LoginCheckFilter;

@Component
public class LoginUserInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
	    HttpSession session = request.getSession();
	    Object user = session.getAttribute("user");
	    if (user != null) {
	        request.setAttribute("loginUser", user);
	    }
	    return true;
	}
	
	@Bean
	public FilterRegistrationBean<LoginCheckFilter> loginCheckFilter() {
	    FilterRegistrationBean<LoginCheckFilter> bean =
	            new FilterRegistrationBean<>();

	    bean.setFilter(new LoginCheckFilter());
	    bean.addUrlPatterns("/*"); // 全部対象
	    return bean;
	}
}