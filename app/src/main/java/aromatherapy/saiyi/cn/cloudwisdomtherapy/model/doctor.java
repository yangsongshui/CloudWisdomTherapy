package aromatherapy.saiyi.cn.cloudwisdomtherapy.model;

/**
 * Created by yangsong on 2017/2/13.
 */

public class doctor extends User {
    private String hospital;
    private String department;

    public doctor() {
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
