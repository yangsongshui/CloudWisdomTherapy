package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Aftermarket;

/**
 * Created by Administrator on 2017/2/9.
 */
public class AftermarketAdapter extends RecyclerView.Adapter<AftermarketAdapter.ViewHoader> {
    List<Aftermarket> mList;
    Context context;

    public AftermarketAdapter(List<Aftermarket> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aftermarket_item, parent, false);
        return new ViewHoader(view);
    }

    @Override
    public void onBindViewHolder(ViewHoader holder, int position) {
        Aftermarket aftermarket = mList.get(position);
        holder.aftermarket_name_tv.setText(aftermarket.getName());
        MyApplication.newInstance().getmImageLoader().get(aftermarket.getPicture(), holder.aftermarket_pic_iv);
        holder.aftermarket_num_tv.setText(aftermarket.getNum());
        holder.aftermarket_purchase_price_tv.setText(aftermarket.getPurchase_price());
        holder.aftermarket_price_tv.setText(aftermarket.getPrice());
        holder.aftermarket_type_tv.setText(aftermarket.getType());
        holder.aftermarket_total_tv.setText(aftermarket.getTotal());
        holder.aftermarket_standard_tv.setText(aftermarket.getStandard());
        holder.aftermarket_reimburse_tv.setText(aftermarket.getReimburse());
        if (aftermarket.getState() == 5) {
            holder.aftermarket_state_tv.setText(context.getResources().getString(R.string.aftermarket_state1));
        } else if (aftermarket.getState() == 6) {
            holder.aftermarket_state_tv.setText(context.getResources().getString(R.string.aftermarket_state2));
        } else if (aftermarket.getState() == 7) {
            holder.aftermarket_state_tv.setText(context.getResources().getString(R.string.aftermarket_state3));
        } else if (aftermarket.getState() == 8) {

        }

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {


        private ImageView aftermarket_pic_iv;
        private TextView aftermarket_name_tv, aftermarket_purchase_price_tv, aftermarket_price_tv,
                aftermarket_type_tv, aftermarket_standard_tv, aftermarket_num_tv, aftermarket_total_tv,
                aftermarket_rmb_tv, aftermarket_state_tv, aftermarket_reimburse_tv;


        public ViewHoader(View itemView) {
            super(itemView);

            aftermarket_pic_iv = (ImageView) itemView.findViewById(R.id.aftermarket_pic_iv);
            aftermarket_name_tv = (TextView) itemView.findViewById(R.id.aftermarket_name_tv);
            aftermarket_purchase_price_tv = (TextView) itemView.findViewById(R.id.aftermarket_purchase_price_tv);
            aftermarket_rmb_tv = (TextView) itemView.findViewById(R.id.aftermarket_rmb_tv);
            aftermarket_purchase_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            aftermarket_rmb_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            aftermarket_price_tv = (TextView) itemView.findViewById(R.id.aftermarket_price_tv);
            aftermarket_type_tv = (TextView) itemView.findViewById(R.id.aftermarket_type_tv);
            aftermarket_standard_tv = (TextView) itemView.findViewById(R.id.aftermarket_standard_tv);
            aftermarket_total_tv = (TextView) itemView.findViewById(R.id.aftermarket_total_tv);
            aftermarket_num_tv = (TextView) itemView.findViewById(R.id.aftermarket_num_tv);
            aftermarket_state_tv = (TextView) itemView.findViewById(R.id.aftermarket_state_tv);
            aftermarket_reimburse_tv = (TextView) itemView.findViewById(R.id.aftermarket_reimburse_tv);


        }
    }

    public void setmList(List<Aftermarket> list) {

        this.mList = list;
        this.notifyDataSetChanged();
    }
}
