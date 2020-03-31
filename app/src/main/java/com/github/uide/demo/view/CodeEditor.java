package com.github.uide.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CodeEditor extends View {
    //The height and width of CodeView
    private int widthSize;
    private int heightSize;

    //  行号偏移度
    private float startOffset = 0;

    //  不同类型字符颜色
    private static int INT_COLOR = 0xFFFFFFFF;
    private static int PLAIN_COLOR = 0xFFFFFFFF;
    private static int STRING_COLOR = 0xFF98FB98;
    private static int KEY_WORD_COLOR = 0xFFFF00FF;
    private static int DIVIDE_LINE_COLOR = 0xFF1E90FF;

    //  字符大小
    private static final float PADDING = 5F;


    private static float TEXT_SIZE = 24F;

    //  不同类型字符的画笔
    private Paint INT_PAINT;
    private Paint PLAIN_PAINT;
    private Paint STRING_PAINT;
    private Paint KEY_WORD_PAINT;
    private Paint DIVIDE_LINE_PAINT;

    public CodeEditor(Context context) {
        super(context);
    }

    public CodeEditor(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CodeEditor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.widthSize = MeasureSpec.getSize(widthMeasureSpec);
        this.heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int withMode=MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
//        withMode=MeasureSpec.EXACTLY;
//        heightMode=MeasureSpec.EXACTLY;
//        setMeasuredDimension(MeasureSpec.makeMeasureSpec());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setStartOffset(float startOffset) {
        this.startOffset = startOffset;
    }

    public static void setIntColor(int intColor) {
        INT_COLOR = intColor;
    }

    public static void setPlainColor(int plainColor) {
        PLAIN_COLOR = plainColor;
    }

    public static void setStringColor(int stringColor) {
        STRING_COLOR = stringColor;
    }

    public static void setKeyWordColor(int keyWordColor) {
        KEY_WORD_COLOR = keyWordColor;
    }

    public static void setDivideLineColor(int divideLineColor) {
        DIVIDE_LINE_COLOR = divideLineColor;
    }

    public static int getIntColor() {
        return INT_COLOR;
    }

    public static int getPlainColor() {
        return PLAIN_COLOR;
    }

    public static int getStringColor() {
        return STRING_COLOR;
    }

    public static int getKeyWordColor() {
        return KEY_WORD_COLOR;
    }

    public static int getDivideLineColor() {
        return DIVIDE_LINE_COLOR;
    }

    public static float getPADDING() {
        return PADDING;
    }

    public static float getTextSize() {
        return TEXT_SIZE;
    }

    public static void setTextSize(float textSize) {
        TEXT_SIZE = textSize;
    }


}

