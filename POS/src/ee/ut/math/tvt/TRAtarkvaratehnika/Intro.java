package ee.ut.math.tvt.TRAtarkvaratehnika;

import java.io.IOException;
<<<<<<< HEAD
=======

import org.apache.log4j.BasicConfigurator;
>>>>>>> branch 'master' of https://github.com/taivop/xse.git
import org.apache.log4j.Logger;

public class Intro {
	private static final Logger log = Logger.getLogger(Intro.class);
	
	public static void main(String[] args) {
		// Initialise the logger
		Logger log = Logger.getLogger(Intro.class);
<<<<<<< HEAD
=======
		BasicConfigurator.configure();
		
>>>>>>> branch 'master' of https://github.com/taivop/xse.git
		try {
			IntroUI.Window();
		} catch (IOException e) {
			e.printStackTrace();
		}
<<<<<<< HEAD
=======
		
>>>>>>> branch 'master' of https://github.com/taivop/xse.git
		log.info("UI Window started!");
	}
}
