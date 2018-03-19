package com.liuh.signincalendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

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

    @BindView(R.id.user_signin_data_cur_title)
    TextView data_cur_title;

    private MyAdapter mAdapter;

    private Bundle bundle = new Bundle();

    private SigninFragment signinFragment;
    private int sysCurDay;
    private int sysCurMonth;
    private int sysCurYear;

    public int month, year;

    public static int day;

    private String date_today_chinese;

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
        year = Integer.parseInt(date1.substring(0, 4));
        sysCurYear = year;

        month = Integer.parseInt(date1.substring(5));
        sysCurMonth = month;

        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        String date_today = sdf2.format(now_Data);
        int todayPosition = DateUtil.getTodayPosition(date_today);
        day = todayPosition;
        sysCurDay = todayPosition;
        date_today_chinese = year + "年" + month + "月" + day + "日";
        changeTitle(date_today_chinese);
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

    public void changeTitle(String bookInTitle) {
        data_cur_title.setText(bookInTitle);
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
