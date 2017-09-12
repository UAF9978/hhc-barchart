package com.hhc.barchart.view;

import java.util.List;

/**
 * 条形图组信息
 *
 * @author hehuachuan
 */
public class BarGroupInfo {
    private List<Double> mData;
    private String name;

    public List<Double> getData() {
        return mData;
    }

    public void setData(List<Double> data) {
        mData = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
