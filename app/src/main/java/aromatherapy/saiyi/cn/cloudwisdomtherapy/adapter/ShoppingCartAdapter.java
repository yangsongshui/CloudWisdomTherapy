package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemCheckListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Commodity;

/**
 * Created by Administrator on 2017/2/7.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHoader> {

    private List<Commodity> data;
    private Context context;
    private boolean isConceal = false;
    private OnItemCheckListener onItemCheckListener;

    public void setOnItemCheckListener(OnItemCheckListener onItemCheckListener) {
        this.onItemCheckListener = onItemCheckListener;
    }

    public ShoppingCartAdapter(Context context, List<Commodity> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_cart_itme, parent, false);

        return new ViewHoader(view);

    }

    @Override
    public void onBindViewHolder(final ViewHoader holder, final int position) {
        Commodity commodity = data.get(position);
        holder.cart_item_name_tv.setText(commodity.getName());
        holder.cart_item_type_tv.setText("类型:" + commodity.getType());
        holder.cart_item_edit_type.setText("类型:" + commodity.getType());
        holder.cart_item_standard_tv.setText("规格:" + commodity.getStandard());
        holder.cart_item_edit_standard.setText("规格:" + commodity.getStandard());


        MyApplication.newInstance().getmImageLoader().load(commodity.getPicture()).into(holder.cart_item_pic_iv);
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("¥" + commodity.getPurchase_price());
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 0, (commodity.getPurchase_price().length() + 1), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.cart_item_purchase_price_tv.setText(spannableString);
        if (isConceal) {
            holder.cart_item_information_ll.setVisibility(View.GONE);
            holder.cart_item_edit_ll.setVisibility(View.VISIBLE);
        } else {
            holder.cart_item_information_ll.setVisibility(View.VISIBLE);
            holder.cart_item_edit_ll.setVisibility(View.GONE);
        }
        holder.num_tv.setText(commodity.getNum());
        holder.cart_item_price_tv.setText("¥" + commodity.getPrice());
        holder.cart_item_num_tv.setText(commodity.getNum());
        holder.cart_item_choice_cb.setChecked(commodity.isChoice());
        holder.cart_item_choice_cb.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemCheckListener != null)
                            onItemCheckListener.onitemCheck(v, holder.cart_item_choice_cb.isChecked(), position);
                    }
                }
        );

        holder.jia_button_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(holder.num_tv.getText().toString());
                num++;
                holder.cart_item_num_tv.setText("×" + num);
                holder.num_tv.setText("" + num);
                if (onItemCheckListener != null)
                    onItemCheckListener.onNumCheck(v, num, position);
            }
        });
        holder.jian_button_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(holder.num_tv.getText().toString());
                num--;
                if (num == 0)
                    num = 1;
                holder.cart_item_num_tv.setText("×" + num);
                holder.num_tv.setText(num + "");
                if (onItemCheckListener != null)
                    onItemCheckListener.onNumCheck(v, num, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {

        private CheckBox cart_item_choice_cb;
        private ImageView cart_item_pic_iv, jia_button_tv, jian_button_iv;
        private TextView cart_item_name_tv, cart_item_type_tv, cart_item_standard_tv,
                cart_item_price_tv, cart_item_purchase_price_tv, cart_item_num_tv, num_tv, cart_item_edit_type, cart_item_edit_standard;
        private LinearLayout cart_item_information_ll;
        private LinearLayout cart_item_edit_ll;

        public ViewHoader(View itemView) {
            super(itemView);
            cart_item_choice_cb = (CheckBox) itemView.findViewById(R.id.cart_item_choice_cb);
            cart_item_pic_iv = (ImageView) itemView.findViewById(R.id.cart_item_pic_iv);
            cart_item_pic_iv = (ImageView) itemView.findViewById(R.id.cart_item_pic_iv);
            jia_button_tv = (ImageView) itemView.findViewById(R.id.jia_button_tv);
            jian_button_iv = (ImageView) itemView.findViewById(R.id.jian_button_iv);
            cart_item_name_tv = (TextView) itemView.findViewById(R.id.cart_item_name_tv);
            cart_item_edit_type = (TextView) itemView.findViewById(R.id.cart_item_edit_type);
            cart_item_edit_standard = (TextView) itemView.findViewById(R.id.cart_item_edit_standard);
            num_tv = (TextView) itemView.findViewById(R.id.num_tv);
            cart_item_type_tv = (TextView) itemView.findViewById(R.id.cart_item_type_tv);
            cart_item_standard_tv = (TextView) itemView.findViewById(R.id.cart_item_standard_tv);
            cart_item_price_tv = (TextView) itemView.findViewById(R.id.cart_item_price_tv);
            cart_item_purchase_price_tv = (TextView) itemView.findViewById(R.id.cart_item_purchase_price_tv);
            cart_item_num_tv = (TextView) itemView.findViewById(R.id.cart_item_num_tv);
            cart_item_information_ll = (LinearLayout) itemView.findViewById(R.id.cart_item_information_ll);
            cart_item_edit_ll = (LinearLayout) itemView.findViewById(R.id.cart_item_edit_ll);

        }
    }

    public void setConceal(boolean isConceal) {
        this.isConceal = isConceal;
        notifyDataSetChanged();
    }

    public void setItems(List<Commodity> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }
}
