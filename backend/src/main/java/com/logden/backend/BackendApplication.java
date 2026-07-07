package com.logden.backend;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.logden.backend.domain.Category;
import com.logden.backend.domain.CategoryRepository;
import com.logden.backend.domain.Product;
import com.logden.backend.domain.ProductRepository;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
CommandLineRunner loadData(ProductRepository productRepository,
                           CategoryRepository categoryRepository) {
    return args -> {

        if (productRepository.count() >= 0) {

            Category decoration = new Category("Puukoristeet", "Koristeeksi luotuja puuesineitä");
            categoryRepository.save(decoration);

            productRepository.save(new Product(
                    "Puulehmä",
                    "Vuoltu puulehmä",
                    new BigDecimal("299.90"),
                    20,
                    true,
                    decoration
            ));

            productRepository.save(new Product(
                    "Puusaapas",
                    "Pölkystä muotoiltu saapas",
                    new BigDecimal("499.90"),
                    15,
                    true,
                    decoration
            ));
        }
    };
}

}
