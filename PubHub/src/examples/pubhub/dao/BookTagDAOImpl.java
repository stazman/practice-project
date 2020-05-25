package examples.pubhub.dao;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import examples.pubhub.model.Book;
import examples.pubhub.model.BookTag;
import examples.pubhub.utilities.DAOUtilities;

public class BookTagDAOImpl implements BookTagDAO {
	
	Connection connection = null;
	PreparedStatement stmt = null;

	
	@Override
	public List<BookTag> getAllTagsForGivenBook(String isbn13, String tagName) {
		
		List<BookTag> bookTags = new ArrayList<>();
		
		try {
			connection = DAOUtilities.getConnection();
			
			String sql = "SELECT book_tags.tag_name FROM book_tags WHERE isbn_13=?";

//			This didn't work: String sql = "SELECT book_tags.tag_name FROM book_tags WHERE \"isbn_13\" = ?";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, isbn13);
			stmt.setString(2, tagName);
			
			
			
			ResultSet rs = stmt.executeQuery();
			
//		Below shows that isbn_13 column is not recognized in ResultSet object:
			
//			ResultSetMetaData rsmd = rs.getMetaData();
//			String name = rsmd.getColumnName(1);
//			 
//			System.out.println(name);
			
			
			while (rs.next()) {
				BookTag bookTag = new BookTag();

				bookTag.setIsbn13(rs.getString("isbn_13"));
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
	
	
	public List<BookTag> getBooksWithGivenTag(String isbn13, String tagName) {
		
		List<BookTag> bookTags = new ArrayList<>();
		
		List<Book> books = new ArrayList<>();
		

		try {
			connection = DAOUtilities.getConnection();
			String sql = "SELECT book_tags.isbn_13, books.title, book_tags.tag_name\n" + 
					"		FROM book_tags\n" + 
					"		INNER JOIN books ON book_tags.isbn_13 = books.isbn_13;";
			
			stmt = connection.prepareStatement(sql);
			
			stmt.setString(1, "isbn_13");
			stmt.setString(2, "tag_name");
			
			//????
			//SetString with book table properties??
			//try catch block for book but don't close ???
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				BookTag bookTag = new BookTag();

				bookTag.setIsbn13(rs.getString("isbn_13"));
				bookTag.setTagName(rs.getString("tag_name"));
				
				bookTags.add(bookTag);		
			}
			
			//use if books.isbn_13 == bookTags.isbn_13 etc. ???
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResources();
		}
		
		return bookTags;
	}

		
		
//
//	@Override
//	public List<BookTag> getBooksWithGivenTag(String tag_name) {
//		
//		List<BookTag> bookTags = new ArrayList<>();
//		
//		try {
//			connection = DAOUtilities.getConnection();
//			String sql = "SELECT book_tags.isbn_13, books.title, book_tags.tag_name "
//					+ "FROM book_tags "
//					+ "INNER JOIN books "
//					+ "ON book_tags.isbn_13 = books.isbn_13";
//			stmt = connection.prepareStatement(sql);
//			
//			ResultSet rs = stmt.executeQuery();
//			
//			while (rs.next()) {
//				BookTag bookTag = new BookTag();
//
//				bookTag.setIsbn13(rs.getString("isbn_13"));
//				bookTag.setTagName(rs.getString("tag_name"));
//				
//				bookTags.add(bookTag);
//				
//			}
//			
//			rs.close();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			closeResources();
//		}
//	
//		return bookTags;
//	}
//
//
//	@Override
//	public List<BookTag> addTagToBookByIsbn(String isbn, String tag_name) {
//		
//		List<BookTag> bookTags = new ArrayList<>();
//		
//		try {
//			connection = DAOUtilities.getConnection();
//			String sql = "INSERT INTO book_tags"
//					+ "VALUES (?, ?)";
//			stmt = connection.prepareStatement(sql);
//			
//			ResultSet rs = stmt.executeQuery();
//			
//			while (rs.next()) {
//				BookTag bookTag = new BookTag();
//
//				bookTag.setIsbn13(rs.getString("isbn_13"));
//				bookTag.setTagName(rs.getString("tag_name"));
//				
//				bookTags.add(bookTag);
//				
//			}
//			
//			rs.close();
//			
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} finally {
//			closeResources();
//		}
//	
//		return bookTags;
//	}
//
//
	



//	
//		return bookTags;
//	}

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