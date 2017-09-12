package com.hhc.barchart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 条形图View
 *
 * @author hehuachuan
 */
public class BarFixView extends View {
    private Paint mPaint;
    private Path mPath;

    //参数数据
    private List<String> mLabels = new ArrayList<>();//标签文本
    private List<Integer> mColors = new ArrayList<>();//标签与条形色值
    private String mDescribe;//描述文本
    private double maxValue = 100d;//最大值
    private double sectionValue = 20d;//每刻度多大值

    //条形组属性值
    private List<BarGroup> mColumnGroups = new ArrayList<>();
    private int mTotalHeight;
    private int mStripGroupSpc;//条形组之间间距
    private int mStripWidth;//条形宽度
    private int mStripSpc;//条形之间间距
    private Rect mRect = new Rect();

    //坐标刻度属性值
    private int mScaleNumber;//刻度数量
    private double mScaleSpc;//刻度实际绘制间距
    private int mXTextMaxW;//X坐标文本最大宽度
    private int mXTextMaxH;//X坐标文本最大高度
    private int mXTextSize;//X坐标字体大小
    private int mXTextColor;//X坐标字体颜色
    private int mXTextSpc;//X坐标字体与虚线间距

    //绘制标签属性设置
    private int blockSize;//方块大小
    private int blockSpc;//方块与方块之间间距
    private int blockDownSpc;//方块下间距
    private int labelColor;//标签字体颜色
    private int describeColor;//描述字体颜色
    private int describeSize;//描述字体大小
    private int blockLabelSpc;//方块与标签间距

    public BarFixView(Context context) {
        this(context, null);
    }

