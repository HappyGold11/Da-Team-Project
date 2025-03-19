

import org.junit.Test;

import com.example.Backend;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestingBackend {

    @Test
    public void TestCSV(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/test.csv", dataList);

        assertEquals(2, dataList.size());
        assertEquals("Max Verstappen, RedBull", dataList.get(0));
        assertEquals("Lewis Hamilton, Ferrari", dataList.get(1));
    }

    @Test
    public void TestDriverSearch(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/Drivers.csv", dataList);
        List<String> driver = new ArrayList<>();
        driver.add(dataList.get(5));
        assertEquals(driver,backend.searchDrivers("Max"));
    }

    @Test
    public void TestTeamSearch(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/Teams.csv", dataList);
        List<String> team = new ArrayList<>();
        team.add(dataList.get(3));
        assertEquals(team,backend.searchTeams("Red"));
    }

    @Test
    public void TestDriverPersonalList(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/Drivers.csv", dataList);
        backend.addDriverToPersonalList(dataList.get(5));
        List<String> driver = new ArrayList<>();
        driver.add("Max Verstappen, Red Bull Racing, 63, 112, 65000000, 2028, 3023.5, 1st");
        assertEquals(driver,backend.getPersonalDriversList());
    }

    @Test
    public void TestTeamPersonalList(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("csv/Teams.csv", dataList);
        backend.addTeamToPersonalList(dataList.get(3));
        List<String> team = new ArrayList<>();
        team.add("Red Bull Racing");
        assertEquals(team,backend.getPersonalTeamsList());
    }

    //Test Registration
    @Test
    public void TestRegister() {
        try {
            Backend backend = new Backend();
            backend.register("csv/Login.csv", "Username", "Password"); //Register Username and password in Login database
            String filteredLogin = backend.matchLogin("Username, Password"); //Takes a String value and searches in Login.csv for a match
            assertEquals("Username, Password", filteredLogin); //Compares the entire line in database to match with the username and password
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Test Login feature
    @Test
    public void TestLogin() {
        try {
            Backend backend = new Backend();
            List<String> loginInfo = new ArrayList<>();
            backend.loadDataFromCSV("csv/Login.csv", loginInfo);
            for (String line : loginInfo) {
                String[] info = line.split(",");
                assertEquals("Successfully Logged In", backend.login(info[0], info[1])); //Login returns a String message when successful
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
