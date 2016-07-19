package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.VisitData;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.InstitutionDetailPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.InstitutionDetailImpl;
import com.graduate.lsj.lbschartforgraduate.ui.view.MyMarkerView;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;
import com.graduate.lsj.lbschartforgraduate.utils.SystemUtil;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static com.graduate.lsj.lbschartforgraduate.R.drawable.shape_institution_btn_unchecked;

/**
 * Created by lsj on 2016/4/1.
 */
public class InstitutionDetailActivity extends BaseActivity implements InstitutionDetailImpl, OnChartGestureListener, OnChartValueSelectedListener {

    @ViewInject(R.id.institution_visit_chart)
    LineChart mChart;
    @ViewInject(R.id.institution_detail_like)
    Button btnLike;
    @ViewInject(R.id.institution_detail_sign_in)
    Button btnSignIn;
    @ViewInject(R.id.institution_detail_name)
    TextView institutionName;
    @ViewInject(R.id.institution_detail_description)
    TextView institutionDescription;
    @ViewInject(R.id.institution_detail_head_icon)
    ImageView institutionIcon;
    private InstitutionDetailPresenter mPresenter;
    private VisitData mVisitData;
    private String mObjectID;
    private String mVisitID;
    private boolean likeIscheched = false;
    private boolean signInIsChecked = false;

