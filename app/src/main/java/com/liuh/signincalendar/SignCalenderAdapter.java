package com.liuh.signincalendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * 用户签到日历的适配器
 */
public class SignCalenderAdapter extends RecyclerView.Adapter<SignCalenderAdapter.MyViewHolder> {
    private Context context;
    private List<UserBookInData> userBookInDataList;
    private MyOnItemClickListener listener;

    public SignCalenderAdapter(Context context) {
        this.context = context;
    }

    public void setUserBookInDataList(List<UserBookInData> userBookInDataList) {
        this.userBookInDataList = userBookInDataList;
    }

    public void setListener(MyOnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_userbookin, parent, false), listener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        if (userBookInDataList != null) {
            boolean isBookined = userBookInDataList.get(position).isBookined();
            boolean isToday = userBookInDataList.get(position).isToday();
            boolean isTodayInOtherMonth = userBookInDataList.get(position).isTodayInOtherMonth();
            String data = userBookInDataList.get(position).getSigntime();
            if (data == null) {
                holder.user_bookin_data_rl.setVisibility(View.GONE);
                holder.user_bookin_data_today.setVisibility(View.GONE);
            } else {
                if (isTodayInOtherMonth) {
                    //为当前选中项
                    if (isBookined) {
                        //已签到
                        holder.user_bookin_data_rl.setBackgroundDrawable(context
                                .getResources().getDrawable(R.drawable.cal_clicked_signed_bg));
                        if (isToday) {
                            holder.user_bookin_data_today.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //未签到
                        if (isToday) {
                            holder.user_bookin_data_today.setVisibility(View.VISIBLE);
                            holder.user_bookin_data_rl.setBackgroundDrawable(context
                                    .getResources().getDrawable(R.drawable.cal_clicked_unsigned_bg));
                        } else {
                            holder.user_bookin_data_rl.setBackgroundDrawable(context
                                    .getResources().getDrawable(R.drawable.cal_clicked_unsigned_bg));
                        }
                    }
                } else {
                    //非当前选中项
                    if (isBookined) {
                        //已签到
                        holder.user_bookin_data_rl.setBackgroundDrawable(context
                                .getResources().getDrawable(R.drawable.cal_signed_bg));
                        if (isToday) {
                            holder.user_bookin_data_today.setVisibility(View.VISIBLE);
                        }
                    } else {
                        //未签到
                        if (isToday) {
                            holder.user_bookin_data_today.setVisibility(View.VISIBLE);
                            holder.user_bookin_data_rl.setBackgroundDrawable(context
                                    .getResources().getDrawable(R.drawable.cal_today_unsigned_bg));
                        } else {
                            holder.user_bookin_data_rl.setBackgroundDrawable(context
                                    .getResources().getDrawable(R.drawable.cal_unsigned_bg));
                        }
                    }

                }

                holder.bookinData.setText(data);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (userBookInDataList != null) {
            return userBookInDataList.size();
        }
        return 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView user_bookin_data_today;
        RelativeLayout user_bookin_data_rl;
        TextView bookinData;

        MyOnItemClickListener listener;

        public MyViewHolder(View itemView, MyOnItemClickListener listener) {
            super(itemView);
            this.listener = listener;
            user_bookin_data_today = (TextView) itemView.findViewById(R.id.user_bookin_data_today);
            user_bookin_data_rl = (RelativeLayout) itemView.findViewById(R.id.user_bookin_data_rl);
            user_bookin_data_rl.setOnClickListener(this);
            bookinData = (TextView) itemView.findViewById(R.id.user_bookin_data_text);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(view, getAdapterPosition());
        }
    }
}
