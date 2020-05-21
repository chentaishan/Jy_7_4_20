package com.example.geeknews.ui.fragment;

import android.support.v7.widget.AppCompatCheckBox;
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

    @Override
    protected void initView(View itemView) {

        //获取默认的夜间模式的状态
        final boolean status = SPUtils.getInstance().getBoolean(Constants.IS_NIGHT, false);

        mSettingNightCb = (AppCompatCheckBox) itemView.findViewById(R.id.cb_setting_night);

        mSettingNightCb.setChecked(status);

//        mSettingNightCb.setOnCheckedChangeListener();
        mSettingNightCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final boolean isNight = SPUtils.getInstance().getBoolean(Constants.IS_NIGHT, false);

                // 状态的保存  文件保存 sharedPrefrences(工具类)
                SPUtils.getInstance().put(Constants.IS_NIGHT,!isNight);


                //发送eventbus ，通过UI切换夜间模式
                NightEvent nightEvent = new NightEvent();
                nightEvent.setNight(!isNight);
                EventBus.getDefault().post(nightEvent);

            }
        });

    }

    @Override
    protected int getLayout() {
        return R.layout.setting_fragment;
    }
}
