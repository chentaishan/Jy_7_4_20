package com.example.geeknews.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.geeknews.R;
import com.example.geeknews.adapter.CartAdapter;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.CartEvent;
import com.example.geeknews.contract.CartContract;
import com.example.geeknews.presenter.CartPresenter;
import com.example.geeknews.ui.base.BaseMvpFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 微信----购物车模块
 */
public class WxFragment extends BaseMvpFragment<CartPresenter> implements CartContract.View {
    private RecyclerView mRecycler;
    private CheckBox mCheckbox;
    private TextView mCount;
    private TextView mPriceTotal;
    private CartAdapter cartAdapter;

    @Override
    protected void initData() {

        EventBus.getDefault().register(this);

        presenter.getCartListData();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateUI(CartEvent cartEvent){


        mPriceTotal.setText("总价："+cartEvent.price+"元");
        mCount.setText("全部：（"+cartEvent.number+"）");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);


    }

    @Override
    protected void initViewFragment(View itemView) {
        mRecycler = (RecyclerView) itemView.findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mCheckbox = (CheckBox) itemView.findViewById(R.id.checkbox);
        mCount = (TextView) itemView.findViewById(R.id.count);
        mPriceTotal = (TextView) itemView.findViewById(R.id.total_price);

        cartAdapter = new CartAdapter(getActivity());

        mRecycler.setAdapter(cartAdapter);
    }

    @Override
    protected CartPresenter initPresent() {
        return new CartPresenter();
    }

    @Override
    protected int getLayout() {
        return R.layout.wx_fragment;
    }

    @Override
    public void successUI(CartBean cartBean) {
        cartAdapter.addDataList(cartBean.getData().getCartList());
    }

    private static final String TAG = "WxFragment";
    @Override
    public void errorUI(String error) {

        Log.d(TAG, "errorUI: ");
    }
}
