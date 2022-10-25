package com.yyds.bitmapcompress;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.yyds.bitmapcompress.adapter.RaceRecordViewAdapter;
import com.yyds.bitmapcompress.bean.RaceDataBean;

import java.util.ArrayList;
import java.util.List;

public class GuestActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView mRecycleView;
    RaceRecordViewAdapter adapter;
    TextView textViewGuestCount,textViewRanking;
    RaceDataBean raceDataBean;
    TextView upperPagerTv,nextPagerTv,leftPagerNumberTv,rightPagerNumberTv;
    RelativeLayout leftPagerNumberBtn,rightPagerNumberBtn;
    int page = 1;
    int number = 5;
    int index = 1;
    int leftNumber = 1;
    int rightNumber = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        init();
        loadData();
    }

    private void loadData() {
        raceDataBean = new RaceDataBean();
        raceDataBean.setRaceCount(12);
        raceDataBean.setRanking(0);
        List<RaceDataBean.RaceInfo> raceInfoList = new ArrayList<>();
        raceInfoList.clear();

        int size = page * number + 1;
        for (int i = index; i < size; i++) {
            RaceDataBean.RaceInfo raceInfo = new RaceDataBean.RaceInfo();
            raceInfo.setDate(System.currentTimeMillis());
            raceInfo.setLeftCountry("德国");
            raceInfo.setRightCountry("乌拉圭");
            raceInfo.setRace("德国胜" + i);
            raceInfo.setResult("未出结果");
            raceInfoList.add(raceInfo);
        }
        raceDataBean.setRaceInfoList(raceInfoList);

        textViewGuestCount.setText(raceDataBean.getRaceCount()+"");
        if (raceDataBean.getRanking() == 0) {
            textViewRanking.setText("暂无排名");
        } else {
            textViewRanking.setText(raceDataBean.getRanking()+"");
        }
        adapter = new RaceRecordViewAdapter(raceDataBean);
        mRecycleView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void init() {
        leftPagerNumberTv = findViewById(R.id.tv_left_page_count);
        rightPagerNumberTv = findViewById(R.id.tv_right_page_count);
        leftPagerNumberTv.setText(leftNumber+"");
        rightPagerNumberTv.setText(rightNumber+"");

        leftPagerNumberBtn = findViewById(R.id.rel_left_page_count);
        rightPagerNumberBtn = findViewById(R.id.rel_right_page_count);

        leftPagerNumberBtn.setSelected(true);

        upperPagerTv = findViewById(R.id.tv_upper_page);
        nextPagerTv = findViewById(R.id.tv_next_page);

        leftPagerNumberBtn.setOnClickListener(this);
        rightPagerNumberBtn.setOnClickListener(this);
        upperPagerTv.setOnClickListener(this);
        nextPagerTv.setOnClickListener(this);

        textViewGuestCount = findViewById(R.id.tv_guest_count);
        textViewRanking = findViewById(R.id.tv_ranking);
        mRecycleView = findViewById(R.id.rv_guest_list);
        CustomLinearLayoutManager layoutManager = new CustomLinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rel_left_page_count:
                if (!leftPagerNumberBtn.isSelected()) {
                    leftPagerNumberBtn.setSelected(true);
                    rightPagerNumberBtn.setSelected(false);
                }
                int parseInt = Integer.parseInt(leftPagerNumberTv.getText().toString());
                page = parseInt;
                int size = page * number + 1;
                index = size - number;
                loadData();
                break;
            case R.id.rel_right_page_count:
                if (leftPagerNumberBtn.isSelected()) {
                    leftPagerNumberBtn.setSelected(false);
                    rightPagerNumberBtn.setSelected(true);
                }
                int parseInt2 = Integer.parseInt(rightPagerNumberTv.getText().toString());
                page = parseInt2;
                int size2 = page * number + 1;
                index = size2 - number;
                loadData();
                break;
            case R.id.tv_upper_page:
                if (Integer.parseInt(leftPagerNumberTv.getText().toString()) == 1) return;
                if (rightPagerNumberBtn.isSelected()) {
                    clickUpperPager(Integer.parseInt(rightPagerNumberTv.getText().toString()));
                } else {
                    clickUpperPager(Integer.parseInt(leftPagerNumberTv.getText().toString()));
                }
                break;
            case R.id.tv_next_page:
                if (leftPagerNumberBtn.isSelected()) {
                    clickNextPager(Integer.parseInt(leftPagerNumberTv.getText().toString()));
                } else {
                    clickNextPager(Integer.parseInt(rightPagerNumberTv.getText().toString()));
                }
                break;
            default:
                break;
        }
    }

    private void clickNextPager(int choosePager){
        choosePager = choosePager + 2;
        page = choosePager;
        int size = page * number + 1;
        index = size - number;

        loadData();

        leftNumber = leftNumber + 2;
        rightNumber = rightNumber + 2;
        leftPagerNumberTv.setText(leftNumber+"");
        rightPagerNumberTv.setText(rightNumber+"");
    }

    private void clickUpperPager(int choosePager) {
        choosePager = choosePager - 2;
        page = choosePager;
        int size = page * number + 1;
        index = size - number;

        loadData();

        leftNumber = leftNumber - 2;
        rightNumber = rightNumber - 2;
        leftPagerNumberTv.setText(leftNumber+"");
        rightPagerNumberTv.setText(rightNumber+"");
    }


}