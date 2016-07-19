package com.graduate.lsj.lbschartforgraduate.ui.modules.activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.graduate.lsj.lbschartforgraduate.R;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.MyUser;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.NearInstitution;
import com.graduate.lsj.lbschartforgraduate.dao.pojo.VisitData;
import com.graduate.lsj.lbschartforgraduate.mapapi.clusterutil.clustering.ClusterItem;
import com.graduate.lsj.lbschartforgraduate.mapapi.clusterutil.clustering.ClusterManager;
import com.graduate.lsj.lbschartforgraduate.ui.modules.listener.MyOrientationListener;
import com.graduate.lsj.lbschartforgraduate.ui.modules.presenter.CorePresenter;
import com.graduate.lsj.lbschartforgraduate.ui.modules.viewimpl.CoreImpl;
import com.graduate.lsj.lbschartforgraduate.utils.LogTool;
import com.graduate.lsj.lbschartforgraduate.utils.LoginDataUtil;
import com.graduate.lsj.lbschartforgraduate.utils.NetUtils;
import com.graduate.lsj.lbschartforgraduate.utils.ToastUtil;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.GetListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by lsj on 2016/3/14.
 */
public class CoreActivity extends AppCompatActivity implements CoreImpl {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ImageView headIconImg;
    private TextView mUserNameTv;
    private CorePresenter mPresenter;
    //    private ClusterManager<MyItem> mClusterManager;
    MapView mMapView = null;

    private BaiduMap mBaiduMap;

    //定位相关
    private LocationClient mLocationClient;

    private MyLocationListener mLocationListener;
    private boolean isFirstIn = true;
    private double mLatitude;   //最新的经纬度
    private double mLongitude;


    //infoWindow
    @ViewInject(R.id.bdmap_marker_info_window)
    RelativeLayout bdmapMarkerInfoWindow;
    TextView infoWindowArea;
    TextView infoWindowDistance;
    TextView infoWindowName;
    RatingBar infoWindowRating;


    //自定义定位图标
    private BitmapDescriptor mIconLocation;
    private MyOrientationListener myOrientationListener;
    private float mCurrentX;
    private MyLocationConfiguration.LocationMode mLocationMode;//地图模式


    //覆盖物相关
    private List<BitmapDescriptor> mMarkers;
    private BitmapDescriptor mMarker;

