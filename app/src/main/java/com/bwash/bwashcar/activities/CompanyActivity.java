package com.bwash.bwashcar.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.imageloader.ImageLoaderProxy;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;
import com.bwash.bwashcar.utility.ImageUtils;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.QRCodeUtil;
import com.bwash.bwashcar.utility.QiniuUtil;
import com.bwash.bwashcar.utility.StringUtils;
import com.bwash.bwashcar.view.UISwitchButton;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.bwash.bwashcar.R;
import com.bwash.bwashcar.utility.LogUtils;
import com.bwash.bwashcar.view.SelectPicturePopupWindow;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;

/**
 * Created by zhengpingli on 2017/4/11.
 */

public class CompanyActivity extends BaseActivity implements SelectPicturePopupWindow.OnSelectedListener {


    //  private TextView uploadResult;  //显示上传结果
    private ProgressDialog progressDialog;  //上传进度提示框
    private UploadManager uploadManager;  //七牛SDK的上传管理者
    private MyUpCompletionHandler mHandler;  //七牛SDK的上传返回监听

    private static final int GALLERY_REQUEST_CODE = 0;    // 相册选图标记
    private static final int CAMERA_REQUEST_CODE = 1;    // 相机拍照标记

    File inFile;
    String fileKey;
    String filePath = null;
    String qiniuUpToken = null;

    // 剪切后图像文件
    private Uri mDestinationUri, sourceUri;

    SelectPicturePopupWindow mSelectPicturePopupWindow;
    private OnPictureSelectedListener mOnPictureSelectedListener;

    // UI 中的 View
    private RadioGroup rg_license_type;

    UISwitchButton toggleButton;

    EditText et_license_name, et_license_reg, et_owner_name, et_owner_id;

    ImageView iv_license_image, iv_owner_id_front, iv_owner_id_back;
    Button btn_license, btn_owner_id_front, btn_owner_id_back, btn_submit;

    SwitchImage switchImage;
    SwitchType switchType;


    int companyType; // 0：企业法人; 1:个人
    public String licenseName;//营业执照名称
    public String licenseRegNo;//营业执照名称
    public String licenseImageUrl;//营业执照图片

    public String proprietorName;//经营者姓名
    public String proprietorID;//经营者身份证号
    public String contactEmailAddress;//经营者邮箱

    public String idFrontUrl;//经营者身份证正面照
    public String idBackUrl;//经营者身份证反面照

