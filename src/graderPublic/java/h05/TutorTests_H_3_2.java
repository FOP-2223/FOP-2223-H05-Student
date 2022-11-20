package h05;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.tutor.general.assertions.Context;

import java.lang.reflect.Field;
import java.util.function.IntSupplier;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class TutorTests_H_3_2 {

    @Test
    public void test_class() {
        H05_Tester.ELECTRIC_BOAT_CT.get().verify();
    }

    @Test
    public void test_attributes() {
        Field specificType_field = H05_Tester.ELECTRIC_BOAT_CT
            .get().resolve().resolveAttribute(H05_Tester.ELECTRIC_BOAT_SPECIFIC_TYPE_AM.get());
        Field currentCharge_field = H05_Tester.ELECTRIC_BOAT_CT
            .get().resolve().resolveAttribute(H05_Tester.ELECTRIC_BOAT_CURRENT_CHARGE_AM.get());
        Field capacity_field = H05_Tester.ELECTRIC_BOAT_CT
            .get().resolve().resolveAttribute(H05_Tester.ELECTRIC_BOAT_CAPACITY_AM.get());

        H05_Tester.ELECTRIC_BOAT_CT.get().resolve().assertHasGetter(specificType_field);
        H05_Tester.ELECTRIC_BOAT_CT.get().resolve().assertHasGetter(currentCharge_field);
        H05_Tester.ELECTRIC_BOAT_CT.get().resolve().assertHasGetter(capacity_field);
    }

    @Test
    public void test_getAsInt() {
        ClassTester<?> electric_boat = H05_Tester.ELECTRIC_BOAT_CT.get().resolve();
        Field currentCharge_field = electric_boat.resolveAttribute(H05_Tester.ELECTRIC_BOAT_CURRENT_CHARGE_AM.get());
        Field capacity_field = electric_boat.resolveAttribute(H05_Tester.ELECTRIC_BOAT_CAPACITY_AM.get());

        Object instance = electric_boat.resolveInstance();

        int capacity = (int) electric_boat.setFieldRandom(capacity_field);
        int currentCharge = (int) electric_boat.setFieldRandom(currentCharge_field);
        int returned_value = ((IntSupplier) instance).getAsInt();

        Context context = contextBuilder()
            .add("capacity", capacity)
            .add("currentCharge", currentCharge)
            .build();
        assertEquals(capacity - currentCharge, returned_value, context, result ->
            "getAsInt did not return the expected value");
    }
}
