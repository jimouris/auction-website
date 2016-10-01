<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Conversation</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="/css/skeleton.css" rel="stylesheet">
    <link href="/css/custom.css" rel="stylesheet">
    <link href="/css/organism.css" rel="stylesheet">

    <jsp:useBean id="messagesLst" class="java.util.ArrayList" scope="request" />
    <jsp:useBean id="sendersLst" class="java.util.ArrayList" scope="request" />
</head>
<body>
<c:if test="${empty user}">
    <c:redirect url="/"/>
</c:if>
<c:if test="${not empty param.rid}">
<!-- HEADER STUFF -->
<%@ include file="../header.jsp" %>

<div class="container">
    <!-- MESSAGES -->
    <div class="row">
        <main class="u-full-width ">
            <div class="row u-full-width">
                <form action="/message.do" method="POST">
                    <input type="hidden" name="rid" value=${rid} />
                    <input type="hidden" name="aid" value=${aid} />
                    <input type="text" name="message_text" required class="u-full-width">
                    <button class="button-primary" type="submit" name="action" value="addNewMessage">send</button>
                </form>
            </div>
            <c:if test="${not empty messagesLst}">
                <c:forEach var="message" items="${messagesLst}" varStatus="status">
                    <c:if test="${user.userId == sendersLst[status.index].userId}">
                        <div class="message message--you c-delete__trigger">
                            <span class="message__composer">you:</span>
                            <span class="message__text">${message.message}</span>
                            <span class="message__time">${message.sendDate}</span>
                            <div class="message__delete c-delete">
                                <span class="c-delete__icon"></span>
                                <form action="/message.do" method="POST" class="h-fillParent">
                                    <span class="c-delete__cancel">cancel</span>
                                    <input type="hidden" name="mid" value="${message.messageId}">
                                    <input type="hidden" name="sid" value="${message.senderId}">
                                    <input type="hidden" name="rid" value="${rid}">
                                    <input type="hidden" name="aid" value="${aid}">
                                    <button type="submit" name="action" value="deleteMessage" class="c-delete__confirm h-fillParent">delete message</button>
                                </form>
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${user.userId != sendersLst[status.index].userId}">
                        <p class="message message--other"><span class="message__composer">${sendersLst[status.index].firstname} ${sendersLst[status.index].lastname}:</span> <span class="message__text">${message.message}</span> <span class="message__time">${message.sendDate}</span></p>
                    </c:if>
                </c:forEach>
            </c:if>
            <hr />
        </main>
    </div>
    <!-- end of messages row -->
    </c:if>

</div>
<script src="/js/jquery.min.js"></script>
<script src="../js/jquery.min.js"></script>
<script src="../js/scripts.js"></script>
</body>
</html>
