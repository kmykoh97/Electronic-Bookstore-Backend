package SJTU.eBook.DAO;

import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpSession;

public interface CartManagement {

    @RequestMapping("/getBook")
    String getBooklist();

    @RequestMapping("/purchase/add_to_cart")
    String addCart(HttpSession httpSession, int book_id);

    @RequestMapping("/purchase/fetch_cart")
    String fetchCart(HttpSession httpSession);

    @RequestMapping("/purchase/change_amount")
    String changeAmount(HttpSession httpSession, int order_id, int new_amount);

}
