package com.example.demo.curd.repository;

import com.example.demo.curd.entity.IndexEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexRepository extends JpaRepository<IndexEntity, Long> {
}
