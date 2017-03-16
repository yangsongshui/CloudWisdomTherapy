package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

/**
 * Created by yangsong on 2017/3/15.
 */

public class NewUser extends User {
    private int state;

    public NewUser(String name, String pic, String phone, int type, int state) {
        super(name, pic, phone, type);
        this.state = state;
    }

    public NewUser() {

    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
