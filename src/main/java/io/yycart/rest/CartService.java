package io.yycart.rest;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.yycart.rest.ICartService;
import io.yycart.model.CartItem;
import io.yycart.model.Product;
import io.yycart.repositories.CartItemRepository;
import io.yycart.repositories.ProductRepository;


/**
 * A cart service keeps track of products and their quantity in the basket.
 * 
 * TODO: modify CartItemRepository to add different carts per different user.
 * 
 * @author yy
 *
 */
@Service
public class CartService implements ICartService {
	
	private static final Logger log = LoggerFactory.getLogger(CartService.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CartItemRepository cartItemRepository;
     
	@Autowired
	private IPriceService priceHelper;
	
    /** 
     * Get a list of cart items for that user.  
	 * 
	 * @see io.yycart.rest.ICartService#getItems(java.lang.String)
	 */
    @Override
	public List<CartItem> getItems(String username) {
    	List<CartItem> result = new ArrayList<CartItem>();
        for (CartItem item : cartItemRepository.findAll()) {
            result.add(item);
        }
        return result;
    }
    
    public CartItem addProduct(String productName, int quantity, String username){
    	log.debug("Adding product to cart: {}", productName);
    	// Sanity check for quantity and product existence.
    	assert quantity > 0;
    	List<Product> foundProducts = productRepository.findByName(productName);
    	if (foundProducts.size() == 0) {
    		throw new ProductNotFoundException();
    	}
    	// Is product already in the cart?
    	Product product = foundProducts.get(0);
    	List<CartItem> foundItems = cartItemRepository.findByProduct(product);
    	CartItem cartItem;
    	if (foundItems.size() > 0) {
    		// yes, increment count 
    		cartItem = foundItems.get(0);
    		cartItem.setQuantity(cartItem.getQuantity() + quantity);
    	} else {
    		// not yet, add a new product
    		cartItem = new CartItem(foundProducts.get(0), quantity);
    	}
		return cartItemRepository.save(cartItem);
    }
    
    public void emptyCart(String username){
    	cartItemRepository.deleteAll();
    }
    
	@Override
	public double getTotalCost(String username) {
		double totalPrice = 0;
		for (CartItem item : cartItemRepository.findAll()) {
			// Add up price times count
			totalPrice += item.getProduct().getPrice() * item.getQuantity();
			// Substract discount
			double discount = priceHelper.getDiscount(item.getProduct(), item.getQuantity());
			
			if (discount > 0) {
				totalPrice -= discount;
			}
		}
		return totalPrice;
	}

}
