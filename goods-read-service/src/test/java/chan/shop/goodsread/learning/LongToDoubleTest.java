package chan.shop.goodsread.learning;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class LongToDoubleTest {

    @Test
    void longToDoubleTest() {
        long longValue = 111_111_111_111_111_111L;
        System.out.println("longValue = " + longValue);

        double doubleValue = longValue;
        System.out.println("doubleValue = " + new BigDecimal(doubleValue).toString());

        long longValue2 = (long) doubleValue;
        System.out.println("longValue2 = " + longValue2);

        // long : 64비트로 정수 표현
        // double : 64비트로 부동소수점 표현
    }
}
