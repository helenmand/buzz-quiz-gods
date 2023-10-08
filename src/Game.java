import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Η κλάση αναπαριστά το παιχνίδι buzz quiz.
 *
 * @author Christos Apostolidis, Eleni Mandana
 */
public class Game {

    /**
     * ο αριθμός του τρέχων γύρου
     */
    protected int currentRound;
    /**
     * Αριθμός γύρων που θα παίξει ο παίχτης
     */
    protected int numOfRounds;
    /**
     * ο αριθμός της τρέχων ερώτησης του τρέχων γύρου
     */
    protected int currentQuestion;
    /**
     * Αριθμός ερωτήσεων ανά γύρο
     */
    private int numOfQuestions;
    /**
     * Η ώρα του υπολογιστή μόλις εμφανίζεται μία ερώτηση
     */
    protected long questionStartingTime;
    /**
     * Αποθηκεύει όλες τις πιθανές ερωτήσεις του παιχνιδιού
     */
    protected ArrayList<Question> allQuestions;
    /**
     * Αποθηκέυει όλες τις γύρες του παιχνιδιού
     */
    protected ArrayList<Round> rounds;
    /**
     * Αποθηκεύει όλους τους παίχτες που συμμετάσχουν στο παιχνίδι
     */
    protected ArrayList<Player> players;
    /**
     * Οι πόντοι που θα κερδίσουν οι παίκτες σε μία ερώτηση
     */
    protected ArrayList<Integer> playerRewards;
    /**
     * η σειρά που απάντησαν οι παίκτες μία ερώτηση
     */
    protected ArrayList<Integer> playerAnswerOrder;
    /**
     * Ο αριθμός σωστών απαντήσεων που έχει δώσει ο κάθε παίχτης
     */
    protected ArrayList<Integer> playerCorrectAnswerAmount;
    /**
     * Η ώρα του υπολογιστή όταν έδωσε την απάντηση του ο κάθε παίχτης
     */
    protected ArrayList<Long> playerAnswerTime;
    /**
     * Εαν έχει τελοιώσει το παιχνίδι
     */
    protected boolean gameHasEnded;
    /**
     * τo όνομα του αρχείου με τα high scores
     */
    protected String highScoreFileName;


    /**
     * κατασκευαστής
     *
     * @param numOfRounds ο αριθμός των γύρων που θα παίξει ο παίχτης.
     * @param numOfQuestions ο αριθμός των ερωτήσεψν που καλείται να απαντήσει ανά γύρο.
     */
    public Game(int numOfRounds, int numOfQuestions) {

        this.currentRound = 0;
        this.numOfRounds = numOfRounds;
        this.currentQuestion = 0;
        this.numOfQuestions = numOfQuestions;
        this.players = new ArrayList<Player>();
        this.playerRewards = new ArrayList<Integer>();
        this.playerAnswerOrder = new ArrayList<Integer>();
        this.playerCorrectAnswerAmount = new ArrayList<>();
        this.playerAnswerTime = new ArrayList<>();
        this.gameHasEnded = false;
        this.highScoreFileName = "highScores.txt";

        this.allQuestions = new ArrayList<>();
        fillQuestions();
        Collections.shuffle(this.allQuestions); // μπέρδεμα της λίστας των συνολικών ερωτήσεων

        this.rounds = new ArrayList<>();
    }

    /**
     * high score file name setter
     * @param newName το όνομα του αρχείου που θέλουμε να διαβάσουμε
     */
    public void setHighScoreFileName(String newName){
        this.highScoreFileName = newName;
    }

    /**
     * player adder
     * @param player new player in the game
     */
    public void addPlayer(Player player){
        this.players.add(player);
        this.playerRewards.add(0);
        this.playerAnswerTime.add((long) 0 );
        playerCorrectAnswerAmount.add(0);
    }

    /**
     * getter του αριθμού των παικτών
     *
     * @return ο αριθμός των παικτων του παιχνιδιού που είναι ίσο με το μέγεθος του array των παικτών
     */
    protected int getNumOfPlayers(){
        return players.size();
    }

