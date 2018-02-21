package com.example.sufehelperapp;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * 多节点进度条自定义视图
 * @author huqiang
 *
 */
public class MutiProgress extends View{

    private int nodesNum ; //节点数量
    private Drawable progressingDrawable;  //进行中的图标
    private Drawable unprogressingDrawable;
    private Drawable progresFailDrawable;  //失败的节点
    private Drawable progresSuccDrawable;  //成功的节点
    private int nodeRadius;  //节点的半径
    private int processingLineColor;  //进度条的颜色

    //    private int progressLineHeight;   //进度条的高度
    private int currNodeNO;  //当前进行到的节点编号。从0开始计算
    private int currNodeState; //当前进行到的节点编号所对应的状态 0：失败  1：成功
    //    private int textSize;  //字体大小
    Context mContext;

    int mWidth,mHeight;
    private Paint mPaint;
    private Canvas mCanvas;
    private Bitmap mBitmap; //mCanvas绘制在这上面
    private ArrayList<Node> nodes;

    private int DEFAULT_LINE_COLOR = Color.BLUE;
    public MutiProgress(Context context) {
        this(context,null);

    }
    public MutiProgress(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }
    public MutiProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,R.styleable.MutiProgress);
        nodesNum = mTypedArray.getInteger(R.styleable.MutiProgress_nodesNum, 1); //默认一个节点
        nodeRadius = mTypedArray.getDimensionPixelSize(R.styleable.MutiProgress_nodeRadius, 10); //节点半径
        progressingDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_progressingDrawable);
        unprogressingDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_unprogressingDrawable);
        progresFailDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_progresFailDrawable);
        progresSuccDrawable = mTypedArray.getDrawable(R.styleable.MutiProgress_progresSuccDrawable);
        processingLineColor = mTypedArray.getColor(R.styleable.MutiProgress_processingLineColor, DEFAULT_LINE_COLOR);
        currNodeState = mTypedArray.getInt(R.styleable.MutiProgress_currNodeState, 1);
        currNodeNO = mTypedArray.getInt(R.styleable.MutiProgress_currNodeNO, 1);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Config.ARGB_8888);
        mPaint = new Paint();
        mPaint.setColor(processingLineColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeJoin(Paint.Join.ROUND); // 圆角
        mPaint.setStrokeCap(Paint.Cap.ROUND); // 圆角
        mCanvas = new Canvas(mBitmap);

        nodes = new ArrayList<MutiProgress.Node>();
        float nodeWidth = ((float)mWidth)/(nodesNum-1);
        for(int i=0;i<nodesNum;i++)
        {
            Node node = new Node();
            if(i==0)
                node.mPoint = new Point(((int)nodeWidth*i),mHeight/2-nodeRadius);
            else if(i==(nodesNum-1))
                node.mPoint = new Point(((int)nodeWidth*i)-nodeRadius*2,mHeight/2-nodeRadius);
            else
                node.mPoint = new Point(((int)nodeWidth*i)-nodeRadius,mHeight/2-nodeRadius);
            if(currNodeNO == i)
                node.type = 1;  //当前进度所到达的节点
            else
                node.type = 0; //已完成
            nodes.add(node);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        DrawProgerss();
        Log.v("ondraw", "mBitmap="+mBitmap);
        if(mBitmap!=null)
        {
            canvas.drawBitmap(mBitmap, new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight()), new Rect(0,0,mBitmap.getWidth(),mBitmap.getHeight()), mPaint);
        }
        for(int i=0;i<nodes.size();i++)
        {
            Node node = nodes.get(i);
            Log.v("ondraw", node.mPoint.x +";y="+node.mPoint.y);
            if(i<currNodeNO)  //已完成的进度节点
            {
                progressingDrawable.setBounds(node.mPoint.x,  node.mPoint.y , node.mPoint.x + nodeRadius*2,node.mPoint.y + nodeRadius*2);
                progressingDrawable.draw(canvas);
            }
            else if(i==currNodeNO)  //当前所到达的进度节点（终点）
            {
                if(currNodeState == 1) //判断是成功还是失败  0 :失败  1：成功
                {
                    progresSuccDrawable.setBounds(node.mPoint.x,  node.mPoint.y , node.mPoint.x + nodeRadius*2,node.mPoint.y + nodeRadius*2);
                    progresSuccDrawable.draw(canvas);
                }
                else
                {
                    progresFailDrawable.setBounds(node.mPoint.x,  node.mPoint.y , node.mPoint.x + nodeRadius*2,node.mPoint.y + nodeRadius*2);
                    progresFailDrawable.draw(canvas);
                }
            }
            else   //未完成的进度节点
            {
                unprogressingDrawable.setBounds(node.mPoint.x,  node.mPoint.y , node.mPoint.x + nodeRadius*2,node.mPoint.y + nodeRadius*2);
                unprogressingDrawable.draw(canvas);
            }
        }
    }
    private void DrawProgerss()
    {
        //先画背景
        Paint bgPaint = new Paint();
        bgPaint.setColor(Color.parseColor("#f0f0f0"));
        mCanvas.drawRect(0, 0, mWidth, mHeight, bgPaint);
        //先画线段，线段的高度为nodeRadius/2
        mPaint.setStrokeWidth(nodeRadius/2);
        //前半截线段
//        mCanvas.drawLine(nodeRadius, mHeight/2, mWidth-nodeRadius, mHeight/2, mPaint);  //线段2端去掉nodeRadius
        mCanvas.drawLine(nodeRadius, mHeight/2, nodes.get(currNodeNO).mPoint.x + nodeRadius, nodes.get(currNodeNO).mPoint.y + nodeRadius, mPaint);  //线段2端去掉nodeRadius
        //后半截线段
        mPaint.setColor(Color.parseColor("#dddddd"));
        mCanvas.drawLine(nodes.get(currNodeNO).mPoint.x +nodeRadius, nodes.get(currNodeNO).mPoint.y + nodeRadius, mWidth-nodeRadius, mHeight/2, mPaint);  //线段2端去掉nodeRadius
    }
    class Node
    {
        Point mPoint;
        int type; //0:已完成  1:当前到达的进度节点
    }
}
