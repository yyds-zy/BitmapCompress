package com.yyds.bitmapcompress.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yyds.bitmapcompress.R;
import com.yyds.bitmapcompress.bean.RaceDataBean;

import java.util.List;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/24
 * Describe:
 */
public class RaceRecordViewAdapter extends RecyclerView.Adapter<RaceRecordViewAdapter.ViewHolder> {

    private List<RaceDataBean> mRaceDataList;

    public RaceRecordViewAdapter(List<RaceDataBean> raceDataBeans){
        mRaceDataList =  raceDataBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest_record_info,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RaceDataBean raceDataBean = mRaceDataList.get(position);
        //holder.textView.setText(raceDataBean);
    }

    @Override
    public int getItemCount() {
        return mRaceDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_race_date);
        }
    }
}
