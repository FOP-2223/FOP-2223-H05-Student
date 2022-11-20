package h05;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class TutorTests_H_3_3 {

    @Test
    public void test_HybridType1_class() {
        H05_Tester.HYBRID_TYPE_1_CT.get().verify();
    }

    @Test
    public void test_HybridType1_attributes() {
        H05_Tester.HYBRID_TYPE_1_CT.get().resolve().resolveAttribute(H05_Tester.HYBRID_TYPE_1_FUEL_TYPE_AM.get());
        H05_Tester.HYBRID_TYPE_1_CT.get().resolve().resolveAttribute(H05_Tester.HYBRID_TYPE_1_AVERAGE_CONSUMPTION_AM.get());
        H05_Tester.HYBRID_TYPE_1_CT.get().resolve().resolveAttribute(H05_Tester.HYBRID_TYPE_1_HIGH_VOLTAGE_CHARGEABLE_TYPE_AM.get());
        H05_Tester.HYBRID_TYPE_1_CT.get().resolve().resolveAttribute(H05_Tester.HYBRID_TYPE_1_STANDARD_VOLTAGE_CHARGEABLE_AM.get());
    }

    @Test
    public void test_HybridType2_class() {
        H05_Tester.HYBRID_TYPE_2_CT.get().verify();
    }

    @Test
    public void test_HybridType2_hybrid_Object() throws InvocationTargetException, InstantiationException, IllegalAccessException {
        Field hybridObject_field = H05_Tester.HYBRID_TYPE_2_CT
            .get().resolve().resolveAttribute(H05_Tester.HYBRID_TYPE_2_HYBRID_OBJECT_AM.get());
        hybridObject_field.setAccessible(true);

        Constructor<?> constructor = H05_Tester.HYBRID_TYPE_2_CT
            .get().resolve().resolveConstructor(H05_Tester.HYBRID_TYPE_2_CONSTRUCTOR_PARAMETER_MATCHERS.get());
        Object instance = constructor.newInstance();
        Object hybridObject = hybridObject_field.get(instance);

        assertNotEquals(null, hybridObject, emptyContext(), result -> "hybridObject was not set by the constructor");
    }
}
