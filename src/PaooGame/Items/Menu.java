package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;
import PaooGame.States.State;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Menu extends Item{
    private BufferedImage image;       /*!< Referinta catre imaginea curenta a obiectului colectabil.*/
    private String helpString;
    private int hashtagX, hashtagY;

    private static Hero hero;

    private List<BufferedImage> letterImages  = Assets.letters;
    private List<BufferedImage> myItemsImages = Hero.getMyItemsImages();
    private List<String> myItemsString        = Hero.getMyItemsString();
    private List<String> optionsString        = new ArrayList<String>(3);
    private List<String> myStatsString        = new ArrayList<String>(5);

    private static boolean state;

    private boolean escFlag;
    private boolean releaseESCFlag;
    private boolean downFlag;
    private boolean releaseDOWNFlag;
    private boolean upFlag;
    private boolean releaseUPFlag;
    private boolean enterFlag;
    private boolean releaseENTERFlag;

    private boolean isInMenu;

    public Menu(RefLinks refLink)
    {
        super(refLink, 0, 0, 400, 400);
        image = ImageLoader.LoadImage("/Sprites/player/flowerRectangle.png");

        hero = refLink.getHero();

        optionsString.add("Resume Game");
        optionsString.add("Easy Level ");
        optionsString.add("Hard Level ");
        optionsString.add("Quit Game  ");
        helpString      = "     O-Resume Game   Enter-Select   Arrows-Move";
        //hashtag       = "#";
        hashtagX = 520;
        hashtagY = 200;

        escFlag = releaseESCFlag = false;

        state = false;

        isInMenu = true;
    }

    public static boolean getState()
    {
        return state;
    }

    public static void setState(boolean tmp)
    {
        state = tmp;
    }

    public void resetMenu()
    {
        hashtagY = 200;
        hashtagX = 520;
    }

    public int getHashtagX()
    {
        return hashtagX;
    }


    public int getHashtagY()
    {
        return hashtagY;
    }

    @Override
    public void Update()
    {

        if (refLink.GetKeyManager().esc == true)
        {
            escFlag = true;
            //System.out.println("esc is held");
        }
        else if (releaseESCFlag == true)
        {
            //System.out.println("esc is released");
            escFlag = false;
            releaseESCFlag = false;
        }
        if (escFlag == true && releaseESCFlag == false)
        {
            releaseESCFlag = true;
        }

        // Down
        if (refLink.GetKeyManager().down == true)
        {
            downFlag = true;
            //System.out.println("down is held");
        }
        else if (releaseDOWNFlag == true)
        {
            downFlag = false;
            releaseDOWNFlag = false;
            //System.out.println("down is released");
        }
        if (downFlag == true && releaseDOWNFlag == false)
        {
            releaseDOWNFlag = true;
            //System.out.println("down a fost apasat in meniu");

            if (hashtagY < 400)
                hashtagY += 75;
        }

        //Up
        if (refLink.GetKeyManager().up == true)
        {
            upFlag = true;
            //System.out.println("yp is held");
        }
        else if (releaseUPFlag == true)
        {
            upFlag = false;
            releaseUPFlag = false;
            //System.out.println("yp is released");
        }
        if (upFlag == true && releaseUPFlag == false)
        {
            releaseUPFlag = true;
            //System.out.println("yp a fost apasat in meniu");

            if (hashtagY > 200)
                hashtagY -= 75;
        }

    }

    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(image, (int)x, (int)y, refLink.GetWidth(), refLink.GetHeight(), null);
        for(int i = 0; i < helpString.length(); ++i)
        {
            char character = helpString.charAt(i);
            int index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), 230+i*19, 550, 28, 44, null);
        }

        int tmp;
        char character;
        int index;

        tmp = 0;
        for(String myString : optionsString)
        {
            character = '#';
            index = Item.getSymbolIndex(character);
            g.drawImage(letterImages.get(index), hashtagX, hashtagY, 60, 66, null);

            for(int i = 0; i < myString.length(); ++i)
            {
                character = myString.charAt(i);
                index = Item.getSymbolIndex(character);

                g.drawImage(letterImages.get(index), 600+i*27, 200+tmp*75, 40, 66, null);
            }
            ++tmp;
        }
    }

}
