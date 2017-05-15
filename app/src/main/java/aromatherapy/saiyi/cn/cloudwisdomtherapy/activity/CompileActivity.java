package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.text.method.DigitsKeyListener;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.User;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.GetCity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.PicUtil;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.CompilePopupWindow;
import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static aromatherapy.saiyi.cn.cloudwisdomtherapy.util.PicUtil.hasSdcard;


public class CompileActivity extends BaseActivity {

    private static final int RESULT = 1;
    private static final int PHOTO_REQUEST_CUT = 2;
    private static final int CODE_CAMERA_REQUEST = 3;
    /* 头像文件 */
    private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";

    @BindView(R.id.tv_toolbar_white_title)
    TextView tv_toolbar_white_title;
    @BindView(R.id.toolbar_left_white_iv)
    ImageView toolbar_left_white_iv;
    @BindView(R.id.tv_toolbar_white_right)
    TextView tvToolbarWhiteRight;
    @BindView(R.id.compile_pic_iv)
    CircleImageView compilePicIv;
    @BindView(R.id.compile_name_tv)
    TextView compileNameTv;
    @BindView(R.id.compile_phone_tv)
    TextView compilePhoneTv;
    @BindView(R.id.compile_address_tv)
    TextView compileAddressTv;
    @BindView(R.id.compile_sex_tv)
    TextView compileSexTv;
    @BindView(R.id.compile_height_tv)
    TextView compileHeightTv;
    @BindView(R.id.compile_weight_tv)
    TextView compileWeightTv;
    @BindView(R.id.compile_hospital_tv)
    TextView compileHospitalTv;
    @BindView(R.id.compile_hospital_ll)
    LinearLayout compileHospitalLl;
    @BindView(R.id.compile_family_tv)
    TextView compileFamilyTv;
    @BindView(R.id.compile_family_ll)
    LinearLayout compileFamilyLl;
    @BindView(R.id.compile_birthday_tv)
    TextView compileBirthdayTv;

    private OptionsPickerView optionsPickerView;//地区选择
    private TimePickerView timePickerView;
    Bitmap bitmap;
    GetCity getCity;
    //自定义的弹出框类
    CompilePopupWindow menuWindow;
    String photo = "";
    Map<String, String> map;
    Toast toast;
    User user;

    @Override
    protected int getContentView() {
        return R.layout.activity_compile;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        user = MyApplication.newInstance().getUser();
        map = new HashMap<>();
        toast = new Toast(this);
        tv_toolbar_white_title.setText(getResources().getString(R.string.compile_title));
        toolbar_left_white_iv.setVisibility(View.VISIBLE);
        tvToolbarWhiteRight.setVisibility(View.VISIBLE);
        tvToolbarWhiteRight.setText(getString(R.string.information_save));
        toolbar_left_white_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getCity = new GetCity(this);

        initView();
        initInfor();
        //实例化SelectPicPopupWindow
        menuWindow = new CompilePopupWindow(this, itemsOnClick);
    }


    @OnClick({R.id.compile_pic_ll, R.id.compile_name_ll, R.id.compile_phone_ll, R.id.compile_address_ll, R.id.compile_sex_ll,
            R.id.compile_height_ll, R.id.compile_weight_ll, R.id.compile_hospital_ll, R.id.compile_family_ll, R.id.compile_birthday_ll, R.id.tv_toolbar_white_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.compile_pic_ll:
                //显示窗口
                menuWindow.showAtLocation(CompileActivity.this.findViewById(R.id.activity_compile), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;

                break;
            case R.id.compile_name_ll:
                showDialog2(compileNameTv);
                break;

            case R.id.compile_address_ll:
                optionsPickerView.show();
                break;
            case R.id.compile_sex_ll:
                showSexDialog();
                break;
            case R.id.compile_height_ll:
                showDialog(compileHeightTv);
                break;
            case R.id.compile_weight_ll:
                showDialog(compileWeightTv);
                break;

            case R.id.compile_birthday_ll:
                timePickerView.show();
                break;
            case R.id.tv_toolbar_white_right:
                //保存
                saveInfor();
                break;
        }
    }

    private void initInfor() {
        if (user.getPic().length() > 0)
            MyApplication.newInstance().getmImageLoader().load(user.getPic()).skipMemoryCache(true).into(compilePicIv);
        compileNameTv.setText(user.getName());
        if (user.getSex() != null && user.getSex().equals("女")) {
            compileSexTv.setText(user.getSex());
        } else {
            compileSexTv.setText("男");
        }
        if (user.getBirthday().length() > 0)
            compileBirthdayTv.setText(user.getBirthday());
        compilePhoneTv.setText(user.getPhone());
        compileAddressTv.setText(user.getAddress());
        if (user.getType() == 1) {
            compileFamilyLl.setVisibility(View.VISIBLE);
            compileHospitalLl.setVisibility(View.VISIBLE);
            compileHospitalTv.setText(user.getHospital());
            compileFamilyTv.setText(user.getDepartment());
        }
        compileBirthdayTv.setText(user.getBirthday());
        compileHeightTv.setText(user.getHeight());
        compileWeightTv.setText(user.getWidth());
    }

    private void saveInfor() {
        String phone = compilePhoneTv.getText().toString().trim();
        String name = compileNameTv.getText().toString().trim();
        String addtess = compileAddressTv.getText().toString().trim();
        String brithday = compileBirthdayTv.getText().toString().trim();
        String height = compileHeightTv.getText().toString().trim();
        String weight = compileWeightTv.getText().toString().trim();
        String sex = compileSexTv.getText().toString().trim();
        map.clear();
        map.put("phoneNumber", phone);
        map.put("nickName", name);
        if (photo.length() > 0)
            map.put("headPicByte", photo);
        map.put("city", addtess);
        map.put("sex", sex);
        map.put("birthday", brithday);
        map.put("weight", weight);
        map.put("height", height);
        user.setBirthday(brithday);
        user.setSex(sex);
        user.setHeight(height);
        user.setWidth(weight);
        user.setAddress(addtess);
        user.setName(name);
        NetworkRequests.getInstance().initViw(this).GetRequests(Constant.UPDATEUSER, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {

                Log.e("CompileActivity", jsonObject.toString());
                user.setPic(jsonObject.optJSONObject("resBody").optString("headPic"));
                MyApplication.newInstance().setUser(user);
                finish();
            }
        });

    }


