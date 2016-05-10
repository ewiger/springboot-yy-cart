package io.yycart.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.yycart.model.CartItem;


@RestController
@RequestMapping(value = CartController.PATH, produces = CartController.APPLICATION_JSON_VALUE)
public class CartController {

//	protected static final String PATH = "/0.1/cart";
	protected static final String PATH = "/cart";
	protected static final String APPLICATION_JSON_VALUE = "application/json; charset=utf-8";

	@Autowired
	ICartService cart;
	
	@RequestMapping(value="/items", method=RequestMethod.GET)
	public List<CartItem> getItems(@RequestParam(value="user", defaultValue="anonymous") String username)
	{		
		return cart.getItems(username);
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public CartItem addProduct(@RequestParam(value="product") String productName, @RequestParam(value="qty", defaultValue="1") int quantity, 
							   @RequestParam(value="user", defaultValue="anonymous") String username)
	{
		return cart.addProduct(productName, quantity, username);
	}
	
	@RequestMapping(value="/empty", method=RequestMethod.GET)
	public void emptyCart(@RequestParam(value="user", defaultValue="anonymous") String username)
	{
		cart.emptyCart(username);
	}
	
	@RequestMapping(value="/cost", method=RequestMethod.GET)
	public double getTotalCost(@RequestParam(value="user", defaultValue="anonymous") String username)
	{
		return cart.getTotalCost(username);
	}
}
