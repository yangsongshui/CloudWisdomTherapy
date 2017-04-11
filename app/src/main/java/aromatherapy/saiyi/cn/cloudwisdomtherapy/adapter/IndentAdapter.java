package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;

/**
 * Created by Administrator on 2017/2/9.
 */
public class IndentAdapter extends RecyclerView.Adapter<IndentAdapter.ViewHoader> {
    List<Indent> mList;
    Context context;
    OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

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
    public void onBindViewHolder(ViewHoader holder, final int position) {
        Indent indent = mList.get(position);
        IndentMallAdapter mallAdapter = new IndentMallAdapter(indent.getMalls(), context);
        holder.indent_item_total_tv.setText(indent.getTotal());
        holder.indent_mall_lv.setAdapter(mallAdapter);
        if (indent.getState() == 1) {
            //待付款
            holder.indent_item_confirm_tv.setVisibility(View.GONE);
            holder.indent_item_payment_tv.setVisibility(View.VISIBLE);
            holder.indent_item_logistics_tv.setVisibility(View.GONE);
        } else if (indent.getState() == 3) {
            //已发货
            holder.indent_item_confirm_tv.setVisibility(View.VISIBLE);
            holder.indent_item_payment_tv.setVisibility(View.GONE);
            holder.indent_item_logistics_tv.setVisibility(View.VISIBLE);
        } else if (indent.getState() == 4) {
            //已确认

            //确认收货
            holder.indent_item_confirm_tv.setVisibility(View.GONE);
            //代付款按钮
            holder.indent_item_payment_tv.setVisibility(View.GONE);
            //物流信息按钮
            holder.indent_item_logistics_tv.setVisibility(View.VISIBLE);

        } else if (indent.getState() == 2) {
            //待发货
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {


        private TextView indent_item_total_tv, indent_item_payment_tv, indent_item_logistics_tv, indent_item_confirm_tv;
        private ListView indent_mall_lv;

        public ViewHoader(View itemView) {
            super(itemView);


            indent_item_payment_tv = (TextView) itemView.findViewById(R.id.indent_item_payment_tv);
            indent_item_logistics_tv = (TextView) itemView.findViewById(R.id.indent_item_logistics_tv);
            indent_item_confirm_tv = (TextView) itemView.findViewById(R.id.indent_item_confirm_tv);
            indent_item_total_tv = (TextView) itemView.findViewById(R.id.indent_item_total_tv);
           // indent_mall_lv = (ListView) itemView.findViewById(R.id.indent_mall_lv);

        }
    }

    public void setmList(List<Indent> list) {

        this.mList = list;
        this.notifyDataSetChanged();
    }
}
