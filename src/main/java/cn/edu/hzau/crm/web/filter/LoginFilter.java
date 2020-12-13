package cn.edu.hzau.crm.web.filter;

import cn.edu.hzau.crm.settings.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String servletPath = request.getServletPath();

        if ("/login.jsp".equals(servletPath) || "/settings/user/login.do".equals(servletPath) || user != null){
            filterChain.doFilter(servletRequest, servletResponse);
        }else {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }

    }

    @Override
    public void destroy() {

    }
}
