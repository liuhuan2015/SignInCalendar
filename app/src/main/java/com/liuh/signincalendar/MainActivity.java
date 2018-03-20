package com.liuh.signincalendar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 日历效果
 */
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.usersignin_data_mviewpager)
    ViewPager mViewPager;

    @BindView(R.id.user_signin_data_cur_title)
    TextView data_cur_title;

    private MyAdapter mAdapter;

    private SigninFragment signinFragment;
    private int sysCurDay;
    private int sysCurMonth;
    private int sysCurYear;

    public int month, year;

    public static int day;

    private String date_today_chinese;

    private Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mAdapter = new MyAdapter(getSupportFragmentManager());

        mViewPager.setAdapter(mAdapter);

        initTimeData();

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
        Log.e("----------", "date_today_chinese : " + date_today_chinese);
        changeTitle(date_today_chinese);

        mViewPager.setCurrentItem(500);
    }

    private void initListener() {
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCalendar = CalendarUtil.getSelectCalendar(position);
                year = mCalendar.get(Calendar.YEAR);
                month = mCalendar.get(Calendar.MONTH) + 1;
                int monthDayNum = DateUtil.getMonthDayNum(year + "-" + month);
                Log.e("-----------", "monthDayNum : " + monthDayNum);
                if (day > monthDayNum) {
                    day = monthDayNum;
                }
                changeTitle(year + "年" + month + "月" + day + "日");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void changeTitle(String bookInTitle) {
        data_cur_title.setText(bookInTitle);
    }

    @OnClick({R.id.user_signin_data_leftarrow, R.id.user_signin_data_rightarrow})
    void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.user_signin_data_leftarrow:
                //向左,月份减少
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
            case R.id.user_signin_data_rightarrow:
                //向右,月份增加
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                break;
        }
    }

    class MyAdapter extends FragmentPagerAdapter {
        private Bundle bundle;

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            signinFragment = new SigninFragment();
            bundle = new Bundle();
            bundle.putInt("sysCurDay", sysCurDay);
            bundle.putInt("sysCurMonth", sysCurMonth);
            bundle.putInt("sysCurYear", sysCurYear);
            bundle.putInt("position", position);
            Log.e("-------------", "position:" + position);
            signinFragment.setArguments(bundle);
            return signinFragment;
        }

        @Override
        public int getCount() {
            return 1000;//使用户可以多次进行向左或向右的滑动
        }
    }

}
