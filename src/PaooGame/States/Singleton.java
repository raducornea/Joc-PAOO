package PaooGame.States;

public class Singleton
{
    private static Singleton instance = null;
    private int data;

    public int getData()
    {
        return data;
    }

    void setData(int value)
    {
        data = value;
    }

    private Singleton() throws InterruptedException // protected deriv
    {
        //System.out.println("Singleton()");
    }

    public static Singleton GetInstance() throws InterruptedException
    {
        if(instance == null)
        {
            instance = new Singleton();
        }
        return instance;
    }

    public static void Reset()
    {
        instance = null;
    }

}
