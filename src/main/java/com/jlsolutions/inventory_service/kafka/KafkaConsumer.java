package com.jlsolutions.inventory_service.kafka;

import com.jlsolutions.commons.ProductEvent;
import com.jlsolutions.inventory_service.model.InventoryItem;
import com.jlsolutions.commons.ProductDTO;
import com.jlsolutions.inventory_service.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaConsumer {

	private final InventoryService inventoryService;

	@Autowired
	public KafkaConsumer(InventoryService inventoryService) {
		this.inventoryService = inventoryService;
	}

	@KafkaListener(topics = "product-events", groupId = "inventory-group")
	public void listenProductEvents(ProductEvent productEvent) {
		String event = productEvent.getEvent();
		ProductDTO productDTO = productEvent.getProductDTO();
		switch (event) {
			case "ProductCreated":
				log.info("Evento recibido: ProductCreated para el producto ID {}", productDTO.getId());

				// Buscar el InventoryItem por ID
				InventoryItem inventoryItemCreated = inventoryService.getInventoryItem(productDTO.getId());

				if (inventoryItemCreated == null) {
					// Si no existe, crear un nuevo InventoryItem con la cantidad inicial (por ejemplo, 1)
					inventoryItemCreated = new InventoryItem();
					inventoryItemCreated.setProductId(productDTO.getId());
					inventoryItemCreated.setQuantity(1);
					inventoryService.createInventoryItem(inventoryItemCreated);  // Suponiendo que existe este método
					log.info("InventoryItem creado para el producto ID {} con cantidad inicial {}", productDTO.getId(), 1);
				} else {
					// Si existe, simplemente actualizamos la cantidad
					inventoryService.updateInventoryItem(productDTO.getId(), inventoryItemCreated.getQuantity() + 1);
					log.info("InventoryItem actualizado para el producto ID {} con nueva cantidad {}", productDTO.getId(), inventoryItemCreated.getQuantity() + 1);
				}
				break;
			case "ProductUpdated":
				log.info("Evento recibido: ProductUpdated para el producto ID {}", productDTO.getId());
				// Lógica para manejar actualizaciones de productos
				break;
			case "ProductDeleted":
				log.info("Evento recibido: ProductDeleted para el producto ID {}", productDTO.getId());
				// Lógica para manejar eliminación de productos
				InventoryItem inventoryItemDeleted = inventoryService.getInventoryItem(productDTO.getId());
				inventoryService.updateInventoryItem(productDTO.getId(), inventoryItemDeleted.getQuantity()-1);
				log.info("Producto ID {} eliminado del inventario.", productDTO.getId());
				break;
			default:
				// Manejar eventos desconocidos
				break;
		}
	}
}