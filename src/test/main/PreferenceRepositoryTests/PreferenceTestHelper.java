//package main.PreferenceRepositoryTests;
//
//import helper.SensorData;
//import helper.User;
//import support.Preference;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//public class PreferenceTestHelper {
//    static List<Preference> multiplePreferences = Arrays.asList(
//            new Preference("Jack", 2,Arrays.asList(
//                    "when 20 suggest shops",
//                    "when 30 suggest pool",
//                    "when APO suggest bowling",
//                    "when weather suggest cinema"
//            )),
//            new Preference("David", 3, Arrays.asList(
//                    "when 16 suggest pool",
//                    "when APO suggest cinema",
//                    "when weather suggest shops"
//            ))
//
//    );
//
//    public static Object[][] generateTestDataAmountCase() {
//        return new Object[][] {
//                // Test case 1: Empty preferences
//                {"Empty pref list", "Jack", new ,
//                        new User(0, new int[0], 0, 0, new SensorData(), 0, false, false)
//                },
//                // Test case 2: Multiple users
//                // Jack
//                {"Non-empty pref list", "Jack", multiplePreferences,
//                        new User(2, new int[]{20, 30}, 0, 0, new SensorData(), 0, false, false)
//                },
//                // David
//                {"Non-empty pref list", "David", multiplePreferences,
//                        new User(3, new int[]{16}, 0, 0, new SensorData(), 0, false, false)
//                },
//                // Test case 3: Multiple users - no match
//                {"Non-empty pref list but no match", "John", multiplePreferences,
//                        new User(0, new int[0], 0, 0, new SensorData(), 0, false, false)
//                }
//        };
//    }
//}
