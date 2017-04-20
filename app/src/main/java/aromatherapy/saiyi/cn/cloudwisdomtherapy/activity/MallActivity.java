package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.inter.JsonDataReturnListener;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.model.Mall;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Constant;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Log;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.NetworkRequests;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.SaveObservable;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.SaveSubscriber;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.util.Toastor;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MallGoodsPopupWindow;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MallSharePopuoWindow;
import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MallActivity extends BaseActivity implements View.OnClickListener {


    @BindView(R.id.mall_goods_iv)
    ImageView mallGoodsIv;
    @BindView(R.id.mall_name_tv)
    TextView mallNameTv;
    @BindView(R.id.mall_sold_tv)
    TextView mallSoldTv;
    @BindView(R.id.mall_inventory_tc)
    TextView mallInventoryTc;
    @BindView(R.id.mall_money_tv)
    TextView mallMoneyTv;
    @BindView(R.id.mall_original_tv)
    TextView mallOriginalTv;
    @BindView(R.id.mall_factory_tv)
    TextView mallFactoryTv;
    @BindView(R.id.mall_introduce_tv)
    TextView mallIntroduceTv;

    MallSharePopuoWindow mallSharePopuoWindow;
    MallGoodsPopupWindow mallGoodsPopupWindow;
    Mall mall;
    boolean isJoin = false;
    Map<String, String> mMap;
    Toastor toastor;

    @Override
    protected int getContentView() {
        return R.layout.activity_mall;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mMap = new HashMap<>();
        mall = (Mall) getIntent().getSerializableExtra("mall");
        toastor = new Toastor(this);
        mallSharePopuoWindow = new MallSharePopuoWindow(this, this);
        mallGoodsPopupWindow = new MallGoodsPopupWindow(this, this);
        initView();

    }

    @OnClick({R.id.mall_service_tv, R.id.mall_share_tv, R.id.mall_join_tv, R.id.mall_purchase_tv, R.id.mall_back_iv, R.id.mall_describe_tv})
    public void onClickView(View view) {
        switch (view.getId()) {
            case R.id.mall_service_tv:
                break;
            case R.id.mall_describe_tv:

                startActivity(new Intent(this, DescribeActivity.class));
                break;
            case R.id.mall_share_tv:
                //分享
                mallSharePopuoWindow.showAtLocation(MallActivity.this.findViewById(R.id.activity_mall), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.mall_join_tv:
                //加入购物车
                isJoin = false;
                mallGoodsPopupWindow.showAtLocation(MallActivity.this.findViewById(R.id.activity_mall), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.mall_purchase_tv:
                //立即购买
                isJoin = true;
                mallGoodsPopupWindow.showAtLocation(MallActivity.this.findViewById(R.id.activity_mall), Gravity.BOTTOM, 0, 0); //设置layout在PopupWindow中显示的位;
                break;
            case R.id.mall_back_iv:
                finish();
                break;
        }
    }

    private void initView() {

        MyApplication.newInstance().getmImageLoader().load(mall.getPicture()).skipMemoryCache(true).into(mallGoodsIv);
        mallNameTv.setText(mall.getName());

        mallFactoryTv.setText(mall.getProductionFactory());
        mallMoneyTv.setText(mall.getPrice());
        mallOriginalTv.setText(mall.getPurchase_price());
        mallIntroduceTv.setText(mall.getDescribe());

        MyApplication.newInstance().getmImageLoader().load(mall.getPicture()).into(mallGoodsPopupWindow.getPop_pic_iv());
        mallGoodsPopupWindow.getPop_standard_tv().setText(mall.getStandard());
        mallGoodsPopupWindow.getPop_rmb_tv().setText(mall.getPrice());
        mallGoodsPopupWindow.getPop_type_tv().setText(mall.getType());

    }

    @Override
    public void onClick(View v) {
        UMImage image = new UMImage(this, mall.getPicture());
        switch (v.getId()) {
            case R.id.mall_QQ_tv:

                new ShareAction(MallActivity.this).setPlatform(SHARE_MEDIA.QQ)
                        .withText("云网智疗")
                        .withMedia(image)
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.mall_weixin_iv:

                new ShareAction(MallActivity.this).setPlatform(SHARE_MEDIA.WEIXIN)
                        .withText("云网智疗")
                        .setCallback(umShareListener)
                        .share();
                break;
            case R.id.mall_friend_tv:
                saveImageView(getViewBitmap(mallGoodsIv));

                startActivity(new Intent(this, ShareActivity.class).putExtra("name", mall.getID() + ".jpg"));
                break;
            case R.id.pop_confirm_tv:
                mallGoodsPopupWindow.dismiss();
                if (isJoin) {
                    mall.setNum(mallGoodsPopupWindow.getNum() + "");
                    int num = mallGoodsPopupWindow.getNum();
                    double price = Double.parseDouble(mall.getPrice());
                    mall.setTotalPrice((price * num));
                    ArrayList<Mall> malls = new ArrayList<>();
                    malls.add(mall);

                    startActivity(new Intent(this, ConfirmActivity.class).putExtra("list", malls));
                } else
                    addCart();
                break;
        }
    }

    private void addCart() {
        mMap.clear();
        String phone = MyApplication.newInstance().getUser().getPhone();
        mMap.put("phoneNumber", phone);
        mMap.put("commodityNo", mall.getID());
        mMap.put("num", mallGoodsPopupWindow.getNum() + "");
        mMap.put("price", (Double.parseDouble(mall.getPrice()) * mallGoodsPopupWindow.getNum()) + "");
        NetworkRequests.getInstance().initViw(this).GetRequests(Constant.ADDSHOPPINGCAR, mMap, new JsonDataReturnListener() {
            @Override
            public void jsonListener(JSONObject jsonObject) {
                Log.e("jsonListener", jsonObject.toString());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(MallActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MallActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(MallActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private Bitmap getViewBitmap(View view) {
        if (view == null) {
            return null;
        }
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    private void saveImageView(Bitmap drawingCache) {
        Observable.create(new SaveObservable(drawingCache, mall.getID() + ".jpg"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SaveSubscriber());
    }


}
