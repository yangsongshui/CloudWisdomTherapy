package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
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
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.OnViewClickListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.NewUser;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yangsong on 2017/2/13.
 */

public class NewFriendAdapter extends RecyclerView.Adapter<NewFriendAdapter.ViewHoader> {
    List<NewUser> mList;
    Context context;
    private OnItemClickListener onItemClickListener;

    public void setOnbuttonClickListener(OnViewClickListener onbuttonClickListener) {
        this.onbuttonClickListener = onbuttonClickListener;
    }

    private OnViewClickListener onbuttonClickListener;


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public NewFriendAdapter(List<NewUser> mList, Context context) {

        this.mList = mList;
        this.context = context;
    }

    @Override
    public ViewHoader onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.new_friend_item, parent, false);
        return new NewFriendAdapter.ViewHoader(view);
    }

    @Override
    public void onBindViewHolder(ViewHoader holder, final int position) {
        NewUser user = mList.get(position);
        holder.friend_name_tv.setText(user.getName());
        holder.friend_address_tv.setText(user.getAddress());
        if (user.getType() == 1) {
            holder.friend_hospital_ll.setVisibility(View.VISIBLE);
            holder.friend_authentication_iv.setVisibility(View.VISIBLE);
            holder.friend_hospital_tv.setText(user.getHospital());
            holder.friend_department_tv.setText(user.getDepartment());
        } else if (user.getType() == 0) {
            holder.friend_hospital_ll.setVisibility(View.GONE);
            holder.friend_authentication_iv.setVisibility(View.GONE);
        }
        if (user.getState() == 2) {
            holder.new_friend_add_tv.setVisibility(View.VISIBLE);
            holder.new_friend_add_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onbuttonClickListener != null)
                        onbuttonClickListener.OnViewClick(v, position, 0);
                }
            });
            holder.new_friend_yes_tv.setVisibility(View.GONE);
        } else if (user.getState() == 1 || user.getState() == 0) {
            holder.new_friend_add_tv.setVisibility(View.GONE);
            holder.new_friend_yes_tv.setVisibility(View.VISIBLE);
            if (user.getState() == 0) {
                holder.new_friend_yes_tv.setText(context.getString(R.string.new_friend_yes));
                holder.new_friend_yes_tv.setTextColor(context.getResources().getColor(R.color.text_color));
            } else {
                holder.new_friend_yes_tv.setText(context.getString(R.string.new_friend_no));
                holder.new_friend_yes_tv.setTextColor(context.getResources().getColor(R.color.red));
            }

        }

        MyApplication.newInstance().getmImageLoader().load(mList.get(position).getPic()).into(holder.friend_pic_cv);
        holder.friend_item_ll.setOnClickListener(new View.OnClickListener() {
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


        private CircleImageView friend_pic_cv;
        private TextView friend_name_tv, friend_hospital_tv, friend_department_tv, friend_address_tv, new_friend_add_tv, new_friend_yes_tv;
        private ImageView friend_sex_tv, friend_authentication_iv;
        private LinearLayout friend_hospital_ll, friend_item_ll;

        public ViewHoader(View itemView) {
            super(itemView);
            friend_pic_cv = (CircleImageView) itemView.findViewById(R.id.new_friend_pic_cv);
            friend_name_tv = (TextView) itemView.findViewById(R.id.new_friend_name_tv);
            new_friend_add_tv = (TextView) itemView.findViewById(R.id.new_friend_add_tv);
            new_friend_yes_tv = (TextView) itemView.findViewById(R.id.new_friend_yes_tv);
            friend_hospital_tv = (TextView) itemView.findViewById(R.id.new_friend_hospital_tv);//医院
            friend_department_tv = (TextView) itemView.findViewById(R.id.new_friend_department_tv);//科室
            friend_address_tv = (TextView) itemView.findViewById(R.id.new_friend_address_tv);
            friend_sex_tv = (ImageView) itemView.findViewById(R.id.new_friend_sex_tv);
            friend_authentication_iv = (ImageView) itemView.findViewById(R.id.new_friend_authentication_iv);//认证信息
            friend_hospital_ll = (LinearLayout) itemView.findViewById(R.id.new_friend_hospital_ll);
            friend_item_ll = (LinearLayout) itemView.findViewById(R.id.new_friend_item_ll);

        }
    }

    public void setmList(List<NewUser> list) {

        this.mList = list;
        this.notifyDataSetChanged();
    }
}
