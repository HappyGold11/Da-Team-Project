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

    public Backend() {
        drivers = new ArrayList<>();
        teams = new ArrayList<>();
        personalDriversList = new ArrayList<>();
        personalTeamsList = new ArrayList<>();
        
        loadDataFromCSV("csv/Drivers.csv", drivers);
        loadDataFromCSV("csv/Teams.csv", teams);
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
}
