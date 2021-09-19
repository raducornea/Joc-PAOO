package PaooGame.States;

import PaooGame.Items.Inventory;
import PaooGame.RefLinks;

import java.awt.*;

/*! \class public class MenuState extends State
    \brief Implementeaza notiunea de menu pentru joc.
 */
public class InventoryState extends State
{

    private static Inventory inventory;
    /*! \fn public MenuState(RefLinks refLink)
        \brief Constructorul de initializare al clasei.

        \param refLink O referinta catre un obiect "shortcut", obiect ce contine o serie de referinte utile in program.
     */
    public InventoryState(RefLinks refLink)
    {
        ///Apel al constructorului clasei de baza.
        super(refLink);
        inventory = new Inventory(refLink);
    }
    /*! \fn public void Update()
        \brief Actualizeaza starea curenta a meniului.
     */
    @Override
    public void Update()
    {
        //System.out.println("u did it boi, u are in inventory state");
        inventory.Update();
    }

    /*! \fn public void Draw(Graphics g)
        \brief Deseneaza (randeaza) pe ecran starea curenta a meniului.

        \param g Contextul grafic in care trebuie sa deseneze starea jocului pe ecran.
    */
    @Override
    public void Draw(Graphics g)
    {
        inventory.Draw(g);
    }

    public static Inventory getInventory()
    {
        return inventory;
    }

    public static boolean getState()
    {
        return inventory.getState();
    }

    public static void setState(boolean tmp)
    {
        inventory.setState(tmp);
    }

}
