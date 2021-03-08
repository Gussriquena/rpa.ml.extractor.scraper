package rpa.ml.extractor.main;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import rpa.ml.extractor.controller.MercadoLivreController;

public class Main {
	private static Logger log = Logger.getLogger(Main.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("resources\\log4j.properties");
		
		log.info("Starting 'Mercado Livre extractor' flow");
		
		MercadoLivreController mercadoLivreController = new MercadoLivreController();
		mercadoLivreController.initFlow();
	}
}
