package com.liuh.signincalendar;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2018/3/19 11:51
 * Description:日历里面的fagment
 */

public class SigninFragment extends Fragment implements MyOnItemClickListener {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    Bundle bundle;

    private int sysCurDay;
    private int sysCurMonth;
    private int sysCurYear;
    private int mPageNumber;

    public int year, month, day;

    private String date_today_chinese;

    private List<UserBookInData> bookInData;

    private Calendar mCalendar;

    private SignCalenderAdapter calenderAdapter;

    private Context mContext;

    private boolean isPrepared = false;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {

            //如果UserBookInActivity中的day已发生改变并且日期在本月份也有
            //那么改变数据集合，刷新
            if (isPrepared && bookInData != null && bookInData.size() > 0) {

                for (int i = 0; i < bookInData.size(); i++) {
                    if (bookInData.get(i).getSigntime() != null) {
                        if (MainActivity.day == Integer.parseInt(bookInData.get(i).getSigntime())) {
                            if (year == sysCurYear && month == sysCurMonth && MainActivity.day == sysCurDay) {
                                bookInData.get(i).setIsToday(true);
                            } else {
                                bookInData.get(i).setIsToday(false);
                                bookInData.get(i).setIsTodayInOtherMonth(true);
                            }
                        } else {
                            bookInData.get(i).setIsTodayInOtherMonth(false);
                        }
                    }
                }
                calenderAdapter.notifyDataSetChanged();
            }
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {
            sysCurDay = bundle.getInt("sysCurDay");
            sysCurMonth = bundle.getInt("sysCurMonth");
            sysCurYear = bundle.getInt("sysCurYear");
            mPageNumber = bundle.getInt("position");

            mCalendar = CalendarUtil.getSelectCalendar(mPageNumber);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_signin, null);
        ButterKnife.bind(this, view);
        isPrepared = true;
        year = mCalendar.get(Calendar.YEAR);
        month = mCalendar.get(Calendar.MONTH) + 1;
        day = MainActivity.day;
        Log.e("------------", "year : " + year + "month : " + month + "day : " + day);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mContext = getActivity();
        if (mContext != null) {
//            System.out.println(".......context不为null");
            calenderAdapter = new SignCalenderAdapter(mContext);
            calenderAdapter.setListener(this);
            rvList.setLayoutManager(new GridLayoutManager(mContext, 7));
            rvList.setAdapter(calenderAdapter);
        } else {
//            System.out.println(".......context为null");
        }
        initData2();
    }

    //测试数据
    private void initData2() {
        String date1 = year + "-" + month;
        String date = date1 + "-01";
        Log.e("--------", "date : " + date);
        //获取月份第一天是星期几，以确定第一行前面空几个位置
        int emptyWeekNum = DateUtil.getWeek(date);
        Log.e("--------", "month : " + month + "emptyWeekNum : " + emptyWeekNum);
        date_today_chinese = year + "年" + month + "月" + day + "日";
        Log.e("--------", "date_today_chinese : " + date_today_chinese);
        //1表示星期日，以此后推
        bookInData = new ArrayList<UserBookInData>();
        //添加空日期
        for (int i = 0; i < emptyWeekNum - 1; i++) {
            UserBookInData userBookInData = new UserBookInData();
            userBookInData.setSigntime(null);
            userBookInData.setIsToday(false);
            bookInData.add(userBookInData);
        }
        //获取某个月的天数
//        System.out.print("................date1:" + date1);
        int monthDayNum = DateUtil.getMonthDayNum(date1);
        for (int i = 0; i < monthDayNum; i++) {
            String dataNum = i + 1 + "";
            UserBookInData userBookInData = new UserBookInData();
            userBookInData.setSigntime(dataNum);
            //服务器请求数据
//            for (int j = 0; j < bookInDataFromServer.size(); j++) {
//                String strServer = bookInDataFromServer.get(j).getSigntime();
//                int signedDateNum = Integer.parseInt(strServer.substring(strServer.lastIndexOf("-") + 1));
//                if (i + 1 == signedDateNum) {
//                    userBookInData.setIsBookined(true);
//                }
//            }

            if ((i == sysCurDay - 1) && (date1.equals(sysCurYear + "-" + sysCurMonth))) {
                userBookInData.setIsToday(true);
            } else if (i == day - 1) {
                userBookInData.setIsTodayInOtherMonth(true);
            }
            bookInData.add(userBookInData);
        }
        //模拟数据
        bookInData.get(month).setIsBookined(true);
//        p("...........bookInData.size():" + bookInData.size());
        calenderAdapter.setUserBookInDataList(bookInData);
        calenderAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
//        Toast.makeText(mContext, "view:" + view + ",position:" + position + "被点击了", Toast.LENGTH_SHORT).show();
        day = Integer.parseInt(bookInData.get(position).getSigntime());
        MainActivity.day = day;
        date_today_chinese = year + "年" + month + "月" + day + "日";
        ((MainActivity) getActivity()).changeTitle(date_today_chinese);
        for (int i = 0; i < bookInData.size(); i++) {
            if (i == position) {
                bookInData.get(i).setIsTodayInOtherMonth(true);
            } else {
                bookInData.get(i).setIsTodayInOtherMonth(false);
            }
        }
        calenderAdapter.setUserBookInDataList(bookInData);
        calenderAdapter.notifyDataSetChanged();
    }
}
