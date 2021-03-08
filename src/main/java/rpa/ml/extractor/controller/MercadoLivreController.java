package rpa.ml.extractor.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import rpa.ml.extractor.constants.PageEnum;
import rpa.ml.extractor.model.Product;

public class MercadoLivreController {

	private static Logger log = Logger.getLogger(MercadoLivreController.class);

	public void initFlow() {

		List<Product> products = loadProducts();
		createProductSearch(products);

	}

	private List<Product> loadProducts() {
		List<Product> products = new ArrayList<>();

		products.add(new Product(1, "Xiaomi Redmi note 9 pro", "", ""));

		return products;
	}

	private void createProductSearch(List<Product> products) {
		try {
			for (Product product : products) {
				log.info("Acessando: " + PageEnum.URL_ML_SEARCH.getValue() + product.getName().replaceAll(" ", "-"));
				Document doc = Jsoup.connect(PageEnum.URL_ML_SEARCH.getValue() + product.getName().replaceAll(" ", "-")).get();
				log.info(doc.body());
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	}
}
