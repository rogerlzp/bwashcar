package com.bwash.bwashcar.utility;

/**
 * Created by rogerlzp on 15/11/23.
 */
public class LTNConstants {
    public static String LTN_CONFIG = "LTN_CONFIG";
    public static String FIRST_LOGIN_FLAG = "FIRST_LOGIN";
    public static String NOTIFICATION_RECEIVER = "notification_receiver";
    public static String NOTIFICATION_SOUND = "notification_sound";
    public static String NOTIFICATION_VIBRATE = "notification_vibrate";
    public static String USER_MOBILE = "user_mobile";
    public static String IS_LOCK = "is_lock";
    public static String LOCK_TIME = "lock_time";
    public static String IS_REGISTER = "is_register";
    public static String HAS_CHECKED_CURRENT = "has_checked_current";
    public static String REGISTER_FORM_LOGIN = "fromLogin";
    public static String PACKAGE_NAME = "com.wy.lingtouniao";
    public static String AGREEMENT_CZ = "agreementCZ";
    public static String LIST = "list";
    public static String BANK_LIST = "bank_list";

    public static String FROM_JPUSH = "from_jpush";

    public static String private_key = "private_key";
    public static String private_value = "ltn$%^qpdhTH18";

    public static String SHOW_MONEY = "show_money";

    public static String JUMP_AFTER_DEPOSIT = "is_jump";

    // 首页砸金蛋活动
    public static String DT_URL = "dtUrl";
    public static String HAS_SHOWN = "isHasShown";
    public static String IS_ALLOW_SHOWN = "isAllowShown";
    public static String ALLOW_SHOWN_FALSE = "false";
    public static String HAS_SHOWN_FALSE = "false";

    // TODO: 和后台确定
    public static String JPUSH_REG_ID = "registration-id";

    //客服电话
    public static String PHONE_NUMBER = "400-999-9980";

    // 免密支付
    public static String AGREEMENT_TYPE = "agreement_type";
    public static String UNBIND = "unbind";
    public static String AGREEMENT_TYPE_TZ = "ZTBB0G00";
    public static String AGREEMENT_TYPE_CZ = "ZCZP0800";


    public static class ACCESS_URL {
        //       public static String HOST = "http://192.168.18.112:8080";
        public static String HOST = "http://121.42.145.216:8080";
//        public static String HOST = "https://www.lingtouniao.com/v1"; // 正是环境
        //       public static String HOST = "http://120.55.184.234/v1";


        public static String IMAGE_HOST = HOST;


        public static String REGISTER_URL = HOST + "/buser/register/registerUser";
        public static String VERIFY_CODE_URL = HOST + "/mobile/mobilecode/getMobileCode";
        public static String VERIFY_MOBILE_CODE_URL = HOST + "/mobile/mobilecode/verifyMobileCode";

        public static String PICTURE_CODE_HOST = HOST + "/buser/register/pictureCode";
        public static String LOGIN_CODE_URL = HOST + "/buser/login/login";
        public static String RESET_PASSWORD_URL = HOST + "/buser/login/editPwd";
        public static String RETRIEVE_PASSWORD_URL = HOST + "/buser/login/retrievePwd";
        // 实名认证
        public static String AUTH_USER_URL = HOST + "/buser/userAuth";

        // 预购买页面
        public static String PRODUCT_PREBUY_URL = HOST + "/buser/order/orderPrepare";

        // 确认购买页面
        public static String PRODUCT_BUY_URL = HOST + "/bproduct/buy/confirm";

        //产品列表
        public static String GET_PRODUCTS_URL = HOST + "/bproduct/lists";


        // 绑定银行卡
        public static String BIND_BANK_CARD_URL = HOST + "/buser/bindBankCard";

        // 换绑银行卡
        public static String CHANGE_BANK_CARD_URL = HOST + "/buser/replaceBankCard";

        // 首页
        public static String HOMEPAGE_URL = HOST + "/bhomepage/recommend";

        // 充值
        public static String DEPOSIT_URL = HOST + "/buser/recharge";

        //
        public static String WITHDRAW_URL = HOST + "/buser/withdrawals";


        // 我的理财金券
        public static String MY_COUPON_URL = HOST + "/buser/account/myFinancialCoupon";

        // 我的账户
        public static String MY_ACCOUNT_URL = HOST + "/buser/account/myAccount";

