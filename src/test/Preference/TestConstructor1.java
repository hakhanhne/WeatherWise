package Preference;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;
import support.Preference;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class TestConstructor1 {
    private Preference testPreference;

    @Parameter
    public static String testName;
    @Parameter(1)
    public String name;
    @Parameter(2)
    public Integer medicalCondition;
    @Parameter(3)
    public List<String> suggestions;

    @Parameters(name = "{index}: {0} {1}")
    public static Collection<Object[]> testPreferences() {
        return Arrays.asList(new Object[][]{
                {"Positive Case", "Jack", 2, Arrays.asList("when 20 suggest pool", "when 30 suggest pool",
                                                           "when APO suggest bowling",
                                                           "when weather suggest cinema")},
                {"Positive Case", "David", 3, Arrays.asList("when 16 suggest pool", "when APO suggest cinema",
                                                            "when weather suggest shops")},
                {"Empty Name","", 2, Arrays.asList("when 20 suggest pool", "when 30 suggest pool",
                                                   "when APO suggest bowling",
                                                   "when weather suggest cinema")},
                {"0 Medical condition", "Jack", 0, Arrays.asList("when 20 suggest pool", "when 30 suggest pool",
                                                                 "when APO suggest bowling",
                                                                 "when weather suggest cinema")},
                {"Empty Suggestion List", "Jack", 2, Collections.emptyList()},
                {"All parameters are empty", "", 0, Collections.emptyList()}
        });
    }

    @Before
    public void setUp() {
        // create mock preference for testing
        testPreference = new Preference(name, medicalCondition, suggestions);
    }

    @Test
    public void testGetNameShouldReturnName() {
        String actualName = testPreference.getName();
        assertEquals("Preference's Name", this.name, actualName);
    }
    @Test
    public void testGetMedicalConditionShouldReturnMedicalCondition() {
        Integer actualMedicalCondition = testPreference.getMedicalCondition();
        assertEquals("Preference's medical condition", medicalCondition, actualMedicalCondition);
    }
    @Test
    public void testGetSuggestionsShouldReturnSuggestionList() {
        List<String> actualSuggestions = testPreference.getSuggestions();
        assertEquals("Preference's suggestion list size", this.suggestions.size(), actualSuggestions.size());
        assertEquals("Preference's suggestion list content", this.suggestions, actualSuggestions);
    }
    @Test
    public void testToString() {
        String expectedString = "Preference [name="+ name +", medical condition="+ medicalCondition
                +", suggestions=" + suggestions + "]";
        assertEquals("Preference's content as string", expectedString, testPreference.toString());
    }

    @Test
    public void testSetName() {
        String newName = "newName";
        testPreference.setName(newName);
        assertEquals("Preference's name setter", newName, testPreference.getName());
    }
}