package com.bwash.bwashcar.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwash.bwashcar.activities.CompanyActivity;
import com.bwash.bwashcar.imageloader.ImageLoaderProxy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.bwash.bwashcar.R;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.library.PullToRefreshBase;
import com.bwash.bwashcar.library.PullToRefreshScrollView;
import com.bwash.bwashcar.model.Banner;
import com.bwash.bwashcar.model.Shop;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.Utils;
import com.bwash.bwashcar.utility.ViewUtils;
import com.bwash.bwashcar.view.BannerView;
import com.bwash.bwashcar.view.ProductTagView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 首页
 */
public class LTNHomeFragment extends BaseFragment implements View.OnClickListener, PullToRefreshBase.OnRefreshListener2<ScrollView> {

    public static String FRAGMENT_TAG = LTNHomeFragment.class.getSimpleName();
    private View rootView;
    private BannerView bannerView = null;
    private PullToRefreshScrollView mPullToRefreshScrollView = null;
    private ArrayList<Banner> bannerList = new ArrayList<Banner>();
    private Shop mShop = null; //推荐的店铺
    private Button btn_order;

    // TODO:
    // 添加天气预报信息
    //洗车店图片
    private ImageView iv_shop;
    // 洗车店
    private TextView tv_name;
    // 星级
    private RatingBar rb_rating;
    // 地址
    private TextView tv_address;
    //距离
    private TextView tv_distance;
    // 标签
    private ProductTagView tag_view;
    // 活动描述
    private TextView tv_activity_desc;
    // 优惠活动
    private RelativeLayout tl_activity;
    // 原价
    private TextView tv_original_price;
    //现价
    private TextView tv_current_price;
    //排队车辆
    private TextView tv_queue_cars;
    // 施工车辆
    private TextView tv_working_cars;
    // 等待时间
    private TextView tv_waiting_time;


    // 产品详情
    private ImageView egg_img = null;

    private ImageView id_viewflow;

