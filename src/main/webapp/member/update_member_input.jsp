<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<%
   MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員資料修改</title>

<style>
  table#table-1 {
    background-color: #CCCCFF;
    border: 2px solid black;
    text-align: center;
    width: 450px;
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

  .form-table {
    width: 450px;
    background-color: white;
    margin: 20px auto;
  }
  .form-table td {
    padding: 10px;
    vertical-align: middle;
  }
  .form-input {
    width: 250px;
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 4px;
  }
  .required-mark {
    color: red;
    font-weight: bold;
  }
  .submit-btn {
    background-color: #4CAF50;
    color: white;
    padding: 8px 20px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 16px;
  }
  .submit-btn:hover {
    background-color: #45a049;
  }
  .error-message {
    color: red;
    font-size: 14px;
    margin-top: 5px;
  }
</style>
</head>
<body bgcolor='white'>

<table id="table-1">
    <tr>
        <td>
            <h3>會員資料修改</h3>
            <h4><a href="select_page.jsp">回首頁</a></h4>
        </td>
    </tr>
</table>

<c:if test="${not empty errorMsgs}">
    <div style="width: 450px; margin: 20px auto;">
        <font style="color:red">請修正以下錯誤:</font>
        <ul>
            <c:forEach var="message" items="${errorMsgs}">
                <li class="error-message">${message}</li>
            </c:forEach>
        </ul>
    </div>
</c:if>

<FORM METHOD="post" ACTION="member.do" name="form1">
<table class="form-table">
    <tr>
        <td>會員編號:<span class="required-mark">*</span></td>
        <td><%=memberVO.getMemberId()%></td>
    </tr>
    <tr>
        <td>會員姓名:<span class="required-mark">*</span></td>
        <td><input type="TEXT" name="memberName" value="<%=memberVO.getMemberName()%>" 
            class="form-input" placeholder="請輸入姓名"/></td>
    </tr>
    <tr>
        <td>電子郵件:<span class="required-mark">*</span></td>
        <td><input type="EMAIL" name="memberEmail" value="<%=memberVO.getMemberEmail()%>" 
            class="form-input" placeholder="請輸入電子郵件"/></td>
    </tr>
    <tr>
        <td>密碼:<span class="required-mark">*</span></td>
        <td><input type="PASSWORD" name="memberPassword" value="<%=memberVO.getMemberPassword()%>" 
            class="form-input" placeholder="請輸入密碼"/>
            <div class="error-message">若不修改密碼請勿更動此欄位</div>
        </td>
    </tr>
    <tr>
        <td>手機號碼:<span class="required-mark">*</span></td>
        <td><input type="TEXT" name="memberPhoneNumber" value="<%=memberVO.getMemberPhoneNumber()%>" 
            class="form-input" placeholder="請輸入手機號碼"/></td>
    </tr>
    <tr>
        <td>性別:<span class="required-mark">*</span></td>
        <td>
            <input type="radio" name="memberGender" value="1" <%=(memberVO.getMemberGender() == 1)? "checked" : ""%>> 男
            <input type="radio" name="memberGender" value="0" <%=(memberVO.getMemberGender() == 0)? "checked" : ""%>> 女
        </td>
    </tr>
    <tr>
        <td>出生日期:<span class="required-mark">*</span></td>
        <td><input name="memberBirthdate" id="f_date1" type="text" class="form-input"></td>
    </tr>
    <tr>
        <td>地址:<span class="required-mark">*</span></td>
        <td><input type="TEXT" name="memberAddress" value="<%=memberVO.getMemberAddress()%>" 
            class="form-input" placeholder="請輸入地址"/></td>
    </tr>
</table>
<div style="text-align: center;">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="memberId" value="<%=memberVO.getMemberId()%>">
    <input type="submit" value="確認修改" class="submit-btn">
</div>
</FORM>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
    width: 300px;
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
    height: 151px;
  }
</style>

<script>
$.datetimepicker.setLocale('zh');
$('#f_date1').datetimepicker({
    theme: '',
    timepicker: false,
    step: 1,
    format: 'Y-m-d',
    value: '<%=memberVO.getMemberBirthdate()%>',
    maxDate: '+1970/01/01' // 限制不能選擇未來日期
});

// 前端驗證
document.form1.onsubmit = function(e) {
    let errorMsg = [];
    const email = document.form1.memberEmail.value;
    const phone = document.form1.memberPhoneNumber.value;
    const name = document.form1.memberName.value;
    
    if (name.trim().length < 2) {
        errorMsg.push("姓名長度不能小於2個字元");
    }
    
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        errorMsg.push("請輸入有效的電子郵件地址");
    }
    
    if (!/^09\d{8}$/.test(phone)) {
        errorMsg.push("請輸入有效的手機號碼（09開頭的10位數字）");
    }
    
    if (errorMsg.length > 0) {
        alert(errorMsg.join("\n"));
        return false;
    }
};
</script>
</body>
</html>