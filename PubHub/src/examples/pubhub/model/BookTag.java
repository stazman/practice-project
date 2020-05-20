package examples.pubhub.model;

public class BookTag {
	
	private String isbn13;
	private String tagName;

	public BookTag() {
		this.isbn13 = null;
		this.tagName = null;
		
	}
	
	public String getIsbn13() {
		return isbn13;
	}
	
	public void setIsbn13(String isbn) {
		this.isbn13 = isbn;
	}
		
	public String getTagName() {
		return tagName;
	}
		
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	
}
