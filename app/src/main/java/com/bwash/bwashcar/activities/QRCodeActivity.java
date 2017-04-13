package com.bwash.bwashcar.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.bwash.bwashcar.R;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.model.User;
import com.bwash.bwashcar.utility.ImageUtils;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.QRCodeUtil;

import java.io.File;

/**
 * Created by rogerlzp on 16/1/22.
 */
public class QRCodeActivity extends BaseActivity implements View.OnClickListener {


    ImageView ivQRCpde;
    int QR_WIDTH = 1000;
    int QR_HEIGHT = 1000;

    String filePath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode);
        initView();
        //

    }

    private void initView() {
        findViewById(R.id.back_btn).setOnClickListener(this);
        findViewById(R.id.btn_save).setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setText("我的二维码");
        ivQRCpde = (ImageView) findViewById(R.id.iv_qrcode);

        String hostUrl = LTNConstants.ACCESS_URL.H5_PRODUCT_SHARE_URL;
        User currentUser = LTNApplication.getInstance().getCurrentUser();
        String userPhone = currentUser.getUserPhone(); //首先从user 里面获取
        if (currentUser != null) {
            if (android.text.TextUtils.isEmpty(userPhone)) { // userPhone 为空
                userPhone = currentUser.getUserInfo().getMobile();
            }
        }
        if (userPhone != null) {
            hostUrl = hostUrl + "?mobile=" + userPhone;
        }
        //      createQRImage(hostUrl);

        filePath = QRCodeUtil.getFileRoot(QRCodeActivity.this) + File.separator
                + "qr_" + System.currentTimeMillis() + ".png";



        QRCodeUtil.createQRImage(hostUrl, QR_WIDTH, QR_HEIGHT,
                ImageLoader.getInstance().loadImageSync("drawable://" + R.drawable.icon160),
                filePath, ivQRCpde);
//
//        QRCodeUtil.createQRImage(hostUrl, QR_WIDTH, QR_HEIGHT,
//                null,
//                filePath, ivQRCpde);
        //    ImageUtils.displayImage(QRCodeActivity.this, filePath, ivQRCpde);
        //  ivQRCpde.setImageBitmap(ImageLoader.getInstance().loadImageSync(filePath));


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.btn_save:
                File file = new File(filePath);
                ImageUtils.saveImageToGallery(QRCodeActivity.this, file);
                Toast.makeText(QRCodeActivity.this, "已经保存图片到系统相册", Toast.LENGTH_SHORT).show();
                break;
        }
    }


}
