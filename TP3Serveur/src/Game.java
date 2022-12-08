import java.io.*;
import java.net.*;
import java.util.HashSet;
import java.util.Vector;

public class Game extends Serveur
{
    boolean gameIsActive = true;
    boolean TestInput = false;
    String[] boardPos = {"11", "12", "13", "21", "22", "23", "31", "32", "33"};
    String[] boardInfo = {"#", "#", "#", "#", "#", "#", "#", "#", "#"};

    HashSet<String> player1Positions = new HashSet<String>();
    HashSet<String> player2Positions = new HashSet<String>();
    HashSet<String> allActivePositions = new HashSet<String>();
    //String[][] possibilities = {{"11", "12", "13"} , {"21", "22", "23"} , {"31", "32", "33"} , {"11", "21", "31"} , {"12", "22", "32"} , {"13", "23", "33"} , {"11", "22", "33"} , {"13", "22", "31"}};
    HashSet<String> winningCombination1 = new HashSet<String>();
    HashSet<String> winningCombination2 = new HashSet<String>();
    HashSet<String> winningCombination3 = new HashSet<String>();
    HashSet<String> winningCombination4 = new HashSet<String>();
    HashSet<String> winningCombination5 = new HashSet<String>();
    HashSet<String> winningCombination6 = new HashSet<String>();
    HashSet<String> winningCombination7 = new HashSet<String>();
    HashSet<String> winningCombination8 = new HashSet<String>();

    int turn = 0;
    public Game(int port)
    {
        super(port);
    }

    @Override
    public void run()
    {
        super.run();
        CreateWinPossibilities();
        while(gameIsActive)
        {
            PlayGame();
        }
    }

    public void PlayGame()
    {
        String player1Req = "";
        String player2Req = "";
        while (!player1Req.equals("RageQuit") || !player2Req.equals("RageQuit"))
        {
            try
            {
                if(turn == 0)
                {
                    dataOutputStream1.writeUTF("It's your turn!");
                    dataOutputStream1.writeUTF(RenderBoard());
                    dataOutputStream2.writeUTF("Player One is making a move...");
                    while(!TestInput)
                    {
                        //dataOutputStream1.writeUTF("A");
                        System.out.println("test1");
                        player1Req = dataInputStream1.readUTF();
                        SetBoard(player1Req, "X");
                        //System.out.println("test2");
                       //if(!TestInput)
                       //{
                       //    dataOutputStream1.writeUTF("Denied");
                       //}
                       //else if (TestInput)
                       //{
                       //    dataOutputStream1.writeUTF("Accepted");
                       //}
                        //System.out.println(player1Req);
                        //System.out.println("test3");
                    }
                    TestInput = false;
                    SetPlayer1Positions(player1Req);
                    dataOutputStream1.writeUTF(RenderBoard());
                    //System.out.println("Player 1 :  " + player1Req);
                    //System.out.println(player1Positions);
                    if(CheckWin(player1Positions))
                    {
                        System.out.println("Player One won!");
                    };
                }

                if(turn == 1)
                {
                    dataOutputStream2.writeUTF("It's your turn!");
                    dataOutputStream2.writeUTF(RenderBoard());
                    dataOutputStream1.writeUTF("Player Two is making a move...");
                    while(!TestInput)
                    {
                        player2Req = dataInputStream2.readUTF();
                        SetBoard(player2Req, "O");
                    }
                    TestInput = false;
                    SetPlayer2Positions(player2Req);
                    dataOutputStream2.writeUTF(RenderBoard());
                    //System.out.println("Player 2 :  " + player2Req);
                    if(CheckWin(player2Positions))
                    {
                        System.out.println("Player Two won!");
                    };
                }
                ManageTurns(turn);
            }
            catch (Exception e) {
                System.out.println(e);
            }
        }
        gameIsActive = false;
    }

    public void ManageTurns(int t)
    {
        if(t == 0)
        {
            turn = 1;
        }
        else
        {
            turn = 0;
        }
    }

    public void SetPlayer1Positions(String move)
    {
        player1Positions.add(move);
    }

    public void SetPlayer2Positions(String move)
    {
        player2Positions.add(move);
    }
    public void SetBoard(String move, String sign)
    {
        for(int i = 0; i < boardPos.length; i++)
        {
            if(move.equals(boardPos[i]))
            {
                if(boardInfo[i].equals("#"))
                {
                    boardInfo[i] = sign;
                    try
                    {
                        if(turn == 0)
                        {
                            dataOutputStream1.writeUTF("Accepted");
                        }
                        else if (turn == 1)
                        {
                            dataOutputStream2.writeUTF("Accepted");
                        }
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    TestInput = true;
                    //Si la valeur est valide et n'est pas déja utilisée
                }
            }
        }
        try
        {
            if(turn == 0 && !TestInput)
            {
                dataOutputStream1.writeUTF("Denied");
            }
            else if (turn == 1 && !TestInput)
            {
                dataOutputStream2.writeUTF("Denied");
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
        //System.out.println("false");
        //Si la valeur n'est pas valide ou déjà utilisée
    }
    public String RenderBoard()
    {
        //11 12 13
        //21 22 23
        //31 32 33
        String board = boardInfo[0] + " " + boardInfo[1] + " " + boardInfo[2] + "\n" +
                       boardInfo[3] + " " + boardInfo[4] + " " + boardInfo[5] + "\n" +
                       boardInfo[6] + " " + boardInfo[7] + " " + boardInfo[8];
        return board;
    }

    public boolean CheckWin(HashSet<String> playerPositions)
    {
        if(playerPositions.containsAll(winningCombination1)){return true;}
        if(playerPositions.containsAll(winningCombination2)){return true;}
        if(playerPositions.containsAll(winningCombination3)){return true;}
        if(playerPositions.containsAll(winningCombination4)){return true;}
        if(playerPositions.containsAll(winningCombination5)){return true;}
        if(playerPositions.containsAll(winningCombination6)){return true;}
        if(playerPositions.containsAll(winningCombination7)){return true;}
        if(playerPositions.containsAll(winningCombination8)){return true;}
        else return false;
    }

    public void CreateWinPossibilities()
    {
        winningCombination1.add("11"); winningCombination1.add("12"); winningCombination1.add("13");
        winningCombination2.add("21"); winningCombination2.add("22"); winningCombination2.add("23");
        winningCombination3.add("31"); winningCombination3.add("32"); winningCombination3.add("33");
        winningCombination4.add("11"); winningCombination4.add("21"); winningCombination4.add("31");
        winningCombination5.add("12"); winningCombination5.add("22"); winningCombination5.add("32");
        winningCombination6.add("13"); winningCombination6.add("23"); winningCombination6.add("33");
        winningCombination7.add("11"); winningCombination7.add("22"); winningCombination7.add("33");
        winningCombination8.add("13"); winningCombination8.add("22"); winningCombination8.add("31");
    }
}
