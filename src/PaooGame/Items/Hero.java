package PaooGame.Items;

import java.awt.event.KeyEvent;
import java.lang.Math;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


import PaooGame.Graphics.Camera;
import PaooGame.RefLinks;
import PaooGame.Graphics.Assets;

/*! \class public class Hero extends Character
    \brief Implementeaza notiunea de erou/player (caracterul controlat de jucator).

    Elementele suplimentare pe care le aduce fata de clasa de baza sunt:
        imaginea (acest atribut poate fi ridicat si in clasa de baza)
        deplasarea
        atacul (nu este implementat momentan)
        dreptunghiul de coliziune
 */
public class Hero extends Character
{
    private List<BufferedImage> letterImages  = Assets.letters;
    private Camera camera;

    private int score;

    private int totalAbsconded;
    private int totalGreeded;

    private float xSafe;
    private float ySafe;
    private BufferedImage image;       /*!< Referinta catre imaginea curenta a eroului.*/
    private static List<NPC> npcs;

    private static List<String> myItemsString        = new ArrayList<String>(8);
    private static List<BufferedImage> myItemsImages = new ArrayList<BufferedImage>(8);

    public static List<String> getMyItemsString()
    {
        return myItemsString;
    }
    public static List<BufferedImage> getMyItemsImages()
    {
        return myItemsImages;
    }

    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public Hero(RefLinks refLink, float x, float y)
    {
            ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
            ///Seteaza imaginea de start a eroului
        image = Assets.heroLeft;
            ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 16;
        normalBounds.y = 16;
        normalBounds.width = 16;
        normalBounds.height = 32;

            ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        attackBounds.x = 10;
        attackBounds.y = 10;
        attackBounds.width = 38;
        attackBounds.height = 38;

            ///Initial coordonatele de "giruanta" vor fi initializate cu 0
        xSafe = 0;
        ySafe = 0;

        camera = new Camera(x, y, refLink);

        life = 100;
        attackDamage = 5;

        for (int i = 1; i <= 8; ++i)
        {
            myItemsString.add("");
            myItemsImages.add(Assets.itemBackground);
        }

        score = 0;
        totalAbsconded = 0;
        totalGreeded = 0;
    }


    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update()
    {
        camera.Update(this);
            ///Verifica daca a fost apasata o tasta
        GetInput();

            ///Actualizeaza pozitia
        Move();
            ///Actualizeaza imaginea
        int theTime = (int) (refLink.GetSecondsTime()/100%60);
        int theSecods = (int) (refLink.GetSecondsTime()/1000%60);
        int index = (theTime%9);

        //System.out.println(life);

        ///!isInInventory == isInMap
        ///Actualizez player <-, ->, ^, v, dar si in functie de index-ul ales
        if(refLink.GetKeyManager().left == true)
        {
            image = Assets.heroLeft;
            Assets.heroLeft = Assets.SetCropHero(index, 1);
        }
        else if(refLink.GetKeyManager().right == true)
        {
            image = Assets.heroRight;
            Assets.heroRight = Assets.SetCropHero(index, 3);
        }
        else if(refLink.GetKeyManager().up == true)
        {
            image = Assets.heroUp;
            Assets.heroUp = Assets.SetCropHero(index, 0);
        }
        else if(refLink.GetKeyManager().down == true)
        {
            image = Assets.heroDown;
            Assets.heroDown = Assets.SetCropHero(index, 2);
        }
        //to be done npcs
    }

