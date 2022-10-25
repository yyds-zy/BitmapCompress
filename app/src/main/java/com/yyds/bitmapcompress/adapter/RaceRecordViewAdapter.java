package com.yyds.bitmapcompress.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.yyds.bitmapcompress.R;
import com.yyds.bitmapcompress.bean.RaceDataBean;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by 阿飞の小蝴蝶 on 2022/10/24
 * Describe:
 */
public class RaceRecordViewAdapter extends RecyclerView.Adapter<RaceRecordViewAdapter.ViewHolder> {

    private RaceDataBean mRaceData;

    public RaceRecordViewAdapter(RaceDataBean raceDataBean){
        mRaceData =  raceDataBean;
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
        RaceDataBean.RaceInfo raceInfoList = mRaceData.getRaceInfoList().get(position);
        holder.textViewDate.setText(stampToDate(raceInfoList.getDate()));
        holder.textViewLeftCountry.setText(raceInfoList.getLeftCountry());
        holder.textViewRightCountry.setText(raceInfoList.getRightCountry());
        holder.textViewWinner.setText(raceInfoList.getRace());
        holder.textViewRaceResult.setText(raceInfoList.getResult());
        if (position%2!=0){
            holder.relativeLayout.setBackgroundResource(R.drawable.guest_record_list_item_bg);
        } else {
            holder.relativeLayout.setBackgroundResource(R.drawable.guest_record_list_item_bg2);
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout relativeLayout;
        TextView textViewDate,textViewLeftCountry,textViewRightCountry,textViewWinner,textViewRaceResult;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            relativeLayout = itemView.findViewById(R.id.rel_bg);
            textViewDate = itemView.findViewById(R.id.tv_race_date);
            textViewLeftCountry = itemView.findViewById(R.id.tv_left_country);
            textViewRightCountry = itemView.findViewById(R.id.tv_right_country);
            textViewWinner = itemView.findViewById(R.id.tv_country_winner);
            textViewRaceResult = itemView.findViewById(R.id.tv_race_result);
        }
    }

    public static String stampToDate(long time) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm");
        Date date = new Date(time);
        res = simpleDateFormat.format(date);
        return res;
    }
}
