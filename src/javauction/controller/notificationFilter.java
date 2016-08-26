package javauction.controller;

import javauction.model.NotificationEntity;
import javauction.model.UserEntity;
import javauction.service.NotificationService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by gpelelis on 25/8/2016.
 */
public class notificationFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // get all the notifications and add them to a list
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        NotificationService notificationService = new NotificationService();
        UserEntity user = (UserEntity) session.getAttribute("user");
        if (user != null) {
            long rid = user.getUserId();
            List<NotificationEntity> notifList = notificationService.getNotificationsOf(rid);

            //        generate the appropriate list with notifications
            //        List<Notification> notifLst = null;
            //        for (NotificationEntity notif : notifList){
            //            switch (notif.getType()){
            //                case "message":
            //                    notifLst.add("")
            //                break;
            //            }
            //        }

            // append them to request object
            request.setAttribute("notifLst", notifList);
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
