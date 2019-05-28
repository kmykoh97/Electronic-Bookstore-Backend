package SJTU.eBook.Repository;

import SJTU.eBook.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>
{

    @Query("select o from Order o where (o.username=:username) and (o.book_id=:book_id)")
    List<Order> getOrderFormsByUsernameAndBook_id(@Param("username") String username, @Param("book_id") int book_id);

    @Query("select o from Order o where (o.username=:username)")
    List<Order> getOrderFormsByUsername(@Param("username") String username);

    @Query("select o from Order o where (o.orderId=:orderId)")
    Order getOrderFormsByOrderId(@Param("orderId") int orderId);

}