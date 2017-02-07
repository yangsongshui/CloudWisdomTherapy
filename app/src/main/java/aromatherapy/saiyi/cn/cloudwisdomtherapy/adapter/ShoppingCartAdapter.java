package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;

/**
 * Created by Administrator on 2017/2/7.
 */
public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHoader> {
    private List<String> data;
    public ShoppingCartAdapter(List<String> data) {
        this.data = data;
    }
    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_item, parent, false);

        return new ViewHoader(view);

    }

    @Override
    public void onBindViewHolder(ViewHoader holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHoader extends RecyclerView.ViewHolder {

        private TextView forum_title_tv;
        private TextView forum_time_tv;

        public ViewHoader(View itemView) {
            super(itemView);

            forum_title_tv = (TextView) itemView.findViewById(R.id.forum_title_tv);
            //时间
            forum_time_tv = (TextView) itemView.findViewById(R.id.forum_time_tv);
        }
    }


}
