package aromatherapy.saiyi.cn.cloudwisdomtherapy.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;


/**
 * Created by Administrator on 2016/4/14.
 */
public class ReturnGoodsWindow extends PopupWindow {
    private TextView return_tv1, return_tv2, return_tv3, return_tv4, return_tv5, return_tv6, btn_cancel;
    private View mMenuView;

    public ReturnGoodsWindow(Activity context, View.OnClickListener itemsOnClick) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.return_pop, null);
        return_tv1 = (TextView) mMenuView.findViewById(R.id.return_tv1);
        return_tv2 = (TextView) mMenuView.findViewById(R.id.return_tv2);
        return_tv3 = (TextView) mMenuView.findViewById(R.id.return_tv3);
        return_tv4 = (TextView) mMenuView.findViewById(R.id.return_tv4);
        return_tv5 = (TextView) mMenuView.findViewById(R.id.return_tv5);
        return_tv6 = (TextView) mMenuView.findViewById(R.id.return_tv6);
        btn_cancel = (TextView) mMenuView.findViewById(R.id.cancel);
        //取消按钮
        btn_cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //设置按钮监听
        return_tv1.setOnClickListener(itemsOnClick);
        return_tv2.setOnClickListener(itemsOnClick);
        return_tv3.setOnClickListener(itemsOnClick);
        return_tv4.setOnClickListener(itemsOnClick);
        return_tv5.setOnClickListener(itemsOnClick);
        return_tv6.setOnClickListener(itemsOnClick);

        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.MATCH_PARENT);
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
}
