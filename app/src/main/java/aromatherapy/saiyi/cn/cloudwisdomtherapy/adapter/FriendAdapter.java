package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yangsong on 2017/2/13.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHoader>{


    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHoader holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHoader extends RecyclerView.ViewHolder {


        private CircleImageView news_pic_cv;
        private TextView news_msg_tv,news_name_tv,news_time_tv,news_num_tv;


        public ViewHoader(View itemView) {
            super(itemView);
            news_pic_cv= (CircleImageView) itemView.findViewById(R.id.news_pic_cv);
            news_name_tv= (TextView) itemView.findViewById(R.id.news_name_tv);
            news_time_tv= (TextView) itemView.findViewById(R.id.news_time_tv);
            news_msg_tv= (TextView) itemView.findViewById(R.id.news_msg_tv);
            news_num_tv= (TextView) itemView.findViewById(R.id.news_num_tv);

        }
    }
}
