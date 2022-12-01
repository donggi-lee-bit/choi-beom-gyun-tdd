package ch03;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ExpiryDateCalculatorTest {

    @Test
    void 만원_납부하면_한달_뒤_만료일이_됨() {
        LocalDate billingDate = LocalDate.of(2022, 11, 1);
        int payAmount = 10_000;

        ExpiryDateCalculator expiryDateCalculator = new ExpiryDateCalculator();
        LocalDate expiryDate = expiryDateCalculator.calculateExpiryDate(billingDate, payAmount);

        LocalDate billingDate2 = LocalDate.of(2022, 5, 1);
        int payAmount2 = 10_000;

        LocalDate expiryDate2 = expiryDateCalculator.calculateExpiryDate(billingDate2, payAmount2);

        assertThat(LocalDate.of(2022, 12, 1)).isEqualTo(expiryDate);
        assertThat(LocalDate.of(2022, 6, 1)).isEqualTo(expiryDate2);
    }
}
