package PaooGame.Input;

import PaooGame.RefLinks;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*! \class public class KeyManager implements KeyListener
    \brief Gestioneaza intrarea (input-ul) de tastatura.

    Clasa citeste daca au fost apasata o tasta, stabiliteste ce tasta a fost actionata si seteaza corespunzator un flag.
    In program trebuie sa se tina cont de flagul aferent tastei de interes. Daca flagul respectiv este true inseamna
    ca tasta respectiva a fost apasata si false nu a fost apasata.
 */
public class KeyManager implements KeyListener
{
    private boolean[] keys; /*!< Vector de flaguri pentru toate tastele. Tastele vor fi regasite dupa cod [0 - 255]*/
    public boolean up;      /*!< Flag pentru tasta "sus" apasata.*/
    public boolean down;    /*!< Flag pentru tasta "jos" apasata.*/
    public boolean left;    /*!< Flag pentru tasta "stanga" apasata.*/
    public boolean right;   /*!< Flag pentru tasta "dreapta" apasata.*/
    public boolean e;       /*!< Flag pentru tasta "inventory" apasata.*/
    public boolean o;       /*!< Flag pentru tasta "meniu" apasata.*/
    public boolean esc;     /*!< Flag pentru tasta "back" apasata.*/
    public boolean enter;   /*!< Flag pentru tasta "enter" apasata.*/
    public RefLinks reflink;/*!< Reflink pentru ce se intampla referitor la harta si alte lucruri*/

    /*! \fn public KeyManager()
        \brief Constructorul clasei.
     */
    public KeyManager()
    {
            ///Constructie vector de flaguri aferente tastelor.
        keys = new boolean[256];
    }


    public void Update()
    {
        up      = keys[KeyEvent.VK_UP];
        down    = keys[KeyEvent.VK_DOWN];
        left    = keys[KeyEvent.VK_LEFT];
        right   = keys[KeyEvent.VK_RIGHT];
        e       = keys[KeyEvent.VK_E];
        esc     = keys[KeyEvent.VK_ESCAPE];
        enter   = keys[KeyEvent.VK_ENTER];
        o       = keys[KeyEvent.VK_O];
    }

    /*! \fn public void keyPressed(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand un un eveniment de tasta apasata este generat.

         \param e obiectul eveniment de tastatura.
     */
    @Override
    public void keyPressed(KeyEvent e)
    {
            ///se retine in vectorul de flaguri ca o tasta a fost apasata.
        keys[e.getKeyCode()] = true;
    }

    /*! \fn public void keyReleased(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand un un eveniment de tasta eliberata este generat.

         \param e obiectul eveniment de tastatura.
     */
    @Override
    public void keyReleased(KeyEvent e)
    {
            ///se retine in vectorul de flaguri ca o tasta a fost eliberata.
        if(keys[KeyEvent.VK_E])
        {
            //reflink.SetInventory();
        }
        keys[e.getKeyCode()] = false;

    }

    /*! \fn public void keyTyped(KeyEvent e)
        \brief Functie ce va fi apelata atunci cand o tasta a fost apasata si eliberata.
        Momentan aceasta functie nu este utila in program.
     */
    @Override
    public void keyTyped(KeyEvent e)
    {

    }
}
