package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/9.
 */
public class Indent implements Serializable {
    /*订单状态*/
    private int state;
    /*购买总价*/
    private String total;
    /*商品集合*/
    private List<Mall> malls;
    /*地址信息*/
    private Address address;

    public Indent() {
    }

    public Indent(int state, String total, List<Mall> malls, Address address) {
        this.state = state;
        this.total = total;
        this.malls = malls;
        this.address = address;
    }

    public List<Mall> getMalls() {
        return malls;
    }

    public void setMalls(List<Mall> malls) {
        this.malls = malls;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

}