    //xUtil相关
    private ImageOptions imageOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);


        mMapView = (MapView) findViewById(R.id.bmapView);
        x.view().inject(this);


        initMapView();

        // 初始化定位
        initLocation();
        // 覆盖物
        initMarker();

        mPresenter = new CorePresenter(this, this);
        mPresenter.init();
        mPresenter.getAccountInfo();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);
        View headerView = mNavigationView.getHeaderView(0);
        headIconImg = (ImageView) headerView.findViewById(R.id.core_head_icon);
        mUserNameTv = (TextView) headerView.findViewById(R.id.core_user_name);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setClass(CoreActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        x.view().inject(this);

        imageOptions = new ImageOptions.Builder()
                // 加载中或错误图片的ScaleType
                //.setPlaceholderScaleType(ImageView.ScaleType.MATRIX)
                // 默认自动适应大小
                // .setSize(...)
                .setSize(DensityUtil.dip2px(120), DensityUtil.dip2px(120))
                .setRadius(DensityUtil.dip2px(5))
                .setIgnoreGif(false)
                        // 如果使用本地文件url, 添加这个设置可以在本地文件更新后刷新立即生效.
                        //.setUseMemCache(false)
                .setImageScaleType(ImageView.ScaleType.CENTER).build();

        MyUser user = BmobUser.getCurrentUser(this, MyUser.class);
        if (user != null) {
            BmobQuery<MyUser> query = new BmobQuery<MyUser>();
            query.getObject(this, user.getObjectId(), new GetListener<MyUser>() {
                @Override
                public void onSuccess(MyUser myUser) {
                    LogTool.e("成功", myUser.getUserIcon());
                    String headIcon = myUser.getUserIcon();
                    LoginDataUtil.setIcon(CoreActivity.this, headIcon);
//                    http://file.bmob.cn/M03/FA/F5/oYYBAFb1CWyAesKSAAbLD4gMJNQ927.jpg
                    x.image().bind(headIconImg, headIcon, imageOptions);
                    mUserNameTv.setText(myUser.getUsername());
                    LoginDataUtil.setName(CoreActivity.this, myUser.getUsername());
                    LoginDataUtil.setPhone(CoreActivity.this, myUser.getMobilePhoneNumber());
                    LoginDataUtil.setNickName(CoreActivity.this, "应物人");
                    LoginDataUtil.setAge(CoreActivity.this, 23);
                }

                @Override
                public void onFailure(int i, String s) {

                    LogTool.e("失败", "什么鬼");
                }
            });

//            x.image().bind(headIconImg, "http://pic.baike.soso.com/p/20090711/20090711101754-314944703.jpg", imageOptions);

            Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
            toolbar.setTitle("一统地图");
            setSupportActionBar(toolbar);

            final ActionBar ab = getSupportActionBar();
            ab.setHomeAsUpIndicator(R.mipmap.personal_setting);
            ab.setDisplayHomeAsUpEnabled(true);


            setupDrawerContent(mNavigationView);


        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        centerToMyLocation();
//    }

    /**
     * 定位到我的位置,防止切换时失去位置
     */
    private void centerToMyLocation() {
        LatLng latLng = new LatLng(mLatitude, mLongitude);//设置经纬度
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(msu);  //以动画方式传送
    }


    private void initLocation() {
        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
        mLocationClient = new LocationClient(this.getApplicationContext());
        mLocationListener = new MyLocationListener();

        //注册监听器
        mLocationClient.registerLocationListener(mLocationListener);

        //添加选择器
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");  //坐标格式
        option.setOpenGps(true);
        option.setIsNeedAddress(true);
        option.setScanSpan(1000);  //隔1s进行请求

        //设置坐标参数
        mLocationClient.setLocOption(option);

        //初始化图标
        mIconLocation = BitmapDescriptorFactory.fromResource(R.mipmap.icon_navigation_bdmap);

        myOrientationListener = new MyOrientationListener(this);

        myOrientationListener.setOnOrientationListener(new MyOrientationListener.OnOrientationListener() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;

            }
        });

    }

    private void initMapView() {
        //去掉缩放按钮
        mMapView.showZoomControls(false);
        //设置标尺在500米左右
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);

        //实例化Baidumap
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setTrafficEnabled(true);
        mBaiduMap.setMapStatus(msu);

    }

    private void initMarker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.mipmap.bdmap_marker_1);
//        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
//        View layout = inflater.inflate(R.layout.custom_marker_view, null);
//        TextView tv = (TextView) layout.findViewById(R.id.tvContent);
//        tv.setText("daslfjl");
//
//        mMarker = BitmapDescriptorFactory.fromView(layout);

