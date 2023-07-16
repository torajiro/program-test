public class Config {
    private Integer totalWeight;

    Config(Integer totalWeight, Integer totalLowerWeightLimit, Integer totalUpperWeightLimit) {
        if (totalWeight < totalLowerWeightLimit)
            throw new IllegalArgumentException("TOTAL WEIGHT LOWER LIMIT");
        if (totalWeight > totalUpperWeightLimit)
            throw new IllegalArgumentException("TOTAL WEIGHT UPPER LIMIT");
        this.totalWeight = totalWeight;
    }

    public long getTotalWeight() {
        return this.totalWeight;
    }
}
