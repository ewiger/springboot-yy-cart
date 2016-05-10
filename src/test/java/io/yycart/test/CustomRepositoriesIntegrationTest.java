package io.yycart.test;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import io.yycart.model.CartItem;
import io.yycart.model.Product;
import io.yycart.repositories.CartItemRepository;
import io.yycart.repositories.ProductRepository;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * On one hand, writing unit-tests for spring jpa wired magic like CRUD repositories is usually superfluous.
 * On the other, it makes perfect sense to write integration tests to cover any custom behavior that 
 * has been implemented on top of standard CRUD repository. This is what we do here.
 * 
 * @author yy
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class CustomRepositoriesIntegrationTest {

  private static final Logger log = LoggerFactory.getLogger(CustomRepositoriesIntegrationTest.class);

	
  @Autowired 
  ProductRepository productRepository;

  @Autowired 
  CartItemRepository cartItemRepository;

  
  @Test
  public void productTestCase() {
    Product product;
    product = productRepository.save(new Product("Apple", 0.25));
    product = productRepository.save(new Product("Orange", 0.30));
    product = productRepository.save(new Product("Garlic", 0.15));

    List<Product> result = productRepository.findByName("Garlic");
    
	log.debug("Last product name: {}", product.getName());
	log.debug("Retrieved product name: {}", result.get(0).getName());
	
    assertThat(result.size(), is(1));
    assertThat(result, hasItem(product));
  }
  
  @Test
  public void cartItemTestCase() {
    Product product;
    product = productRepository.save(new Product("Apple", 0.25));
    product = productRepository.save(new Product("Orange", 0.30));
    product = productRepository.save(new Product("Garlic", 0.15));

    CartItem cartItem;
    cartItem = cartItemRepository.save(new CartItem(product, 2));
    
    
	log.debug("Last product: {}", product);
	log.debug("Last cart item: {}", cartItem);
	
	List<CartItem> items = cartItemRepository.findByProduct(product);
	log.debug("Found cart item by product: {}", items.get(0));
    assertThat(items.size(), is(1));
    assertEquals(product, items.get(0).getProduct());
  }
}
