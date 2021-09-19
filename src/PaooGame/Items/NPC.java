package PaooGame.Items;

import java.awt.event.KeyEvent;
import java.lang.Math;

import java.awt.*;
import java.awt.image.BufferedImage;


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
public class NPC extends Character
{
    private BufferedImage image;       /*!< Referinta catre imaginea curenta a eroului.*/
    private boolean isFriend;
    private boolean visited;

    final int DEFAULT_PEACIFYOPTIONS = 10;
    private int peacifyOptions;

    /*! \fn public Hero(RefLinks refLink, float x, float y)
        \brief Constructorul de initializare al clasei Hero.

        \param refLink Referinta catre obiectul shortcut (obiect ce retine o serie de referinte din program).
        \param x Pozitia initiala pe axa X a eroului.
        \param y Pozitia initiala pe axa Y a eroului.
     */
    public NPC(RefLinks refLink, float x, float y, BufferedImage myImage, boolean isFriend)
    {

        ///Apel al constructorului clasei de baza
        super(refLink, x,y, Character.DEFAULT_CREATURE_WIDTH, Character.DEFAULT_CREATURE_HEIGHT);
        ///Seteaza imaginea de start a eroului
        image = myImage;
        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea implicita(normala)
        normalBounds.x = 16;
        normalBounds.y = 16;
        normalBounds.width = 64;   // helps at collision
        normalBounds.height = 64;

        ///Stabilieste pozitia relativa si dimensiunea dreptunghiului de coliziune, starea de atac
        attackBounds.x = 10;
        attackBounds.y = 10;
        attackBounds.width = 38;
        attackBounds.height = 38;

        this.isFriend = isFriend;
        visited = false;

        peacifyOptions = DEFAULT_PEACIFYOPTIONS;
    }

    /*! \fn public void Update()
        \brief Actualizeaza pozitia si imaginea eroului.
     */
    @Override
    public void Update()
    {
        ///Verifica daca a fost apasata o tasta
        GetInput();

        ///Actualizeaza pozitia
        Move();

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
    }

    /*! \fn public void Draw(Graphics g)
        \brief Randeaza/deseneaza eroul in noua pozitie.

        \brief g Contextul grafi in care trebuie efectuata desenarea eroului.
     */
    @Override
    public void Draw(Graphics g)
    {
        g.drawImage(image, (int)x - (int) refLink.getCamera().getX(), (int)y - (int) refLink.getCamera().getY(), width+32, height+32, null);

        ///doar pentru debug daca se doreste vizualizarea dreptunghiului de coliziune altfel se vor comenta urmatoarele doua linii
        //g.setColor(Color.blue);
        //g.fillRect((int)(x + bounds.x), (int)(y + bounds.y), bounds.width, bounds.height);
    }

    public boolean getFriend()
    {
        return isFriend;
    }

    public boolean getVisited()
    {
        return visited;
    }

    public void setVisited(boolean x)
    {
        visited = x;
    }

    public void setFriend(boolean x)
    {
        isFriend = x;
    }

    public BufferedImage getNPCImage()
    {
        return image;
    }

    public int getPeacifyOptions() { return peacifyOptions; }

    public void setPeacifyOptions(int x) { peacifyOptions = x; }



}
