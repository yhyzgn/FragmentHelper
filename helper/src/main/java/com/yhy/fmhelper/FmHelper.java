package com.yhy.fmhelper;


import androidx.annotation.IdRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.yhy.fmhelper.manager.FmCbManager;
import com.yhy.fmhelper.callback.OnFmCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HongYi Yan on 2017/5/25 15:10.
 */
public class FmHelper {
    // Fragment进入动画
    private static final int DEF_HELPER_ANIM_ENTER = R.anim.helper_slide_in_right;
    // Fragment退出动画
    private static final int DEF_HELPER_ANIM_EXIT = R.anim.helper_slide_out_left;
    // 用来保存Fragment栈的集合
    private final List<Fragment> mFmList;
    // 当前Activity
    private FragmentActivity mActivity;
    // 显示Fragment的布局容器ViewId
    @IdRes
    private int mContainerId;
    // 是否需要显示根布局
    private boolean mShowRoot;
    // Fragment管理器
    private FragmentManager mFm;
    // 父Fragment，针对多级Fragment
    private Fragment mParent;
    // 当前显示的Fragment
    private Fragment mCurrentFm;
    // 进入动画
    private int mAnimEnter;
    // 退出动画
    private int mAnimExit;

    /**
     * 私有化构造方法，使用构造器创建对象
     *
     * @param builder 构造器
     */
    private FmHelper(Builder builder) {
        if (null == builder.parent) {
            // Activity直接接管Fragment
            mActivity = builder.activity;
            // 获取到Activity的Fragment管理器
            mFm = mActivity.getSupportFragmentManager();
        } else {
            // 用于多级Fragment，由父Fragment接管子级Fragment
            mParent = builder.parent;
            // 从父Fragment中获取到Activity
            mActivity = mParent.getActivity();
            // 获取到Fragment针对子级的管理器
            mFm = mParent.getChildFragmentManager();
        }
        mContainerId = builder.containerId;
        mShowRoot = builder.showRoot;

        // 切换动画
        if (builder.mAnimEnter > 0 && builder.mAnimExit > 0) {
            mAnimEnter = builder.mAnimEnter;
            mAnimExit = builder.mAnimExit;
        } else {
            mAnimEnter = DEF_HELPER_ANIM_ENTER;
            mAnimExit = DEF_HELPER_ANIM_EXIT;
        }

        mFmList = new ArrayList<>();
    }

    /**
     * 打开一个Fragment页面，不关闭当前页面
     *
     * @param fm Fragment页面
     * @return 当前对象
     */
    public FmHelper open(Fragment fm) {
        return open(fm, false);
    }

    /**
     * 打开一个Fragment页面
     *
     * @param fm           Fragment页面
     * @param closeCurrent 是否关闭当前页面
     * @return 当前对象
     */
    public FmHelper open(Fragment fm, boolean closeCurrent) {
        if (fm.isAdded()) {
            // 如果已经添加过Fragment，就控制隐藏、移除和显示
            if (null != mCurrentFm) {
                if (closeCurrent) {
                    // 如果允许关闭当前页面，就移除当前页面
                    remove(mCurrentFm);
                } else {
                    hide(mCurrentFm);
                }
            }
            // 显示要显示的页面
            show(fm);
        } else {
            // 否则就添加或者替换
            if (closeCurrent) {
                replace(fm);
            } else {
                add(fm);
            }
        }

        return this;
    }

    /**
     * 关闭某个Fragment页面
     *
     * @param fm 需要关闭的Fragment页面
     * @return 当前对象
     */
    public FmHelper close(Fragment fm) {
        remove(fm);
        return this;
    }

    /**
     * 返回
     * <p>
     * 同时处理了多个Fragment返回和Activity自动返回
     *
     * @return 是否还可以继续回退Fragment
     */
    public boolean back() {
        return back(true);
    }

    /**
     * 返回
     * <p>
     * 同时处理了多个Fragment返回和Activity自动返回
     *
     * @param autoFinish 是否允许自动关闭Activity
     * @return 是否还可以继续回退Fragment栈，true表示还能继续回退，false表示不能再回退了
     */
    public boolean back(boolean autoFinish) {
        // 默认返回时保留最后一个Fragment，即直属于Activity的第一个Fragment
        int safeCount = 1;

        if (mShowRoot) {
            // 需要显示根布局，关闭到一个页面都不剩余
            safeCount = 0;
        }

        // 当Fragment栈大小大于需要剩余的页面数量时，就关闭最后一个页面
        if (mFmList.size() > safeCount) {
            close(getLastFm());
            return true;
        }

        // 如果允许自动关闭当前Activity，就直接finish()
        if (autoFinish) {
            mActivity.finish();
        }

        // 返回false，表示不能再回退Fragment栈了
        return false;
    }

    /**
     * 获取到最后一个Fragment
     *
     * @return 最后一个Fragment
     */
    public Fragment getLastFm() {
        return null != mFmList && !mFmList.isEmpty() ? mFmList.get(mFmList.size() - 1) : null;
    }

