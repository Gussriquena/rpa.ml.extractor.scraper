package rpa.ml.extractor.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import rpa.ml.extractor.constants.PageEnum;
import rpa.ml.extractor.model.Product;
import rpa.ml.extractor.model.ProductOutput;

public class MercadoLivreController {

	private static Logger log = Logger.getLogger(MercadoLivreController.class);

	public void initFlow() {
		List<ProductOutput> productsOutput;
		ExcelController excelController = new ExcelController();
		
		List<Product> productsInput = excelController.readRowsExcel();
		
		if (productsInput.size() != 0) {
			productsOutput = collectProductsData(productsInput);
			
		} else {
			log.warn("No products found for search!");
		}
	}

	private List<ProductOutput> collectProductsData(List<Product> products) {
		List<ProductOutput> productOutput = new ArrayList<>();
		
		try {
			for (Product product : products) {
				String urlSearch = PageEnum.URL_ML_SEARCH.getValue() + product.getName().replaceAll(" ", "-");

				log.info("Connecting: " + urlSearch);
				Document document = Jsoup.connect(urlSearch).get();
				Elements linksSearchPage = document.getElementsByClass("ui-search-layout")
						.select(".ui-search-result__image")
						.select(".ui-search-link")
						.select("a[href]");

				for (Element element : linksSearchPage) {
					document = Jsoup.connect(element.attr("href")).get();
					String salesAmount = document.select(".ui-pdp-header")
							.select(".ui-pdp-subtitle")
							.first()
							.text();

					if (salesAmount.contains("vendido") || salesAmount.contains("vendidos")) {
						String price = document
								.select(".ui-pdp-price__second-line").select(".price-tag-fraction")
								.first()
								.text();
						
						salesAmount = salesAmount.replaceAll("\\D", "");
						productOutput.add(new ProductOutput(product.getName(), new BigDecimal(price), Integer.parseInt(salesAmount)));
						log.info(product.getName() + " " + price + " " + salesAmount);
					}
				}
			}
			
			log.info("Process ended");
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return productOutput;
	}
	
}
