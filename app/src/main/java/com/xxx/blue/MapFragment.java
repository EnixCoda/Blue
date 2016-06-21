package com.xxx.blue;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.Text;
import com.amap.api.maps2d.model.TextOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

import java.util.ArrayList;

import getData.SiteData;
import utility.AMapUtil;
import utility.ToastUtil;

/**
 * Created by sits on 2016/6/16.
 */
public class MapFragment extends Fragment implements GeocodeSearch.OnGeocodeSearchListener, View.OnClickListener {

    private ProgressDialog progDialog = null;
    private GeocodeSearch geocoderSearch;
    private String addressName;
    private AMap aMap;
    private MapView mapView;
    private Marker geoMarker;
    private Marker regeoMarker;
    private LatLonPoint latLonPoint = new LatLonPoint(31.2047372, 121.4489017);


    private double aimpos[] = new double[2];
    private double lat[], lon[];
    private int aqi[];
    private Marker marker;
    private LatLng latLng;
    private TextOptions textOptions = new TextOptions();
    private String textnumber = "";
    private int a = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        if (b != null) {
            a = b.getInt("num");
            lat = b.getDoubleArray("lat");
            lon = b.getDoubleArray("lon");
            aimpos = b.getDoubleArray("aim");
            aqi = b.getIntArray("aqi");
        }

    }

    public void setData(ArrayList<SiteData> arrayList) {
        double avgLat = 0, avgLon = 0;
        double[] latd = new double[10], lond = new double[10];
        int[] aqid = new int[10];
        int i;
        for (i = 0; i < arrayList.size() && i < 10; i++) {
            aqid[i] = arrayList.get(i).pm25;
            latd[i] = arrayList.get(i).lat;
            avgLat += arrayList.get(i).lat;
            lond[i] = arrayList.get(i).lon;
            avgLon += arrayList.get(i).lon;
        }

        lat = latd;
        lon = lond;
        aimpos[0] = avgLat/i;
        aimpos[1] = avgLon/i;
        aqi = aqid;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_fragment, null);
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        init(view);
        return view;
    }

    public void init(View view) {
        if (lat != null) {
            if (aMap == null) {
                aMap = mapView.getMap();
                geoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                regeoMarker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                        .icon(BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }

            geocoderSearch = new GeocodeSearch(getActivity());
            geocoderSearch.setOnGeocodeSearchListener(this);
            progDialog = new ProgressDialog(getActivity());

            add();
        }

    }


    public void add() {
        aim(new LatLonPoint(aimpos[0], aimpos[1]));
        for (int i = 0; i < lat.length; i++) {
            addMarkers(new LatLonPoint(lat[i], lon[i]), String.valueOf(aqi[i]));
        }
    }

    public void add(String[][] strings) {
        aim(new LatLonPoint(Double.parseDouble(strings[0][1]), Double.parseDouble(strings[0][0])));
        for (String[] string : strings) {
            Double lat = Double.parseDouble(string[1]);
            Double lon = Double.parseDouble(string[0]);
            Log.v("sl40", lat + lon + "");
            addMarkers(new LatLonPoint(lat, lon), string[2]);
        }
    }

    private void aim(LatLonPoint lat) {
        aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                AMapUtil.convertToLatLng(lat), 11));

        //9 sj //13
    }

    private void addMarkers(LatLonPoint latLonPoint, String number) {

        int aqi = Integer.parseInt(number);
        if (aqi < 50) aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_GREEN))).setPosition(AMapUtil.convertToLatLng(latLonPoint));
        if (aqi >= 50) aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))).setPosition(AMapUtil.convertToLatLng(latLonPoint));
        if (aqi >= 100) aMap.addMarker(new MarkerOptions().anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED))).setPosition(AMapUtil.convertToLatLng(latLonPoint));


        latLng = new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude());
        textOptions.position(latLng)
                .text(number).fontColor(Color.BLACK)
                .fontSize(30).align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
                .zIndex(1.f).typeface(Typeface.DEFAULT_BOLD);
        aMap.addText(textOptions);

    }

    /**
     * 方法必须重写
     */
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * ckrr
     * 方法必须重写
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    /**
     * 显示进度条对话框
     */
    public void showDialog() {
        progDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progDialog.setIndeterminate(false);
        progDialog.setCancelable(true);
        progDialog.setMessage("正在获取地址");
        progDialog.show();
    }

    /**
     * 隐藏进度条对话框
     */
    public void dismissDialog() {
        if (progDialog != null) {
            progDialog.dismiss();
        }
    }

    /**
     * 响应地理编码
     */
    public void getLatlon(final String name) {
        showDialog();
        GeocodeQuery query = new GeocodeQuery(name, "010");// 第一个参数表示地址，第二个参数表示查询城市，中文或者中文全拼，citycode、adcode，
        geocoderSearch.getFromLocationNameAsyn(query);// 设置同步地理编码请求
    }

    /**
     * 响应逆地理编码
     */
    public void getAddress(final LatLonPoint latLonPoint) {
        showDialog();
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 200,
                GeocodeSearch.AMAP);// 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
        geocoderSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    /**
     * 地理编码查询回调
     */
    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == 1000) {
            if (result != null && result.getGeocodeAddressList() != null
                    && result.getGeocodeAddressList().size() > 0) {
                GeocodeAddress address = result.getGeocodeAddressList().get(0);
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        AMapUtil.convertToLatLng(address.getLatLonPoint()), 15));
                geoMarker.setPosition(AMapUtil.convertToLatLng(address
                        .getLatLonPoint()));
                addressName = "经纬度值:" + address.getLatLonPoint() + "\n位置描述:"
                        + address.getFormatAddress();
                ToastUtil.show(getActivity(), addressName);
            } else {
                ToastUtil.show(getActivity(), "无响应");
            }

        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }
    }

    /**
     * 逆地理编码回调
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {
        dismissDialog();
        if (rCode == 1000) {
            if (result != null && result.getRegeocodeAddress() != null
                    && result.getRegeocodeAddress().getFormatAddress() != null) {
                addressName = result.getRegeocodeAddress().getFormatAddress()
                        + "附近";
                aMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        AMapUtil.convertToLatLng(latLonPoint), 15));
                ToastUtil.show(getActivity(), addressName);
            } else {
                ToastUtil.show(getActivity(), "无响应");
            }
        } else {
            ToastUtil.showerror(getActivity(), rCode);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.searchButton:
//                if(editText1.getText()!=null&&editText2.getText()!=null&&editText3.getText()!=null){
//                    LatLonPoint Point = new LatLonPoint(Double.valueOf(editText1.getText().toString()), Double.valueOf(editText2.getText().toString()));
//                    latLng=new LatLng(Double.valueOf(editText1.getText().toString()), Double.valueOf(editText2.getText().toString()));
//                    textnumber=editText3.getText().toString();
//                    latLonPoint=Point;
//                    addMarkersToMap();
//                    getAddress(Point);
//                }
//                break;
//            default:
//                break;
        }

    }

    private void addMarkersToMap() {
        textOptions.position(latLng)
                .text(textnumber).fontColor(Color.BLACK)
                .fontSize(30).align(Text.ALIGN_CENTER_HORIZONTAL, Text.ALIGN_CENTER_VERTICAL)
                .zIndex(1.f).typeface(Typeface.DEFAULT_BOLD);
        regeoMarker.setPosition(AMapUtil.convertToLatLng(latLonPoint));
        aMap.addText(textOptions);
    }


    public void setMarkerColor(MarkerOptions markerColor, int aqi) {
        if (aqi < 50) markerColor.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        if (aqi >= 50) markerColor.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
        if (aqi >= 100) markerColor.icon(BitmapDescriptorFactory
                .defaultMarker(BitmapDescriptorFactory.HUE_RED));

    }

}
