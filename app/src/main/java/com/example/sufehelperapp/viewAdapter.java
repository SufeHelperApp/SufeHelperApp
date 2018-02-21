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

public class viewAdapter extends PagerAdapter {

    public List<View> list_view;
    private List<String> list_Title; //tab名的列表
    private int[] tabImg;
    private Context context;

    public viewAdapter(MyActivity_Mytask context, List<View> list_view, List<String> list_Title, int[] tabImg) {
        this.list_view = list_view;
        this.list_Title = list_Title;
        this.tabImg = tabImg;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list_view.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView(list_view.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ((ViewPager) container).addView(list_view.get(position), 0);
        return list_view.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title.get(position % list_Title.size());
    }
}