    /**
     * δημιουργούμε ένα ArrayList με τους συνολικούς γύρους που θα παίξει ο παιχτής
     */
    protected void createRounds() {

        //Αποθηκεύει τους τύπους γύρου που υπάρχουν στο παιχνίδι με κωδικό 0, 1, 2, 3, 4.
        ArrayList<Integer> roundTypes = new ArrayList<>();

        roundTypes.add(0); // τύπος γύρου: Σωστή Απάντηση
        roundTypes.add(1); // τύπος γύρου: Ποντάρισμα
        roundTypes.add(2); // τύπος γύρου: Σταμάτα το χρονόμετρο

        if(getNumOfPlayers()>1) {
            roundTypes.add(3); // τύπος γύρου: Γρήγορη Απάντηση
            roundTypes.add(4); //τύπος γύρου: Θερμόμετρο
        }

        int start = 0;
        int end = this.numOfQuestions - 1;

        //Δημιουργούνται όλοι η γύροι που θα παίξει ο παίχτης
        for(int i=0;i<this.numOfRounds;i++) {

            //Η κατηγορία κάθε γύρου είναι τυχαία επιλεγμένη
            if(i%roundTypes.size() == 0)
                Collections.shuffle(roundTypes);

            if(roundTypes.get( i%roundTypes.size() ) == 4) {
                this.rounds.add(new Round(selectQuestions(start, start+19), 4));
                start = start+20;
                end = start+(numOfQuestions-1);
            } else {
                this.rounds.add(new Round(selectQuestions(start, end), roundTypes.get(i%roundTypes.size())));
                start+=this.numOfQuestions;
                end+=this.numOfQuestions;
            }



        }
    }

    /**
     * διαλέγουμε ένα κομμάτι του ArrayList allQuestions με αρχή start και τέλος end, και το αντιγράφουμε σε
     * μία καινούργια λίστα. Το μέγεθος της λίστας αυτής ορίζεται από τον αριθμό των ερωτήσεων που θα κληθεί να
     * απαντήσει ο παίκτης του παιχνιδιού σε κάθε γύρο.
     *
     * @param start η αρχή του διαστήματος
     * @param end το τέλος του διαστήματος
     * @return το ArrayList με μερικές από τις ερωτήσεις του allQuestions
     */
    protected ArrayList<Question> selectQuestions (int start, int end) {
        ArrayList<Question> newArray = new ArrayList<>();
        for(int i=start;i<=end;i++)
            newArray.add(this.allQuestions.get(i));
        return newArray;
    }

