package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/13.
 */
public class Information implements Serializable {
    String context;
    String topPic;
    String title;
    String date;
    String type;

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopPic() {
        return topPic;
    }

    public void setTopPic(String topPic) {
        this.topPic = topPic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Information() {
    }


}
