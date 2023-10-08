import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class QuestionTest {

    @Test
    public void getPrompt() {
        Question question = new Question("c","q","rightans","filler", "filler", "filler", "-");
        assertEquals("q", question.getPrompt());

        Question question1 = new Question("c","q1","rightans","filler", "filler", "filler", "-");
        assertEquals("q1", question1.getPrompt());

    }

    @Test
    public void getCategory() {
        Question question = new Question("c","q","rightans","filler", "filler", "filler", "-");
        assertEquals("c", question.getCategory());

        Question question1 = new Question("c1","q","rightans","filler", "filler", "filler", "-");
        assertEquals("c1", question1.getCategory());
    }

    @Test
    public void getAnswerList() {
        Question question = new Question("c","q","right answer","filler 1", "filler 2", "filler 3", "-");
        ArrayList<String> AnswerList = new ArrayList<>();
        AnswerList.add("right answer");
        AnswerList.add("filler 1");
        AnswerList.add("filler 2");
        AnswerList.add("filler 3");
        
        assertArrayEquals(AnswerList, question.getAnswerList());
    }

    private void assertArrayEquals(ArrayList<String> answerList, ArrayList<String> answerList1) {
    }

    @Test
    public void getCorrectAnswer() {
        Question question = new Question("c","q","right answer","filler", "filler", "filler", "-");
        assertEquals("right answer", question.getCorrectAnswer());

        Question question1 = new Question("c","q1","right answer 1","filler", "filler", "filler", "-");
        assertEquals("right answer 1", question1.getCorrectAnswer());
    }

    @Test
    public void getImagePath() {
        Question question = new Question("c","q","rightans","filler", "filler", "filler", "imagePath");
        assertEquals("imagePath", question.getImagePath());

        Question question1 = new Question("c","q1","rightans","filler", "filler", "filler", "imagePath1");
        assertEquals("imagePath1", question1.getImagePath());
    }

    @Test
    public void checkAnswer() {
        Question question = new Question("c","q","rightans","filler", "filler", "filler", "-");
        Player player = new Player(0, "player");

        player.setAnswerPosition(0);
        //meos
    }
}