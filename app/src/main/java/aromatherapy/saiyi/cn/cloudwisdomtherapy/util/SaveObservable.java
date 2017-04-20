package aromatherapy.saiyi.cn.cloudwisdomtherapy.util;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by yangsong on 2017/4/20.
 */

public class SaveObservable implements Observable.OnSubscribe<String> {

    private Bitmap drawingCache = null;
    private String name;

    public SaveObservable(Bitmap drawingCache, String name) {
        this.drawingCache = drawingCache;
        this.name = name;
    }

    @Override
    public void call(Subscriber<? super String> subscriber) {
        if (drawingCache == null) {
            subscriber.onError(new NullPointerException("imageview的bitmap获取为null,请确认imageview显示图片了"));
        } else {
            try {
                File imageFile = new File(Environment.getExternalStorageDirectory() + "/saving_picture/", name);
                FileOutputStream outStream;
                outStream = new FileOutputStream(imageFile);
                drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                subscriber.onNext(Environment.getExternalStorageDirectory().getPath()+ "/saving_picture/");
                subscriber.onCompleted();
                outStream.flush();
                outStream.close();
            } catch (IOException e) {
                subscriber.onError(e);
            }
        }
    }
}
