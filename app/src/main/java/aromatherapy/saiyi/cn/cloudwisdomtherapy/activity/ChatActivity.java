package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.easeui.widget.EaseChatMessageList;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import butterknife.BindView;
import butterknife.OnClick;

public class ChatActivity extends BaseActivity {


    @BindView(R.id.toolbar_left_iv)
    ImageView toolbar_left_iv;
    @BindView(R.id.tv_toolbar_title)
    TextView tv_toolbar_title;
    @BindView(R.id.char_msg_et)
    EditText charMsgEt;
    @BindView(R.id.message_list)
    EaseChatMessageList messageList;

    @Override
    protected int getContentView() {
        return R.layout.activity_chat;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        toolbar_left_iv.setVisibility(View.VISIBLE);
        tv_toolbar_title.setText("张医师");
    }


    @OnClick({R.id.char_msg_et, R.id.char_send_tv,R.id.toolbar_left_iv})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.char_msg_et:
                break;
            case R.id.char_send_tv:
                break;
            case R.id.toolbar_left_iv:
                finish();
                break;
        }
    }
}
