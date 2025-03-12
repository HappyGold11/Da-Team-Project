import javax.swing.*;
import java.io.*;
import java.util.*;

public class Backend {
    private List<String> drivers;
    private List<String> teams;
    private List<String> personalDriversList;
    private List<String> personalTeamsList;

    public Backend() {
        drivers = new ArrayList<>();
        teams = new ArrayList<>();
        personalDriversList = new ArrayList<>();
        personalTeamsList = new ArrayList<>();
        loadDataFromCSV("Drivers.csv",drivers); // Ensure the CSV is in the same directory
        loadDataFromCSV("Teams.csv",teams);
    }

    void loadDataFromCSV(String fileName,List<String> list) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Assuming the CSV format is "Driver Name,Team Name"
                if (data.length >= 1) {
                    list.add(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        }
    }

    public List<String> getDrivers() {
        return drivers;
    }

    public List<String> getTeams() {
        return teams;
    }

    public void addDriverToPersonalList(String driver) {
        if (!personalDriversList.contains(driver)) {
            personalDriversList.add(driver);
        }
    }

    public void addTeamToPersonalList(String team) {
        if (!personalTeamsList.contains(team)) {
            personalTeamsList.add(team);
        }
    }

    public List<String> getPersonalDriversList() {
        return personalDriversList;
    }

    public List<String> getPersonalTeamsList() {
        return personalTeamsList;
    }

    public void searchDrivers(String text) {
        return;
    }

    public void searchTeams(String text) {
        return;
    }
    
}

