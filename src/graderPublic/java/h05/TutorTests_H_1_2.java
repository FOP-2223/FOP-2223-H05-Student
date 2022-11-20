package h05;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
public class TutorTests_H_1_2 {

    @Test
    public void test_enum() {
        H05_Tester.FUEL_TYPE_CT.get().verify().assertEnumConstants(new String[] {"GASOLINE", "DIESEL", "LPG"});
    }

    @Test
    public void test_interface() {
        H05_Tester.FUEL_DRIVEN_CT.get().verify();
    }

    @Test
    public void test_methods() {
        H05_Tester.FUEL_DRIVEN_GET_FUEL_TYPE_MT.get().verify();
        H05_Tester.FUEL_DRIVEN_GET_AVERAGE_CONSUMPTION_MT.get().verify();
    }
}
