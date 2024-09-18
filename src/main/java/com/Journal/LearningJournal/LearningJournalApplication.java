package com.Journal.LearningJournal;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class LearningJournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningJournalApplication.class, args);
	}

	@Autowired
	private DataSource dataSource;

	@PostConstruct
	public void testConnection() throws SQLException {
		try (Connection connection = dataSource.getConnection()) {
			System.out.println("Verbindung hergestellt: " + connection);

			// SQL-Statement zum Abrufen aller Benutzer
			String sql = "SELECT * FROM benutzer";

			try (Statement statement = connection.createStatement();
				 ResultSet resultSet = statement.executeQuery(sql)) {

				// Ausgabe der Ergebnisse
				while (resultSet.next()) {
					// Hier kannst du die Spaltenwerte ausgeben, z.B.:
					int id = resultSet.getInt("benutzerid");
					String name = resultSet.getString("benutzername");
					// ... weitere Spalten
					System.out.println("Benutzer ID: " + id + ", Name: " + name);
				}
			} catch (SQLException e) {
				System.err.println("Fehler beim Abrufen der Benutzer: " + e.getMessage());
			}
		} catch (SQLException e) {
			System.err.println("Fehler beim Herstellen der Verbindung: " + e.getMessage());
		}
	}

}
