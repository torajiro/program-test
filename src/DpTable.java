import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DpTable {
    public static final Integer ARTICLE_WEIGHT_FLG = 0;
    public static final Integer ARTICLE_VALUE_FLG = 1;
    public static final Integer TOTAL_WEIGHT_LOWER_LIMIT = 1;
    public static final Integer TOTAL_WEIGHT_UPPER_LIMIT = 10000;
    public static final Integer ARTICLE_WEIGHT_LOWER_LIMIT = 1;
    public static final Integer ARTICLE_WEIGHT_UPPER_LIMIT = 1000;
    public static final Integer ARTICLE_VALUE_LOWER_LIMIT = 1;
    public static final Integer ARTICLE_VALUE_UPPER_LIMIT = 1000;
    public static final Integer ARTICLE_CAPACITY_LOWER_LIMIT = 1;
    public static final Integer ARTICLE_CAPACITY_UPPER_LIMIT = 100;

    private Integer totalWeightLimit;
    private final Map<Integer, Integer> table = new HashMap<>();
    private boolean enableDebug = false;

    public DpTable(Integer weightLimit) {
        this.init(weightLimit, false);
    }

    public DpTable(Integer weightLimit, boolean isDebug) {
        this.init(weightLimit, isDebug);
    }

    private void init(Integer weightLimit, boolean isDebug) {
        if (weightLimit < TOTAL_WEIGHT_LOWER_LIMIT) {
            throw new IllegalArgumentException("TOTAL WEIGHT LOWER LIMIT");
        }

        if (weightLimit > TOTAL_WEIGHT_UPPER_LIMIT) {
            throw new IllegalArgumentException("TOTAL WEIGHT UPPER LIMIT");
        }

        this.totalWeightLimit = weightLimit;
        this.enableDebug = isDebug;
        this.table.put(0, 0);
    }

    private void update(Integer w, Integer v) {
        if (this.enableDebug)
            System.out.println(">> weight:" + w + ", value:" + v);

        Set<Integer> knownData = new HashSet<Integer>();

        for (int i = 0; i <= this.totalWeightLimit; i++) {
            Integer targetWeight = i + w;

            if (targetWeight > this.totalWeightLimit)
                continue;

            if (!this.table.containsKey(i))
                continue;

            if (knownData.contains(i))
                continue;

            Integer currentValue = this.table.get(i);
            Integer afterValue = currentValue + v;

            Integer beforeValue = 0;
            if (this.table.containsKey(targetWeight))
                beforeValue = this.table.get(targetWeight);

            if (beforeValue >= afterValue)
                continue;

            this.table.put(targetWeight, afterValue);
            knownData.add(targetWeight);
        }
    }

    private Integer maxValue() {
        Integer result = 0;
        for (int i = 0; i <= this.totalWeightLimit; i++) {
            if (this.table.containsKey(i) == false)
                continue;

            Integer currentValue = this.table.get(i);
            if (result < currentValue)
                result = currentValue;
        }

        return result;
    }

    public Integer calc(Integer capacity, ArrayList<Pair> weightAndValueList) {
        for (int i = 0; i < capacity; i++) {
            Pair article = weightAndValueList.get(i);
            Integer weight = article.getWeight();
            Integer value = article.getValue();
            this.update(weight, value);
        }

        return this.maxValue();
    }
}
