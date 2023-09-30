package test0932.bookmanagerclass;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @title:
 * @author:nanzhou
 * @date:
 */
public class Books {
    private Integer id;
    private String bookTitle;
    private Date publicationDate;
    private String author;
    private BigDecimal price;
    private Integer quantity;

    @Override
    public String toString() {
        return "Books{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", publicationDate=" + publicationDate +
                ", author='" + author + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


}
