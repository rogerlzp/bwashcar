package com.bwash.bwashcar.utility;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bwash.bwashcar.activities.CouponsActivity;
import com.bwash.bwashcar.activities.CurrentAccountActivity;
import com.bwash.bwashcar.activities.FeedBackActivity;
import com.bwash.bwashcar.activities.GestureEditActivity;
import com.bwash.bwashcar.activities.MainActivity;
import com.bwash.bwashcar.activities.PartnerActivity;
import com.bwash.bwashcar.application.LTNApplication;

/**
 * Created by jiajia on 2016/1/4.
 */
public class LoginUtils {
    public static void loginJump(Context context, Bundle bundle) {
        LTNApplication.getInstance().setIsShowGesture(false);
        LTNApplication.getInstance().unLock();
        if (context == null) {
            return;
        }

        Intent intent = null;


        if (bundle == null) {
            if (LTNApplication.getInstance().getGesturePassword() == null) {
                intent = new Intent(context, GestureEditActivity.class);
                context.startActivity(intent);
            }
            return;
        }
        if (bundle.getBoolean(Constant.LoginParams.FROM_FORGET_GESTURE, false)) {
            LTNApplication.getInstance().clearGesturePassword();
        }
        String type = bundle.getString(Constant.LoginParams.LOGIN_TYPE);
        if (type.equals(Constant.LoginParams.LOGIN_TO_MYACCOUNT)) {       //登陆后跳转我的账户
            intent = new Intent(Constant.LoginParams.LOGIN_TO_MYACCOUNT);
            intent.putExtra(LTNConstants.IS_REGISTER, bundle.getBoolean(LTNConstants.IS_REGISTER, false));
            context.sendBroadcast(intent);
        } else if (type.equals(Constant.LoginParams.LOGIN_TO_COUPON)) { //登录后跳转理财金券
            intent = new Intent(context, CouponsActivity.class);
            intent.putExtra(LTNConstants.IS_REGISTER, bundle.getBoolean(LTNConstants.IS_REGISTER, false));
            context.startActivity(intent);
        } else if (type.equals(Constant.LoginParams.LOGIN_TO_FEEDBACK)) { //登录后跳转理财金券
            intent = new Intent(context, FeedBackActivity.class);
            intent.putExtra(LTNConstants.IS_REGISTER, bundle.getBoolean(LTNConstants.IS_REGISTER, false));
            context.startActivity(intent);
        } else if (type.equals(Constant.LoginParams.LOGIN_TO_TYB_PRODUCT)) { //更改体验标产品界面
            intent = new Intent(Constant.LoginParams.LOGIN_TO_TYB_PRODUCT);
            intent.putExtra(LTNConstants.IS_REGISTER, bundle.getBoolean(LTNConstants.IS_REGISTER, false));
            context.sendBroadcast(intent);
        } else if (type.equals(Constant.LoginParams.LOGIN_TO_PRODUCT)) { //更改产品界面
            intent = new Intent(Constant.LoginParams.LOGIN_TO_PRODUCT);
            intent.putExtra(LTNConstants.IS_REGISTER, bundle.getBoolean(LTNConstants.IS_REGISTER, false));
            context.sendBroadcast(intent);
        } else if (type.equals(Constant.LoginParams.LOGIN_TO_PARTNER)) { //忘记手势密码
            intent = new Intent(context, PartnerActivity.class);
            intent.putExtra(LTNConstants.IS_REGISTER, bundle.getBoolean(LTNConstants.IS_REGISTER, false));
            context.startActivity(intent);
        } else if (type.equals(Constant.LoginParams.LOGIN_TO_MAIN)) {
            intent = new Intent(context, MainActivity.class);
            intent.putExtra(LTNConstants.IS_REGISTER, bundle.getBoolean(LTNConstants.IS_REGISTER, false));
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent);
        } else if (type.equals(Constant.LoginParams.FROM_PRODUCT_LIST_SXT)) {
            intent = new Intent(context, CurrentAccountActivity.class);
            context.startActivity(intent);
        } else {
            intent = new Intent(context, MainActivity.class);
            intent.putExtra(LTNConstants.IS_REGISTER, bundle.getBoolean(LTNConstants.IS_REGISTER, false));
            context.startActivity(intent);
        }
        // 如果没有手势密码, 进入手势密码设置界面.
        if (LTNApplication.getInstance().getGesturePassword() == null) {
            intent = new Intent(context, GestureEditActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        }

    }
}
