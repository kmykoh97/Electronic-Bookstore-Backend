package SJTU.eBook.Database;

import javax.persistence.*;

@Entity
@Table(name="historyitems")
public class HistoryItems {

    private int bookId;
    private String username;
    private int historyId;
    private int amount;

    @Id
    @GeneratedValue
    private int pk;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getHistoryId() {
        return historyId;
    }

    public void setHistoryId(int Id) {
        this.historyId = Id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
