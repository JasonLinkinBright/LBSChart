package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.framework.base.BaseActivity;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.RankPresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.RankImpl;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsj on 2016/4/8.
 */
public class RankActivity extends BaseActivity implements RankImpl {

    @ViewInject(R.id.rank_listView)
    ListView lv;
    private RankPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);

        x.view().inject(this);
        mPresenter = new RankPresenter(this, this);
        mPresenter.init();
        mPresenter.getRank();
    }


    @Override
    public void deploySuccess(List<String> typeList, List<NearInstitution> institutionList) {


        ArrayList<BarData> barList = new ArrayList<BarData>();

//        // 20 items
//        for (int i = 0; i < 20; i++) {
//            list.add(generateData(i + 1));
//        }
        for (int i = 0; i < typeList.size(); i++) {
            String type = typeList.get(i);
            List<NearInstitution> institutions = new ArrayList<>();
            for (int j = 0; j < institutionList.size(); j++) {
                if (type.equals(institutionList.get(j).getInstitutionType())) {
                    institutions.add(institutionList.get(j));
                }
            }
            barList.add(generateData(institutions));
        }

        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), barList);
        cda.setTypeList(typeList);
        lv.setAdapter(cda);

    }

    @Override
    public void deployFailed() {

    }

    @Override
    public void initView(View view) {


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
                            NetUtils.openSetting(RankActivity.this);
                        }
                    });
            builder.create().show();
        }
    }

    private class ChartDataAdapter extends ArrayAdapter<BarData> {

        private Typeface mTf;
        private List<String> typeList;

        public ChartDataAdapter(Context context, List<BarData> objects) {
            super(context, 0, objects);

            mTf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");
        }

        public void setTypeList(List<String> list) {
            this.typeList = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            BarData data = getItem(position);
            String type = typeList.get(position);

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();

                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.list_item_barchart, null);
                holder.chart = (BarChart) convertView.findViewById(R.id.bar_list_chart);
                holder.tv = (TextView) convertView.findViewById(R.id.bar_list_tv);

                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            // apply styling
            data.setValueTypeface(mTf);
            data.setValueTextColor(Color.BLACK);
            holder.chart.setDrawGridBackground(false);

            XAxis xAxis = holder.chart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setTypeface(mTf);
            xAxis.setDrawGridLines(false);

            YAxis leftAxis = holder.chart.getAxisLeft();
            leftAxis.setTypeface(mTf);
            leftAxis.setAxisMaxValue(5);
            leftAxis.setLabelCount(5, false);
            leftAxis.setSpaceTop(15f);

            YAxis rightAxis = holder.chart.getAxisRight();
            rightAxis.setTypeface(mTf);
            rightAxis.setLabelCount(5, false);
            rightAxis.setAxisMaxValue(5);
            rightAxis.setSpaceTop(15f);

            // set data
            holder.chart.setData(data);

            // apply title setting
            holder.tv.setText(String.format("%s评分排行榜", type));

            // do not forget to refresh the chart
//            holder.chart.invalidate();
            holder.chart.animateY(700, Easing.EasingOption.EaseInCubic);

            return convertView;
        }

        private class ViewHolder {

            BarChart chart;
            TextView tv;
        }
    }

    /**
     * generates a random ChartData object with just one DataSet
     *
     * @return
     */
    private BarData generateData(List<NearInstitution> list) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        int count = (list.size() > 8 ? 8 : list.size());
        for (int i = 0; i < count; i++) {
            entries.add(new BarEntry(list.get(i).getNumberStar().floatValue(), i));
        }


        BarDataSet d = new BarDataSet(entries, "New DataSet ");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setBarShadowColor(Color.rgb(203, 203, 203));

        ArrayList<IBarDataSet> sets = new ArrayList<IBarDataSet>();
        sets.add(d);

        BarData cd = new BarData(getMonths(list, count), sets);
        return cd;
    }

    private ArrayList<String> getMonths(List<NearInstitution> list, int count) {

        ArrayList<String> m = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            m.add(list.get(i).getInstitutionName());
        }
        return m;
    }
}