    /**
     * Γεμίζει την λίστα playerRewards με τις σωστές τιμές και έπειτα αλλάζει το σύνολο πόντων του κάθε παίκτη
     * σύμφωνα με την playerRewards. Η απάντηση του κάθε παίκτη γίνεται "-1" και καθαρίζεται η playerAnswerOrder.
     * Τέλος αυξάνει το currentQuestion κατα ένα. Για να αλλάξει ο γύρος πρέπει να έχουν τελειώσει οι ερωτήσεις του κάθε γύρου ή να πετύχει ο παίκτης streak
     * 5 σωστών απαντήσεων, πράγμα που συμβαίνει μόνο στο θερμόμετρο.
     */
    protected void rewardCreator() {
        int roundType = rounds.get(currentRound).getRoundType();
        Question question = rounds.get(currentRound).getQuestions().get(currentQuestion);
        boolean foundFiveQuestionStreak = false;

        switch(roundType) {
            case 0:

                for(int i=0;i<getNumOfPlayers();i++) {
                    if(question.checkAnswer(players.get(i).getAnswerPosition())) {
                        playerRewards.set(i, 1000);
                    } else {
                        playerRewards.set(i, 0);
                    }
                }
                break;
            case 1:
                for(int i=0;i<getNumOfPlayers();i++) {
                    if(!question.checkAnswer(players.get(i).getAnswerPosition())) {
                        playerRewards.set(i, -playerRewards.get(i));
                    }
                }
                break;
            case 2:
                double timeMultiplier = 0.2;
                for(int i = 0; i<getNumOfPlayers(); i++){
                    double trueTime = timeMultiplier*( 5000 - (playerAnswerTime.get(i) - questionStartingTime) );
                    if(question.checkAnswer(players.get(i).getAnswerPosition()) && trueTime > 0){
                        playerRewards.set(i, (int) trueTime);
                    } else {
                        playerRewards.set(i, 0);
                    }
                }
                break;
            case 3:
                int tempReward = 1000;
                for(int i : playerAnswerOrder) {
                    if(question.checkAnswer(players.get(i).getAnswerPosition())) {
                        playerRewards.set(i, tempReward);
                        tempReward -= 500;
                    } else {
                        playerRewards.set(i, 0);
                    }
                }
                break;
            case 4:
                for(int i : playerAnswerOrder) {
                    if(question.checkAnswer(players.get(i).getAnswerPosition())) {
                        playerCorrectAnswerAmount.set(i, playerCorrectAnswerAmount.get(i) + 1);
                        if(playerCorrectAnswerAmount.get(i) == 5 && !foundFiveQuestionStreak) {
                            playerRewards.set(i, 5000);
                            for(int j=0;j<getNumOfPlayers();j++) {
                                if(j!=i) {
                                    playerRewards.set(j, 0);
                                }
                            }
                            foundFiveQuestionStreak = true;
                        }
                    }
                }
                break;
        }



        for(int i=0; i<getNumOfPlayers(); i++) {
            players.get(i).newScore(playerRewards.get(i));
            players.get(i).setAnswerPosition(-1);
        }
        playerAnswerOrder.clear();

        currentQuestion++;

        if(currentQuestion>=rounds.get(currentRound).getNumOfQuestions() || foundFiveQuestionStreak) {
            currentRound++;

            currentQuestion = 0;
            if(currentRound>=numOfRounds) {
                gameHasEnded = true;
            }
        }
    }

    /**
     * γεμίζει το ArrayList allQuestions με όλες τις πιθανές ερωτήσεις του παιχνιδιού. Τις ερωτήσεις τις τραβάει από το αρχείο
     * questions1.txt και η μορφή τους είναι:
     *  [Τυπος ερωτησης,Ερώτηση,Σωστή απάντηση, Λάθος απάντηση 1, Λάθος απάντηση 2, Λάθος απάντηση 3,path εικόνας]
     * Στον πίνακα parts αποθηκεύετε κάθε όρισμα του constructor της Question, δηλαδή η κάθε γραμμή χωρίζει τα ορίσματα με κόμμα.
     * Σε περίπτωση που η ερώτηση δεν περιλαμβάνει εικόνα το path είναι παύλα.
     */
    private void fillQuestions () {
        File file = new File("questions1.txt");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] parts = line.split("@");
                    this.allQuestions.add(new Question(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            return;
        }
    }

