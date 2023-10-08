import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    @Test
    public void setName() {
        Player player = new Player(0, "name test");
        player.setName("it works");
        assertEquals("it works", player.getName());
    }

    @Test
    public void setScore() {
        Player player = new Player(0,"player");
        player.setScore(1000);
        assertEquals(1000, player.getScore());
        int autreScore = player.getScore() * 2;
        player.setScore( autreScore );
        assertEquals(autreScore, player.getScore());
    }

    @Test
    public void setAnswerPosition() {
        Player player = new Player(0, "player");
        player.setAnswerPosition(1);
        assertEquals(1, player.getAnswerPosition());
        player.setAnswerPosition(2);
        assertEquals(2, player.getAnswerPosition());
        player.setAnswerPosition(3);
        assertEquals(3, player.getAnswerPosition());
    }

    @Test
    public void getAnswerPosition() {
        Player player = new Player(0, "player");
        player.setAnswerPosition(1);
        assertEquals(1, player.getAnswerPosition());
        player.setAnswerPosition(2);
        assertEquals(2, player.getAnswerPosition());
        player.setAnswerPosition(3);
        assertEquals(3, player.getAnswerPosition());
    }

    @Test
    public void getScore() {
        Player player1 = new Player(50, "player1");
        assertEquals(50, player1.getScore());
        player1.setScore(100);
        assertEquals(100, player1.getScore());
        Player player2 = new Player();
        assertEquals(0, player2.getScore());
    }

    @Test
    public void getName() {
        Player player1 = new Player();
        assertEquals("Player", player1.getName());
        Player player2 = new Player(0,"name test");
        assertEquals("name test", player2.getName());
    }

    @Test
    public void newScore() {
        Player player = new Player();
        player.newScore(50);
        assertEquals(50, player.getScore());
        player.newScore(-10);
        assertEquals(40, player.getScore());
        player.newScore(-50);
        assertEquals(-10, player.getScore());
    }

    @Test
    public void hasAnswered() {
        Player player = new Player();
        assertEquals(false, player.hasAnswered());
        player.setAnswerPosition(1);
        assertEquals(true, player.hasAnswered());
        player.setAnswerPosition(2);
        assertEquals(true, player.hasAnswered());
        player.setAnswerPosition(3);
        assertEquals(true, player.hasAnswered());
        player.setAnswerPosition(4);
        assertEquals(true, player.hasAnswered());
    }
}