        // 我的账户 账户信息
        public static String MY_ACCOUNT_INFO_URL = HOST + "/buser/account/userInfo";

        // 我的账户 用户信息
        public static String MY_USER_INFO_URL = HOST + "/buser/userInfo";

        // 获取银行列表
        public static String BANK_LIST = HOST + "/bank/bankList";


        // 余额明细
        public static String BALANCE_LIST_URL = HOST + "/baccount/balanceDetail";
        // 鸟币明细
        public static String BIRDCOINAMOUNT = HOST + "/buser/account/birdCoinAmount";
        // 总资产
        public static String TOTAL_ACCTOUNT_URL = HOST + "/buser/account/totalaccount";
        //收益明细
        public static String INCOME_DETAIL_URL = HOST + "/buser/uncRevenue";
        //我的投资
        public static String MY_INVEST_URL = HOST + "/buser/account/myInvestment";

        // 购买记录
        public static String PURCHASE_RECORD_URL = HOST + "/bproduct/purchasehistory";

        // 购买记录
        public static String PURCHASE_DETAIL_URL = HOST + "/bproductDetail";
        // 合伙人
        public static String PARTNER_REWARD = HOST + "/buser/partner";
        //理财金券
        public static String COUPON_LIST = HOST + "/buser/account/myFinancialCoupon";
        //好友统计
        public static String FRIENDS_COUNT = HOST + "/buser/account/friends";
        //补充合伙人
        public static String REPLENISH = HOST + "/buser/partner/replenish";
        //意见反馈
        public static String FEED_BACK = HOST + "/buser/aboutus/feedback";

        //购买记录
        public static String BUY_LIST = HOST + "/bproduct/purchasehistory";
        //我的随心投
        public static String CURRENT_AMOUNT_URL = HOST + "/bproduct/current/homepage";

        // 购买随心投
        public static String PRODUCT_CURRENT_BUY = HOST + "/bproduct/current/buy";

        public static String PRODUCT_CURRENT_EXTRACT = HOST + "/bproduct/current/extract";


        // h5 页面
        // 安全保障
        public static String H5_INSURANCE_URL = HOST + "/h5/insurance.html";

        // 首页风险备用金
        public static String H5_RESERVE_FUND_URL = HOST + "/h5/reservefund.html";

        // 关于领头鸟理财
        public static String H5_ABOUT_URL = HOST + "/h5/about.html";
        // 投资协议
        public static String H5_BUY_HTML = HOST + "/h5/about.html";
        //合伙人奖励规则
        public static String H5_REWARD_RULE_URL = HOST + "/h5/rewardrule.html";

        // 同意协议书
        public static String H5_ACCEPT_RULE_URL = HOST + "/h5/accept.html";

        // 产品分享链接
        public static String H5_PRODUCT_SHARE_URL = HOST + "/h5/share.html";

        // 首页信托
        public static String H5_PROFIT_URL = HOST + "/h5/profit.html";

        // 用户注册 在线服务协议
        public static String H5_REGISTER_URL = HOST + "/h5/accept-register.html";

        // 身份认证 资金托管协议 & 个人用户授权协议
        public static String H5_AUTH_USER_URL = HOST + "/h5/accept-id.html";

        // 绑定银行卡 支付服务协议 & 快捷支付用户协议
        public static String H5_BINDCARD_URL = HOST + "/h5/accept-bind.html";

        // 银行卡充值限额
        public static String BANK_LIMIT_URL = HOST + "/bbank/limitAmount";

        // 银行卡列表
        public static String BANK_LIST_URL = HOST + "/bbank/bankList";


        // share  host log
        public static String SHARE_LOG_URL = HOST + "/logo/logo.png";
        // 检测更新
        public static String CHECK_UPDATA = "https://wwww.lingtouniao.com/app/global/checkupdate";
        //
        public static String PARTER_EARNINGS = HOST + "/buser/partner/earnings";


        //我的投资
        public static String MY_REWARD_URL = HOST + "/buser/partner/earnings";

        //随心投详情
        public static String MY_CURRENT_INCOMELIST_URL = HOST + "/buser/current/incomeList";

        // 随心投投资协议
        public static String H5_CURRENT_AGREEMENT_URL = HOST + "/h5/project-accept-current.html";


