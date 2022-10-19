package com.yyds.bitmapcompress;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yyds.bitmapcompress.util.Constant;
import com.yyds.bitmapcompress.util.SharedPreferenceUtil;
import com.yyds.log.util.LogUtils;

public class SettingActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButtonQuality,radioButtonProportion,radioButtonBlend;
    SeekBar seekBarQuality,seekBarProportion;
    TextView tvQuality,tvProportion;
    private int mQuality;
    private int mProportion;
    private int mMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        clickEvent();
    }

    private void clickEvent() {
        radioGroup.setOnCheckedChangeListener(new ModeRadioButtonListener());
        seekBarQuality.setOnSeekBarChangeListener(new QualitySeekBarListener());
        seekBarProportion.setOnSeekBarChangeListener(new ProportionSeekBarListener());
    }

    private void initView() {
        radioGroup = findViewById(R.id.radioGroup);
        seekBarQuality = findViewById(R.id.seekbar_quality);
        seekBarProportion = findViewById(R.id.seekbar_proportion);

        radioButtonQuality = findViewById(R.id.radio_quality);
        radioButtonProportion = findViewById(R.id.radio_proportion);
        radioButtonBlend = findViewById(R.id.radio_blend);

        tvQuality = findViewById(R.id.tv_quality);
        tvProportion = findViewById(R.id.tv_proportion);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMode = SharedPreferenceUtil.getInstance().getInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_MODE);
        int quality = SharedPreferenceUtil.getInstance().getInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_QUALITY);
        int proportion = SharedPreferenceUtil.getInstance().getInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_MAX_PROPORTION);
        seekBarQuality.setProgress(quality);
        seekBarProportion.setProgress(proportion);
        tvQuality.setText(quality+"");
        tvProportion.setText(proportion+"");
        switch (mMode) {
            case Constant.MODE_QUALITY:
                radioButtonQuality.setChecked(true);
                break;
            case Constant.MODE_PROPORTION:
                radioButtonProportion.setChecked(true);
                break;
            case Constant.MODE_BLEND:
                radioButtonBlend.setChecked(true);
                break;
        }
    }

    private class ModeRadioButtonListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton r = (RadioButton) findViewById(checkedId);
            LogUtils.v(r.getText().toString());
            switch (r.getText().toString()) {
                case "质量压缩":
                    mMode = Constant.MODE_QUALITY;
                    break;
                case "等比例压缩":
                    mMode = Constant.MODE_PROPORTION;
                    break;
                case "混合压缩":
                    mMode = Constant.MODE_BLEND;
                    break;
                default:
                    break;
            }
        }
    }

    private class QualitySeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mQuality = progress;
            tvQuality.setText(progress+"");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    private class ProportionSeekBarListener implements SeekBar.OnSeekBarChangeListener {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            mProportion = progress;
            tvProportion.setText(progress+"");
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferenceUtil.getInstance().putInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_MODE,mMode);
        SharedPreferenceUtil.getInstance().putInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_QUALITY,mQuality);
        SharedPreferenceUtil.getInstance().putInt(SharedPreferenceUtil.SP_CONFIG, SharedPreferenceUtil.KEY_MAX_PROPORTION,mProportion);
    }
}