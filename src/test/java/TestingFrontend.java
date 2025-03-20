import com.example.Frontend;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class TestingFrontend {
    private Frontend frontend;

    @Before
    public void setUp() throws InvocationTargetException, InterruptedException {
        // Run GUI-related setup
        SwingUtilities.invokeAndWait(() -> {
            frontend = new Frontend();
        });
    }

    @Test
    @Ignore public void testDriverSearchUpdatesListModel() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // Simulate a search for "Max"
            frontend.searchDrivers("Max");

            DefaultListModel<String> model = getDriverListModel(frontend);
            assertFalse(model.isEmpty());
            assertTrue(model.getElementAt(0).toLowerCase().contains("max"));
        });
    }

    @Test
    @Ignore public void testTeamSearchUpdatesListModel() throws Exception {
        SwingUtilities.invokeAndWait(() -> {
            // Simulate a search for "Red"
            frontend.searchTeams("Red");

            DefaultListModel<String> model = getTeamListModel(frontend);
            assertFalse(model.isEmpty());
            assertTrue(model.getElementAt(0).toLowerCase().contains("red"));
        });
    }

    // Helper methods to access private fields

    private DefaultListModel<String> getDriverListModel(Frontend frontend) {
        try {
            java.lang.reflect.Field field = Frontend.class.getDeclaredField("driverListModel");
            field.setAccessible(true);
            return (DefaultListModel<String>) field.get(frontend);
        } catch (Exception e) {
            throw new RuntimeException("Could not access driverListModel", e);
        }
    }

    private DefaultListModel<String> getTeamListModel(Frontend frontend) {
        try {
            java.lang.reflect.Field field = Frontend.class.getDeclaredField("teamListModel");
            field.setAccessible(true);
            return (DefaultListModel<String>) field.get(frontend);
        } catch (Exception e) {
            throw new RuntimeException("Could not access teamListModel", e);
        }
    }
}