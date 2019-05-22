package SJTU.eBook.Repository;

import SJTU.eBook.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface HistoryItemsRepository extends JpaRepository<HistoryItems, Long>{
    @Query("select item from HistoryItems item where item.username=:username")
    List<HistoryItems> getByUsername(@Param("username") String username);

    @Query("select item from HistoryItems item")
    List<HistoryItems> getAll();
}