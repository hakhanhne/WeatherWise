package SystemTesting;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class VolumeTest_reading_weather_alrm {
    private List<Integer> readWeatherConditions(String filename){
        File file = new File(filename);
        List<Integer> result = new ArrayList();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                result.add(Integer.parseInt(sc.nextLine()));
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void reading_weather_alarms_original() {
        String file = "weather_alarms.txt";
        //Calculate time
        Instant start = Instant.now();
        List<Integer> result = readWeatherConditions(file);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("original file data entries: " + result.size());
        System.out.println("Total duration of reading original file: " + timeElapsed + " ms");
        assertTrue("Total duration of reading original file: " + timeElapsed + " ms", timeElapsed < 500);
    }

    @Test
    public void reading_weather_alarms_modified() {
        String file = "mock_weather_alarms.txt";
        //Calculate time
        Instant start = Instant.now();
        List<Integer> result = readWeatherConditions(file);
        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Modified file data entries: " + result.size());
        System.out.println("Total duration of reading modified file: " + timeElapsed + " ms");
        assertTrue("Total duration of reading modified file: " + timeElapsed + " ms", timeElapsed < 500);
    }




}
