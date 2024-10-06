package com.centro.estetico.test.bitcamp;

import java.math.BigDecimal;

import com.centro.estetico.bitcamp.Main;
import com.centro.estetico.bitcamp.Product;
import com.centro.estetico.bitcamp.ProductCat;

import DAO.ProductDAO;
import DAO.VATDao;

public class ProductTest {
    public static void main(String[] args) {
		Main main = new Main("jdbc:mysql://localhost:3306/beauty_centerdb", "root", "gen1chir0Takahashi");

    	//generazione test di prodotti, grazie chatgpt
        // Create an array of products
        Product[] products = new Product[10];

        // Populate the array with sample products
        products[0] = new Product("Dentifricio", 50, 10, new BigDecimal("3.99"), VATDao.getVAT(1).get(), ProductCat.ORAL_CARE);
        products[1] = new Product("Crema Idratante", 30, 5, new BigDecimal("15.99"), VATDao.getVAT(2).get(), ProductCat.SKIN_CARE);
        products[2] = new Product("Shampoo Nutriente", 40, 8, new BigDecimal("8.50"), VATDao.getVAT(3).get(), ProductCat.HAIR_CARE);
        products[3] = new Product("Balsamo Per Capelli", 20, 3, new BigDecimal("10.00"), VATDao.getVAT(1).get(), ProductCat.HAIR_CARE);
        products[4] = new Product("Crema Solare", 25, 4, new BigDecimal("18.99"), VATDao.getVAT(2).get(), ProductCat.SKIN_CARE);
        products[5] = new Product("Rossetto", 60, 15, new BigDecimal("12.00"), VATDao.getVAT(2).get(), ProductCat.COSMETICS);
        products[6] = new Product("Profumo Femminile", 15, 2, new BigDecimal("50.00"), VATDao.getVAT(1).get(), ProductCat.PERFUMES);
        products[7] = new Product("Deodorante", 35, 6, new BigDecimal("5.50"), VATDao.getVAT(3).get(), ProductCat.BODY_CARE);
        products[8] = new Product("Scrub Corpo", 20, 3, new BigDecimal("19.99"), VATDao.getVAT(1).get(), ProductCat.BODY_CARE);
        products[9] = new Product("Gel Antibatterico", 45, 7, new BigDecimal("4.99"), VATDao.getVAT(1).get(), ProductCat.OTHER);

        // Insert each product into the database
//        for (Product product : products) {
//            int result = Product.insertData(product);
//            if (result > 0) {
//                System.out.println("Product inserted successfully: " + product.getName());
//            } else {
//                System.out.println("Failed to insert product: " + product.getName());
//            }
//        	System.out.println(product);
//        }

        ProductDAO.insertProduct(products[9]);
    }

}
