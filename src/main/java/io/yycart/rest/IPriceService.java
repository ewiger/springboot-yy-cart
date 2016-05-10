package io.yycart.rest;

import io.yycart.model.Product;

public interface IPriceService {
	double getDiscount(Product product, int quantity);
}
