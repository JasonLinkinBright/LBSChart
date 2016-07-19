package com.graduate.lsj.lbschartforgraduate.framework.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.graduate.lsj.lbschartforgraduate.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;


/**
 * Created by Administrator on 2016/3/10.
 */


public class BaseActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView toolbarTitle;
    private ImageView leftAction;
    private LinearLayout mRightActionContainer;

    public Context context;
    private ViewGroup mRootView;
    private View mLoadingView;

    @Override
    public void setContentView(int layoutResID) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.setContentView(layoutResID);
        context = this;
        mRootView = (ViewGroup) findViewById(android.R.id.content);

        View v = findViewById(R.id.toolbar);
        if (v != null) {
            toolbar = (Toolbar) v;
            setSupportActionBar(toolbar);
            toolbarTitle = (TextView) v.findViewById(R.id.toolbar_title);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            leftAction = (ImageView) v.findViewById(R.id.toolbar_back);
            mRightActionContainer = (LinearLayout) v.findViewById(R.id.toolbar_right_action_container);
        }
    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (toolbarTitle != null) {
            toolbarTitle.setText(title);
        }
    }

    protected void showBackActionBtn() {
        if (leftAction != null) {
            leftAction.setVisibility(View.VISIBLE);
            leftAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }

    /**
     * 添加多个图片
     */
    protected void showRightAction(int resId, View.OnClickListener listener) {
        ImageView rightAction = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rightAction.setLayoutParams(params);
        rightAction.setImageResource(resId);
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, getResources().getDisplayMetrics());
        rightAction.setPadding(space, 0, space, 0);
        rightAction.setOnClickListener(listener);
        if (mRightActionContainer != null) {
            mRightActionContainer.addView(rightAction);
        }
    }

    /**
     * 只设置一张图片
     */
    protected void showOneRightAction(int resId, View.OnClickListener listener) {
        ImageView rightAction = new ImageView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rightAction.setLayoutParams(params);
        rightAction.setImageResource(resId);
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, getResources().getDisplayMetrics());
        rightAction.setPadding(space, 0, space, 0);
        rightAction.setOnClickListener(listener);
        if (mRightActionContainer != null) {
            if (mRightActionContainer.getChildCount() != 0) {
                mRightActionContainer.removeViewAt(0);
                mRightActionContainer.addView(rightAction);
            } else {
                mRightActionContainer.addView(rightAction);
            }
        }
    }

    /**
     * 设置TextView
     * @param text
     * @param listener
     */
    protected void showRightStrAction(String text, View.OnClickListener listener) {
        TextView rightAction = new TextView(this);
        rightAction.setTextColor(Color.WHITE);
        rightAction.setText(text);
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, getResources().getDisplayMetrics());
        rightAction.setPadding(space, 0, space, 0);
        rightAction.setOnClickListener(listener);
        if (mRightActionContainer != null) {
            mRightActionContainer.addView(rightAction);
        }
    }


    protected void showOneRightStrAction(String text, View.OnClickListener listener) {
        TextView rightAction = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rightAction.setLayoutParams(params);
        rightAction.setTextColor(Color.BLACK);
        rightAction.setGravity(Gravity.CENTER_VERTICAL);
        rightAction.setText(text);
        int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                10, getResources().getDisplayMetrics());
        rightAction.setPadding(space, 0, space, 0);
        rightAction.setOnClickListener(listener);
        if (mRightActionContainer != null) {
            if (mRightActionContainer.getChildCount() != 0) {
                mRightActionContainer.removeViewAt(0);
                mRightActionContainer.addView(rightAction);
            } else {
                mRightActionContainer.addView(rightAction);
            }
        }
    }


    /**
     * 启动新的Activity
     */
    protected void startActivity(Class<?> aClass) {
        context.startActivity(new Intent(this, aClass));
    }

    protected void showLoadingView(boolean show) {
        if (mLoadingView == null) {
            mLoadingView = getLayoutInflater().inflate(R.layout.loading_view, mRootView, false);
        }
        if (show) {
            mRootView.addView(mLoadingView);
        } else {
            mRootView.removeView(mLoadingView);
        }
    }

//    protected void showError(final View.OnClickListener listener, View... views) {
//        for (View childView : views) {
//            mRootView.removeView(childView);
//        }
//        final View view = getLayoutInflater().inflate(R.layout.error_layout, null);
//        Button retryBtn = (Button) view.findViewById(R.id.retry_btn);
//        retryBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mRootView.removeView(view);
//                listener.onClick(v);
//            }
//        });
//        mRootView.addView(view);
//
//
//    }
//
//
//    protected void showEmpty(View... views) {
//        for (View childView : views) {
//            mRootView.removeView(childView);
//        }
//        View view = getLayoutInflater().inflate(R.layout.empty_layout, null);
//        mRootView.addView(view);
//
//    }
//
//    public View getEmptyView() {
//        return getLayoutInflater().inflate(R.layout.empty_layout, null);
//    }

//    /**
//     * 为listview设置emptyview
//     */
//    public void setEmptyView(ListView listView) {
//        //listview有一个Ptr的父容器，所以要再获取一次
//        ViewGroup viewGroup = (ViewGroup) listView.getParent().getParent();
//        viewGroup.addView(getEmptyView(), 2);
//        listView.setEmptyView(getEmptyView());
//    }

    public void setLeftActionRes(){
        leftAction.setImageResource(R.mipmap.arrow_left);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingView != null) {
            mRootView.removeView(mLoadingView);
        }
    }

}