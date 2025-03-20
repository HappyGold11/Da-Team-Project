package com.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class Backend {
    private List<String> drivers;
    private List<String> teams;
    private List<String> personalDriversList;
    private List<String> personalTeamsList;
    private final String DRIVER_BOOKMARK_FILE = "Bookmarks/bookmarked_drivers.csv";
    private final String TEAM_BOOKMARK_FILE = "Bookmarks/bookmarked_teams.csv";

    private Set<String> bookmarkedDrivers = new HashSet<>();
    private Set<String> bookmarkedTeams = new HashSet<>();

    public Backend() {
        drivers = new ArrayList<>();
        teams = new ArrayList<>();
        personalDriversList = new ArrayList<>();
        personalTeamsList = new ArrayList<>();
        
        loadDataFromCSV("csv/Drivers.csv", drivers);
        loadDataFromCSV("csv/Teams.csv", teams);

        loadBookmarks();
    }

    public void loadDataFromCSV(String fileName, List<String> list) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(","); // Assuming the CSV format is "Driver Name,Team Name"
                if (data.length >= 1) {
                    list.add(line);
                }
            }
        } catch (IOException | NullPointerException e) {
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

    public List<String> searchDrivers(String text) {
        if (text == null || text.trim().isEmpty()) {
            return drivers; // If no input, return full list
        }

        String query = text.toLowerCase();
        return drivers.stream()
                .filter(driver -> driver.toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    public List<String> searchTeams(String text) {
        if (text == null || text.trim().isEmpty()) {
            return teams; // If no input, return full list
        }

        String query = text.toLowerCase();
        return teams.stream()
                .filter(team -> team.toLowerCase().contains(query))
                .collect(Collectors.toList());
    }

    // Bookmarking Code
    private void loadBookmarks() {
        bookmarkedDrivers = loadFromFile(DRIVER_BOOKMARK_FILE);
        bookmarkedTeams = loadFromFile(TEAM_BOOKMARK_FILE);
    }

    private Set<String> loadFromFile(String filename) {
        Set<String> set = new HashSet<>();
        File file = new File(filename);
        if (!file.exists()) return set;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                set.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public boolean bookmarkDriver(String name) {
        if (bookmarkedDrivers.contains(name)) return false;

        bookmarkedDrivers.add(name);
        return appendToFile(DRIVER_BOOKMARK_FILE, name);
    }

    public boolean bookmarkTeam(String name) {
        if (bookmarkedTeams.contains(name)) return false;

        bookmarkedTeams.add(name);
        return appendToFile(TEAM_BOOKMARK_FILE, name);
    }

    private boolean appendToFile(String filename, String entry) {
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(entry + "\n");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isDriverBookmarked(String name) {
        return bookmarkedDrivers.contains(name);
    }

    public boolean isTeamBookmarked(String name) {
        return bookmarkedTeams.contains(name);
    }

    public boolean unbookmarkDriver(String name) {
        if (!bookmarkedDrivers.contains(name)) return false;

        bookmarkedDrivers.remove(name);
        return overwriteFile(DRIVER_BOOKMARK_FILE, bookmarkedDrivers);
    }

    public boolean unbookmarkTeam(String name) {
        if (!bookmarkedTeams.contains(name)) return false;

        bookmarkedTeams.remove(name);
        return overwriteFile(TEAM_BOOKMARK_FILE, bookmarkedTeams);
    }

    private boolean overwriteFile(String filename, Set<String> entries) {
        try (FileWriter writer = new FileWriter(filename, false)) {
            for (String entry : entries) {
                writer.write(entry + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    //Temp Methods to be finished later
    public void register(String file, String user, String pass) {}

    public String matchLogin(String login) {return "";}

    public String login(String user, String pass) {return "";}
}
