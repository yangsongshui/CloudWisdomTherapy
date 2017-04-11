package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.MyBaseAdapter;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;

/**
 * Created by yangsong on 2017/4/10.
 */

public class IndentMallAdapter extends MyBaseAdapter<Mall> {
    Context mContext;
    List<Mall> list = new ArrayList<>();

    public IndentMallAdapter(List<Mall> list, Context context) {
        super(list);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null) {
            convertView = View.inflate(mContext,
                    R.layout.indent_mall_item, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
       /* Mall mall=mList.get(position);
        holder.indent_item_name_tv.setText(mall.getName());
        MyApplication.newInstance().getmImageLoader().get(mall.getPicture(), holder.indent_item_pic_iv);
        holder.indent_item_num_tv.setText(mall.getNum());
        holder.indent_item_purchase_price_tv.setText(mall.getPurchase_price());
        holder.indent_item_price_tv.setText(mall.getPrice());
        holder.indent_item_type_tv.setText(mall.getType());
        holder.indent_item_standard_tv.setText(mall.getStandard());*/
        return convertView;
    }




   public class ViewHolder {
       private ImageView indent_item_pic_iv;
       private TextView indent_item_name_tv,  indent_item_price_tv,indent_item_purchase_price_tv,
               indent_item_type_tv, indent_item_standard_tv, indent_item_num_tv, intent_rmb_tv;

        public ViewHolder(View itemView) {

            indent_item_pic_iv = (ImageView) itemView.findViewById(R.id.indent_item_pic_iv);
            intent_rmb_tv = (TextView) itemView.findViewById(R.id.intent_rmb_tv);
            indent_item_name_tv = (TextView) itemView.findViewById(R.id.indent_item_name_tv);
            indent_item_price_tv = (TextView) itemView.findViewById(R.id.indent_item_price_tv);
            indent_item_type_tv = (TextView) itemView.findViewById(R.id.indent_item_type_tv);
            indent_item_standard_tv = (TextView) itemView.findViewById(R.id.indent_item_standard_tv);
            indent_item_purchase_price_tv = (TextView) itemView.findViewById(R.id.indent_item_purchase_price_tv);
            indent_item_num_tv = (TextView) itemView.findViewById(R.id.indent_item_num_tv);
            indent_item_purchase_price_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            intent_rmb_tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            itemView.setTag(this);
        }
    }
}
