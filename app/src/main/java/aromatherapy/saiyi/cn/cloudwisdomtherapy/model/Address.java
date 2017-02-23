package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/23.
 */
public class Address implements Serializable{
    private String name;
    private String phone;
    private String address;

    public Address() {
    }

    public Address(String name, String phone, String address, boolean defa) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.defa = defa;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isDefa() {
        return defa;
    }

    public void setDefa(boolean defa) {
        this.defa = defa;
    }

    private boolean defa;
}
