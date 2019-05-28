package SJTU.eBook.Repository;

import SJTU.eBook.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long>
{

    @Query("SELECT c from User c")
    List<User> getAll();

    @Query("select c from User c where c.username=:username")
    User getCustomerByUsername(@Param("username") String username);

}