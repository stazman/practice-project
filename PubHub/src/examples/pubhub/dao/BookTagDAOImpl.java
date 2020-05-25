package examples.pubhub.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

public class BookTagDAOImpl implements BookTagDAO {
	
	Connection connection = null;
	PreparedStatement stmt = null;

	
	@Override
	public List<BookTag> getAllTagsForGivenBook(String isbn13) {
		
		List<BookTag> bookTags = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			
			String sql = "SELECT book_tags.tag_name FROM book_tags WHERE isbn_13=?";

//			This didn't work: String sql = "SELECT book_tags.tag_name FROM book_tags WHERE \"isbn_13\" = ?";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn13);
//			stmt.setString(2, tagName);
			
			
			
			ResultSet rs = stmt.executeQuery();
			
			
//		Below is a way to check that a column/columns is or is not recognized in ResultSet object:
			
//			ResultSetMetaData rsmd = rs.getMetaData();
//			String name = rsmd.getColumnName(?);
//			 
//			System.out.println(name);
			
			
			while (rs.next()) {
				BookTag bookTag = new BookTag();
				
				bookTag.setIsbn13(isbn13);

//				bookTag.setIsbn13(rs.getString("isbn_13"));
				bookTag.setTagName(rs.getString("tag_name"));
				
				bookTags.add(bookTag);
				
			}
			
			//if book.isbn_13 == book_tag.isbn_13
			
			rs.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
	
		return bookTags;
	}
	
	
	
	private void closeResources() {
		try {
			if (stmt != null)
				stmt.close();
		} catch (SQLException e) {
			System.out.println("Could not close statement!");
			e.printStackTrace();
		}
		
		try {
			if (connection != null)
				connection.close();
		} catch (SQLException e) {
			System.out.println("Could not close connection!");
			e.printStackTrace();
		}
	}
	
	
}
