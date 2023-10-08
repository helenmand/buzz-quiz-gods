import java.util.ArrayList;
import java.util.Collections;

/**
 * η κλάση αυτή αναπαριστά μία έρωτηση.
 *
 * @author : Christos Apostolidis Eleni Mandana
 */

public class Question {

    /** Αποθηκεύει το κείμενο της ερώτησης **/
    private String prompt;
    /** Αποθηκεύει όλες τις απαντήσεις **/
    private ArrayList<String> answers;
    /** Αποθηκεύει την θέση της σωστής απάντησης στην answers **/
    private int correctAnswerPosition;
    /** Αποθηκέυει την κατηγορία της ερώτησης **/
    private String category;
    /** Αποθηκεύει το path της εικόνας (για τις ερωτήσεις με εικόνα) **/
    private String imagePath;

    /**
     * κατασκευαστής
     *
     * @param category κατηγορία της ερώτησης (πχ. Αθλητισμός, Ιστορία κτλ.)
     * @param prompt η εκφώνηση της ερώτησης
     * @param correctAnswer η σωστή απαντήση
     * @param falseAnswer1 λάθος απάντηση
     * @param falseAnswer2 λάθος απάντηση
     * @param falseAnswer3 λάθος απάντηση
     * @param imagePath το όνομα αρχείου της εικόνας της ερώτησης
     */
    public Question
    (String category,
     String prompt,
     String correctAnswer,
     String falseAnswer1,
     String falseAnswer2,
     String falseAnswer3,
     String imagePath)
    {
        this.prompt = prompt;
        this.category = category;
        this.imagePath = imagePath;
        // οι επιλογές του παίκτη ως πιθανή σωστή απάντηση για την ερώτηση που καλείται να απαντήσει.
        this.answers = new ArrayList<>();
        this.answers.add(correctAnswer);
        this.answers.add(falseAnswer1);
        this.answers.add(falseAnswer2);
        this.answers.add(falseAnswer3);

        Collections.shuffle(this.answers); //Ανακάτεμα της λίστας απαντήσεων

        //Αναζήτηση νέας θέσης της σωστής απάντησης στο ArrayList answers
        for(int i=0;i<4;i++)
            if (correctAnswer.compareTo(this.answers.get(i)) == 0)
                this.correctAnswerPosition = i;
    }

    /**
     * prompt getter
     *
     * @return την εκφώνηση της ερώτησης
     */
    public String getPrompt() {
        return this.prompt;
    }

    /**
     * Category getter
     *
     * @return το είδος της κατηγορίας της ερώτησης (πχ. Αθλητισμός)
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * AnswerList getter
     *
     * @return την λίστα με τις επιλογές του χρήστη.
     */
    public ArrayList<String> getAnswerList() {
        return this.answers;
    }

    /**
     * CorrectAnswer getter
     *
     * @return τη σωστή απάντηση της ερώτησης.
     */
    public String getCorrectAnswer() {
        return this.answers.get(this.correctAnswerPosition);
    }

    /**
     * correctAnswerPosition getter
     *
     * @return την θέση της σωστής απάντησης
     */
    public int getCorrectAnswerPosition() { return this.correctAnswerPosition; }

    /** Image Path getter
     *
     * @return το path της εικόνας
     */
    public String getImagePath(){
        return this.imagePath;
    }

    /**
     * ελέγχει την ορθότητα της απάντησης του παίκτη.
     * @param answerPos η θέση της απάντησης του παίκτη στον πίνακα των απαντήσεων.
     * @return true, αν ταυτίζεται η απαντηση του με την σωστή (η επιλογη του 1,2,3 ή 4 με τη θέση της σωστής απαντησης), false, αν δεν ταυτίζεται.
     */
    public boolean checkAnswer(int answerPos) {
        if(this.correctAnswerPosition == answerPos)
            return true;
        else
            return false;
    }
}