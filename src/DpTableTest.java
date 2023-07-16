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

    /**
     * 負荷テスト用データ
     * ランダムで最大容量のデータを生成
     */
    private ArrayList<Pair> testData2() {
        Integer maxCapacity = Const.ARTICLE_CAPACITY_UPPER_LIMIT;

        ArrayList<Pair> list = new ArrayList<>();
        for (int i = 0; i < maxCapacity; i++) {
            Integer weight = this.getRandomNumber(Const.ARTICLE_WEIGHT_LOWER_LIMIT, Const.ARTICLE_WEIGHT_UPPER_LIMIT);
            Integer value = this.getRandomNumber(Const.ARTICLE_VALUE_LOWER_LIMIT, Const.ARTICLE_VALUE_UPPER_LIMIT);
            list.add(this.getTestArticle(weight, value));
        }

        return list;
    }

    @Test
    public void 価値の総和の最大値が合っているかA() {
        boolean dbg = false;
        Integer correct = 16; // 正解をこの値に設定
        Integer totalWeightUpperLimit = 10; // 重さの総和の最大値をこの値に設定
        DpTable dp = new DpTable(totalWeightUpperLimit, dbg);
        ArrayList<Pair> testData = this.testData0();
        Integer capacity = testData.size();
        Integer result = dp.calc(capacity, testData);

        if (!Objects.equals(correct, result))
            fail("correct: " + correct + " / anser:" + result);

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " - passed");
        System.out.println("result: " + result + ", correct: " + correct);
    }

    @Test
    public void 価値の総和の最大値が合っているかB() {
        boolean dbg = false;
        Integer correct = 12; // 正解をこの値に設定
        Integer totalWeightUpperLimit = 9; // 重さの総和の最大値をこの値に設定
        DpTable dp = new DpTable(totalWeightUpperLimit, dbg);
        ArrayList<Pair> testData = this.testData1();
        Integer capacity = testData.size();
        Integer result = dp.calc(capacity, testData);

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
        Integer totalWeightUpperLimit = Const.TOTAL_WEIGHT_UPPER_LIMIT;

        long startTime = System.nanoTime();

        DpTable dp = new DpTable(totalWeightUpperLimit, dbg);
        ArrayList<Pair> testData = this.testData2();
        Integer capacity = testData.size();
        Integer result = dp.calc(capacity, testData);

        long endTime = System.nanoTime();

        long progressTotalTime = ((endTime - startTime) / 1000 / 1000);
        if (progressTotalTime > limitMilliSec)
            fail("制限時間を超えました。処理時間：" + progressTotalTime + " ミリ秒");

        final String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        System.out.println(methodName + " - passed");
        System.out.println("result: " + result + ", procTime(ms): " + ((endTime - startTime) / 1000 / 1000));
    }
}
