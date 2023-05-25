package SystemTesting;

import org.junit.Test;
import support.LocationDetails;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class VolumeTest_reading_CityInfo {
    private static List<LocationDetails> readCityInfo(String filename) {
        List<LocationDetails> result = new ArrayList<>();
        File file = new File(filename);
        int count = 0;
        try {
            Scanner sc = new Scanner(file);
            List<String> strings = new ArrayList<>();
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.equals("***")) {
                    strings.add(line);
                } else {
                    LocationDetails locationDetails = new LocationDetails(strings);
                    result.add(locationDetails);
                    strings = new ArrayList<>();
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void reading_weather_alarms_original() {
        String file = "CityInfo";
        //Calculate time
        Instant start = Instant.now();
        List<LocationDetails> result = readCityInfo(file);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("original file data entries: " + result.size());
        System.out.println("Total duration of reading original file: " + timeElapsed + " ms");
        assertTrue("Total duration of reading original file: " + timeElapsed + " ms", timeElapsed < 500);
    }

    @Test
    public void reading_weather_alarms_modified() {
        String file = "mockCityInfo";
        //Calculate time
        Instant start = Instant.now();
        List<LocationDetails> result = readCityInfo(file);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Modified file data entries: " + result.size());
        System.out.println("Total duration of reading modified file: " + timeElapsed + " ms");
        assertTrue("Total duration of reading modified file: " + timeElapsed + " ms", timeElapsed < 500);
    }




}
