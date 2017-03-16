package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnItemClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yangsong on 2017/2/13.
 */

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHoader> {
    List<User> mList;
    private OnItemClickListener onItemClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public FriendAdapter(List<User> mList) {
        this.mList = mList;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        return new FriendAdapter.ViewHoader(view);
    }

    @Override
    public void onBindViewHolder(ViewHoader holder, final int position) {
        User user = mList.get(position);
        holder.friend_name_tv.setText(user.getName());
        //holder.friend_address_tv.setText(user.getAddress());
        if (user.getType() == 1) {
            holder.friend_hospital_ll.setVisibility(View.VISIBLE);
            holder.friend_authentication_iv.setVisibility(View.VISIBLE);
            //holder.friend_hospital_tv.setText(user.getHospital());
            //holder.friend_department_tv.setText(user.getDepartment());
        } else if (user.getType() == 0) {
            holder.friend_hospital_ll.setVisibility(View.GONE);
            holder.friend_authentication_iv.setVisibility(View.GONE);
        }
        MyApplication.newInstance().getmImageLoader().get(mList.get(position).getPic(), holder.friend_pic_cv);
        holder.friend_item_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener!=null)
                    onItemClickListener.onItemClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHoader extends RecyclerView.ViewHolder {


        private CircleImageView friend_pic_cv;
        private TextView friend_name_tv, friend_hospital_tv, friend_department_tv, friend_address_tv;
        private ImageView friend_sex_tv, friend_authentication_iv;
        private LinearLayout friend_hospital_ll,friend_item_ll;

        public ViewHoader(View itemView) {
            super(itemView);
            friend_pic_cv = (CircleImageView) itemView.findViewById(R.id.friend_pic_cv);
            friend_name_tv = (TextView) itemView.findViewById(R.id.friend_name_tv);
            friend_hospital_tv = (TextView) itemView.findViewById(R.id.friend_hospital_tv);//医院
            friend_department_tv = (TextView) itemView.findViewById(R.id.friend_department_tv);//科室
            friend_address_tv = (TextView) itemView.findViewById(R.id.friend_address_tv);
            friend_sex_tv = (ImageView) itemView.findViewById(R.id.friend_sex_tv);
            friend_authentication_iv = (ImageView) itemView.findViewById(R.id.friend_authentication_iv);//认证信息
            friend_hospital_ll = (LinearLayout) itemView.findViewById(R.id.friend_hospital_ll);
            friend_item_ll = (LinearLayout) itemView.findViewById(R.id.friend_item_ll);

        }
    }
}
