package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.MarketSharePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.MarketShareImpl;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lsj on 2016/4/7.
 */
public class MarketShareActivity extends BaseActivity implements MarketShareImpl, OnChartValueSelectedListener {

    @ViewInject(R.id.market_share_pie_chart)
    PieChart mPieChart;
    private MarketSharePresenter mPresenter;
    private List<String> dataList;
    private Map<String,Integer> map=new HashMap<>();

    private Typeface tf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_share);

        x.view().inject(this);
        setTitle("机构份额");

        mPresenter = new MarketSharePresenter(this, this);
        mPresenter.init();
        mPresenter.loadData();

    }

    @Override
    public void deployData(List<String> stringList, Map<String, Integer> map) {
        this.dataList = stringList;
        this.map = map;
        drawChart();
    }

    @Override
    public void loadingFailed() {

    }

    @Override
    public void initView(View view) {
        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription("");
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mPieChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
        mPieChart.setCenterText(generateCenterSpannableText());

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColor(Color.WHITE);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(true);
        mPieChart.setHighlightPerTapEnabled(true);

        // mPieChart.setUnit(" €");
        // mPieChart.setDrawUnitsInChart(true);


//        setData(3, 100);

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mPieChart.spin(2000, 0, 360);

        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("市场保有量\ndeveloped by Lin Shi Jun");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 5, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 5, s.length() - 12, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 5, s.length() - 12, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 5, s.length() - 12, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 12, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 12, s.length(), 0);
        return s;
    }

    private void drawChart() {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // 添加Y轴值的集合
        for (int i = 0; i < dataList.size(); i++) {
            yVals1.add(new Entry(map.get(dataList.get(i)), i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        // 添加X轴值的集合
        for (int i = 0; i < dataList.size(); i++)
            xVals.add(dataList.get(i));

        // 引入饼状图
        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);


        colors.add(ColorTemplate.getHoloBlue());

        // 设置颜色集合
        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setValueTypeface(tf);

        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();
    }

    @Override
    public void initListener() {
        // add a selection listener
        mPieChart.setOnChartValueSelectedListener(this);
    }

    @Override
    public void showLoading(boolean shouldShow) {

    }

    @Override
    public void isNetConnected(boolean connected) {
        if(!connected){
            android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this).setMessage(R.string.netSetting)
                    .setNegativeButton(R.string.settingAccess, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            NetUtils.openSetting(MarketShareActivity.this);
                        }
                    });
            builder.create().show();
        }
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {

    }
}
