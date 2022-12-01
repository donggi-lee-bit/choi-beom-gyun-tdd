package ch03;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ExpiryDateCalculatorTest {

    @Test
    void 만원_납부하면_한달_뒤_만료일이_됨() {
        assertExpiryDate(PayData.builder()
            .billingDate(LocalDate.of(2022, 11, 1))
            .payAmount(10_000)
            .build(),
            LocalDate.of(2022, 12, 1));

        assertExpiryDate(PayData.builder()
                .billingDate(LocalDate.of(2022, 6, 1))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2022, 7, 1));
    }

    @Test
    void 납부일과_한달뒤_일자가_같지_않음() {
        assertExpiryDate(PayData.builder()
                .billingDate(LocalDate.of(2019, 1, 31))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2019, 2, 28));

        assertExpiryDate(PayData.builder()
                .billingDate(LocalDate.of(2019, 5, 31))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2019, 6, 30));

        assertExpiryDate(PayData.builder()
                .billingDate(LocalDate.of(2020, 1, 31))
                .payAmount(10_000)
                .build(),
            LocalDate.of(2020, 2, 29));
    }

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator expiryDateCalculator = new ExpiryDateCalculator();
        LocalDate realExpiryDate = expiryDateCalculator.calculateExpiryDate(payData);
        assertThat(realExpiryDate).isEqualTo(expectedExpiryDate);
    }
}
