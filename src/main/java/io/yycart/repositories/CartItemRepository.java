package io.yycart.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import io.yycart.model.CartItem;
import io.yycart.model.Product;


public interface CartItemRepository extends CrudRepository<CartItem, Long> {
	List<CartItem> findByProduct(Product product);
}
