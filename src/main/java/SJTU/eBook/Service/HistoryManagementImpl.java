package SJTU.eBook.Service;

import SJTU.eBook.DAO.HistoryManagement;
import SJTU.eBook.Entity.*;
import SJTU.eBook.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Calendar;

@RestController
@SpringBootApplication
public class HistoryManagementImpl implements HistoryManagement
{

    @Autowired
    BookRepository bookRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    HistoryItemsRepository historyItemsRepository;

    @Autowired
    HistoryRepository historyRepository;

    public String createHistory(HttpSession httpSession) {
        Object usn = httpSession.getAttribute("user");

        if (usn == null) {
            return "Not logged in";
        }

        List<Order> orders = orderRepository.getOrderFormsByUsername(usn.toString());
        int len = orders.size();

        if (len == 0) {
            return "No order to submit";
        }

        History history = new History();
        Calendar c = Calendar.getInstance(); // get date
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        String create_time = year + "-" +
                ((month>=10)?month:("0"+month)) + "-" +
                (date>=10?date:("0"+date)) + "T" +
                (hour>=10?hour:("0"+hour)) + ":" +
                (minute>=10?minute:("0"+minute)) + ":" +
                (second>=10?second:("0"+second));
        history.setCreateTime(create_time);
        historyRepository.save(history);

        // iterate to do following procedure on history items based on one time of history
        for (int i = 0; i < len; i++) {
            Order order = orders.get(i);
            Book book = bookRepository.getBookByBookId(order.getBook_id());
            book.setSales(book.getSales()+order.getAmount());
            book.setInventory(book.getInventory()-order.getAmount());
            HistoryItems new_item = new HistoryItems();
            new_item.setAmount(order.getAmount());
            new_item.setBookId(book.getBookId());
            new_item.setHistoryId(history.getHistoryId());
            new_item.setUsername(usn.toString());
            new_item.setHistoryId(history.getHistoryId());
            orderRepository.delete(order);
            historyItemsRepository.save(new_item);
        }

        historyRepository.save(history);

        return "Success";
    }


    public String fetchHistory(HttpSession httpSession, String book_filter, String author_filter, String time_filter) {
        Object usn = httpSession.getAttribute("user");

        if(usn == null) {
            return "Not logged in";
        }

        List<HistoryItems> historyItemsList = historyItemsRepository.getByUsername(usn.toString());
        int len = historyItemsList.size();

        if (book_filter == null) {
            book_filter = "";
        }

        if (author_filter == null) {
            author_filter = "";
        }

        if (time_filter == null) {
            time_filter = "";
        }

        StringBuffer buf = new StringBuffer("[");

        for (int i = 0; i < len; i++) {
            HistoryItems historyItem = historyItemsList.get(i);
            String time = historyRepository.getHistoryByHistoryId(historyItem.getHistoryId()).getCreateTime();
            Book book = bookRepository.getBookByBookId(historyItem.getBookId());
            String name = book.getBookName();
            String author = book.getAuthor();

            if (!name.toLowerCase().contains(book_filter.toLowerCase())) {
                continue;
            }

            if (!author.toLowerCase().contains(author_filter.toLowerCase())) {
                continue;
            }

            if (!time.startsWith(time_filter)) {
                continue;
            }

            buf.append(
                    "{\"Book_name\" : \"" + name +
                    "\", \"Author\" : \"" +book.getAuthor() +
                    "\", \"OrderID\" : \"" +historyItem.getHistoryId() +
                    "\", \"Time\" : \"" +time +
                    "\", \"Price\" : \"" +book.getPrice() * historyItem.getAmount() +
                    "\", \"Amount\" : \""+ historyItem.getAmount() +"\"},");
        }

        if (buf.toString().equals("[")) {
            return "[]";
        }

        buf.deleteCharAt(buf.length()-1);
        buf.append("]");

        return buf.toString();
    }
}
