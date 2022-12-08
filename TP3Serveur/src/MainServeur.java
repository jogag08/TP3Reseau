public class MainServeur
{
    public static void main(String[] args)
    {
        Game game1 = new Game(2000);
        Game game2 = new Game(3000);

        Thread GameThread1 = new Thread(game1);
        //Thread GameThread2 = new Thread(game2);

        GameThread1.start();
        //GameThread2.start();
    }
}
