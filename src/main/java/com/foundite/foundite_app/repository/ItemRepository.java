package com.foundite.foundite_app.repository;

import com.foundite.foundite_app.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategory(String category);
    List<Item> findByStatus(Item.ItemStatus status);
    // TODO: add search by keyword (title/description)
}
