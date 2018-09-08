package com.vankata.residentevil.repository;

import com.vankata.residentevil.domain.entity.Virus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VirusRepository extends JpaRepository<Virus, Long> {
}
