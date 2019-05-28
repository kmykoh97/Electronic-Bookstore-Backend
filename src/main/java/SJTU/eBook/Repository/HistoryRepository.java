package SJTU.eBook.Repository;

import SJTU.eBook.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface HistoryRepository extends JpaRepository<History, Long>
{

@Query("select i from History i where i.historyId=:id")
    History getHistoryByHistoryId(@Param("id") int id);

}