        // TODO: 更新协议
        // 免密充值协议
        public static String H5_CZ_AGREEMENT_URL = HOST + "/h5/project-accept-current.html";
        // 免密投资协议
        public static String H5_TZ_AGREEMENT_URL = HOST + "/h5/project-accept-current.html";

        // 免密协议 接口
        public static String MIANMI_AGREEMENT_URL = HOST + "/buser/agreement";

        // 安心投接口
        public static String ANXINTOU_URL = HOST + "/bproduct/findOfflineOrder";

        //砸金蛋URL
        //   public static String H5_EGG_URL = HOST + "/h5/drawaward.html";

        //砸金蛋URL
        public static String H5_HELP_URL = HOST + "/h5/help-center.html";

        // 退出登录
        public static String LOGOUT_URL = HOST + "/buser/login/logout";

        // 新增URL
        public static String CAR_ADD_URL = HOST + "/car/add";
        // 获取用户车辆
        public static String CAR_LIST_URL = HOST + "/car/list";
        // 获取车辆详细信息
        public static String CAR_DETAIL_URL = HOST + "/car/detail";

        // 店铺详细信息
        public static String SHOPDETAIL_URL = HOST + "/bshop/detail";

        //获取店铺服务列表
        public static String SHOPPRODUCT_URL = HOST + "/bshop/product/list";

        // 预订
        public static String RESERVE_URL = HOST + "/breserve/add";
        //预订列表
        public static String RESERVE_LIST_URL = HOST + "/breserve/list";

        //店铺列表LIST
        public static String GET_SHOPS_URL = HOST + "/bshop/list";

        //B端用户新建用户 新建用户
        public static String ADD_USER_URL = HOST + "/buser/add";

        //B端用户新建用户 新建用户
        public static String COMPANY_ADD_URL = HOST + "/bcompany/add";

        //B端用户新建用户 新建用户
        public static String ADD_SHOP_URL = HOST + "/bshop/add";

        //B端用户新建用户 新建用户
        public static String ADD_SHOPPRODUCT_URL = HOST + "/bshop/add/product";


        //商家获取订单
        public static String GET_BRSERVE_URL = HOST + "/buser/reserve/list";

        //商家获取订单
        public static String CONFIRM_BRSERVE_URL = HOST + "/buser/reserve/confirm";


        // 七牛云uploadToken 有效期1个小时
        public static String GET_QINIU_UPTOKEN = HOST + "/qiniu/upToken";


