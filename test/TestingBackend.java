import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestingBackend {

    @Test
    public void TestCSV(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("test.csv", dataList);

        assertEquals(2, dataList.size());
        assertEquals("Max Verstappen, RedBull", dataList.get(0));
        assertEquals("Lewis Hamilton, Ferrari", dataList.get(1));
    }

    @Test
    public void TestDriverSearch(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("Drivers.csv", dataList);
        List<String> driver = new ArrayList<>();
        driver.add(dataList.get(5));
        assertEquals(driver,backend.searchDrivers("Max"));
    }

    @Test
    public void TestTeamSearch(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("Teams.csv", dataList);
        List<String> team = new ArrayList<>();
        team.add(dataList.get(3));
        assertEquals(team,backend.searchTeams("Red"));
    }

    @Test
    public void TestDriverPersonalList(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("Drivers.csv", dataList);
        backend.addDriverToPersonalList(dataList.get(5));
        List<String> driver = new ArrayList<>();
        driver.add("Max Verstappen, Red Bull Racing, 63, 112, 65000000, 2028, 3023.5, 1st");
        assertEquals(driver,backend.getPersonalDriversList());
    }

    @Test
    public void TestTeamPersonalList(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("Teams.csv", dataList);
        backend.addTeamToPersonalList(dataList.get(3));
        List<String> team = new ArrayList<>();
        team.add("Red Bull Racing");
        assertEquals(team,backend.getPersonalTeamsList());
    }




}