    /*! \fn public Boolean CheckCollision()
        \brief Verifica daca Hero face sau nu coliziune cu harta. Are un mic bug cand vine vorba de a trece prin locuri strampte.
     */
    public Boolean CheckCollision()
    {
        //System.out.println(refLink.total);
        //System.out.println(x + " " + y);
        ///Coliziune cu harta
        ///           topA
        ///         ________
        /// leftA  |        |  rightA
        ///        |________|
        ///          bottomA

        float topA, rightA, bottomA, leftA;
        leftA = x + bounds.x;
        topA = y + bounds.y;
        rightA = x + bounds.x + bounds.width;
        bottomA = y + bounds.y + bounds.height;

        // am pus -80 tocmai pentru ca vreau sa las randul de sus pentru hud
        ///Tinem cont de dimensinea hartii iar daca depaseste sau e mai mica => collision
        if(leftA < 0 || topA - 80 < 0 || rightA > refLink.GetMap().getWidth()*64 || bottomA > refLink.GetMap().getHeight()*64 - 70) // dale x dimensiune
        {
            return true;
        }

        ///Coliziune cu npcs
        ///A = Hero; B = NPC
        npcs = refLink.getNpcs();

        for (NPC i : npcs)
        {
            float leftB = i.x + i.bounds.x;
            float rightB = i.x + i.bounds.x + i.bounds.width;
            float topB = i.y + i.bounds.y;
            float bottomB = i.y + i.bounds.y + i.bounds.height;

            if(bottomA > topB && topA < bottomB && rightA > leftB && leftA < rightB)
            {
                // if is friend and not visited
                if (i.getFriend() == true && i.getVisited() == false)
                {
                    System.out.println("ally");

                    refLink.SetNPC(i);
                    RefLinks.setHero(this);
                    i.setVisited(true);

                    refLink.setBattle(true);

                }

                // if is friend and visited
                if (i.getFriend() == true && i.getVisited() == true)
                {
                    return false;
                }

                // if is enemy and not visited
                if (i.getFriend() == false && i.getVisited() == false)
                {
                    //System.out.println("enemyyy");
                    refLink.SetNPC(i);
                    RefLinks.setHero(this);
                    i.setVisited(true);

                    refLink.setBattle(true);
                }

                // if is enemy and visited -> nu exista situatia asta, pentru ca
                // toti inamicii vizitati fie au fost ucisi, fie convertiti la ally

                return true;
            }
        }
        //System.out.println(refLink.getBattle());


        ///Coliziune cu Tiles
        ///    a________b
        ///    |        |
        ///    |________|
        ///    d        c

        ///ne folosim de width si height de la tiles pentru a face coliziuena corect
        int width = refLink.GetTileWidth();
        int height = refLink.GetTileHeight();
        int xs, xd, yj, ys;

        ///Aflam coordonatele pentru coliziune pentru xstanga, xdreapta, ysus, yjos
        xs = (int) (leftA / width);
        ys = (int) (topA / height);
        xd = (int) (rightA / width);
        yj = (int) (bottomA / height);

        ///Verificam colturile: a
        if(refLink.GetMap().GetTile(xs , ys).IsSolid() == true)
        {
            //System.out.println("coliziune");
            return true;
        }
            ///Verificam colturile: b
        if(refLink.GetMap().GetTile(xs, yj).IsSolid() == true)
        {
            //System.out.println("coliziune");
            return true;
        }
            ///Verificam colturile: c
        if(refLink.GetMap().GetTile(xd, ys).IsSolid() == true)
        {
            //System.out.println("coliziune");
            return true;
        }
            ///Verificam colturile: d
        if(refLink.GetMap().GetTile(xd, yj).IsSolid() == true)
        {
            //System.out.println("coliziune");
            return true;
        }

        //System.out.println("nu coliziune");
        return false;
    }

