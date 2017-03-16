package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;

/**
 * Created by yangsong on 2017/3/13.
 */

public class LogiaticsAdapter extends RecyclerView.Adapter<LogiaticsAdapter.ViewHoader> {

    private List<String> data;
    private Context context;


    public LogiaticsAdapter(Context context, List<String> data) {
        this.data = data;
        this.context = context;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.logistics_item, parent, false);

        return new LogiaticsAdapter.ViewHoader(view);
    }

    @Override
    public void onBindViewHolder(ViewHoader holder, int position) {
        if (position == 0) {
            holder.time_marker.setImageDrawable(context.getResources().getDrawable(R.drawable.wuliu_peisong_icon));

        } else if (position == (data.size()-1)) {
            holder.itme_line.setVisibility(View.INVISIBLE);
            holder.time_marker.setImageDrawable(context.getResources().getDrawable(R.drawable.wuliu_jiedian_icon));
        } else {
            holder.time_marker.setImageDrawable(context.getResources().getDrawable(R.drawable.wuliu_jiedian_icon));
        }
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {

        private ImageView time_marker, itme_line;
        private TextView item_examine_tv, item_time_tv;

        public ViewHoader(View itemView) {
            super(itemView);
            time_marker = (ImageView) itemView.findViewById(R.id.time_marker);
            itme_line = (ImageView) itemView.findViewById(R.id.itme_line);
            item_examine_tv = (TextView) itemView.findViewById(R.id.item_examine_tv);
            item_time_tv = (TextView) itemView.findViewById(R.id.item_time_tv);


        }
    }
}
