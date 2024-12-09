package com.member.model;

import java.util.List;

public interface MemberDAO_interface {
    /**
     * 新增會員資料
     * @param memberVO 會員資料值物件
     */
    public void insert(MemberVO memberVO);
    
    /**
     * 修改會員資料
     * @param memberVO 會員資料值物件
     */
    public void update(MemberVO memberVO);
    
    /**
     * 刪除會員資料
     * @param memberId 會員編號
     */
    public void delete(Integer memberId);
    
    /**
     * 透過會員編號查詢單筆會員資料
     * @param memberId 會員編號
     * @return 會員資料值物件
     */
    public MemberVO findByPrimaryKey(Integer memberId);
    
    /**
     * 查詢所有會員資料
     * @return 會員資料值物件列表
     */
    public List<MemberVO> getAll();
    
    /**
     * 透過信箱查詢會員資料（用於確認信箱是否已被使用）
     * @param memberEmail 會員信箱
     * @return 會員資料值物件
     */
    public MemberVO findByEmail(String memberEmail);
    
    /**
     * 透過手機號碼查詢會員資料（用於確認手機號碼是否已被使用）
     * @param memberPhoneNumber 會員手機號碼
     * @return 會員資料值物件
     */
    public MemberVO findByPhoneNumber(String memberPhoneNumber);
}