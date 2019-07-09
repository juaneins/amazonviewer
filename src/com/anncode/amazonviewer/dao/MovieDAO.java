/**
 * 
 */
package com.anncode.amazonviewer.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.anncode.amazonviewer.db.IDBConnection;
import com.anncode.amazonviewer.model.Movie;
import static com.anncode.amazonviewer.db.DataBase.*;

/**
 * @author Usuario
 *
 */
public interface MovieDAO extends IDBConnection{
	
	default Movie setMovieViewed(Movie movie) {
		
		try (Connection connection = connectToDB()) {
			Statement statement = connection.createStatement();
			String query = "insert into viewed (id_material, id_element, id_user) values(" + ID_TMATERIALS[0] + ","
					+ movie.getId() + "," + TUSER_IDUSUARIO + ")";
			if (statement.executeUpdate(query) > 0) {
				System.out.println("Marcado en visto");
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		
		return movie;
	}
	
	default ArrayList<Movie> read() {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		
		try (Connection connection = connectToDB()) {
			String query = "Select * from movie";			
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				Movie movie = new Movie(
						rs.getString("title"), 
						rs.getString("genre"),
						rs.getString("creator"), 
						Integer.valueOf(rs.getString("duration")), 
						Short.valueOf(rs.getString("year")));
				movie.setId(Integer.valueOf(rs.getString("id")));
				movie.setViewed(getMovieViewed(preparedStatement, connection, Integer.valueOf(rs.getString("id"))));
				movies.add(movie);
			}
		} catch (SQLException e) {
			// TODO: handle exception
		}
		
		return movies;
	}
	
	private boolean getMovieViewed(PreparedStatement preparedStatement, Connection connection, int idMovie) {
		boolean viewed = false;
		String query = "select * from viewed where id_material = ? and id_element = ? and  id_user = ?";
		ResultSet rs = null;
		
		
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, ID_TMATERIALS[0]);
			preparedStatement.setInt(2, idMovie);
			preparedStatement.setInt(3, TUSER_IDUSUARIO);
			
			rs = preparedStatement.executeQuery();
			viewed = rs.next();
			
		} catch (SQLException e) {
			// TODviewedO Auto-generated catch block
			e.printStackTrace();
		}
		return viewed;
	}

}
