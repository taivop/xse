package ee.ut.math.tvt.TRAtarkvaratehnika;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


class IntroUI extends JFrame {
	
	IntroUI() throws IOException {
		setTitle("POS system");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		// from application.properties
		Properties appProp = new Properties();
		InputStream appIn = IntroUI.class.getResourceAsStream("/application.properties");
		//FileInputStream appIn = new FileInputStream("application.properties");
		appProp.load(appIn);
		appIn.close();
		// from version.properties
		Properties verProp = new Properties();
		InputStream verIn = IntroUI.class.getResourceAsStream("/version.properties");
		//FileInputStream verIn = new FileInputStream("version.properties");
		verProp.load(verIn);
		verIn.close();
		// setting layout
		setLayout(new GridBagLayout());
		String[] data = { "Team Name",
				appProp.getProperty("team_name").replaceAll("\"", ""),
				"Team Leader",
				appProp.getProperty("team_leader").replaceAll("\"", ""),
				"Team Leader email",
				appProp.getProperty("team_leader_email").replaceAll("\"", ""),
				"Team members",
				appProp.getProperty("team_members").replaceAll("\"", ""),
				"Build number",
				verProp.getProperty("build.number").replaceAll("\"", "") };
		// placing labels
		GridBagConstraints c = new GridBagConstraints();
		JLabel label1 = (new JLabel());
		Integer count = 0;
		for (int i = 0; i < 5; i++) {
			c.gridy = i;
			for (int j = 0; j < 2; j++) {
				c.gridx = j;
				if (j == 0) {
					c.weightx = 0.3;
				} else {
					c.weightx = 0.7;
				}
				label1 = new JLabel(data[count]);
				add(label1, c);
				count++;
			}
		}
		// adding team logo
		GridBagConstraints constr = new GridBagConstraints();
		URL imageURL = IntroUI.class.getResource("/rabbit.jpg");
		ImageIcon icon = new ImageIcon(imageURL);
		label1 = new JLabel(icon);
		constr.fill = GridBagConstraints.HORIZONTAL;
		constr.gridy = 5;
		constr.gridwidth = 2;
		constr.insets = new Insets(20, 0, 0, 0);
		add(label1, constr);
		setVisible(true);
	}
	
	
//}

//public class IntroUI {
	//public static void Window() throws IOException {
	//	Window frame = new Window();
	//}
}