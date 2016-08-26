package javauction.controller;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by gpelelis on 25/8/2016.
 */
public class notificationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
