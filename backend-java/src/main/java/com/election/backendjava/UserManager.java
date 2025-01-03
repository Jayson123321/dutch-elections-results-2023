package com.election.backendjava;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.*;
import java.util.Scanner;

public class UserManager {

    // Laad de environment-variabelen uit een .env-bestand
    private static final Dotenv dotenv = Dotenv.configure()
            .directory("backend-java") // de backend-map
            .filename(".env")     // Bestandsnaam
            .load();

    // Laad databasegegevens uit de environment-variabelen
    private static final String URL = dotenv.get("DB_URL");
    private static final String USERNAME = dotenv.get("DB_USERNAME");
    private static final String PASSWORD = dotenv.get("DB_PASSWORD");

    public static void main(String[] args) {
        // Debug: Print de huidige werkdirectory
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        Scanner scanner = new Scanner(System.in);

        System.out.println("Choose an action: ");
        System.out.println("1. Add a new user");
        System.out.println("2. Retrieve all users");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.println("Enter username:");
                String username = scanner.nextLine();

                System.out.println("Enter password:");
                String password = scanner.nextLine();

                System.out.println("Enter role (e.g., 'admin', 'user'):");
                String role = scanner.nextLine();

                addUser(username, password, role);
                break;

            case 2:
                getAllUsers();
                break;

            default:
                System.out.println("Invalid choice.");
        }

        scanner.close();
    }

    public static void addUser(String username, String password, String role) {
        String insertUserSQL = "INSERT INTO \"user\" (username, password, role, create_time) VALUES (?, ?, ?, NOW())";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(insertUserSQL)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, role);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("User added successfully!");
            } else {
                System.out.println("Failed to add user.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Database connection failed!");
        }
    }

    public static void getAllUsers() {
        String selectAllUsersSQL = "SELECT id, username, role, create_time FROM \"user\"";

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllUsersSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            System.out.printf("%-5s %-20s %-10s %-25s%n", "ID", "Username", "Role", "Create Time");
            System.out.println("---------------------------------------------------------------");

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String role = resultSet.getString("role");
                Timestamp createTime = resultSet.getTimestamp("create_time");

                System.out.printf("%-5d %-20s %-10s %-25s%n", id, username, role, createTime);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to retrieve users.");
        }
    }
}