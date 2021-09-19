package PaooGame;

import PaooGame.Graphics.Camera;
import PaooGame.Input.KeyManager;
import PaooGame.Items.Inventory;
import PaooGame.Items.Menu;
import PaooGame.Items.NPC;
import PaooGame.Items.Hero;
import PaooGame.States.BattleState;
import PaooGame.States.InventoryState;
import PaooGame.States.MenuState;
import PaooGame.States.PlayState;
import PaooGame.Maps.Map;
import PaooGame.Tiles.Tile;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.awt.image.BufferedImage;
import java.util.List;

/*! \class public class RefLinks
    \brief Clasa ce retine o serie de referinte ale unor elemente pentru a fi usor accesibile.

    Altfel ar trebui ca functiile respective sa aiba o serie intreaga de parametri si ar ingreuna programarea.
 */
public class RefLinks
{
    private static boolean isInBattle;
    private Game game;           /*!< Referinta catre obiectul Game.*/
    private Map map;             /*!< Referinta catre harta curenta.*/
    private static Hero hero;    /*!< Referinta catre erou*/
    private NPC npc;             /*!< Referinta catre un NPC*/

    public static int level;
    public static int total = 0;

    /*! \fn public RefLinks(Game game)
        \brief Constructorul de initializare al clasei.

        \param game Referinta catre obiectul game.
     */
    public RefLinks(Game game)
    {
        this.game = game;
    }

    /*! \fn public KeyManager GetKeyManager()
        \brief Returneaza referinta catre managerul evenimentelor de tastatura.
     */
    public KeyManager GetKeyManager()
    {
        return game.GetKeyManager();
    }

    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei jocului.
     */
    public int GetTileWidth()
    {
        return Tile.GetWidth();
    }
    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei jocului.
     */
    public int GetTileHeight()
    {
        return Tile.GetHeight();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei jocului.
     */
    /*! \fn public int GetWidth()
        \brief Returneaza latimea ferestrei jocului.
     */
    public int GetWidth()
    {
        return game.GetWidth();
    }

    /*! \fn public int GetHeight()
        \brief Returneaza inaltimea ferestrei jocului.
     */
    public int GetHeight()
    {
        return game.GetHeight();
    }

    /*! \fn public Game GetGame()
        \brief Intoarce referinta catre obiectul Game.
     */
    public Game GetGame()
    {
        return game;
    }

    /*! \fn public void SetGame(Game game)
        \brief Seteaza referinta catre un obiect Game.

        \param game Referinta obiectului Game.
     */
    public void SetGame(Game game)
    {
        this.game = game;
    }

    public NPC GetNPC()
    {
        return npc;
    }

    public Hero GetHero()
    {
        return hero;
    }

    public void SetNPC(NPC npc)
    {
        this.npc = npc;
    }

    /*! \fn public Map GetMap()
        \brief Intoarce referinta catre harta curenta.
     */
    public Map GetMap()
    {
        return map;
    }

    /*! \fn public void SetMap(Map map)
        \brief Seteaza referinta catre harta curenta.

        \param map Referinta catre harta curenta.
     */
    public void SetMap(Map map)
    {
        this.map = map;
    }

    /*! \fn public RefLinks(Game game)
        \brief Constructorul de initializare al clasei.

        \param game Referinta catre obiectul game.
     */
    public long GetSecondsTime()
    {
        return game.GetSecondsTime();
    }

    public static List<NPC> getNpcs()
    {
        return PlayState.getNpcs();
    }

    public static List<NPC> HeroNpcs()
    {
        return Hero.getHeroNpcs();
    }

    public static Hero getHero()
    {
        return PlayState.getHero();
    }

    public static Inventory getInventory()
    {
        return InventoryState.getInventory();
    }

    public static String getInventoryPage()
    {
        return InventoryState.getInventory().getPage();
    }

    public static void resetInventory()
    {
        InventoryState.getInventory().resetInventory();
    }


    public static Camera getCamera()
    {
        return PlayState.getHero().getCamera();
    }


    public static Menu getMenu()
    {
        return MenuState.getMenu();
    }

    public static void resetMenu()
    {
        MenuState.getMenu().resetMenu();
    }

    public static int getHashtagY()
    {
        return MenuState.getMenu().getHashtagY();
    }

    public static int getHashtagX()
    {
        return MenuState.getMenu().getHashtagX();
    }

    public static void setHero(Hero x)
    {
        hero = x;
    }

    public static int getLife()
    {
        return getHero().getLife();
    }

    public static int getAttackDamage()
    {
        return getHero().getAttackDamage();
    }

    public static void setBattle(boolean x) { isInBattle = x; }

    public static boolean getBattle() { return isInBattle;}

}