    String companyName = "";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_add);
        initView();
        initData();
    }

    public void initView() {
        //  findViewById(R.id.btn_upload).setOnClickListener(this);
        mSelectPicturePopupWindow = new SelectPicturePopupWindow(this);
        mSelectPicturePopupWindow.setOnSelectedListener(this);

        rg_license_type = (RadioGroup) findViewById(R.id.rg_license_type);

        rg_license_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

            }
        });
        toggleButton = (UISwitchButton) findViewById(R.id.toggleButton);
        et_license_name = (EditText) findViewById(R.id.et_license_name);
        et_license_reg = (EditText) findViewById(R.id.et_license_reg);
        et_owner_name = (EditText) findViewById(R.id.et_owner_name);
        et_owner_id = (EditText) findViewById(R.id.et_owner_id);

        iv_license_image = (ImageView) findViewById(R.id.iv_license_image);
        iv_owner_id_front = (ImageView) findViewById(R.id.iv_owner_id_front);
        iv_owner_id_back = (ImageView) findViewById(R.id.iv_owner_id_back);


        btn_license = (Button) findViewById(R.id.btn_license);
        btn_owner_id_front = (Button) findViewById(R.id.btn_owner_id_front);
        btn_owner_id_back = (Button) findViewById(R.id.btn_owner_id_back);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        btn_license.setOnClickListener(this);
        btn_owner_id_front.setOnClickListener(this);
        btn_owner_id_back.setOnClickListener(this);
        btn_submit.setOnClickListener(this);

    }

    public void initData() {

        uploadManager = QiniuUtil.init();


        if (this.progressDialog == null) {
            this.progressDialog = new ProgressDialog(this);
            this.progressDialog.setCancelable(true);
            this.progressDialog.setIndeterminate(true);
            this.progressDialog.setCanceledOnTouchOutside(false);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_license:
                mSelectPicturePopupWindow.showPopupWindow(this);
                switchType = SwitchType.STATUS_1001;
                // uploadSwitcher = SwitchImage.getValue("1001");
                break;
            case R.id.btn_owner_id_front:
                mSelectPicturePopupWindow.showPopupWindow(this);
                //uploadSwitcher = SwitchImage.getValue("1002");
                switchType = SwitchType.STATUS_1002;
                break;
            case R.id.btn_owner_id_back:
                mSelectPicturePopupWindow.showPopupWindow(this);
                //  uploadSwitcher = SwitchImage.getValue("1003");
                switchType = SwitchType.STATUS_1003;
                break;
            case R.id.btn_submit:
                addCompany();
                break;
        }
    }


    RadioGroup.OnCheckedChangeListener myRGlistener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup Group, int Checkid) {
            // TODO Auto-generated method stub
            //设置TextView的内容显示CheckBox的选择结果

        }
    };


    class MyUpCompletionHandler implements UpCompletionHandler {
//        SwitchType switchType;
//
//        public MyUpCompletionHandler(SwitchType switchType) {
//            switchType = switchType;
//        }

        /**
         * @param key      上传时的upKey；
         * @param info     Json串表示的上传信息，包括使用版本，请求状态，请求Id等信息；
         * @param response Json串表示的文件信息，包括文件Hash码，文件Mime类型，文件大小等信息；
         */
        @Override
        public void complete(String key, ResponseInfo info, JSONObject response) {
            progressDialog.dismiss();
            //        uploadResult.setText(key + "!\n" + info + "!\n" + response + "!");

            switch (switchType.getType()) {
                case "license":
                    //
                    licenseImageUrl = key;
                    break;
                case "idfront":
                    idFrontUrl = key;
                    break;
                case "idback":

                    idBackUrl = key;
                    break;
            }

        }
    }

    public void onUploadQiniu() {

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
        // progressDialog.setMax(upLoadData.length);
        if (mHandler == null) {
            mHandler = new MyUpCompletionHandler();
        }
        progressDialog.show();
        switch (switchImage.getValue()) {
            case 1001:
                uploadManager.put(inFile, fileKey, qiniuUpToken, mHandler, null);
                break;
            case 1002:
                uploadManager.put(filePath, fileKey, qiniuUpToken, mHandler, null);
                break;
        }


    }

    @Override
    public void OnSelected(View v, int position) {
        switch (position) {
            case 0:
                // "拍照"按钮被点击了
                switchImage = SwitchImage.STATUS_1001;

                fileKey = switchType.getImageName(switchType.getCode());
                //         "source_" + System.currentTimeMillis() + ".jpg";
                inFile = new File(ImageUtils.getOutDir(), fileKey);
                sourceUri = Uri.fromFile(inFile);
                takePhoto();
                break;
            case 1:
                // "从相册选择"按钮被点击了
                fileKey = switchType.getImageName(switchType.getCode());
                switchImage = SwitchImage.STATUS_1002;
                pickFromGallery();
                break;
            case 2:
                // "取消"按钮被点击了
                mSelectPicturePopupWindow.dismissPopupWindow();
                break;
        }
    }

    private void takePhoto() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    getString(R.string.permission_write_storage_rationale),
                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
        } else {
            mSelectPicturePopupWindow.dismissPopupWindow();
            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            //下面这句指定调用相机拍照后的照片存储的路径
            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, sourceUri);
            startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
        }
    }

    private void pickFromGallery() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
