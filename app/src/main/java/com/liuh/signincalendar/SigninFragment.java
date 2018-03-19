package com.liuh.signincalendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Date: 2018/3/19 11:51
 * Description:
 */

public class SigninFragment extends Fragment {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    Bundle bundle;

    private int sysCurDay;
    private int sysCurMonth;
    private int sysCurYear;
    private int mPageNumber;

    private Calendar mCalendar;

    private SignCalenderAdapter calenderAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        if (bundle != null) {

            sysCurDay = bundle.getInt("sysCurDay");
            sysCurMonth = bundle.getInt("sysCurMonth");
            sysCurYear = bundle.getInt("sysCurYear");
            mPageNumber = bundle.getInt("position");

            Log.e("-------------", "sysCurDay:" + sysCurDay +
                    "sysCurMonth:" + sysCurMonth +
                    "sysCurYear:" + sysCurYear +
                    "position:" + mPageNumber);

            mCalendar = CalendarUtil.getSelectCalendar(mPageNumber);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.fragment_signin, null);
        ButterKnife.bind(this, view);
        return view;
    }


}