    private void showDialog(final TextView textView) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CompileActivity.this);
        DigitsKeyListener numericOnlyListener = new DigitsKeyListener(false, true);
        final EditText editText = new EditText(this);
        if (textView.getId() == R.id.compile_weight_tv || textView.getId() == R.id.compile_height_tv) {
            editText.setKeyListener(numericOnlyListener);
        }
        editText.setMaxLines(1);
        alertDialog.setTitle("请输入").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("") || editText.getText().toString().length() == 0)
                    return;
                textView.setText(editText.getText().toString());

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog tempDialog = alertDialog.create();
        tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        tempDialog.show();
    }

    private void showDialog2(final TextView textView) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(CompileActivity.this);
        final EditText editText = new EditText(this);
        editText.setMaxLines(1);
        alertDialog.setTitle("请输入").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("") || editText.getText().toString().length() == 0)
                    return;
                textView.setText(editText.getText().toString());

            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog tempDialog = alertDialog.create();
        tempDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            public void onShow(DialogInterface dialog) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        tempDialog.show();
    }

    private void showSexDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CompileActivity.this);
        builder.setTitle("选择性别");
        builder.setPositiveButton("男", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                compileSexTv.setText("男");
            }
        });
        builder.setNegativeButton("女", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                compileSexTv.setText("女");
            }
        });
        builder.create().show();
    }

    private void initView() {
        optionsPickerView = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                String tx;
                //返回的分别是三个级别的选中位置
                String city = getCity.getOptions1Items().get(options1).getPickerViewText();
                //  如果是直辖市或者特别行政区只设置市和区/县
                if ("北京".equals(city) || "上海".equals(city) || "天津".equals(city) || "重庆".equals(city) || "澳门".equals(city) || "香港".equals(city)) {
                    tx = city + " " + getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                } else {
                    tx = getCity.getOptions1Items().get(options1).getPickerViewText() +
                            getCity.getOptions2Items().get(options1).get(options2) +
                            getCity.getOptions3Items().get(options1).get(options2).get(options3).getPickerViewText();
                }

                compileAddressTv.setText(tx);
            }
        }).build();
        optionsPickerView.setPicker(getCity.getOptions1Items(), getCity.getOptions2Items(), getCity.getOptions3Items());//三级选择器
        timePickerView = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                compileBirthdayTv.setText(getTime(date));
            }
        }).setType(TimePickerView.Type.YEAR_MONTH_DAY)
                .build();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.compile_photo_tv:
                    //相册
                    openGallery();
                    break;
                case R.id.compile_camera_tv:
                    //相机
                    openGamera();

                    break;

                default:
                    break;
            }
            menuWindow.dismiss();

        }

    };

    /**
     * 打开相机
     */
    private void openGamera() {
        // 跳转至拍照界面
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // 判断存储卡是否可用，存储照片文件
        if (hasSdcard()) {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                    .fromFile(new File(Environment
                            .getExternalStorageDirectory(), IMAGE_FILE_NAME)));
        }

        startActivityForResult(intentFromCapture, CODE_CAMERA_REQUEST);
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
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT) {
            if (data != null) {
                Uri uri = data.getData();
                Log.e("------", uri + " ");
                crop(uri);
            }
        } else if (requestCode == PHOTO_REQUEST_CUT) {
            if (data != null) {
                setImageToHeadView(data);
            }
        } else if (requestCode == CODE_CAMERA_REQUEST) {
            if (hasSdcard()) {
                File tempFile = new File(
                        Environment.getExternalStorageDirectory(),
                        IMAGE_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(getApplication(), "没有SDCard!", Toast.LENGTH_LONG)
                        .show();
            }


        }
        super.onActivityResult(requestCode, resultCode, data);

    }


    private void setImageToHeadView(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null) {
            bitmap = extras.getParcelable("data");
            compilePicIv.setImageBitmap(bitmap);
            byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
            photo = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);
            bitmap.recycle();
        }
    }


}
