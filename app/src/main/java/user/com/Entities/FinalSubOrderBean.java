package user.com.Entities;

import java.sql.Date;
import java.util.ArrayList;

/**
 * Created by Admin on 9/22/2015.
 */
public class FinalSubOrderBean {

    private ArrayList<SubOrderBean> finalSubOrderList;
    private Date dateOfDelivery;
    private String mobileNumber;
    private String addresKey;

    public ArrayList<SubOrderBean> getFinalSubOrderList() {
        return finalSubOrderList;
    }

    public void setFinalSubOrderList(ArrayList<SubOrderBean> finalSubOrderList) {
        this.finalSubOrderList.addAll(finalSubOrderList);
    }

    public FinalSubOrderBean() {
        this.finalSubOrderList = new ArrayList<SubOrderBean>();
    }

}
