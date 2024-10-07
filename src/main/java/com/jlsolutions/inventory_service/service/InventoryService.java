package com.jlsolutions.inventory_service.service;

import com.jlsolutions.inventory_service.model.InventoryItem;
import com.jlsolutions.inventory_service.repository.InventoryRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {

	private final InventoryRepository repository;

	public InventoryService(InventoryRepository repository) {
		this.repository = repository;
	}

	public InventoryItem getInventoryItem(String productId) {
		return repository.findByProductId(productId);
	}

	public InventoryItem updateInventoryItem(String productId, int newQuantity) {
		InventoryItem item = repository.findByProductId(productId);
		if (item != null) {
			item.setQuantity(newQuantity);
			return repository.save(item);
		}
		return null;
	}

	public void updateBatch(List<InventoryItem> items) {
		repository.saveAll(items);
	}
}