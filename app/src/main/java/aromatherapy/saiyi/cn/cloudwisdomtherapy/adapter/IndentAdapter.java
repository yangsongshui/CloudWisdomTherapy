package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnViewClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullListView;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullViewHolder;

/**
 * Created by Administrator on 2017/2/9.
 */
public class IndentAdapter extends NestFullListViewAdapter<Indent> {
    List<Indent> mList;
    Context context;
    OnViewClickListener listener;
    OnItemClickListener onItemClickListener;

    public IndentAdapter(int mItemLayoutId, List<Indent> mDatas) {
        super(mItemLayoutId, mDatas);
        mList = mDatas;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setListener(OnViewClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onBind(final int pos, Indent indent, NestFullViewHolder holder) {
        TextView indent_item_confirm_tv = holder.getView(R.id.indent_item_confirm_tv);
        TextView indent_item_payment_tv = holder.getView(R.id.indent_item_payment_tv);
        TextView indent_item_logistics_tv = holder.getView(R.id.indent_item_logistics_tv);
        TextView indent_standard_tv = holder.getView(R.id.indent_standard_tv);
        holder.setText(R.id.indent_item_total_tv,indent.getTotal());
        NestFullListView indent_item_ll = holder.getView(R.id.cstFullShowListView2);
        indent_item_ll.setOnItemClickListener(new NestFullListView.OnItemClickListener() {
            @Override
            public void onItemClick(NestFullListView parent, View view, int position) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(parent, pos);
            }
        });
        indent_item_confirm_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null)
                    listener.OnViewClick(v, pos, 1);
            }
        });
        indent_item_payment_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnViewClick(v, pos, 2);
            }
        });
        indent_item_logistics_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnViewClick(v, pos, 3);
            }
        });
        if (indent.getState() == 0) {
            //待付款
            indent_item_confirm_tv.setVisibility(View.GONE);
            indent_item_payment_tv.setVisibility(View.VISIBLE);
            indent_item_logistics_tv.setVisibility(View.GONE);
            indent_standard_tv.setText("待付款");
        } else if (indent.getState() == 3) {
            //已确认收货
            indent_item_confirm_tv.setVisibility(View.GONE);
            indent_item_payment_tv.setVisibility(View.GONE);
            indent_item_logistics_tv.setVisibility(View.VISIBLE);
            indent_standard_tv.setText("已买到");
        } else if (indent.getState() == 2) {
            //待收货
            indent_item_confirm_tv.setVisibility(View.VISIBLE);
            indent_item_payment_tv.setVisibility(View.GONE);
            indent_item_logistics_tv.setVisibility(View.VISIBLE);
            indent_standard_tv.setText("待收货");
        } else if (indent.getState() == 1) {
            //待发货
            indent_item_confirm_tv.setVisibility(View.VISIBLE);
            indent_item_payment_tv.setVisibility(View.GONE);
            indent_item_logistics_tv.setVisibility(View.GONE);
            indent_standard_tv.setText("待发货");
        }

        ((NestFullListView) holder.getView(R.id.cstFullShowListView2)).setAdapter(new NestFullListViewAdapter<Mall>(R.layout.indent_mall_item, indent.getMalls()) {
            @Override
            public void onBind(int pos, Mall mall, NestFullViewHolder holder) {
                ImageView indent_item_pic_iv = holder.getView(R.id.indent_item_pic_iv);

                MyApplication.newInstance().getmImageLoader().load(mall.getPicture()).skipMemoryCache(true).into(indent_item_pic_iv);
                TextView intent_rmb_tv = holder.getView(R.id.intent_rmb_tv);
                TextView indent_item_purchase_price_tv = holder.getView(R.id.indent_item_purchase_price_tv);
                holder.getView(R.id.indent_item_num_tv);
                indent_item_purchase_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                intent_rmb_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                indent_item_purchase_price_tv.setText(mall.getPurchase_price());
                holder.setText(R.id.indent_item_name_tv, mall.getName());
                holder.setText(R.id.indent_item_price_tv, mall.getPrice());
                holder.setText(R.id.indent_item_type_tv, mall.getType());
                holder.setText(R.id.indent_item_standard_tv, mall.getStandard());
            }
        });
    }
}


