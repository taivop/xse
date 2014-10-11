package ee.ut.math.tvt.TRAtarkvaratehnika;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class Intro {
	private static final Logger log = Logger.getLogger(Intro.class);

	public static void main(String[] args) {
		// Init. logger
		Logger log = Logger.getLogger(Intro.class);
		DOMConfigurator.configure("etc/log4j.xml");
		//BasicConfigurator.configure();
		
		try {
			IntroUI.Window();
		} catch (IOException e) {
			e.printStackTrace();
		}
		log.info("Intro window opened");
	}
}
