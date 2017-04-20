package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.NewUser;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.DateUtil;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyInformationActivity extends BaseActivity {

    @BindView(R.id.information_name_tv)
    TextView informationNameTv;
    @BindView(R.id.information_sex_tv)
    ImageView informationSexTv;
    @BindView(R.id.information_delete_iv)
    ImageView information_delete_iv;
    @BindView(R.id.information_age_tc)
    TextView informationAgeTc;
    @BindView(R.id.information_phone_tv)
    TextView informationPhoneTv;
    @BindView(R.id.information_address_tv)
    TextView informationAddressTv;
    @BindView(R.id.information_hospital_tv)
    TextView informationHospitalTv;
    @BindView(R.id.information_hospital_ll)
    LinearLayout informationHospitalLl;
    @BindView(R.id.information_family_tv)
    TextView informationFamilyTv;
    @BindView(R.id.information_family_ll)
    LinearLayout informationFamilyLl;
    @BindView(R.id.information_birthday_tv)
    TextView informationBirthdayTv;
    @BindView(R.id.information_height_tv)
    TextView informationHeightTv;
    @BindView(R.id.information_weight_tv)
    TextView informationWeightTv;
    @BindView(R.id.information_compile_tv)
    TextView informationCompileTv;
    @BindView(R.id.activity_my_information)
    LinearLayout activityMyInformation;
    @BindView(R.id.information_pic_iv)
    CircleImageView informationPicIv;
    User user;
    int type = -1;

    Map<String, String> map;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_my_information;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        toastor = new Toastor(this);
    }

    private void initView() {

        type = getIntent().getIntExtra("type", -1);
        if (type == 0) {
            //个人资料
            user = (User) getIntent().getSerializableExtra("user");
            information_delete_iv.setVisibility(View.GONE);
            informationCompileTv.setText(getString(R.string.information_compile));
        } else if (type == 1) {
            //新好友资料
            user = (NewUser) getIntent().getSerializableExtra("user");
            information_delete_iv.setVisibility(View.GONE);
            informationCompileTv.setText("添加好友");
        } else if (type == 2) {
            //查看好友资料
            user = (User) getIntent().getSerializableExtra("user");
            information_delete_iv.setVisibility(View.VISIBLE);
            informationCompileTv.setText("发起聊天");
        }

        if (user.getPic().length() > 0)
            MyApplication.newInstance().getmImageLoader().load(user.getPic()).into(informationPicIv);
        informationNameTv.setText(user.getName());
        if (user.getSex() != null && user.getSex().equals("女")) {
            informationSexTv.setImageDrawable(getResources().getDrawable(R.drawable.woman_icon));
        } else {
            informationSexTv.setImageDrawable(getResources().getDrawable(R.drawable.man_icon));
        }
        if (user.getBirthday().length() > 0)
            informationAgeTc.setText(DateUtil.yearDiff(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT), user.getBirthday()) + "");
        informationPhoneTv.setText(user.getPhone());
        informationAddressTv.setText(user.getAddress());
        if (user.getType() == 1) {
            informationFamilyLl.setVisibility(View.VISIBLE);
            informationHospitalLl.setVisibility(View.VISIBLE);
            informationHospitalTv.setText(user.getHospital());
            informationFamilyTv.setText(user.getDepartment());
        }
        informationBirthdayTv.setText(user.getBirthday());
        informationHeightTv.setText(user.getHeight());
        informationWeightTv.setText(user.getWidth());
    }

    @OnClick({R.id.information_back_iv, R.id.information_compile_tv, R.id.information_delete_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.information_back_iv:
                finish();

                break;
            case R.id.information_compile_tv:
                if (type == 0)
                    startActivity(new Intent(this, CompileActivity.class));
                else if (type == 1)
                    //添加好友
                    addUser();
                else if (type == 2)
                    startActivity(new Intent(this, ChatActivity.class).putExtra("Username", user.getName()).putExtra("ToChatUsername", user.getPhone()));
                break;
            case R.id.information_delete_iv:
                //删除好友
                deleteUser();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initView();
    }

    private void addUser() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("friendID", user.getPhone());
        map.put("statu", 0 + "");
         NetworkRequests.getInstance().initViw(this).GetRequests( Constant.UPDATEFRIENDSTATUS, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    //getUser();
                    toastor.showSingletonToast("添加成功");
                    finish();
                }else
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }

    private void deleteUser() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("friendID", user.getPhone());
         NetworkRequests.getInstance().initViw(this).GetRequests( Constant.DELFRIEND, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                if (jsonObject.optInt("resCode") == 0) {
                    toastor.showSingletonToast("删除成功");
                    finish();
                }else{
                    toastor.showSingletonToast("删除失败");
                }
                Log.e("CompileActivity", jsonObject.toString());

            }
        });
    }
}
