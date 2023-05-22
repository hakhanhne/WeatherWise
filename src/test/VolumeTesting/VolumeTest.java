package VolumeTesting;

import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Scanner;

import static org.junit.Assert.assertTrue;

public class VolumeTest {
    private LinkedHashMap<String, Integer> readData(String type) {
        File file = new File("mockDavid" + type);

        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String[] words = sc.nextLine().split(",");
                String value = words[0];
                Integer number = Integer.parseInt(words[1]);
                result.put(value, number);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void AllSensorsTestLoadingTime() {
        //Calculate time
        Instant start = Instant.now();

        readData("Location");
        readData("Temperature");
        readData("AQI");

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Total duration of running all sensors: " + timeElapsed);

        assertTrue("Total duration of running all sensors", timeElapsed < 5000);
    }

    @Test
    public void LocationTestLoadingTime() {
        //Calculate time
        Instant start = Instant.now();

        readData("Location");

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Total duration of location: " + timeElapsed);
        assertTrue("Total duration of location", timeElapsed < 5000);
    }

    @Test
    public void TemperatureTestLoadingTime() {
        //Calculate time
        Instant start = Instant.now();

        readData("Temperature");

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Total duration of temperature: " + timeElapsed);
        assertTrue("Total duration of temperature", timeElapsed < 5000);
    }

    @Test
    public void AQITestLoadingTime() {
        //Calculate time
        Instant start = Instant.now();

        readData("AQI");

        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();

        System.out.println("Total duration of AQI: " + timeElapsed);
        assertTrue("Total duration of AQI", timeElapsed < 5000);
    }

}
