package aromatherapy.saiyi.cn.cloudwisdomtherapy.util;

import rx.Subscriber;

/**
 * Created by yangsong on 2017/4/20.
 */

public class SaveSubscriber extends Subscriber<String> {
    public SaveSubscriber() {
    }

    @Override
    public void onCompleted() {
        //Toast.makeText(getApplicationContext(), "保存成功", Toast.LENGTH_SHORT).show();
        Log.e(getClass().getSimpleName(), "保存成功");
    }

    @Override
    public void onError(Throwable e) {
        Log.i(getClass().getSimpleName(), e.toString());
        //Toast.makeText(getApplicationContext(), "保存失败——> " + e.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNext(String s) {
        Log.e(getClass().getSimpleName(), s.toString());

    }
}