    /*! \fn private void GetInput()
        \brief Verifica daca a fost apasata o tasta din cele stabilite pentru controlul eroului.
     */
    private void GetInput()
    {
            ///Pentru a folosi miscarea pe diagonala cu aceeasi viteza, vom folosi o abordare de tipul xViteza, yViteza
        float xVeloctiy, yVeloctiy;
        xVeloctiy = 0;
        yVeloctiy = 0;

        /*
        if (isInInventory == true)
        {
            ///Daca dau return, xMove si yMove se pastreaza, de asta trebuie sa setez viteza lor pe 0
            xMove = yMove = 0;
            return;
        }
        */
            ///Daca avem coliziune, x revine la pozitia dinainte, adica Safe
        if (CheckCollision() == true)
        {
            life--;
            x = xSafe*48;
            y = ySafe*48;
        }
            ///Implicit eroul nu trebuie sa se deplaseze daca nu este apasata o tasta
        xMove = 0;
        yMove = 0;
            ///Verificare apasare tasta "sus"
        if(refLink.GetKeyManager().up)
        {
            yVeloctiy = -speed;
        }
            ///Verificare apasare tasta "jos"
        if(refLink.GetKeyManager().down)
        {
            yVeloctiy = speed;
        }
            ///Verificare apasare tasta "left"
        if(refLink.GetKeyManager().left)
        {
            xVeloctiy = -speed;
        }
            ///Verificare apasare tasta "dreapta"
        if(refLink.GetKeyManager().right)
        {
            xVeloctiy = speed;
        }
            ///Salvam pozitia sigura
        xSafe = x/48;
        ySafe = y/48;
            ///Actualizam pozitia in functie de velocitate
        if (xVeloctiy != 0 && yVeloctiy != 0)
        {
            if (xVeloctiy < 0)
            {
                xMove -= Math.sqrt(2) / 2 * (Math.abs(xVeloctiy));
            }
            else
            {
                xMove += Math.sqrt(2) / 2 * (Math.abs(xVeloctiy));
            }

            if (yVeloctiy < 0)
            {
                yMove -= Math.sqrt(2) / 2 * (Math.abs(yVeloctiy));
            }
            else
            {
                yMove += Math.sqrt(2) / 2 * (Math.abs(yVeloctiy));
            }
        }
        else
        {
            xMove += xVeloctiy;
            yMove += yVeloctiy;
        }

    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(image, (int)x - (int) camera.getX(), (int)y - (int) camera.getY(), width+16, height+16, null);

            ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        //g.setColor(Color.blue);
        //g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);

        String Hud = "                                                        ";

        char character = ' ';
        int index;

        for(int i = 0; i < Hud.length(); ++i)
        {
            character = Hud.charAt(i);
            index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), i*25, 0, 40, 66, null);
            g.drawImage(letterImages.get(index), i*25, 702, 40, 66, null);
        }

        String Life = "HP: " + String.valueOf(life);
        character = ' ';

        for(int i = 0; i < Life.length(); ++i)
        {
            character = Life.charAt(i);
            index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), 400+i*25, 0, 40, 66, null);
        }

        String Score = "Score: " + String.valueOf(score);
        character = ' ';

        for(int i = 0; i < Score.length(); ++i)
        {
            character = Score.charAt(i);
            index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), i*25, 0, 40, 66, null);
        }

        String Attack = "AD: " + String.valueOf(attackDamage);
        character = ' ';

        for(int i = 0; i < Attack.length(); ++i)
        {
            character = Attack.charAt(i);
            index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), 700+i*25, 0, 40, 66, null);
        }

        String Pacified = "P: " + String.valueOf(totalAbsconded);
        character = ' ';

        for(int i = 0; i < Pacified.length(); ++i)
        {
            character = Pacified.charAt(i);
            index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), 900+i*25, 0, 40, 66, null);
        }

        String Greeded = "G: " + String.valueOf(totalGreeded);
        character = ' ';

        for(int i = 0; i < Greeded.length(); ++i)
        {
            character = Greeded.charAt(i);
            index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), 1100+i*25, 0, 40, 66, null);
        }
    }

    
    public void Draw(Graphics g, NPC npc)
    {
        String Hud = "                                                        ";

        char character = ' ';
        int index;

        for(int i = 0; i < Hud.length(); ++i)
        {
            character = Hud.charAt(i);
            index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), i*25, 0, 40, 66, null);
            g.drawImage(letterImages.get(index), i*25, 702, 40, 66, null);
        }
    }

    public Camera getCamera()
    {
        return camera;
    }

    public static List<NPC> getHeroNpcs()
    {
        return npcs;
    }

    public void setScore(int x)
    {
        score = x;
    }

    public int getScore()
    {
        return score;
    }

    public void setAbsconded(int x)
    {
        totalAbsconded = x;
    }

    public int getAbsconded()
    {
        return totalAbsconded;
    }

    public void setGreeded(int x)
    {
        totalGreeded = x;
    }

    public int getGreeded()
    {
        return totalGreeded;
    }
}

