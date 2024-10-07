package com.jlsolutions.inventory_service.repository;

import com.jlsolutions.inventory_service.model.InventoryItem;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InventoryRepository extends MongoRepository<InventoryItem, String> {
	InventoryItem findByProductId(String productId);
}