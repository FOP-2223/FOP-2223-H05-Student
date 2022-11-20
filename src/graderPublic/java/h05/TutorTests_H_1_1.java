package h05;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
public class TutorTests_H_1_1 {
    @Test
    public void test_interface() {
        H05_Tester.ELECTRICALLY_DRIVEN_CT.get().verify();
    }

    @Test
    public void test_methods() {
        H05_Tester.ELECTRICALLY_DRIVEN_STANDARD_VOLTAGE_CHARGEABLE_MT.get().verify();
        H05_Tester.ELECTRICALLY_DRIVEN_HIGH_VOLTAGE_CHARGEABLE_MT.get().verify();
        H05_Tester.ELECTRICALLY_DRIVEN_LETS_GO_MT.get().verify();
    }
}
