package user.com.Entities;
import java.io.Serializable;
/**
 * Created by Admin on 5/3/2016.
 */
@SuppressWarnings("serial")
public class DeliveryAddressEntity implements Serializable{

        private String clientKey;
        private int addressKey;
        private int defaultAddressFlag;
        private String clientAddressLine1;
        private String clientAddressLine2;
        private String clientLandmark;
        private String clientCity;
        private String clientArea;
        private String clientState;
        private long clientZip;
        public String getClientKey() {
            return clientKey;
        }
        public void setClientKey(String clientKey) {
            this.clientKey = clientKey;
        }
        public int getAddressKey() {
            return addressKey;
        }
        public void setAddressKey(int addressKey) {
            this.addressKey = addressKey;
        }
        public int getDefaultAddressFlag() {
            return defaultAddressFlag;
        }

        @Override
        public String toString() {
            return "DeliveryAddress [clientKet=" + clientKey + ", addressKey=" + addressKey + ", defaultAddressFlag="
                    + defaultAddressFlag + ", clientAddressLine1=" + clientAddressLine1 + ", clientAddressLine2="
                    + clientAddressLine2 + ", clientLandmark=" + clientLandmark + ", clientCity=" + clientCity
                    + ", clientArea=" + clientArea + ", clientState=" + clientState + ", clientZip=" + clientZip + "]";
        }

        public void setDefaultAddressFlag(int defaultAddressFlag) {
            this.defaultAddressFlag = defaultAddressFlag;
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
        public String getClientLandmark() {
            return clientLandmark;
        }
        public void setClientLandmark(String clientLandmark) {
            this.clientLandmark = clientLandmark;
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

    }