    /**
     * Με τη μέθοδο αυτή ενημερώνουμε το αρχείο με το ατομικό high Score.
     * Αν δεν υπάρχει high score ήδη τότε δημιουργείται το αρχείο αλλιώς τροποποιούμε κατάλληλα το αρχείο αν
     * έχει ξεπεραστεί το σκορ του προηγούμενου σκορερ.
     *
     * @param username το username του παίκτη.
     * @param score το σκορ που πέτυχε σε αυτόν τον γύρο.
     * @param hasWon true εαν νίκησε ο παίκτης το Versus, false εάν δεν νίκησε.
     */
    public void toHighScoreFile(String username, int score, boolean hasWon) {

        int onevonewins = hasWon? 1 : 0;

        String oldFileName = highScoreFileName;
        String tempFileName = "highScores_temp.txt";

        BufferedReader br = null;
        BufferedWriter bw = null;

        File file = new File(oldFileName);

        if(file.length() != 0 ) {
            try {
                br = new BufferedReader(new FileReader(oldFileName));
                bw = new BufferedWriter(new FileWriter(tempFileName));
                String line;

                boolean isAlreadyPlayer = false;

                while ((line = br.readLine()) != null) {

                    String[] parts = line.split(",");
                    int previousScore = Integer.parseInt(parts[1]);
                    int prevOneVoneWins = Integer.parseInt(parts[2]);
                    if(parts[0].equals(username)) {
                        isAlreadyPlayer = true;
                        if (previousScore < score)
                            line = username + "," + score + "," + prevOneVoneWins;
                        if(onevonewins == 1)
                            line = username + "," + previousScore + "," + ++prevOneVoneWins;
                    }
                    bw.write(line + "\n");
                }
                if(!isAlreadyPlayer) {
                    line = username + "," + score + "," + onevonewins;
                    bw.write(line + "\n");
                }
            }
            catch (Exception e) {
                return;
            }
            finally{
                try{
                    if (br != null)
                        br.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                try{
                    if (bw != null)
                        bw.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File oldFile = new File(oldFileName);
            oldFile.delete();

            File newFile = new File(tempFileName);
            newFile.renameTo(oldFile);
        }
        else{
            try {
                bw = new BufferedWriter(new FileWriter(oldFileName));
                bw.write(username + "," + score + "," + onevonewins +"\n");
                bw.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * προσθέτει τα σκορ των παικτών στο αρχείο των σκορ. Αν ο παίκτης έπαιζε μόνος στη θέση του ορίσματος αν νίκησε στο παιχνίδι
     * των 2 παικτών      
     */
    protected void endGame() {
        if (getNumOfPlayers() == 1){
            toHighScoreFile(players.get(0).getName(), players.get(0).getScore(), false);
        } else {
            toHighScoreFile(players.get(0).getName(), 0, players.get(0).getScore() > players.get(1).getScore());
            toHighScoreFile(players.get(1).getName(), 0, players.get(0).getScore() < players.get(1).getScore());
            }
        }

    /**
     * αναζητάει τους καλύτερους παίκτες που έχουν υπάρξει στο παιχνίδι.
     *
     * @return ένα array list τύπου string που περιέχει σε κάθε θέση:
     *      0. όνομα παίκτη με το μεγαλύτερο σκορ στο ατομικό παιχνίδι
     *      1. μεγαλύτερο σκορ στο ατομικό παιχνίδι
     *      2. όνομα παίκτη με το μεγαλύτερο σκορ νικών στο παιχνίδι 2 παικτών
     *      3. μεγαλύτερο σκορ νικών στο παιχίδι των 2 παικτών
     */
    protected ArrayList<String> bestPlayers(){
        ArrayList<String> goodestPlayers = new ArrayList<String>();

        String fileName = highScoreFileName;
        File file = new File(fileName);
        boolean fileIsEmpty = (file.length() == 0) ? true : false;

        if(!fileIsEmpty) {
            ArrayList<String> usernames = new ArrayList<String>();
            ArrayList<Integer> personalHighScores = new ArrayList<Integer>();
            ArrayList<Integer> totalWins = new ArrayList<Integer>();

            try {
                BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
                String line = "";

                while ((line = br.readLine()) != null) {
                    String[] parts = line.split(",");
                    usernames.add(parts[0]);
                    personalHighScores.add(Integer.parseInt(parts[1]));
                    totalWins.add(Integer.parseInt(parts[2]));
                }

                int maxScore = Collections.max(personalHighScores);
                goodestPlayers.add(usernames.get(personalHighScores.indexOf(maxScore)));
                goodestPlayers.add(String.valueOf(maxScore));

                int maxWins = Collections.max(totalWins);
                goodestPlayers.add((usernames.get(totalWins.indexOf(maxWins))));
                goodestPlayers.add(String.valueOf(maxWins));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            goodestPlayers.add("None");
            goodestPlayers.add("0");
            goodestPlayers.add("None");
            goodestPlayers.add("0");
        }
        return goodestPlayers;
    }
}
