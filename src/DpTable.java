import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DpTable {

    private final Map<Long, Long> table = new HashMap<>();
    private long totalWeightLimit;

    public DpTable(long totalWeightLimit) {
        this.init(totalWeightLimit);
    }

    private void init(long totalWeightLimit) {
        this.totalWeightLimit = totalWeightLimit;
        this.table.put(0L, 0L);
    }

    private void update(Integer w, Integer v) {

        Set<Long> knownData = new HashSet<Long>();

        for (long i = 0; i <= this.totalWeightLimit; i++) {
            final long targetWeight = i + w;

            if (targetWeight > this.totalWeightLimit)
                continue;

            if (!this.table.containsKey(i))
                continue;

            if (knownData.contains(i))
                continue;

            final long currentValue = this.table.get(i);
            final long afterValue = currentValue + v;

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
            final Pair article = weightAndValueList.get(i);
            final Integer weight = article.getWeight();
            final Integer value = article.getValue();
            this.update(weight, value);
        }

        return this.getMaxValue();
    }
}
