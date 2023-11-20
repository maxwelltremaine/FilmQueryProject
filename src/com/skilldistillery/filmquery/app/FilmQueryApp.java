package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {
	Scanner input = new Scanner(System.in);
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//		app.test();
//		app.findActorById();
		app.launch();
//		app.findFilmById();
//		app.findFilmByKeyWord();
	}

	private void test() {
		Film film;
		try {
			film = db.findFilmById(1);
			System.out.println(film);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void launch() {
		int userInput;
		do {
			startUserInterface();
			userInput = input.nextInt();
			input.nextLine();

			switch (userInput) {
			case 1:
				System.out.println("please insert the keyword you would like to search with");
				String userKeyword = input.nextLine();
				findFilmByKeyWord(userKeyword);
				break;
			case 2:
				System.out.println("Please insert the film ID you would like to search with");
				int filmId = input.nextInt();
				input.nextLine();
				findFilmById(filmId);
				break;
			case 3:
				System.out.println("Thank you for using our search interface, have a nice day!");
				break;
			default:System.out.println("Please input a valid value.");
			}
		} while (userInput != 3);
	}

	private void startUserInterface() {
		System.out.println("---------------");
		System.out.println("Please choose whether you would like to look up a film by ID or by keyword");
		System.out.println("press 1 for Keyword");
		System.out.println("press 2 for film id");
		System.out.println("press 3 to exit the program");
		System.out.println("---------------");

	}

	private void findFilmById(int filmId) {
		Film film;
		try {
			film = db.findFilmById(filmId);
			if (film == null) {
				System.out.println("Like zoinks scoob no films here besides us chickens");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void findFilmByKeyWord(String userKeyword) {
		List<Film> film;
		try {
			film = db.findFilmByKeyword(userKeyword);
			if (film.isEmpty()) {
				System.out.println("If I'm here and you're here and there's nothing here WHOS WATCHING THE FILMS?!?!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
