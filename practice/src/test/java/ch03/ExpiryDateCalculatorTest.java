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

    @Test
    void 첫_납부일과_만료일의_일자가_같지_않을_때_1만원_납부하면_첫_납부일_기준으로_다음_만료일을_정함() {
        PayData payData = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 1, 31))
            .billingDate(LocalDate.of(2019, 2, 28))
            .payAmount(10_000)
            .build();

        PayData payData2 = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 1, 30))
            .billingDate(LocalDate.of(2019, 2, 28))
            .payAmount(10_000)
            .build();

        PayData payData3 = PayData.builder()
            .firstBillingDate(LocalDate.of(2019, 5, 31))
            .billingDate(LocalDate.of(2019, 6, 30))
            .payAmount(10_000)
            .build();

        assertExpiryDate(payData, LocalDate.of(2019, 3, 31));
        assertExpiryDate(payData2, LocalDate.of(2019, 3, 30));
        assertExpiryDate(payData3, LocalDate.of(2019, 7, 31));
    }

    @Test
    void 이만원_이상_납부하면_비례하여_만료일_계산() {
        assertExpiryDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(20_000)
                .build(),
            LocalDate.of(2019, 5, 1)
        );

        assertExpiryDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(30_000)
                .build(),
            LocalDate.of(2019, 6, 1)
        );

        assertExpiryDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(50_000)
                .build(),
            LocalDate.of(2019, 8, 1)
        );

        assertExpiryDate(
            PayData.builder()
                .billingDate(LocalDate.of(2019, 3, 1))
                .payAmount(70_000)
                .build(),
            LocalDate.of(2019, 10, 1)
        );
    }

    @Test
    void 첫_납부일과_만료일_일자가_다를_때_이만원_이상_납부() {
        assertExpiryDate(
            PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 1, 31))
                .billingDate(LocalDate.of(2019, 2, 28))
                .payAmount(20_000)
                .build(),
            LocalDate.of(2019, 4, 30)
        );

        assertExpiryDate(
            PayData.builder()
                .firstBillingDate(LocalDate.of(2019, 3, 31))
                .billingDate(LocalDate.of(2019, 4, 30))
                .payAmount(30_000)
                .build(),
            LocalDate.of(2019, 7, 31)
        );
    }

    private void assertExpiryDate(PayData payData, LocalDate expectedExpiryDate) {
        ExpiryDateCalculator expiryDateCalculator = new ExpiryDateCalculator();
        LocalDate realExpiryDate = expiryDateCalculator.calculateExpiryDate(payData);
        assertThat(realExpiryDate).isEqualTo(expectedExpiryDate);
    }
}
