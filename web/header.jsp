<!-- HEADER STUFF -->
<header class="l-full">
    <div class="c-header h-cf">
        <div class="three columns">
            <a href="/" class="icon-home home"></a>
            <span class="greetings">
            <c:if test="${not empty sessionScope.user}">
                Hello ${sessionScope.user.getUsername()},
            </c:if>
            <c:if test="${empty sessionScope.user}">
                <c:if test="${empty isAdmin}">
                    Hello guest,
                </c:if>
                <c:if test="${not empty isAdmin}">
                    Hello admin,
                </c:if>
            </c:if>
            </span>
        </div>
    <c:if test="${not empty sessionScope.user}">
        <div class="offset-by-one two columns">
            <div class="c-dropdown c-notification" unselectable="on">
                <span class="c-dropdown__trigger"><span class="c-btn--round c-btn icon-bell"></span>notifications</span>
                <div class="c-dropdown__content notifications">
                    <p class="c-dropdown__title">Latest notifications</p>
                    <c:if test="${not empty notifLst}">
                        <c:forEach var="notification" items="${notifLst}">
                            <c:if test="${notification.isSeen == 1}">
                                <p class="c-notification--seen">
                                    You have a new ${notification.type} from user ${notification.actor.username}. <a href="/notification.do?action=viewNotification&nid=${notification.notificationId}">${notification.type} back</a>
                                </p>
                            </c:if>
                            <c:if test="${notification.isSeen == 0}">
                                <p class="c-notification--unseen" data-type="${notification.type}">
                                    You have a new ${notification.type} from user ${notification.actor.username}. <a href="/notification.do?action=viewNotification&nid=${notification.notificationId}">${notification.type} back</a>
                                </p>
                            </c:if>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="two columns">
            <div class="c-dropdown c-messages" unselectable="on">
                <span class="c-dropdown__trigger"><span class="c-btn--round c-btn icon-chat-alt"></span>messages</span>
                <div class="c-dropdown__content l-pad1">
                    <a class="icon-download c-dropdown__item" href="/message.do?action=listInbox">Inbox</a><br />
                    <a class="icon-upload c-dropdown__item" href="/message.do?action=listSent">Sent</a>
                </div>
            </div>
        </div>
        <div class="two columns">
            <div class="c-dropdown c-ratings" unselectable="on">
                <span class="c-dropdown__trigger"><span class="c-btn--round c-btn icon-star-filled"></span>ratings</span>
                <div class="c-dropdown__content l-pad1">
                    <a class="icon-download c-dropdown__item" href="/rate.do?action=listFrom">Received</a><br/>
                    <a class="icon-upload c-dropdown__item" href="/rate.do?action=listTo">Submitted</a>
                </div>
            </div>
        </div>
        <div class="two columns logout">
            <a href="/logout.do"><span class="delete icon-power">Logout</span></a>
        </div>
    </c:if>
    <c:if test="${not empty isAdmin and isAdmin == true}">
        <div class="eight columns logout">
            <a href="/logout.do"><span class="delete icon-power">Logout</span></a>
        </div>
    </c:if>
    </div>
</header>
<!-- end of header row -->