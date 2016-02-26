package com.magi.mobilesecurity.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;

/**
 * 获取经纬度的服务
 */
public class LocationService extends Service {

    private LocationManager mLocationManager;
    private MyLocationListener mListener;
    private SharedPreferences mSpref;

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        mSpref = getSharedPreferences("config", MODE_PRIVATE);
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
//        List<String> allProviders = mLocationManager.getAllProviders();// 获取所有位置提供者
        Criteria criteria = new Criteria();
        criteria.setCostAllowed(true);  //是否允许付费，使用网络
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        String bestProvider = mLocationManager.getBestProvider(criteria, true);

        mListener = new MyLocationListener();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(bestProvider, 0, 0, mListener);// 参1表示位置提供者,参2表示最短更新时间,参3表示最短更新距离
    }

    class MyLocationListener implements LocationListener {

        // 位置发生变化
        @Override
        public void onLocationChanged(Location location) {
//            String longitude = "经度:" + location.getLongitude();
//            String latitude = "纬度:" + location.getLatitude();
//            String accuracy = "精确度:" + location.getAccuracy();
//            String altitude = "海拔:" + location.getAltitude();
            mSpref.edit().putString("location", "longitude:" + location.getLongitude() +"; latitude:" + location.getLatitude()).apply();
            stopSelf();  //停掉service

        }

        // 位置提供者状态发生变化
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            System.out.println("onStatusChanged");
        }

        // 用户打开gps
        @Override
        public void onProviderEnabled(String provider) {
            System.out.println("onProviderEnabled");
        }

        // 用户关闭gps
        @Override
        public void onProviderDisabled(String provider) {
            System.out.println("onProviderDisabled");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.removeUpdates(mListener);// 当activity销毁时,停止更新位置, 节省电量
    }
}
