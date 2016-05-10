package io.yycart.rest;

import java.util.List;

import io.yycart.model.CartItem;

public interface ICartService {
	List<CartItem> getItems(String username);
	CartItem addProduct(String productName, int quantity, String username);
	public void emptyCart(String username);
	double getTotalCost(String username);
}
