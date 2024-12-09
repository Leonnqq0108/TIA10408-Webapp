<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>

<html>
<head>
<title>會員管理系統</title>

<style>
table#table-1 {
    width: 450px;
    background-color: #CCCCFF;
    margin-top: 5px;
    margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
    text-align: center;
}

table#table-1 h4 {
    color: red;
    display: block;
    margin-bottom: 1px;
}

h4 {
    color: blue;
    display: inline;
}

.search-block {
    margin: 20px 0;
    padding: 15px;
    border: 1px solid #ccc;
    border-radius: 5px;
}

.search-block label {
    display: inline-block;
    width: 150px;
    margin-bottom: 10px;
}

.search-block input[type="text"] {
    padding: 5px;
    border-radius: 3px;
    border: 1px solid #ccc;
}

.search-block input[type="submit"] {
    padding: 5px 15px;
    background-color: #4CAF50;
    color: white;
    border: none;
    border-radius: 3px;
    cursor: pointer;
}

.search-block select {
    padding: 5px;
    border-radius: 3px;
    min-width: 200px;
}
</style>

</head>
<body bgcolor="white">

<table id="table-1">
    <tr><td>
        <h3>會員管理系統</h3>
        <h4>( MVC )</h4>
    </td></tr>
</table>

<h3>資料查詢</h3>

<%-- 錯誤訊息顯示 --%>
<c:if test="${not empty errorMsgs}">
    <font style="color:red">請修正以下錯誤:</font>
    <ul>
        <c:forEach var="message" items="${errorMsgs}">
            <li style="color:red">${message}</li>
        </c:forEach>
    </ul>
</c:if>

<div class="search-block">
    <ul>
        <li><a href="listAllMember.jsp">查看所有會員</a></li>

        <li>
            <form method="post" action="<%=request.getContextPath()%>/member/member.do">
                <label>輸入會員編號:</label>
                <input type="text" name="memberId" placeholder="請輸入會員編號">
                <input type="hidden" name="action" value="getOne_For_Display">
                <input type="submit" value="查詢">
            </form>
        </li>

        <jsp:useBean id="memberSvc" scope="page" class="com.member.model.MemberService" />

        <li>
            <form method="post" action="<%=request.getContextPath()%>/member/member.do">
                <label>選擇會員姓名:</label>
                <select name="memberId">
                    <c:forEach var="memberVO" items="${memberSvc.all}">
                        <option value="${memberVO.memberId}">${memberVO.memberName}</option>
                    </c:forEach>
                </select>
                <input type="hidden" name="action" value="getOne_For_Display">
                <input type="submit" value="查詢">
            </form>
        </li>

        <li>
            <form method="post" action="<%=request.getContextPath()%>/member/member.do">
                <label>搜尋電子郵件:</label>
                <input type="text" name="memberEmail" placeholder="請輸入電子郵件">
                <input type="hidden" name="action" value="getOne_By_Email">
                <input type="submit" value="查詢">
            </form>
        </li>
    </ul>
</div>

<h3>會員管理</h3>
<div class="search-block">
    <ul>
        <li><a href='addMember.jsp'>新增會員資料</a></li>
    </ul>
</div>

<script>
document.querySelectorAll('form').forEach(form => {
    form.onsubmit = function(e) {
        const inputs = this.querySelectorAll('input[type="text"]');
        let valid = true;

        inputs.forEach(input => {
            if (input.value.trim() === '') {
                alert('請填寫所有欄位');
                valid = false;
            }
        });

        return valid;
    }
});
</script>

</body>
</html>