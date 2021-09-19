package PaooGame.Graphics;
import PaooGame.Items.Hero;
import PaooGame.RefLinks;

public class Camera
{
    private float x;
    private float y;
    private RefLinks reflink;
    private Boolean isNewLevel;

    public Camera(float x, float y, RefLinks r)
    {
        this.x = x;
        this.y = y;
        reflink = r;
        isNewLevel = true;
    }

    public void Update(Hero hero)
    {
        int widthOfMap  = reflink.GetMap().getWidth()  *64; // 64 = width dala
        int heightOfMap = reflink.GetMap().getHeight() *64; // 64 = height dala

        int restX = widthOfMap  - reflink.GetWidth();
        int restY = heightOfMap - reflink.GetHeight();

        //                                     <-----> restX = widthOfMap - reflink.GetWidth();
        // <---------------------------------->        reflink.getWidth() aka width of camera
        // <-----------------------------------------> widthOfMap
        x = hero.GetX() + 16 - reflink.GetWidth() / 2; // midX
        if (hero.GetX() + 16 < (widthOfMap - restX)/2) // left
            x = 0;
        if (hero.GetX() + 16 > (widthOfMap + restX)/2) // right
            x = restX;

        y = hero.GetY() + 32 - reflink.GetHeight() / 2; // midY
        if (hero.GetY() + 32 < (heightOfMap - restY)/2) // up
            y = 0;
        if (hero.GetY() + 32 > (heightOfMap + restY)/2) // down
            y = restY;

    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

}
