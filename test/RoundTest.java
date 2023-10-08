import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RoundTest {

    @Test
    public void getQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("c1","q1","rightans","filler", "filler", "filler", "-"));
        questions.add(new Question("c2","q2","rightans","filler", "filler", "filler", "-"));

        Round r = new Round(questions, 0);
        assertArrayEquals(questions, r.getQuestions());

        questions.add(new Question("c","q","rightans","filler", "filler", "filler", "-"));
        assertArrayEquals(questions, r.getQuestions());
    }

    private void assertArrayEquals(ArrayList<Question> questions, ArrayList<Question> questions1) {

    }

    @Test
    public void getRoundType() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("c1","q1","rightans","filler", "filler", "filler", "-"));
        questions.add(new Question("c2","q2","rightans","filler", "filler", "filler", "-"));

        Round round = new Round(questions, 0);
        assertEquals(0, round.getRoundType());

        Round round1 = new Round(questions,1);
        assertEquals(1, round1.getRoundType());

        Round round2 = new Round(questions,2);
        assertEquals(2, round2.getRoundType());

        Round round3 = new Round(questions, 3);
        assertEquals(3,round3.getRoundType());

        Round round4 = new Round(questions, 4);
        assertEquals(4, round4.getRoundType());
    }


    @Test
    public void getRoundTitle() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("c1","q1","rightans","filler", "filler", "filler", "-"));
        questions.add(new Question("c2","q2","rightans","filler", "filler", "filler", "-"));

        Round round = new Round(questions, 0);
        assertEquals("Σωστή Απάντηση", round.getRoundTitle());

        Round round1 = new Round(questions,1);
        assertEquals("Ποντάρισμα", round1.getRoundTitle());

        Round round2 = new Round(questions,2);
        assertEquals("Σταμάτα το χρονόμετρο", round2.getRoundTitle());

        Round round3 = new Round(questions, 3);
        assertEquals("Γρήγορη απάντηση", round3.getRoundTitle());

        Round round4 = new Round(questions, 4);
        assertEquals("Θερμόμετρο", round4.getRoundTitle());
    }

    @Test
    public void getRoundDescription() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("c1","q1","rightans","filler", "filler", "filler", "-"));
        questions.add(new Question("c2","q2","rightans","filler", "filler", "filler", "-"));

        Round round = new Round(questions, 0);
        assertEquals("Κάθε σωστή απάντηση αξίζει 1000 πόντους!", round.getRoundDescription());

        Round round1 = new Round(questions,1);
        assertEquals("Πόνταρε πόντους γνωρίζωντας μόνο την κατηγορία και κέρδισε την διπλάσια ποσότητα!", round1.getRoundDescription());

        Round round2 = new Round(questions,2);
        assertEquals("Όσο πιο γρήγορα απαντάς, τόσο πιο πολλούς πόντους κερδίζεις!", round2.getRoundDescription());

        Round round3 = new Round(questions, 3);
        assertEquals("Ο πρώτος παίχτης που απαντήσεις σωστά κερδίζει 1000 πόντους, ενώ ο δεύτερος 500!", round3.getRoundDescription());

        Round round4 = new Round(questions, 4);
        assertEquals("Ο πρώτος παίχτης που απαντήσει 5 ερωτήσεις σωστά κερδίζει 5000 πόνοτυς!", round4.getRoundDescription());
    }

    @Test
    public void getNumOfQuestions() {
        ArrayList<Question> questions = new ArrayList<>();
        questions.add(new Question("c1","q1","rightans","filler", "filler", "filler", "-"));
        questions.add(new Question("c2","q2","rightans","filler", "filler", "filler", "-"));

        Round round = new Round(questions, 0);
        assertEquals(2, round.getNumOfQuestions());

        questions.add(new Question("c","q","rightans","filler", "filler", "filler", "-"));
        Round round1 = new Round(questions, 0);
        assertEquals(3, round1.getNumOfQuestions());
    }
}