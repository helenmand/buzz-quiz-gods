/**
 * η κλάση αυτή αναπαριστά έναν παίκτη του παιχνιδιού.
 *
 * @author Eleni Mandana Christos Apostolidis
 */

public class Player {

    /** Το σκορ του παίχτη **/
    private int score;
    /** Το όνομα του παίχτη **/
    private String name;
    /** Η θέση της τελευταίας απάντησης που επέλεξε ο παίχτης. Εάν -1, ο παίκτης δεν απάντησε **/
    private int answerPosition;

    /**
     * κατασκευαστής
     *
     * @param name το όνομα του παίκτη
     *
     * @param score το σκόρ του παίκτη
     */
    public Player(int score, String name){
        this.score = score;
        this.name = name;
        this.answerPosition = -1;
    }

    /** κενός κατασκευαστής
     *
     * θέτει το όνομα του παίκτη σε Player και το σκορ του παίχτη σε 0.
     *
     */
    public Player() {
        this.score = 0;
        this.name = "Player";
        this.answerPosition = -1;
    }

    /**
     * name setter
     *
     * @param name το νέο όνομα του παίκτη.
     */
    public void setName(String name) {
        String pureName = name.replace(",","");
        this.name = pureName;
    }

    /**
     * score setter
     *
     * @param score το νέο σκόρ του παίκτη
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * answer position setter
     *
     * @param answerPosition η νέα θέση της σωστής απάντησης
     */
    public void setAnswerPosition(int answerPosition){
        this.answerPosition = answerPosition;
    }

    /**
     * answer position getter
     *
     * @return την θέση της απάντηση του παίκτη (1, 2, 3, 4).
     */
    public int getAnswerPosition(){
        return this.answerPosition;
    }

    /**
     *  score getter
     *
     * @return το σκορ του παίχτη.
     */
    public int getScore() {
        return score;
    }

    /**
     * name getter
     *
     * @return το όνομα του παίκτη.
     */
    public String getName() {
        return name;
    }

    /**
     * στη μέθοδο αυτή προσθέτουμε πάντα τον αριθμό των πόντων που κερδίζει (μπορεί να κερδίσει αρνητικούς
     * πόντους, δηλαδή να χάσει την ερώτηση) ένας παίκτης
     * σε κάθε ερώτηση του παιχνιδιού.
     *
     * @param points οι πόντοι που θα προστεθούν.
     */
    public void newScore(int points){
        score += points;
    }

    /**
     * αν το answerPosition είναι αρνητικό θεωρούμε ότι ο παίχτης δεν έχει απαντήσει
     * @return αν έχει απαντήση ο παίχτης
     */
    public boolean hasAnswered() {
        return answerPosition>=0;
    }
}
