package com.theverygroup.products.repository;

import com.theverygroup.products.dto.Product;
import com.theverygroup.products.exceptions.DataAlreadyExistException;
import com.theverygroup.products.exceptions.DataNotFoundException;
import com.theverygroup.products.util.ProductDataUtils;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepository {

	private List<Product> products;

	public ProductRepository() {
		products = ProductDataUtils.loadAll();
	}

	/**
	 *
	 * @return Product response
	 * @implNote Function for Getting all the data.
	 */
	public List<Product> findAll() {
		return products;
	}

	/**
	 * @param product
	 * @return Product response
	 * @implNote Function to save the data.
	 */
	public Product saveProductData(Product product) {
		if (findProductDataById(product.getId()).isPresent()) {
			throw new DataAlreadyExistException(
					"Exception occured due to product is already exists with Id : " + product.getId());
		}
		products.add(product);
		return product;
	}

	/**
	 * @param id
	 * @return Product response
	 * @implNote Function to get the data on the basis of 'Id' parameter.
	 */
	public Optional<Product> findProductDataById(String id) {
		return products.stream().filter(p -> p.getId().equals(id)).findFirst();
	}

	/**
	 * @param type
	 * @return Product response
	 * @implNote Function to get the data on the basis of 'Id' parameter.
	 */
	public List<Product> findProductDataByType(String type) {
		return products.stream().filter(p -> p.getType().toString().equalsIgnoreCase(type))
				.collect(Collectors.toList());
	}

	/**
	 * @param id
	 * @implNote Function to delete the data on the basis of 'Id' parameter.
	 */
	public void deleteProductDataById(String id) {
		Product product = findProductDataById(id)
				.orElseThrow(() -> new DataNotFoundException("Product data with id " + id + " not found!"));
		products.remove(product);
	}
}