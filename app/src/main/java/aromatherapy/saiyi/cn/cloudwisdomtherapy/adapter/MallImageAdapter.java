package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;

/**
 * Created by Administrator on 2017/1/17.
 */
public class MallImageAdapter extends RecyclerView.Adapter<MallImageAdapter.ViewHolder> {
    private List<Mall> images;

    private int largeCardHeight, smallCardHeight;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MallImageAdapter(List<Mall> images, Activity activity) {
        this.images = images;
        DisplayMetrics dm = new DisplayMetrics();
        //获取屏幕信息
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        largeCardHeight = (int) activity.getResources().getDisplayMetrics().density * 300;
        smallCardHeight = (int) activity.getResources().getDisplayMetrics().density * 200;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_mall_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Mall mall = images.get(position);
        holder.merchandise_name_tv.setText(mall.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });

        MyApplication.newInstance().getmImageLoader().load(mall.getPicture()).skipMemoryCache(true).into( holder.iv);
        holder.merchandise_price_tv.setText(mall.getPrice());
        holder.merchandise_original_price_tv.setText(mall.getPurchase_price());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public void setItems(List<Mall> images) {
        this.images = images;
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;
        private ImageView iv;
        private TextView merchandise_name_tv, merchandise_price_tv, merchandise_original_price_tv;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.merchandise_image_cardview);
            iv = (ImageView) itemView.findViewById(R.id.merchandise_pic_iv);
            merchandise_name_tv = (TextView) itemView.findViewById(R.id.merchandise_name_tv);
            merchandise_price_tv = (TextView) itemView.findViewById(R.id.merchandise_price_tv);
            merchandise_original_price_tv = (TextView) itemView.findViewById(R.id.merchandise_original_price_tv);
        }
    }
}
