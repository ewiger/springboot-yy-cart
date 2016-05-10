package io.yycart.repositories;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import io.yycart.model.Product;


public interface ProductRepository extends CrudRepository<Product, Long> {
	List<Product> findByName(String name);
}
