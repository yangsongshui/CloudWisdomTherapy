package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/2/23.
 */
public class Address implements Serializable {
    private String ID;
    private String name;
    private String phone;
    private String sheng;
    private String shi;
    private String qu;
    private String address;
    private boolean defa;
    private String mail;
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


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getQu() {
        return qu;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
