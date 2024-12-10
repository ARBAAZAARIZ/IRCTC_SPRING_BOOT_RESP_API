package com.arbaaz.IRCTC.repository;

import com.arbaaz.IRCTC.model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface BankRepository extends JpaRepository<BankAccount,Integer> {
//        The @Query annotation allows you to define custom queries that go beyond the standard built-in query methods
//        provided by Spring Data JPA. This gives you the flexibility to tailor your queries to fit specific
//        requirements or optimizations that aren't covered by the default method name conventions.

        @Query("SELECT b.id FROM BankAccount b WHERE b.bankName = :bankName")
        List<Integer> findIdsByBankName(@Param("bankName") String bankName);

        @Query("SELECT b.bankName FROM BankAccount b INNER JOIN user u  where u.userName = :username ")
        public String findBankNameByUserName( @Param("username") String username);


//        @Query("SELECT b.bankName FROM BankAccount b INNER JOIN b.user u WHERE u.userName = :username")
//        public String findBankNameByUserName(@Param("username") String username);


}
