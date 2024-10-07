package com.jlsolutions.inventory_service.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "inventory")
public class InventoryItem {

	@Id
	private String productId;
	private int quantity;

}