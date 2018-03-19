package com.liuh.signincalendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 日历效果(签到日历)
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.usersignin_data_mviewpager)
    ViewPager mViewPager;

    private MyAdapter mAdapter;

    private Bundle bundle = new Bundle();

    private SigninFragment signinFragment;
    private int sysCurDay;
    private int sysCurMonth;
    private int sysCurYear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTimeData();


        mAdapter = new MyAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(500);

        initListener();
    }

    private void initTimeData() {
        Date now_Data = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");

        String date1 = sdf.format(now_Data);
        sysCurYear = Integer.parseInt(date1.substring(0, 4));

        sysCurMonth = Integer.parseInt(date1.substring(5));

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date_today = sdf2.format(now_Data);
        sysCurDay = DateUtil.getTodayPosition(date_today);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    class MyAdapter extends FragmentStatePagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            signinFragment = new SigninFragment();
            bundle.putInt("sysCurDay", sysCurDay);
            bundle.putInt("sysCurMonth", sysCurMonth);
            bundle.putInt("sysCurYear", sysCurYear);
            bundle.putInt("position", position);
            signinFragment.setArguments(bundle);
            return signinFragment;
        }

        @Override
        public int getCount() {
            return 1000;//使用户可以多次进行向左或向右的滑动
        }
    }

}