        public static void resetUrl(String HOST) {
            LTNConstants.ACCESS_URL.HOST = HOST;
            IMAGE_HOST = HOST;
            REGISTER_URL = HOST + "/user/register/registerUser";
            VERIFY_CODE_URL = HOST + "/mobile/mobilecode/getMobileCode";
            PICTURE_CODE_HOST = HOST + "/user/register/pictureCode";
            LOGIN_CODE_URL = HOST + "/user/login/login";
            RESET_PASSWORD_URL = HOST + "/user/login/editPwd";
            RETRIEVE_PASSWORD_URL = HOST + "/user/login/retrievePwd";
            VERIFY_MOBILE_CODE_URL = HOST + "/mobile/mobilecode/verifyMobileCode";
            PRODUCT_PREBUY_URL = HOST + "/user/order/orderPrepare";
            PRODUCT_BUY_URL = HOST + "/product/buy/confirm";
            GET_PRODUCTS_URL = HOST + "/product/lists";
            AUTH_USER_URL = HOST + "/user/userAuth";
            BIND_BANK_CARD_URL = HOST + "/user/bindBankCard";
            HOMEPAGE_URL = HOST + "/homepage/recommend";
            DEPOSIT_URL = HOST + "/user/recharge";
            WITHDRAW_URL = HOST + "/user/withdrawals";
            CHANGE_BANK_CARD_URL = HOST + "/user/replaceBankCard";
            MY_COUPON_URL = HOST + "/user/account/myFinancialCoupon";
            MY_ACCOUNT_URL = HOST + "/user/account/myAccount";
            MY_ACCOUNT_INFO_URL = HOST + "/user/account/userInfo";
            MY_USER_INFO_URL = HOST + "/user/userInfo";
            BALANCE_LIST_URL = HOST + "/account/balanceDetail";
            BIRDCOINAMOUNT = HOST + "/user/account/birdCoinAmount";
            TOTAL_ACCTOUNT_URL = HOST + "/user/account/totalaccount";
            INCOME_DETAIL_URL = HOST + "/user/uncRevenue";
            MY_INVEST_URL = HOST + "/user/account/myInvestment";
            PURCHASE_RECORD_URL = HOST + "/product/purchasehistory";
            PARTNER_REWARD = HOST + "/user/partner";
            COUPON_LIST = HOST + "/user/account/myFinancialCoupon";
            FRIENDS_COUNT = HOST + "/user/account/friends";
            REPLENISH = HOST + "/user/partner/replenish";
            FEED_BACK = HOST + "/user/aboutus/feedback";
            BUY_LIST = HOST + "/product/purchasehistory";

            // h5 页面
            // 安全保障
            H5_INSURANCE_URL = HOST + "/h5/insurance.html";

            // 首页风险备用金
            H5_RESERVE_FUND_URL = HOST + "/h5/reservefund.html";

            // 关于领头鸟理财
            H5_ABOUT_URL = HOST + "/h5/about.html";
            // 投资协议
            H5_BUY_HTML = HOST + "/h5/about.html";
            //合伙人奖励规则
            H5_REWARD_RULE_URL = HOST + "/h5/rewardrule.html";

            // 同意协议书
            H5_ACCEPT_RULE_URL = HOST + "/h5/accept.html";

            // 产品分享链接
            H5_PRODUCT_SHARE_URL = HOST + "/h5/share.html";

            // 首页信托
            H5_PROFIT_URL = HOST + "/h5/profit.html";

            // 用户注册 在线服务协议
            H5_REGISTER_URL = HOST + "/h5/accept-register.html";

            // 身份认证 资金托管协议 & 个人用户授权协议
            H5_AUTH_USER_URL = HOST + "/h5/accept-id.html";

            // 绑定银行卡 支付服务协议 & 快捷支付用户协议
            H5_BINDCARD_URL = HOST + "/h5/accept-bind.html";

            // 银行卡充值限额
            BANK_LIMIT_URL = HOST + "/bank/limitAmount";


            // share  host log
            SHARE_LOG_URL = HOST + "/logo/logo.png";
            // 检测更新
            // CHECK_UPDATA = HOST + "/app/global/checkupdate";
            //
            PARTER_EARNINGS = HOST + "/user/partner/earnings";

            //我的投资
            MY_REWARD_URL = HOST + "/user/partner/earnings";


            //随心投详情
            MY_CURRENT_INCOMELIST_URL = HOST + "/user/current/incomeList";

            // 银行卡列表
            BANK_LIST_URL = HOST + "/bank/bankList";

            // 随心投协议
            H5_CURRENT_AGREEMENT_URL = HOST + "/h5/project-accept-current.html";

            // 我的随心投首页
            CURRENT_AMOUNT_URL = HOST + "/product/current/homepage";


            // 购买随心投
            PRODUCT_CURRENT_BUY = HOST + "/product/current/buy";

            PRODUCT_CURRENT_EXTRACT = HOST + "/product/current/extract";

            BANK_LIST = HOST + "/bank/bankList";


            // 免密充值协议
            H5_CZ_AGREEMENT_URL = HOST + "/h5/project-accept-current.html";
            // 免密投资协议
            H5_TZ_AGREEMENT_URL = HOST + "/h5/project-accept-current.html";

            // 免密协议 接口
            MIANMI_AGREEMENT_URL = HOST + "/user/agreement";

            // 安心投接口
            ANXINTOU_URL = HOST + "/product/findOfflineOrder";

            //      H5_EGG_URL = HOST + "/h5/drawaward.html";

            H5_HELP_URL = HOST + "/h5/help-center.html";

            // 退出登录
            LOGOUT_URL = HOST + "/user/login/logout";


            // 新增URL
            CAR_ADD_URL = HOST + "/car/add";
            // 获取用户车辆
            CAR_LIST_URL = HOST + "/car/list";

            // 预订
            RESERVE_URL = HOST + "/reserve/add";


        }
    }


    public static class PRODUCT {
        public static String STATUS_TBZ = "1"; //投标中
        public static String STATUS_HKZ = "2"; //还款中
        public static String STATUS_YHK = "3"; //已还款
        public static String STATUS_JS = "4";  // 结标

