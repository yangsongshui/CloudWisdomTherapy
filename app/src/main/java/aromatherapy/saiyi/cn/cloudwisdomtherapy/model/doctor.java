package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

/**
 * Created by yangsong on 2017/2/13.
 */

public class doctor extends User {
    private String hospital;
    private String department;

    public doctor() {
    }

    public doctor(String name, String pic, String phone, String hospital, String department) {
        super(name, pic, phone);
        this.hospital = hospital;
        this.department = department;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
