package com.jlsolutions.inventory_service.controller;

import com.jlsolutions.inventory_service.model.InventoryItem;
import com.jlsolutions.inventory_service.service.InventoryService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	private final InventoryService inventoryService;

	public InventoryController(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@GetMapping("/{productId}")
	public InventoryItem getInventory(@PathVariable String productId) {
		return inventoryService.getInventoryItem(productId);
	}

	@PutMapping("/{productId}")
	public InventoryItem updateInventory(@PathVariable String productId, @RequestBody int newQuantity) {
		return inventoryService.updateInventoryItem(productId, newQuantity);
	}

	@PostMapping("/update-batch")
	public void updateInventoryBatch(@RequestBody List<InventoryItem> items) {
		inventoryService.updateBatch(items);
	}
}