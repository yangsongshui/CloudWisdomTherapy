package aromatherapy.saiyi.cn.cloudwisdomtherapy.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.BaseActivity;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.view.MyViewPager;

public class MallActivity extends BaseActivity {


    /*广告栏数据*/
    // 滚动栏viewpager
    private MyViewPager vpager;
    private ImageView[] imgvsOfVpager = new ImageView[4];
    private View[] pointvsOfVpager = new View[4];
    // viewpager适配器
    private PagerAdapter vpagerAdapter;
    // 图片资源id数组
    private int[] imgvsResId = {R.drawable.pic, R.drawable.weixin_icon,
            R.drawable.pic, R.drawable.weixin_icon};
    // 点view的id数组
    private int[] pointvsId = {R.id.mall_point1ForVpager,
            R.id.mall_point2ForVpager,
            R.id.mall_point3ForVpager,R.id.mall_point4ForVpager};
    private Handler handler = new Handler(); // 用于执行viewpager自动滚动效果
    private Runnable tempRun; // viewpager自动滚动任务
    private int currentPageOfVpager; // 正在展示的viewpager页数
    private int imgvsNum;
    private boolean isResetTimer = true; // 手动改变viewpager显示页时，要重置自动滚动计时
    private ViewGroup adVentureView;

    @Override
    protected int getContentView() {
        return R.layout.activity_mall;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initVpager();
    }

    /**
     * 初始化viewpager
     *
     * @param
     */
    private void initVpager() {
        vpager = (MyViewPager) findViewById(R.id.mall_myVpager);
        vpager.setDisallowParentInterceptTouchEvent(true);
        imgvsNum = imgvsOfVpager.length;
        for (int i = 0; i < imgvsNum; i++) {
            // 图片
            imgvsOfVpager[i] = new ImageView(this);
            ViewPager.LayoutParams params = new ViewPager.LayoutParams();
            imgvsOfVpager[i].setScaleType(ImageView.ScaleType.FIT_XY);
            imgvsOfVpager[i].setImageResource(imgvsResId[i]);
            // 点
            pointvsOfVpager[i] = findViewById(pointvsId[i]);
            pointvsOfVpager[i].setTag(i);
            // pointvsOfVpager[i].setOnClickListener(onPointClickListener);
        }
        pointvsOfVpager[currentPageOfVpager % imgvsNum].setSelected(true);
        // 适配器
        vpagerAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position,
                                    Object object) {
                container.removeView(imgvsOfVpager[position % imgvsNum]);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {

                ImageView v = imgvsOfVpager[position % imgvsNum];
                //设置Tag用于实时更新图片
                v.setTag(imgvsResId[position % imgvsNum]);
                ViewGroup parent = (ViewGroup) v.getParent();
                //Log.i("ViewPaperAdapter", parent.toString());
                if (parent != null) {
                    parent.removeAllViews();
                }

                container.addView(imgvsOfVpager[position % imgvsNum]);
                return imgvsOfVpager[position % imgvsNum];
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0 == arg1;
            }

            @Override
            public int getCount() {
                return Integer.MAX_VALUE;
            }


        };
        vpager.setAdapter(vpagerAdapter);
        currentPageOfVpager = imgvsNum * 100;
        vpager.setCurrentItem(currentPageOfVpager);
        // viewpager的滚动事件
        vpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                pointvsOfVpager[currentPageOfVpager % imgvsNum]
                        .setSelected(false);
                currentPageOfVpager = arg0;
                pointvsOfVpager[currentPageOfVpager % imgvsNum]
                        .setSelected(true);
                // 判断是否重置滚动计时
                if (isResetTimer) {
                    handler.removeCallbacks(tempRun);
                    setVpagerAutoScroll();
                }
                isResetTimer = true;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        setVpagerAutoScroll();
    }

    /**
     * viewpager自动循环滚动
     */
    private void setVpagerAutoScroll() {
        tempRun = new Runnable() {
            @Override
            public void run() {
                isResetTimer = false;
                vpager.setCurrentItem((currentPageOfVpager + 1)
                        % Integer.MAX_VALUE);

                setVpagerAutoScroll();
                Log.e("-----", "自动滚动");
            }
        };
        handler.postDelayed(tempRun, 5000);
    }


    @Override
    protected void onStop() {
        super.onStop();
       /* if (hidden){
            handler.removeCallbacks(tempRun);
        }else {
            handler.postDelayed(tempRun, 5000);
        }*/
    }
}
