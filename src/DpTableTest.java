import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DpTableTest {

    public static final Integer TOTAL_WEIGHT_LOWER_LIMIT = 1;
    public static final Integer TOTAL_WEIGHT_UPPER_LIMIT = 10000;
    public static final Integer ARTICLE_WEIGHT_LOWER_LIMIT = 1;
    public static final Integer ARTICLE_WEIGHT_UPPER_LIMIT = 1000;
    public static final Integer ARTICLE_VALUE_LOWER_LIMIT = 1;
    public static final Integer ARTICLE_VALUE_UPPER_LIMIT = 1000;
    public static final Integer ARTICLE_CAPACITY_LOWER_LIMIT = 1;
    public static final Integer ARTICLE_CAPACITY_UPPER_LIMIT = 100;

    private Pair getTestArticle(Integer weight, Integer value) {
        return new Pair(weight, value);
    }

    private Integer getRandomNumber(int min, int max) {
        final Random random = new Random();
        return random.nextInt(0 + max) + min;
    }

    /** 重さの総和の最大値を10にした場合、正解が16になるテストデータを作成 */
    private ArrayList<Pair> testData0() {
        final ArrayList<Pair> list = new ArrayList<>();
        list.add(this.getTestArticle(9, 15));
        list.add(this.getTestArticle(6, 10));
        list.add(this.getTestArticle(4, 6));
        return list;
    }

    /** 重さの総和の最大値を9にした場合、正解が12になるテストデータを作成 */
    private ArrayList<Pair> testData1() {
        final ArrayList<Pair> list = new ArrayList<>();
        list.add(this.getTestArticle(1, 2));
        list.add(this.getTestArticle(3, 4));
        list.add(this.getTestArticle(5, 6));
        list.add(this.getTestArticle(7, 8));
        return list;
    }

    /** 重さの総和の最大値を2921にした場合、正解が3657162058Lになるテストデータを作成 */
    private ArrayList<Pair> testData2() {
        final ArrayList<Pair> list = new ArrayList<>();
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
        final Integer maxCapacity = DpTableTest.ARTICLE_CAPACITY_UPPER_LIMIT;
        final ArrayList<Pair> list = new ArrayList<>();
        for (int i = 0; i < maxCapacity; i++) {
            final Integer weight = this.getRandomNumber(DpTableTest.ARTICLE_WEIGHT_LOWER_LIMIT,
                    DpTableTest.ARTICLE_WEIGHT_UPPER_LIMIT);
            final Integer value = this.getRandomNumber(DpTableTest.ARTICLE_VALUE_LOWER_LIMIT,
                    DpTableTest.ARTICLE_VALUE_UPPER_LIMIT);
            list.add(this.getTestArticle(weight, value));
        }

        return list;
    }

    private long getTestResult(ArrayList<Pair> testData, Integer totalWeight) {
        final DpTable dp = new DpTable(totalWeight);
        final Integer capacity = testData.size();
        return dp.calc(capacity, testData);
    }

    @Test
    public void 価値の総和の最大値が合っているかA() {
        final long correct = 16; // 正解値
        final Integer totalWeight = 10; // 重さの総和の最大値をこの値に設定
        final long result = this.getTestResult(this.testData0(), totalWeight);

        if (!Objects.equals(correct, result))
            fail("correct: " + correct + " / anser:" + result);

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " - passed");
        System.out.println("result: " + result + ", correct: " + correct);
    }

    @Test
    public void 価値の総和の最大値が合っているかB() {
        final long correct = 12; // 正解値
        final Integer totalWeight = 9; // 重さの総和の最大値をこの値に設定
        final long result = this.getTestResult(this.testData1(), totalWeight);

        if (!Objects.equals(correct, result))
            fail("correct: " + correct + " / anser:" + result);

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " - passed");
        System.out.println("result: " + result + ", correct: " + correct);
    }

    @Test
    public void 価値の総和の最大値が合っているかC() {
        final long correct = 3657162058L; // 正解値
        final Integer totalWeight = 2921; // 重さの総和の最大値をこの値に設定
        final long result = this.getTestResult(this.testData2(), totalWeight);

        if (!Objects.equals(correct, result))
            fail("correct: " + correct + " / anser:" + result);

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " - passed");
        System.out.println("result: " + result + ", correct: " + correct);
    }

    @Test
    public void 性能試験() {
        final Integer limitMilliSec = 1000; // 制限時間を1秒に設定
        final Integer totalWeight = DpTableTest.TOTAL_WEIGHT_UPPER_LIMIT;

        final long startTime = System.nanoTime();

        final DpTable dp = new DpTable(totalWeight);
        final ArrayList<Pair> testData = this.testData3();
        final Integer capacity = testData.size();
        final long result = dp.calc(capacity, testData);

        final long endTime = System.nanoTime();

        final long progressTotalTime = ((endTime - startTime) / 1000 / 1000);
        if (progressTotalTime > limitMilliSec)
            fail("制限時間を超えました。処理時間：" + progressTotalTime + " ミリ秒");

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println("result: " + result + ", procTime(ms): " + ((endTime - startTime) / 1000 / 1000));
        System.out.println(methodName + " - passed");
    }
}
