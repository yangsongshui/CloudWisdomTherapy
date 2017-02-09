package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

/**
 * Created by Administrator on 2017/2/9.
 */
public class Indent extends Commodity {
    /*订单状态*/
    private int state;
    /*购买总价*/
    private String total;

    public Indent(String name, String type, String picture, int state, String total) {
        super(name, type, picture);
        this.state = state;
        this.total = total;
    }

    public Indent(String name, String picture, String type, String standard, String price, String purchase_price, String num, int state, String total) {
        super(name, picture, type, standard, price, purchase_price, num);
        this.state = state;
        this.total = total;
    }

    public Indent(int state, String total) {
        this.state = state;
        this.total = total;
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
