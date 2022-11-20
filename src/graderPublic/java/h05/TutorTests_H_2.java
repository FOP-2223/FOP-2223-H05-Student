package h05;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.tutor.general.assertions.Assertions2;
import org.tudalgo.algoutils.tutor.general.assertions.Context;
import org.tudalgo.algoutils.tutor.general.assertions.expected.ExpectedObject;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import static org.tudalgo.algoutils.tutor.general.assertions.Assertions2.*;

@TestForSubmission
public class TutorTests_H_2 {

    @Test
    public void test_enum() {
        H05_Tester.TRANSPORT_TYPE_CT.get().verify().assertEnumConstants(new String[]{"BICYCLE", "CAR", "VESSEL", "AIRCRAFT"});
    }

    @Test
    public void test_class() {
        Class<?> meansOfTransportClass = H05_Tester.MEANS_OF_TRANSPORT_CT.get().verify().findClass();
        assertEquals(1, meansOfTransportClass.getDeclaredConstructors().length, emptyContext(),
            result -> "Class should not have any explicitly declared constructors other than the default constructor");
    }

    @Test
    public void test_transport_type() {
        Field transport_type_field = H05_Tester.MEANS_OF_TRANSPORT_CT
            .get().resolve().resolveAttribute(H05_Tester.MEANS_OF_TRANSPORT_TRANSPORT_TYPE_AM.get());
        H05_Tester.MEANS_OF_TRANSPORT_CT.get().resolve().assertHasGetter(transport_type_field);
    }

    @Test
    public void test_let_me_move() {
        H05_Tester.MEANS_OF_TRANSPORT_LET_ME_MOVE_MT.get().verify();
    }

    private void checkMessage(String input) throws Throwable {
        Class<?> transportTypeClass = H05_Tester.TRANSPORT_TYPE_CT.get().verify().getTheClass();
        Constructor<?> constructor = transportTypeClass.getDeclaredConstructor(String.class, int.class);
        constructor.trySetAccessible();
        MethodHandle methodHandle = MethodHandles.lookup().unreflectConstructor(constructor);
        Object instance = transportTypeClass.cast(methodHandle.invoke(input, 357));

        Field transport_type_field = H05_Tester.MEANS_OF_TRANSPORT_CT
            .get().resolve().resolveAttribute(H05_Tester.MEANS_OF_TRANSPORT_TRANSPORT_TYPE_AM.get());
        Object obj = H05_Tester.MEANS_OF_TRANSPORT_CT.get().resolve().getNewRealInstance();
        ClassTester.setField(obj, transport_type_field, instance);

        Context context = contextBuilder().add("transportType", input).build();
        Assertions2.<String>testOfObjectBuilder()
            .expected(ExpectedObject.of(MessageHelper_H_2.expectedName(input), MessageHelper_H_2::matchesFormat))
            .build()
            .run(() -> (String) H05_Tester.MEANS_OF_TRANSPORT_TO_STRING_MT.get().resolveMethod().invoke(obj))
            .check(context, null);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/H2_T5.csv", numLinesToSkip = 1)
    public void test_message_normal(String input) throws Throwable {
        checkMessage(input);
    }

    /* TODO: Fix other tests, check for illegal methods
    @Test
    public void test_message_undefined() {
        Field transport_type_field =
            H05_Tester.MEANS_OF_TRANSPORT_CT.get().resolve().resolveAttribute(H05_Tester.MEANS_OF_TRANSPORT_TRANSPORT_TYPE_AM.get());
        var obj = H05_Tester.MEANS_OF_TRANSPORT_CT.get().resolve().getNewRealInstance();
        H05_Tester.MEANS_OF_TRANSPORT_CT.get().resolve().setField(obj, transport_type_field, null);
        String s = null;
        try {
            s = (String) H05_Tester.MEANS_OF_TRANSPORT_TO_STRING_MT.get().resolveMethod().invoke(obj, null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

//        String name = MessageHelper_H_2.matchesFormat(s);
//        assertEquals("undefined", name.toLowerCase(Locale.ROOT), emptyContext(), result ->
//            "TransportType null does not return undefined for toString");
    }

    @Test
    public void test_message_article() throws Throwable {
        String[] testvec = {"towel", "showel", "hovercraft", "unimog", "apple"};
        for (String testcase : testvec) {
            checkMessage(testcase);
        }
    }

    @Test
    public void test_message_special_char() throws Throwable {
        String[] testvec = {"elephan%$t", "st(i)ck"};
        for (String testcase : testvec) {
            checkMessage(testcase);
        }
    }

    @Test
    public void test_message_only_char() {
        fail(emptyContext(), result -> "Not implemented: toString() only uses char operations");
    }
    */
}
