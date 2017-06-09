package com.yhy.fmhelper.manager;

import android.support.v4.app.Fragment;

import com.yhy.fmhelper.callback.OnFmCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by HongYi Yan on 2017/6/5 17:12.
 */
public class FmCbManager {
    private volatile static FmCbManager instance;

    private Map<Fragment, OnFmCallBack> mCbMap;

    private FmCbManager() {
        if (null != instance) {
            throw new IllegalStateException("Can not instantiate Singleton class.");
        }

        mCbMap = new HashMap<>();
    }

    public static FmCbManager getInstance() {
        if (null == instance) {
            synchronized (FmCbManager.class) {
                if (null == instance) {
                    instance = new FmCbManager();
                }
            }
        }
        return instance;
    }

    public void registFmCallBack(Fragment fm, OnFmCallBack obs) {
        mCbMap.put(fm, obs);
    }

    public void unRegistFmCallBack(Fragment fm, OnFmCallBack obs) {
        if (mCbMap.containsKey(fm) && mCbMap.get(fm) == obs) {
            mCbMap.remove(fm);
        }
    }

    public OnFmCallBack getCallBack(Fragment fm) {
        return null != fm && mCbMap.containsKey(fm) ? mCbMap.get(fm) : null;
    }
}
