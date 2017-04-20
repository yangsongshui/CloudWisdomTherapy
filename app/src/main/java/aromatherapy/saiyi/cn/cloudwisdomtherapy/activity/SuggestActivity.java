package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

import static android.R.attr.type;

public class SuggestActivity extends BaseActivity {


    @BindView(R.id.toolbar_left_white_iv)
    ImageView toolbarLeftWhiteIv;
    @BindView(R.id.tv_toolbar_white_title)
    TextView tvToolbarWhiteTitle;
    @BindView(R.id.suggest_input_et)
    EditText suggestInputEt;
    Map<String, String> map;
    Toastor toast;

    @Override
    protected int getContentView() {
        return R.layout.activity_suggest;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        map = new HashMap<>();
        toast = new Toastor(this);
        toolbarLeftWhiteIv.setVisibility(View.VISIBLE);
        tvToolbarWhiteTitle.setText(getResources().getString(R.string.install_suggest));
    }


    @OnClick({R.id.toolbar_left_white_iv, R.id.suggest_submit_tv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_left_white_iv:
                finish();
                break;
            case R.id.suggest_submit_tv:
                addSuggest();
                break;

        }
    }

    private void addSuggest() {
        map.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("orderNo", phone);
        if (tvToolbarWhiteTitle.getText().toString().trim().length() > 0) {
            map.put("returnType", type + "");
             NetworkRequests.getInstance().initViw(this).GetRequests( Constant.INSERTRETURNMSG, map, new JsonDataReturnListener() {
                @Override
                public void jsonListener(JSONObject jsonObject) {
                    Log.e("jsonListener", jsonObject.toString());
                    if (jsonObject.optInt("resCode") == 0) {
                        toast.showSingletonToast("提交成功");
                        finish();
                    }
                }
            });
        }
    }
}
