package com.bwash.bwashcar.model;

/**
 * Created by zhengpingli on 2017/4/12.
 */

public class Company {

    private long companyId;
    private String companyNo;// 店编号
    private String companyName;// 店编号
    private int companyType;// 营业执照类型：1.企业法人； 2. 个体 3:0 没有执照
    private String lincenseName;// 营业执照名称
    private String lincenseRegNo;// 营业执照注册号
    private String lincenseImageUrl;// 营业执照图片

    private String createrId;// 创建用户的ID

    private String proprietorName;// 经营者姓名
    private String proprietorID;// 经营者身份证号
    private String contactEmailAddress;// 经营者邮箱
    private String proprietorMobile;// 经营者手机

    private String proprietorIDFrontImage;// 经营者身份证正面照
    private String proprietorIDBackImage;// 经营者身份证反面照


    private String certificationName1;// 经营者其他执照1
    private String certificationImageUrl1;// 经营者其他执照图片1

    private String certificationName2;// 经营者其他执照1
    private String certificationImageUrl2;// 经营者其他执照图片1

    private String certificationName3;// 经营者其他执照1
    private String certificationImageUrl3;// 经营者其他执照图片1

    public long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyNo() {
        return companyNo;
    }

    public void setCompanyNo(String companyNo) {
        this.companyNo = companyNo;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyType() {
        return companyType;
    }

    public void setCompanyType(int companyType) {
        this.companyType = companyType;
    }

    public String getLincenseName() {
        return lincenseName;
    }

    public void setLincenseName(String lincenseName) {
        this.lincenseName = lincenseName;
    }

    public String getLincenseRegNo() {
        return lincenseRegNo;
    }

    public void setLincenseRegNo(String lincenseRegNo) {
        this.lincenseRegNo = lincenseRegNo;
    }

    public String getLincenseImageUrl() {
        return lincenseImageUrl;
    }

    public void setLincenseImageUrl(String lincenseImageUrl) {
        this.lincenseImageUrl = lincenseImageUrl;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getProprietorName() {
        return proprietorName;
    }

    public void setProprietorName(String proprietorName) {
        this.proprietorName = proprietorName;
    }

    public String getProprietorID() {
        return proprietorID;
    }

    public void setProprietorID(String proprietorID) {
        this.proprietorID = proprietorID;
    }

    public String getContactEmailAddress() {
        return contactEmailAddress;
    }

    public void setContactEmailAddress(String contactEmailAddress) {
        this.contactEmailAddress = contactEmailAddress;
    }

    public String getProprietorMobile() {
        return proprietorMobile;
    }

    public void setProprietorMobile(String proprietorMobile) {
        this.proprietorMobile = proprietorMobile;
    }

    public String getProprietorIDFrontImage() {
        return proprietorIDFrontImage;
    }

    public void setProprietorIDFrontImage(String proprietorIDFrontImage) {
        this.proprietorIDFrontImage = proprietorIDFrontImage;
    }

    public String getProprietorIDBackImage() {
        return proprietorIDBackImage;
    }

    public void setProprietorIDBackImage(String proprietorIDBackImage) {
        this.proprietorIDBackImage = proprietorIDBackImage;
    }

    public String getCertificationName1() {
        return certificationName1;
    }

    public void setCertificationName1(String certificationName1) {
        this.certificationName1 = certificationName1;
    }

    public String getCertificationImageUrl1() {
        return certificationImageUrl1;
    }

    public void setCertificationImageUrl1(String certificationImageUrl1) {
        this.certificationImageUrl1 = certificationImageUrl1;
    }

    public String getCertificationName2() {
        return certificationName2;
    }

    public void setCertificationName2(String certificationName2) {
        this.certificationName2 = certificationName2;
    }

    public String getCertificationImageUrl2() {
        return certificationImageUrl2;
    }

    public void setCertificationImageUrl2(String certificationImageUrl2) {
        this.certificationImageUrl2 = certificationImageUrl2;
    }

    public String getCertificationName3() {
        return certificationName3;
    }

    public void setCertificationName3(String certificationName3) {
        this.certificationName3 = certificationName3;
    }

    public String getCertificationImageUrl3() {
        return certificationImageUrl3;
    }

    public void setCertificationImageUrl3(String certificationImageUrl3) {
        this.certificationImageUrl3 = certificationImageUrl3;
    }
}
