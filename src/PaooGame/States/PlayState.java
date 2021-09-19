package PaooGame.States;

import PaooGame.Graphics.Assets;
import PaooGame.Items.Hero;
import PaooGame.Items.NPC;
import PaooGame.RefLinks;
import PaooGame.Maps.Map;
import PaooGame.Items.Pickable;

import java.awt.*;
import java.util.List;
import java.util.ArrayList; // ArrayList -> multi thread
import java.util.Vector;    // Vector -> one thread at a time


/*! \class public class PlayState extends State
    \brief Implementeaza/controleaza jocul.
 */
public class PlayState extends State
{
    private static List<NPC> npcs = new Vector<NPC>(20);
    //private static List<Pickable> pickables = new ArrayList<Pickable>(5);
    /*! 5 steps: import Items.Hero, private Hero hero, hero = new Hero, hero.Update(), hero.draw */
    private static Hero hero;  /*!< Referinta catre obiectul animat erou (controlat de utilizator).*/

    private Map map;    /*!< Referinta catre harta curenta.*/

    /*! \fn public PlayState(RefLinks refLink)
        \brief Constructorul de initializare al clasei

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public PlayState(RefLinks refLink)
    {
            ///Apel al constructorului clasei de baza
        super(refLink);
            ///Construieste harta jocului
        map = new Map(refLink);

            ///Referinta catre harta construita este setata si in obiectul shortcut pentru a fi accesibila si in alte clase ale programului.
        refLink.SetMap(map);
        RefLinks.total = 0;

        if (RefLinks.level == 1)
        {
            //hero = new Hero(refLink,50, 2050);
            hero = new Hero(refLink,50, 70);
            //hero = new Hero(refLink,350, 650);

            npcs.clear();
            //(reflink, x, y, Assets.image, friend or not)

            npcs.add(new NPC(refLink,621, 175, Assets.deer4, true));
            npcs.add(new NPC(refLink,927, 249, Assets.deer5, true));
            npcs.add(new NPC(refLink,187, 637, Assets.forestGuy, true));

            RefLinks.total = npcs.size();
        }

        if (RefLinks.level == 2)
        {

            //hero = new Hero(refLink,50, 2050);
            hero = new Hero(refLink,0, 70);
            //hero = new Hero(refLink,350, 650);

            npcs.clear();
            //(reflink, x, y, Assets.image, friend or not)
            npcs.add(new NPC(refLink,358, 92, Assets.forestGuy, true));
            npcs.add(new NPC(refLink,318, 645, Assets.grunt1, false));
            npcs.add(new NPC(refLink,100, 2049, Assets.grunt1, false));
            npcs.add(new NPC(refLink,652, 946, Assets.deer4, false));
            npcs.add(new NPC(refLink,1023, 764, Assets.deer4, false));

            RefLinks.total = npcs.size();
        }


        //pickables.add(new Pickable(refLink, 200, 250, 32, 32, Assets.blueFlower));
        //pickables.add(new Pickable(refLink, 600, 250, 176, 224, Assets.roofTop1));

    }

    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a jocului.
     */
    @Override
    public void Update()
    {
        //System.out.println(hero.GetX() + " " + hero.GetY());
        map.Update();
        hero.Update();

        // trebuie updatate si npc-urile in functie de ce face player-ul
        npcs = hero.getHeroNpcs();
        for(NPC i:npcs)
        {
            i.Update();
        }
        /*for(Pickable i:pickables)
        {
            i.Update();
        }*/

    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a jocului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
     */
    @Override
    public void Draw(Graphics g)
    {
        map.Draw(g);
        /*
        for(Pickable i:pickables)
        {
            i.Draw(g);
        }
        */

        for(NPC i:npcs)
        {
            i.Draw(g);
        }
        hero.Draw(g);
    }

    public static List<NPC> getNpcs()
    {
        return npcs;
    }

    public static Hero getHero()
    {
        return hero;
    }

}