        public static String REPAYMENT_YCXHK_CODE = "一次性还款";
        public static String REPAYMENT_YCXHK = "YCXHK";

        public static String TYPE_TYB = "TYB";
        public static String TYPE_XSB = "XSB";
        public static String TYPE_SXT = "SXT";


        // 购买记录
        public static String PURCHASE_HISTORY_LIST = "purchaseHistoryList";

        // 乐巢投系列
        public static String LCTXL_TYB = "LCTXL";

        // 乐巢投
        public static String LCT_TYB = "LCT";

    }

    public static String RETURN_URL = "url";

    public static String HEADER_SIGN = "header_sign";


    // 安心投type
    public static String ORDER_TYPE = "orderType";

    public static String OFFLINE_ORDER_LIST = "offlineOrderList";

    public static String BIRD_COIN = "birdCoin";
    public static String BUCKLE = "buckle"; // 1 or 2
    public static String HANDLING_CHARGE_VALUE_SYSTEM = "PTF"; // 系统出手续费
    public static String HANDLING_CHARGE_VALUE_PERSON = "JYF"; // 个人出手续费

    public static String EXPECTED_REVENUE = "revenue";


    public static String MSG_SUCCESS = "0";
    public static String MSG_FAILURE = "1";
    public static String MAG_SESSION = "10000006";//登录超时


    // 分页参数
    public static int PAGE_COUNT = 20;
    public static String PAGE_SIZE = "pageSize";
    public static String CURRENT_PAGE = "currentPage";

    // 账户相关参数
    public static String CLIENT_TYPE_PARAM = "clientType";

    // 当前版本
    public static String APP_VERSION = "appVersion";

    public static String CLIENT_TYPE_MOBILE = "A"; // 表示请求类型是来自Android版本

    public static String MOBILE_NO = "mobileNo";
    public static String PASSWORD = "password";
    public static String MOBILE_CODE = "mobileCode";
    public static String READ_AND_AGREE = "readAndAgree";

    public static String READ_AND_AGREE_TRUE = "1";
    public static String READ_AND_AGREE_FALSE = "0";
    public static String MACHINE_NUMBER = "machineNo";
    public static String PICTURE_CODE = "pictureCode";

    public static String OLD_PASSWORD = "primaryCode";
    public static String NEW_PASSWORD = "newPwd";
    public static String SEND_TYPE = "sendType";

    public static String SENT_TYPE_REGISTER = "1";
    public static String SENT_TYPE_FORGET_PASSWORD = "2";
    public static String ID_CARD = "idCard";

    public static String USER_COUPON = "userCouponId";
    public static String USER_COUPON_VALUE = "user_coupon_value";

    public static String CERTIFICATION = "certification";
    public static String CERTIFICATION_TRUE = "1";
    public static String CERTIFICATION_FALSE = "0";

    // daily limit, once limit
    public static String DAILY_LIMIT = "dailyLimit";
    public static String ONCE_LIMIT = "onceLimit";


    // 首页列表
    public static String BANK_FILE = "bank_list";
    public static String BANNERS = "bannersList";
    public static String BANNER_NAME = "banner_name";
    public static String BANNER_IMAGE_URL = "bannerUrl";
    public static String BANNER_URL = "linkUrl";

    public static String PRODUCTS = "productList";
    public static String OUT_MONEY = "out_money";

    //for shop

    // 店家列表
    public static String SHOP_LIST = "shopList";

    // 产品列表
    public static String PRODUCT_LIST = "productList";

    // 余额明细
    public static String BALANCE_DETAILS = "balanceDetails";
    // 可用余额
    public static String BALANCE_USEABLE = "usableBalance";
    // 鸟币明细
    public static String BIRD_COINS = "birdCoins";
    // 鸟币总额
    public static String TOTAL_AMOUNT = "totalAmount";
    // 收益明细
    public static String INCOME_TOTAL = "uncRevenueTotal";//总收益
    public static String INCOME_LIST = "uncRevenueList";//收益列表
    public static String TYPE = "type";

    //我的投资
    public static String STATUS = "status";
    public static String UPDATED_STATUS = "updatedStatus";

    public static String INVESTMENTS = "investments";
    public static String LIST_PARTNER_EARNINGS = "listPartnerEarnings";


