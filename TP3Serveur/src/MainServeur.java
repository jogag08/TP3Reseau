public class MainServeur
{
    public static void main(String[] args)
    {
        Game game1 = new Game(2000);
        Game game2 = new Game(3000);
        Game game3 = new Game(4000);
        Game game4 = new Game(5000);

        Thread GameThread1 = new Thread(game1);
        Thread GameThread2 = new Thread(game2);
        Thread GameThread3 = new Thread(game3);
        Thread GameThread4 = new Thread(game4);

        GameThread1.start();
        GameThread2.start();
        GameThread3.start();
        GameThread4.start();
    }
}
