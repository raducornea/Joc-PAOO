package PaooGame.Graphics;

import PaooGame.Items.NPC;

import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;


/*! \class public class Assets
    \brief Clasa incarca fiecare element grafic necesar jocului.

    Game assets include tot ce este folosit intr-un joc: imagini, sunete, harti etc.
 */
public class Assets
{
    /// Referinte catre elementele grafice (dale) utilizate in joc.
    public static BufferedImage heroLeft;
    public static BufferedImage heroRight;
    public static BufferedImage heroUp;
    public static BufferedImage heroDown;

    public static BufferedImage itemBackground;
    public static BufferedImage soil;
    public static BufferedImage grass;
    public static BufferedImage mountain;
    public static BufferedImage townGrass;
    public static BufferedImage townGrassDestroyed;
    public static BufferedImage townSoil;
    public static BufferedImage water;
    public static BufferedImage rockUp;
    public static BufferedImage rockDown;
    public static BufferedImage rockLeft;
    public static BufferedImage rockRight;
    public static BufferedImage tree;

    public static BufferedImage forestGuy;
    public static BufferedImage grunt1;
    public static BufferedImage grunt2;
    public static BufferedImage deer1;
    public static BufferedImage deer2;
    public static BufferedImage deer3;
    public static BufferedImage deer4;
    public static BufferedImage deer5;
    public static BufferedImage guy1;
    public static BufferedImage guy2;
    public static BufferedImage guy3;
    public static BufferedImage guy4;
    public static BufferedImage guy5;
    public static BufferedImage guy6;
    public static BufferedImage clown;
    public static BufferedImage villager1;
    public static BufferedImage villager2;
    public static BufferedImage villager3;
    public static BufferedImage villager4;
    public static BufferedImage villager5;
    public static BufferedImage villager6;
    public static BufferedImage horse1;
    public static BufferedImage defender1;
    public static BufferedImage defender2;
    public static BufferedImage defender3;
    public static BufferedImage mage1;
    public static BufferedImage mage2;
    public static BufferedImage mage3;
    public static BufferedImage archer;
    public static BufferedImage horse2;

    public static BufferedImage blueFlower;
    public static BufferedImage roofTop1;

    public static List<BufferedImage> letters = new ArrayList<BufferedImage>(26); //69 total

    /// SpriteSheet folosit si in alte functii
    public static SpriteSheet sheetHero = new SpriteSheet(ImageLoader.LoadImage("/Sprites/player/models/hero.png"),64,64);
    public static SpriteSheet sheetNPC = new SpriteSheet(ImageLoader.LoadImage("/Sprites/player/models/npc/npcs.png"), 270, 270);
    public static SpriteSheet sheetSymbols = new SpriteSheet(ImageLoader.LoadImage("/Sprites/player/text.png"), 18, 22);

    /*! \fn public static void Init()
        \brief Functia initializaza referintele catre elementele grafice utilizate.

        Aceasta functie poate fi rescrisa astfel incat elementele grafice incarcate/utilizate
        sa fie parametrizate. Din acest motiv referintele nu sunt finale.
     */
    public static void Init()
    {

        /// Construiesc sprite pentru litere de la a la z citindu-le coordonatele din fisier
        String contentLetters = "";
        try
        {
            contentLetters = new String ( Files.readAllBytes( Paths.get("src/PaooGame/Graphics/GameText.txt")));
        }
        catch (IOException e)
        {
            System.out.println("The file does not exist.");
            e.printStackTrace();
        }
        //System.out.println(contentLetters);
        BufferedReader bufReader = new BufferedReader(new StringReader(contentLetters));
        String line = null;

        while(true)
        {
            try
            {
                if (!((line=bufReader.readLine()) != null)) break;
                //System.out.println(line);

                char tmp = '\0';
                String myXNumber = "";
                String myYNumber = "";

                for (int i = 0; i < line.length(); ++i)
                {
                    char ch = line.charAt(i);
                    //System.out.print(ch);
                    if (ch == ':' && tmp == 'x')
                    {
                        i += 2;
                        ch = line.charAt(i);
                        while (ch != ',')
                        {
                            myXNumber = myXNumber + ch;
                            ++i;
                            ch = line.charAt(i);
                        }
                    }
                    if (ch == ':' && tmp == 'y')
                    {
                        i += 2;
                        ch = line.charAt(i);
                        while (ch != ',')
                        {
                            myYNumber = myYNumber + ch;
                            ++i;
                            ch = line.charAt(i);
                        }
                    }
                    tmp = ch;
                }

                //adaug imaginile in lista mea letters
                BufferedImage aux = sheetSymbols.crop(parseInt(myXNumber), parseInt(myYNumber));
                letters.add(aux);
                //System.out.println(parseInt(myXNumber) + " " + parseInt(myYNumber));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }


        /* Test ca sa numar imaginile
        int i = 0;
        for (BufferedImage aux : letters)
        {
            i++;
            System.out.println("i: " + i);
        }
        */


        /// Se creaza temporar un obiect SpriteSheet initializat prin intermediul clasei ImageLoader
        //SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/textures/PaooGameSpriteSheet.png"));
        SpriteSheet sheet = new SpriteSheet(ImageLoader.LoadImage("/Sprites/maps/forest/sprites/forest.png"));
        //SpriteSheet sheetHero = new SpriteSheet(ImageLoader.LoadImage("/Sprites/player/models/hero.png"),64,64);

            /// Se obtin subimaginile corespunzatoare elementelor necesare.
        grass = sheet.crop(0, 0);
        soil = sheet.crop(5, 0); // pamant
        water = sheet.crop(14, 132);
        mountain = sheet.crop(1, 8);
        townGrass = sheet.crop(0, 1);
        townGrassDestroyed = sheet.crop(1, 1);
        townSoil = sheet.crop(2, 1);
        tree = sheet.crop(16, 3, 128, 128);
        rockUp = sheet.crop(2, 2);
        rockDown = sheet.crop(3, 2);
        rockLeft = sheet.crop(0, 3);
        rockRight = sheet.crop(1, 3);

        heroUp = sheetHero.crop(0, 0);
        heroDown = sheetHero.crop(0, 2);
        heroLeft = sheetHero.crop(0, 1);
        heroRight = sheetHero.crop(0, 3);

        forestGuy = sheetNPC.crop(0, 0);
        grunt1 = sheetNPC.crop(1, 0);
        grunt2 = sheetNPC.crop(2, 0);
        deer4 = sheetNPC.crop(0, 1);
        deer5 = sheetNPC.crop(1, 1);

        blueFlower = sheet.crop(6,6);
        roofTop1 = sheet.crop(10,79,176,224);

        itemBackground = ImageLoader.LoadImage("/Sprites/player/text.png");

    }

    public static BufferedImage SetCropHero(int x, int y)
    {
        BufferedImage newImage;
        newImage = sheetHero.crop(x, y);
        return newImage;
    }
}
