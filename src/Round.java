import java.util.ArrayList;

/**
 *  η κλάση αυτή αναπαριστά έναν γύρο.
 *
 * @author Eleni Mandana Christos Apostolidis
 */
public class Round {

    /**
     * ο τύπος του γύρου.
     */
    private int roundType;
    /**
     * οι ερωτήσεις που καλείται να απαντήσει ο παίκτης.
     */
    private ArrayList<Question> questions;
    /**
     * ο αριθμός των ερωτήσεων
     */
    private int numOfQuestions;
    /**
     * οι επιλογές του παίκτη για να ποντάρει.
     */
    private ArrayList<Integer> bettingOptions;

    /** κατασκευαστής
     *
     * @param questions το array list με τις ερωτήσεις που θα κληθεί να απαντήσει ο παίκτης.
     *
     * @param roundType ο τύπος του γύρου.
     */
    public Round(ArrayList questions, int roundType){
        this.questions = questions; // εδω πρέπει το size του array να είναι ίσο με τον αριθμό των ερωτήσεων ή να προσαρμόζεται ώστε να είναι ίσο με τον αριθμό των ερωτήσεων.
        this.roundType = roundType; // τύπος γύρου.

        bettingOptions = new ArrayList<>(); // οι πόντοι που μπορεί να ποντάρει ο παίκτης στον γύρο ποντάρισμα.
            bettingOptions.add(250);
            bettingOptions.add(500);
            bettingOptions.add(750);
            bettingOptions.add(1000);
    }

    /**
     * questions getter
     *
     * @return την λίστα ερωτήσεων
     */
    public ArrayList<Question> getQuestions() {
        return this.questions;
    }

    /**
     * roundType getter
     * @return την κατηγορία της γύρας
     */
    public int getRoundType() {
        return this.roundType;
    }

    /**
     *
     * @param option θέση στην λίστα bettingOptions
     * @return έναν int από την λίστα bettingOptions
     */
    public int getBettingOption(int option) {
        return bettingOptions.get(option);
    }

    /**
     * @return τον τίτλο της κατηγορίας του γύρου
     */
    public String getRoundTitle() {
        String title = "";
        switch(roundType) {
            case 0:
                title = "Σωστή Απάντηση";
                break;
            case 1:
                title = "Ποντάρισμα";
                break;
            case 2:
                title = "Σταμάτα το χρονόμετρο";
                break;
            case 3:
                title = "Γρήγορη απάντηση";
                break;
            case 4:
                title = "Θερμόμετρο";
                break;
        }
        return title;
    }

    /**
     *
     * @return την περιγραφή των κανόνων του γύρου
     */
    public String getRoundDescription() {
        String description = "";
        switch(roundType) {
            case 0:
                description = "Κάθε σωστή απάντηση αξίζει 1000 πόντους!";
                break;
            case 1:
                description = "Πόνταρε πόντους γνωρίζωντας μόνο την κατηγορία και κέρδισε την διπλάσια ποσότητα!";
                break;
            case 2:
                description = "Όσο πιο γρήγορα απαντάς, τόσο πιο πολλούς πόντους κερδίζεις!";
                break;
            case 3:
                description = "Ο πρώτος παίχτης που απαντήσεις σωστά κερδίζει 1000 πόντους, ενώ ο δεύτερος 500!";
                break;
            case 4:
                description = "Ο πρώτος παίχτης που απαντήσει 5 ερωτήσεις σωστά κερδίζει 5000 πόνοτυς!";
                break;
        }
        return description;
    }

    /**
     *
     * @return επιστρέφει το μέγεθος της λίστας questions
     */
    int getNumOfQuestions() {
        return questions.size();
    }

}
