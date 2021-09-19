package PaooGame.Items;

import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Pickable extends Terrain
{
    private BufferedImage image;       /*!< Referinta catre imaginea curenta a obiectului colectabil.*/

    public Pickable(RefLinks refLink, float x, float y, int width, int height, BufferedImage image)
    {
        super(refLink, x, y, width, height, image);
        this.image = image;
    }

    @Override
    public void Update()
    {

    }

    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(image, (int)x, (int)y, width, height, null);
        //g.setColor(Color.blue);
        //g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
    }
}
