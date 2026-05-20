package com.foundite.foundite_app.service;

import com.foundite.foundite_app.model.Item;
import com.foundite.foundite_app.repository.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> findAll() {
        return itemRepository.findAll();
    }

    public Optional<Item> findById(Long id) {
        return itemRepository.findById(id);
    }

    public List<Item> findByCategory(String category) {
        return itemRepository.findByCategory(category);
    }

    public List<Item> findByStatus(Item.ItemStatus status) {
        return itemRepository.findByStatus(status);
    }

    public List<Item> findByCategoryAndStatus(String category, Item.ItemStatus status) {
        return itemRepository.findByCategoryAndStatus(category, status);
    }

    public List<Item> search(String keyword) {
        return itemRepository.searchByKeyword(keyword);
    }

    public Item save(Item item) {
        return itemRepository.save(item);
    }

    public void deleteById(Long id) {
        itemRepository.deleteById(id);
    }
}
