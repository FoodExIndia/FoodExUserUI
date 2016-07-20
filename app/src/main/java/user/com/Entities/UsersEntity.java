package user.com.Entities;
import java.sql.Date;

/**
 * Created by Admin on 5/3/2016.
 */
public class UsersEntity {

        private String clientKey;
        private String emailId;
        private long mobileNum;
        private String password;
        private int recentOTP;
        private Date insertDate;
        private Date updateDate;
        private String status;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "UsersEntity [clientKey=" + clientKey + ", emailId=" + emailId + ", mobileNum=" + mobileNum
                    + ", password=" + password + ", recentOTP=" + recentOTP + ", insertDate=" + insertDate + ", updateDate="
                    + updateDate + ", status=" + status + "]";
        }

        public String getClientKey() {
            return clientKey;
        }

        public void setClientKey(String clientKey) {
            this.clientKey = clientKey;
        }

        public String getEmailId() {
            return emailId;
        }

        public void setEmailId(String emailId) {
            this.emailId = emailId;
        }

        public long getMobileNum() {
            return mobileNum;
        }

        public void setMobileNum(long mobileNum) {
            this.mobileNum = mobileNum;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getRecentOTP() {
            return recentOTP;
        }

        public void setRecentOTP(int recentOTP) {
            this.recentOTP = recentOTP;
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

