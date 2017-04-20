package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.AddressActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.AftermarketActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.IndentActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.InstallActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.MyInformationActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    @BindView(R.id.me_pic_cv)
    CircleImageView mePicCv;//头像
    @BindView(R.id.me_doctor_renzheng_iv)
    ImageView meDoctorRenzhengIv;  //认证图标
    @BindView(R.id.me_name_tv)
    TextView meNameTv;
    @BindView(R.id.me_phone_tv)
    TextView mePhoneTv;
    User user;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        user = MyApplication.newInstance().getUser();
        if (user.getType() == 1)
            meDoctorRenzhengIv.setVisibility(View.VISIBLE);
        else
            meDoctorRenzhengIv.setVisibility(View.GONE);
        Log.e("MeFragment", user.getPic());
        if (user.getPic().length() > 0)
            MyApplication.newInstance().getmImageLoader().load(user.getPic()).into(mePicCv);
        meNameTv.setText(user.getName());
        mePhoneTv.setText(user.getPhone());

    }

    @Override
    protected int getContentView() {

        return R.layout.fragment_me;
    }


    @OnClick({R.id.me_pic_cv, R.id.me_order_rl, R.id.me_pending_payment_tv, R.id.me_waiting_for_delivery_tv, R.id.me_receiving_goods_tv, R.id.me_already_buy_tv, R.id.me_customer_service_tv, R.id.me_address_rl, R.id.me_discount_rl, R.id.me_product_rl, R.id.me_install_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_pic_cv:
                //点击头像
                startActivity(new Intent(getActivity(), MyInformationActivity.class).putExtra("user", user).putExtra("type", 0));
                break;
            case R.id.me_order_rl:
                //订单
                startActivity(new Intent(getActivity(), IndentActivity.class).putExtra("type", 8));
                break;
            case R.id.me_pending_payment_tv:
                //待付款
                startActivity(new Intent(getActivity(), IndentActivity.class).putExtra("type", 0));
                break;
            case R.id.me_waiting_for_delivery_tv:
                //待发货
                startActivity(new Intent(getActivity(), IndentActivity.class).putExtra("type", 1));
                break;
            case R.id.me_receiving_goods_tv:
                //待收货
                startActivity(new Intent(getActivity(), IndentActivity.class).putExtra("type", 2));
                break;
            case R.id.me_already_buy_tv:
                //已买到
                startActivity(new Intent(getActivity(), IndentActivity.class).putExtra("type", 3));
                break;
            case R.id.me_customer_service_tv:
                //售后服务
                startActivity(new Intent(getActivity(), AftermarketActivity.class));
                break;
            case R.id.me_address_rl:
                //收货地址
                startActivity(new Intent(getActivity(), AddressActivity.class));
                break;
            case R.id.me_discount_rl:
                //优惠方案
                break;
            case R.id.me_product_rl:
                //产品介绍
                //getwuliu();
                break;
            case R.id.me_install_rl:
                //设置
                startActivity(new Intent(getActivity(), InstallActivity.class));
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        user = MyApplication.newInstance().getUser();
        if (user.getType() == 1)
            meDoctorRenzhengIv.setVisibility(View.VISIBLE);
        else
            meDoctorRenzhengIv.setVisibility(View.GONE);
        Log.e("MeFragment", user.getPic());
        if (user.getPic().length() > 0)
            MyApplication.newInstance().getmImageLoader().load(user.getPic()).into(mePicCv);
        meNameTv.setText(user.getName());
        mePhoneTv.setText(user.getPhone());
    }

   /* public void getwuliu() {

        JSONObject jsonObject = null;
        String url = "http://ali-deliver.showapi.com/showapi_expInfo?com=auto&nu=" + "884810193665143773";
        NetworkRequests.getInstance().initViw(getActivity()).makeHTTPrequest(url);
    }*/
}
