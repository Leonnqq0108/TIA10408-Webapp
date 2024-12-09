<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.member.model.*"%>

<%
    MemberVO memberVO = (MemberVO) request.getAttribute("memberVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員資料新增</title>

<style>
  table#table-1 {
    background-color: #CCCCFF;
    border: 2px solid black;
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

  table {
    width: 450px;
    background-color: white;
    margin-top: 1px;
    margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 8px;
  }
  .form-input {
    width: 250px;
    padding: 5px;
    border-radius: 4px;
    border: 1px solid #ccc;
  }
  .required {
    color: red;
    font-weight: bold;
  }
</style>
</head>
<body bgcolor='white'>

<table id="table-1">
    <tr>
        <td><h3>會員資料新增</h3></td>
        <td>
            <h4><a href="select_page.jsp">回首頁</a></h4>
        </td>
    </tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
    <font style="color:red">請修正以下錯誤:</font>
    <ul>
        <c:forEach var="message" items="${errorMsgs}">
            <li style="color:red">${message}</li>
        </c:forEach>
    </ul>
</c:if>

<FORM METHOD="post" ACTION="member.do" name="form1">
<table>
    <tr>
        <td>會員姓名:<span class="required">*</span></td>
        <td><input type="TEXT" name="memberName" value="<%= (memberVO==null)? "" : memberVO.getMemberName()%>" 
            class="form-input" placeholder="請輸入姓名"/></td>
    </tr>
    
    <tr>
        <td>電子郵件:<span class="required">*</span></td>
        <td><input type="EMAIL" name="memberEmail" value="<%= (memberVO==null)? "" : memberVO.getMemberEmail()%>" 
            class="form-input" placeholder="請輸入電子郵件"/></td>
    </tr>
    
    <tr>
        <td>密碼:<span class="required">*</span></td>
        <td><input type="PASSWORD" name="memberPassword" 
            class="form-input" placeholder="請輸入密碼"/></td>
    </tr>
    
    <tr>
        <td>手機號碼:<span class="required">*</span></td>
        <td><input type="TEXT" name="memberPhoneNumber" value="<%= (memberVO==null)? "" : memberVO.getMemberPhoneNumber()%>" 
            class="form-input" placeholder="請輸入手機號碼"/></td>
    </tr>
    
    <tr>
        <td>性別:<span class="required">*</span></td>
        <td>
            <input type="radio" name="memberGender" value="1" <%= (memberVO!=null && memberVO.getMemberGender()==1)? "checked" : "" %>> 男
            <input type="radio" name="memberGender" value="0" <%= (memberVO!=null && memberVO.getMemberGender()==0)? "checked" : "" %>> 女
        </td>
    </tr>
    
    <tr>
        <td>出生日期:<span class="required">*</span></td>
        <td><input name="memberBirthdate" id="f_date1" type="text" class="form-input"></td>
    </tr>
    
    <tr>
        <td>地址:<span class="required">*</span></td>
        <td><input type="TEXT" name="memberAddress" value="<%= (memberVO==null)? "" : memberVO.getMemberAddress()%>" 
            class="form-input" placeholder="請輸入地址"/></td>
    </tr>
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增" style="padding: 5px 15px; background-color: #4CAF50; color: white; border: none; border-radius: 4px; cursor: pointer;">
</FORM>

<!-- 日期選擇器設定 -->
<% 
  java.sql.Date memberBirthdate = null;
  try {
       memberBirthdate = memberVO.getMemberBirthdate();
   } catch (Exception e) {
       memberBirthdate = new java.sql.Date(System.currentTimeMillis());
   }
%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;
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
    value: '<%=memberBirthdate%>',
    // 設定最大日期為今天（不能選擇未來日期）
    maxDate: '+1970/01/01'
});

// 表單驗證
document.form1.onsubmit = function(e) {
    let errorMsg = [];
    const email = document.form1.memberEmail.value;
    const phone = document.form1.memberPhoneNumber.value;
    const password = document.form1.memberPassword.value;

    // 電子郵件格式驗證
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        errorMsg.push("電子郵件格式不正確");
    }

    // 手機號碼格式驗證
    if (!/^09\d{8}$/.test(phone)) {
        errorMsg.push("手機號碼格式不正確（需為09開頭的10位數字）");
    }

    // 密碼長度驗證
    if (password.length < 6) {
        errorMsg.push("密碼長度至少需要6個字元");
    }

    if (errorMsg.length > 0) {
        alert(errorMsg.join("\n"));
        return false;
    }
};
</script>
</body>
</html>