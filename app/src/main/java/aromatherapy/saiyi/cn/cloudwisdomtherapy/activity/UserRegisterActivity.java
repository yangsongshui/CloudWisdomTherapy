package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.MD5;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.PicUtil;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

public class UserRegisterActivity extends BaseActivity {
    private static final int RESULT = 1;
    private static final int PHOTO_REQUEST_CUT = 2;

    @BindView(R.id.registered_name_et)
    EditText registeredNameEt;
    @BindView(R.id.registered_phone_et)
    EditText registered_phone_et;
    @BindView(R.id.registered_coed_et)
    EditText registeredCoedEt;
    @BindView(R.id.registered_psw_et)
    EditText registeredPswEt;
    @BindView(R.id.registered_psw2_et)
    EditText registeredPsw2Et;
    @BindView(R.id.register_verification_tv)
    TextView registerVerificationTv;
    @BindView(R.id.register_add_iv1)
    ImageView registerAddIv1;
    @BindView(R.id.register_add_iv2)
    ImageView registerAddIv2;
    @BindView(R.id.register_add_iv3)
    ImageView registerAddIv3;
    @BindView(R.id.register_verification_ll)
    LinearLayout registerVerificationLl;
    @BindView(R.id.registered_getcoed_tv)
    TextView registered_getcoed_tv;
    @BindView(R.id.registered_psw_ll2)
    LinearLayout registered_psw_ll2;
    @BindView(R.id.registered_psw_ll)
    LinearLayout registered_psw_ll;

