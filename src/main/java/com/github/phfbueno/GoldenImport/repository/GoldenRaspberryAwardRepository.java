package com.github.phfbueno.GoldenImport.repository;

import com.github.phfbueno.GoldenImport.model.GoldenRaspberryAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoldenRaspberryAwardRepository extends JpaRepository<GoldenRaspberryAward, Long> {
}
