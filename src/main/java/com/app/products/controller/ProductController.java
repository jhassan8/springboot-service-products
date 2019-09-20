package com.app.products.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.app.products.models.entity.Product;
import com.app.products.models.service.IProductService;

@RestController
public class ProductController {

	private IProductService iProductService;
	private Environment envirenment;
	
	@Value("${server.port}")
	private Integer port;
	
	@Autowired
	public ProductController(IProductService iProductService, Environment envirenment) {
		this.iProductService = iProductService;
		this.envirenment = envirenment;
	}
	
	@GetMapping("/products")
	public List<Product> products() {
		return this.iProductService.findAll().stream().map(p -> {
			//p.setPort(Integer.parseInt(envirenment.getProperty("local.server.port")));
			p.setPort(port);
			return p;
		}).collect(Collectors.toList());
	}
	
	@GetMapping("/product/{id}")
	public Product product(@PathVariable Long id) {
		Product product = this.iProductService.findById(id);
		//product.setPort(Integer.parseInt(envirenment.getProperty("local.server.port")));
		product.setPort(port);
		return product;
	}
	
}