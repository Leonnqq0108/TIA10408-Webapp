package com.member.model;

import java.util.*;
import java.sql.*;

public class MemberJDBCDAO implements MemberDAO_interface {
    String driver = "com.mysql.cj.jdbc.Driver";
    String url = "jdbc:mysql://localhost:3306/memberdb?serverTimezone=Asia/Taipei";
    String userid = "root";
    String passwd = "123456";

    private static final String INSERT_STMT = 
        "INSERT INTO member (member_name, member_email, member_password, member_phone_number, member_gender, member_birthdate, member_address) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String GET_ALL_STMT = 
        "SELECT member_id, member_name, member_email, member_password, member_phone_number, member_gender, member_birthdate, member_address FROM member ORDER BY member_id";
    private static final String GET_ONE_STMT = 
        "SELECT member_id, member_name, member_email, member_password, member_phone_number, member_gender, member_birthdate, member_address FROM member WHERE member_id = ?";
    private static final String GET_BY_EMAIL = 
        "SELECT member_id, member_name, member_email, member_password, member_phone_number, member_gender, member_birthdate, member_address FROM member WHERE member_email = ?";
    private static final String GET_BY_PHONE = 
        "SELECT member_id, member_name, member_email, member_password, member_phone_number, member_gender, member_birthdate, member_address FROM member WHERE member_phone_number = ?";
    private static final String DELETE = 
        "DELETE FROM member WHERE member_id = ?";
    private static final String UPDATE = 
        "UPDATE member SET member_name=?, member_email=?, member_password=?, member_phone_number=?, member_gender=?, member_birthdate=?, member_address=? WHERE member_id = ?";

