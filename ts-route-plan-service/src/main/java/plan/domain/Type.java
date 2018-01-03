package plan.domain;

import java.io.Serializable;

/**
 * Created by Chenjie Xu on 2017/5/9.
 */
public enum Type implements Serializable{
    //G("GC-高铁/城际", 1), D("D-动车", 2);
    //Z("Z-直达",3), T("T-特快", 4), K("K-快速", 5);

    G("G", 1), D("D", 2),
    Z("Z",3), T("T", 4), K("K", 5);

    private String name;
    private int index;

    Type(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (Type type : Type.values()) {
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
