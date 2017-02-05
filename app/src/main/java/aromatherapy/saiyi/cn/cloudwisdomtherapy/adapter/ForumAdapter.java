package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;


/**
 * Created by Administrator on 2016/12/22.
 */
public class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ViewHoader> {
    private List<String> data;
    private ItemClickListener mListener;


    public ForumAdapter(List<String> data) {
        this.data = data;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.information_item, parent, false);

        return new ViewHoader(view);
    }

    @Override
    public void onBindViewHolder(ViewHoader holder, final int position) {
        String str = data.get(position);
        holder.forum_time_tv.setText(str);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, position);
            }
        });
    }

    public void setItems(List<String> data) {
        this.data = data;
        this.notifyDataSetChanged();
    }

    public void clearData() {
        this.data.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
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

    public interface ItemClickListener {
        void onItemClick(View view, int postion);
    }

    public void setmListener(ItemClickListener mListener) {
        this.mListener = mListener;
    }
}