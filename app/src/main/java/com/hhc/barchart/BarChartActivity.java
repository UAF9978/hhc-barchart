package com.hhc.barchart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hhc.barchart.beans.BarDataUtils;
import com.hhc.barchart.beans.BusinessInfo;
import com.hhc.barchart.beans.LifeInfo;
import com.hhc.barchart.view.BarFixView;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {
    private BarFixView mBarFixView1;
    private BarFixView mBarFixView2;
    private BarFixView mBarFixView3;
    private BarFixView mBarFixView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBarFixView1 = (BarFixView) findViewById(R.id.mBarFixView1);
        mBarFixView2 = (BarFixView) findViewById(R.id.mBarFixView2);
        mBarFixView3 = (BarFixView) findViewById(R.id.mBarFixView3);
        mBarFixView4 = (BarFixView) findViewById(R.id.mBarFixView4);

        setBarFixView1(mBarFixView1);
        setBarFixView2(mBarFixView2);
        setBarFixView3(mBarFixView3);
        mBarFixView4.clearData();
    }

    private void setBarFixView1(BarFixView mBarFixView1) {
        mBarFixView1.setDescribe("说明：上述统计为楼盘附近1公里范围");

        List<String> mLabels = new ArrayList<>();
        mLabels.add("餐饮");
        mLabels.add("银行");
        mLabels.add("购物");
        mLabels.add("酒店");

        List<Integer> mColors = new ArrayList<>();
        mColors.add(Color.parseColor("#FC4758"));
        mColors.add(Color.parseColor("#64B3FB"));
        mColors.add(Color.parseColor("#ADA4FD"));
        mColors.add(Color.parseColor("#27D2B4"));
        mBarFixView1.setLabels(mColors, mLabels);

        List<LifeInfo> lifeInfos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LifeInfo info = new LifeInfo();
            info.setBuilding_name("某某股份科技限责任");
            info.setBank_num((int) (Math.random() * 100) + 20);
            info.setHotel_num((int) (Math.random() * 100) + 20);
            info.setRestaurant_num((int) (Math.random() * 10) + 20);
            info.setShopping_num((int) (Math.random() * 10) + 20);
            lifeInfos.add(info);
        }

        try {
            mBarFixView1.setData(BarDataUtils.getDataLifeInfo(lifeInfos, 100), 100d, 20d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBarFixView2(BarFixView mBarFixView1) {
        mBarFixView1.setDescribe("说明：上述统计为楼盘附近1公里范围");

        List<String> mLabels = new ArrayList<>();
        mLabels.add("餐饮");
        mLabels.add("银行");
        mLabels.add("购物");
        mLabels.add("酒店");

        List<Integer> mColors = new ArrayList<>();
        mColors.add(Color.RED);
        mColors.add(Color.BLUE);
        mColors.add(Color.GREEN);
        mColors.add(Color.YELLOW);
        mBarFixView1.setLabels(mColors, mLabels);

        List<LifeInfo> lifeInfos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            LifeInfo info = new LifeInfo();
            info.setBuilding_name("某某股份有限责任");
            info.setBank_num(90);
            info.setHotel_num((int) (Math.random() * 100));
            info.setRestaurant_num(60);
            info.setShopping_num(56);
            lifeInfos.add(info);
        }

        try {
            mBarFixView1.setData(BarDataUtils.getDataLifeInfo(lifeInfos, 100), 100d, 20d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBarFixView3(BarFixView mBarFixView1) {
        mBarFixView1.setDescribe("说明：上述统计为楼盘附近1公里范围");

        List<String> mLabels = new ArrayList<>();
        mLabels.add("餐饮");
        mLabels.add("银行");

        List<Integer> mColors = new ArrayList<>();
        mColors.add(Color.parseColor("#FC4758"));
        mColors.add(Color.parseColor("#64B3FB"));
        mBarFixView1.setLabels(mColors, mLabels);

        List<BusinessInfo> businessInfos = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BusinessInfo info = new BusinessInfo();
            info.setBuilding_name("某某科技有限责任");
            info.setCoffee_num((int) (Math.random() * 100));
            info.setOffice_num((int) (Math.random() * 100));
            businessInfos.add(info);
        }

        try {
            mBarFixView1.setData(BarDataUtils.getDataBusinessInfo(businessInfos, 100), 100d, 20d);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
