import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestingBackend {

    @Test
    public void Test1(){
        Backend backend = new Backend();
        List<String> dataList = new ArrayList<>();
        backend.loadDataFromCSV("test.csv", dataList);

        assertEquals(2, dataList.size());
        assertEquals("Max Verstappen, RedBull", dataList.get(0));
        assertEquals("Lewis Hamilton, Ferrari", dataList.get(1));
    }
}
