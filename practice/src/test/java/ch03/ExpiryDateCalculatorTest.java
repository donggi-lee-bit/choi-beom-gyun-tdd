package ch03;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class ExpiryDateCalculatorTest {

    @Test
    void 만원_납부하면_한달_뒤_만료일이_됨() {
        assertExpiryDate(LocalDate.of(2022, 11, 1), 10_000, LocalDate.of(2022, 12, 1));
        assertExpiryDate(LocalDate.of(2022, 6, 1), 10_000, LocalDate.of(2022, 7, 1));

    }

    private void assertExpiryDate(LocalDate billingDate, int payAmount,
        LocalDate expectedExpiryDate) {
        ExpiryDateCalculator expiryDateCalculator = new ExpiryDateCalculator();
        LocalDate realExpiryDate = expiryDateCalculator.calculateExpiryDate(billingDate, payAmount);
        assertThat(realExpiryDate).isEqualTo(expectedExpiryDate);
    }
}
