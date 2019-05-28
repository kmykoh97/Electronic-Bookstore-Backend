package SJTU.eBook.DAO;

import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

public interface HistoryManagement
{

    @RequestMapping("/purchase/create_indent")
    String createHistory(HttpSession httpSession);

    @RequestMapping("/fetch_indents")
    String fetchHistory(HttpSession httpSession, String book_filter, String author_filter, String time_filter);

}
