package com.example.sufehelperapp;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.listener.ComboLineColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.PieChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.ComboLineColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ComboLineColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class Explore_weekly extends AppCompatActivity {
    private String myPhone;
    Connection con;
    ResultSet rs;
    private user user;
    private ComboLineColumnChartView comboChart;
    private int[] taskNum = {0,0,0,0};
    private ComboLineColumnChartData comboLineColumnChartData;
    private List<PointValue> pointValues = new ArrayList<PointValue>();

    private LineChartView lineChartView;
    private String [] weekend = new String[] {"Mon","Tues","Wed","Thur"};
    private int[] score = {0,0,0,0};
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();

    private PieChartView pieChartView;
    private List<SliceValue> values = new ArrayList<SliceValue>();
    private PieChartData pieChartData;
    private double[] payment = {0,0};
    private int[] color = {Color.parseColor("#33CCFF"),Color.parseColor("#9966FF")};
    private String[] stateChar = {"收入","支出"};

    private TextView mDateDisplay;
    private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    static final int DATE_DIALOG_ID = 0;

    private int launch_w1 = 0;
    private int launch_w2 = 0;
    private int launch_w3 = 0;
    private int launch_w4 = 0;

    private int receive_w1 = 0;
    private int receive_w2 = 0;
    private int receive_w3 = 0;
    private int receive_w4 = 0;

    private double pay = 0;
    private double earn = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore_weekly);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //user
        myPhone = getIntent().getStringExtra("user_phone");

        try{
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            con = DbUtils.getConn();
            Statement st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM `user` WHERE `phonenumber` = '"+myPhone+"'");

            List<user> userList = new ArrayList<>();
            List list = DbUtils.populate(rs,user.class);
            for(int i=0; i<list.size(); i++){
                userList.add((user)list.get(i));
            }
            user = userList.get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                }

        }

        getLaunchAndPay();
        getReceiveAndEarn();

        //综合图
        comboChart = (ComboLineColumnChartView) findViewById(R.id.launch_chartview);
        comboChart.setOnValueTouchListener(selectListener);
        getComboAxisPoints();
        initComboChart();

        //折线图
        lineChartView = (LineChartView) findViewById(R.id.launch_time);
        getAxisXLables();//获取x轴的标注
        getAxisPoints(); //获取坐标点
        initLineChart(); //初始化

        //饼状图
        pieChartView = (PieChartView) findViewById(R.id.payment_chartview);
        pieChartView.setOnValueTouchListener(selectListener1);//设置点击事件监听
        setPieChartData();
        initPieChart();

        BottomNavigationView bottomNavigationItemView = (BottomNavigationView) findViewById(R.id.btn_navigation);
        bottomNavigationItemView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId())
                {
                    case R.id.item_task:
                        Intent intent1 = new Intent(Explore_weekly.this, Task_HomeActivity.class);
                        intent1.putExtra("user_phone", myPhone);
                        startActivity(intent1);
                        break;
                    case R.id.item_explore:
                        Intent intent3 = new Intent(Explore_weekly.this, ExploreActivity.class);
                        intent3.putExtra("user_phone", myPhone);
                        startActivity(intent3);
                        break;
                    case R.id.item_my:
                        Intent intent2 = new Intent(Explore_weekly.this, My_HomeActivity.class);
                        intent2.putExtra("user_phone", myPhone);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });


        Button button1 = (Button) findViewById(R.id.title_back);
        button1.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick (View view){
                Intent intent = new Intent(Explore_weekly.this, ExploreActivity.class);
                intent.putExtra("user_phone", myPhone);
                startActivity(intent);
            }
        });
       // mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);
        mPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Explore_weekly.this.showDialog(DATE_DIALOG_ID);
            }
        });

        final Calendar c = Calendar.getInstance(Locale.CHINA);
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        updateDisplay();
    }
    private void updateDisplay() {
        mPickDate.setText(new StringBuffer()
                //Month is 0 base so add 1
                .append(mYear).append("-")
                .append(mMonth + 1).append("-")
                .append(mDay).append(" ")

        );
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,DatePickerDialog.THEME_DEVICE_DEFAULT_LIGHT,mOnDateSetListener , mYear, mMonth, mDay);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener mOnDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }
            };

    private void getAxisXLables() {
        for (int i = 0; i < weekend.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(weekend[i]));
        }
    }
    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i,score[i]));
        }
    }

    private void initLineChart() {
        Line line = new Line(mPointValues).setColor(Color.parseColor("#FF9966"));//折线颜色
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
        line.setHasLabelsOnlyForSelected(false);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis();//X轴
        axisX.setHasTiltedLabels(true);//x轴字体是斜的还是直的，true是斜的显示
        //axisX.setTextColor(Color.BLACK);//设置字体颜色
        //axisX.setName("");设置表格名称
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(5);
        axisX.setValues(mAxisXValues);//填充x轴的坐标名称
        data.setAxisXBottom(axisX);//x轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("时间");//y轴标注
        axisY.setTextSize(15);//设置字体大小
        //axisY.setTextColor(Color.BLACK);
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边

        //设置行为属性，支持缩放、滑动以及平移
        lineChartView.setInteractive(true);
        lineChartView.setZoomType(ZoomType.HORIZONTAL);
        lineChartView.setMaxZoom((float) 2);//最大方法比例
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChartView.setLineChartData(data);
        lineChartView.setVisibility(View.VISIBLE);
    }
    private void setPieChartData() {
        for (int i = 0; i < payment.length; i++) {
            SliceValue sliceValue = new SliceValue((float) payment[i],color[i]);
            values.add(sliceValue);
        }
    }
    private void initPieChart() {
        pieChartData = new PieChartData();
        pieChartData.setHasLabels(true);//显示表情
        pieChartData.setHasLabelsOnlyForSelected(false);//不用点击显示占的百分比
        pieChartData.setHasLabelsOutside(false);//占的百分比是否显示在饼图外面
        pieChartData.setHasCenterCircle(true);//是否是环形显示
        pieChartData.setValues(values);//填充数据
        pieChartData.setCenterCircleColor(Color.WHITE);//设置环形中间的颜色
        pieChartData.setCenterCircleScale(0.5f);//设置环形的大小级别
        pieChartView.setPieChartData(pieChartData);
        pieChartView.setValueSelectionEnabled(true);//选择饼图某一块变大
        pieChartView.setAlpha(0.9f);//设置透明度
        pieChartView.setCircleFillRatio(1f);//设置饼图大小

    }
    private PieChartOnValueSelectListener selectListener1 = new PieChartOnValueSelectListener() {

        @Override
        public void onValueDeselected() {


        }

        @Override
        public void onValueSelected(int arg0, SliceValue value) {
            //选择对应图形后，在中间部分显示相应信息
            pieChartData.setCenterText1(stateChar[arg0]);
            pieChartData.setCenterText1Color(color[arg0]);
            pieChartData.setCenterText1FontSize(10);
            pieChartData.setCenterText2(value.getValue() + "（" + calPercent(arg0) + ")");
            pieChartData.setCenterText2Color(color[arg0]);
            pieChartData.setCenterText2FontSize(12);
        }
    };

    private String calPercent(int i) {
        String result = "";
        int sum = 0;
        for (int i1 = 0; i1 < payment.length; i1++) {
            sum += payment[i1];
        }
        result = String.format("%.2f", (float) payment[i] * 100 / sum) + "%";
        return result;
    }

    private ComboLineColumnChartOnValueSelectListener selectListener = new ComboLineColumnChartOnValueSelectListener() {
        @Override
        public void onColumnValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {}
        @Override
        public void onPointValueSelected(int lineIndex, int pointIndex, PointValue value) {}
        @Override
        public void onValueDeselected() {}
    };
    private void initComboChart() {
        comboChart.setZoomEnabled(true);//设置是否支持缩放
        comboChart.setInteractive(true);//设置图表是否可以与用户互动
        comboChart.setValueSelectionEnabled(true);//设置图表数据是否选中进行显示
        comboLineColumnChartData = new ComboLineColumnChartData();
        comboLineColumnChartData.setValueLabelsTextColor(Color.WHITE);// 设置数据文字颜色
        comboLineColumnChartData.setValueLabelTextSize(10);// 设置数据文字大小

        //为组合图设置折线图数据
        Line dataLine = new Line(pointValues).setColor(Color.parseColor("#FF99FF"));//折线颜色
        List<Line> lines = new ArrayList<>();
        dataLine.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        dataLine.setCubic(false);//曲线是否平滑，即是曲线还是折线
        dataLine.setFilled(false);//是否填充曲线的面积
        dataLine.setHasLabels(true);//曲线的数据坐标是否加上备注
        dataLine.setHasLabelsOnlyForSelected(false);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        dataLine.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        dataLine.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(dataLine);
        LineChartData lineCharData = new LineChartData();//initLineChartData(dataLine);
        lineCharData.setLines(lines);
        //坐标轴
        Axis axisX = new Axis();//X轴
        axisX.setHasTiltedLabels(true);//x轴字体是斜的还是直的，true是斜的显示
        axisX.setTextSize(10);//设置字体大小
        axisX.setMaxLabelChars(5);
        axisX.setValues(mAxisXValues);//填充x轴的坐标名称
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("数量/件");//y轴标注
        axisY.setTextSize(15);//设置字体大小

        comboLineColumnChartData.setLineChartData(lineCharData);//为组合图设置折线图数据

        //为组合图设置柱形图数据
        List<Column> dataColumn = initColumnLine();
        ColumnChartData columnChartData = initColumCharData(dataColumn);
        columnChartData.setColumns(dataColumn);
        comboLineColumnChartData.setColumnChartData(columnChartData);//为组合图设置柱形图数据

        comboLineColumnChartData.setAxisYLeft(axisY);
        comboLineColumnChartData.setAxisXBottom(axisX);
        comboChart.setComboLineColumnChartData(comboLineColumnChartData);//为图表设置数据，数据类型为ComboLineColumnChartData
    }
    private void getComboAxisPoints() {
        for (int i = 0; i < taskNum.length; i++) {
            pointValues.add(new PointValue(i,taskNum[i]));
        }
    }

    //定义方法设置柱状图中数据
    private List<Column> initColumnLine() {
        List<Column> list = new ArrayList<>();
        List<SubcolumnValue> subcolumnValueList;
        mAxisXValues = new ArrayList<AxisValue>();
        int numSubcolumns = 1;
        int numColumns = taskNum.length;
        for (int i = 0; i < numColumns; ++i) {
            subcolumnValueList = new ArrayList<SubcolumnValue>();
            for (int j = 0; j < numSubcolumns; ++j) {
                subcolumnValueList.add(new SubcolumnValue((float) taskNum[i], Color.parseColor("#FFCCFF")));
            }
            // 点击柱状图就展示数据量
            //mAxisXValues.add(new AxisValue(i).setLabel(weekend[i]));
            list.add(new Column(subcolumnValueList).setHasLabelsOnlyForSelected(true));
        }
        return list;
    }
    //定义方法设置柱状图中数据
    private ColumnChartData initColumCharData(List<Column> dataLine) {
        ColumnChartData columnChartData = new ColumnChartData(dataLine);
        comboChart.setValueSelectionEnabled(true);
        comboChart.setZoomType(ZoomType.HORIZONTAL);

        return columnChartData;
    }
