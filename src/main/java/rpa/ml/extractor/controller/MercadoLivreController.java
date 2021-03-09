package rpa.ml.extractor.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rpa.ml.extractor.constants.PageEnum;
import rpa.ml.extractor.model.Product;

public class MercadoLivreController {

	private static Logger log = Logger.getLogger(MercadoLivreController.class);

	public void initFlow() {

		List<Product> products = loadProducts();
		products = createProductSearch(products);
		
		for (Product product : products) {
			log.info(product.getName() + " " + product.getPrice() + " " + product.getSalesAmount());
		}

	}

	private List<Product> loadProducts() {
		List<Product> products = new ArrayList<>();

		products.add(new Product(1, "Xiaomi Redmi note 9 pro", "", ""));

		return products;
	}

	private List<Product> createProductSearch(List<Product> products) {
		try {
			for (Product product : products) {
				String urlSearch = PageEnum.URL_ML_SEARCH.getValue() + product.getName().replaceAll(" ", "-");
				
				log.info("Connecting: " + urlSearch);
				Document document = Jsoup.connect(urlSearch).get();

				Elements linksSearchPage = document.getElementsByClass("ui-search-layout").select(".ui-search-result__image").select(".ui-search-link").select("a[href]");
				
				for (Element element : linksSearchPage) {
					document = Jsoup.connect(element.attr("href")).get();
					String salesAmount = document.select(".ui-pdp-header").select(".ui-pdp-subtitle").first().text();
					
					if (salesAmount.contains("vendido") || salesAmount.contains("vendidos")) {
						String price = document.select(".ui-pdp-price__second-line").select(".price-tag-fraction").first().text();
						salesAmount = salesAmount.replaceAll("\\D", "");
						products.add(new Product(1, product.getName(), price, salesAmount));
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		
		return products;
	}
}
