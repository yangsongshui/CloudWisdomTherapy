package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

/**
 * Created by Administrator on 2017/2/8.
 */
public class Commodity {
    private String name;
    private String picture;
    private String type;
    private String standard;
    private String price;
    private String purchase_price;
    private String num;
    private boolean choice;


    public Commodity() {
    }

    public Commodity(String name, String type, String picture) {
        this.name = name;
        this.type = type;
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStandard() {
        return standard;
    }

    public void setStandard(String standard) {
        this.standard = standard;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPurchase_price() {
        return purchase_price;
    }

    public void setPurchase_price(String purchase_price) {
        this.purchase_price = purchase_price;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public boolean isChoice() {
        return choice;
    }

    public void setChoice(boolean choice) {
        this.choice = choice;
    }

}
