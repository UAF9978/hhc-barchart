package com.hhc.barchart.view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 条形图组
 *
 * @author hehuachuan
 */
class BarGroup {
    private List<Double> mDatas;//百分比数据
    private List<Integer> mColors;//颜色值
    private String mTitle;//标题
    private int mTotalHeight;//总绘制高度

    private int mStripHSpacing = 10;//条状间水平间距
    private int mStripWidth = 10;//条状宽度

    private List<String> mTitleSegment = new ArrayList<>();//标题分段
    private int mTitleVSpacing = 20;//标题上下间距
    private int mTitleColor = Color.DKGRAY;//标题颜色
    private int mTitleSize = 35;//标题颜色

    private double dh;
    private int mWidth;//整个条组图宽度
    private Rect mTitleRect = new Rect();

    public BarGroup(List<Double> mDatas, List<Integer> mColors) {
        this.mDatas = mDatas;
        this.mColors = mColors;
        calculateWidth();
    }

    public void onDraw(Canvas canvas, Paint paint, int x, int y) {
        for (int i = 0; i < mDatas.size(); i++) {
            paint.setColor(mColors.get(i));
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(2);

            dh = mTotalHeight - mTotalHeight * mDatas.get(i);
            canvas.drawRect(x + i * (mStripWidth + mStripHSpacing), y + (float) dh, x
                    + i * (mStripWidth + mStripHSpacing) + mStripWidth, y + mTotalHeight, paint);
        }

        if (!TextUtils.isEmpty(mTitle) && mTitleSegment.size() > 0) {
            paint.setColor(mTitleColor);
            paint.setTextSize(mTitleSize);

            for (int i = 0; i < mTitleSegment.size(); i++) {
                paint.getTextBounds(mTitleSegment.get(i), 0, mTitleSegment.get(i).length(), mTitleRect);
                if (i == 0) {
                    canvas.drawText(mTitleSegment.get(i), x + (mWidth - mTitleRect.width()) / 2,
                            y + mTotalHeight + mTitleRect.height() + mTitleVSpacing
                                    + i * mTitleRect.height(), paint);
                    continue;
                }
                canvas.drawText(mTitleSegment.get(i), x + (mWidth - mTitleRect.width()) / 2,
                        y + mTotalHeight + mTitleRect.height() + mTitleVSpacing
                                + i * mTitleRect.height() + 10, paint);
            }
        }
    }

    public void setStripHSpacing(int stripHSpacing) {
        if (mStripHSpacing == stripHSpacing) {
            return;
        }
        mStripHSpacing = stripHSpacing;
        calculateWidth();
    }

    public void setStripWidth(int stripWidth) {
        if (mStripWidth == stripWidth) {
            return;
        }
        mStripWidth = stripWidth;
        calculateWidth();
    }

    public void setTitle(String title) {
        if (TextUtils.isEmpty(title) || title.equals(mTitle)) {
            return;
        }
        mTitle = title;
        mTitleSegment.clear();
        mTitleSegment.addAll(BarTextUtils.getStrList(mTitle, 5));
    }

    public void setTotalHeight(int totalHeight) {
        if (mTotalHeight == totalHeight) {
            return;
        }
        mTotalHeight = totalHeight;
    }

    public void setTitleVSpacing(int titleVSpacing) {
        mTitleVSpacing = titleVSpacing;
    }

    public void setTitleColor(int titleColor) {
        mTitleColor = titleColor;
    }

    public void setTitleSize(int titleSize) {
        mTitleSize = titleSize;
    }

    public int getWidth() {
        return mWidth;
    }

    /**
     * 条组宽度
     */
    private void calculateWidth() {
        mWidth = 0;
        for (int i = 0; i < mDatas.size(); i++) {
            if (i >= mDatas.size() - 1) {
                mWidth = mWidth + mStripWidth;
            } else {
                mWidth = mWidth + mStripWidth + mStripHSpacing;
            }
        }
    }
}