    String dtUrl = null;
    boolean isHidden = false;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_home, null);
        initView();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewUtils.calculateScreenSize(getActivity());
        Utils.checkVersion(getActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1002 && resultCode == Activity.RESULT_OK) {
            if (!isHidden) {
                getData();
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
        if (!hidden) {
            getData();
        }
    }

    public void getData() {

        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.LATITUDE, "31.912");
        mReqParams.put(LTNConstants.LONGITUDE, "121.234");

        if (!android.text.TextUtils.isEmpty(LTNApplication.getInstance().getSessionKey())) {
            mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());
        }

        WCOKHttpClient.getOkHttpClient(getActivity()).requestAsyn(LTNConstants.ACCESS_URL.HOMEPAGE_URL, WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {
                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);
                            // 验证码错误
                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {
                                Gson gson = new Gson();
                                JSONObject resultObj = (JSONObject) jsonObject.get(LTNConstants.DATA);

                                JSONArray bannerArray = resultObj.getJSONArray(LTNConstants.BANNERS);
                                if (bannerList == null || (bannerList != null && bannerList.size() == 0)) {
                                    bannerList = gson.fromJson(bannerArray.toString(), new TypeToken<ArrayList<Banner>>() {
                                    }.getType());
                                    if (bannerList != null) {
                                        bannerView.initAdv(bannerList);
                                    }
                                }
                                JSONArray productArray = resultObj.getJSONArray(LTNConstants.SHOP_LIST);
                                if (productArray.length() > 0) {
                                    JSONObject productObj = productArray.getJSONObject(0);
                                    //    放在一个Product 里面比较好点
                                    mShop = gson.fromJson(productObj.toString(), Shop.class);
                                    setData();
                                }


                            } else {
                                String resultMsg = jsonObject.getString(LTNConstants.RESULT_MESSAGE);
                                if (!android.text.TextUtils.isEmpty(resultMsg)) {
                                    Toast.makeText(getActivity(), resultMsg, Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (
                                JSONException je) {
                            // Log.d(FRAGMENT_TAG, je.getMessage());
                        }
                        if (mPullToRefreshScrollView.isRefreshing())

                        {
                            mPullToRefreshScrollView.onRefreshComplete();
                        }

                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        if (mPullToRefreshScrollView.isRefreshing()) {
                            mPullToRefreshScrollView.onRefreshComplete();
                        }
                    }

                });

    }

    private void setData() {
        if (getActivity() == null) {
            return;
        }
        if (mShop != null) {
            tv_name.setText(mShop.getShopName());
            tv_address.setText(mShop.getShopLocation());

            if (mShop.getDistance() % 1000 != 0) {
                tv_distance.setText(mShop.getDistance() / 1000 + "公里");
            } else {
                tv_distance.setText(mShop.getDistance() + "米");
            }
            tv_distance.setText("" + mShop.getDistance());
            tv_waiting_time.setText("" + mShop.getWaitingTime());
            ImageLoaderProxy.getInstance().displayImage(this.getContext(), mShop.getPrimaryPic(), iv_shop, R.drawable.ic_image_holder);
            //   iv_shop.setImageURI(mShop.getPrimaryPic());
            rb_rating.setRating(mShop.getRating());

//            // 星级
//            private RatingBar rb_rating;
//            // 标签
//            private ProductTagView tag_view;
//            // 活动描述
//            private TextView tv_activity_desc;
//            // 优惠活动
//            private RelativeLayout tl_activity;
//            // 原价
//            private TextView tv_original_price;
//            //现价
//            private TextView tv_current_price;
//            //排队车辆
//            private TextView tv_queue_cars;
//            // 施工车辆
//            private TextView tv_working_cars;

        }


    }

    @Override
    public void onPause() {
        super.onPause();
        if (bannerView != null) {
            bannerView.bannerStopPlay();
        }
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (bannerView != null) {
            bannerView.bannerStartPlay();
        }
        if (!isHidden) {
            getData();
        }
    }


    public void initView() {


        ((TextView) rootView.findViewById(R.id.title)).setText(getString(R.string.app_name));
        rootView.findViewById(R.id.back_btn).setVisibility(View.GONE);
        rootView.findViewById(R.id.right_menu_commit).setVisibility(View.GONE);
        rootView.findViewById(R.id.right_menu_button).setVisibility(View.GONE);
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_address = (TextView) rootView.findViewById(R.id.tv_address);
        rb_rating = (RatingBar) rootView.findViewById(R.id.rb_rating);
        tv_distance = (TextView) rootView.findViewById(R.id.tv_distance);
        tv_activity_desc = (TextView) rootView.findViewById(R.id.tv_activity_desc);
        tl_activity = (RelativeLayout) rootView.findViewById(R.id.tl_activity);
        tv_original_price = (TextView) rootView.findViewById(R.id.tv_original_price);
        tv_current_price = (TextView) rootView.findViewById(R.id.tv_current_price);
        tv_queue_cars = (TextView) rootView.findViewById(R.id.tv_queue_cars);
        tv_working_cars = (TextView) rootView.findViewById(R.id.tv_working_cars);
        tv_waiting_time = (TextView) rootView.findViewById(R.id.tv_waiting_time);

        //   bannerView = (BannerView) rootView.findViewById(R.id.id_viewflow);
        mPullToRefreshScrollView = (PullToRefreshScrollView) rootView.findViewById(R.id.pull_to_refresh_scrollview);
        mPullToRefreshScrollView.setOnRefreshListener(this);
        mPullToRefreshScrollView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        id_viewflow = (ImageView) rootView.findViewById(R.id.id_viewflow);

        id_viewflow.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_viewflow:
                Intent intent = new Intent(this.getActivity(), CompanyActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void clickEffective(View v) {
        int viewId = v.getId();
        switch (viewId) {
//            case R.id.tv_invite_friends://邀请好友
//                Intent intent = null;
//                // 跳转到合伙人
////                intent = new Intent(getActivity(), PartnerActivity.class);
////                startActivity(intent);
//                if (android.text.TextUtils.isEmpty(LTNApplication.getInstance().getSessionKey())) {
//                    intent = new Intent(getActivity(), LoginActivity.class);
//                    Bundle b1 = new Bundle();
//                    b1.putString(Constant.LoginParams.LOGIN_TYPE, Constant.LoginParams.LOGIN_TO_PARTNER);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//                    intent.putExtras(b1);
//                    startActivity(intent);
//                } else {
//                    intent = new Intent(getActivity(), PartnerActivity.class);
//                    startActivity(intent);
//                }
//                break;

//            case R.id.btn_order:// 立即投资
//                if (mShop != null) {
//                    Bundle proBundle = new Bundle();
//                    proBundle.putString(LTNConstants.SHOP_ID, mShop.getShopId()); // 产品ID
//                    proBundle.putString(LTNConstants.SHOP_NAME, mShop.getShopName()); // 产品ID
//                    proBundle.putString(LTNConstants.SHOP_LOCATION, mShop.getShopLocation()); // 产品ID
//                    proBundle.putInt(LTNConstants.WAITING_TIME, mShop.getWaitingTime()); // 等待时间，以分钟为单位
//                    proBundle.putDouble(LTNConstants.LATITUDE, mShop.getLatidude()); // 产品余额
//                    proBundle.putDouble(LTNConstants.LONGITUDE, mShop.getLongitude()); // 产品余额
//                    proBundle.putFloat(LTNConstants.SHOP_RATING, mShop.getRating()); // 产品余额
//                    proBundle.putInt(LTNConstants.SHOP_DISTANCE, mShop.getDistance());
//                    proBundle.putString(LTNConstants.SHOP_PRIMARY_PIC, mShop.getPrimaryPic()); // 产品图片
//                    proBundle.putString(LTNConstants.SHOP_WORK_TIME, mShop.getWorkTime()); // 产品ID
//
//                    /*
//                    proBundle.putInt(LTNConstants.SHOP_CAR_WASHING, mShop.getCarWashing());
//                    proBundle.putInt(LTNConstants.SHOP_CAR_WAITING, mShop.getCarWaiting());
//                    proBundle.putString(LTNConstants.SHOP_TAG, mShop.getShopTag()); // 产品ID
//                    proBundle.putDouble(LTNConstants.ORIGINAL_PRICE, mShop.getOriginalPrice()); // 产品余额
//                    proBundle.putDouble(LTNConstants.CURRENT_PRICE, mShop.getCurrentPrice()); // 产品余额
//                    proBundle.putString(LTNConstants.DISCOUNT_BEGINTIME, mShop.getDiscountBeginTime()); // 产品总额
//                    proBundle.putString(LTNConstants.DISCOUNT_ENDTIME, mShop.getDiscountEndTime());      // 截止时间
//                    */
//
//
//                    Intent intent = new Intent(getActivity(), ShopDetailActivity.class);
//                    intent.putExtras(proBundle);
//                    startActivity(intent);
//                }


            default:
                break;
        }

    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
        getData();
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {

    }


}
