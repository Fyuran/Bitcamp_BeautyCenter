package com.bitcamp.centro.estetico.test;

import java.math.BigDecimal;

import com.bitcamp.centro.estetico.DAO.ProductDAO;
import com.bitcamp.centro.estetico.DAO.VAT_DAO;
import com.bitcamp.centro.estetico.models.Main;
import com.bitcamp.centro.estetico.models.Product;
import com.bitcamp.centro.estetico.models.ProductCat;

public class ProductTest {
    public static void main(String[] args) {
		new Main("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "gen1chir0Takahashi");

    	//generazione test di prodotti, grazie chatgpt
        // Create an array of products
        Product[] products = new Product[10];

        // Populate the array with sample products
        products[0] = new Product("Dentifricio", 50, 10, new BigDecimal("3.99"), VAT_DAO.getInstance().get(1).get(), ProductCat.ORAL_CARE);
        products[1] = new Product("Crema Idratante", 30, 5, new BigDecimal("15.99"), VAT_DAO.getInstance().get(2).get(), ProductCat.SKIN_CARE);
        products[2] = new Product("Shampoo Nutriente", 40, 8, new BigDecimal("8.50"), VAT_DAO.getInstance().get(3).get(), ProductCat.HAIR_CARE);
        products[3] = new Product("Balsamo Per Capelli", 20, 3, new BigDecimal("10.00"), VAT_DAO.getInstance().get(1).get(), ProductCat.HAIR_CARE);
        products[4] = new Product("Crema Solare", 25, 4, new BigDecimal("18.99"), VAT_DAO.getInstance().get(2).get(), ProductCat.SKIN_CARE);
        products[5] = new Product("Rossetto", 60, 15, new BigDecimal("12.00"), VAT_DAO.getInstance().get(2).get(), ProductCat.COSMETICS);
        products[6] = new Product("Profumo Femminile", 15, 2, new BigDecimal("50.00"), VAT_DAO.getInstance().get(1).get(), ProductCat.PERFUMES);
        products[7] = new Product("Deodorante", 35, 6, new BigDecimal("5.50"), VAT_DAO.getInstance().get(3).get(), ProductCat.BODY_CARE);
        products[8] = new Product("Scrub Corpo", 20, 3, new BigDecimal("19.99"), VAT_DAO.getInstance().get(1).get(), ProductCat.BODY_CARE);
        products[9] = new Product("Gel Antibatterico", 45, 7, new BigDecimal("4.99"), VAT_DAO.getInstance().get(1).get(), ProductCat.OTHER);

         //Insert each product into the database
        for (Product product : products) {
            ProductDAO.getInstance().insert(product);

        	System.out.println(product);
        }

        ProductDAO.getInstance().insert(products[9]);
    }

}
