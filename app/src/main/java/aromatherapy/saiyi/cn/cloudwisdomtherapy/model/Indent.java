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
    /*推荐人号码*/
    private String recommender;
    /*订单号*/
    private String orderNo;
    /*订单时间*/
    private String date;
    /*物流公司*/
    private String logisticsCompany;
    /*物流单号*/
    private String logisticsNo;
    /*退款金额*/
    private String reimburse;
    /*退货物流*/
    private String RelogisticsNo;
    /*退货公司*/
    private String RelogisticsCompany;
    /*退款原因*/
    private String returnReson;
    /*退款说明*/
    private String remark;
    /*退款类型*/
    private String type;
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

    public String getRecommender() {
        return recommender;
    }

    public void setRecommender(String recommender) {
        this.recommender = recommender;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getLogisticsNo() {
        return logisticsNo;
    }

    public void setLogisticsNo(String logisticsNo) {
        this.logisticsNo = logisticsNo;
    }

    public String getReimburse() {
        return reimburse;
    }

    public void setReimburse(String reimburse) {
        this.reimburse = reimburse;
    }

    public String getRelogisticsNo() {
        return RelogisticsNo;
    }

    public void setRelogisticsNo(String relogisticsNo) {
        RelogisticsNo = relogisticsNo;
    }

    public String getRelogisticsCompany() {
        return RelogisticsCompany;
    }

    public void setRelogisticsCompany(String relogisticsCompany) {
        RelogisticsCompany = relogisticsCompany;
    }

    public String getReturnReson() {
        return returnReson;
    }

    public void setReturnReson(String returnReson) {
        this.returnReson = returnReson;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
