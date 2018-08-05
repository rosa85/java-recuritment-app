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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Fund fund = (Fund) o;

        if (!ID.equals(fund.ID)) return false;
        if (!name.equals(fund.name)) return false;
        return foundType == fund.foundType;
    }
}
