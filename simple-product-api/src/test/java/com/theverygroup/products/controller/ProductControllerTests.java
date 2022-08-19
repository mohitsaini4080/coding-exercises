package com.theverygroup.products.controller;

import com.theverygroup.products.dto.Price;
import com.theverygroup.products.dto.Product;
import com.theverygroup.products.dto.Type;
import com.theverygroup.products.exceptions.DataAlreadyExistException;
import com.theverygroup.products.exceptions.DataNotFoundException;
import com.theverygroup.products.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductRepository productRepository;

	//Declaring global variables
	public Price price;

	public Product product;

	String Id = "CLN-CDE-BOOK";

	String request = "{\"id\":\"CLN-CDE-BOOK\"," + "\"name\":\"Clean Code\","
			+ "\"description\":\"Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)\","
			+ "\"price\":{\"value\":18.99,\"currency\":\"GBP\"}," + "\"type\":\"Book\","
			+ "\"department\":\"Books and Stationery\"," + "\"weight\":\"220g\"}";

	@BeforeEach
	public void setUp() {
		price = Price.builder().value(new BigDecimal("18.99")).currency("GBP").build();
		product = Product.builder().id(Id).name("Clean Code")
				.description("Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)").price(price)
				.type(Type.BOOKS).department("Books and Stationery").weight("220g").build();

		when(productRepository.findAll()).thenReturn(Arrays.asList(product));
	}

	@Test
	public void testFindAll() throws Exception {
		String expected = "[{\"id\":\"CLN-CDE-BOOK\"," + "\"name\":\"Clean Code\","
				+ "\"description\":\"Clean Code: A Handbook of Agile Software Craftsmanship (Robert C. Martin)\","
				+ "\"price\":{\"value\":18.99,\"currency\":\"GBP\"}," + "\"type\":\"Book\","
				+ "\"department\":\"Books and Stationery\"," + "\"weight\":\"220g\"}]";

		mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk()).andExpect(content().json(expected));
	}

	/**
	 * Function to Test /getProductByType end-point using Type as "Electrical"
	 */
	@Test
	public void testFindProductDataByType() throws Exception {
		when(productRepository.findProductDataByType(anyString())).thenReturn(Arrays.asList(product));
		mockMvc.perform(get("/getProductByType/?Type=").queryParam("Type", "Electrical")).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").exists())
				.andExpect(jsonPath("$[0].type").exists()).andExpect(jsonPath("$[0].type").value("Book"));
	}

	/**
	 * Function to Test /saveData end-point.
	 */
	@Test
	public void testSaveProductData() throws Exception {
		when(productRepository.saveProductData(any(Product.class))).thenReturn(product);
		mockMvc.perform(post("/saveData").accept(MediaType.APPLICATION_JSON).content(request)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Function to Test /getProductById end-point on the basis of 'id' parameter.
	 */
	@Test
	public void testFindProductDataById() throws Exception {
		when(productRepository.findProductDataById(anyString())).thenReturn(Optional.ofNullable(product));
		mockMvc.perform(get("/getProductById/?Id=" + "CLN-CDE-BOOK")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.id").value(Id));
	}

	/**
	 * Function to Test /deleteProductById end-point on the basis of 'id' parameter.
	 */
	@Test
	public void testDeleteProductData() throws Exception {
		doNothing().when(productRepository).deleteProductDataById(anyString());
		mockMvc.perform(delete("/deleteProductById/?Id=" + Id)).andDo(print()).andExpect(status().isOk());
	}

	/**
	 * Function to Test /deleteProductById end-point with a negative scenario when
	 * data is not found on the basis of 'id' parameter.
	 */
	@Test
	public void testDeleteProductDataNotFound() throws Exception {
		doThrow(DataNotFoundException.class).when(productRepository).deleteProductDataById(anyString());
		mockMvc.perform(delete("/deleteProductById/?Id=" + Id)).andDo(print()).andExpect(status().isBadRequest());
	}

	/**
	 * Function to Test /saveData end-point with a negative scenario when product
	 * with the same 'id' already present.
	 */
	@Test
	public void testSaveProductDataAlreadyPresent() throws Exception {
		doThrow(DataAlreadyExistException.class).when(productRepository).saveProductData(any(Product.class));
		mockMvc.perform(post("/saveData").accept(MediaType.APPLICATION_JSON).content(request)
				.contentType(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isBadRequest());
	}

	/**
	 * Function to Test /getProductById end-point with a negative scenario when data
	 * is not found on the basis of 'id' parameter.
	 */
	@Test
	public void testFindProductDataNotFoundById() throws Exception {
		doThrow(DataNotFoundException.class).when(productRepository).findProductDataById(anyString());
		mockMvc.perform(get("/getProductById/?Id=" + Id)).andDo(print()).andExpect(status().isBadRequest());
	}
}