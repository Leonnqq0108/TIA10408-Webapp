package com.member.model;

import java.sql.Date;
import java.util.List;

public class MemberService {
    private MemberDAO_interface dao;

    public MemberService() {
        dao = new MemberJDBCDAO();
    }

    /**
     * 新增會員
     */
    public MemberVO addMember(String memberName, String memberEmail, String memberPassword,
                            String memberPhoneNumber, Integer memberGender, Date memberBirthdate,
                            String memberAddress) {
        MemberVO memberVO = new MemberVO();
        memberVO.setMemberName(memberName);
        memberVO.setMemberEmail(memberEmail);
        memberVO.setMemberPassword(memberPassword);
        memberVO.setMemberPhoneNumber(memberPhoneNumber);
        memberVO.setMemberGender(memberGender);
        memberVO.setMemberBirthdate(memberBirthdate);
        memberVO.setMemberAddress(memberAddress);

        // 檢查電子郵件是否已存在
        if (dao.findByEmail(memberEmail) != null) {
            throw new RuntimeException("此電子郵件已被註冊");
        }

        // 檢查手機號碼是否已存在
        if (dao.findByPhoneNumber(memberPhoneNumber) != null) {
            throw new RuntimeException("此手機號碼已被註冊");
        }

        dao.insert(memberVO);
        return memberVO;
    }

    /**
     * 修改會員資料
     */
    public MemberVO updateMember(Integer memberId, String memberName, String memberEmail,
                               String memberPassword, String memberPhoneNumber, Integer memberGender,
                               Date memberBirthdate, String memberAddress) {
        MemberVO memberVO = new MemberVO();
        memberVO.setMemberId(memberId);
        memberVO.setMemberName(memberName);
        memberVO.setMemberEmail(memberEmail);
        memberVO.setMemberPassword(memberPassword);
        memberVO.setMemberPhoneNumber(memberPhoneNumber);
        memberVO.setMemberGender(memberGender);
        memberVO.setMemberBirthdate(memberBirthdate);
        memberVO.setMemberAddress(memberAddress);

        // 檢查會員是否存在
        MemberVO existingMember = dao.findByPrimaryKey(memberId);
        if (existingMember == null) {
            throw new RuntimeException("找不到此會員");
        }

        // 檢查電子郵件是否重複（排除當前會員）
        MemberVO emailCheck = dao.findByEmail(memberEmail);
        if (emailCheck != null && !emailCheck.getMemberId().equals(memberId)) {
            throw new RuntimeException("此電子郵件已被其他會員使用");
        }

        // 檢查手機號碼是否重複（排除當前會員）
        MemberVO phoneCheck = dao.findByPhoneNumber(memberPhoneNumber);
        if (phoneCheck != null && !phoneCheck.getMemberId().equals(memberId)) {
            throw new RuntimeException("此手機號碼已被其他會員使用");
        }

        dao.update(memberVO);
        return memberVO;
    }

    /**
     * 刪除會員
     */
    public void deleteMember(Integer memberId) {
        // 檢查會員是否存在
        MemberVO existingMember = dao.findByPrimaryKey(memberId);
        if (existingMember == null) {
            throw new RuntimeException("找不到此會員");
        }
        dao.delete(memberId);
    }

    /**
     * 查詢單一會員
     */
    public MemberVO getOneMember(Integer memberId) {
        return dao.findByPrimaryKey(memberId);
    }

    /**
     * 查詢所有會員
     */
    public List<MemberVO> getAll() {
        return dao.getAll();
    }

    /**
     * 透過電子郵件查詢會員
     */
    public MemberVO findMemberByEmail(String memberEmail) {
        return dao.findByEmail(memberEmail);
    }

    /**
     * 透過手機號碼查詢會員
     */
    public MemberVO findMemberByPhoneNumber(String memberPhoneNumber) {
        return dao.findByPhoneNumber(memberPhoneNumber);
    }

    /**
     * 驗證電子郵件是否可用
     */
    public boolean verifyMemberEmail(String memberEmail) {
        return dao.findByEmail(memberEmail) == null;
    }

    /**
     * 驗證手機號碼是否可用
     */
    public boolean verifyMemberPhoneNumber(String memberPhoneNumber) {
        return dao.findByPhoneNumber(memberPhoneNumber) == null;
    }
}