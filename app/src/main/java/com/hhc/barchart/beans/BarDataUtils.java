package com.hhc.barchart.beans;

import com.hhc.barchart.view.BarGroupInfo;

import java.util.ArrayList;
import java.util.List;

public class BarDataUtils {

    /**
     * 转换LifeInfo类型为BarGroupInfo类型
     */
    public static List<BarGroupInfo> getDataLifeInfo(List<LifeInfo> infos, int maxVaue) {
        if (infos == null || infos.size() == 0) {
            return null;
        }

        List<BarGroupInfo> data = new ArrayList<>();
        BarGroupInfo groupInfo;
        List<Double> mData;
        double value;
        for (LifeInfo info : infos) {
            groupInfo = new BarGroupInfo();
            mData = new ArrayList<>();
            groupInfo.setData(mData);

            //计算百分比
            value = (double) info.getRestaurant_num() / (double) maxVaue;
            mData.add(value);
            value = (double) info.getBank_num() / (double) maxVaue;
            mData.add(value);
            value = (double) info.getShopping_num() / (double) maxVaue;
            mData.add(value);
            value = (double) info.getHotel_num() / (double) maxVaue;
            mData.add(value);

            //设置标题
            groupInfo.setName(info.getBuilding_name());

            data.add(groupInfo);
        }
        return data;
    }

    /**
     * 设置BusinessInfo类型数据
     */
    public static List<BarGroupInfo> getDataBusinessInfo(List<BusinessInfo> infos, int maxVaue) {
        if (infos == null || infos.size() == 0) {
            return null;
        }

        List<BarGroupInfo> data = new ArrayList<>();
        BarGroupInfo groupInfo;
        List<Double> mData;
        double value;
        for (BusinessInfo info : infos) {
            groupInfo = new BarGroupInfo();
            mData = new ArrayList<>();
            groupInfo.setData(mData);

            //计算百分比
            value = (double) info.getOffice_num() / (double) maxVaue;
            mData.add(value);
            value = (double) info.getCoffee_num() / (double) maxVaue;
            mData.add(value);

            //设置标题
            groupInfo.setName(info.getBuilding_name());

            data.add(groupInfo);
        }
        return data;
    }
}
