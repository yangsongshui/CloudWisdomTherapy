package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Indent;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.PicUtil;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.ReturnGoodsWindow;
import butterknife.BindView;
import butterknife.OnClick;

public class ReturnGoodsActivity extends BaseActivity {
    private static final int RESULT = 1;
    private static final int PHOTO_REQUEST_CUT = 2;


    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.return_cause_msg_tv)
    TextView returnCauseMsgTv;
    @BindView(R.id.return_money_et)
    EditText returnMoneyEt;
    @BindView(R.id.return_money_tv)
    TextView returnMoneyTv;
    @BindView(R.id.return_input_et)
    EditText returnInputEt;
    @BindView(R.id.return_add_iv1)
    ImageView returnAddIv1;
    @BindView(R.id.return_add_iv2)
    ImageView returnAddIv2;
    @BindView(R.id.return_add_iv3)
    ImageView returnAddIv3;
    @BindView(R.id.return_add_iv4)
    ImageView returnAddIv4;
    //自定义的弹出框类
    ReturnGoodsWindow menuWindow;

    Indent indent;
    Map<String, String> map;
    Toastor toast;
    int type = -1;

    @Override
    protected int getContentView() {
        return R.layout.activity_return_goods;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        toast = new Toastor(this);
        indent = (Indent) getIntent().getSerializableExtra("indent");
        type = getIntent().getIntExtra("type", -1);
        tv_toolbar_title.setText(getResources().getString(R.string.return_title));
        toolbar_left_iv.setVisibility(View.VISIBLE);
        //实例化SelectPicPopupWindow
        menuWindow = new ReturnGoodsWindow(this, itemsOnClick);
        returnMoneyTv.setText(indent.getTotal());

    }


    @OnClick({R.id.return_cause_msg_tv, R.id.return_add_iv1, R.id.return_add_iv2, R.id.return_add_iv3, R.id.return_add_iv4,
            R.id.return_button_tv, R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.return_cause_msg_tv:
                //退款原因
                menuWindow.showAtLocation(this.findViewById(R.id.activity_return_goods), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.return_add_iv1:
                openGallery();
                break;
            case R.id.return_add_iv2:
                openGallery();
                break;
            case R.id.return_add_iv3:
                openGallery();
                break;
            case R.id.return_add_iv4:
                openGallery();
                break;
            case R.id.return_button_tv:
                //提交申请
                returnGoods();
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {

        public void onClick(View v) {
            String msg = ((TextView) v).getText().toString();
            returnCauseMsgTv.setText(msg);
            menuWindow.dismiss();

        }

    };

    private void returnGoods() {
        map.clear();
        map.put("orderNo", indent.getOrderNo());
        map.put("returnType", type + "");
        if (returnMoneyEt.getText().toString().trim().length() > 0)
            map.put("returnMoney", returnMoneyEt.getText().toString());
        else {
            toast.showSingletonToast("退款金额不能为空");
            return;
        }
        map.put("returnReson", returnCauseMsgTv.getText().toString());
        map.put("remark", returnInputEt.getText().toString());
        if (photo1 != null)
            map.put("returnCertificateByte1", photo1);
        if (photo2 != null)
            map.put("returnCertificateByte2", photo2);
        if (photo3 != null)
            map.put("checkPicByte3", photo3);
         NetworkRequests.getInstance().initViw(this).GetRequests( Constant.INSERTRETURNMSG, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());
                if (jsonObject.optInt("resCode") == 0) {
                    toast.showSingletonToast("申请成功，等待商家确认");
                    finish();
                }
            }
        });
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
                returnAddIv1.setImageBitmap(bitmap);
                byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                photo1 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);
            } else if (indext == 2) {
                returnAddIv2.setImageBitmap(bitmap);
                byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                photo2 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);

            } else if (indext == 3) {
                returnAddIv3.setImageBitmap(bitmap);
                byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                photo3 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);

            } else {
                if (photo1 == null) {
                    byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                    photo1 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);
                    returnAddIv1.setImageBitmap(bitmap);
                } else if (photo2 == null) {
                    returnAddIv2.setImageBitmap(bitmap);
                    byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                    photo2 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);

                } else {
                    returnAddIv3.setImageBitmap(bitmap);
                    byte[] bytes = PicUtil.bitmap2Bytes(bitmap);
                    photo3 = Base64.encodeToString(bytes, 0, bytes.length, Base64.DEFAULT);

                }
            }

            // bitmap.recycle();
        }
    }
}