    private FmHelper add(Fragment fm) {
        // 开启事务添加页面
        mFm.beginTransaction()
                .setCustomAnimations(mAnimEnter, mAnimExit)
                .add(mContainerId, fm)
                .commit();

        // 由于上述commit()是延时操作，在这里强制及时执行事务，就能避免很多Fragment中的NullPointerException异常，比如getActivity()为null
        mFm.executePendingTransactions();

        // 主动触发页面的显示和隐藏回调
        if (null != mCurrentFm) {
            mCurrentFm.setUserVisibleHint(false);
        }
        fm.setUserVisibleHint(true);

        // 更新当前Fragment
        mCurrentFm = fm;
        // Fragment入栈
        mFmList.add(fm);

        return this;
    }

    /**
     * 替换Fragment
     *
     * @param fm 需要显示的Fragment
     * @return
     */
    private FmHelper replace(Fragment fm) {
        mFm.beginTransaction()
                .setCustomAnimations(mAnimEnter, mAnimExit)
                .replace(mContainerId, fm)
                .commit();

        mFm.executePendingTransactions();

        if (null != mCurrentFm) {
            mCurrentFm.setUserVisibleHint(false);

            // 将当前Fragment从栈中移除
            mFmList.remove(mCurrentFm);
        }
        fm.setUserVisibleHint(true);

        mCurrentFm = fm;
        mFmList.add(fm);

        return this;
    }

    /**
     * 显示某个Fragment
     *
     * @param fm 要显示的Fragment
     * @return 当前对象
     */
    private FmHelper show(Fragment fm) {
        mFm.beginTransaction()
                .setCustomAnimations(mAnimEnter, mAnimExit)
                .show(fm)
                .commit();
        mFm.executePendingTransactions();

        if (null != mCurrentFm) {
            mCurrentFm.setUserVisibleHint(false);
        }
        fm.setUserVisibleHint(true);

        mCurrentFm = fm;

        return this;
    }

    /**
     * 隐藏某个Fragment
     *
     * @param fm 需要隐藏的Fragment
     * @return 当前对象
     */
    private FmHelper hide(Fragment fm) {
        mFm.beginTransaction()
                .setCustomAnimations(mAnimEnter, mAnimExit)
                .hide(fm)
                .commit();
        mFm.executePendingTransactions();

        fm.setUserVisibleHint(false);

        mCurrentFm = getLastFm();
        if (null != mCurrentFm) {
            mCurrentFm.setUserVisibleHint(true);
        }

        return this;
    }

    /**
     * 移除某个Fragment
     * <p>
     * 在将要显示的Fragment的setUserVisibleHint()方法中能获取到被移除页面返回的参数
     *
     * @param fm 要移除的Fragment
     * @return 当前对象
     */
    private FmHelper remove(Fragment fm) {
        mFm.beginTransaction()
                .setCustomAnimations(mAnimEnter, mAnimExit)
                .remove(fm)
                .commit();
        mFm.executePendingTransactions();

        mFmList.remove(fm);

        mCurrentFm = getLastFm();

        fm.setUserVisibleHint(false);
        if (null != mCurrentFm) {
            // 采用回调方式，模拟onActivityResult()方法了

            // 当前页面的回调对象，也就是将要重新显示的页面
            OnFmCallBack currentFmCb = FmCbManager.getInstance().getCallBack(mCurrentFm);
            // 上一个页面的回调对象
            OnFmCallBack lastFmCb = FmCbManager.getInstance().getCallBack(fm);

            if (null != currentFmCb && null != lastFmCb) {
                currentFmCb.onResult(lastFmCb.getResult());
            }

            // 回调setUserVisibleHint()方法
            mCurrentFm.setUserVisibleHint(true);
        }

        return this;
    }

    /**
     * Helper构造器
     */
    public static class Builder {
        // 当前Activity
        private FragmentActivity activity;
        // 显示Fragment的容器ViewId
        @IdRes
        private int containerId;
        // 父Fragment
        private Fragment parent;
        // 是否允许单独显示根布局
        private boolean showRoot;
        private int mAnimEnter;
        private int mAnimExit;

        /**
         * Activity中专用构造器，用于单层Fragment
         *
         * @param activity    当前Activity
         * @param containerId 容器ViewId
         */
        public Builder(FragmentActivity activity, @IdRes int containerId) {
            this.activity = activity;
            this.containerId = containerId;
        }

        /**
         * Fragment中专用构造器，用于嵌套Fragment
         *
         * @param parent      当前Fragment，即父Fragment
         * @param containerId 容器ViewId
         */
        public Builder(Fragment parent, @IdRes int containerId) {
            this.parent = parent;
            this.containerId = containerId;
        }

        /**
         * 是否需要显示根布局
         *
         * @param showRoot 是否需要显示
         * @return 当前构造器对象
         */
        public Builder showRoot(boolean showRoot) {
            this.showRoot = showRoot;
            return this;
        }

        /**
         * 设置切换动画
         *
         * @param enter 进入动画
         * @param exit  退出动画
         * @return 当前构造器对象
         */
        public Builder animate(int enter, int exit) {
            mAnimEnter = enter;
            mAnimExit = exit;
            return this;
        }

        /**
         * 构造Helper实例
         *
         * @return Helper对象
         */
        public FmHelper build() {
            return new FmHelper(this);
        }
    }
}
