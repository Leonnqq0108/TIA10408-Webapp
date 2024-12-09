<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.member.model.*"%>

<%
MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<html>
<head>
<title>會員詳細資料</title>

<style>
table#table-1 {
    background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
    width: 800px;
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

.data-table {
    width: 800px;
    background-color: white;
    margin: 20px auto;
    border-collapse: collapse;
}

.data-table, .data-table th, .data-table td {
    border: 1px solid #CCCCFF;
    padding: 8px;
}

.data-table th {
    background-color: #f2f2f2;
    color: #333;
    font-weight: bold;
}

.data-table td {
    text-align: left;
    line-height: 1.5;
}

.back-link {
    text-decoration: none;
    color: #333;
    display: inline-block;
    padding: 5px 10px;
    background-color: #f8f9fa;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.back-link:hover {
    background-color: #e9ecef;
}

.label-column {
    width: 150px;
    background-color: #f8f9fa;
}
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
    <tr>
        <td>
            <h3>會員詳細資料</h3>
            <h4><a href="select_page.jsp" class="back-link">回首頁</a></h4>
        </td>
    </tr>
</table>

<table class="data-table">
    <tr>
        <td class="label-column">會員編號：</td>
        <td><%=memberVO.getMemberId()%></td>
    </tr>
    <tr>
        <td class="label-column">會員姓名：</td>
        <td><%=memberVO.getMemberName()%></td>
    </tr>
    <tr>
        <td class="label-column">電子郵件：</td>
        <td><%=memberVO.getMemberEmail()%></td>
    </tr>
    <tr>
        <td class="label-column">手機號碼：</td>
        <td><%=memberVO.getMemberPhoneNumber()%></td>
    </tr>
    <tr>
        <td class="label-column">性別：</td>
        <td><%=memberVO.getMemberGender() == 1 ? "男" : "女"%></td>
    </tr>
    <tr>
        <td class="label-column">出生日期：</td>
        <td><%=memberVO.getMemberBirthdate()%></td>
    </tr>
    <tr>
        <td class="label-column">地址：</td>
        <td><%=memberVO.getMemberAddress()%></td>
    </tr>
</table>

<div style="text-align: center; margin-top: 20px;">
    <a href="member.do?action=getOne_For_Update&memberId=<%=memberVO.getMemberId()%>" class="back-link">修改資料</a>
</div>

</body>
</html>