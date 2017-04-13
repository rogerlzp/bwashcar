package com.bwash.bwashcar.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.bwash.bwashcar.R;
import com.bwash.bwashcar.activities.FeedBackActivity;
import com.bwash.bwashcar.activities.LTNWebPageActivity;
import com.bwash.bwashcar.activities.LoginActivity;
import com.bwash.bwashcar.activities.SetHostActivity;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.utility.Constant;
import com.bwash.bwashcar.utility.ImageUtils;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.LogUtils;
import com.bwash.bwashcar.utility.MarketUtils;
import com.bwash.bwashcar.utility.ShareUtils;
import com.bwash.bwashcar.utility.Utils;
import com.bwash.bwashcar.utility.ViewUtils;


/**
 * 关于我们
 */
public class LTNMoreFragment extends BaseFragment implements OnClickListener {

    public static String FRAGMENT_TAG = LTNMoreFragment.class.getSimpleName();
    private View rootView;
    private boolean isPrepared;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tab_more, null);
        initView();
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_feedback:
                Intent intent = null;
                if (TextUtils.isEmpty(LTNApplication.getInstance().getSessionKey())) {
                    intent = new Intent(getActivity(), LoginActivity.class);
                    Bundle b = new Bundle();
                    b.putString(Constant.LoginParams.LOGIN_TYPE, Constant.LoginParams.LOGIN_TO_FEEDBACK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtras(b);
                } else {
                    intent = new Intent(getActivity(), FeedBackActivity.class);
                }
                startActivity(intent);

                break;
            case R.id.tv_score:
                //TODO: 等在应用市场确定后在修改

                int result = MarketUtils.mainMarkets(getActivity());

                try {
                    Uri uri = Uri.parse("market://details?id=" + "com.wy.lingtouniao");
                    Intent intent1 = new Intent(Intent.ACTION_VIEW, uri);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    switch (result) {
                        case 1:
                            intent1.setPackage(MarketUtils.MARKET_YINGYONGBAO_APP);
                            break;
                        case 2:
                            intent1.setPackage(MarketUtils.MARKET_360_APP);
                            break;
                        default:
                            break;
                    }
                    startActivity(intent1);
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "请先去安装应用宝或者其他主流应用市场!", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.tv_about:
                intent = new Intent(getActivity(), LTNWebPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(LTNWebPageActivity.BUNDLE_URL, LTNConstants.ACCESS_URL.H5_ABOUT_URL);
                bundle.putString(LTNWebPageActivity.BUNDLE_TITLEBAR, "关于领投鸟理财");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_phone:
                ViewUtils.showCallDialog(getActivity(), LTNConstants.PHONE_NUMBER);
                break;
            case R.id.tv_share:
                ShareUtils.share(getActivity(), umShareListener);
                break;
            case R.id.set_host:
                intent = new Intent(getActivity(), SetHostActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_help:
                intent = new Intent(getActivity(), LTNWebPageActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putString(LTNWebPageActivity.BUNDLE_URL, LTNConstants.ACCESS_URL.H5_HELP_URL);
                bundle1.putString(LTNWebPageActivity.BUNDLE_TITLEBAR, "帮助中心");
                intent.putExtras(bundle1);
                startActivity(intent);
                break;
        }
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(getActivity(), platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(getActivity(), platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private void initView() {
        rootView.findViewById(R.id.back_btn).setVisibility(View.GONE);
        ((TextView) rootView.findViewById(R.id.title)).setText(getString(R.string.tab_more));
        rootView.findViewById(R.id.tv_feedback).setOnClickListener(this);
        rootView.findViewById(R.id.tv_score).setOnClickListener(this);
        rootView.findViewById(R.id.tv_about).setOnClickListener(this);
        rootView.findViewById(R.id.tv_phone).setOnClickListener(this);
        rootView.findViewById(R.id.tv_share).setOnClickListener(this);
        rootView.findViewById(R.id.tv_help).setOnClickListener(this);
        rootView.findViewById(R.id.set_host).setVisibility(LogUtils.isDebug ? View.VISIBLE : View.GONE);
        rootView.findViewById(R.id.set_host).setOnClickListener(this);
        String versionCode = Utils.getAppVersionName(getActivity());
        if (!TextUtils.isEmpty(versionCode)) {
            rootView.findViewById(R.id.versionCode).setVisibility(View.VISIBLE);
            ((TextView) rootView.findViewById(R.id.versionCode)).setText("版本号：" + versionCode);
        }

        rootView.findViewById(R.id.rl_jpush).setVisibility(LogUtils.isDebug ? View.VISIBLE : View.GONE);
        ((TextView) rootView.findViewById(R.id.tv_jpush_id_value)).setText(LTNApplication.getInstance().getJPushID());

        ImageView imageview = (ImageView) rootView.findViewById(R.id.iv_banner);
        ImageUtils.getInstance().displayImageDrawable(getActivity(), R.drawable.lingtouniao, imageview);
    }

    //	public void initTitle() {
//		((TextView) rootView.findViewById(android.R.id.title)).setText(getString(R.string.app_name));
//		rootView.findViewById(R.id.left_menu_button).setVisibility(View.GONE);
//		rootView.findViewById(R.id.right_menu_commit).setVisibility(View.GONE);
//		rootView.findViewById(R.id.right_menu_button).setVisibility(View.GONE);
//	}
    @Override
    protected void lazyLoad() {
        //在调用了onCreateView后并且fragment的UI是可见的就填充数据
        //如果是要下载网络数据，不是给view填充数据之类的，就不需要isPrepared参数了
        if (!isPrepared || !isVisible) return;
        //填充各控件的数据
    }


}
