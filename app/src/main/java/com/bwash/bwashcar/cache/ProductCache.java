package com.bwash.bwashcar.cache;

import android.content.Context;

import com.bwash.bwashcar.adapter.ProductAdapter;
import com.bwash.bwashcar.model.Product;

import java.util.ArrayList;

/**
 * Created by rogerlzp on 15/12/16.
 */
public class ProductCache {
    public ArrayList<Product> mProducts;
     public ProductAdapter mProductAdapter;

    public int nDisplayNum;
    public Boolean hasShowAllProducts;
    public int currentPage;

    public ProductCache(Context ctx) {
        mProducts = new ArrayList<Product>();
        hasShowAllProducts = false;
        mProductAdapter = new ProductAdapter(ctx);
        currentPage = 0;// 从0 开始计算
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void increaseCurrentPage() {
        this.currentPage++;
    }

    public int getDisplayNum() {
        return nDisplayNum;
    }

    public void setDisplayNum(int nDisplayNum) {
        this.nDisplayNum = nDisplayNum;
    }

}
