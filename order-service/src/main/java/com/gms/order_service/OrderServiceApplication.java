package com.gms.order_service;

import com.gms.order_service.entity.Product;
import com.gms.order_service.entity.Stock;
import com.gms.order_service.entity.User;
import com.gms.order_service.repository.ProductRepo;
import com.gms.order_service.repository.StockRepo;
import com.gms.order_service.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderServiceApplication implements CommandLineRunner {

    public OrderServiceApplication(UserService userService, ProductRepo productRepo, StockRepo stokRepo) {
        this.userService = userService;
        this.productRepo = productRepo;
        this.stockRepo = stokRepo;
    }

    public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}
	private final UserService userService;
	private final ProductRepo productRepo;
	private final StockRepo stockRepo;
	@Override
	public void run(String... args) throws Exception {
		User user = new User(1,100,"dinoekin@gmail.com",true);
		Product product = new Product(1,"tarak",10);
		userService.save(user);
		productRepo.save(product);
		stockRepo.save(new Stock(1,product,3));



	}
}
