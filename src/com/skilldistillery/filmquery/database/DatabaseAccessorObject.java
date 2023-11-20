package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {

	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	private final static String USER = "student";
	private final static String PWD = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		Connection conn = DriverManager.getConnection(URL, USER, PWD);
		String sql = "SELECT *, language.name FROM film JOIN language ON language.id = film.language_id WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, filmId);

		ResultSet filmResult = stmt.executeQuery();

		if (filmResult.next()) {

			film = new Film();

			// Here is our mapping of query columns to our object fields:
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			;
			film.setDescription(filmResult.getString("description"));
			film.setReleaseYear(filmResult.getInt("release_year"));
			film.setLanguageId(filmResult.getInt("language_id"));
			film.setRentalDuration(filmResult.getInt("rental_duration"));
			film.setRentalRate(filmResult.getDouble("rental_rate"));
			film.setLength(filmResult.getInt("length"));
			film.setReplacementCost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
			film.setSpecialFeatures(filmResult.getString("special_features"));
			film.setLanguage(filmResult.getString("name"));
			findActorsByFilmId(filmId);
			System.out.println(film);
			System.out.println("Actors featured in this film " + findActorsByFilmId(filmId));
		}

		conn.close();
		return film;

	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {

		Actor actor = null;
		Connection conn = DriverManager.getConnection(URL, USER, PWD);

		String sql = "SELECT * FROM actor WHERE id = ?";

		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setInt(1, actorId);

		ResultSet actorResult = stmt.executeQuery();

		if (actorResult.next()) {
			actor = new Actor(); // Create the object
			// Here is our mapping of query columns to our object fields:
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString("first_name"));
			actor.setLastName(actorResult.getString("last_name"));
			List<Film> theFilms = findFilmsByActorId(actorId);

			actor.setFilms(theFilms);
		}

		conn.close();
		return actor;

	}

	public List<Film> findFilmsByActorId(int actorId) {
		return null;
//		 
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> listOActors = new ArrayList<>();

		Connection conn;
		try {
			conn = DriverManager.getConnection(URL, USER, PWD);

			String sql = "SELECT *, ac.first_name, ac.last_name FROM film_actor JOIN actor ac ON actor_id = ac.id WHERE film_id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);

			stmt.setInt(1, filmId);

			ResultSet actorResult = stmt.executeQuery();
			while (actorResult.next()) {
				Actor actor = new Actor();
				actor.setId(actorResult.getInt("actor_id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
				listOActors.add(actor);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listOActors;
	}
	public List<Film> findFilmByKeyword(String Keyword) throws SQLException {
		List<Film> films = new ArrayList<>();
		Connection conn = DriverManager.getConnection(URL, USER, PWD);
		String sql = "SELECT *, language.name FROM film JOIN language ON language.id = film.language_id WHERE film.title LIKE ? OR film.description LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sql);

		stmt.setString(1, "%" + Keyword + "%");
		stmt.setString(2, "%" + Keyword + "%");
		ResultSet filmResult = stmt.executeQuery();

		while (filmResult.next()) {
			Film film = new Film();

			// Here is our mapping of query columns to our object fields:
			film.setId(filmResult.getInt("id"));
			film.setTitle(filmResult.getString("title"));
			film.setDescription(filmResult.getString("description"));
			film.setReleaseYear(filmResult.getInt("release_year"));
			film.setLanguageId(filmResult.getInt("language_id"));
			film.setRentalDuration(filmResult.getInt("rental_duration"));
			film.setRentalRate(filmResult.getDouble("rental_rate"));
			film.setLength(filmResult.getInt("length"));
			film.setReplacementCost(filmResult.getDouble("replacement_cost"));
			film.setRating(filmResult.getString("rating"));
			film.setSpecialFeatures(filmResult.getString("special_features"));
			film.setLanguage(filmResult.getString("name"));
			findActorsByFilmId(filmResult.getInt("id"));
			System.out.println(film);
			System.out.println("Actors featured in this film " + findActorsByFilmId(filmResult.getInt("id"))+ "\n");
			films.add(film);
		}

		conn.close();
		return films;

	}

}
