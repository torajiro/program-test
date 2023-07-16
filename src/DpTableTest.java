import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DpTableTest {

    private Pair getTestArticle(Integer weight, Integer value) {
        return new Pair(weight, value);
    }

    private Integer getRandomNumber(int min, int max) {
        Random random = new Random();
        return random.nextInt(0 + max) + min;
    }

    /** 重さの総和の最大値を9にした場合、正解が12になるテストデータを作成 */
    private ArrayList<Pair> testData0() {
        ArrayList<Pair> list = new ArrayList<>();
        list.add(this.getTestArticle(9, 15));
        list.add(this.getTestArticle(6, 10));
        list.add(this.getTestArticle(4, 6));
        return list;
    }

    /** 重さの総和の最大値を9にした場合、正解が12になるテストデータを作成 */
    private ArrayList<Pair> testData1() {
        ArrayList<Pair> list = new ArrayList<>();
        list.add(this.getTestArticle(1, 2));
        list.add(this.getTestArticle(3, 4));
        list.add(this.getTestArticle(5, 6));
        list.add(this.getTestArticle(7, 8));
        return list;
    }

    /** 重さの総和の最大値を2921にした場合、正解が3657162058Lになるテストデータを作成(atcorderより借用) */
    private ArrayList<Pair> testData2() {
        ArrayList<Pair> list = new ArrayList<>();
        list.add(this.getTestArticle(325, 981421680));
        list.add(this.getTestArticle(845, 515936168));
        list.add(this.getTestArticle(371, 17309336));
        list.add(this.getTestArticle(112, 788067075));
        list.add(this.getTestArticle(96, 104855562));
        list.add(this.getTestArticle(960, 494541604));
        list.add(this.getTestArticle(161, 32007355));
        list.add(this.getTestArticle(581, 772339969));
        list.add(this.getTestArticle(248, 55112800));
        list.add(this.getTestArticle(22, 98577050));
        return list;
    }

    /**
     * 負荷テスト用データ
     * ランダムで最大容量のデータを生成
     */
    private ArrayList<Pair> testData3() {
        Integer maxCapacity = Const.ARTICLE_CAPACITY_UPPER_LIMIT;
        ArrayList<Pair> list = new ArrayList<>();
        for (int i = 0; i < maxCapacity; i++) {
            Integer weight = this.getRandomNumber(Const.ARTICLE_WEIGHT_LOWER_LIMIT, Const.ARTICLE_WEIGHT_UPPER_LIMIT);
            Integer value = this.getRandomNumber(Const.ARTICLE_VALUE_LOWER_LIMIT, Const.ARTICLE_VALUE_UPPER_LIMIT);
            list.add(this.getTestArticle(weight, value));
        }

        return list;
    }

    private long getTestResult(Boolean dbg, ArrayList<Pair> testData, Integer totalWeight,
            Integer totalWeightLowerLimit,
            Integer totalWeightUpperLimit) {
        Config config = new Config(
                totalWeight,
                totalWeightLowerLimit,
                totalWeightUpperLimit);
        DpTable dp = new DpTable(config, dbg);
        Integer capacity = testData.size();
        return dp.calc(capacity, testData);
    }

    @Test
    public void 価値の総和の最大値が合っているかA() {
        boolean dbg = false;
        long correct = 16; // 正解値
        Integer totalWeight = 10; // 重さの総和の最大値をこの値に設定
        long result = this.getTestResult(
                dbg,
                this.testData0(),
                totalWeight,
                Const.TOTAL_WEIGHT_LOWER_LIMIT,
                Const.TOTAL_WEIGHT_UPPER_LIMIT);

        if (!Objects.equals(correct, result))
            fail("correct: " + correct + " / anser:" + result);

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " - passed");
        System.out.println("result: " + result + ", correct: " + correct);
    }

    @Test
    public void 価値の総和の最大値が合っているかB() {
        boolean dbg = false;
        long correct = 12; // 正解値
        Integer totalWeight = 9; // 重さの総和の最大値をこの値に設定
        long result = this.getTestResult(
                dbg,
                this.testData1(),
                totalWeight,
                Const.TOTAL_WEIGHT_LOWER_LIMIT,
                Const.TOTAL_WEIGHT_UPPER_LIMIT);

        if (!Objects.equals(correct, result))
            fail("correct: " + correct + " / anser:" + result);

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " - passed");
        System.out.println("result: " + result + ", correct: " + correct);
    }

    @Test
    public void 価値の総和の最大値が合っているかC() {
        boolean dbg = false;
        long correct = 3657162058L; // 正解値
        Integer totalWeight = 2921; // 重さの総和の最大値をこの値に設定
        long result = this.getTestResult(
                dbg,
                this.testData2(),
                totalWeight,
                Const.TOTAL_WEIGHT_LOWER_LIMIT,
                totalWeight + 1);

        if (!Objects.equals(correct, result))
            fail("correct: " + correct + " / anser:" + result);

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " - passed");
        System.out.println("result: " + result + ", correct: " + correct);
    }

    @Test
    public void 性能試験() {
        boolean dbg = false;
        Integer limitMilliSec = 1000; // 制限時間を1秒に設定
        Integer totalWeight = Const.TOTAL_WEIGHT_UPPER_LIMIT;

        long startTime = System.nanoTime();

        Config config = new Config(
                totalWeight,
                Const.TOTAL_WEIGHT_LOWER_LIMIT,
                Const.TOTAL_WEIGHT_UPPER_LIMIT);
        DpTable dp = new DpTable(config, dbg);
        ArrayList<Pair> testData = this.testData3();
        Integer capacity = testData.size();
        long result = dp.calc(capacity, testData);

        long endTime = System.nanoTime();

        long progressTotalTime = ((endTime - startTime) / 1000 / 1000);
        if (progressTotalTime > limitMilliSec)
            fail("制限時間を超えました。処理時間：" + progressTotalTime + " ミリ秒");

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " - passed");
        System.out.println("result: " + result + ", procTime(ms): " + ((endTime - startTime) / 1000 / 1000));
    }
}
