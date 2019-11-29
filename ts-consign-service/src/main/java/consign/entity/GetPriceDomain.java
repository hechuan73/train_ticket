package consign.entity;

/**
 * @author fdse
 */
public class GetPriceDomain {
    private double weight;
    private boolean isWithinRegion;

    public GetPriceDomain(){
        //Default Constructor
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isWithinRegion() {
        return isWithinRegion;
    }

    public void setWithinRegion(boolean withinRegion) {
        isWithinRegion = withinRegion;
    }
}
