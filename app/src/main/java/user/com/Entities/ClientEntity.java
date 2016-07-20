package user.com.Entities;
import java.sql.Date;
/**
 * Created by Admin on 5/5/2016.
 */
public class ClientEntity {

    private String clientKey;
    private String clientEmail;
    private long clientMobileNum;
    private String clientFirstName;
    private String clientLastName;
    private String clientAddressLine1;
    private String clientAddressLine2;
    private String clientCity;
    private String clientArea;
    private String clientState;
    private String clientLandmark;
    public String getClientLandmark() {
        return clientLandmark;
    }
    public void setClientLandmark(String clientLandmark) {
        this.clientLandmark = clientLandmark;
    }
    private long clientZip;
    private Date clientDOB;
    private String clientGpsLocation;
    private Date insertDate;
    private Date updateDate;
    public String getClientKey() {
        return clientKey;
    }
    @Override
    public String toString() {
        return "ClientEntity [clientKey=" + clientKey + ", clientEmail=" + clientEmail + ", clientMobileNum="
                + clientMobileNum + ", clientFirsteName=" + clientFirstName + ", clientLastName=" + clientLastName
                + ", clientAddressLine1=" + clientAddressLine1 + ", clientAddressLine2=" + clientAddressLine2
                + ", clientCity=" + clientCity + ", clientArea=" + clientArea
                + ", clientState=" + clientState + ", clientZip=" + clientZip + ", clientDOB=" + clientDOB
                + ", clientGpsLocation=" + clientGpsLocation + ", insertDate=" + insertDate + ", updateDate="
                + updateDate + "]";
    }
    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }
    public String getClientEmail() {
        return clientEmail;
    }
    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }
    public long getClientMobileNum() {
        return clientMobileNum;
    }
    public void setClientMobileNum(long clientMobileNum) {
        this.clientMobileNum = clientMobileNum;
    }
    public String getClientFirstName() {
        return clientFirstName;
    }
    public void setClientFirstName(String clientFirstName) {
        this.clientFirstName = clientFirstName;
    }
    public String getClientLastName() {
        return clientLastName;
    }
    public void setClientLastName(String clientLastName) {
        this.clientLastName = clientLastName;
    }
    public String getClientAddressLine1() {
        return clientAddressLine1;
    }
    public void setClientAddressLine1(String clientAddressLine1) {
        this.clientAddressLine1 = clientAddressLine1;
    }
    public String getClientAddressLine2() {
        return clientAddressLine2;
    }
    public void setClientAddressLine2(String clientAddressLine2) {
        this.clientAddressLine2 = clientAddressLine2;
    }
    public String getClientCity() {
        return clientCity;
    }
    public void setClientCity(String clientCity) {
        this.clientCity = clientCity;
    }
    public String getClientArea() {
        return clientArea;
    }
    public void setClientArea(String clientArea) {
        this.clientArea = clientArea;
    }
    public String getClientState() {
        return clientState;
    }
    public void setClientState(String clientState) {
        this.clientState = clientState;
    }
    public long getClientZip() {
        return clientZip;
    }
    public void setClientZip(long clientZip) {
        this.clientZip = clientZip;
    }
    public Date getClientDOB() {
        return clientDOB;
    }
    public void setClientDOB(Date clientDOB) {
        this.clientDOB = clientDOB;
    }
    public String getClientGpsLocation() {
        return clientGpsLocation;
    }
    public void setClientGpsLocation(String clientGpsLocation) {
        this.clientGpsLocation = clientGpsLocation;
    }
    public Date getInsertDate() {
        return insertDate;
    }
    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

}


