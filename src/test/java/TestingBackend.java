

import com.example.LoginManager;
import org.junit.Ignore;
import org.junit.Test;

import com.example.Backend;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestingBackend {

    @Test
    public void TestCSV(){
        Backend backend = new Backend(() -> "TestUser");
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/test.csv", dataList);

        assertEquals(2, dataList.size());
        assertEquals("Max Verstappen, RedBull", dataList.get(0));
        assertEquals("Lewis Hamilton, Ferrari", dataList.get(1));
    }

    @Test
    public void TestDriverSearch(){
        Backend backend = new Backend(() -> "TestUser");
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/Drivers.csv", dataList);
        List<String> driver = new ArrayList<>();
        driver.add(dataList.get(5));
        assertEquals(driver,backend.searchDrivers("Max"));
    }

    @Test
    public void TestTeamSearch(){
        Backend backend = new Backend(() -> "TestUser");
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/Teams.csv", dataList);
        List<String> team = new ArrayList<>();
        team.add(dataList.get(3));
        assertEquals(team,backend.searchTeams("Red"));
    }

    @Test
    public void TestDriverPersonalList(){
        Backend backend = new Backend(() -> "TestUser");
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/Drivers.csv", dataList);
        backend.addDriverToPersonalList(dataList.get(5));
        List<String> driver = new ArrayList<>();
        driver.add("Max Verstappen, Red Bull Racing, 63, 112, 65000000, 2028, 3023.5, 1st");
        assertEquals(driver,backend.getPersonalDriversList());
    }

    @Test
    public void TestTeamPersonalList(){
        Backend backend = new Backend(() -> "TestUser");
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/Teams.csv", dataList);
        backend.addTeamToPersonalList(dataList.get(3).split(",")[0]);
        List<String> team = new ArrayList<>();
        team.add("Red Bull Racing");
        assertEquals(team,backend.getPersonalTeamsList());
    }

    //Test Registration
     @Test
     public void TestRegister() {
         try {
             LoginManager loginManager = new LoginManager();
             loginManager.register("Username", "Password"); //Register Username and password in Login database
             List<String> dataList = new ArrayList<>();

             //Read Login file and put data into datalist
             Files.lines(Paths.get("Bookmarks/login_data.csv")).forEach(line -> {
                 String[] parts = line.split(",");
                 if (parts.length == 2) {
                     dataList.add(parts[0] + "," + parts[1]);
                 }
             });

             assertEquals("Username,e7cf3ef4f17c3999a94f2c6f612e8a888e5b1026878e4e19398b23bd38ec221a", dataList.getLast()); //Compares the entire line in database to match with the username and password
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
     }

    //Test Login feature
    @Test
    public void TestLogin() {
        try {
            Backend backend = new Backend(() -> "TestUser");
            LoginManager loginManager = new LoginManager();
            List<String> dataList = new ArrayList<>();

            //Read Login file and put data into datalist
            Files.lines(Paths.get("Bookmarks/login_data.csv")).forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    dataList.add(parts[0] + "," + parts[1]);
                }
            });

            assertTrue(loginManager.login("Username", "Password")); //Login returns a String message when successful
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
