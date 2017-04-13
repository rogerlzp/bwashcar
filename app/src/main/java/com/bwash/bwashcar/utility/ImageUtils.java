package com.bwash.bwashcar.utility;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.bwash.bwashcar.application.LTNApplication;

import java.io.File;
import java.io.FileNotFoundException;

public class ImageUtils {
    private static ImageUtils mImageUtils = null;
    public static ImageLoader mImageLoader = null;

    //	private static int sWidth = 0;
    private ImageUtils() {

    }

    public static ImageUtils getInstance() {
        if (mImageUtils == null) {
            mImageUtils = new ImageUtils();
            mImageLoader = ImageLoader.getInstance();
//			sWidth = WindowUtil.getWindowWidth(MyApplication.getContext());
        }
        return mImageUtils;
    }

    public void stopLoad() {
        if (mImageLoader != null && mImageLoader.isInited()) {
            mImageLoader.stop();
        }
    }

    public void displayRoundImage(Context context, String uri, ImageView view, int loadRes, int errorRes) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadRes) //设置加载中图片
                .showImageForEmptyUri(errorRes)
                .showImageOnFail(errorRes)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                  // 设置下载的图片是否缓存在SD卡中
                .displayer(new RoundedBitmapDisplayer(15))  // 设置成圆角图片
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(LTNApplication.getInstance())
                .defaultDisplayImageOptions(options).build();
        mImageLoader.init(configuration);
        mImageLoader.displayImage(uri, view, options);
        //		Picasso.with(context).load(uri).placeholder(loadRes).error(errorRes).into(view);
    }

    public void displayImage(Context context, String uri, ImageView view, int loadRes, int errorRes) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadRes) //设置加载中图片
                .showImageForEmptyUri(errorRes)
                .showImageOnFail(errorRes)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                  // 设置下载的图片是否缓存在SD卡中
                .displayer(new FadeInBitmapDisplayer(500))
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(LTNApplication.getInstance())
                .defaultDisplayImageOptions(options).build();
        mImageLoader.init(configuration);
        mImageLoader.displayImage(uri, view, options);
        //		 Picasso.with(context).load(uri).placeholder(loadRes).error(errorRes).into(view);
    }

    public static void displayImage(Context context, String uri, ImageView view) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                  // 设置下载的图片是否缓存在SD卡中
                .displayer(new FadeInBitmapDisplayer(500))
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(LTNApplication.getInstance())
                .defaultDisplayImageOptions(options).build();
        mImageLoader.init(configuration);
        mImageLoader.displayImage(uri, view, options);
        //		 Picasso.with(context).load(uri).placeholder(loadRes).error(errorRes).into(view);
    }

    public void displayImageDrawable(Context context, int imageId, ImageView view) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                  // 设置下载的图片是否缓存在SD卡中
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(LTNApplication.getInstance())
                .defaultDisplayImageOptions(options).build();
        mImageLoader.init(configuration);
        mImageLoader.displayImage("drawable://" + imageId,
                view);
        //		 Picasso.with(context).load(uri).placeholder(loadRes).error(errorRes).into(view);
    }

    public void displayImageNoFade(Context context, String uri, ImageView view, int loadRes, int errorRes) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadRes) //设置加载中图片
                .showImageForEmptyUri(errorRes)
                .showImageOnFail(errorRes)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                  // 设置下载的图片是否缓存在SD卡中
//		.displayer(new FadeInBitmapDisplayer(500)) 
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(LTNApplication.getInstance())
                .defaultDisplayImageOptions(options).build();
        mImageLoader.init(configuration);
        mImageLoader.displayImage(uri, view, options);
        //		 Picasso.with(context).load(uri).placeholder(loadRes).error(errorRes).into(view);
    }

    //	new ImageLoadingListener() {
//
//		@Override
//		public void onLoadingStarted(String arg0, View arg1) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
//			// TODO Auto-generated method stub
//			ImageView imageView = (ImageView)arg1;
//			if(imageView!=null){
//				mImageLoader.cancelDisplayTask(imageView);
//			}
//			mImageLoader.clearMemoryCache();
//		}
//
//		@Override
//		public void onLoadingComplete(String arg0, View arg1, Bitmap bitmap) {
//			// TODO Auto-generated method stub
//			ImageView imageView = (ImageView)arg1;
//			if(imageView!=null){
//				try {
//					int w = (bitmap.getWidth()>sWidth)?sWidth:bitmap.getWidth();
//					int h = bitmap.getHeight();
//					if(w>sWidth){
//						w = sWidth;
//						h = (int) (((float)w*(float)bitmap.getHeight())/bitmap.getWidth());
//						imageView.setImageBitmap(resizeImage(bitmap, w, h));
//					}else{
//						imageView.setImageBitmap(bitmap);
//					}
//				} catch (Exception e) {
//					// TODO: handle exception
//					imageView.setImageBitmap(bitmap);
//				}
//			}
//		}
//
//		@Override
//		public void onLoadingCancelled(String arg0, View arg1) {
//			// TODO Auto-generated method stub
//			ImageView imageView = (ImageView)arg1;
//			if(imageView!=null){
//				mImageLoader.cancelDisplayTask(imageView);
//			}
//		}
//	}
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h) {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

    public void displayImage(String uri, ImageView view, int loadRes, int errorRes, ImageLoadingListener listener) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(loadRes) //设置加载中图片
                .showImageForEmptyUri(errorRes)
                .showImageOnFail(errorRes)       // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                .cacheOnDisk(true)                  // 设置下载的图片是否缓存在SD卡中
                .build();                                   // 创建配置过得DisplayImageOption对象
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(LTNApplication.getInstance())
                .defaultDisplayImageOptions(options).build();
        mImageLoader.init(configuration);
        mImageLoader.displayImage(uri, view, options, listener);
    }

    public void pause() {
        // TODO Auto-generated method stub
        if (mImageLoader != null && mImageLoader.isInited())
            mImageLoader.pause();
    }

    public void resume() {
        // TODO Auto-generated method stub
        if (mImageLoader != null && mImageLoader.isInited())
            mImageLoader.resume();
    }

    //清除图片缓存
    public static void clear() {
        // TODO Auto-generated method stub
        if (mImageLoader != null && mImageLoader != null) {
            mImageLoader.clearDiskCache();
            mImageLoader.clearMemoryCache();
        }
    }

    // 保存图片到相册
    public static void saveImageToGallery(Context context, File file) {
        // 把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(context.getContentResolver(), file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // 通知图库更新
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getAbsolutePath())));

    }

    public static String getRealFilePathFromImageUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public static File getOutDir() {
        File outDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
        return outDir;
    }
}
