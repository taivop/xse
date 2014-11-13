package ee.ut.math.tvt.TRAtarkvaratehnika;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.domain.controller.impl.SalesDomainControllerImpl;
import ee.ut.math.tvt.salessystem.ui.ConsoleUI;
import ee.ut.math.tvt.salessystem.ui.SalesSystemUI;

public class Intro{

	private static final Logger log = Logger.getLogger(Intro.class);
	private static final String MODE = "console";


	public static void main(String[] args) {
		//DOMConfigurator.configure("log4j.xml");		// TODO doesn't work in JAR; need to use relative path or sth
		final SalesDomainController domainController = new SalesDomainControllerImpl();

		if (args.length == 1 && args[0].equals(MODE)) {
			log.debug("Mode: " + MODE);

			ConsoleUI cui = new ConsoleUI(domainController);
			cui.run();
		} else {

			try { IntroUI introUI = new IntroUI();
			introUI.setVisible(true);
			introUI.setAlwaysOnTop(true); 
			log.info("Intro window opened");


			final SalesSystemUI ui = new SalesSystemUI(domainController);
			ui.setVisible(true);

			introUI.setAlwaysOnTop(false);
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			introUI.setVisible(false);}
	
			catch (IOException e2) 
			{ e2.printStackTrace(); }
		}
	}
}

/*
 * public class Intro { private static final Logger log =
 * Logger.getLogger(Intro.class);
 * 
 * public static void main(String[] args) { // Init. logger Logger log =
 * Logger.getLogger(Intro.class); DOMConfigurator.configure("etc/log4j.xml");
 * //BasicConfigurator.configure();
 * 
 * try { IntroUI.Window(); } catch (IOException e) { e.printStackTrace(); }
 * log.info("Intro window opened"); } }
 */