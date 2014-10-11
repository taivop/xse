package ee.ut.math.tvt.TRAtarkvaratehnika;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;

class Window extends JFrame
{
	Window() throws IOException
	{
		setTitle("POS system"); 
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		//from application.properties
		Properties appProp = new Properties();
		FileInputStream appIn = new FileInputStream("application.properties");
		appProp.load(appIn);
		appIn.close();
		//from version.properties
		Properties verProp = new Properties();
		FileInputStream verIn = new FileInputStream("version.properties");
		verProp.load(verIn);
		verIn.close();
		setLayout(new GridBagLayout());
		String[] data = {
				"Team Name",
				appProp.getProperty("team_name").replaceAll("\"", ""),
		        "Team Leader",
		        appProp.getProperty("team_leader").replaceAll("\"", ""),
		        "Team Leader email",
		        appProp.getProperty("team_leader_email").replaceAll("\"", ""),
		        "Team members",
		        appProp.getProperty("team_members").replaceAll("\"", ""),
		        "Build number",
		        verProp.getProperty("build.number").replaceAll("\"", "")
		        };
		//Gridi asjade paigutamine.
		GridBagConstraints c = new GridBagConstraints();
	JLabel label1 = (new JLabel());
		Integer count = 0;
		for (int i=0;i<5;i++){
			c.gridy = i;
			for (int j=0;j<2;j++){
				c.gridx = j;
				if (j==0){
					c.weightx = 0.3;
				}
				else{
					c.weightx = 0.7;
				}
				label1 = new JLabel(data[count]);
				add(label1,c);
				count++;		
			}
		}
		//Pildi lisamine
		GridBagConstraints d = new GridBagConstraints();
		ImageIcon icon = new ImageIcon("rabbit.jpg");
		label1 = new JLabel(icon);
		d.fill = GridBagConstraints.HORIZONTAL;
		d.gridy = 5;
		d.gridwidth = 2;
		d.anchor = GridBagConstraints.CENTER;
		add(label1,d);
		setVisible(true);
	}
}
public class IntroUI
{
		public static void Window() throws IOException 
		{
			Window frame = new Window();			
		}
}