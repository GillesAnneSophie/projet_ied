package jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.JdbcConnection;
import data.Film;

public class Queries {
	public static Film getFilm(Film film) {
		String title = film.getTitre();
		String regex = "^";
		for(int j=0 ; j<title.length() ; j++) {
			regex += "("+title.toLowerCase().charAt(j)+"|"+title.toUpperCase().charAt(j)+")";
		}
		
		try {
			String getFilmsQuery = "SELECT titre, date_sortie, genre, distributeur, budget, revenus_etats_unis, revenus_mondiaux FROM film WHERE titre REGEXP ?;";

			Connection dbConnection = JdbcConnection.getConnection();
			PreparedStatement preparedStatement = dbConnection.prepareStatement(getFilmsQuery);

			preparedStatement.setString(1, regex);

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				film.setDateSortie(result.getDate("date_sortie"));
				film.setGenre(result.getString("genre"));
				film.setDistributeur(result.getString("distributeur"));
				film.setBudget(result.getDouble("budget"));
				film.setRevenusEtatsUnis(result.getDouble("revenus_etats_unis"));
				film.setRevenusMondiaux(result.getDouble("revenus_mondiaux"));
			}

			preparedStatement.close();
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
		return film;
	}
	
	public static ArrayList<String> getCsvInformation() {
		ArrayList<String> csvInformation = new ArrayList<String>();
		try {
			String getCsvInformationQuery = "SELECT value FROM talend_info WHERE `key` REGEXP ? ORDER BY `key`;";

			Connection dbConnection = JdbcConnection.getConnection();
			PreparedStatement preparedStatement = dbConnection.prepareStatement(getCsvInformationQuery);

			preparedStatement.setString(1, "^CSV_");

			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				String value = result.getString("value");
				csvInformation.add(value);
			}

			preparedStatement.close();
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
		
		return csvInformation;
	}
	
	public static boolean isFilmTableEmpty() {
		boolean isEmpty = true;
		try {
			String getCsvInformationQuery = "SELECT COUNT(id) FROM film;";

			Connection dbConnection = JdbcConnection.getConnection();
			PreparedStatement preparedStatement = dbConnection.prepareStatement(getCsvInformationQuery);
			ResultSet result = preparedStatement.executeQuery();

			while (result.next()) {
				int count = result.getInt("COUNT(id)");
				if(count > 0) {
					isEmpty = false;
				}
			}

			preparedStatement.close();
		} catch (SQLException se) {
			System.err.println(se.getMessage());
		}
		
		return isEmpty;
	}
}
