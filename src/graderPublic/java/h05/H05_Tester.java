package h05;

import org.tudalgo.algoutils.reflect.AttributeMatcher;
import org.tudalgo.algoutils.reflect.ClassTester;
import org.tudalgo.algoutils.reflect.IdentifierMatcher;
import org.tudalgo.algoutils.reflect.MethodTester;
import org.tudalgo.algoutils.reflect.ParameterMatcher;
import org.tudalgo.algoutils.reflect.TestUtils;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class H05_Tester {

    public static final double minSim = 1d;

    //H1.1
    public static final Supplier<ClassTester<?>> ELECTRICALLY_DRIVEN_CT = () ->
        new ClassTester<>("h05",
            "ElectricallyDriven",
            minSim,
            Modifier.INTERFACE | Modifier.PUBLIC | Modifier.ABSTRACT);
    // Abstract needed
    public static final Supplier<MethodTester> ELECTRICALLY_DRIVEN_STANDARD_VOLTAGE_CHARGEABLE_MT = () ->
        new MethodTester(ELECTRICALLY_DRIVEN_CT.get(),
            "standardVoltageChargeable",
            minSim,
            Modifier.PUBLIC | Modifier.ABSTRACT,
            boolean.class);
    public static final Supplier<MethodTester> ELECTRICALLY_DRIVEN_HIGH_VOLTAGE_CHARGEABLE_MT = () ->
        new MethodTester(ELECTRICALLY_DRIVEN_CT.get(),
            "highVoltageChargeable",
            minSim,
            Modifier.PUBLIC | Modifier.ABSTRACT,
            boolean.class);
    public static final Supplier<MethodTester> ELECTRICALLY_DRIVEN_LETS_GO_MT = () -> new MethodTester(ELECTRICALLY_DRIVEN_CT.get(),
        "letsGo",
        minSim,
        Modifier.PUBLIC | Modifier.ABSTRACT,
        void.class,
        List.of(
            new ParameterMatcher("additionalChargeVolume", minSim, byte.class),
            new ParameterMatcher("distance", minSim, int.class)
        ));

    //H1.2
    public static final Supplier<ClassTester<?>> FUEL_TYPE_CT = () -> new ClassTester<>("h05",
        "FuelType",
        minSim,
        Modifier.PUBLIC | TestUtils.ENUM | Modifier.FINAL); // final needed??
    public static final Supplier<ClassTester<?>> FUEL_DRIVEN_CT = () -> new ClassTester<>("h05",
        "FuelDriven",
        minSim,
        Modifier.INTERFACE | Modifier.PUBLIC | Modifier.ABSTRACT);
    public static final Supplier<MethodTester> FUEL_DRIVEN_GET_FUEL_TYPE_MT = () -> new MethodTester(FUEL_DRIVEN_CT.get(),
        "getFuelType",
        minSim,
        Modifier.PUBLIC | Modifier.ABSTRACT,
        FUEL_TYPE_CT.get().findClass());

    public static final Supplier<MethodTester> FUEL_DRIVEN_GET_AVERAGE_CONSUMPTION_MT = () -> new MethodTester(FUEL_DRIVEN_CT.get(),
        "getAverageConsumption",
        minSim,
        Modifier.PUBLIC | Modifier.ABSTRACT,
        double.class,
        List.of(new ParameterMatcher("speed", minSim, double.class)));

    //H1.3
    public static final Supplier<ClassTester<?>> DRIVE_TYPE_CT = () -> new ClassTester<>("h05",
        "DriveType",
        minSim,
        Modifier.PUBLIC | TestUtils.ENUM | Modifier.FINAL);
    public static final Supplier<ClassTester<?>> HYBRID_VEHICLE_CT = () -> new ClassTester<>("h05",
        "HybridVehicle",
        minSim,
        Modifier.INTERFACE | Modifier.PUBLIC | Modifier.ABSTRACT,
        null,
        new ArrayList<>(List.of(
            new IdentifierMatcher("FuelDriven", "h05", minSim),
            new IdentifierMatcher("ElectricallyDriven", "h05", minSim)
        )));

    public static final Supplier<MethodTester> HYBRID_VEHICLE_GET_PREFERRED_DRIVE_TYPE_MT = () ->
        new MethodTester(HYBRID_VEHICLE_CT.get(),
            "getPreferredDriveType",
            minSim,
            Modifier.PUBLIC | Modifier.ABSTRACT,
            DRIVE_TYPE_CT.get().findClass());

    // H2
    public static final Supplier<ClassTester<?>> TRANSPORT_TYPE_CT = () -> new ClassTester<>("h05",
        "TransportType",
        minSim,
        Modifier.PUBLIC | TestUtils.ENUM | Modifier.FINAL);
    public static final Supplier<AttributeMatcher> MEANS_OF_TRANSPORT_TRANSPORT_TYPE_AM = () -> new AttributeMatcher(
        "transportType",
        minSim,
        Modifier.PROTECTED,
        TRANSPORT_TYPE_CT.get().findClass());
    public static final Supplier<ParameterMatcher[]> FUEL_DRIVEN_VEHICLE_CONSTRUCTOR_PARAMETER_MATCHERS = () -> new ParameterMatcher[] {
        new ParameterMatcher("fuelType", minSim, FUEL_TYPE_CT.get().findClass()),
        new ParameterMatcher("transportType", minSim, TRANSPORT_TYPE_CT.get().findClass()),
        new ParameterMatcher("fillingLevel", minSim, int.class)
    };
    public static final Supplier<ClassTester<?>> MEANS_OF_TRANSPORT_CT = () -> new ClassTester<>("h05",
        "MeansOfTransport",
        minSim,
        Modifier.PUBLIC | Modifier.ABSTRACT);
    public static final Supplier<MethodTester> MEANS_OF_TRANSPORT_TO_STRING_MT = () -> new MethodTester(MEANS_OF_TRANSPORT_CT.get(),
        "toString",
        minSim,
        Modifier.PUBLIC,
        String.class);
    public static final Supplier<MethodTester> MEANS_OF_TRANSPORT_LET_ME_MOVE_MT = () -> new MethodTester(MEANS_OF_TRANSPORT_CT.get(),
        "letMeMove",
        minSim,
        Modifier.PUBLIC | Modifier.ABSTRACT,
        int.class,
        List.of(new ParameterMatcher("distance", minSim, int.class)));
    // H3.1
    public static final Supplier<ClassTester<?>> FUEL_DRIVEN_VEHICLE_CT = () -> new ClassTester<>("h05",
        "FuelDrivenVehicle",
        minSim,
        Modifier.PUBLIC,
        MEANS_OF_TRANSPORT_CT.get().findClass(),
        new ArrayList<>(List.of(new IdentifierMatcher("FuelDriven", "h05", minSim))));
    public static final Supplier<AttributeMatcher> FUEL_DRIVEN_VEHICLE_FUEL_TYPE_AM = () -> new AttributeMatcher("fuelType",
        minSim,
        Modifier.PRIVATE,
        FUEL_DRIVEN_VEHICLE_CT.get().findClass());
    public static final Supplier<AttributeMatcher> FUEL_DRIVEN_VEHICLE_FILLING_LEVEL_AM = () -> new AttributeMatcher("fillingLevel",
        minSim,
        Modifier.PRIVATE,
        int.class);
    public static final Supplier<MethodTester> FUEL_DRIVEN_VEHICLE_FILL_UP_MT = () -> new MethodTester(FUEL_DRIVEN_VEHICLE_CT.get(),
        "fillUp",
        minSim,
        Modifier.PUBLIC,
        void.class,
        List.of(new ParameterMatcher("fillValue", minSim, int.class)));
    // H3.2
    public static final Supplier<ClassTester<?>> ELECTRIC_BOAT_CT = () ->  new ClassTester<>("h05",
        "ElectricBoat",
        minSim,
        Modifier.PUBLIC,
        MEANS_OF_TRANSPORT_CT.get().findClass(),
        new ArrayList<>(List.of(
            new IdentifierMatcher("ElectricallyDriven", "h05", minSim),
            new IdentifierMatcher("IntSupplier", "java.util.function", minSim)
        )));
    public static final Supplier<AttributeMatcher> ELECTRIC_BOAT_SPECIFIC_TYPE_AM = () ->  new AttributeMatcher("specificType",
        minSim,
        Modifier.PRIVATE,
        byte.class);
    public static final Supplier<AttributeMatcher> ELECTRIC_BOAT_CURRENT_CHARGE_AM = () ->  new AttributeMatcher("currentCharge",
        minSim,
        Modifier.PRIVATE,
        int.class);
    public static final Supplier<AttributeMatcher> ELECTRIC_BOAT_CAPACITY_AM = () ->  new AttributeMatcher("capacity",
        minSim,
        Modifier.PRIVATE,
        int.class);

    //H3.3
    public static final Supplier<ClassTester<?>> HYBRID_TYPE_1_CT = () -> new ClassTester<>("h05",
        "HybridType1",
        minSim,
        Modifier.PUBLIC,
        null,
        new ArrayList<>(List.of(
            new IdentifierMatcher("ElectricallyDriven", "h05", minSim),
            new IdentifierMatcher("FuelDriven", "h05", minSim)
        )));
    public static final Supplier<AttributeMatcher> HYBRID_TYPE_1_FUEL_TYPE_AM = () -> new AttributeMatcher(
        "fuelType",
        minSim,
        Modifier.PRIVATE | Modifier.STATIC,
        FUEL_TYPE_CT.get().findClass());
    public static final Supplier<AttributeMatcher> HYBRID_TYPE_1_AVERAGE_CONSUMPTION_AM = () -> new AttributeMatcher(
        "averageConsumption",
        minSim,
        Modifier.PRIVATE | Modifier.STATIC,
        double.class);
    public static final Supplier<AttributeMatcher> HYBRID_TYPE_1_STANDARD_VOLTAGE_CHARGEABLE_AM = () -> new AttributeMatcher(
        "standardVoltageChargeable",
        minSim,
        Modifier.PRIVATE | Modifier.STATIC,
        boolean.class);
    public static final Supplier<AttributeMatcher> HYBRID_TYPE_1_HIGH_VOLTAGE_CHARGEABLE_TYPE_AM = () -> new AttributeMatcher(
        "highVoltageChargeable",
        minSim,
        Modifier.PRIVATE | Modifier.STATIC,
        boolean.class);

    public static final Supplier<ClassTester<?>> HYBRID_TYPE_2_CT = () -> new ClassTester<>("h05",
        "HybridType2",
        minSim,
        Modifier.PUBLIC,
        MEANS_OF_TRANSPORT_CT.get().findClass(),
        new ArrayList<>(List.of(
            new IdentifierMatcher("ElectricallyDriven", "h05", minSim),
            new IdentifierMatcher("FuelDriven", "h05", minSim)
        )));
    public static final Supplier<AttributeMatcher> HYBRID_TYPE_2_HYBRID_OBJECT_AM = () -> new AttributeMatcher(
        "hybridObject",
        minSim,
        Modifier.PRIVATE,
        HYBRID_TYPE_1_CT.get().findClass());
    public static final Supplier<ParameterMatcher[]> HYBRID_TYPE_2_CONSTRUCTOR_PARAMETER_MATCHERS = () -> new ParameterMatcher[] {};


    //H3.4
    public static final Supplier<ClassTester<?>> HYBRID_TYPE_3_CT = () -> new ClassTester<>("h05",
        "HybridType3",
        minSim,
        Modifier.PUBLIC,
        null,
        new ArrayList<>(List.of(new IdentifierMatcher("HybridVehicle", "h05", minSim))));
}
