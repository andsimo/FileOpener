import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 * Skapar en statisk bild h�mtad fr�n Google Maps, utifr�n inskickad latitud och longitud.
 * 
 * OBS! K�R ENDAST MED EN FIL I TAGET!!!!!!
 * 
 */
public class StaticMapCreator {

	public StaticMapCreator(){
	
	}
	
	public void createMap(double latitude, double longitude){
	JFrame test = new JFrame("Google Maps");

        try {
        	//�ndra zoom=x (1 - 12) f�r zooma n�rmare. 
        	String imageUrl = "https://maps.googleapis.com/maps/api/staticmap?center=" + latitude + "," + longitude + "&zoom=12&size=612x612&scale=2&maptype=roadmap";            
        	String destinationFile = "image.jpg";
            URL url = new URL(imageUrl);
            InputStream is = url.openStream();
            OutputStream os = new FileOutputStream(destinationFile);

            byte[] b = new byte[2048];
            int length;

            while ((length = is.read(b)) != -1) {
                os.write(b, 0, length);
            }

            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        test.add(new JLabel(new ImageIcon((new ImageIcon("image.jpg")).getImage().getScaledInstance(630, 600,
                java.awt.Image.SCALE_SMOOTH))));

        test.setVisible(true);
        test.pack();

    }
}