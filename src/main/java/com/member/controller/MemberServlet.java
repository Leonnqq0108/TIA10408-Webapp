package com.member.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import com.member.model.*;
import java.sql.Date;

public class MemberServlet extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        doPost(req, res);
    }
    

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String action = req.getParameter("action");

        if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            // 1.接收請求參數 - 輸入格式的錯誤處理
            String str = req.getParameter("memberId");
            if (str == null || (str.trim()).length() == 0) {
                errorMsgs.add("請輸入會員編號");
            }

            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            Integer memberId = null;
            try {
                memberId = Integer.valueOf(str);
            } catch (Exception e) {
                errorMsgs.add("會員編號格式不正確");
            }

            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            // 2.開始查詢資料
            MemberService memberSvc = new MemberService();
            MemberVO memberVO = memberSvc.getOneMember(memberId);
            if (memberVO == null) {
                errorMsgs.add("查無資料");
            }

            if (!errorMsgs.isEmpty()) {
                RequestDispatcher failureView = req.getRequestDispatcher("/member/select_page.jsp");
                failureView.forward(req, res);
                return;
            }

            // 3.查詢完成,準備轉交
            req.setAttribute("memberVO", memberVO);
            String url = "/member/listOneMember.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("getOne_For_Update".equals(action)) { // 來自listAllMember.jsp
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            // 1.接收請求參數
            Integer memberId = Integer.valueOf(req.getParameter("memberId"));

            // 2.開始查詢資料
            MemberService memberSvc = new MemberService();
            MemberVO memberVO = memberSvc.getOneMember(memberId);

            // 3.查詢完成,準備轉交
            req.setAttribute("memberVO", memberVO);
            String url = "/member/update_member_input.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("update".equals(action)) { // 來自update_member_input.jsp
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            // 1.接收請求參數 - 輸入格式的錯誤處理
            Integer memberId = Integer.valueOf(req.getParameter("memberId").trim());

            String memberName = req.getParameter("memberName");
            String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
            if (memberName == null || memberName.trim().length() == 0) {
                errorMsgs.add("會員姓名: 請勿空白");
            } else if (!memberName.trim().matches(nameReg)) {
                errorMsgs.add("會員姓名: 只能是中、英文字母, 且長度必需在2到10之間");
            }

            String memberEmail = req.getParameter("memberEmail").trim();
            String emailReg = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (memberEmail == null || memberEmail.trim().length() == 0) {
                errorMsgs.add("電子郵件: 請勿空白");
            } else if (!memberEmail.trim().matches(emailReg)) {
                errorMsgs.add("請輸入有效的電子郵件地址");
            }

            String memberPassword = req.getParameter("memberPassword").trim();
            if (memberPassword == null || memberPassword.trim().length() == 0) {
                errorMsgs.add("密碼請勿空白");
            }

            String memberPhoneNumber = req.getParameter("memberPhoneNumber").trim();
            String phoneReg = "^09\\d{8}$";
            if (memberPhoneNumber == null || memberPhoneNumber.trim().length() == 0) {
                errorMsgs.add("手機號碼: 請勿空白");
            } else if (!memberPhoneNumber.trim().matches(phoneReg)) {
                errorMsgs.add("請輸入有效的手機號碼格式");
            }

            Integer memberGender = null;
            try {
                memberGender = Integer.valueOf(req.getParameter("memberGender").trim());
                if (memberGender != 0 && memberGender != 1) {
                    errorMsgs.add("性別請選擇有效選項");
                }
            } catch (NumberFormatException e) {
                errorMsgs.add("性別請選擇有效選項");
            }

            Date memberBirthdate = null;
            try {
                memberBirthdate = Date.valueOf(req.getParameter("memberBirthdate").trim());
            } catch (IllegalArgumentException e) {
                memberBirthdate = new Date(System.currentTimeMillis());
                errorMsgs.add("請輸入日期!");
            }

            String memberAddress = req.getParameter("memberAddress").trim();
            if (memberAddress == null || memberAddress.trim().length() == 0) {
                errorMsgs.add("地址請勿空白");
            }

            MemberVO memberVO = new MemberVO();
            memberVO.setMemberId(memberId);
            memberVO.setMemberName(memberName);
            memberVO.setMemberEmail(memberEmail);
            memberVO.setMemberPassword(memberPassword);
            memberVO.setMemberPhoneNumber(memberPhoneNumber);
            memberVO.setMemberGender(memberGender);
            memberVO.setMemberBirthdate(memberBirthdate);
            memberVO.setMemberAddress(memberAddress);

            if (!errorMsgs.isEmpty()) {
                req.setAttribute("memberVO", memberVO);
                RequestDispatcher failureView = req.getRequestDispatcher("/member/update_member_input.jsp");
                failureView.forward(req, res);
                return;
            }

            // 2.開始修改資料
            MemberService memberSvc = new MemberService();
            try {
                memberVO = memberSvc.updateMember(memberId, memberName, memberEmail, memberPassword,
                        memberPhoneNumber, memberGender, memberBirthdate, memberAddress);
            } catch (RuntimeException e) {
                errorMsgs.add("修改資料失敗:" + e.getMessage());
                req.setAttribute("memberVO", memberVO);
                RequestDispatcher failureView = req.getRequestDispatcher("/member/update_member_input.jsp");
                failureView.forward(req, res);
                return;
            }

            // 3.修改完成,準備轉交
            req.setAttribute("memberVO", memberVO);
            String url = "/member/listOneMember.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("insert".equals(action)) { // 來自addMember.jsp
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            // 1.接收請求參數 - 輸入格式的錯誤處理
            String memberName = req.getParameter("memberName");
            String nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z)]{2,10}$";
            if (memberName == null || memberName.trim().length() == 0) {
                errorMsgs.add("會員姓名: 請勿空白");
            } else if (!memberName.trim().matches(nameReg)) {
                errorMsgs.add("會員姓名: 只能是中、英文字母, 且長度必需在2到10之間");
            }

            String memberEmail = req.getParameter("memberEmail").trim();
            String emailReg = "^[A-Za-z0-9+_.-]+@(.+)$";
            if (memberEmail == null || memberEmail.trim().length() == 0) {
                errorMsgs.add("電子郵件: 請勿空白");
            } else if (!memberEmail.trim().matches(emailReg)) {
                errorMsgs.add("請輸入有效的電子郵件地址");
            }

            String memberPassword = req.getParameter("memberPassword").trim();
            if (memberPassword == null || memberPassword.trim().length() == 0) {
                errorMsgs.add("密碼請勿空白");
            }

            String memberPhoneNumber = req.getParameter("memberPhoneNumber").trim();
            String phoneReg = "^09\\d{8}$";
            if (memberPhoneNumber == null || memberPhoneNumber.trim().length() == 0) {
                errorMsgs.add("手機號碼: 請勿空白");
            } else if (!memberPhoneNumber.trim().matches(phoneReg)) {
                errorMsgs.add("請輸入有效的手機號碼格式");
            }

            Integer memberGender = null;
            try {
                memberGender = Integer.valueOf(req.getParameter("memberGender").trim());
                if (memberGender != 0 && memberGender != 1) {
                    errorMsgs.add("性別請選擇有效選項");
                }
            } catch (NumberFormatException e) {
                errorMsgs.add("性別請選擇有效選項");
            }

            Date memberBirthdate = null;
            try {
                memberBirthdate = Date.valueOf(req.getParameter("memberBirthdate").trim());
            } catch (IllegalArgumentException e) {
                memberBirthdate = new Date(System.currentTimeMillis());
                errorMsgs.add("請輸入日期!");
            }

            String memberAddress = req.getParameter("memberAddress").trim();
            if (memberAddress == null || memberAddress.trim().length() == 0) {
                errorMsgs.add("地址請勿空白");
            }

            MemberVO memberVO = new MemberVO();
            memberVO.setMemberName(memberName);
            memberVO.setMemberEmail(memberEmail);
            memberVO.setMemberPassword(memberPassword);
            memberVO.setMemberPhoneNumber(memberPhoneNumber);
            memberVO.setMemberGender(memberGender);
            memberVO.setMemberBirthdate(memberBirthdate);
            memberVO.setMemberAddress(memberAddress);

            if (!errorMsgs.isEmpty()) {
                req.setAttribute("memberVO", memberVO);
                RequestDispatcher failureView = req.getRequestDispatcher("/member/addMember.jsp");
                failureView.forward(req, res);
                return;
            }

            // 2.開始新增資料
            MemberService memberSvc = new MemberService();
            try {
                memberVO = memberSvc.addMember(memberName, memberEmail, memberPassword,
                        memberPhoneNumber, memberGender, memberBirthdate, memberAddress);
            } catch (RuntimeException e) {
                errorMsgs.add("新增資料失敗:" + e.getMessage());
                req.setAttribute("memberVO", memberVO);
                RequestDispatcher failureView = req.getRequestDispatcher("/member/addMember.jsp");
                failureView.forward(req, res);
                return;
            }

            // 3.新增完成,準備轉交
            String url = "/member/listAllMember.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
        }

        if ("delete".equals(action)) { // 來自listAllMember.jsp
            List<String> errorMsgs = new LinkedList<String>();
            req.setAttribute("errorMsgs", errorMsgs);

            // 1.接收請求參數
            Integer memberId = Integer.valueOf(req.getParameter("memberId"));

            // 2.開始刪除資料
            MemberService memberSvc = new MemberService();
            try {
                memberSvc.deleteMember(memberId);
            } catch (RuntimeException e) {
                errorMsgs.add("刪除資料失敗:" + e.getMessage());
                RequestDispatcher failureView = req.getRequestDispatcher("/member/listAllMember.jsp");
                failureView.forward(req, res);
                return;
            }

            // 3.刪除完成,準備轉交
            String url = "/member/listAllMember.jsp";
            RequestDispatcher successView = req.getRequestDispatcher(url);
            successView.forward(req, res);
            
            
            
            
        }
    }
}

