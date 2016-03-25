<%
// no parameters are passed
if ( (request.getParameter("type") == null || request.getParameter("type") == "") ){
  response.sendRedirect("./index.jsp");
} else if ( request.getParameter("type").equals("admin") ){ // this is an admin
    session.setAttribute("isAdmin", null); // terminating admin's session
    session.invalidate();
    response.sendRedirect("./backoffice.jsp");
  }
  else if ( request.getParameter("type").equals("regular") ){ // this is a regular user
    session.setAttribute("isUser", null); // terminating user's session
    session.invalidate();
    response.sendRedirect("./index.jsp");
  }
%>