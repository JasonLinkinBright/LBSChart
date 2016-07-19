package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.VisitData;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.DiscountPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.DiscountImpl;
import com.graduate.lsj.lbschartforgraduate.utils.LogTool;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/4/14.
 */
public class DiscountActivity extends BaseActivity implements DiscountImpl {

    @ViewInject(R.id.discount_listView)
    ListView lv;

    private DiscountPresenter mPresenter;
    private dataAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount);

        x.view().inject(this);
        setTitle("优惠活动");

        mPresenter = new DiscountPresenter(this, this);
        mPresenter.init();
        mPresenter.getData();
    }


    @Override
    public void deployData(List<VisitData> list) {
        mAdapter.setData(list);
    }

    @Override
    public void deployFailed() {

    }

    @Override
    public void initView(View view) {
        mAdapter = new dataAdapter();
        lv.setAdapter(mAdapter);
    }

    @Override
    public void initListener() {

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
                            NetUtils.openSetting(DiscountActivity.this);
                        }
                    });
            builder.create().show();
        }
    }

    class dataAdapter extends BaseAdapter {

        List<VisitData> mVisitList = new ArrayList<>();

        public void setData(List<VisitData> data) {
            mVisitList.clear();
            mVisitList.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mVisitList.size();
        }

        @Override
        public Object getItem(int position) {
            return mVisitList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.discount_item_layout, parent, false);
                ViewHolder viewholder = new ViewHolder(convertView);
                convertView.setTag(viewholder);
            }
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            setViewContent(convertView, viewHolder, position);

            return convertView;
        }

        private void setViewContent(View view, final ViewHolder vh, int position) {
            VisitData visitData = mVisitList.get(position);
            NearInstitution institution = visitData.getInstitution();
            final LineChart mChart = vh.mChart;
            vh.tv.setText(institution.getInstitutionName());
            mChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
//                    this.centerViewToAnimated(e.getXIndex(), e.getVal(), vh.mChart.getData().getDataSetByIndex(dataSetIndex).getAxisDependency(), 500);
                    mChart.centerViewToAnimated(e.getXIndex(), e.getVal(), mChart.getData().getDataSetByIndex(dataSetIndex).getAxisDependency(), 500);

                }

                @Override
                public void onNothingSelected() {

                }
            });

            // no description text
            mChart.setDescription("");
            mChart.setNoDataTextDescription("You need to provide data for the chart.");

            // enable touch gestures
            mChart.setTouchEnabled(true);

            mChart.setDragDecelerationFrictionCoef(0.9f);

            // enable scaling and dragging
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);
            mChart.setDrawGridBackground(false);
            mChart.setHighlightPerDragEnabled(true);

            // if disabled, scaling can be done on x- and y-axis separately
            mChart.setPinchZoom(true);

            // set an alternative background color
            mChart.setBackgroundColor(Color.LTGRAY);

            // add data
//            setData(20, 30);

            mChart.setData(setLineData(visitData));
            mChart.animateX(2500);

            Typeface tf = Typeface.createFromAsset(DiscountActivity.this.getAssets(), "OpenSans-Regular.ttf");

            // get the legend (only possible after setting data)
            Legend l = mChart.getLegend();

            // modify the legend ...
            // l.setPosition(LegendPosition.LEFT_OF_CHART);
            l.setForm(Legend.LegendForm.LINE);
            l.setTypeface(tf);
            l.setTextSize(11f);
            l.setTextColor(Color.WHITE);
            l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
//        l.setYOffset(11f);

            XAxis xAxis = mChart.getXAxis();
            xAxis.setTypeface(tf);
            xAxis.setTextSize(12f);
            xAxis.setTextColor(Color.WHITE);
            xAxis.setDrawGridLines(false);
            xAxis.setDrawAxisLine(false);
            xAxis.setSpaceBetweenLabels(1);

            YAxis leftAxis = mChart.getAxisLeft();
            leftAxis.setTypeface(tf);
            leftAxis.setTextColor(ColorTemplate.getHoloBlue());
            leftAxis.setAxisMaxValue(1f);
            leftAxis.setAxisMinValue(0f);
            leftAxis.setDrawGridLines(true);
//            leftAxis.setGranularityEnabled(true);

            YAxis rightAxis = mChart.getAxisRight();
            rightAxis.setTypeface(tf);
            rightAxis.setTextColor(Color.RED);
            rightAxis.setAxisMaxValue(1f);
            rightAxis.setAxisMinValue(0f);
            rightAxis.setDrawGridLines(false);
            rightAxis.setDrawZeroLine(false);
//            rightAxis.setGranularityEnabled(false);
        }

        public class ViewHolder {
            @ViewInject(R.id.discount_line_chart)
            LineChart mChart;
            @ViewInject(R.id.discount_tv)
            TextView tv;

            ViewHolder(View view) {
                x.view().inject(this, view);
            }
        }
    }

    private LineData setLineData(VisitData visitData) {

        int count = 6;

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            xVals.add((i) + "");
        }


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

//        for (int i = 0; i < count; i++) {
//            float mult = range / 2f;
//            float val = (float) (Math.random() * mult) + 50;// + (float)
//            // ((mult *
//            // 0.1) / 10);
//            yVals1.add(new Entry(val, i));
//        }
        yVals1.add(new Entry(Float.parseFloat(visitData.getDiscount1() + ""), 0));
        yVals1.add(new Entry(Float.parseFloat(visitData.getDiscount2() + ""), 1));
        yVals1.add(new Entry(Float.parseFloat(visitData.getDiscount3() + ""), 2));
        yVals1.add(new Entry(Float.parseFloat(visitData.getDiscount4() + ""), 3));
        yVals1.add(new Entry(Float.parseFloat(visitData.getDiscount5() + ""), 4));
        yVals1.add(new Entry(Float.parseFloat(visitData.getDiscount6() + ""), 5));

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals1, visitData.getDiscountName());
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setCircleColor(Color.WHITE);
        set1.setLineWidth(2f);
        set1.setCircleRadius(3f);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);
        //set1.setFillFormatter(new MyFillFormatter(0f));
//        set1.setDrawHorizontalHighlightIndicator(false);
//        set1.setVisible(false);
//        set1.setCircleHoleColor(Color.WHITE);

//        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
//
//        for (int i = 0; i < count; i++) {
//            float mult = range;
//            float val = (float) (Math.random() * mult) + 450;// + (float)
//            // ((mult *
//            // 0.1) / 10);
//            yVals2.add(new Entry(val, i));
//        }

//        // create a dataset and give it a type
//        LineDataSet set2 = new LineDataSet(yVals2, "DataSet 2");
//        set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
//        set2.setColor(Color.RED);
//        set2.setCircleColor(Color.WHITE);
//        set2.setLineWidth(2f);
//        set2.setCircleRadius(3f);
//        set2.setFillAlpha(65);
//        set2.setFillColor(Color.RED);
//        set2.setDrawCircleHole(false);
//        set2.setHighLightColor(Color.rgb(244, 117, 117));
        //set2.setFillFormatter(new MyFillFormatter(900f));

        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
//        dataSets.add(set2);
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        return data;

    }
}
