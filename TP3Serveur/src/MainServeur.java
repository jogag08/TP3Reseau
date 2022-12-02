public class MainServeur
{
    public static void main(String[] args)
    {
        Game game1 = new Game( 2000);

        Thread GameThread1 = new Thread(game1);

        GameThread1.start();
    }
}
