package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.utils.VersionUpdateHelper;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by lsj on 2016/3/22.
 */
public class GuideActivity extends AppCompatActivity{

    @ViewInject(R.id.guide_viewpager)
    ViewPager guideViewPager;

    private static final int[] DATA = new int[] {
            R.drawable.guide_1,
            R.drawable.guide_2,
            R.drawable.guide_3,
            R.drawable.guide_4,
            R.drawable.guide_5,
    };
    private PagerAdapter mAdapter;
    private View[] mGuideViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        x.view().inject(this);

        initViews();

        mAdapter = new GuideAdapter(mGuideViews);
        guideViewPager.setAdapter(mAdapter);
    }

    private void initViews() {
        mGuideViews = new View[DATA.length];
        for (int i = 0; i < DATA.length; ++i) {
            ImageView item = new ImageView(this);
            item.setBackgroundResource(DATA[i]);
            mGuideViews[i] = item;
        }
        mGuideViews[DATA.length - 1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VersionUpdateHelper.updateConfig(GuideActivity.this);

                finish();
            }
        });
    }

    class GuideAdapter extends PagerAdapter {

        private View[] views;

        GuideAdapter(View[] views) {
            this.views = views;
        }

        @Override
        public int getCount() {
            return views.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            container.addView(views[position]);
            return views[position];
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(views[position]);
        }
    }

}
