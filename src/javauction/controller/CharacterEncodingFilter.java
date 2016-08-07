package javauction.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Created by gpelelis on 7/8/2016.
 */
@WebFilter(filterName = "CharacterEncodingFilter ", urlPatterns = "/*")
public class CharacterEncodingFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
