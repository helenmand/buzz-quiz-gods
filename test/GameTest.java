import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest {

    @Test
    public void createRounds() {
        Player player1 = new Player(0,"player1");
        Player player2 = new Player(0, "player2");

        Game game = new Game(5,1);
        game.addPlayer(player1);

        game.createRounds();
        assertEquals(5, game.rounds.size());

        Game game2 = new Game(6,1);
        game.addPlayer(player1);
        game.addPlayer(player2);

        game2.createRounds();
        assertEquals(6, game2.rounds.size());

    }

    @Test
    public void rewardCreator() {
        Game game = new Game(5, 1);

        Player player1 = new Player(0,"player1");
        Player player2 = new Player(0, "player2");

        game.addPlayer(player1);
        game.addPlayer(player2);

        game.questionStartingTime = (long) 0;
        game.createRounds();

        for (Round r : game.rounds) {
            game.players.get(0).setAnswerPosition(r.getQuestions().get(0).getCorrectAnswerPosition());
            game.players.get(1).setAnswerPosition(4); //always false
            
            game.playerRewards.set(0, r.getBettingOption(0));
            game.playerRewards.set(1, r.getBettingOption(1));

            game.playerAnswerTime.set(0, (long) 0);
            game.playerAnswerTime.set(1, (long) 5000);

            game.playerAnswerOrder.add(0);
            game.playerAnswerOrder.add(1);

            game.playerCorrectAnswerAmount.set(0, 4);
            game.playerCorrectAnswerAmount.set(1, 4);

            game.rewardCreator();
            switch(r.getRoundType()){
                case 0:
                    assertEquals(1000, game.players.get(0).getScore());
                    assertEquals(0, game.players.get(1).getScore());
                    break;
                case 1:
                    assertEquals(r.getBettingOption(0), game.players.get(0).getScore());
                    assertEquals(-r.getBettingOption(1), game.players.get(1).getScore());
                    break;
                case 2:
                    assertEquals( (int) (0.2 * 5000), game.players.get(0).getScore());
                    assertEquals((int) (0.2 * 0), game.players.get(1).getScore());
                    break;
                case 3:
                    assertEquals(1000, game.players.get(0).getScore());
                    assertEquals(0, game.players.get(1).getScore());
                    break;
                case 4:
                    assertEquals(5000, game.players.get(0).getScore());
                    assertEquals(0, game.players.get(1).getScore());
                    break;
            }
            game.players.get(1).setScore(0);
            game.players.get(0).setScore(0);
        }
    }

    @Test
    public void toHighScoreFile() {
        Game game = new Game(1,1);
        game.setHighScoreFileName("highScoreTest.txt");
        game.toHighScoreFile("player", 1000, false);

        BufferedReader br = null;

        File file = new File("highScoreTest.txt");

        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            String username = "";
            int score = -999999999;
            int onevonewins = -1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                username = parts[0];
                score = Integer.parseInt(parts[1]);
                onevonewins = Integer.parseInt(parts[2]);
            }
            assertEquals("player", username);
            assertEquals(1000, score);
            assertEquals(0, onevonewins);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(br != null)
                    br.close();
            }
            catch (IOException e){
                    e.printStackTrace();
            }
        }

        game.toHighScoreFile("player", 7000, false);
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            String username = "";
            int score = -999999999;
            int onevonewins = -1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                username = parts[0];
                score = Integer.parseInt(parts[1]);
                onevonewins = Integer.parseInt(parts[2]);
            }
            assertEquals("player", username);
            assertEquals(7000, score);
            assertEquals(0, onevonewins);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(br != null)
                    br.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        game.toHighScoreFile("player",500, false);
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            String username = "";
            int score = -999999999;
            int onevonewins = -1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                username = parts[0];
                score = Integer.parseInt(parts[1]);
                onevonewins = Integer.parseInt(parts[2]);
            }
            assertEquals("player", username);
            assertEquals(7000, score);
            assertEquals(0, onevonewins);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(br != null)
                    br.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        game.toHighScoreFile("player", 0,true);
        try {
            br = new BufferedReader(new FileReader(file));
            String line = "";
            String username = "";
            int score = -999999999;
            int onevonewins = -1;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                username = parts[0];
                score = Integer.parseInt(parts[1]);
                onevonewins = Integer.parseInt(parts[2]);
            }
            assertEquals("player", username);
            assertEquals(1, onevonewins);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(br != null)
                    br.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

        file.delete();
    }

    @Test
    public void bestPlayers() {
        Game game = new Game(1,1);
        game.setHighScoreFileName("highScoreTest.txt");

        game.toHighScoreFile("player1", 1000, false);
        game.toHighScoreFile("player2", 900,  false);
        game.toHighScoreFile("player3",0,true);
        game.toHighScoreFile("player4", 1200, false);
        game.toHighScoreFile("player5", 0, true);
        game.toHighScoreFile("player5", 0, true);

        File file = new File("highScoreTest.txt");

        ArrayList<String> goodestPlayers = game.bestPlayers();
        assertEquals("player4",goodestPlayers.get(0));
        assertEquals("1200", goodestPlayers.get(1));
        assertEquals("player5",goodestPlayers.get(2));
        assertEquals("2",goodestPlayers.get(3));

        try {
            PrintWriter writer = new PrintWriter(file);
            writer.print("");
            writer.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    @Test
    public void fillQuestions(){
        Game game = new Game(1,2);

        assertEquals(46, game.allQuestions.size());
    }

    @Test
    public void selectQuestions(){
        Game game = new Game(2,1);

        ArrayList<Question> questions = game.selectQuestions(7,12);

        for(int i = 7; i < 13; i++){
            assertEquals(questions.get(i-7).getPrompt(), game.allQuestions.get(i).getPrompt());
        }

    }

}