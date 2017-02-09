package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.IndentActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
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

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentView() {

        return R.layout.fragment_me;
    }


    @OnClick({R.id.me_pic_cv, R.id.me_order_rl, R.id.me_pending_payment_tv, R.id.me_waiting_for_delivery_tv, R.id.me_receiving_goods_tv, R.id.me_already_buy_tv, R.id.me_customer_service_tv, R.id.me_address_rl, R.id.me_discount_rl, R.id.me_product_rl, R.id.me_install_rl})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.me_pic_cv:
//                点击头像
                break;
            case R.id.me_order_rl:
//               订单
                startActivity(new Intent(getActivity(), IndentActivity.class));
                break;
            case R.id.me_pending_payment_tv:
//待付款
                break;
            case R.id.me_waiting_for_delivery_tv:
//                待发货
                break;
            case R.id.me_receiving_goods_tv:
//                待收货
                break;
            case R.id.me_already_buy_tv:
//                已买到
                break;
            case R.id.me_customer_service_tv:
//                售后服务
                break;
            case R.id.me_address_rl:
//                收货地址
                break;
            case R.id.me_discount_rl:
//                优惠方案
                break;
            case R.id.me_product_rl:
//                产品介绍
                break;
            case R.id.me_install_rl:
//                设置
                break;
        }
    }
}