    // 返回结果
    public static String DATA = "data";
    public static String RESULTS = "results";
    public static String RESULT_CODE = "resultCode";
    public static String RESULT_MESSAGE = "resultMessage";
    public static String SESSION_KEY = "sessionKey";
    public static String SESSION_KEY_TIME_OUT = "sessionKey_timeout";
    public static String PICTURE_CODE_URL = "pictureCodeUrl";
    public static String ORDER_AMOUNT2 = "order_amount";
    public static String ORDER_AMOUNT = "orderAmount";
    public static String CURRENT_TOTAL_INCOME = "currentTotalIncome";
    public static String CURRENT_IN_ACCOUNT = "current_in_account";
    public static String CURRENT_IN_ACCOUNT_AFTER = "current_in_account_after";


    // 错误的结果
    public static String SUCCESS_CODE_0 = "0";
    public static String ERROR_CODE_1 = "1";
    public static String ERROR_CODE_A0001 = "A0001";
    //意见反馈
    public static String FEED_BACK = "feed_back";


    // 密码正则表达式
    public static String PASSWORD_PATTERN = "(?=.*[A-Za-z])(?=.*[0-9])[a-zA-Z0-9]{6,18}";
    public static String IDCARD_PATTERN = "(\\d{17}[0-9xX])";


    // 下拉列表
    public static int REFRESH = 0;
    public static int MORE_OPERATION = 1;

    /* product
        [{"annualIncome":0.8888,"annualIncomeText":"12.8888%",
                "arrangeDate":"2015-12-11 13:13:53.0","bidPublishDate":"2015-12-11 13:13:53",
                "buyCount":"0","convertDay":11,"createBy":"2","createDate":"2015-12-11 13:13:53","deadlineUnit":"1",
                "isArrange":"0","isFisrtPage":0,"offBidDate":"2015-12-11 13:13:53","orderNo":1,
                "overBidDate":"2015-12-11 13:13:53","productDeadline":1,"productName":"安心投1号",
                "productNo":"111111","productRemainAmount":800000,"productStatus":"1",
                "productTag":"新手,限额","productTitle":"好产品1",
                "productTotalAmount":1000000,"productType":"1","raiseEndDate":"2015-12-11 13:13:53",
                "repaymentType":"1","singleLimitAmount":100,"staInvestAmount":1000,"staRateDate":"2015-12-11 13:20:18"},
                */
    // product 属性 开始
    // 产品名称
    public static String PRO_Name = "productName";


    // 产品标题
    public static String PRO_Title = "productTitle";
    // 产品详情url
    public static String PRO_DetailUrl = "detailsUrl";

    // 产品截止时间
    public static String PRO_RaiseEndDate = "raiseEndDate";

    // 产品ID
    public static String PRO_Id = "productId";

    // 产品编号
    public static String PRO_No = "productNo";
    // 产品状态
    public static String PRO_Status = "productStatus";
    // 产品Tag
    public static String PRO_Tags = "productTag";
    // 产品Type
    public static String PRO_Type = "productType";

    // 产品 RateCalculateType
    public static String PRO_RateCalculateType = "rateCalculateType";


    public static String PRO_BUY_TYPE_TYB = "体验金券购买";
    public static String PRO_BUY_TYPE_SB = "元起投";

    public static String PRO_BTN_TY = "立即体验";
    public static String PRO_BTN_BUY = "立即购买";
    public static String PRO_STOP_BTN_BUY = "停止投资";
    public static String PRO_BTN_RESERVE = "立即预约";
    public static String PRO_BTN_BUYOUT = "已抢光";
    public static String PRO_STA_INVEST_Amount = "元起投";

    // 年收益率
    public static String PRO_AnnualIncome = "annualIncome";

    // 年化收益率
    public static String PRO_AnnualIncomeText = "annualIncomeText";

    // 期限单位
    public static String PRO_DeadlineUnit = "deadlineUnit";
    // 期限
    public static String PRO_Deadline = "productDeadline";
    // 剩余总额
    public static String PRO_PrAmount = "productRemainAmount";
    // 总额
    public static String PRO_PtAmount = "productTotalAmount";

    // 已买产品次数
    public static String PRO_BuyCount = "buyCount";

    // 收益方式
    public static String PRO_RepaymentType = "repaymentType";

