package com.foodaggregator.serviceimpl;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.foodaggregator.dto.FoodDetailsDTO;
import com.foodaggregator.service.FoodDetailsService;
import com.foodaggregator.utils.APPConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FoodDetailsServiceImpl implements FoodDetailsService {

	@Value("${supplier.api.baseurl}")
	private String supplierBaseUrl;

	final String fruitSupplier = "c51441de-5c1a-4dc2-a44e-aab4f619926b";
	final String vegetableSupplier = "4ec58fbc-e9e5-4ace-9ff0-4e893ef9663c";
	final String grainSupplier = "e6c77e5c-aec9-403f-821b-e14114220148";

	@Override
	public FoodDetailsDTO getItemDetails(String itemName) {
		FoodDetailsDTO foodDetailsDTO = null;
		log.info("in getItemDetails method of FoodDetailsServiceImpl class");
		log.debug("Trying to find item from Fruit supplier");
		foodDetailsDTO = findItem(supplierBaseUrl + fruitSupplier, APPConstants.SUPPLIER_TYPE.FRUIT.name(), itemName,
				-1, -1);
		if (Objects.isNull(foodDetailsDTO)) {
			log.debug("Trying to find item from vegetable supplier");
			foodDetailsDTO = findItem(supplierBaseUrl + vegetableSupplier, APPConstants.SUPPLIER_TYPE.VEGETABLE.name(),
					itemName, -1, -1);
		}
		if (Objects.isNull(foodDetailsDTO)) {
			log.debug("Trying to find item from grain supplier");
			foodDetailsDTO = findItem(supplierBaseUrl + grainSupplier, APPConstants.SUPPLIER_TYPE.GRAIN.name(),
					itemName, -1, -1);
		}
		return foodDetailsDTO;
	}

	private FoodDetailsDTO findItem(String apiPath, String supplierType, String itemName, int itemQty,
			double itemPrice) {
		FoodDetailsDTO foodDetailsDTO = null;
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(apiPath);
		StringEntity input = null;
		HttpResponse response = null;
		try {
			input = new StringEntity("product");
			post.setEntity(input);
			response = client.execute(post);
			String json_string = EntityUtils.toString(response.getEntity());
			JSONArray jsonArr = new JSONArray(json_string);

			if (StringUtils.equalsIgnoreCase(supplierType, APPConstants.SUPPLIER_TYPE.FRUIT.name())) {
				log.debug("Trying to find item from Fruit supplier");
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArr.get(i);
					String name = jsonObj.getString("name");
					int qty = jsonObj.getInt("quantity");
					String price = jsonObj.getString("price");
					price = price.substring(1);
					double pr = Double.parseDouble(price);
					if (StringUtils.equalsIgnoreCase(itemName, name) && ((itemQty > 0 && itemQty <= qty) || itemQty < 0)
							&& ((itemPrice > 0 && pr >= itemPrice) || itemPrice < 0)) {
						foodDetailsDTO = new FoodDetailsDTO();
						foodDetailsDTO.setProducId(jsonObj.getString("id"));
						foodDetailsDTO.setProductName(jsonObj.getString("name"));
						foodDetailsDTO.setQuantity(jsonObj.getInt("quantity"));
						foodDetailsDTO.setPrice(jsonObj.getString("price"));
						log.debug("Required Item found");
						break;
					}
				}
			} else if (StringUtils.equalsIgnoreCase(supplierType, APPConstants.SUPPLIER_TYPE.VEGETABLE.name())) {
				log.debug("Trying to find item from Vegetable supplier");
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArr.get(i);
					String name = jsonObj.getString("productName");
					int qty = jsonObj.getInt("quantity");
					String price = jsonObj.getString("price");
					price = price.substring(1);
					double pr = Double.parseDouble(price);
					if (StringUtils.equalsIgnoreCase(itemName, name) && ((itemQty > 0 && itemQty <= qty) || itemQty < 0)
							&& ((itemPrice > 0 && pr >= itemPrice) || itemPrice < 0)) {
						foodDetailsDTO = new FoodDetailsDTO();
						foodDetailsDTO.setProducId(jsonObj.getString("productId"));
						foodDetailsDTO.setProductName(jsonObj.getString("productName"));
						foodDetailsDTO.setQuantity(jsonObj.getInt("quantity"));
						foodDetailsDTO.setPrice(jsonObj.getString("price"));
						log.debug("Required Item found");
						break;
					}
				}
			} else if (StringUtils.equalsIgnoreCase(supplierType, APPConstants.SUPPLIER_TYPE.GRAIN.name())) {
				log.debug("Trying to find item from Grain supplier");
				for (int i = 0; i < jsonArr.length(); i++) {
					JSONObject jsonObj = (JSONObject) jsonArr.get(i);
					String name = jsonObj.getString("itemName");
					int qty = jsonObj.getInt("quantity");
					String price = jsonObj.getString("price");
					price = price.substring(1);
					double pr = Double.parseDouble(price);
					if (StringUtils.equalsIgnoreCase(itemName, name) && ((itemQty > 0 && itemQty <= qty) || itemQty < 0)
							&& ((itemPrice > 0 && pr >= itemPrice) || itemPrice < 0)) {
						foodDetailsDTO = new FoodDetailsDTO();
						foodDetailsDTO.setProducId(jsonObj.getString("itemId"));
						foodDetailsDTO.setProductName(jsonObj.getString("itemName"));
						foodDetailsDTO.setQuantity(jsonObj.getInt("quantity"));
						foodDetailsDTO.setPrice(jsonObj.getString("price"));
						log.debug("Required Item found");
						break;
					}
				}
			}
			// System.out.println( jsonObj. );
		} catch (

		Exception e) {
			e.printStackTrace();
		}

		return foodDetailsDTO;
	}

	@Override
	public FoodDetailsDTO getItemDetailsByNameAndQtyAndPrice(String itemName, int itemQty, double itemPrice) {
		FoodDetailsDTO foodDetailsDTO = null;
		log.debug("Trying to find item from Fruit supplier");
		foodDetailsDTO = findItem(supplierBaseUrl + fruitSupplier, APPConstants.SUPPLIER_TYPE.FRUIT.name(), itemName,
				itemQty, itemPrice);
		if (Objects.isNull(foodDetailsDTO)) {
			log.debug("Trying to find item from vegetable supplier");
			foodDetailsDTO = findItem(supplierBaseUrl + vegetableSupplier, APPConstants.SUPPLIER_TYPE.VEGETABLE.name(),
					itemName, itemQty, itemPrice);
		}
		if (Objects.isNull(foodDetailsDTO)) {
			log.debug("Trying to find item from Grain supplier");
			foodDetailsDTO = findItem(supplierBaseUrl + grainSupplier, APPConstants.SUPPLIER_TYPE.GRAIN.name(),
					itemName, itemQty, itemPrice);
		}
		return foodDetailsDTO;
	}

	@Override
	public FoodDetailsDTO getItemDetailsFast(String itemName) {
		FoodDetailsDTO foodDetailsDTO = null;
		log.debug("Creating Task to find item parallel from all suppliers");
		Callable<FoodDetailsDTO> task1 = () -> {
			return findItem(supplierBaseUrl + fruitSupplier, APPConstants.SUPPLIER_TYPE.FRUIT.name(), itemName, -1, -1);
		};
		Callable<FoodDetailsDTO> task2 = () -> {
			return findItem(supplierBaseUrl + vegetableSupplier, APPConstants.SUPPLIER_TYPE.VEGETABLE.name(), itemName,
					-1, -1);
		};
		Callable<FoodDetailsDTO> task3 = () -> {
			return findItem(supplierBaseUrl + grainSupplier, APPConstants.SUPPLIER_TYPE.GRAIN.name(), itemName, -1, -1);
		};

		// ExecutorService executorService =
		// Executors.newSingleThreadExecutor();
		// Future future1 = executorService.submit(task1);
		// Future future2 = executorService.submit(task2);
		// Future future3 = executorService.submit(task3);
		// try {
		// if (!Objects.isNull(future1.get())) {
		// foodDetailsDTO = (FoodDetailsDTO) future1.get();
		// } else if (!Objects.isNull(future2.get())) {
		// foodDetailsDTO = (FoodDetailsDTO) future2.get();
		// } else if (!Objects.isNull(future3.get())) {
		// foodDetailsDTO = (FoodDetailsDTO) future3.get();
		// }
		// } catch (Exception e) {
		// }

		ExecutorService executor = Executors.newFixedThreadPool(1);
		List<Callable<FoodDetailsDTO>> callables = Arrays.asList(task1, task2, task3);

		try {
			log.debug("executing task for each suppliers parallel");
			List<FoodDetailsDTO> futures = executor.invokeAll(callables).stream().map(future -> {
				try {
					return future.get();
				} catch (Exception e) {
					throw new IllegalStateException(e);
				}
			}).collect(Collectors.toList());
			for (FoodDetailsDTO dto : futures) {
				if (!Objects.isNull(dto)) {
					foodDetailsDTO = (FoodDetailsDTO) dto;
					break;
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return foodDetailsDTO;
	}

}
