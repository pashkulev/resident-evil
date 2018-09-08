package com.vankata.residentevil.repository;

import com.vankata.residentevil.domain.entity.Capital;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CapitalRepository extends JpaRepository<Capital, Long> {

    List<Capital> findAllByOrderByName();
}