    // 起息时间
    public static String PRO_StaRateDate = "staRateDate";

    // 起投金额
    // 递增金额
    public static String PRO_StaInvestAmount = "staInvestAmount";

    //投资期限单位
    public static String PRO_DEADLINEUNIT = "productDeadlineUnit";

    // 产品认购上限
    public static String PRO_SlAmount = "singleLimitAmount";

    public static String PRACELABLE_PRODUCT = "parceable_product";
    public static String PRACELABLE_COUPONS = "parceable_coupons";

    public static String COUPONS = "coupons";
    public static String PRODUCT_EXPIRE_DATE = "productExpireDate";


    // product 属性结束


    // 实名认证,绑卡,充值,提现
    public static String ACCOUNT_USERNAME = "userName";
    public static String ACCOUNT_IDENTITYCODE = "identityCode";
    public static String ACCOUNT_CARDID = "cardId";

    // 绑卡后跳转方向
    public static String FORWARD_URL = "forward_url";
    public static String BACK_TO_BINDCARD = "back_to_bindcard";
    public static String BACK_TO_DEPOSIT = "back_to_deposit";
    public static String BACK_TO_WITHDRAW = "back_to_withdraw";

    public static String BACK_TO_CURRENT = "back_to_current";
    public static String BACK_TO_HOMEPAGE = "back_to_homepage";

    // 产品购买成功后跳转方向
    public static String BACK_TO_PRODUCT_BUY = "back_to_product_buy";
    // 免密签约
    public static String BACK_TO_MIANMI = "back_to_mianmi";


    //绑定银行卡后,跳转到的URL界面
    public static String BIND_CARD_RETURN_URL = "umpayGateway/ptp_mer_bind_card";
    public static int FROM_WEBVIEW_INTERFACE = 1001;

    // 绑卡推荐人银行
    public static String BELONG_BANK = "belongBank";


    //充值后,跳转到的URL界面
    public static String DEPOSIT_RETURN_URL = "umpayGateway/mer_recharge_person";
    public static String BIND_CARD_RESULT = "bind_card_result";
    public static final int BIND_CARD_RESULT_SUCCESS = 901;
    public static final int PRODUCT_BUY_SUCCESS = 902;
    public static final int DEPOSIT_RESULT_SUCCESS = 903;
    public static final int WITHDRAW_RESULT_SUCCESS = 904;
    public static final int CURRENT_BUY_SUCCESS = 905;
    public static final int CURRENT_DEPOSIT_VALUE = 906;
    public static final int GRANT_RESULT_SUCCESS = 907;
    public static final int ZAJIDAN_SUCCESS = 908;
    public static final int FROM_DEPOSIT = 909;
    public static final int DEPOSIT_MIANMI_SUCCESS = 1000;
    public static final int CARD_CHANGE_SUCCESS = 1001;
    public static final int CHECK_BIRDCOIN_SUCCESS = 1002;


    //提现后,跳转到的URL界面
    public static String WITHDRAW_RETURN_URL = "umpayGateway/cust_withdrawals";

    // 购买后,跳转到的URL界面
    public static String PRODUCT_BUY_RETURN_URL = "umpayGateway/project_transfer";

    public static String JSInterface = "jsinterface";


    public static String AGREEMENT_URL = "鸟人协议";

    // bundle obj
    //  public static String BUNDLE_OBJ = "bundle_obj";

    // 购买体验标,需要传到后台的API
    public static String TYB_AMOUNT = "0";


    // 手势密码
    public static final int POINT_STATE_NORMAL = 0;
    public static final int POINT_STATE_SELECTED = 1;
    public static final int POINT_STATE_WRONG = 2;

    public static String FROM_MODIFY_GESTURE_PASSWORD = "from_modify_gesture_password";

    public static String GESTURE_PASSWORD = "gesture_password";
    public static String FROM_LAUNCH = "from_launch";
    public static String FROM_FORGET_PASSWORD = "forget_password";

    public static String FROM_LOGIN_ACTIVITY = "from_login_activity";
    // 设置手势密码:
    public static int SET_GESTURE_CODE = 1001;

    // 提现金额
    public static String WITHDRAW_HINT_VALUE = "withdraw_hint_value";


    // 注册推荐人参数
    public static String PARTER_MOBILE = "partnerMobile";

