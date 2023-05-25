package LocationServer;

import org.junit.Test;
import support.LocationDetails;

import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class LocationDetailTest {
    
//    @Test
//    public void testConstructorAndGetters() {
//        String name = "Vivo City Shopping Centre";
//        String location = "A";
//        String info = "Vivo City Shopping Centre is a major regional shopping centre in the southern suburb of Ho Chi Minh City, Vietnam. It is the second largest shopping centre in the southern suburbs of Ho Chi Minh City, by gross area, and contains the only H&M store in that region.";
//        List<String> services = Arrays.asList("cinema", "restaurants", "pool","shops","bowling");
//
//        LocationDetails details = new LocationDetails(name, location, info, services);
//
//        assertEquals(name, details.getName());
//        assertEquals(location, details.getLocation());
//        assertEquals(info, details.getInfo());
//        assertEquals(services, details.getServices());
//    }
//
    @Test
    public void testStringConstructor() {
        List<String> strings = Arrays.asList(
                "name: Crescent Mall",
                "location: B",
                "information: Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores.",
                "services: cinema, restaurants, shops"
        );
        
        LocationDetails details = new LocationDetails(strings);
        
        assertEquals("Crescent Mall", details.getName());
        assertEquals("B", details.getLocation());
    assertEquals(
        "Crescent Mall Shopping Centre is located 10km South of the Ho Chi Minh City central business district(CBD) and includes Banana Republic, Baskin Robins, CGV Cinema, Bobapop and over 130 specialty stores.",
        details.getInfo());
        assertEquals(Arrays.asList("cinema", "restaurants", "shops"), details.getServices());
    }
    
    @Test
    public void testToString() {
        String name = "Dam Sen Parklands";
        String location = "C";
    String info =
        "The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big lake promenade stretches the area of Dam Sen Parklands.";
        List<String> services = Arrays.asList("restaurants", "pool", "shops", "Ferris wheel");
        
        LocationDetails details = new LocationDetails(name, location, info, services);

    String expected =
        "LocationDetails [name=Dam Sen Parklands, location=C, info=The Dam Sen Parklands area was created as part of the rejuvenation of the industrial upgrade undertaken for World Expo 1988. The Parklands area is spacious with plenty of green and spaces for all ages. A big lake promenade stretches the area of Dam Sen Parklands., services=[restaurants, pool, shops, Ferris wheel]]";
        assertEquals(expected, details.toString());
    }
    
//    @Test
//    public void testSetters() {
//    LocationDetails details =
//        new LocationDetails(
//            "Ho Chi Minh City, Downtown",
//            "D",
//            "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5.",
//            Arrays.asList("restaurants", "shops", "market", "bowling"));
//
//        String newName = "Updated Location D";
//        String newLocation = "Updated City W";
//        String newInfo = "Updated info";
//        List<String> newServices = Arrays.asList("Updated Service M", "Updated Service N");
//
//        details.setName(newName);
//        details.setLocation(newLocation);
//        details.setInfo(newInfo);
//        details.setServices(newServices);
//
//        assertEquals(newName, details.getName());
//        assertEquals(newLocation, details.getLocation());
//        assertEquals(newInfo, details.getInfo());
//        assertEquals(newServices, details.getServices());
//    }
    
    @Test
    public void testGetName() {
        LocationDetails location =
                new LocationDetails(
                        "Ho Chi Minh City, Downtown",
                        "D",
                        "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5.",
                        Arrays.asList("restaurants", "shops", "market", "bowling"));
        assertEquals("Ho Chi Minh City, Downtown", location.getName());
    }
    
    @Test
    public void testGetLocation() {
        LocationDetails location =
                new LocationDetails(
                        "Ho Chi Minh City, Downtown",
                        "D",
                        "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5.",
                        Arrays.asList("restaurants", "shops", "market", "bowling"));
    
        assertEquals("D", location.getLocation());
    }
    
    @Test
    public void testGetInfo() {
        LocationDetails location =
                new LocationDetails(
                        "Ho Chi Minh City, Downtown",
                        "D",
                        "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5.",
                        Arrays.asList("restaurants", "shops", "market", "bowling"));

    assertEquals(
        "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5.",location.getInfo());
    }
    
    @Test
    public void testGetServices() {
        List<String> services = Arrays.asList("restaurants", "shops", "market", "bowling");
        LocationDetails location =
                new LocationDetails(
                        "Ho Chi Minh City, Downtown",
                        "D",
                        "The Ho Chi Minh City central business district (CBD), or 'the City' is located on a central point in district One. The point, known at its tip as Central Point, slopes upward to the north-west where 'the city' is bounded by parkland and the inner city suburb of District 3, District 4 and District 5.",
                        Arrays.asList("restaurants", "shops", "market", "bowling"));
assertEquals(services, location.getServices());
    }
    
    @Test
    public void testSetName() {
        LocationDetails location = new LocationDetails("Location Name", "Location", "Location Info", Arrays.asList("Service1", "Service2"));
        location.setName("New Name");
        assertEquals("New Name", location.getName());
    }
    
    @Test
    public void testSetLocation() {
        LocationDetails location = new LocationDetails("Location Name", "Location", "Location Info", Arrays.asList("Service1", "Service2"));
        location.setLocation("New Location");
        assertEquals("New Location", location.getLocation());
    }
    
    @Test
    public void testSetInfo() {
        LocationDetails location = new LocationDetails("Location Name", "Location", "Location Info", Arrays.asList("Service1", "Service2"));
        location.setInfo("New Info");
        assertEquals("New Info", location.getInfo());
    }
    
    @Test
    public void testSetServices() {
        List<String> services = Arrays.asList("Service1", "Service2");
        LocationDetails location = new LocationDetails("Location Name", "Location", "Location Info", Arrays.asList("OldService"));
        location.setServices(services);
        assertEquals(services, location.getServices());
    }
    
}
