package com.bwash.bwashcar.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.bwash.bwashcar.R;
import com.bwash.bwashcar.adapter.ShopAdapter;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.library.PullToRefreshBase;
import com.bwash.bwashcar.library.PullToRefreshListView;
import com.bwash.bwashcar.model.Shop;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.Utils;
import com.bwash.bwashcar.utility.ViewUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * 我要理财
 */
public class LTNInvestimentFragment extends BaseFragment implements OnClickListener, PullToRefreshBase.OnRefreshListener2<ListView> {

    public static String FRAGMENT_TAG = LTNInvestimentFragment.class.getSimpleName();

    private View rootView;
    private Context context;
    DisplayImageOptions options;
    PullToRefreshListView lvProduct;

//    ProductCache productCache;

    public ShopAdapter mShopAdapter;
    private ArrayList<Shop> mShops = null;
    ProgressDialog mProgressdialog;
    private ProgressBar mProgressBar;
    //    boolean isForceRefreshHorder = false;
    private int page = 0;
    //    InvestDownLoadTask mInvestDownLoadTask;
    private boolean isViewShown;
    private boolean isPrepared;
    private boolean isCreate = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_shops, null);

        isPrepared = true;
        initView(rootView, inflater);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        app = (LTNApplication) this.getActivity().getApplication();

        context = this.getContext();
        mShopAdapter = new ShopAdapter(getActivity());
        lvProduct.setAdapter(mShopAdapter);
        lvProduct.setOnRefreshListener(this);
        ListView listview = lvProduct.getRefreshableView();
        listview.setDivider(ContextCompat.getDrawable(getActivity(), R.drawable.divide_normal));
        listview.setDividerHeight(ViewUtils.dip2px(getActivity(), 10));
        showDialog();
        onPullDownToRefresh(lvProduct);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isCreate) {
            refreshCurrent(true);
        } else {
            isCreate = false;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if ((!hidden) && !isCreate) {
            refreshCurrent(true);
        } else {
            isCreate = false;
        }
    }

    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible) {
        }
    }

    public void showDialog() {
        if (mProgressdialog == null || !mProgressdialog.isShowing()) {
            mProgressdialog = new ProgressDialog(this.getActivity());
            mProgressdialog.setMessage("正在加载数据");
            mProgressdialog.setIndeterminate(true);
            mProgressdialog.setCancelable(true);
            mProgressdialog.show();
        }
    }

    public void initView(View rootView, LayoutInflater inflater) {
        rootView.findViewById(R.id.back_btn).setVisibility(View.GONE);
        ((TextView) rootView.findViewById(R.id.title)).setText(getString(R.string.investments));
        lvProduct = (PullToRefreshListView) rootView.findViewById(R.id.lv_products);
        lvProduct.setMode(PullToRefreshBase.Mode.BOTH);

    }


    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
        page = 0;
        getShops(true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        page++;
        getShops(false);
    }

    @Override
    public void onClick(View v) {

    }

    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500); // 设置image隐藏动画500ms
                    displayedImages.add(imageUri); // 将图片uri添加到集合中
                }
            }
        }
    }

    public void getShops(final boolean isPullDownToRefresh) {

        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.CURRENT_PAGE, String.valueOf(page));
        mReqParams.put(LTNConstants.PAGE_SIZE, String.valueOf(LTNConstants.PAGE_COUNT));
        String sessionKey = LTNApplication.getInstance().getSessionKey();
        if (sessionKey != null) {
            mReqParams.put(LTNConstants.SESSION_KEY, sessionKey);
        }
        // TODO: 上传location

        WCOKHttpClient.getOkHttpClient(this.getContext()).requestAsyn(LTNConstants.ACCESS_URL.GET_SHOPS_URL, WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {
                            if (jsonObject.optInt(LTNConstants.RESULT_CODE) == 0) {
                                if (mShops == null) {
                                    mShops = new ArrayList<Shop>();
                                }

                                JSONObject dataObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                JSONArray resultArray = (JSONArray) dataObj.get(LTNConstants.SHOP_LIST);
                                ArrayList<Shop> shops = new Gson().fromJson(resultArray.toString(),
                                        new TypeToken<ArrayList<Shop>>() {
                                        }.getType());
                                if (shops != null) {
                                    if (isPullDownToRefresh) {
                                        mShops.clear();
                                    }
                                    mShops.addAll(shops);
                                }
                                mShopAdapter.setShops(mShops);
                            }
                        } catch (JSONException je) {
                            // Log.d(FRAGMENT_TAG, je.getMessage());
                        }
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvProduct.onRefreshComplete();
                        upDateRefreshTime();
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        Log.d(TAG, errorMsg);
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvProduct.onRefreshComplete();
                    }
                });

    }

    public void refreshCurrent(final boolean isPullDownToRefresh) {


        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);

        mReqParams.put(LTNConstants.CURRENT_PAGE, "0");
        mReqParams.put(LTNConstants.PAGE_SIZE, String.valueOf(LTNConstants.PAGE_COUNT + page * LTNConstants.PAGE_COUNT));
        String sessionKey = LTNApplication.getInstance().getSessionKey();
        if (sessionKey != null) {
            mReqParams.put(LTNConstants.SESSION_KEY, sessionKey);
        }
        // TODO: 上传location

        WCOKHttpClient.getOkHttpClient().requestAsyn(LTNConstants.ACCESS_URL.GET_SHOPS_URL, WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {

                            if (jsonObject.optInt(LTNConstants.RESULT_CODE) == 0) {
                                if (mShops == null) {
                                    mShops = new ArrayList<Shop>();
                                }

                                JSONObject dataObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                JSONArray resultArray = (JSONArray) dataObj.get(LTNConstants.SHOP_LIST);
                                ArrayList<Shop> shops = new Gson().fromJson(resultArray.toString(),
                                        new TypeToken<ArrayList<Shop>>() {
                                        }.getType());
                                if (shops != null) {
                                    if (isPullDownToRefresh) {
                                        mShops.clear();
                                    }
                                    mShops.addAll(shops);
                                }
                                mShopAdapter.setShops(mShops);
                            }

                            if (mProgressdialog.isShowing()) {
                                mProgressdialog.cancel();
                            }
                            lvProduct.onRefreshComplete();
                            upDateRefreshTime();


                        } catch (JSONException je) {
                            // Log.d(FRAGMENT_TAG, je.getMessage());
                        }
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvProduct.onRefreshComplete();
                        upDateRefreshTime();
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        if (mProgressdialog.isShowing()) {
                            mProgressdialog.cancel();
                        }
                        lvProduct.onRefreshComplete();
                        Utils.isNetworkConnected(getActivity());
                    }
                });


    }

    //更新刷新时间
    private void upDateRefreshTime() {
        String label = DateUtils.formatDateTime(getActivity(),
                System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
                        | DateUtils.FORMAT_SHOW_DATE
                        | DateUtils.FORMAT_ABBREV_ALL);
        lvProduct.getLoadingLayoutProxy().setLastUpdatedLabel(label);// 加上时间
    }

}
