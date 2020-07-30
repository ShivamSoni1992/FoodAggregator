package com.foodaggregator.service;

import com.foodaggregator.dto.FoodDetailsDTO;

public interface FoodDetailsService {

	public FoodDetailsDTO getItemDetails(String itemName);

	public FoodDetailsDTO getItemDetailsByNameAndQtyAndPrice(String itemName, int itemQty, double itemPrice);

	public FoodDetailsDTO getItemDetailsFast(String itemName);

}
