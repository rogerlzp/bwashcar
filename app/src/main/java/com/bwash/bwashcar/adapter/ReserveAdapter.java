package com.bwash.bwashcar.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.activities.ConfirmReserveActivity;
import com.bwash.bwashcar.activities.ShopDetailActivity;
import com.bwash.bwashcar.application.LTNApplication;
import com.bwash.bwashcar.imageloader.ImageLoaderProxy;
import com.bwash.bwashcar.model.BReserve;
import com.bwash.bwashcar.model.Shop;
import com.bwash.bwashcar.model.ShopService;
import com.bwash.bwashcar.net.ReqCallBack;
import com.bwash.bwashcar.net.WCOKHttpClient;
import com.bwash.bwashcar.utility.LTNConstants;
import com.bwash.bwashcar.utility.StringUtils;
import com.bwash.bwashcar.utility.Utils;
import com.bwash.bwashcar.view.ProductTagView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by zhengpingli on 2017/4/13.
 */

public class ReserveAdapter extends BaseAdapter {

    private static final String TAG = ShopAdapter.class.getSimpleName();
    public ArrayList<BReserve> mBReserves = new ArrayList<BReserve>();
    public Context ctx;

    // 当前时间
    SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    ConfirmInterface onConfirmInterface;

    public ReserveAdapter(Context context, ConfirmInterface onConfirmInterface) {
        this.ctx = context;
        this.onConfirmInterface = onConfirmInterface;
        // 当前时间
    }

    public void setBReserves(ArrayList<BReserve> _mShops) {
        mBReserves = _mShops;
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ReserveAdapter.ViewHolder holder;
        if (convertView == null) {
            convertView = (ViewGroup) LayoutInflater.from(ctx).inflate(
                    R.layout.reserve_item, null);
            holder = new ReserveAdapter.ViewHolder();
            holder.tv_customer = (TextView) convertView.findViewById(R.id.tv_customer);
            holder.tv_contact = (TextView) convertView.findViewById(R.id.tv_contact);
            holder.tv_car = (TextView) convertView.findViewById(R.id.tv_car);
            holder.tv_credit = (TextView) convertView.findViewById(R.id.tv_credit);
            holder.tv_reserve_name = (TextView) convertView.findViewById(R.id.tv_reserve_name);
            holder.tv_reserve_time = (TextView) convertView.findViewById(R.id.tv_reserve_time);
            holder.tv_confirm = (TextView) convertView.findViewById(R.id.tv_confirm);
            holder.tv_cancel = (TextView) convertView.findViewById(R.id.tv_cancel);
            holder.iv_user = (ImageView) convertView.findViewById(R.id.iv_user);
            holder.divide_tag = convertView.findViewById(R.id.divide_tag);


            convertView.setTag(holder);
        } else {
            holder = (ReserveAdapter.ViewHolder) convertView.getTag();
        }

        BReserve bReserve = mBReserves.get(position);
        ReserveAdapter.ShopDetailListener shopDetailListener = new ReserveAdapter.ShopDetailListener(ctx, bReserve);
        convertView.setOnClickListener(shopDetailListener);

        holder.tv_customer.setText(bReserve.getCustomerName());
        holder.tv_contact.setText(bReserve.getCustomerPhone());
        holder.tv_car.setText(bReserve.getCarName());

        if (!StringUtils.isNullOrEmpty(String.valueOf(bReserve.getCustomerCredit()))) {
            holder.tv_credit.setText("" + bReserve.getCustomerCredit());
        }

        List<ShopService> reserveProductList = bReserve.getReserveProductList();
        if (reserveProductList.size() != 0) {
            holder.tv_reserve_name.setText(reserveProductList.get(0).getServiceName());
        }

        holder.tv_reserve_time.setText(bReserve.getReserveServiceTime());

        if (!StringUtils.isNullOrEmpty(String.valueOf(bReserve.getCustomerImageUrl()))) {
            ImageLoaderProxy.getInstance().displayImage(ctx, bReserve.getCustomerImageUrl(), holder.iv_user, R.drawable.ic_image_holder);
        }

        ReserveAdapter.ConfirmListener confirmListener = new ReserveAdapter.ConfirmListener(ctx, bReserve);
        holder.tv_confirm.setOnClickListener(confirmListener);

        ReserveAdapter.CancelListener cancelListener = new ReserveAdapter.CancelListener(ctx, bReserve);
        holder.tv_cancel.setOnClickListener(cancelListener);

        // 首先判断是否是体验标


        return convertView;
    }

    private class ViewHolder {
        TextView tv_customer;
        TextView tv_contact;
        TextView tv_car;
        TextView tv_credit;
        TextView tv_reserve_name;
        TextView tv_reserve_time;
        TextView tv_confirm;
        TextView tv_cancel;
        ImageView iv_user;

        View divide_tag;
    }


    class ShopDetailListener implements View.OnClickListener {
        BReserve bReserve;
        public Context ctx;

        public ShopDetailListener(Context _ctx, BReserve _shop) {
            ctx = _ctx;
            this.bReserve = _shop;
        }


        /**
         * 体验标跳转到体验标
         * 散标跳转到散标
         *
         * @param v
         */
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(ctx, ShopDetailActivity.class);
            Bundle bundle = new Bundle();

            intent.putExtras(bundle);
            ctx.startActivity(intent);
        }

    }

    public interface ConfirmInterface {
        public void onConfirmClicked(BReserve bReserve);
    }

    class ConfirmListener implements View.OnClickListener {
        BReserve bReserve;
        public Context ctx;

        public ConfirmListener(Context _ctx, BReserve _shop) {
            ctx = _ctx;
            this.bReserve = _shop;
        }


        /**
         * 体验标跳转到体验标
         * 散标跳转到散标
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            if (onConfirmInterface != null) {
                onConfirmInterface.onConfirmClicked(this.bReserve);
            }
//            Intent intent = new Intent(ctx, ShopDetailActivity.class);
//            Bundle bundle = new Bundle();
//            intent.putExtras(bundle);
//            ctx.startActivity(intent);
        }

    }


    class CancelListener implements View.OnClickListener {
        BReserve bReserve;
        public Context ctx;

        public CancelListener(Context _ctx, BReserve _shop) {
            ctx = _ctx;
            this.bReserve = _shop;
        }


        /**
         * 体验标跳转到体验标
         * 散标跳转到散标
         *
         * @param v
         */
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(ctx, ShopDetailActivity.class);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        }

    }


    public int getCount() {
        // TODO Auto-generated method stub
        return mBReserves == null ? 0 : mBReserves.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
}
