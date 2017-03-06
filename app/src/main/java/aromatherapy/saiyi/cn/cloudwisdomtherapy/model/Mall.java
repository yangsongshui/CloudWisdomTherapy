package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

import java.io.Serializable;

/**
 * Created by yangsong on 2017/2/24.
 */

public class Mall extends Commodity implements Serializable {
    private String describe;
    private String factory;
    private int sold;
    private int inventory;

    public Mall(String name, String type, String picture, String describe, String factory, int sold, int inventory) {
        super(name, type, picture);
        this.describe = describe;
        this.factory = factory;
        this.sold = sold;
        this.inventory = inventory;
    }

    public Mall() {

    }


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
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
}
