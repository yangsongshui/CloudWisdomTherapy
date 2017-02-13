package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

import java.util.List;

/**
 * Created by Administrator on 2017/2/13.
 */
public class News {
    private String time;
    private String userName;
    private List<Information> NewsList;
    private String pic;

    public News(String time, String userName, List<Information> newsList, String pic) {
        this.time = time;
        this.userName = userName;
        NewsList = newsList;
        this.pic = pic;
    }

    public News() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Information> getNewsList() {
        return NewsList;
    }

    public void setNewsList(List<Information> newsList) {
        NewsList = newsList;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
