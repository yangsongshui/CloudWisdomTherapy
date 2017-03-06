package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.News;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/2/13.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHoader> {
    List<News> mList;
    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NewsAdapter(List<News> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false);
        return new NewsAdapter.ViewHoader(view);
    }

    @Override
    public void onBindViewHolder(ViewHoader holder, final int position) {
        holder.news_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {


        private CircleImageView news_pic_cv;
        private TextView news_msg_tv, news_name_tv, news_time_tv, news_num_tv;
        private LinearLayout news_ll;


        public ViewHoader(View itemView) {
            super(itemView);
            news_pic_cv = (CircleImageView) itemView.findViewById(R.id.news_pic_cv);
            news_name_tv = (TextView) itemView.findViewById(R.id.news_name_tv);
            news_time_tv = (TextView) itemView.findViewById(R.id.news_time_tv);
            news_msg_tv = (TextView) itemView.findViewById(R.id.news_msg_tv);
            news_num_tv = (TextView) itemView.findViewById(R.id.news_num_tv);
            news_ll = (LinearLayout) itemView.findViewById(R.id.news_ll);

        }
    }
}