/*
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(Explore_weekly.this, ExploreActivity.class);
        intent.putExtra("user_now",user);
        startActivity(intent);
        finish();
    }*/

//TODO:数据库调用：调出当前用户所发布的所有任务

    private void getLaunchAndPay(){
        //List<task>taskList = DataSupport.where("launcherName = ?",user.getMyName()).find(task.class);

        List<task>taskList = new ArrayList<>();

        try{

            con = DbUtils.getConn();
            Statement st = con.createStatement();

            rs= st.executeQuery("SELECT * FROM `task` WHERE `launcherName` = '"+user.getMyName()+"'");

            List<task> sampleList = new ArrayList<>(); //清空taskList

            List list = DbUtils.populate(rs,task.class);
            for(int i = 0; i < list.size(); i++){
                sampleList.add((task)list.get(i));
            }

            taskList = sampleList;

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!taskList.isEmpty()){
            for(task task:taskList){
                if(TimeUtils.WithinOneWeek(task.getLaunchtime())){
                    launch_w1++;
                    if(task.getProgress()>=4) {
                        pay = pay + task.getPayment();
                    }
                    continue;
                }
                if(TimeUtils.WithinTwoWeek(task.getLaunchtime())){
                    launch_w2++;
                    if(task.getProgress()>=4) {
                        pay = pay + task.getPayment();
                    }
                    continue;
                }
                if(TimeUtils.WithinThreeWeek(task.getLaunchtime())){
                    launch_w3++;
                    if(task.getProgress()>=4) {
                        pay = pay + task.getPayment();
                    }
                    continue;
                }
                if(TimeUtils.WithinFourWeek(task.getLaunchtime())){
                    launch_w4++;
                    if(task.getProgress()>=4) {
                        pay = pay + task.getPayment();
                    }
                }
            }
        }
        taskNum[0] = launch_w1;
        taskNum[1] = launch_w2;
        taskNum[2] = launch_w3;
        taskNum[3] = launch_w4;
        payment[1] = pay;
    }


    //TODO:数据库调用：调出当前用户所接收的所有任务

    private void getReceiveAndEarn(){

        //List<task>taskList = DataSupport.where("helperName = ?",user.getMyName()).find(task.class);

        List<task>taskList = new ArrayList<>();

        try{

            con = DbUtils.getConn();
            Statement st = con.createStatement();

            rs= st.executeQuery("SELECT * FROM `task` WHERE `helperName` = '"+user.getMyName()+"'");

            List<task> sampleList = new ArrayList<>(); //清空taskList

            List list = DbUtils.populate(rs,task.class);
            for(int i = 0; i < list.size(); i++){
                sampleList.add((task)list.get(i));
            }

            taskList = sampleList;

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }


        if(!taskList.isEmpty()){
            for(task task:taskList){
                if(TimeUtils.WithinOneWeek(task.getLaunchtime())){
                    receive_w1++;
                    if(task.getProgress()>=4) {
                        earn = earn + task.getPayment();
                    }
                    continue;
                }
                if(TimeUtils.WithinTwoWeek(task.getLaunchtime())){
                    receive_w2++;
                    if(task.getProgress()>=4) {
                        earn = earn + task.getPayment();
                    }
                    continue;
                }
                if(TimeUtils.WithinThreeWeek(task.getLaunchtime())){
                    receive_w3++;
                    if(task.getProgress()>=4) {
                        earn = earn + task.getPayment();
                    }
                    continue;
                }
                if(TimeUtils.WithinFourWeek(task.getLaunchtime())){
                    receive_w4++;
                    if(task.getProgress()>=4) {
                        earn = earn + task.getPayment();
                    }
                }
            }
        }

        score[0] = receive_w1;
        score[1] = receive_w2;
        score[2] = receive_w3;
        score[3] = receive_w4;
        payment[0] = earn;
    }


}
