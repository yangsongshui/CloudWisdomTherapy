package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;

/**
 * Created by Administrator on 2017/2/7.
 */
public class ShoppingMallAdapter extends RecyclerView.Adapter<ShoppingMallAdapter.ViewHoader> {

    private List<Mall> data;
    private Context context;
    private boolean isConceal = false;


    public ShoppingMallAdapter(Context context, List<Mall> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.confirm_mall_rv, parent, false);

        return new ViewHoader(view);

    }

    @Override
    public void onBindViewHolder(final ViewHoader holder, final int position) {
        Mall commodity = data.get(position);
        holder.confirmNameTv.setText(commodity.getName());
        holder.confirmTypeTv.setText(commodity.getType());
        holder.confirmStandardTv.setText(commodity.getStandard());
        MyApplication.newInstance().getmImageLoader().load(commodity.getPicture()).into(holder.confirmPicIv);
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("¥" + commodity.getPurchase_price());
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 0, (commodity.getPurchase_price().length() + 1), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        holder.confirmPurchasePriceTv.setText(spannableString);
        holder.confirmPriceTv.setText(commodity.getPrice());
        holder.confirmNumTv.setText(commodity.getNum());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {
        ImageView confirmPicIv;//商品图片
        TextView confirmNameTv;//商品名字
        TextView confirmTypeTv;//商品类型
        TextView confirmStandardTv;//商品规格
        TextView confirmPurchasePriceTv;//商品原价
        TextView confirmPriceTv;
        TextView confirmNumTv;//购买数量


        public ViewHoader(View itemView) {
            super(itemView);

            confirmPicIv = (ImageView) itemView.findViewById(R.id.confirm_pic_iv);
            confirmNameTv = (TextView) itemView.findViewById(R.id.confirm_name_tv);
            confirmTypeTv = (TextView) itemView.findViewById(R.id.confirm_type_tv);
            confirmStandardTv = (TextView) itemView.findViewById(R.id.confirm_standard_tv);
            confirmPurchasePriceTv = (TextView) itemView.findViewById(R.id.confirm_purchase_price_tv);
            confirmPriceTv = (TextView) itemView.findViewById(R.id.confirm_price_tv);
            confirmNumTv = (TextView) itemView.findViewById(R.id.confirm_num_tv);

        }
    }
}
