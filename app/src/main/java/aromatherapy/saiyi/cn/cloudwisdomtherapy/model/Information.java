package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

/**
 * Created by Administrator on 2017/2/13.
 */
public class Information {
    String content;
    int sign;

    public Information() {
    }

    public Information(String content, int sign) {
        this.content = content;
        this.sign = sign;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSign() {
        return sign;
    }

    public void setSign(int sign) {
        this.sign = sign;
    }
}
