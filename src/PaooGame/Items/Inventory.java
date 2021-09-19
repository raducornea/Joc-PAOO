package PaooGame.Items;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.RefLinks;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Inventory extends Item{
    private BufferedImage image;       /*!< Referinta catre imaginea curenta a obiectului colectabil.*/
    private String helpString;
    private int hashtagX, hashtagY;

    private static Hero hero;

    private List<BufferedImage> letterImages  = Assets.letters;
    private List<BufferedImage> myItemsImages = Hero.getMyItemsImages();
    private List<String> myItemsString        = Hero.getMyItemsString();
    private List<String> optionsString        = new ArrayList<String>(3);
    private List<String> myStatsString        = new ArrayList<String>(5);
    private List<String> myObjectivesString   = new ArrayList<String>(3);
    private static boolean state;

    private boolean escFlag;
    private boolean releaseESCFlag;
    private boolean downFlag;
    private boolean releaseDOWNFlag;
    private boolean upFlag;
    private boolean releaseUPFlag;
    private boolean enterFlag;
    private boolean releaseENTERFlag;

    private String page;

    public Inventory(RefLinks refLink)
    {
        super(refLink, 0, 0, 400, 400);
        image = ImageLoader.LoadImage("/Sprites/player/flowerRectangle.png");

        hero = refLink.getHero();

        optionsString.add("Items      ");
        optionsString.add("My Stats   ");
        optionsString.add("Objectives ");
        helpString      = "E-Exit    Enter-Select    Arrows-Move    ESC-Back";
        //hashtag       = "#";
        hashtagX = 520;
        hashtagY = 200;

        myStatsString.add("Total Life:   ");
        myStatsString.add("Attack Damage:");
        myStatsString.add("Difficulty:   ");
        myStatsString.add("Mobs Greeded: ");
        myStatsString.add("Mobs Pacified:");

        myObjectivesString.add("1. GreeD everyone you meet. OR");
        myObjectivesString.add("2. Peacify. OR");
        myObjectivesString.add("3. Do whatever you want.");

        escFlag = releaseESCFlag = false;
        page = "First Page";

        state = false;
    }

    public static boolean getState()
    {
        return state;
    }

    public static void setState(boolean tmp)
    {
        state = tmp;
    }

    public String getPage()
    {
        return page;
    }

    public void resetInventory()
    {
        page = "First Page";
        hashtagY = 200;
        hashtagX = 520;
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
            if (page != "First Page")
                page = "First Page";
        }
        if (escFlag == true && releaseESCFlag == false)
        {
            releaseESCFlag = true;
        }

        switch (page)
        {
            case "First Page":

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
                    //System.out.println("down a fost apasat in inventar");

                    if (hashtagY < 400)
                        hashtagY += 100;
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
                    //System.out.println("yp a fost apasat in inventar");

                    if (hashtagY > 200)
                        hashtagY -= 100;
                }

                //Enter
                if (refLink.GetKeyManager().enter == true)
                {
                    enterFlag = true;

                }
                else if (releaseENTERFlag == true)
                {
                    enterFlag = false;
                    releaseENTERFlag = false;

                }
                ///ENTER toggled OFF/ON
                if (enterFlag == true && releaseENTERFlag == false)
                {
                    releaseENTERFlag = true;

                    // 3 cazuri pentru hashtagY: 200, 300, 400
                    //System.out.println(hashtagY);

                    switch(hashtagY)
                    {
                        case 200:
                            page = "Items";
                            break;

                        case 300:
                            page = "My Stats";
                            break;

                        case 400:
                            page = "Objectives";
                            break;

                        default:
                            break;
                    }
                }
                break;

            case "Items":
                break;

            case "My Stats":
                for (String i : myStatsString)
                {
                    if (i.contains("Total Life:"))
                    {
                        int aux;
                        try
                        {
                            aux = RefLinks.getHero().getLife();
                        }
                        catch(NullPointerException e)
                        {
                            aux = 0;
                        }
                        i = "Total Life:    " + aux;
                        myStatsString.set(0, i);
                    }
                    if (i.contains("Attack Damage:"))
                    {
                        int aux;
                        try
                        {
                            aux = RefLinks.getHero().getAttackDamage();
                        }
                        catch(NullPointerException e)
                        {
                            aux = 0;
                        }
                        i = "Attack Damage: " + aux;
                        myStatsString.set(1, i);
                    }
                    if (i.contains("Difficulty:"))
                    {
                        int aux = RefLinks.level;
                        i = "Difficulty:    " + aux;
                        myStatsString.set(2, i);
                    }
                    if (i.contains("Mobs Greeded:"))
                    {
                        int aux;
                        try
                        {
                            aux = RefLinks.getHero().getGreeded();
                        }
                        catch(NullPointerException e)
                        {
                            aux = 0;
                        }
                        i = "Mobs Greeded:  " + aux;
                        myStatsString.set(3, i);
                    }
                    if (i.contains("Mobs Pacified:"))
                    {
                        int aux;
                        try
                        {
                            aux =  RefLinks.getHero().getAbsconded();
                        }
                        catch(NullPointerException e)
                        {
                            aux = 0;
                        }
                        i = "Mobs Pacified: " + aux;
                        myStatsString.set(4, i);
                    }

                }
                break;

            case "Objectives":
                break;

            default:
                break;
        }
    }

    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(image, (int)x, (int)y, refLink.GetWidth(), refLink.GetHeight(), null);
        for(int i = 0; i < helpString.length(); ++i)
        {
            // 'E' -> OrdineLitere -> 4 -> | A B C D *E* ...  |  -> desenez unde vreau
            char character = helpString.charAt(i);
            int index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), 230+i*19, 550, 28, 44, null);
        }

        int tmp;
        char character;
        int index;
        switch (page)
        {
            case "First Page":
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

                        g.drawImage(letterImages.get(index), 600+i*27, 200+tmp*100, 40, 66, null);
                    }
                    ++tmp;
                }
                break;

            case "Items":

                break;


            case "My Stats":
                tmp = 0;
                for(String myString : myStatsString)
                {
                    for(int i = 0; i < myString.length(); ++i)
                    {
                        character = myString.charAt(i);
                        index = Item.getSymbolIndex(character);

                        g.drawImage(letterImages.get(index), 250+i*27, 200+tmp*55, 40, 52, null);
                    }
                    ++tmp;
                }
                break;

            case "Objectives":
                tmp = 0;
                for(String myString : myObjectivesString)
                {
                    for(int i = 0; i < myString.length(); ++i)
                    {
                        character = myString.charAt(i);
                        index = Item.getSymbolIndex(character);

                        g.drawImage(letterImages.get(index), 250+i*27, 200+tmp*55, 40, 52, null);
                    }
                    ++tmp;
                }
                break;

            default:
                break;
        }
        //g.setColor(Color.blue);
        //g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
    }

}
