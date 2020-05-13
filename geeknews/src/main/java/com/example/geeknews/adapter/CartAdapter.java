package com.example.geeknews.adapter;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.geeknews.R;
import com.example.geeknews.bean.CartBean;
import com.example.geeknews.bean.CartEvent;

import org.greenrobot.eventbus.EventBus;


public class CartAdapter extends BaseAdapter<CartBean.DataBean.CartListBean> {

    public CartAdapter(Context context) {
        super(context);


    }

    @Override
    protected int getItemLayout() {
        return R.layout.cart_item;
    }

    @Override
    protected void bindData(BaseViewHolder baseViewHolder, final CartBean.DataBean.CartListBean cartListBean) {

        ImageView img = (ImageView) baseViewHolder.getViewById(R.id.img);
        CheckBox check = (CheckBox) baseViewHolder.getViewById(R.id.checkBox);
        TextView title = (TextView) baseViewHolder.getViewById(R.id.title);
        TextView price = (TextView) baseViewHolder.getViewById(R.id.price);
        TextView count = (TextView) baseViewHolder.getViewById(R.id.count);

        Glide.with(context).load(cartListBean.getList_pic_url()).into(img);
        title.setText(cartListBean.getGoods_name());
        price.setText(cartListBean.getRetail_price() + "");
        count.setText("x " + cartListBean.getNumber() + "");


        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                cartListBean.setChecked(isChecked);

                // 计算价格  通知给UI

                computePrice();

            }
        });
        check.setChecked(cartListBean.isChecked());

    }

    private void computePrice() {
        int price = 0;
        int number=0;
        for (int i = 0; i < datalist.size(); i++) {
            CartBean.DataBean.CartListBean cartListBean = datalist.get(i);
            //如果该商品被选中，则加入计算
            if (cartListBean.isChecked()) {

                price = price +(cartListBean.getNumber() * cartListBean.getRetail_price());
                number =number+cartListBean.getNumber();
            }
        }

        //price 最终 总价格

        CartEvent cartEvent = new CartEvent();
        cartEvent.number = number;
        cartEvent.price = price;
        EventBus.getDefault().post(cartEvent);
    }
}
