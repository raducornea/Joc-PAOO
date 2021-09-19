package PaooGame.Items;

import PaooGame.RefLinks;

import java.awt.image.BufferedImage;

/*! \class public abstract class Terrain extends Item
    \brief Defineste notiunea abstracta de decoratiune/obiect destructibil/interactionabil din joc.
 */
public abstract class Terrain extends Item
{
    protected float xMove;  /*!< Retine noua pozitie a caracterului pe axa X.*/
    protected float yMove;  /*!< Retine noua pozitie a caracterului pe axa Y.*/
    protected BufferedImage image;

    /*! \fn public Character(RefLinks refLink, float x, float y, int width, int height)
        \brief Constructor de initializare al clasei Character

        \param refLink Referinta catre obiectul shortcut (care retine alte referinte utile/necesare in joc).
        \param x Pozitia de start pa axa X a caracterului.
     */
    public Terrain(RefLinks refLink, float x, float y, int width, int height, BufferedImage image)
    {
        ///Apel constructor la clasei de baza
        super(refLink, x,y, width, height);
        //Seteaza pe valorile implicite pentru viata, viteza si distantele de deplasare
        xMove  = 0;
        yMove  = 0;
    }

    /*! \fn public void Move()
        \brief Modifica pozitia caracterului
     */
    public void Move()
    {
        ///Modifica pozitia caracterului pe axa X.
        ///Modifica pozitia caracterului pe axa Y.
        MoveX();
        MoveY();
    }

    /*! \fn public void MoveX()
        \brief Modifica pozitia caracterului pe axa X.
     */
    public void MoveX()
    {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa X.
        x += xMove;
    }

    /*! \fn public void MoveY()
        \brief Modifica pozitia caracterului pe axa Y.
     */
    public void MoveY()
    {
        ///Aduna la pozitia curenta numarul de pixeli cu care trebuie sa se deplaseze pe axa Y.
        y += yMove;
    }
}

