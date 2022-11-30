package ch02;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PasswordStrengthMeterTest {

    @Test
    void meetsOnlyUpperCriteria_Then_Weak() {
        PasswordStrengthMeter meter = new PasswordStrengthMeter();
        PasswordStrength result = meter.meter("abcDef");
        assertThat(PasswordStrength.WEAK).isEqualTo(result);
    }

    @Test
    void meetsAllCriteria_Then_Weak() {
        PasswordStrengthMeter meter = new PasswordStrengthMeter();
        PasswordStrength result = meter.meter("abcDef12");
        assertThat(PasswordStrength.STRONG).isEqualTo(result);
    }

    @Test
    void meetsAllCriteria_Then_Strong() {
        PasswordStrengthMeter meter = new PasswordStrengthMeter();
        PasswordStrength result = meter.meter("abcDef12");
        assertThat(PasswordStrength.STRONG).isEqualTo(result);
    }
}
