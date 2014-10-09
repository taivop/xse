package ee.ut.math.tvt.TRAtarkvaratehnika;

import java.io.IOException;
//import org.apache.log4j.Logger;

public class Intro {
	//private static final Logger log = Logger.getLogger(Intro.class);
	public static void main(String[] args) {
		// Initialise the logger
	//	Logger log = Logger.getLogger(Intro.class);
		try {
			IntroUI.Window();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//log.info("UI Window started!");
	}
}