    // 随心投列表
    public static String CURRENT_INCOME_LIST = "currentIncomeList";

    public static String CURRENT_HOLD_AMOUNT = "current_hold_amount";
    public static String CURRENT_REMAIN_AMOUNT = "current_remain_amount";
    public static String LEAST_REVENUE_AMOUNT = "leastRevenueAmount";
    public static String AVAILABLE_AMOUNT = "available_amount";
    public static String ANNUAL_INCOME_PERDAY = "annual_income_perday";

    // 活期
    public static String CURRENT_DEPOSIT = "current_deposit";
    //


    //SHOP
    public static String SHOP_NAME = "shopName";
    public static String SHOP_ID = "shopId";
    public static String SHOP_LOCATION = "shopLocation";
    public static String WAITING_TIME = "waitingTime";
    public static String LATITUDE = "latitude";
    public static String LONGITUDE = "longitude";
    public static String SHOP_RATING = "rating";
    public static String SHOP_DISTANCE = "distance";
    public static String SHOP_CAR_WASHING = "carWashing";
    public static String SHOP_CAR_WAITING = "carWaiting";
    public static String ORIGINAL_PRICE = "originalPrice";
    public static String CURRENT_PRICE = "currentPrice";
    public static String SHOP_PRIMARY_PIC = "primaryPic";
    public static String SHOP_WORK_TIME = "workTime";
    public static String SHOP_TAG = "shopTag";
    public static String DISCOUNT_BEGINTIME = "discountBeginTime";
    public static String DISCOUNT_ENDTIME = "discountEndTime";
    public static String SHOP_ADDRESS = "shopAddress";
    public static String SHOP_WASHSPACE = "shopWashspace";


    // car 参数
    public static String CAR_PLATE = "plateNumber";
    public static String CAR_TYPE = "carType";
    public static String CAR_BARND = "brand";
    public static String CAR_BARNDTYPE = "brandtype";
    public static String CAR_COLOR = "color";
    public static String CAR_ID = "carId";
    public static String CAR = "CAR";
    public static String CAR_NO = "carNo";

    public static String SHOP_SERVICE = "SHOP_SERVICE";
    public static String SHOPSERVICE_LIST = "shopServiceList";
    // 服务
    public static String SERVICE_ID = "serviceId";
    public static String SERVICE_NAME = "serviceName";
    // public static String SERVICE_PRICE = "servicePrice";

    //和B段保持一直
    public static String SERVICE_TIME_CONSUME = "timeConsume";
    public static String SERVICE_PRICE = "originalPrice";
    public static String SERVICE_DESC = "desc";


    //RESERVE 参数
    public static String RESERVE_BEGINTIME = "reserveBegintime";
    public static String RESERVED_PRODUCT_LIST = "reserveProductList";
    public static String IS_RESERVE = "IS_RESERVE";
    public static String IS_FROM_RESERVE = "IS_FROM_RESERVE";
    public static String RESERVE_NO = "reserveNo";


    //角色
    public static String ROLE_NAME = "ROLE_NAME";

    //七牛云upload token
    public static String QINIU_UPLOAD_TOKEN = "uploadToken";

    // company 参数

    public static String COMPANY_ID = "companyId";
    public static String COMPANY_TYPE = "companyType";
    public static String COMPANY_NAME = "companyName";
    public static String LICENSE_NAME = "licenseName";//营业执照名称
    public static String LICENSE_REG_NO = "licenseRegNo";//营业执照名称
    public static String LICENSE_IMAGE_URL = "licenseImageUrl";//营业执照图片

    public static String PROPRIETOR_NAME = "proprietorName";//经营者姓名
    public static String PROPRIETOR_ID = "proprietorID";//经营者身份证号
    public static String CONTACT_EMAIL_ADDRESS = "contactEmailAddress";//经营者邮箱

    public static String PROPRIETOR_ID_FRONT_IMAGE = "proprietorIDFrontImage";//经营者身份证正面照
    public static String PROPRIETOR_ID_BACK_IMAGE = "proprietorIDBackImage";//经营者身份证反面照


    public static final int ADD_CAR = 9000;
    public static final int ADD_CAR_SUCCESS = 9001;
    public static final int ADD_BSHOP_PRODUCT_SUCCESS = 9002;


}