    private SimpleDateFormat sdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_detail);

        x.view().inject(this);

        setTitle("商家详情");
        sdf = new SimpleDateFormat("MM月dd日");
        mObjectID = getIntent().getStringExtra("objectID");
        mPresenter = new InstitutionDetailPresenter(this, this);
        mPresenter.init();
        mPresenter.loadData(mObjectID);

    }

    @Override
    public void deploySuccess(VisitData visitData) {
        mVisitData = visitData;
        mVisitID = visitData.getObjectId();
        NearInstitution nearInstitution = mVisitData.getInstitution();
        institutionName.setText("商家名称：" + nearInstitution.getInstitutionName());
        institutionDescription.setText("商家简介："+nearInstitution.getInstitutionName()+"是成都著名的"+nearInstitution.getInstitutionType()+"商家，累计获得" + (mVisitData.getLike() + 8) + "个点赞");
        drawChart(mVisitData);
    }

    private void drawChart(VisitData visitData) {
        ArrayList<String> xVals = new ArrayList<String>();
        long currentTime = System.currentTimeMillis();
        long oneDay = 24 * 60 * 60 * 1000;
        for (int i = 0; i < 6; i++) {
            xVals.add(sdf.format(currentTime - (5 - i) * oneDay));
        }

        ArrayList<Entry> yVals = new ArrayList<>();

        yVals.add(new Entry(visitData.getVisit1(), 0));
        yVals.add(new Entry(visitData.getVisit2(), 1));
        yVals.add(new Entry(visitData.getVisit3(), 2));
        yVals.add(new Entry(visitData.getVisit4(), 3));
        yVals.add(new Entry(visitData.getVisit5(), 4));
        yVals.add(new Entry(visitData.getVisit6(), 5));

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(yVals, "DataSet 1");
        // set1.setFillAlpha(110);
        // set1.setFillColor(Color.RED);

        // set the line to be drawn like this "- - - - - -"
        set1.enableDashedLine(10f, 5f, 0f);
        set1.enableDashedHighlightLine(10f, 5f, 0f);
        set1.setColor(Color.BLACK);
        set1.setCircleColor(Color.BLACK);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);
        set1.setDrawFilled(true);

        if (SystemUtil.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.YELLOW);
        }


        ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(set1); // add the datasets

        // create a data object with the datasets
        LineData data = new LineData(xVals, dataSets);

        // set data
        mChart.setData(data);

    }


    @Override
    public void deployFailed() {

    }

    @Override
    public void changeLikeState() {

    }

    @Override
    public void changeLikeStateFailed() {

    }

    @Override
    public void changeSignInState() {

    }

    @Override
    public void changeSignInStateFailed() {

    }

    @Override
    public void initView(View view) {

        mChart.setDrawGridBackground(false);

        // no description text
        mChart.setDescription("访问量");
        mChart.setNoDataTextDescription("You need to provide data for the chart.");

        // enable touch gestures
        mChart.setTouchEnabled(true);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        // mPieChart.setScaleXEnabled(true);
        // mPieChart.setScaleYEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);

        // set an alternative background color
        mChart.setBackgroundColor(Color.GRAY);

        // create a custom MarkerView (extend MarkerView) and specify the layout
        // to use for it
        MyMarkerView mv = new MyMarkerView(this, R.layout.custom_marker_view);

        // set the marker to the chart
        mChart.setMarkerView(mv);

        // x-axis limit line
        LimitLine llXAxis = new LimitLine(10f, "Index 10");
        llXAxis.setLineWidth(4f);
        llXAxis.enableDashedLine(10f, 10f, 0f);
        llXAxis.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        llXAxis.setTextSize(10f);

        XAxis xAxis = mChart.getXAxis();
        //xAxis.setValueFormatter(new MyCustomXAxisValueFormatter());
        //xAxis.addLimitLine(llXAxis); // add x-axis limit line

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
//        leftAxis.addLimitLine(ll1);
//        leftAxis.addLimitLine(ll2);
        leftAxis.setAxisMaxValue(250f);
        leftAxis.setAxisMinValue(-0f);
        //leftAxis.setYOffset(20f);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setDrawZeroLine(false);

        // limit lines are drawn behind data (and not on top)
        leftAxis.setDrawLimitLinesBehindData(true);

        mChart.getAxisRight().setEnabled(false);

        //mPieChart.getViewPortHandler().setMaximumScaleY(2f);
        //mPieChart.getViewPortHandler().setMaximumScaleX(2f);

//        mPieChart.setVisibleXRange(20);
//        mPieChart.setVisibleYRange(20f, AxisDependency.LEFT);
//        mPieChart.centerViewTo(20, 50, AxisDependency.LEFT);

        mChart.animateX(2500, Easing.EasingOption.EaseInOutQuart);
//        mPieChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();

        // modify the legend ...
        // l.setPosition(LegendPosition.LEFT_OF_CHART);
        l.setForm(Legend.LegendForm.LINE);

        // // dont forget to refresh the drawing
        // mPieChart.invalidate();

    }

    @Override
    public void initListener() {
        mChart.setOnChartGestureListener(this);
        mChart.setOnChartValueSelectedListener(this);

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (likeIscheched) {
                    btnLike.setBackgroundResource(R.drawable.shape_institution_btn_unchecked);
                    likeIscheched = !likeIscheched;
                    mPresenter.addLike(mVisitID, false);
                    btnLike.setText("点赞");
                } else {
                    btnLike.setBackgroundResource(R.drawable.shape_institution_btn_checked);
                    likeIscheched = !likeIscheched;
                    mPresenter.addLike(mVisitID, true);
                    btnLike.setText("取消点赞");
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signInIsChecked) {
                    btnSignIn.setBackgroundResource(R.drawable.shape_institution_btn_unchecked);
                    signInIsChecked = !signInIsChecked;
                    mPresenter.addSignIn(mVisitID, false);
                    btnSignIn.setText("签到");
                } else {
                    btnSignIn.setBackgroundResource(R.drawable.shape_institution_btn_checked);
                    signInIsChecked = !signInIsChecked;
                    mPresenter.addSignIn(mVisitID, true);
                    btnSignIn.setText("取消签到");
                }
            }
        });

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
                            NetUtils.openSetting(InstitutionDetailActivity.this);
                        }
                    });
            builder.create().show();
        }
    }

    @Override
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "START, x: " + me.getX() + ", y: " + me.getY());
    }

    @Override
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
        Log.i("Gesture", "END, lastGesture: " + lastPerformedGesture);

        // un-highlight values after the gesture is finished and no single-tap
        if (lastPerformedGesture != ChartTouchListener.ChartGesture.SINGLE_TAP)
            mChart.highlightValues(null); // or highlightTouch(null) for callback to onNothingSelected(...)
    }

    @Override
    public void onChartLongPressed(MotionEvent me) {
        Log.i("LongPress", "Chart longpressed.");
    }

    @Override
    public void onChartDoubleTapped(MotionEvent me) {
        Log.i("DoubleTap", "Chart double-tapped.");
    }

    @Override
    public void onChartSingleTapped(MotionEvent me) {
        Log.i("SingleTap", "Chart single-tapped.");
    }

    @Override
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
        Log.i("Fling", "Chart flinged. VeloX: " + velocityX + ", VeloY: " + velocityY);
    }

    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        Log.i("Scale / Zoom", "ScaleX: " + scaleX + ", ScaleY: " + scaleY);
    }

    @Override
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
        Log.i("Translate / Move", "dX: " + dX + ", dY: " + dY);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        Log.i("Entry selected", e.toString());
        Log.i("LOWHIGH", "low: " + mChart.getLowestVisibleXIndex() + ", high: " + mChart.getHighestVisibleXIndex());
        Log.i("MIN MAX", "xmin: " + mChart.getXChartMin() + ", xmax: " + mChart.getXChartMax() + ", ymin: " + mChart.getYChartMin() + ", ymax: " + mChart.getYChartMax());
    }

    @Override
    public void onNothingSelected() {
        Log.i("Nothing selected", "Nothing selected.");
    }


}
