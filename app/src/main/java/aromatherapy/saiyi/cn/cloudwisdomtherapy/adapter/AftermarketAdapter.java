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
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullListView;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.NestFullViewHolder;

/**
 * Created by Administrator on 2017/2/9.
 */
public class AftermarketAdapter extends NestFullListViewAdapter<Indent> {

    List<Indent> mList;
    Context context;
    OnItemClickListener onItemClickListener;

    public AftermarketAdapter(int mItemLayoutId, List<Indent> mDatas, Context context) {
        super(mItemLayoutId, mDatas);
        mList = mDatas;
        this.context = context;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBind(final int pos, Indent indent, NestFullViewHolder holder) {
        TextView aftermarket_state_tv = holder.getView(R.id.aftermarket_state_tv);

        holder.setText(R.id.aftermarket_total_tv, indent.getTotal());
        holder.setText(R.id.aftermarket_reimburse_tv, indent.getReimburse());
        NestFullListView indent_item_ll = holder.getView(R.id.cstFullShowListView2);
        indent_item_ll.setOnItemClickListener(new NestFullListView.OnItemClickListener() {
            @Override
            public void onItemClick(NestFullListView parent, View view, int position) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(parent, pos);
            }
        });
        if (indent.getState() == 4) {
            //退货中
            aftermarket_state_tv.setText(context.getString(R.string.aftermarket_state1));
            if (indent.getRelogisticsNo() != null && indent.getRelogisticsNo().trim().length() > 0) {
                aftermarket_state_tv.setText(context.getString(R.string.aftermarket_state4));
            } else {
                aftermarket_state_tv.setText(context.getString(R.string.aftermarket_state1));

            }
        } else if (indent.getState() == 5) {
            //已确认收货
            aftermarket_state_tv.setText(context.getString(R.string.aftermarket_state3));
        } else if (indent.getState() == 6) {
            //换货
            //aftermarket_state_tv.setText(context.getString(R.string.aftermarket_state1));

        } else if (indent.getState() == 999) {
            //等待客户确认
            aftermarket_state_tv.setText(context.getString(R.string.aftermarket_state2));

        }

        indent_item_ll.setAdapter(new NestFullListViewAdapter<Mall>(R.layout.indent_mall_item, indent.getMalls()) {
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
