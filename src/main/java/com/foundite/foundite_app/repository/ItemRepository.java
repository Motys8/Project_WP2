package com.foundite.foundite_app.repository;

import com.foundite.foundite_app.model.Item;
import com.foundite.foundite_app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByCategory(String category);
    List<Item> findByStatus(Item.ItemStatus status);
    List<Item> findByCategoryAndStatus(String category, Item.ItemStatus status);
    List<Item> findByPostedBy(User user);
    long countByPostedBy(User user);

    @Query("SELECT i FROM Item i WHERE LOWER(i.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(i.description) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Item> searchByKeyword(@Param("keyword") String keyword);
}