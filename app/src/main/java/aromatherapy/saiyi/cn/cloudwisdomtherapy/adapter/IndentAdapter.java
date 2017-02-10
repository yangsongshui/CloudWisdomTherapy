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
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;

/**
 * Created by Administrator on 2017/2/9.
 */
public class IndentAdapter extends RecyclerView.Adapter<IndentAdapter.ViewHoader> {
    List<Indent> mList;
    Context context;

    public IndentAdapter(List<Indent> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.indent_item, parent, false);
        return new ViewHoader(view);
    }

    @Override
    public void onBindViewHolder(ViewHoader holder, int position) {
        Indent indent = mList.get(position);
        holder.indent_item_name_tv.setText(indent.getName());
        MyApplication.newInstance().getmImageLoader().get(indent.getPicture(), holder.indent_item_pic_iv);
        holder.indent_item_num_tv.setText(indent.getNum());
        holder.indent_item_purchase_price_tv.setText(indent.getPurchase_price());
        holder.indent_item_price_tv.setText(indent.getPrice());
        holder.indent_item_type_tv.setText(indent.getType());
        holder.indent_item_total_tv.setText(indent.getTotal());
        holder.indent_item_standard_tv.setText(indent.getStandard());

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {


        private ImageView indent_item_pic_iv;
        private TextView indent_item_name_tv, indent_item_purchase_price_tv, indent_item_price_tv,
                indent_item_type_tv, indent_item_standard_tv, indent_item_num_tv, indent_item_total_tv,
                indent_item_payment_bt, indent_item_logistics_bt, indent_item_confirm_bt,intent_rmb_tv;


        public ViewHoader(View itemView) {
            super(itemView);

            indent_item_pic_iv = (ImageView) itemView.findViewById(R.id.indent_item_pic_iv);
            indent_item_name_tv = (TextView) itemView.findViewById(R.id.indent_item_name_tv);
            indent_item_purchase_price_tv = (TextView) itemView.findViewById(R.id.indent_item_purchase_price_tv);
            intent_rmb_tv = (TextView) itemView.findViewById(R.id.intent_rmb_tv);
            indent_item_purchase_price_tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            intent_rmb_tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            indent_item_price_tv = (TextView) itemView.findViewById(R.id.indent_item_price_tv);
            indent_item_type_tv = (TextView) itemView.findViewById(R.id.indent_item_type_tv);
            indent_item_standard_tv = (TextView) itemView.findViewById(R.id.indent_item_standard_tv);
            indent_item_total_tv = (TextView) itemView.findViewById(R.id.indent_item_total_tv);
            indent_item_num_tv = (TextView) itemView.findViewById(R.id.indent_item_num_tv);
            indent_item_payment_bt = (TextView) itemView.findViewById(R.id.indent_item_payment_tv);
            indent_item_logistics_bt = (TextView) itemView.findViewById(R.id.indent_item_logistics_tv);
            indent_item_confirm_bt = (TextView) itemView.findViewById(R.id.indent_item_confirm_tv);


        }
    }
}
