package h05;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission
public class TutorTests_H_3_4 {
    @Test
    public void test_HybridType3_class() {
        H05_Tester.HYBRID_TYPE_3_CT.get().verify();
    }
}
