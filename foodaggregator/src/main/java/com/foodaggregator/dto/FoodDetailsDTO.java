package com.foodaggregator.dto;

import lombok.Data;

@Data
public class FoodDetailsDTO {
	
	private String producId;
	
	private String productName;
	
	private int quantity;
	
	private String price;

}