    @BindView(R.id.tv_toolbar_title)
    TextView tvToolbarTitle;
    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.registered_hospital_et)
    EditText registeredHospitalEt;
    @BindView(R.id.registered_consultingRoom_et)
    EditText registeredConsultingRoomEt;
    @BindView(R.id.register_add_iv4)
    ImageView registerAddIv4;
    @BindView(R.id.register_complete_tv)
    TextView registerCompleteTv;
    @BindView(R.id.registered_hospital_ll)
    LinearLayout registeredHospitalLl;
    @BindView(R.id.registered_consultingRoom_ll)
    LinearLayout registeredConsultingRoomLl;

    private String CODE = "";
    Toastor toasr;
    private String type = "";
    Map<String, String> map;
    private CountDownTimer timer;

    @Override
    protected int getContentView() {
        return R.layout.activity_user_register;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        toasr = new Toastor(this);
        type = getIntent().getStringExtra("type");
        initToolbar();
        initView();
        timer = new CountDownTimer(60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                registered_getcoed_tv.setText(millisUntilFinished / 1000 + "s后重新发送短信");
            }

            @Override
            public void onFinish() {
                registered_getcoed_tv.setText("获取短信验证码");
                registered_getcoed_tv.setEnabled(true);
            }
        };

    }

    private void initToolbar() {
        toolbar_left_iv.setVisibility(View.VISIBLE);
        toolbar_left_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void initView() {
        if (type.equals("2") || type.equals("32")) {
            tvToolbarTitle.setText(getResources().getString(R.string.register_user));
            registerVerificationLl.setVisibility(View.GONE);
            registeredHospitalLl.setVisibility(View.GONE);
            registeredConsultingRoomLl.setVisibility(View.GONE);
        } else if (type.equals("1") || type.equals("31")) {
            tvToolbarTitle.setText(getResources().getString(R.string.register_doctor));
            registerVerificationLl.setVisibility(View.VISIBLE);
            registeredHospitalLl.setVisibility(View.VISIBLE);
            registeredConsultingRoomLl.setVisibility(View.VISIBLE);
        }
        if (type.equals("31") || type.equals("32")) {
            registered_psw_ll2.setVisibility(View.GONE);
            registered_psw_ll.setVisibility(View.GONE);
        }

        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        spannableStringBuilder.append(getResources().getString(R.string.register_doctor_verification));
        //设置字体大小
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(50);
        //设置需要改变字体大小的位置
        spannableStringBuilder.setSpan(absoluteSizeSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //设置字体颜色
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(Color.parseColor("#333333"));
        //设置需要改变字体颜色的位置
        spannableStringBuilder.setSpan(foregroundColorSpan, 0, 4, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        registerVerificationTv.setText(spannableStringBuilder);

    }

    @OnClick({R.id.registered_getcoed_tv, R.id.register_add_iv1, R.id.register_add_iv2, R.id.register_add_iv3, R.id.register_add_iv4, R.id.register_complete_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.registered_getcoed_tv:

                String phone = registered_phone_et.getText().toString().trim();
                if (phone.length() == 11) {
                    map.clear();
                    map.put("type", "0");
                    map.put("phoneNumber", phone);
                    timer.start();
                    NetworkRequests.GetRequests(this, Constant.GETIDENTIFY, map, new JsonDataReturnListener() {
                        @Override
                        public void jsonListener(JSONObject jsonObject) {
                            Log.e("User", jsonObject.toString());

                            toasr.showSingletonToast(jsonObject.optString("resMessage"));
                            if (jsonObject.optInt("resCode") == 0) {
                                CODE = jsonObject.optJSONObject("resBody").optString("identify");
                            }
                        }
                    });
                } else {
                    toasr.showSingletonToast("手机号码长度不正确");
                }

                break;
            case R.id.register_add_iv1:
                openGallery();
                indext = 1;
                break;
            case R.id.register_add_iv2:
                openGallery();
                indext = 2;
                break;
            case R.id.register_add_iv3:
                openGallery();
                indext = 3;
                break;
            case R.id.register_add_iv4:
                if (photo1 == null || photo2 == null || photo3 == null) {
                    openGallery();
                    indext = 4;
                }
                break;
            case R.id.register_complete_tv:

                if (type.equals("2") || type.equals("1")) {
                    registerUser(type);
                } else {
                    if (type.equals("31"))
                        registerUser("1");
                    else if (type.equals("32"))
                        registerUser("2");
                }

                break;
        }
    }


    private void registerUser(String type) {
        String psw = registeredPswEt.getText().toString().trim();
        String psw2 = registeredPsw2Et.getText().toString().trim();
        String phone = registered_phone_et.getText().toString().trim();
        String name = registeredNameEt.getText().toString().trim();
        String code = registeredCoedEt.getText().toString().trim();
        String hospital = registeredHospitalEt.getText().toString().trim();
        String consultingRoom = registeredConsultingRoomEt.getText().toString().trim();
        if (phone.length() == 11) {
            if (CODE.equals(code)) {
                if ((psw.length() >= 6 && psw.length() <= 16) || this.type.length() == 2) {
                    if (psw.equals(psw2) || this.type.length() == 2) {
                        if (name.length() > 0) {
                            //用户注册
                            map.clear();
                            map.put("phoneNumber", phone);
                            if (this.type.length() == 1) {
                                map.put("passWord", MD5.getMD5(psw));
                                map.put("isThird", "0");
                            } else if (this.type.length() == 2) {
                                map.put("passWord", "");
                                map.put("isThird", "1");
                            }

                            map.put("role", type);
                            map.put("nickName", name);
                            //医生注册
                            if (photo2 != null && photo1 != null && photo3 != null) {
                                map.put("checkPicByte1", photo1);
                                map.put("checkPicByte2", photo2);
                                map.put("checkPicByte3", photo3);
                                if (hospital.length() > 0 && consultingRoom.length() > 0) {
                                    map.put("hospital", hospital);
                                    map.put("consultingRoom", consultingRoom);
                                } else {
                                    return;
                                }

                            } else {
                                return;
                            }

                            NetworkRequests.GetRequests(this, Constant.REGISTER, map, new JsonDataReturnListener() {
                                @Override
                                public void jsonListener(JSONObject jsonObject) {
                                    Log.e("jsonListener", jsonObject.toString());
                                    if (jsonObject.optInt("resCode") == 0) {
                                        toasr.showSingletonToast(jsonObject.optString("resMessage"));
                                        finish();
                                    } else {
                                        toasr.showSingletonToast(jsonObject.optString("resMessage"));
                                    }

                                }
                            });

                        } else
                            toasr.showSingletonToast("昵称不能为空");
                    } else
                        toasr.showSingletonToast("两次密码输入不一致");
                } else
                    toasr.showSingletonToast("密码长度不得低于6位或大于十六位");
            } else
                toasr.showSingletonToast("验证码输入错误");
        } else {
            toasr.showSingletonToast("手机号码长度不正确");
        }
    }

    /**
     * 打开相册
     */
    public void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);// 打开相册
        intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
        intent.setType("image/*");
        startActivityForResult(intent, RESULT);
    }

    private void crop(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT) {
            if (data != null) {
                Uri uri = data.getData();
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
                setImageToHeadView(data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    String photo1, photo2, photo3;
    int indext = 0;


    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            Bitmap bitmap = extras.getParcelable("data");

            if (indext == 1) {
                registerAddIv1.setImageBitmap(bitmap);
                byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                photo1 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);
            } else if (indext == 2) {
                registerAddIv2.setImageBitmap(bitmap);
                byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                photo2 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);

            } else if (indext == 3) {
                registerAddIv3.setImageBitmap(bitmap);
                byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                photo3 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);

            } else {
                if (photo1 == null) {
                    byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                    photo1 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);
                    registerAddIv1.setImageBitmap(bitmap);
                } else if (photo2 == null) {
                    registerAddIv2.setImageBitmap(bitmap);
                    byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                    photo2 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);

                } else {
                    registerAddIv3.setImageBitmap(bitmap);
                    byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                    photo3 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);

                }
            }

            // bitmap.recycle();
        }
    }

}
