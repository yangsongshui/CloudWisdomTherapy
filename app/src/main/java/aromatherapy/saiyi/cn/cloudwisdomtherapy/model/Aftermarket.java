package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

/**
 * Created by Administrator on 2017/2/16.
 */
public class Aftermarket extends Indent{
    private String reimburse;

    public Aftermarket(String name, String type, String picture, int state, String total) {
        super(name, type, picture, state, total);
    }

    public Aftermarket(int state, String total) {
        super(state, total);
    }

    public Aftermarket(String name, String picture, String type, String standard, String price, String purchase_price, String num, int state, String total, String reimburse) {
        super(name, picture, type, standard, price, purchase_price, num, state, total);
        this.reimburse = reimburse;
    }

    public String getReimburse() {
        return reimburse;
    }

    public void setReimburse(String reimburse) {
        this.reimburse = reimburse;
    }

}
