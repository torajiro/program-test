import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DpTable {

    private long totalWeightLimit;
    private Config config;
    private final Map<Long, Long> table = new HashMap<>();
    private boolean enableDebug = false;

    public DpTable(Config config) {
        this.config = config;
        this.init(this.config, false);
    }

    public DpTable(Config config, boolean isDebug) {
        this.config = config;
        this.init(this.config, isDebug);
    }

    private void init(Config config, boolean isDebug) {
        this.totalWeightLimit = config.getTotalWeight();
        this.enableDebug = isDebug;
        this.table.put(0L, 0L);
    }

    private void update(Integer w, Integer v) {
        if (this.enableDebug)
            System.out.println(">> weight:" + w + ", value:" + v);

        Set<Long> knownData = new HashSet<Long>();

        for (long i = 0; i <= this.totalWeightLimit; i++) {
            long targetWeight = i + w;

            if (targetWeight > this.totalWeightLimit)
                continue;

            if (!this.table.containsKey(i))
                continue;

            if (knownData.contains(i))
                continue;

            long currentValue = this.table.get(i);
            long afterValue = currentValue + v;

            long beforeValue = 0;
            if (this.table.containsKey(targetWeight))
                beforeValue = this.table.get(targetWeight);

            if (beforeValue >= afterValue)
                continue;

            this.table.put(targetWeight, afterValue);
            knownData.add(targetWeight);
        }
    }

    private long getMaxValue() {
        long result = 0;
        for (long i = 0; i <= this.totalWeightLimit; i++) {
            if (this.table.containsKey(i) == false)
                continue;

            Long currentValue = this.table.get(i);
            if (result < currentValue)
                result = currentValue;
        }

        return result;
    }

    public long calc(Integer capacity, ArrayList<Pair> weightAndValueList) {
        for (int i = 0; i < capacity; i++) {
            Pair article = weightAndValueList.get(i);
            Integer weight = article.getWeight();
            Integer value = article.getValue();
            this.update(weight, value);
        }

        return this.getMaxValue();
    }
}
