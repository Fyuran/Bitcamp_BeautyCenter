package com.centro.estetico.test.bitcamp;

import java.math.BigDecimal;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Product;
import com.centro.estetico.bitcamp.ProductCat;

public class ProductTest {
    public static void main(String[] args) {
		Main main = new Main("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "gen1chir0Takahashi");

    	//generazione test di prodotti, grazie chatgpt
        // Create an array of products
        Product[] products = new Product[10];

        // Populate the array with sample products
        products[0] = new Product("Dentifricio", 50, 10, new BigDecimal("3.99"), 22.0, ProductCat.ORAL_CARE, true);
        products[1] = new Product("Crema Idratante", 30, 5, new BigDecimal("15.99"), 10.0, ProductCat.SKIN_CARE, true);
        products[2] = new Product("Shampoo Nutriente", 40, 8, new BigDecimal("8.50"), 22.0, ProductCat.HAIR_CARE, true);
        products[3] = new Product("Balsamo Per Capelli", 20, 3, new BigDecimal("10.00"), 22.0, ProductCat.HAIR_CARE, true);
        products[4] = new Product("Crema Solare", 25, 4, new BigDecimal("18.99"), 10.0, ProductCat.SKIN_CARE, true);
        products[5] = new Product("Rossetto", 60, 15, new BigDecimal("12.00"), 22.0, ProductCat.COSMETICS, true);
        products[6] = new Product("Profumo Femminile", 15, 2, new BigDecimal("50.00"), 22.0, ProductCat.PERFUMES, true);
        products[7] = new Product("Deodorante", 35, 6, new BigDecimal("5.50"), 22.0, ProductCat.BODY_CARE, true);
        products[8] = new Product("Scrub Corpo", 20, 3, new BigDecimal("19.99"), 5.0, ProductCat.BODY_CARE, true);
        products[9] = new Product("Gel Antibatterico", 45, 7, new BigDecimal("4.99"), 0.0, ProductCat.OHER, true);

        // Insert each product into the database
        for (Product product : products) {
            int result = Product.insertData(product);
            if (result > 0) {
                System.out.println("Product inserted successfully: " + product.getName());
            } else {
                System.out.println("Failed to insert product: " + product.getName());
            }
        	System.out.println(product);
        }
    }

}