//            Intent intent = new Intent();
//            intent.setType("image/*");
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);

            mSelectPicturePopupWindow.dismissPopupWindow();
            Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
            // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
            pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);

        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:   // 调用相机拍照
                    //  File temp = new File(mTempPhotoPath);
                    // 上传图片到七牛
                    //   startCropActivity(sourceUri);
                    onUploadQiniu();

                    switch (switchType.getType()) {
                        case "license":
                            //
                            ImageLoaderProxy.getInstance().displayImage(this.getApplicationContext(), sourceUri.toString(), iv_license_image, R.drawable.ic_image_holder);
                            break;
                        case "idfront":
                            ImageLoaderProxy.getInstance().displayImage(this.getApplicationContext(), sourceUri.toString(), iv_owner_id_front, R.drawable.ic_image_holder);

                            break;
                        case "idback":
                            ImageLoaderProxy.getInstance().displayImage(this.getApplicationContext(), sourceUri.toString(), iv_owner_id_back, R.drawable.ic_image_holder);


                            break;
                    }
                    break;
                case GALLERY_REQUEST_CODE:  // 直接从相册获取
                    // 上传图片到七牛
                    //    startCropActivity(data.getData());
                    try {
                        Uri uri = data.getData();
                        filePath = ImageUtils.getRealFilePathFromImageUri(this, uri);
                        //    inFile = new File(new URI(uri.toString()));
                    } catch (Exception e) {

                    }
                    onUploadQiniu();
                    switch (switchType.getType()) {
                        case "license":
                            //
                            ImageLoaderProxy.getInstance().displayImage(this.getApplicationContext(), filePath, iv_license_image, R.drawable.ic_image_holder);
                            break;
                        case "idfront":
                            ImageLoaderProxy.getInstance().displayImage(this.getApplicationContext(), filePath, iv_owner_id_front, R.drawable.ic_image_holder);

                            break;
                        case "idback":
                            ImageLoaderProxy.getInstance().displayImage(this.getApplicationContext(), filePath, iv_owner_id_back, R.drawable.ic_image_holder);

                            break;
                    }


                    break;
                case UCrop.REQUEST_CROP:    // 裁剪图片结果
                    handleCropResult(data);
                    break;
                case UCrop.RESULT_ERROR:    // 裁剪图片错误
                    handleCropError(data);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void startCropActivity(Uri uri) {

        UCrop uCrop = UCrop.of(uri, mDestinationUri);
        //初始化UCrop配置
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //是否隐藏底部容器，默认显示
        options.setHideBottomControls(true);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(this, R.color.colorPrimary));
        //是否能调整裁剪框
        options.setFreeStyleCropEnabled(true);
        //UCrop配置
        uCrop.withOptions(options);
        //设置裁剪图片的宽高比，比如16：9
        uCrop.withAspectRatio(1, 1);
        //uCrop.useSourceImageAspectRatio();
        //跳转裁剪页面
        uCrop.start(CompanyActivity.this, 10001);
        //   return cameraScalePath;

