
 
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
 
public class Tools {
	public static final int MAP_X = 1080;
	public static final int MAP_Y = 720;
	public static final int SCREEN_X = 1080;
	public static final int SCREEN_Y = 720;
	public static final String GameName = "ssnake1.0.0";
 
	public final static int FOODS_TYPE_SUM = 1;
	public final static int FOODS_SUM = 5;
	public final static int SNAKE_STYLE_SUM = 1;
	public final static int SNAKE__SUM = 10;
	
	/**
	 * 
	 * 
	 * @param bufferedimage
	 *            
	 * @param degree
	 *           
	 * @return
	 */
	public final static BufferedImage rotateImage(final BufferedImage bufferedimage, final int degree) {
		int w = bufferedimage.getWidth();
		int h = bufferedimage.getHeight();
		int type = bufferedimage.getColorModel().getTransparency();
		BufferedImage img;
		Graphics2D graphics2d;
		(graphics2d = (img = new BufferedImage(w, h, type)).createGraphics())
				.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		graphics2d.rotate(Math.toRadians(degree), w / 2, h / 2);
		graphics2d.drawImage(bufferedimage, 0, 0, null);
		graphics2d.dispose();
		return img;
	}
 
}