    @Override
    public void insert(MemberVO memberVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(INSERT_STMT);

            pstmt.setString(1, memberVO.getMemberName());
            pstmt.setString(2, memberVO.getMemberEmail());
            pstmt.setString(3, memberVO.getMemberPassword());
            pstmt.setString(4, memberVO.getMemberPhoneNumber());
            pstmt.setInt(5, memberVO.getMemberGender());
            pstmt.setDate(6, memberVO.getMemberBirthdate());
            pstmt.setString(7, memberVO.getMemberAddress());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            closeResources(con, pstmt, null);
        }
    }

    @Override
    public void update(MemberVO memberVO) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(UPDATE);

            pstmt.setString(1, memberVO.getMemberName());
            pstmt.setString(2, memberVO.getMemberEmail());
            pstmt.setString(3, memberVO.getMemberPassword());
            pstmt.setString(4, memberVO.getMemberPhoneNumber());
            pstmt.setInt(5, memberVO.getMemberGender());
            pstmt.setDate(6, memberVO.getMemberBirthdate());
            pstmt.setString(7, memberVO.getMemberAddress());
            pstmt.setInt(8, memberVO.getMemberId());

            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            closeResources(con, pstmt, null);
        }
    }

    @Override
    public void delete(Integer memberId) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(DELETE);

            pstmt.setInt(1, memberId);
            pstmt.executeUpdate();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            closeResources(con, pstmt, null);
        }
    }

    @Override
    public MemberVO findByPrimaryKey(Integer memberId) {
        MemberVO memberVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ONE_STMT);

            pstmt.setInt(1, memberId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                memberVO = new MemberVO();
                setMemberVOFromResultSet(memberVO, rs);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            closeResources(con, pstmt, rs);
        }
        return memberVO;
    }

    @Override
    public List<MemberVO> getAll() {
        List<MemberVO> list = new ArrayList<MemberVO>();
        MemberVO memberVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_ALL_STMT);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                memberVO = new MemberVO();
                setMemberVOFromResultSet(memberVO, rs);
                list.add(memberVO);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            closeResources(con, pstmt, rs);
        }
        return list;
    }

    @Override
    public MemberVO findByEmail(String memberEmail) {
        MemberVO memberVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_BY_EMAIL);

            pstmt.setString(1, memberEmail);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                memberVO = new MemberVO();
                setMemberVOFromResultSet(memberVO, rs);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            closeResources(con, pstmt, rs);
        }
        return memberVO;
    }

    @Override
    public MemberVO findByPhoneNumber(String memberPhoneNumber) {
        MemberVO memberVO = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, userid, passwd);
            pstmt = con.prepareStatement(GET_BY_PHONE);

            pstmt.setString(1, memberPhoneNumber);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                memberVO = new MemberVO();
                setMemberVOFromResultSet(memberVO, rs);
            }

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
        } catch (SQLException se) {
            throw new RuntimeException("A database error occurred. " + se.getMessage());
        } finally {
            closeResources(con, pstmt, rs);
        }
        return memberVO;
    }

    private void setMemberVOFromResultSet(MemberVO memberVO, ResultSet rs) throws SQLException {
        memberVO.setMemberId(rs.getInt("member_id"));
        memberVO.setMemberName(rs.getString("member_name"));
        memberVO.setMemberEmail(rs.getString("member_email"));
        memberVO.setMemberPassword(rs.getString("member_password"));
        memberVO.setMemberPhoneNumber(rs.getString("member_phone_number"));
        memberVO.setMemberGender(rs.getInt("member_gender"));
        memberVO.setMemberBirthdate(rs.getDate("member_birthdate"));
        memberVO.setMemberAddress(rs.getString("member_address"));
    }

    private void closeResources(Connection con, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace(System.err);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException se) {
                se.printStackTrace(System.err);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace(System.err);
            }
        }
    }

    public static void main(String[] args) {
        MemberJDBCDAO dao = new MemberJDBCDAO();

        // 測試新增
        MemberVO memberVO1 = new MemberVO();
        memberVO1.setMemberName("測試會員");
        memberVO1.setMemberEmail("test@test.com");
        memberVO1.setMemberPassword("password123");
        memberVO1.setMemberPhoneNumber("0912345678");
        memberVO1.setMemberGender(1);
        memberVO1.setMemberBirthdate(java.sql.Date.valueOf("1990-01-01"));
        memberVO1.setMemberAddress("測試地址123號");
        dao.insert(memberVO1);
        System.out.println("新增成功");

        // 測試修改
        MemberVO memberVO2 = new MemberVO();
        memberVO2.setMemberId(1);
        memberVO2.setMemberName("修改會員");
        memberVO2.setMemberEmail("update@test.com");
        memberVO2.setMemberPassword("newpassword");
        memberVO2.setMemberPhoneNumber("0987654321");
        memberVO2.setMemberGender(0);
        memberVO2.setMemberBirthdate(java.sql.Date.valueOf("1991-01-01"));
        memberVO2.setMemberAddress("修改地址456號");
        dao.update(memberVO2);
        System.out.println("修改成功");

        // 測試刪除
        dao.delete(2);
        System.out.println("刪除成功");

        // 測試查詢單筆
        MemberVO memberVO3 = dao.findByPrimaryKey(1);
        printMemberInfo(memberVO3);

        // 測試查詢全部
        List<MemberVO> list = dao.getAll();
        for (MemberVO aMember : list) {
            printMemberInfo(aMember);
        }
    }

    private static void printMemberInfo(MemberVO memberVO) {
        System.out.println("會員編號: " + memberVO.getMemberId());
        System.out.println("會員姓名: " + memberVO.getMemberName());
        System.out.println("電子郵件: " + memberVO.getMemberEmail());
        System.out.println("手機號碼: " + memberVO.getMemberPhoneNumber());
        System.out.println("性別: " + (memberVO.getMemberGender() == 1 ? "男" : "女"));
        System.out.println("出生日期: " + memberVO.getMemberBirthdate());
        System.out.println("地址: " + memberVO.getMemberAddress());
        System.out.println("---------------------");
    }
}