//        UCrop.of(uri, mDestinationUri)
//                .withAspectRatio(1, 1)
//                .withMaxResultSize(512, 512)
//                .start(CompanyActivity.this);

    }

    private void handleCropResult(Intent result) {
        //  deleteTempPhotoFile();
        final Uri resultUri = UCrop.getOutput(result);
        if (null != resultUri && null != mOnPictureSelectedListener) {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mOnPictureSelectedListener.onPictureSelected(resultUri, bitmap);
        } else {
            Toast.makeText(this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleCropError(Intent result) {
        //   deleteTempPhotoFile();
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            LogUtils.e("handleCropError: " + cropError.getMessage());
            Toast.makeText(this, cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "无法剪切选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 删除拍照临时文件
     */
    private void deleteTempPhotoFile(String tempFilePath) {
        File tempFile = new File(tempFilePath);
        if (tempFile.exists() && tempFile.isFile()) {
            tempFile.delete();
        }
    }

    public void setOnPictureSelectedListener(OnPictureSelectedListener l) {
        this.mOnPictureSelectedListener = l;
    }

    /**
     * 图片选择的回调接口
     */
    public interface OnPictureSelectedListener {
        /**
         * 图片选择的监听回调
         *
         * @param fileUri
         * @param bitmap
         */
        void onPictureSelected(Uri fileUri, Bitmap bitmap);
    }

    protected static final int REQUEST_STORAGE_READ_ACCESS_PERMISSION = 101;
    protected static final int REQUEST_STORAGE_WRITE_ACCESS_PERMISSION = 102;

    private AlertDialog mAlertDialog;

    /**
     * Hide alert dialog if any.
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
        }
    }


    /**
     * Requests given permission.
     * If the permission has been denied previously, a Dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     */
    protected void requestPermission(final String permission, String rationale, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            showAlertDialog(getString(R.string.permission_title_rationale), rationale,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(CompanyActivity.this,
                                    new String[]{permission}, requestCode);
                        }
                    }, getString(R.string.btn_confirm), null, getString(R.string.btn_cancel));
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    /**
     * This method shows dialog with given title & message.
     * Also there is an option to pass onClickListener for positive & negative button.
     *
     * @param title                         - dialog title
     * @param message                       - dialog message
     * @param onPositiveButtonClickListener - listener for positive button
     * @param positiveText                  - positive button text
     * @param onNegativeButtonClickListener - listener for negative button
     * @param negativeText                  - negative button text
     */
    protected void showAlertDialog(@Nullable String title, @Nullable String message,
                                   @Nullable DialogInterface.OnClickListener onPositiveButtonClickListener,
                                   @NonNull String positiveText,
                                   @Nullable DialogInterface.OnClickListener onNegativeButtonClickListener,
                                   @NonNull String negativeText) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton(positiveText, onPositiveButtonClickListener);
        builder.setNegativeButton(negativeText, onNegativeButtonClickListener);
        mAlertDialog = builder.show();
    }


    /**
     * 获取qiniu upload token
     **/

    public void getQiNiuUpToken() {
        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        //   mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());

        WCOKHttpClient.getOkHttpClient(this).requestAsyn(LTNConstants.ACCESS_URL.GET_QINIU_UPTOKEN,
                WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        try {
                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);
                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {
                                JSONObject resultObj = (JSONObject) jsonObject.get(LTNConstants.DATA);
                                qiniuUpToken = resultObj.getString(LTNConstants.QINIU_UPLOAD_TOKEN);
                            }

                        } catch (JSONException je) {
                            LogUtils.e(je.getMessage());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        LogUtils.e(errorMsg);
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        //如果没有qiniuUpToken的话，则更新upToken
        if (StringUtils.isNullOrEmpty(qiniuUpToken)) {
            getQiNiuUpToken();
        }
    }

    public void validateCompanyParams() {
        switch (rg_license_type.getCheckedRadioButtonId()) {
            case R.id.rb_0:
                companyType = 0;
                break;
            case R.id.rb_1:
                companyType = 1;
                break;
            default:
                companyType = 0;
                break;
        }
        licenseName = et_license_name.getText().toString().trim();
        licenseRegNo = et_license_reg.getText().toString().trim();

        proprietorName = et_owner_name.getText().toString().trim();
        proprietorID = et_owner_id.getText().toString().trim();

        if (StringUtils.isNullOrEmpty(idFrontUrl)) {
            Toast.makeText(CompanyActivity.this, getResources().getString(R.string.get_id_front_image),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(idBackUrl)) {
            Toast.makeText(CompanyActivity.this, getResources().getString(R.string.get_id_back_image),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(licenseImageUrl)) {
            Toast.makeText(CompanyActivity.this, getResources().getString(R.string.license_image_url),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(licenseRegNo)) {
            Toast.makeText(CompanyActivity.this, getResources().getString(R.string.license_reg_hint),
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isNullOrEmpty(proprietorName)) {
            Toast.makeText(CompanyActivity.this, getResources().getString(R.string.owner_name_hint),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (StringUtils.isNullOrEmpty(proprietorID)) {
            Toast.makeText(CompanyActivity.this, getResources().getString(R.string.owner_id_hint),
                    Toast.LENGTH_SHORT).show();
            return;
        }

    }

    public void addCompany() {
        validateCompanyParams();

        HashMap<String, String> mReqParams = new HashMap();
        mReqParams.put(LTNConstants.CLIENT_TYPE_PARAM, LTNConstants.CLIENT_TYPE_MOBILE);
        mReqParams.put(LTNConstants.SESSION_KEY, LTNApplication.getInstance().getSessionKey());
        mReqParams.put(LTNConstants.COMPANY_TYPE, "" + companyType);
        mReqParams.put(LTNConstants.COMPANY_NAME, "" + companyName);
        mReqParams.put(LTNConstants.LICENSE_NAME, licenseName);
        mReqParams.put(LTNConstants.LICENSE_IMAGE_URL, licenseImageUrl);
        mReqParams.put(LTNConstants.LICENSE_REG_NO, licenseRegNo);
        mReqParams.put(LTNConstants.PROPRIETOR_NAME, proprietorName);
        mReqParams.put(LTNConstants.PROPRIETOR_ID, "" + proprietorID);
        mReqParams.put(LTNConstants.PROPRIETOR_ID_FRONT_IMAGE, "" + idFrontUrl);
        mReqParams.put(LTNConstants.PROPRIETOR_ID_BACK_IMAGE, "" + idBackUrl);


        WCOKHttpClient.getOkHttpClient(this).requestAsyn(LTNConstants.ACCESS_URL.COMPANY_ADD_URL,
                WCOKHttpClient.TYPE_GET, mReqParams,
                new ReqCallBack<JSONObject>() {

                    @Override
                    public void onReqSuccess(JSONObject jsonObject) {
                        dismissProgressDialog();
                        try {
                            String resultCode = jsonObject.getString(LTNConstants.RESULT_CODE);
                            // 正确
                            if (resultCode.equals(LTNConstants.MSG_SUCCESS)) {

                                Toast.makeText(CompanyActivity.this, "添加成功", Toast.LENGTH_SHORT);
                                setResult(LTNConstants.ADD_CAR_SUCCESS);
                                finish();

                            } else {
                                String resultMsg = jsonObject.getString(LTNConstants.RESULT_MESSAGE);

                            }
                        } catch (JSONException je) {
                            // Log.d(TAG, je.getMessage());
                        }

                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        Toast.makeText(CompanyActivity.this, R.string.ERROR_CODE_1, Toast.LENGTH_SHORT);
                        dismissProgressDialog();
                    }

                });


    }


    enum SwitchType {
        STATUS_O("0", "0"),
        STATUS_1001("1001", "license"),
        STATUS_1002("1002", "idfront"),
        STATUS_1003("1003", "idback");
        private String code;
        private int value;
        private String type;

        SwitchType(String code, String type) {
            this.code = code;
            this.type = type;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        //返回图片名字
        public static String getImageName(String code) {
            for (SwitchType switchType :
                    SwitchType.values()) {
                if (switchType.code.equalsIgnoreCase(code)) {
                    //   return LTNApplication.getInstance().getCurrentUser().getUserPhone() + switchType.getType() + ".jpg";
                    return switchType.getType() + ".jpg";
                }
            }
            return "";
        }

    }

    enum SwitchImage {
        STATUS_O("0", 0),
        STATUS_1001("1001", 1001),
        STATUS_1002("1002", 1002);


        private String code;
        private int value;

        SwitchImage(String code, int value) {
            this.code = code;
            this.value = value;
        }


        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public static int getValue(String code) {
            for (SwitchImage switchImage :
                    SwitchImage.values()) {
                if (switchImage.code.equalsIgnoreCase(code)) {
                    return switchImage.getValue();
                }
            }
            return 0;
        }
    }
}