package biz.application.Funds;


public class Fund {
    private final Integer ID;
    private final String name;
    private final FundType foundType;

    public Fund(Integer ID, String name, FundType foundType) {
        this.ID = ID;
        this.name = name;
        this.foundType = foundType;
    }

    public Integer getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public FundType getFoundType() {
        return foundType;
    }
}
