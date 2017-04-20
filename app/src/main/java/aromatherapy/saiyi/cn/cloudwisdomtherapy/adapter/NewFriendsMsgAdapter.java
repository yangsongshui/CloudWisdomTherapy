package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.db.InviteMessgeDao;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.domain.InviteMessage;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * 本demo只提供好友的邀请通知，群相关的通知，已经拒绝申请的处理请参考官方demo
 */
public class NewFriendsMsgAdapter extends ArrayAdapter<InviteMessage> {

    private Context context;
    private InviteMessgeDao messgeDao;

    public NewFriendsMsgAdapter(Context context, int textViewResourceId, List<InviteMessage> objects) {
        super(context, textViewResourceId, objects);
        this.context = context;
        messgeDao = new InviteMessgeDao(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {

            convertView = View.inflate(context, R.layout.new_friend_item , null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        final InviteMessage msg = getItem(position);
        if (msg != null) {

            holder.new_friend_add_tv.setVisibility(View.INVISIBLE);


            holder.new_friend_yes_tv.setText(msg.getReason());
            holder.friend_name_tv.setText(msg.getFrom());
            if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEAGREED) {
                holder.new_friend_yes_tv.setText("已添加");
            } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEINVITEED || msg.getStatus() == InviteMessage.InviteMesageStatus.BEAPPLYED ||
                    msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION) {
                holder.new_friend_add_tv.setVisibility(View.VISIBLE);
                holder.new_friend_add_tv.setEnabled(true);
                holder.new_friend_add_tv.setBackgroundResource(android.R.drawable.btn_default);
                if (msg.getReason() == null) {
                    // 如果没写理由
                    // holder.tv_reason.setText("请求加你为好友");
                }
                // 设置点击事件
                holder.new_friend_add_tv.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 同意别人发的好友请求
                        acceptInvitation(holder.new_friend_add_tv, msg);
                    }
                });

            } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.AGREED) {

            } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.REFUSED) {

            } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEREFUSED) {
                //用户拒绝添加
                holder.new_friend_yes_tv.setText("已拒绝");
            } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION_DECLINED) {

            }

        }

        return convertView;
    }

    /**
     * 同意好友请求或者群申请
     *
     * @param
     * @param
     */
    private void acceptInvitation(final TextView buttonAgree, final InviteMessage msg) {
        final ProgressDialog pd = new ProgressDialog(context);
        String str1 = context.getResources().getString(R.string.Are_agree_with);
        final String str2 = context.getResources().getString(R.string.Has_agreed_to);
        final String str3 = context.getResources().getString(R.string.Agree_with_failure);
        pd.setMessage(str1);
        pd.setCanceledOnTouchOutside(false);
        pd.show();

        new Thread(new Runnable() {
            public void run() {
                // 调用sdk的同意方法
                try {
                    if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEINVITEED) {//同意好友请求
                        EMClient.getInstance().contactManager().acceptInvitation(msg.getFrom());
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.BEAPPLYED) { //同意加群申请
                        EMClient.getInstance().groupManager().acceptApplication(msg.getFrom(), msg.getGroupId());
                    } else if (msg.getStatus() == InviteMessage.InviteMesageStatus.GROUPINVITATION) {
                        EMClient.getInstance().groupManager().acceptInvitation(msg.getGroupId(), msg.getGroupInviter());
                    }
                    msg.setStatus(InviteMessage.InviteMesageStatus.AGREED);
                    // 更新db
                    ContentValues values = new ContentValues();
                    values.put(InviteMessgeDao.COLUMN_NAME_STATUS, msg.getStatus().ordinal());
                    messgeDao.updateMessage(msg.getId(), values);
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @SuppressWarnings("deprecation")
                        @Override
                        public void run() {
                            pd.dismiss();
                            buttonAgree.setText(str2);
                            buttonAgree.setBackgroundDrawable(null);
                            buttonAgree.setEnabled(false);


                        }
                    });
                } catch (final Exception e) {
                    ((Activity) context).runOnUiThread(new Runnable() {

                        @SuppressLint("ShowToast")
                        @Override
                        public void run() {
                            pd.dismiss();
                            Toast.makeText(context, str3 + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }).start();
    }


    /*	private static class ViewHolder {
             TextView tv_name;
            TextView tv_reason;
            Button btn_agree;

         }*/
    public class ViewHolder {


        private CircleImageView friend_pic_cv;
        private TextView friend_name_tv, friend_hospital_tv, friend_department_tv, friend_address_tv, new_friend_add_tv, new_friend_yes_tv;
        private ImageView friend_sex_tv, friend_authentication_iv;
        private LinearLayout friend_hospital_ll, friend_item_ll;

        public ViewHolder(View itemView) {
            friend_pic_cv = (CircleImageView) itemView.findViewById(R.id.new_friend_pic_cv);
            friend_name_tv = (TextView) itemView.findViewById(R.id.new_friend_name_tv);
            new_friend_add_tv = (TextView) itemView.findViewById(R.id.new_friend_add_tv);
            new_friend_yes_tv = (TextView) itemView.findViewById(R.id.new_friend_yes_tv);
            friend_address_tv = (TextView) itemView.findViewById(R.id.new_friend_address_tv);
            friend_sex_tv = (ImageView) itemView.findViewById(R.id.new_friend_sex_tv);
            friend_hospital_ll = (LinearLayout) itemView.findViewById(R.id.new_friend_hospital_ll);
            friend_item_ll = (LinearLayout) itemView.findViewById(R.id.new_friend_item_ll);


            friend_authentication_iv = (ImageView) itemView.findViewById(R.id.new_friend_authentication_iv);//认证信息
            friend_hospital_tv = (TextView) itemView.findViewById(R.id.new_friend_hospital_tv);//医院
            friend_department_tv = (TextView) itemView.findViewById(R.id.new_friend_department_tv);//科室

        }
    }
}
