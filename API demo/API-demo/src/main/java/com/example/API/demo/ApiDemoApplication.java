package com.example.API.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiDemoApplication.class, args);
        getSteamStats();
        getGameDetails();
        System.exit(0);
    }

    /**
     * Display the amount of players currently on Steam and the number of them
     * currently playing a game
     */
    public static void getSteamStats() {
        try {
            String url = "https://www.valvesoftware.com/about/stats";
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String steamStats = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(steamStats);

            String onlineUsers = root.findValue("users_online").asText();
            String usersPlaying = root.findValue("users_ingame").asText();

            System.out.println("\nNumber of players currently on Steam");
            System.out.println("Users online: " + onlineUsers);
            System.out.println("Users playing games: " + usersPlaying);

        } catch (JsonProcessingException ex) {
            System.out.println("Error in getting Steam stats");
        }

    }

    /**
     * Displays a list of featured games on steam
     */
    public static void getGameDetails() {

        try {
            
            //The app id of 10 random steam games
            int[] appIDs = {1086940, 686810, 1649240, 230410, 1693980, 1238860,
                246620, 960090, 232090, 2630};
            
            int gameID = appIDs[(int)(Math.random() * 10)];
            String ID = Integer.toString(gameID);
            
            String url = "https://store.steampowered.com/api/appdetails?appids=" + ID;
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper mapper = new ObjectMapper();

            String steamFeatured = restTemplate.getForObject(url, String.class);
            JsonNode root = mapper.readTree(steamFeatured);
            
            System.out.println("\nInfo on a Steam game");
            System.out.println();
     
                String gameName = root.findValue("name").asText();
                String age = root.findValue("required_age").asText();
                String freeOrNot = root.findValue("is_free").asText();
                String description = root.findValue("short_description").asText();
                               
                System.out.println("Name of game: " + gameName);
                System.out.println("Age to play: " + age);
                
                if (freeOrNot.equalsIgnoreCase("false")){
                    System.out.println("Is the game free: No");
                }else{
                    System.out.println("Is the game free: Yes");
                }
                
                System.out.println("Description: \n" + description);

        } catch (JsonProcessingException ex) {
            System.out.println("Error in obtaining featured games");

        }

    }
}
