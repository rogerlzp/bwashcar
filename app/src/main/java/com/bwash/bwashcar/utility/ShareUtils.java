package com.bwash.bwashcar.utility;

import android.app.Activity;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.model.User;

/**
 * Created by jiajia on 2016/1/5.
 */
public class ShareUtils {
    //友盟分享
    public static void share(final Activity context, final UMShareListener umShareListener) {
        final UMImage image = new UMImage(context, LTNConstants.ACCESS_URL.SHARE_LOG_URL);
//        UMusic music = new UMusic("http://music.huoxing.com/upload/20130330/1364651263157_1085.mp3");
//        music.setTitle("sdasdasd");
//        music.setThumb(new UMImage(context, "http://www.umeng.com/images/pic/social/chart_1.png"));
//        UMVideo video = new UMVideo("http://video.sina.com.cn/p/sports/cba/v/2013-10-22/144463050817.html");
//        new ShareAction(context).setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SMS)
//                .setContentList(new ShareContent(), new ShareContent())
//                .withMedia(image)
//                .setListenerList(umShareListener, umShareListener)
//                .open();
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SMS
                };
        new ShareAction(context).setDisplayList(displaylist)
                .withText("呵呵")
                .withTitle("title")
                .withTargetUrl("http://www.baidu.com")
                .withMedia(image)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {

                        String hostUrl = LTNConstants.ACCESS_URL.H5_PRODUCT_SHARE_URL;
                        User currentUser = LTNApplication.getInstance().getCurrentUser();
                        if (currentUser != null) {
                            if (!android.text.TextUtils.isEmpty(currentUser.getUserPhone())) { // userPhone 为空
                                hostUrl = hostUrl + "?mobile=" + currentUser.getUserInfo().getMobile();
                            }
                        }
                        if (share_media == SHARE_MEDIA.SMS) {
                            new ShareAction(context).setPlatform(share_media)
                                    .setCallback(umShareListener)
                                    .withText("领投鸟App，2016年最棒的理财神器，约P伤身，约会伤钱，约鸟哥赚钱养生随叫随到，大家一起约起来!\n" + hostUrl)
                                    .withTitle("领投鸟App，2016年最棒的理财神器")
                                    .withTargetUrl(hostUrl)
                                    .withMedia(image)
                                    .share();
                        } else {
                            new ShareAction(context).setPlatform(share_media)
                                    .setCallback(umShareListener)
                                    .withText("约P伤身，约会伤钱，约鸟哥赚钱养生随叫随到，大家一起约起来!")
                                    .withTitle("领投鸟App，2016年最棒的理财神器")
                                    .withTargetUrl(hostUrl)
                                    .withMedia(image)
                                    .share();
                        }
                    }
                })
                .open();

    }


}
