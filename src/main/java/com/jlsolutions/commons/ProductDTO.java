package com.jlsolutions.commons;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor  // Añade esto
@AllArgsConstructor
public class ProductDTO {
	private String id;
	private String name;
	private Double price;
	private String description;
}