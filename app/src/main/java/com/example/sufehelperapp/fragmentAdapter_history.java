package com.example.sufehelperapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 这里使用多个fragment进行切换，每个fragment都有自己的独立的代码
 */
public class fragmentAdapter_history extends FragmentPagerAdapter {

    private List<Fragment> list_fragment2;   //fragment列表
    private List<String> list_Title2;        //tab名的列表

    public fragmentAdapter_history(FragmentManager fm,List<Fragment> list_fragment,List<String> list_Title) {
        super(fm);
        this.list_fragment2 = list_fragment;
        this.list_Title2 = list_Title;
    }

    @Override
    public Fragment getItem(int i) {
        return list_fragment2.get(i);
    }

    @Override
    public int getCount() {
        return list_fragment2.size();
    }

    /**
     * 此方法是给tablayout中的tab赋值的，就是显示名称
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {
        return list_Title2.get(position % list_Title2.size());
    }
}

