package aromatherapy.saiyi.cn.cloudwisdomtherapy.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.activity.SearchActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseFragment;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends BaseFragment implements TabLayout.OnTabSelectedListener {
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;

    private Fragment[] frags = new Fragment[2];
    protected BaseFragment currentFragment;
    private NewsFragment newsFragment;

    Map<String, String> map;

    Toastor toastor;

    @Override
    protected void initData(View layout, Bundle savedInstanceState) {
        toastor = new Toastor(getActivity());
        map = new HashMap<>();
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.addTab(mTabLayout.newTab().setText(getActivity().getResources().getString(R.string.friends_news)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getActivity().getResources().getString(R.string.friends_friends)));
        mTabLayout.addOnTabSelectedListener(this);

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_friends;
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //选中了tab的逻辑
        showFragment(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        //上一次tab的逻辑
        Log.i("上一次选中", tab.getPosition() + "");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        //上一次tab的逻辑
        Log.i("上一次选中", tab.getPosition() + "");
    }

    private void initData() {
        if (newsFragment == null) {
            newsFragment = new NewsFragment();
        }

        if (!newsFragment.isAdded()) {

            getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_fl, newsFragment).commit();

            currentFragment = newsFragment;
        }

    }

    /**
     * 添加或者显示 fragment
     *
     * @param transaction
     * @param fragment
     */
    protected void addOrShowFragment(FragmentTransaction transaction, Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment).add(R.id.fragment_fl, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = (BaseFragment) fragment;


    }

    private void showFragment(int position) {
        if (frags[position] == null) {
            frags[position] = getFrag(position);
        }

        addOrShowFragment(getActivity().getSupportFragmentManager().beginTransaction(), frags[position]);
    }

    private Fragment getFrag(int index) {
        switch (index) {
            case 0:
                return new NewsFragment();
            case 1:
                return new FriendFragment();
            default:
                return null;
        }
    }


    @OnClick({R.id.friend_search_tv, R.id.tv_toolbar_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.friend_search_tv:
                startActivity(new Intent(getActivity(), SearchActivity.class));
                break;
            case R.id.tv_toolbar_right:
                showDialog();
                break;
        }
    }

    private void add(final String friendID) {
        String phone = MyApplication.newInstance().getUser().getPhone();
        map.put("userID", phone);
        map.put("friendID", friendID);
         NetworkRequests.getInstance().initViw(getActivity()).GetRequests( Constant.ADDFRIENDREQUEST, map, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("PaymentActivity", jsonObject.toString());
                if (jsonObject.optInt("resCode") == 0) {
                    toastor.showSingletonToast("好友添加已申，等待对方同意");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                EMClient.getInstance().contactManager().addContact(friendID, "请求添加好友");
                            } catch (HyphenateException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();


                } else {
                    toastor.showSingletonToast(jsonObject.optString("resMessage"));
                }


            }
        });

    }

    private void showDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        DigitsKeyListener numericOnlyListener = new DigitsKeyListener(false, true);
        final EditText editText = new EditText(getActivity());
        editText.setKeyListener(numericOnlyListener);

        editText.setMaxLines(1);
        alertDialog.setTitle("请输入").setView(editText).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("") || editText.getText().toString().length() == 0)
                    return;
                add(editText.getText().toString());

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
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        tempDialog.show();
    }
}
