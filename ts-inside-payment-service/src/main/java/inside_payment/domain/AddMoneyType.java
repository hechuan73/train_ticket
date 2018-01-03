package inside_payment.domain;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/3.
 */
public enum AddMoneyType implements Serializable {
    //add money对应账户充值，draw back money对应改签或退票时的退款
    A("Add Money",1),D("Draw Back Money",2);

    private String name;
    private int index;

    AddMoneyType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (AddMoneyType type : AddMoneyType.values()) {
            if (type.getIndex() == index) {
                return type.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
