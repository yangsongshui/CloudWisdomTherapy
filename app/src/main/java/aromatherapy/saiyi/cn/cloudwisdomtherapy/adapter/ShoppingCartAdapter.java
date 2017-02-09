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
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Commodity;

/**
 * Created by Administrator on 2017/2/7.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHoader> {

    private List<Commodity> data;
    private Context context;
    private boolean isConceal = false;

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
    public void onBindViewHolder(ViewHoader holder, int position) {
        holder.cart_item_name_tv.setText(data.get(position).getName());
        holder.cart_item_type_tv.setText("类型:" + data.get(position).getType());
        MyApplication.newInstance().getmImageLoader().get(data.get(position).getPicture(), holder.cart_item_pic_iv);
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("¥12.18");
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 0, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.cart_item_purchase_price_tv.setText(spannableString);
        if (isConceal) {
            holder.cart_item_information_ll.setVisibility(View.GONE);
            holder.cart_item_edit_ll.setVisibility(View.VISIBLE);
        } else {
            holder.cart_item_information_ll.setVisibility(View.VISIBLE);
            holder.cart_item_edit_ll.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {

        private CheckBox cart_item_choice_cb;
        private ImageView cart_item_pic_iv;
        private TextView cart_item_name_tv, cart_item_type_tv, cart_item_standard_tv,
                cart_item_price_tv, cart_item_purchase_price_tv, cart_item_num_tv;
        private LinearLayout cart_item_information_ll;
        private LinearLayout cart_item_edit_ll;

        public ViewHoader(View itemView) {
            super(itemView);
            cart_item_choice_cb = (CheckBox) itemView.findViewById(R.id.cart_item_choice_cb);
            cart_item_pic_iv = (ImageView) itemView.findViewById(R.id.cart_item_pic_iv);
            cart_item_name_tv = (TextView) itemView.findViewById(R.id.cart_item_name_tv);
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

}
