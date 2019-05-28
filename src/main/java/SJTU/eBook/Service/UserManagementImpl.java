package SJTU.eBook.Service;

import SJTU.eBook.DAO.UserManagement;
import SJTU.eBook.Entity.*;
import SJTU.eBook.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpSession;

@RestController
@SpringBootApplication
public class UserManagementImpl implements UserManagement
{

    @Autowired
    UserRepository customerRepository;

    public String checkSession(HttpSession httpSession) {
        String message = "No info received";

        if (httpSession.getAttribute("user") != null) {
            return "Succeed";
        } else {
            return "Failure";
        }
    }

    public void logout(HttpSession httpSession) {
        httpSession.setAttribute("user",null);
    }

    public String checkLogin(String usn, String psw, HttpSession httpSession) {
        User customer = customerRepository.getCustomerByUsername(usn);

        if (customer == null) {
            return "Unknown user";
        } else if (customer.getPassword().equals(psw)) {
            if (customer.getIs_valid() == 0) {
                return "Blocked user";
            }

            httpSession.setAttribute("user", usn);

            return "Succeed";
        } else {
            return "Failure";
        }
    }

    public String getInfo(HttpSession httpSession) {
        Object user = httpSession.getAttribute("user");

        if(user == null) {
            return "Not logged in";
        } else {
            User customer = customerRepository.getCustomerByUsername(user.toString());

            return(
                    "{\"phone\" : \"" + customer.getPhone() +
                            "\", \"address\" : \"" + customer.getAddress() +
                            "\", \"email\" : \"" +customer.getEmail() +
                            "\", \"name\" : \""+ customer.getName() +"\"}"
            );
        }
    }

    public String updateProfile(HttpSession httpSession, String phone, String email, String name, String address) {
        Object user = httpSession.getAttribute("user");

        if(user == null) {
            return "Not logged in";
        }

        User customer = customerRepository.getCustomerByUsername(user.toString());

        if (customer == null) {
            return "Unknown user";
        }

        customer.setAddress(address);
        customer.setEmail(email);
        customer.setName(name);
        customer.setPhone(phone);
        customerRepository.save(customer);

        return "Succeed";
    }

    public String signUp(HttpSession httpSession, String username, String password, String phone, String email,  String address, String realname) {
        User customer = customerRepository.getCustomerByUsername(username);

        if (customer != null) {
            return "Username used";
        }

        User newCustomer = new User();
        newCustomer.setAddress(address);
        newCustomer.setEmail(email);
        newCustomer.setName(realname);
        newCustomer.setPassword(password);
        newCustomer.setPhone(phone);
        newCustomer.setUsername(username);
        newCustomer.setIs_valid(1);
        customerRepository.save(newCustomer);
        httpSession.setAttribute("user", username);

        return "Succeed";
    }
}
