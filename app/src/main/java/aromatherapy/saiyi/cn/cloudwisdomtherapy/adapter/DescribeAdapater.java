package aromatherapy.saiyi.cn.cloudwisdomtherapy.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import aromatherapy.saiyi.cn.cloudwisdomtherapy.R;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.app.MyApplication;
import aromatherapy.saiyi.cn.cloudwisdomtherapy.bean.MyBaseAdapter;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by yangsong on 2017/4/18.
 */

public class DescribeAdapater extends MyBaseAdapter<String> {
    private List<String> conversationList;


    private Context context;

    public DescribeAdapater(List<String> list) {
        super(list);
    }

    public DescribeAdapater(Context context, List<String> objects) {
        super(objects);
        this.context = context;
        conversationList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.describe_item, parent, false);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        MyApplication.newInstance().getmImageLoader().load(conversationList.get(position)).into(holder.news_pic_cv);

        return convertView;
    }

    public class ViewHolder {

        private ImageView news_pic_cv;

        public ViewHolder(View itemView) {
            news_pic_cv = (ImageView) itemView.findViewById(R.id.describe_iv);


        }
    }
}
