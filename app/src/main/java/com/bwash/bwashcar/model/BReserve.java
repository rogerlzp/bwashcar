package com.bwash.bwashcar.model;

import android.widget.ImageView;
import android.widget.TextView;

import com.bwash.bwashcar.R;

import java.util.List;

/**
 * Created by zhengpingli on 2017/4/13.
 */

public class BReserve {


    private long shopId;  //店名
    private String shopName;  //店名

    private String customerId;  //用户ID
    private String customerName;  //称呼，姓名
    private String customerPhone; //联系方式
    private String customerImageUrl; //用户头像
    private int customerCredit; //用户信用积分

    private String carId;  //车子ID
    private String carName; //车子种类

    private String reserveNo;  //预订编号

    private String reserveServiceName; //预订服务名称
    private String reserveServiceTime; //预订时间
    private String reserveCreateTime; //预订创建时间
    private double totalAmount;   //服务费用

    private short status; //0:新建； 1:商家确认； 2:到店 3：过期
    private short isFirstReserve = 0; //1是首单，0:非首单

    private List<ShopService> reserveProductList;

    public List<ShopService> getReserveProductList() {
        return reserveProductList;
    }

    public void setReserveProductList(List<ShopService> reserveProductList) {
        this.reserveProductList = reserveProductList;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getCustomerCredit() {
        return customerCredit;
    }

    public void setCustomerCredit(int customerCredit) {
        this.customerCredit = customerCredit;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public long getShopId() {
        return shopId;
    }

    public void setShopId(long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getReserveNo() {
        return reserveNo;
    }

    public void setReserveNo(String reserveNo) {
        this.reserveNo = reserveNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }


    public String getReserveServiceName() {
        return reserveServiceName;
    }

    public void setReserveServiceName(String reserveServiceName) {
        this.reserveServiceName = reserveServiceName;
    }

    public String getReserveServiceTime() {
        return reserveServiceTime;
    }

    public void setReserveServiceTime(String reserveServiceTime) {
        this.reserveServiceTime = reserveServiceTime;
    }

    public String getReserveCreateTime() {
        return reserveCreateTime;
    }

    public void setReserveCreateTime(String reserveCreateTime) {
        this.reserveCreateTime = reserveCreateTime;
    }

    public String getCustomerImageUrl() {
        return customerImageUrl;
    }

    public void setCustomerImageUrl(String customerImageUrl) {
        this.customerImageUrl = customerImageUrl;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    public short getIsFirstReserve() {
        return isFirstReserve;
    }

    public void setIsFirstReserve(short isFirstReserve) {
        this.isFirstReserve = isFirstReserve;
    }
}
