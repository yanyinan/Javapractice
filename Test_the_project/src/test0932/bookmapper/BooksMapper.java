package test0932.bookmapper;

import test0932.bookmanagerclass.Books;
import test0932.utils.Resultmappable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class BooksMapper implements Resultmappable<Books> {
    @Override
    public Books mapper(ResultSet set) throws SQLException {
        Books books = new Books();
        books.setId(set.getInt("id"));
        books.setBook_title(set.getString("Book_title"));
        books.setPublication_date(set.getDate("publication_date"));
        books.setAuthor(set.getString("author"));
        books.setPrice(set.getDouble("price"));
        books.setQuantity(set.getInt("quantity"));
        return books;
    }
}
