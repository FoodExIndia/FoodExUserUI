package user.com.Entities;
import java.util.List;

/**
 * Created by Admin on 5/3/2016.
 */
public class UserDetails {

    private List<DeliveryAddressEntity> deliveryAddressList;
    private UsersEntity userEntity;
    private ClientEntity clientEntity;
    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }

    private int resultCode;

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public List<DeliveryAddressEntity> getDeliveryAddressList() {
        return deliveryAddressList;
    }

    public void setDeliveryAddressList(List<DeliveryAddressEntity> deliveryAddressList) {
        this.deliveryAddressList = deliveryAddressList;
    }

    public UsersEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UsersEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String toString() {
        return "UserDetailsResponseEntity [deliveryAddressList=" + deliveryAddressList + ", userEntity=" + userEntity
                + ", clientEntity=" + clientEntity + ", resultCode=" + resultCode + "]";
    }

}
