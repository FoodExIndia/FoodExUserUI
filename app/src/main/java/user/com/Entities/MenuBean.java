package user.com.Entities;

import java.sql.Blob;
import java.util.Date;

/**
 * Created by Admin on 2/8/2016.
 */
public class MenuBean {

    private int foodKey;
    private String itemName;
    private double itemPrice;
    private String itemShortDescription;
    private String itemFoodDescription;
    private int courseFlag;

    public byte[] getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(byte[] foodImage) {
        this.foodImage = foodImage;
    }

    private byte[] foodImage;

    public String getItemShortDescription() {
        return itemShortDescription;
    }

    public void setItemShortDescription(String itemShortDescription) {
        this.itemShortDescription = itemShortDescription;
    }

    public String getItemFoodDescription() {
        return itemFoodDescription;
    }

    public void setItemFoodDescription(String itemFoodDescription) {
        this.itemFoodDescription = itemFoodDescription;
    }


    public int getCourseFlag() {
        return courseFlag;
    }

    public void setCourseFlag(int courseFlag) {
        this.courseFlag = courseFlag;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getFoodKey() {
        return foodKey;
    }

    public void setFoodKey(int foodKey) {
        this.foodKey = foodKey;
    }


}
