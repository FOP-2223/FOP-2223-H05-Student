package h05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.assertions.expected.ExpectedObject;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class TutorTests_H_3_1 {

    @Test
    public void test_class() {
        H05_Tester.FUEL_DRIVEN_VEHICLE_CT.get().verify();
    }

    @Test
    public void test_getFuelType() {
        Field fuel_type_field =
            H05_Tester.FUEL_DRIVEN_VEHICLE_CT.get().resolve().resolveAttribute(H05_Tester.FUEL_DRIVEN_VEHICLE_FUEL_TYPE_AM.get());
        H05_Tester.FUEL_DRIVEN_VEHICLE_CT.get().resolve().assertHasGetter(fuel_type_field);
    }

    private void check_getAverageConsumption(int speed) {
        final double EPSILON = 0.0001;
        Object instance = H05_Tester.FUEL_DRIVEN_VEHICLE_CT.get().resolve().getNewRealInstance();
        double expected_consumption = Math.max(0, Math.min(0.1 * speed, 20));

        Context context = contextBuilder().add("speed", speed).build();
        testOfObjectBuilder()
            .expected(ExpectedObject.of("%f \u00B1%f".formatted(expected_consumption, EPSILON),
                o -> o instanceof Double d && d <= expected_consumption + EPSILON && d >= expected_consumption - EPSILON))
            .build()
            .run(() -> H05_Tester.FUEL_DRIVEN_GET_AVERAGE_CONSUMPTION_MT.get().resolveMethod().invoke(instance, speed))
            .check(context, result -> "Return value of getAverageConsumption is incorrect");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/H3_1_T3.csv")
    public void test_getAverageConsumption(int value) {
        check_getAverageConsumption(value);
    }

    private void test_fill_up(int fillValue) throws ReflectiveOperationException {
        Field filling_level_field = H05_Tester.FUEL_DRIVEN_VEHICLE_CT
            .get().resolve().resolveAttribute(H05_Tester.FUEL_DRIVEN_VEHICLE_FILLING_LEVEL_AM.get());
        Object instance = H05_Tester.FUEL_DRIVEN_VEHICLE_CT.get().resolve().getNewRealInstance();
        int filling_level_before = ThreadLocalRandom.current().nextInt();
        ClassTester.setField(instance, filling_level_field, filling_level_before);
        H05_Tester.FUEL_DRIVEN_VEHICLE_FILL_UP_MT.get().resolveMethod().invoke(instance, fillValue);
        int filling_level_after = (int) ClassTester.getFieldValue(instance,
            filling_level_field);

        Context context = contextBuilder()
            .add("fillingLevel", filling_level_before)
            .add("fillValue", fillValue)
            .build();
        assertEquals(filling_level_before + Math.max(0, fillValue), filling_level_after, context,
            result -> "Method fillUp does not work correctly");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/H3_1_T4.csv")
    public void test_filling(int value) throws ReflectiveOperationException {
        //Attribut und Getter
        Field filling_level_field = H05_Tester.FUEL_DRIVEN_VEHICLE_CT
            .get().resolve().resolveAttribute(H05_Tester.FUEL_DRIVEN_VEHICLE_FILLING_LEVEL_AM.get());
        H05_Tester.FUEL_DRIVEN_VEHICLE_CT.get().resolve().assertHasGetter(filling_level_field);

        //Fill UP
        test_fill_up(value);
    }

    private void check_letMeMove(int distance) throws ReflectiveOperationException {
        Field filling_level_field =
            H05_Tester.FUEL_DRIVEN_VEHICLE_CT.get().resolve().resolveAttribute(H05_Tester.FUEL_DRIVEN_VEHICLE_FILLING_LEVEL_AM.get());
        Object instance = H05_Tester.FUEL_DRIVEN_VEHICLE_CT.get().resolve().getNewRealInstance();
        int filling_level_before = 10;
        ClassTester.setField(instance, filling_level_field, filling_level_before);

        int returned_value = (int) H05_Tester.MEANS_OF_TRANSPORT_LET_ME_MOVE_MT.get().resolveMethod().invoke(instance, distance);
        int actual_filling_level_after = (int) ClassTester.getFieldValue(instance, filling_level_field);

        int reduceBy;
        if (distance < 0) {
            reduceBy = 0;
        } else if (distance < 10 * filling_level_before) {
            reduceBy = distance / 10;
        } else {
            reduceBy = filling_level_before;
        }

        Context context = contextBuilder()
            .add("fillingLevel", filling_level_before)
            .add("distance", distance)
            .build();
        assertEquals(filling_level_before - reduceBy, actual_filling_level_after, context, result ->
            "Method letMeMove does not reduce fillingLevel by the correct amount");
        assertEquals(reduceBy / 10, returned_value, context, result ->
            "Return value of letMeMove is incorrect");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/H3_1_T5.csv")
    public void test_letMeMove(int value) throws ReflectiveOperationException {
        check_letMeMove(value);
    }

    private void check_constructor(int fuelType, int transportType, int fillingLevel) throws Throwable {
        Constructor<?> constructor = H05_Tester.FUEL_DRIVEN_VEHICLE_CT
            .get().resolve().resolveConstructor(new ArrayList<>(Arrays.asList(H05_Tester.FUEL_DRIVEN_VEHICLE_CONSTRUCTOR_PARAMETER_MATCHERS.get())));

        Class<?> fuelTypeClass = H05_Tester.FUEL_TYPE_CT.get().verify().getTheClass();
        Constructor<?> c_fuelType = fuelTypeClass.getDeclaredConstructor(String.class, int.class);
        c_fuelType.setAccessible(true);
        MethodHandle h_fuelType = MethodHandles.lookup().unreflectConstructor(c_fuelType);
        Object fuelType_obj = fuelTypeClass.cast(h_fuelType.invoke("dummy", fuelType));

        Class<?> transportTypeClass = H05_Tester.TRANSPORT_TYPE_CT.get().verify().getTheClass();
        Constructor<?> c_transportType = transportTypeClass.getDeclaredConstructor(String.class, int.class);
        c_transportType.setAccessible(true);
        MethodHandle h_transportType = MethodHandles.lookup().unreflectConstructor(c_transportType);
        Object transportType_obj = transportTypeClass.cast(h_transportType.invoke("dummy", transportType));

        Object instance = constructor.newInstance(fuelType_obj, transportType_obj, fillingLevel);

        Field fuelType_field = H05_Tester.FUEL_DRIVEN_VEHICLE_CT
            .get().resolve().resolveAttribute(H05_Tester.FUEL_DRIVEN_VEHICLE_FUEL_TYPE_AM.get());
        fuelType_field.setAccessible(true);
        Object actual_fuelType = ClassTester.getFieldValue(instance, fuelType_field);

        Field transportType_field = H05_Tester.MEANS_OF_TRANSPORT_CT
            .get().resolve().resolveAttribute(H05_Tester.MEANS_OF_TRANSPORT_TRANSPORT_TYPE_AM.get());
        transportType_field.setAccessible(true);
        Object actual_transportType = ClassTester.getFieldValue(instance, transportType_field);

        Field fillingLevel_field = H05_Tester.FUEL_DRIVEN_VEHICLE_CT
            .get().resolve().resolveAttribute(H05_Tester.FUEL_DRIVEN_VEHICLE_FILLING_LEVEL_AM.get());
        fillingLevel_field.setAccessible(true);
        int actual_filling_level = (int) ClassTester.getFieldValue(instance, fillingLevel_field);

        Context context = contextBuilder()
            .add("fuelType", fuelType_obj)
            .add("transportType", transportType_obj)
            .add("fillingLevel", fillingLevel)
            .build();
        assertSame(fuelType_obj, actual_fuelType, context, result ->
            "Constructor of FuelDrivenVehicle did not set fuelType correctly");
        assertSame(transportType_obj, actual_transportType, context, result ->
            "Constructor of FuelDrivenVehicle did not set transportType correctly");
        assertEquals(fillingLevel, actual_filling_level, context, result ->
            "Constructor of FuelDrivenVehicle did not set fillingLevel correctly");
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/H3_1_T6.csv", numLinesToSkip = 1)
    public void test_constructor(int fuelType, int transportType, int fillingLevel) throws Throwable {
        check_constructor(fuelType, transportType, fillingLevel);
    }
}
