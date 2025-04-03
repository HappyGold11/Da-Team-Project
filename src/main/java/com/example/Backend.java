package com.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Supplier;

public class Backend {
    /*
     * Handles core logic for managing driver and team data, including loading from CSV,
     * handling search, and bookmark functionality.
     */
    private List<String> drivers;
    private List<String> teams;
    private List<String> personalDriversList;
    private List<String> personalTeamsList;

    // File paths for bookmark persistence
    private final String DRIVER_BOOKMARK_FILE = "Bookmarks/bookmarked_drivers.csv";
    private final String TEAM_BOOKMARK_FILE = "Bookmarks/bookmarked_teams.csv";

    // In-memory storage of bookmarks
    private Set<String> bookmarkedDrivers = new HashSet<>();
    private Set<String> bookmarkedTeams = new HashSet<>();

    private final Supplier<String> currentUserSupplier;

    public Backend(Supplier<String> currentUserSupplier) {
        this.currentUserSupplier = currentUserSupplier;

        // create lists
        drivers = new ArrayList<>();
        teams = new ArrayList<>();
        personalDriversList = new ArrayList<>();
        personalTeamsList = new ArrayList<>();

        // Load data from CSV files into memory
        loadDataFromCSV("csv/Drivers.csv", drivers);
        loadDataFromCSV("csv/Teams.csv", teams);

        loadBookmarks();
    }

    public void setCurrentUser(String user) {
        loadBookmarks();
    }

    private String getBookmarkFilename(String base, String extension) {
        String user = currentUserSupplier.get();
        if (user == null || user.isEmpty()) return null;
        return base + "_" + user + extension;
    }

    public void loadDataFromCSV(String fileName, List<String> list) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length >= 1) {
                    list.add(line);
                }
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("Error loading CSV: " + e.getMessage());
        }
    }

    public List<String> getDrivers() { return drivers; }
    public List<String> getTeams() { return teams; }
    public void addDriverToPersonalList(String driver) {
        if (!personalDriversList.contains(driver)) personalDriversList.add(driver);
    } // add driver if not in list
    public void addTeamToPersonalList(String team) {
        if (!personalTeamsList.contains(team)) personalTeamsList.add(team);
    }
    public List<String> getPersonalDriversList() { return personalDriversList; }
    public List<String> getPersonalTeamsList() { return personalTeamsList; }

    public List<String> searchDrivers(String text) {
        if (text == null || text.trim().isEmpty()) return drivers;
        String query = text.toLowerCase();
        return drivers.stream().filter(driver -> driver.toLowerCase().contains(query)).collect(Collectors.toList());
    }

    /**
     * Searches for teams that contain the query string.
     */
    public List<String> searchTeams(String text) {
        if (text == null || text.trim().isEmpty()) return teams;
        String query = text.toLowerCase();
        return teams.stream().filter(team -> team.toLowerCase().contains(query)).collect(Collectors.toList());
    }

    /**
     * Loads bookmarked driver and team names from file.
     */
    private void loadBookmarks() {
        bookmarkedDrivers.clear();
        bookmarkedTeams.clear();

        String driverFile = getBookmarkFilename("Bookmarks/bookmarked_drivers", ".csv");
        String teamFile = getBookmarkFilename("Bookmarks/bookmarked_teams", ".csv");

        if (driverFile != null) bookmarkedDrivers = loadFromFile(driverFile);
        if (teamFile != null) bookmarkedTeams = loadFromFile(teamFile);
    }

    /**
     * Reads all lines from a file and returns them as a set.
     */
    private Set<String> loadFromFile(String filename) {
        Set<String> set = new HashSet<>();
        File file = new File(filename);
        if (!file.exists()) return set; // Return empty set if file doesn't exist

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                set.add(line.trim()); // Add each line to the set
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }

    public boolean bookmarkDriver(String name) {
        if (currentUserSupplier.get() == null) return false;
        if (bookmarkedDrivers.contains(name)) return false;
        bookmarkedDrivers.add(name);
        return appendToFile(getBookmarkFilename("Bookmarks/bookmarked_drivers", ".csv"), name);
    }

    public boolean bookmarkTeam(String name) {
        if (currentUserSupplier.get() == null) return false;
        if (bookmarkedTeams.contains(name)) return false; // already bookmarked
        bookmarkedTeams.add(name);
        return appendToFile(getBookmarkFilename("Bookmarks/bookmarked_teams", ".csv"), name); // add to file
    }

    /**
     * Appends a single line to a file.
     */
    private boolean appendToFile(String filename, String entry) {
        if (filename == null) return false;
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write(entry + "\n"); // Write entry followed by newline
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
        return overwriteFile(getBookmarkFilename("Bookmarks/bookmarked_drivers", ".csv"), bookmarkedDrivers);
    }

    public boolean unbookmarkTeam(String name) {
        if (!bookmarkedTeams.contains(name)) return false;
        bookmarkedTeams.remove(name);
        //re-wright
        return overwriteFile(getBookmarkFilename("Bookmarks/bookmarked_teams", ".csv"), bookmarkedTeams);
    }

    /**
     * Rewrites all lines of a file with new entries.
     */
    private boolean overwriteFile(String filename, Set<String> entries) {
        if (filename == null) return false;
        try (FileWriter writer = new FileWriter(filename, false)) { //overwrite
            for (String entry : entries) {
                writer.write(entry + "\n");
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Deprecated stubs
    public void register(String file, String user, String pass) {}
    public String matchLogin(String login) { return ""; }
    public String login(String user, String pass) { return ""; }
}
