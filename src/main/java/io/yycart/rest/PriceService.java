package io.yycart.rest;

import org.springframework.stereotype.Service;

import io.yycart.model.Product;

@Service
public class PriceService implements IPriceService {

	/**
	 * This is mainly a stub for any more serious logic to come.
	 * 
     * Papayas are 50p each, but are available as ‘three for the price of two’.
     * Given a shopping list with items, calculate the total cost of those items.
	 */	
	@Override
	public double getDiscount(Product product, int quantity) {
		if (!(product.getName().equals("Papaya")) || (quantity < 3)) return 0;
		return quantity / 3;
	}

}
