package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Graphics.ImageLoader;
import PaooGame.Items.Item;
import PaooGame.RefLinks;
import PaooGame.Items.NPC;
import PaooGame.Items.Hero;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;



public class BattleState extends State
{
    private List<BufferedImage> letterImages  = Assets.letters;
    private BufferedImage image;
    private NPC npc;
    private Hero hero;
    private int choice;
    private int hashtagX;
    private int hashtagY;
    private boolean myTurn;
    private int countHits;
    Singleton mySingleton;

    private List<String> optionsString = new ArrayList<String>(4);
    private List<NPC> NPCs;

    private boolean downFlag;
    private boolean releaseDOWNFlag;
    private boolean upFlag;
    private boolean releaseUPFlag;
    private boolean enterFlag;
    private boolean releaseENTERFlag;
    private String EnemyOption;
    private String EnemyOption1;

    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public BattleState(RefLinks refLink) throws InterruptedException
    {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        image = ImageLoader.LoadImage("/Sprites/player/flowerRectangle.png");
        npc = refLink.GetNPC();
        List<NPC> NPCs = RefLinks.HeroNpcs();
        hero = refLink.GetHero();
        myTurn = false;
        hashtagX = 200;
        hashtagY = 200;

        EnemyOption = "";
        EnemyOption1 = "";

        optionsString.add("Info      ");
        optionsString.add("Greed     ");
        optionsString.add("Peacify   ");
        optionsString.add("Abscond   ");
        mySingleton = Singleton.GetInstance();
        mySingleton.setData(0);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        // Run
        if (choice == 4)
        {
            choice = 0;
            refLink.setBattle(false);
            try
            {
                Thread.sleep(2000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else if (choice == 3 && npc.getPeacifyOptions() == 0)
        {
            hero.setScore(hero.getScore() + (hero.getLife() + npc.getLife()));
            hero.setLife(hero.getLife() + (hero.getLife()/10 + npc.getLife()/10));
            choice = 0;
            NPCs.remove(npc);
            npc.setFriend(true);
            NPCs.add(npc);
            refLink.setBattle(false);
            try
            {
                Thread.sleep(2000);
                --refLink.total;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else if (choice == 2 && npc.getLife() <= 0)
        {
            System.out.println(mySingleton.getData());
            mySingleton.setData(1 + mySingleton.getData());

            int damageModifier = (int)(Math.random()*(15-0 +1)+15);

            hero.setScore(damageModifier*(hero.getScore() - mySingleton.getData()*npc.getLife()));
            hero.setLife(damageModifier + hero.getLife() + hero.getLife()/5 - npc.getLife()/5 + mySingleton.getData());

            hero.setGreeded(mySingleton.getData());

            choice = 0;
            NPCs.remove(npc);

            refLink.setBattle(false);
            try
            {
                Thread.sleep(2000);
                --refLink.total;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else if (choice == 5)
        {
            choice = 0;

            refLink.setBattle(false);
            try
            {
                Thread.sleep(2000);
                --refLink.total;
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            System.exit(0);
        }


        //System.out.println(refLink.getLife());
        NPCs = RefLinks.getNpcs();
        npc = refLink.GetNPC();
        hero = refLink.GetHero();

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

            if (hashtagY < 500)
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
            // 4 cazuri pentru hashtagY: 200, 300, 400, 600

            switch(hashtagY)
            {
                case 200:
                    // Info
                    choice = 1;
                    break;

                case 300:
                    // Greed
                    choice = 2;
                    int max = hero.getAttackDamage() * 2;
                    int min = hero.getAttackDamage();

                    int damageModifier = (int)(Math.random()*(max-min +1)+max);

                    npc.setLife(npc.getLife() - damageModifier);
                    hero.setLife(hero.getLife() - npc.getAttackDamage());

                    //hero.setScore(hero.)
                    break;

                case 400:
                    // Peacify
                    choice = 3;
                    npc.setPeacifyOptions(npc.getPeacifyOptions() - 1);;
                    break;

                case 500:
                    // Abscond
                    choice = 4;
                    break;

                default:
                    choice = 0;
                    break;
            }
        }

        if (hero.getLife() <= 0)
            choice = 5;

        //when you finish battles:
        //RefLinks.setBattleState(false);
    }

    public void resetBattle()
    {
        choice = 0;
        EnemyOption = "";
        hashtagY = 400;
        hashtagX = 500;
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {

        g.drawImage(image, 0, 0, refLink.GetWidth(), refLink.GetHeight(), null);
        g.drawImage(npc.getNPCImage(), 575, 100, 400,400, null);

        char character = '#';
        int index = Item.getSymbolIndex(character);
        g.drawImage(letterImages.get(index), hashtagX, hashtagY, 60, 66, null);

        int tmp = 0;
        for(String myString : optionsString)
        {
            for(int i = 0; i < myString.length(); ++i)
            {
                character = myString.charAt(i);
                index = Item.getSymbolIndex(character);

                g.drawImage(letterImages.get(index), 254+i*27, 200+tmp*100, 40, 66, null);
            }
            ++tmp;
        }

        //Draws Enemy Options
        switch (choice)
        {
            case 1:
                EnemyOption
                        = "Mob HP: "
                        + npc.getLife()
                        + " Mob AD:"
                        + npc.getAttackDamage();

                break;
            case 2:
                EnemyOption = "HP: " + npc.getLife();
                if (choice == 2 && npc.getLife() <= 0)
                    EnemyOption = "x-x";
                break;
            case 3:
                EnemyOption = npc.getPeacifyOptions() + " remaining";
                if (choice == 3 && npc.getPeacifyOptions() == 0)
                {
                    EnemyOption = "Peacified!";
                    hero.setAbsconded(hero.getAbsconded() + 1);
                }
                break;
            case 4:
                EnemyOption = "Run... RUN!!!";
                break;
            case 5:
                EnemyOption = "You died. RIP";
                break;
            default:
                EnemyOption = "";
                break;
        }

        character = ' ';

        for(int i = 0; i < EnemyOption.length(); ++i)
        {
            character = EnemyOption.charAt(i);
            index = Item.getSymbolIndex(character);

            g.drawImage(letterImages.get(index), 550+i*26, 425, 40, 66, null);
        }

        if (choice == 1)
        {
            EnemyOption1 = "My HP:  " + hero.getLife() + " My AD: " + hero.getAttackDamage();
            for(int i = 0; i < EnemyOption1.length(); ++i)
            {
                character = EnemyOption1.charAt(i);
                index = Item.getSymbolIndex(character);

                g.drawImage(letterImages.get(index), 550+i*26, 500, 40, 66, null);
            }
        }

    }

}
