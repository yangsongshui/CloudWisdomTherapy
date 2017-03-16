package aromatherapy.saiyi.cn.cloudwisdomtherapy.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;

/**
 * Created by yangsong on 2017/3/15.
 */

public class MallGoodsPopupWindow extends PopupWindow {
    TextView pop_confirm_tv, pop_num_tv, pop_standard_tv, pop_type_tv, pop_inventory_tv, pop_rmb_tv;
    ImageView pop_pic_iv, jian_button_iv, jia_button_iv;
    private View mMenuView;
    private int num = 1;
    private int MaxNum = 0;

    public MallGoodsPopupWindow(Context context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.goods_pop, null);
        initView(mMenuView);
        pop_confirm_tv.setOnClickListener(itemsOnClick);
        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        // this.setAnimationStyle(R.style.take_photo_anim   );
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mMenuView.findViewById(R.id.pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

    }

    private void initView(View view) {
        pop_confirm_tv = (TextView) view.findViewById(R.id.pop_confirm_tv);
        pop_num_tv = (TextView) view.findViewById(R.id.pop_num_tv);
        pop_standard_tv = (TextView) view.findViewById(R.id.pop_standard_tv);
        pop_type_tv = (TextView) view.findViewById(R.id.pop_type_tv);
        pop_inventory_tv = (TextView) view.findViewById(R.id.pop_inventory_tv);
        pop_rmb_tv = (TextView) view.findViewById(R.id.pop_rmb_tv);
        pop_pic_iv = (ImageView) view.findViewById(R.id.pop_pic_iv);
        jian_button_iv = (ImageView) view.findViewById(R.id.jian_button_iv);
        jia_button_iv = (ImageView) view.findViewById(R.id.jia_button_iv);
        MaxNum = Integer.parseInt(pop_inventory_tv.getText().toString());
        jian_button_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 1)
                    num--;
                pop_num_tv.setText(num + "");
            }
        });
        jia_button_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MaxNum >= num)
                    num++;
                pop_num_tv.setText(num + "");
            }
        });

    }

    public int getNum() {
        return num;
    }
}