//        BitmapDescriptor mMarker_for_bank = BitmapDescriptorFactory.fromResource(R.mipmap.bdmap_marker_1);
//        BitmapDescriptor mMarker_for_security=BitmapDescriptorFactory.fromResource(R.mipmap.bdmap_marker_2);
    }


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        Intent intent = new Intent();
                        switch (menuItem.getItemId()) {
                            case R.id.nav_market:
                                intent.setClass(CoreActivity.this, MarketShareActivity.class);
                                break;
                            case R.id.nav_rank:
                                intent.setClass(CoreActivity.this, RankActivity.class);
                                break;
                            case R.id.nav_setting:
                                intent.setClass(CoreActivity.this, AccountSettingActivity.class);
                                break;
                            case R.id.nav_special_offer:
                                intent.setClass(CoreActivity.this, DiscountActivity.class);
                                break;
                            case R.id.nav_about:
                                intent.setClass(CoreActivity.this, AboutActivity.class);
                                break;
                            case R.id.nav_author:
                                intent.setClass(CoreActivity.this, AuthorActivity.class);
                                break;

                        }
                        startActivity(intent);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_navigation_view, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void refreshAccountSuccess() {

    }

    @Override
    public void refreshAccountFailed() {

    }

    @Override
    public double getLatitude() {
        return mLatitude;
    }

    @Override
    public double getLongitude() {
        return mLongitude;
    }

    @Override
    public void deployOverlay(List<NearInstitution> mks) {

        //先清除地图的原有内容
        mBaiduMap.clear();
        LatLng latLng;
        Marker marker;
        OverlayOptions options;
        //循环取得标注点信息并序列化
        for (NearInstitution info : mks) {
            latLng = new LatLng(info.getInstitutionLatitude(), info.getInstitutionLongitude());
            options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            // 添加标注点
            marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle arg0 = new Bundle();
            arg0.putSerializable("info", info);
            marker.setExtraInfo(arg0);
        }

    }

    @Override
    public void initView(View view) {
        infoWindowArea = (TextView) bdmapMarkerInfoWindow.findViewById(R.id.info_window_object_area);
        infoWindowName = (TextView) bdmapMarkerInfoWindow.findViewById(R.id.info_window_object_name);
        infoWindowDistance = (TextView) bdmapMarkerInfoWindow.findViewById(R.id.info_window_object_distance);
        infoWindowRating = (RatingBar) bdmapMarkerInfoWindow.findViewById(R.id.info_window_object_rating_bar);

    }

    @Override
    public void initListener() {
        /**
         * 标记点击事件
         */
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle extraInfo = marker.getExtraInfo();
                NearInstitution info = (NearInstitution) extraInfo.getSerializable("info");
                LogTool.e("文字", info.getInstitutionName());
                infoWindowName.setText(info.getInstitutionName());
                infoWindowArea.setText(info.getInstitutionArea());
                infoWindowRating.setRating(Float.parseFloat(String.valueOf(info.getNumberStar())));
                LatLng myCurrentLatLng = new LatLng(mLatitude, mLongitude);//设置经纬度
                LatLng institutionLatLng = new LatLng(info.getInstitutionLatitude(), info.getInstitutionLongitude());
//                ToastUtil.get().showShortToast(CoreActivity.this,DistanceUtil.getDistance(myCurrentLatLng,institutionLatLng)+"米");
                double distance = DistanceUtil.getDistance(myCurrentLatLng, institutionLatLng);
                String distanceString;
                if (distance > 1000) {
                    distanceString = (String.format("%.1f千米", distance / 1000));
                } else {
                    distanceString = String.format("%.0f米", distance);
                }
                infoWindowDistance.setText("距离" + distanceString);

                bdmapMarkerInfoWindow.setTag(info);
                bdmapMarkerInfoWindow.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(CoreActivity.this, R.anim.window_anim);
                bdmapMarkerInfoWindow.setAnimation(animation);
                animation.start();
                return true;
            }
        });

        /**
         * 地图点击事件
         */
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                bdmapMarkerInfoWindow.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        bdmapMarkerInfoWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMapView.setClickable(false);//bdmapView的点击失效
                NearInstitution info = (NearInstitution) v.getTag();
                VisitData visitData = new VisitData();
                visitData.setDiscountName("手续费减免额度");
                visitData.setDiscount1(Double.parseDouble(String.format("%.1f", Math.random() * 0.3 + 0.1)));
                visitData.setDiscount2(Double.parseDouble(String.format("%.1f", Math.random() * 0.3 + 0.1)));
                visitData.setDiscount3(Double.parseDouble(String.format("%.1f", Math.random() * 0.3 + 0.1)));
                visitData.setDiscount4(Double.parseDouble(String.format("%.1f", Math.random() * 0.3 + 0.1)));
                visitData.setDiscount5(Double.parseDouble(String.format("%.1f", Math.random() * 0.3 + 0.1)));
                visitData.setDiscount6(Double.parseDouble(String.format("%.1f", Math.random() * 0.3 + 0.1)));

                visitData.setVisitName("访问量");
                visitData.setVisit1((30 + (int) Math.rint(Math.random() * 200)));
                visitData.setVisit2((30 + (int) Math.rint(Math.random() * 200)));
                visitData.setVisit3((30 + (int) Math.rint(Math.random() * 200)));
                visitData.setVisit4((30 + (int) Math.rint(Math.random() * 200)));
                visitData.setVisit5((30 + (int) Math.rint(Math.random() * 200)));
                visitData.setVisit6((30 + (int) Math.rint(Math.random() * 200)));
                visitData.setInstitution(info);

                visitData.save(CoreActivity.this, new SaveListener() {
                    @Override
                    public void onSuccess() {
                        ToastUtil.get().showShortToast(CoreActivity.this, "ok,保存成功");
                    }

                    @Override
                    public void onFailure(int i, String s) {

                    }
                });


                Intent intent = new Intent(v.getContext(), InstitutionDetailActivity.class);
                intent.putExtra("objectID", info.getObjectId());
                startActivity(intent);
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
                            NetUtils.openSetting(CoreActivity.this);
                        }
                    });
            builder.create().show();
        }
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder()//Builder来放置多个参数
                    .direction(mCurrentX)// 方向
                    .accuracy(bdLocation.getRadius())//
                    .latitude(bdLocation.getLatitude())//
                    .longitude(bdLocation.getLongitude())//
                    .build();

            mBaiduMap.setMyLocationData(data);


            //更新经纬度
            mLatitude = bdLocation.getLatitude();
            mLongitude = bdLocation.getLongitude();


            if (Math.abs(mLatitude - Double.parseDouble(LoginDataUtil.getLatitude(CoreActivity.this))) < 1 ||
                    Math.abs(mLongitude - Double.parseDouble(LoginDataUtil.getLongitude(CoreActivity.this))) < 1) {
                LoginDataUtil.setLongitude(CoreActivity.this, mLongitude + "");
                LoginDataUtil.setLatitude(CoreActivity.this, mLatitude + "");

            }


            //config设置自定义图标
            MyLocationConfiguration config = new
                    MyLocationConfiguration(mLocationMode, true, mIconLocation);
            mBaiduMap.setMyLocationConfigeration(config);


            if (isFirstIn) {

                LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());//设置经纬度
                Log.d("city", "" + bdLocation.getStreet());
                Log.d("cityCode", "" + bdLocation.getCityCode());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);  //以动画方式传送
                mPresenter.loadMarker();
                isFirstIn = false;

//                Toast.makeText(context, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);  //地图允许定位
        if (!mLocationClient.isStarted())
            mLocationClient.start();

//        //开启方向传感器
        myOrientationListener.start();
        Log.d("fragment", "start");

    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //停止定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();

        //停止方向传感器
        myOrientationListener.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;

    }

    /**
     * 每个Marker点，包含Marker点坐标以及图标
     */
    public class MyItem implements ClusterItem {
        private final LatLng mPosition;

        public MyItem(LatLng latLng) {
            mPosition = latLng;
        }

        @Override
        public LatLng getPosition() {
            return mPosition;
        }

        @Override
        public BitmapDescriptor getBitmapDescriptor() {

            LayoutInflater inflater = (LayoutInflater) CoreActivity.this.getSystemService(LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.custom_marker_view, null);
            TextView tv = (TextView) layout.findViewById(R.id.tvContent);
            tv.setText("daslfjl");
//            return BitmapDescriptorFactory
//                    .fromResource(R.drawable.icon_gcoding);
            return BitmapDescriptorFactory.fromView(layout);


        }
    }


}
