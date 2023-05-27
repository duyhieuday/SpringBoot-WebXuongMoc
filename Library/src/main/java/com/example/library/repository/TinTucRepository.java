package com.example.library.repository;

import com.example.library.model.Product;
import com.example.library.model.TinTuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TinTucRepository extends JpaRepository<TinTuc, Long> {
    Long countById(Long id);
}
