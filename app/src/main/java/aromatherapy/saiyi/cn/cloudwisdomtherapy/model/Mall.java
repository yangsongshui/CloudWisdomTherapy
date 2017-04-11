package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangsong on 2017/2/24.
 */

public class Mall extends Commodity implements Serializable {
    private String describe;//商品描述
    private int sold;
    private int inventory;
    private String productionFactory;//厂家
    private String GoogsType;
    private List<String> picList;
    private double  TotalPrice;

    public Mall(String name, String type, String picture, String describe, int sold, int inventory) {
        super(name, type, picture);
        this.describe = describe;
        this.sold = sold;
        this.inventory = inventory;
    }

    public Mall() {

    }

    public String getGoogsType() {
        return GoogsType;
    }

    public void setGoogsType(String googsType) {
        GoogsType = googsType;
    }
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public int getInventory() {
        return inventory;
    }

    public void setInventory(int inventory) {
        this.inventory = inventory;
    }
    public String getProductionFactory() {
        return productionFactory;
    }

    public void setProductionFactory(String productionFactory) {
        this.productionFactory = productionFactory;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }
}
