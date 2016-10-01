<c:if test="${empty previousPage}">
    <span class="u-unvailable icon-left-open">previous page</span> |
</c:if>
<c:if test="${not empty previousPage}">
    <a href="${previousPage}" class="icon-left-open">previous page</a> |
</c:if>
<c:if test="${empty nextPage}">
    <span class="u-unvailable icon-right-open">next page</span>
</c:if>
<c:if test="${not empty nextPage}">
    <a href="${nextPage}" class="icon-right-open">next page</a>
</c:if>