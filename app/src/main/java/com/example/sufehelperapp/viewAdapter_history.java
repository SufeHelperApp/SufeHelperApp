package com.example.sufehelperapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class viewAdapter_history extends PagerAdapter {

    public List<View> list_view2;
    private List<String> list_Title2; //tab名的列表
    private int[] tabImg2;
    private Context context2;

    public viewAdapter_history(MyActivity_Historical_Task context, List<View> list_view, List<String> list_Title, int[] tabImg) {
        this.list_view2 = list_view;
        this.list_Title2 = list_Title;
        this.tabImg2 = tabImg;
        this.context2 = context;
    }

    @Override
    public int getCount() {
        return list_view2.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(list_view2.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(list_view2.get(position), 0);
        return list_view2.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title2.get(position % list_Title2.size());
    }
}