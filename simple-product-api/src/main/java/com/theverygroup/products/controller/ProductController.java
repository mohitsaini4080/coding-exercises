package com.theverygroup.products.controller;

import com.theverygroup.products.dto.Product;
import com.theverygroup.products.exceptions.ApiMessageHandler;
import com.theverygroup.products.exceptions.DataNotFoundException;
import com.theverygroup.products.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ProductController {

	private final ProductRepository productRepository;

	public ProductController(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	/**
	 * @param product
	 * @return Product response
	 * @apiNote Function for Getting all the data.
	 */
	@GetMapping("/products")
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	/**
	 * @param product
	 * @return ApiHandler with Custom message
	 * @apiNote Function to save the data.
	 */
	@PostMapping("/saveData")
	public ResponseEntity<ApiMessageHandler> saveData(@RequestBody Product product) {
		productRepository.saveProductData(product);
		return ResponseEntity
				.ok(new ApiMessageHandler("Data has need added successfully", HttpStatus.OK, LocalDateTime.now()));
	}

	/**
	 * @param type
	 * @return Product response
	 * @apiNote Function to get the data on the basis of 'Type' parameter.
	 */
	@GetMapping("/getProductByType")
	public List<Product> findAll(@RequestParam(name = "Type", required = true) String type) {
		List<Product> products;
		if (type.isBlank() || type.isEmpty()) {
			throw new DataNotFoundException("Type cannot be blank");
		}
		products = productRepository.findProductDataByType(type);
		if (products.isEmpty() && products.size() == 0) {
			throw new DataNotFoundException("Product data not found with Type :" + type);
		}
		return products;
	}

	/**
	 * @param id
	 * @return Product response
	 * @apiNote Function to get the data on the basis of 'Id' parameter.
	 */
	@GetMapping("/getProductById")
	public Product findProductById(@RequestParam(name = "Id", required = true) String id) {
		if (id.isBlank() || id.isEmpty()) {
			throw new DataNotFoundException("Id cannot be blank");
		}
		return productRepository.findProductDataById(id)
				.orElseThrow(() -> new DataNotFoundException("Product data not found with with id :" + id));
	}

	/**
	 * @param id
	 * @return delete the product by id
	 * @apiNote Function to delete the data on the basis of 'Id' parameter.
	 */
	@DeleteMapping("/deleteProductById")
	public ResponseEntity<ApiMessageHandler> deleteProductDataById(@RequestParam(name = "Id", required = true) String id) {
		if (id.isBlank() || id.isEmpty()) {
			throw new DataNotFoundException("Id cannot be blank");
		}
		productRepository.deleteProductDataById(id);
		return ResponseEntity.ok(new ApiMessageHandler("Product data with id " + id + " has been deleted!", HttpStatus.OK,
				LocalDateTime.now()));
	}

}