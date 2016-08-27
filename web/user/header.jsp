<!-- HEADER STUFF -->
<div class="row">
    <div class="one column">
        <a href="/user/homepage.jsp">
            <img class="u-max-full-width" src="/images/logo.png">
        </a>
        Hello ${sessionScope.user.getFirstname()},
    </div>
    <div class="offset-by-four three columns">
        <div class="c-dropdown">
            <span class="c-dropdown__trigger">notifications</span>
            <div class="c-dropdown__content l-pad1">
                <p class="c-dropdown__title">Latest notifications</p>
                <c:if test="${not empty notifLst}">
                    <c:forEach var="notification" items="${notifLst}">
                        <c:if test="${notification.isSeen == 1}">
                            <p class="c-notification--seen">
                                You have a new ${notification.type} from user ${notification.actor.username}. <a href="/notification.do?action=viewNotification&nid=${notification.notificationId}">${notification.type} back</a>
                            </p>
                        </c:if>
                        <c:if test="${notification.isSeen == 0}">
                            <p class="c-notification--unseen">
                                You have a new ${notification.type} from user ${notification.actor.username}. <a href="/notification.do?action=viewNotification&nid=${notification.notificationId}">${notification.type} back</a>
                            </p>
                        </c:if>
                    </c:forEach>
                </c:if>
            </div>
        </div>
    </div>
    <div class=" four columns">
        <div class="nav u-full-width row">
            <div class="one-third column newMessage tooltip"><span class="tooltipFire">Messages</span>
                <div class="tooltipText"><div class="tooltipMargin"></div>
                    <a class="" href="/message.do?action=listInbox">Inbox</a>
                    <a class="" href="/message.do?action=listSent">Sent</a>
                </div>
            </div>
            <div class="one-third column newRating tooltip"><span class="tooltipFire">Ratings</span>
                <div class="tooltipText"><div class="tooltipMargin"></div>
                    <a class="" href="/rate.do?action=listFrom">From</a>
                    <a class="" href="/rate.do?action=listTo">To</a>
                </div>
            </div>
            <div class="one-third column">
                <a href="/logout.do"><span class="delete">Logout</span></a>
            </div>
        </div>
    </div>
</div>
<!-- end of header row -->