    public BarFixView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BarFixView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        PathEffect pathEffect = new DashPathEffect(new float[]{10f, 2f}, 0);
        mPaint.setPathEffect(pathEffect);
        mPath = new Path();
    }

    private void init(Context context) {
        mStripGroupSpc = dip2px(context, 100);//条形组之间间距
        mStripWidth = dip2px(context, 10);//条形宽度
        mStripSpc = dip2px(context, 8);//条形宽度

        mXTextSize = dip2px(context, 14);//X坐标字体大小
        mXTextColor = Color.parseColor("#CCCCCC");//X坐标字体颜色
        mXTextSpc = dip2px(context, 5);//X坐标字体与虚线间距

        blockSize = dip2px(context, 14);//方块大小
        blockSpc = dip2px(context, 98);//方块与方块之间间距
        blockDownSpc = dip2px(context, 10);//方块下间距
        labelColor = Color.parseColor("#6A6A77");//标签字体颜色
        describeColor = Color.parseColor("#9EA4AF");//描述字体颜色
        describeSize = dip2px(context, 12);//描述字体大小
        blockLabelSpc = dip2px(context, 6);//方块与标签间距
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int h = dip2px(getContext(), 93);
        if (heightMode == MeasureSpec.UNSPECIFIED || heightMode == MeasureSpec.AT_MOST) {
            height = h + dip2px(getContext(), 120);
        } else {
            if (height < h) {
                height = h;
            }
        }

        heightMeasureSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mTotalHeight = getMeasuredHeight() - h;
        calculationScale();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int y = 0;
        int x = 0;
        drawCoordinateXText(canvas, mPaint);

        x = mXTextMaxW + mXTextSpc;
        y = mXTextMaxH / 2;
        drawCoordinateDottedLine(canvas, mPaint, x, y);

        x = x + 100;
        drawColumnGroup(canvas, mPaint, x, y);

        drawLabel(canvas, mPaint);
    }

    private void drawCoordinateXText(Canvas canvas, Paint paint) {
        int index = mScaleNumber - 1;
        paint.setColor(mXTextColor);
        paint.setTextSize(mXTextSize);
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mScaleNumber; i++) {
            String valueY = String.valueOf((int) (index * sectionValue));
            index--;
            paint.getTextBounds(valueY, 0, valueY.length(), mRect);
            if (mXTextMaxW <= mRect.width()) {
                mXTextMaxW = mRect.width();
            }
            if (mXTextMaxH <= mRect.height()) {
                mXTextMaxH = mRect.height();
            }

            if (i == mScaleNumber - 1) {
                canvas.drawText(valueY, mXTextMaxW - mRect.width(), (float) (mScaleSpc * i) + mXTextMaxH / 2, paint);
            } else {
                canvas.drawText(valueY, mXTextMaxW - mRect.width(), (float) (mScaleSpc * i) + mXTextMaxH, paint);
            }
        }
    }

    /**
     * 绘制坐标系【虚线】
     *
     * @param canvas
     * @param paint
     * @param x
     * @param y
     */
    private void drawCoordinateDottedLine(Canvas canvas, Paint paint, int x, int y) {
        int mDottedLineWidth = getMeasuredWidth() - mXTextSpc - mXTextMaxW;
        paint.setColor(mXTextColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1);
        for (int i = 0; i < mScaleNumber; i++) {
            mPath.reset();
            mPath.moveTo(x, y + (float) (mScaleSpc * i));
            mPath.lineTo(x + mDottedLineWidth, y + (float) (mScaleSpc * i));
            canvas.drawPath(mPath, paint);
        }
    }

    /**
     * 绘制条形图
     *
     * @param canvas
     * @param paint
     * @param x
     * @param y
     */
    private void drawColumnGroup(Canvas canvas, Paint paint, int x, int y) {
        int i = 0;
        for (BarGroup columnGroup : mColumnGroups) {
            columnGroup.setTotalHeight(mTotalHeight);
            columnGroup.onDraw(canvas, mPaint, x + mStripGroupSpc * i, y);
            i++;
        }
    }

    /**
     * 绘制标签
     *
     * @param canvas
     * @param paint
     */
    private void drawLabel(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(2);
        paint.setTextSize(blockSize);
        paint.setStyle(Paint.Style.FILL);

        String mLabel;
        Integer mColor;
        int x1 = 0;
        int y1 = mTotalHeight + mXTextMaxH / 2 + dip2px(getContext(), 50);
        for (int i = 0; i < mLabels.size(); i++) {
            mLabel = mLabels.get(i);
            mColor = mColors.get(i);

            //绘制矩形框
            paint.setColor(mColor);
            canvas.drawRect(x1 + i * blockSpc, y1, x1 + i * blockSpc + blockSize, y1 + blockSize, paint);

            //绘制标签文本
            paint.getTextBounds(mLabel, 0, mLabel.length(), mRect);
            paint.setColor(labelColor);
            canvas.drawText(mLabel, x1 + i * blockSpc + blockSize + blockLabelSpc, y1 + mRect.height() - 5, paint);
        }

        if (!TextUtils.isEmpty(mDescribe)) {
            paint.setTextSize(describeSize);
            paint.setColor(describeColor);

            paint.getTextBounds(mDescribe, 0, mDescribe.length(), mRect);
            canvas.drawText(mDescribe, x1, y1 + blockSize + blockDownSpc + mRect.height(), paint);
        }
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 计算刻度数据
     */
    private void calculationScale() {
        mScaleNumber = (int) (maxValue / sectionValue) + 1;
        mScaleSpc = mTotalHeight * (sectionValue / maxValue);
    }

    /**
     * 设置标签
     */
    public void setLabels(List<Integer> mColors, List<String> mLabels) {
        if (mColors == null || mColors.size() == 0
                || mLabels == null || mLabels.size() == 0) {
            return;
        }
        if (mColors.size() != mLabels.size()) {
            return;
        }
        this.mColors.clear();
        this.mLabels.clear();
        this.mColors.addAll(mColors);
        this.mLabels.addAll(mLabels);
        invalidate();
    }

    /**
     * 设置描述
     */
    public void setDescribe(String describe) {
        this.mDescribe = describe;
        invalidate();
    }

    /**
     * 设置数据
     *
     * @param mData
     * @param maxValue     最大值
     * @param sectionValue 每刻度多大值
     * @throws Exception
     */
    public void setData(List<BarGroupInfo> mData, double maxValue, double sectionValue) throws Exception {
        if (mData == null || mData.size() == 0) {
            return;
        }

        BarGroup mColumnGroup;
        List<BarGroup> groups = new ArrayList<>();
        int color = Color.parseColor("#9EA4AF");
        for (BarGroupInfo info : mData) {
            List<Double> d = info.getData();
            if (d.size() != mLabels.size()) {
                throw new Exception("条形组数据与标签数量不相同!");
            }
            mColumnGroup = new BarGroup(d, mColors);

            //设置标题
            mColumnGroup.setTitle(info.getName());
            mColumnGroup.setTitleColor(color);
            mColumnGroup.setStripWidth(mStripWidth);
            mColumnGroup.setStripHSpacing(mStripSpc);

            groups.add(mColumnGroup);
        }

        this.maxValue = maxValue;
        this.sectionValue = sectionValue;
        mColumnGroups.addAll(groups);
        invalidate();
    }

    /**
     * 无数据
     */
    public void clearData() {
        mLabels.clear();
        mLabels.add("暂无");
        mLabels.add("暂无");
        mLabels.add("暂无");
        mLabels.add("暂无");

        mColors.clear();
        mColors.add(mXTextColor);
        mColors.add(mXTextColor);
        mColors.add(mXTextColor);
        mColors.add(mXTextColor);
        mDescribe = "说明：暂无说明";

        BarGroup mColumnGroup;
        List<BarGroup> groups = new ArrayList<>();
        int color = Color.parseColor("#9EA4AF");
        for (int i = 0; i < 3; i++) {
            List<Double> d = new ArrayList<>();
            mColumnGroup = new BarGroup(d, mColors);
            d.add(0d);
            d.add(0d);
            d.add(0d);
            d.add(0d);

            //设置标题
            mColumnGroup.setTitle("暂无数据");
            mColumnGroup.setTitleColor(color);
            mColumnGroup.setStripWidth(mStripWidth);
            mColumnGroup.setStripHSpacing(mStripSpc);

            groups.add(mColumnGroup);
        }

        this.maxValue = 100d;
        this.sectionValue = 20d;
        mColumnGroups.addAll(groups);
        invalidate();
    }
}
