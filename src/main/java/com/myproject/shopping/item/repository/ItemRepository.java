package com.myproject.shopping.item.repository;

import com.myproject.shopping.item.domain.Item;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>{
    List<Item> findAllByOrderByIdDesc();
    List<Item> findAllBy(Pageable pageable);
}
