package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/8.
 */
public class Commodity implements Serializable {
    //商品名称
    private String name;
    /*商品图片*/
    private String picture;
    /*商品种类*/
    private String type;
    /*商品规格*/
    private String standard;
    /*商品价格*/
    private String price;
    /*商品原价格*/
    private String purchase_price;
    /*商品购买数量*/
    private String num;
    /*购物车是否选中购买*/
    private boolean choice;


    public Commodity() {
    }

    public Commodity(String name, String type, String picture) {
        this.name = name;
        this.type = type;
        this.picture = picture;
    }

    public Commodity(String name, String picture, String type, String standard, String price, String purchase_price, String num) {
        this.name = name;
        this.picture = picture;
        this.type = type;
        this.standard = standard;
        this.price = price;
        this.purchase_price = purchase_price;
        this.num = num;

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
