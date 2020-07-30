package com.foodaggregator.controller;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.foodaggregator.dto.FoodDetailsDTO;
import com.foodaggregator.service.FoodDetailsService;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FoodDetailsController {
	
	@Autowired
	private FoodDetailsService foodDetailsService;
		
	/**
	 * find item by name
	 * 
	 * @param itemName
	 * @return FoodDetailsDTO
	 */
	 @GetMapping(value="buy-item")
	  public ResponseEntity<FoodDetailsDTO> getItemDetails(
	      @RequestParam("itemName") String itemName) {
		 log.info("In getItemDetails method of FoodDetailsController to find item by name");
	    log.info("get item details by item name");
	    FoodDetailsDTO lmsClasses = foodDetailsService.getItemDetails(itemName);
	    log.info("get item details by item name success");
	    if(Objects.isNull(lmsClasses)){
	    	log.info("Item details not found");
	    return new ResponseEntity<FoodDetailsDTO>(HttpStatus.NOT_FOUND);
	    }
	    else{
	    	log.info("Item details found");
	    	return new ResponseEntity<FoodDetailsDTO>(lmsClasses, HttpStatus.OK);
	    }
	  }
	 
	 /**
	  * find Item by name, quantity and price
	  * 
	  * @param itemName
	  * @param itemQty
	  * @param itemPrice
	  * @return FoodDetailsDTO
	  */
	 @GetMapping(value="buy-item-qty-price")
	  public ResponseEntity<FoodDetailsDTO> getItemDetails(
	      @RequestParam("itemName") String itemName, @RequestParam("itemQty") int itemQty, @RequestParam("itemPrice") double itemPrice) {
		 log.info("In getItemDetails method of FoodDetailsController to find Item by name, quantity and price");
	    log.info("get item details by item name, ");
	    FoodDetailsDTO lmsClasses = foodDetailsService.getItemDetailsByNameAndQtyAndPrice(itemName,itemQty,itemPrice);
	    log.info("get item details by item name success");
	    if(Objects.isNull(lmsClasses)){
	    	log.info("Item details not found");
	    return new ResponseEntity<FoodDetailsDTO>(HttpStatus.NOT_FOUND);
	    }
	    else{
	    	log.info("Item details found");
	    	return new ResponseEntity<FoodDetailsDTO>(lmsClasses, HttpStatus.OK);
	    }
	  }
	 
	 /**
	  * find item by name faster
	  * 
	  * @param itemName
	  * @return FoodDetailsDTO
	  */
	 @GetMapping(value="fast-buy-item")
	  public ResponseEntity<FoodDetailsDTO> getItemDetailsFast(
	      @RequestParam("itemName") String itemName) {
		 log.info("In getItemDetailsFast method of FoodDetailsController to find item by name faster");
	    log.info("get item details by item name faster");
	    FoodDetailsDTO lmsClasses = foodDetailsService.getItemDetailsFast(itemName);
	    log.info("get item details by item name success");
	    if(Objects.isNull(lmsClasses)){
	    	log.info("Item details not found");
	    return new ResponseEntity<FoodDetailsDTO>(HttpStatus.NOT_FOUND);
	    }
	    else{
	    	log.info("Item details found");
	    	return new ResponseEntity<FoodDetailsDTO>(lmsClasses, HttpStatus.OK);
	    }
	  }
}
