<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.member.model.*"%>

<%
    MemberService memberSvc = new MemberService();
    List<MemberVO> list = memberSvc.getAll();
    pageContext.setAttribute("list", list);
%>

<html>
<head>
<title>會員管理系統 - 會員列表</title>

<style>
table#table-1 {
    background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
    width: 1000px;
    margin: 20px auto;
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

.member-table {
    width: 1000px;
    background-color: white;
    margin: 20px auto;
    border-collapse: collapse;
}

.member-table, .member-table th, .member-table td {
    border: 1px solid #CCCCFF;
    padding: 8px;
}

.member-table th {
    background-color: #f2f2f2;
    color: #333;
    font-weight: bold;
}

.member-table tr:nth-child(even) {
    background-color: #f9f9f9;
}

.member-table tr:hover {
    background-color: #f5f5f5;
}

.action-button {
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 14px;
    margin: 2px;
}

.edit-button {
    background-color: #4CAF50;
    color: white;
}

.delete-button {
    background-color: #f44336;
    color: white;
}

.pagination {
    margin: 20px auto;
    text-align: center;
}

.pagination a {
    color: black;
    padding: 8px 16px;
    text-decoration: none;
    border: 1px solid #ddd;
    margin: 0 4px;
}

.pagination a.active {
    background-color: #4CAF50;
    color: white;
    border: 1px solid #4CAF50;
}

.pagination a:hover:not(.active) {
    background-color: #ddd;
}
</style>
</head>
<body>

<table id="table-1">
    <tr>
        <td>
            <h3>會員管理系統 - 會員列表</h3>
            <h4><a href="select_page.jsp">回首頁</a></h4>
        </td>
    </tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
    <font style="color:red">請修正以下錯誤:</font>
    <ul>
        <c:forEach var="message" items="${errorMsgs}">
            <li style="color:red">${message}</li>
        </c:forEach>
    </ul>
</c:if>

<table class="member-table">
    <tr>
        <th>會員編號</th>
        <th>姓名</th>
        <th>電子郵件</th>
        <th>手機號碼</th>
        <th>性別</th>
        <th>出生日期</th>
        <th>地址</th>
        <th>操作</th>
    </tr>
    <%@ include file="page1.file" %> 
<c:forEach var="memberVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">        <tr>
            <td>${memberVO.memberId}</td>
            <td>${memberVO.memberName}</td>
            <td>${memberVO.memberEmail}</td>
            <td>${memberVO.memberPhoneNumber}</td>
            <td>${memberVO.memberGender == 1 ? '男' : '女'}</td>
            <td>${memberVO.memberBirthdate}</td>
            <td>${memberVO.memberAddress}</td>
            <td>
                <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="display:inline;">
                    <input type="submit" value="修改" class="action-button edit-button">
                    <input type="hidden" name="memberId" value="${memberVO.memberId}">
                    <input type="hidden" name="action" value="getOne_For_Update">
                </FORM>
                <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/member/member.do" style="display:inline;">
                    <input type="submit" value="刪除" class="action-button delete-button"
                           onclick="return confirm('確定要刪除此會員資料嗎？');">
                    <input type="hidden" name="memberId" value="${memberVO.memberId}">
                    <input type="hidden" name="action" value="delete">
                </FORM>
            </td>
        </tr>
    </c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>