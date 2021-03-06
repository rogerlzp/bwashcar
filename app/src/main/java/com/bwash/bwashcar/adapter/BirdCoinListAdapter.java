package com.bwash.bwashcar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwash.bwashcar.R;
import com.bwash.bwashcar.model.BirdCoin;
import com.bwash.bwashcar.utility.TextUtils;

import java.util.ArrayList;

/**
 * Created by rogerlzp on 15/12/29.
 */
public class BirdCoinListAdapter extends BaseAdapter {
    public ArrayList<BirdCoin> birdCoins = null;
    public Context ctx;

    public BirdCoinListAdapter(Context context, ArrayList<BirdCoin> birdCoins) {
        this.ctx = context;
        this.birdCoins = birdCoins;
    }

    public void setData(ArrayList<BirdCoin> birdCoins) {
        this.birdCoins = birdCoins;
        notifyDataSetChanged();
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        BirdCoin birdCoin = birdCoins.get(position);
        ViewHolder holder;

        if (convertView == null) {
            convertView = (ViewGroup) LayoutInflater.from(ctx).inflate(
                    R.layout.bird_list_item, null);
            holder = new ViewHolder();
            holder.tv_type = (TextView) convertView.findViewById(R.id.type);
            holder.tv_time = (TextView) convertView.findViewById(R.id.time);
            holder.tv_balance = (TextView) convertView.findViewById(R.id.balance);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_type.setText(birdCoin.typeName);
        holder.tv_time.setText(birdCoin.createDate);
        holder.tv_balance.setText(TextUtils.formatDoubleValue(birdCoin.amount));
        return convertView;
    }


    private class ViewHolder {
        TextView tv_type;
        TextView tv_time;
        TextView tv_balance;
    }

    public int getCount() {
        // TODO Auto-generated method stub
//        return balances==null?0:balances.size();
        return birdCoins==null?0:birdCoins.size();
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

