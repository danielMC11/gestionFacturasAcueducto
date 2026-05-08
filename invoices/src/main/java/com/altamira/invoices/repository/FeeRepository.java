package com.altamira.invoices.repository;

import com.altamira.invoices.entity.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface FeeRepository extends JpaRepository<Fee,Long> {

    List<Fee> findAllByOrderByIdAsc();

}
