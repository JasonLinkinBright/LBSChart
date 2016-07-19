package com.graduate.lsj.lbschartforgraduate.ui.modules.listener;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.Toast;

/**
 * ClassName:MyOrientationListener
 * Created by lsj on 2016/3/28.
 */
public class MyOrientationListener implements SensorEventListener {

    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mSensor;

    //三个坐标轴中只关心X轴
    private float lastX;

    private OnOrientationListener mOnOrientationListener;

    public MyOrientationListener(Context context){
        this.mContext=context;
    }

    public void start(){

        mSensorManager= (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE); //得到系统传感器服务
        if(mSensorManager!=null){
            //获得方向传感器
            mSensor=mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        }

        //注册
        if(mSensor!=null){
//            Toast.makeText(mContext, "有传感器", Toast.LENGTH_SHORT).show();
            mSensorManager.registerListener(this,mSensor,
                    SensorManager.SENSOR_DELAY_UI);  //最后一个参数为精度，可有其它选择
        }

    }
    public void stop(){
        //停止监听
        mSensorManager.unregisterListener(this);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType()==Sensor.TYPE_ORIENTATION){
            float x=event.values[SensorManager.DATA_X];

            if(Math.abs(x-lastX)>1.0){
                //if(mOnOrientationListener!=null){      //这个判断看情况吧，加不加其实都可以
                mOnOrientationListener.onOrientationChanged(x);
                // }

            }
            lastX=x;
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }





    public void setOnOrientationListener(OnOrientationListener OnOrientationListener) {
        this.mOnOrientationListener = OnOrientationListener;
    }

    public interface OnOrientationListener{
        void onOrientationChanged(float x);
    }
}
