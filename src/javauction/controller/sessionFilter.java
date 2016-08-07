package javauction.controller;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by jimouris on 8/7/16.
 */
public class sessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();

        boolean loggedIn = false;
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (session != null) {
            loggedIn = session.getAttribute("uid") != null;
            if (isAdmin == null) {
                isAdmin = false;
            }
            if (isAdmin) {
                loggedIn = true;
            }
        }
        String requestPath = request.getRequestURI();
        if (loggedIn) {
            if (isAdmin) {
                /* admin can't go to the following pages */
                if (!requestPath.startsWith("/admin/") || requestPath.equals("/admin/") || requestPath.equals("/public/backoffice.jsp")) {
                    response.sendRedirect("/admin/homepage.jsp");
                } else {
                    filterChain.doFilter(request, response);
                }
            } else {
                /* user can't go to the following pages */
                if (requestPath.startsWith("/admin/") || requestPath.equals("/public") || requestPath.equals("/public/")
                        || requestPath.equals("/public/index.jsp") || requestPath.equals("/public/register.jsp")
                        || requestPath.equals("/public/backoffice.jsp")) {
                    response.sendRedirect("/user/homepage.jsp");
                } else {
                    filterChain.doFilter(request, response);
                }
            }
        } else { /* if not logged in */
            if (requestPath.startsWith("/public") || requestPath.equals("/")) {
                filterChain.doFilter(request, response);
            } else {
                response.sendRedirect("/public/");
            }


        }
    }

    @Override
    public void destroy() {

    }

}
