package user.com.Entities;

import java.util.Date;
import java.util.jar.Attributes;

/**
 * Created by Admin on 2/7/2016.
 */
public class SubOrderBean implements Comparable<SubOrderBean>
{

    private int FoodKey;
    private int FoodQuantity;
    private int CourseFlag;
    private Date date;
    private String FoodName;

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public int getFoodQuantity() {
        return FoodQuantity;
    }

    public void setFoodQuantity(int foodQuantity) {
        FoodQuantity = foodQuantity;
    }

    public int getFoodKey() {
        return FoodKey;
    }

    public void setFoodKey(int foodKey) {
        FoodKey = foodKey;
    }

    public int getCourseFlag() {
        return CourseFlag;
    }

    public void setCourseFlag(int courseFlag) {
        CourseFlag = courseFlag;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int compareTo(SubOrderBean bean) {

        //System.out.println("Date 1:"+this.date);

       // System.out.println("Date 2:"+bean.date);

        if(this.date.after(bean.date))
        {
            return 1;
        }
        else if(this.date.equals(bean.date))
        {
            //System.out.println("Course Flag : "+this.CourseFlag);
            //System.out.println("Course Bean Flag : " + bean.CourseFlag);
            if(this.CourseFlag>bean.CourseFlag)
            {
               // System.out.println("Course Flag return : 1");
                return 1;
            }else
            {
                return -1;
            }
        }else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "SubOrderBean{" +
                "FoodKey=" + FoodKey +
                ", FoodQuantity=" + FoodQuantity +
                ", CourseFlag=" + CourseFlag +
                ", date=" + date +
                ", FoodName='" + FoodName + '\'' +
                '}';
    }
}
