package com.example.geeknews.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;

import com.example.geeknews.R;
import com.example.geeknews.bean.NightEvent;
import com.example.geeknews.ui.base.BaseFragment;
import com.example.geeknews.utils.Constants;
import com.example.geeknews.utils.SPUtils;

import org.greenrobot.eventbus.EventBus;

public class SettingFragment extends BaseFragment {
    private AppCompatCheckBox mSettingNightCb;



    @Override
    protected void initData() {

    }

    private static final String TAG = "SettingFragment";
    @Override
    protected void initView(View root) {

        mSettingNightCb = (AppCompatCheckBox) root.findViewById(R.id.cb_setting_night);

        final boolean aBoolean = SPUtils.getInstance().getBoolean(Constants.IS_NIGHT);

        mSettingNightCb.setChecked(aBoolean);


        mSettingNightCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onCheckedChanged: "+aBoolean);
                SPUtils.getInstance().put(Constants.IS_NIGHT,!aBoolean);;

                NightEvent nightEvent = new NightEvent();
                nightEvent.setNight(!aBoolean);
                EventBus.getDefault().post(nightEvent);
            }
        });



    }

    @Override
    protected int getLayout() {
        return R.layout.setting_fragment
                ;
    }
}
