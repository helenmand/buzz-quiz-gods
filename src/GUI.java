import javax.swing.*;
import java.awt.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

/**
 * Υλοποιεί το buzz quiz σε Graphical User Interface
 *
 * @author Christos Apostolidis, Eleni Mandana
 */
public class GUI extends Game{
    /** το frame του GUI **/
    private JFrame mainFrame;

    /** Το panel που εμφανίζεται στην αρχή **/
    private JPanel startupPanel;

    /** Το panel που εμφανίζει τα στατιστικά **/
    private JPanel highScorePanel;

    /** Το panel που δίνει το όνομα του ο/οι παίχτης/τες **/
    private JPanel usernamePanel;

    /** το panel Που περιέχει το μήνυμα νέου γύρου **/
    private JPanel roundPanel;
    /** αναγράφει τον αριθμό των γύρων που έχουν παιχτεί **/
    private JLabel roundNumber;
    /** αναγράφει τον τύπο του τρέχων γύρου **/
    private JLabel roundTypeLabel;
    /** μικρή επεξήγηση του τρέχων γύρου **/
    private JLabel roundDescription;

    /** το panel που παρουσιάζει μία ερώτηση **/
    private JPanel questionPanel;
    /** αναγράφει το σκορ του παίχτη 1 **/
    private JLabel score1;
    /** αναγράφει το όνομα του παίχτη 1 **/
    private JLabel name1;
    /** αναγράφει το σκορ του παίχτη 2 **/
    private JLabel score2;
    /** αναγράφει το όνομα του παίχτη 2 **/
    private JLabel name2;
    /** Εδώ εμφανίζεται το μήνυμα "πατήστε space για να συνεχίσετε" **/
    private JLabel info;
    /** αναγράφετε η κατηγορία της τρέχων ερώτησης **/
    private JLabel category;
    /** αναγράφει την ερώτηση προς απάντηση της τρέχων ερώτησης **/
    private JLabel prompt;
    /** περιέχει την εικόνα της τρέχων ερώτησης (εάν έχει) **/
    private JLabel picture;

    /** Οδηγίες για τους παίχτες, ποιο κουμπί να πατήσουν για να διαλέξουν ποια απάντηση **/
    private JLabel[][] playerKey;

    /** αναγράφει τις 4 απαντήσεις στην τρέχων ερώτηση **/
    private JLabel answer[];
    /** τα panels που αντιστοιχούν σε κάθε απάντηση **/
    private JPanel answerPanel[];

    /**
     * Ορίζει το JFrame του GUI και κατασκευάζει όλα τα βασικά JPanels
     *
     * @param numOfRounds αριθμός γύρων του παιχνιδιού
     * @param numOfQuestions αριθμός ερωτήσεων κάθε γύρου
     */
    public GUI(int numOfRounds, int numOfQuestions){

        super(numOfRounds, numOfQuestions);

        //Create GUI frame
        mainFrame = new JFrame("Buzz Quiz");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 600);
        mainFrame.setResizable(false);
        mainFrame.setFocusable(true);

        createStartupPanel();
        createUsernameInputPanel();
        createNewRoundPanel();
        createQuestionPanel();
        createHighScorePanel();
    }

    /**
     * Δημιουργεί το startupPanel που εμφανίζεται στην εκκίνηση του παιχνιδιού. Αποτελείται από τρία κουμπιά.
     * το "1 Player" κουμπί ξεκινάει το παιχνίδι για έναν παίχτη. Ομοίως το "2 Player" κουμπί αλλά για δύο παίχτες.
     * Το κουμπί "Στατιστικά" εμφανίζει το highScorePanel. Αν πατηθούν τα κουμπία "1 Player" ή "2 Player" θα εμφανιστεί
     * το usernameInputPanel
     */
    private void createStartupPanel() {
        //Create startup panel
        startupPanel = new JPanel(new GridBagLayout());

        JButton singlePlayerButton = new JButton("1 Player");
        JButton twoPlayerButton = new JButton("2 Players");
        JButton highScoreButton = new JButton("Στατιστικά");

        startupPanel.add(singlePlayerButton);
        startupPanel.add(twoPlayerButton);
        startupPanel.add(highScoreButton);

        singlePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPlayer(new Player());

                playerKey[0][0].setText("Q");
                playerKey[0][1].setText("W");
                playerKey[0][2].setText("A");
                playerKey[0][3].setText("S");
                createRounds();

                mainFrame.remove(startupPanel);
                mainFrame.add(usernamePanel);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        twoPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i=0;i<2;i++)
                {
                    addPlayer(new Player());
                }

                playerKey[0][0].setText("Q");
                playerKey[0][1].setText("W");
                playerKey[0][2].setText("A");
                playerKey[0][3].setText("S");

                playerKey[1][0].setText("I");
                playerKey[1][1].setText("O");
                playerKey[1][2].setText("K");
                playerKey[1][3].setText("L");
                createRounds();

                mainFrame.remove(startupPanel);
                mainFrame.add(usernamePanel);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

        highScoreButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.remove(startupPanel);
                mainFrame.add(highScorePanel);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });
    }

    /**
     * Δημιουργεί usernameInputPanel που εμφανίζεται στην είσοδο ονόματος. Αποτελείται από ένα JLabel ένα JTextField και
     * ένα JButton. Στο JTextField γράφει ο παίχτης το όνομα του. Όταν πατηθεί το JButton καταγράφεται το όνομα που έδωσε
     * ο παίχτης και 1) Ξεκινάει το παιχνίδι εμφανίζοντας το roundPanel ή 2) Εάν υπάρχει δεύτερος παίχτης ζητάει το όνομα
     * και του δεύτερου παίχτη.
     */
    private void createUsernameInputPanel() {
        //username input panel
        usernamePanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel usernameLabel = new JLabel("Γράψε το όνομα σου");
        JTextField usernameInput = new JTextField(20);
        JButton usernameConfirmationButton = new JButton("Ok");

        c.gridx = 0; c.gridy = 0; usernamePanel.add(usernameLabel, c);
        c.gridx = 0; c.gridy = 1;usernamePanel.add(usernameInput, c);
        c.gridx = 0; c.gridy = 2;usernamePanel.add(usernameConfirmationButton, c);

        usernameConfirmationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!usernameInput.getText().isEmpty()) {
                    players.get(0).setName(usernameInput.getText());
                    name1.setText(players.get(0).getName());

                    if(getNumOfPlayers()>=2) {
                        usernameConfirmationButton.removeActionListener(this);
                        usernameInput.setText("");

                        usernameConfirmationButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                if(!usernameInput.getText().isEmpty()) {
                                    players.get(1).setName(usernameInput.getText());
                                    name2.setText(players.get(1).getName());

                                    mainFrame.remove(usernamePanel);
                                    mainFrame.add(roundPanel);

                                    mainFrame.validate();
                                    mainFrame.repaint();

                                    showRound();
                                }
                            }
                        });
                    } else {
                        mainFrame.remove(usernamePanel);
                        mainFrame.add(roundPanel);

                        mainFrame.validate();
                        mainFrame.repaint();

                        showRound();
                    }
                }

            }
        });
    }

    /**
     * Δημιουργεί το newRoundPanel που εμφανίζεται στην εκκίνηση μιας γύρας. Αποτελείται από 4 JLabel που περιγράφουν στον
     * παίχτη πως να συνεχίσει, ποια γύρα παίζει και τους κανόνες της γύρας που θα παίξει
     */
    private void createNewRoundPanel() {
        //new round panel
        roundPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        roundNumber = new JLabel("", SwingConstants.CENTER);
        roundTypeLabel = new JLabel("", SwingConstants.CENTER);
        roundDescription = new JLabel("", SwingConstants.CENTER);

        c.gridx = 0; c.gridy = 0; roundPanel.add(roundNumber, c);
        c.gridx = 0; c.gridy = 1; roundPanel.add(roundTypeLabel, c);
        c.gridx = 0; c.gridy = 2; roundPanel.add(roundDescription, c);
        c.gridx = 0; c.gridy = 3; roundPanel.add(new JLabel("Πατήστε Space για να συνεχίσετε", SwingConstants.CENTER), c);
    }

    /**
     * Δημιουργεί το questionPanel που εμφανίζεται όταν πρέπει να απαντηθεί μία ερώτηση. Αποτεέιται από διάφορα JPanels
     * που κατατάσουν όλα τα υπόλοιπα στοιχέια. Αυτά τα στοιχεία έιναι τα εξής (όλα είναι JLabels):
     *
     * score1 και score2 που αναγράφουν το σκορ του παίχτη 1 και παίχτη 2 αντίστοιχα.
     * Ομόιως με τα name1 και name2 αλλά για τα ονόματά τους.
     * Το info έχει τις οδήγιες για το πως να συνεχίσουν οι παίχτες στην επόμενη ερώτηση (εφόσον την απαντήσουν).
     * Το picture έχει την εικόνα (εάν υπάρχει) της τρέχων ερώτησης.
     * Το category έχει την κατηγορία της τρέχων ερώτησης και το Prompt το ερώτημα προς απάντηση της τρέχων ερώτησης
     * Τα answer έχουν όλες τις πιθανές απαντήσεις της τρέχων ερώτησης
     * Τα playerKey έχουν τις οδηγίες για το πιο κουμπί αντιστοιχεί σε ποιον παίχτη και ποια απάντηση επιλέγει
     */
    private void createQuestionPanel() {
        //create other elements
        questionPanel = new JPanel(new GridLayout(2, 1));
        JPanel infoPanel = new JPanel(new GridLayout(1, 3));
        JPanel promptPanel = new JPanel(new BorderLayout());
        JPanel actualPromptPanel = new JPanel(new GridLayout(2, 1));
        JPanel answersPanel = new JPanel(new GridLayout(2, 2));
        JPanel scorePanel1 = new JPanel(new GridLayout(2, 1));
        JPanel scorePanel2 = new JPanel(new GridLayout(2, 1));

        //Create question panel
        name1 = new JLabel("", SwingConstants.LEFT);
        score1 = new JLabel("", SwingConstants.LEFT);
        scorePanel1.add(name1);
        scorePanel1.add(score1);
        infoPanel.add(scorePanel1);
        info = new JLabel("", SwingConstants.CENTER);
        infoPanel.add(info);
        name2 = new JLabel("", SwingConstants.RIGHT);
        score2 = new JLabel("", SwingConstants.RIGHT);
        scorePanel2.add(name2);
        scorePanel2.add(score2);
        infoPanel.add(scorePanel2);

        promptPanel.add(infoPanel, BorderLayout.PAGE_START);
        picture = new JLabel("", SwingConstants.CENTER);
        promptPanel.add(picture, BorderLayout.CENTER);
        category = new JLabel("", SwingConstants.CENTER);
        prompt = new JLabel("", SwingConstants.CENTER);
        actualPromptPanel.add(category);
        actualPromptPanel.add(prompt);
        promptPanel.add(actualPromptPanel, BorderLayout.PAGE_END);

        playerKey = new JLabel [2][4];

        for(int i=0;i<4;i++) {
            playerKey[0][i] = new JLabel("", SwingConstants.LEFT);
            playerKey[1][i] = new JLabel("", SwingConstants.RIGHT);
        }

        answer = new JLabel[4];
        answerPanel = new JPanel[4];

        for(int i=0;i<4;i++) {
            answerPanel[i] = new JPanel(new BorderLayout());
            answer[i] = new JLabel("", SwingConstants.CENTER);
            answerPanel[i].add(answer[i], BorderLayout.CENTER);
            answerPanel[i].add(playerKey[0][i], BorderLayout.PAGE_START);
            answerPanel[i].add(playerKey[1][i], BorderLayout.PAGE_END);
            answersPanel.add(answerPanel[i]);

            answerPanel[i].setBorder(BorderFactory.createLineBorder(Color.darkGray));
        }

        questionPanel.add(promptPanel);
        questionPanel.add(answersPanel);
    }

    /**
     * Δημιουργεί το highScorePanel που περιέχει Στατιστικά για τους όλους τους παίχτες του παιχνιδιού. Αποτελείται απο
     * Έχει 6 JLabels που παρουσιάζουν τον καλύτερο παίχτη στις δύο κατηγορίες μαζί με το σκορ του. Έχει ένα JTextField
     * όπου ο χρήστης γράφει το όνομα του παίχτη που θέλει να εξετάσει. Έπειτα αν πατήσει το JButton "Αναζήτηση" θα
     * εμφανίσει τα στατιστικά του παίχτη που επέλεξε ο χρήστς (εάν υπάρχει). Σε ένα JLabel υπάρχουν οδηγίες για την
     * χρήση του JTextField και του JButton που περιγράφηκαν παραπάνω. Τέλος υπάρχει ένα κουμπί "Πίσω" που επιστρέφει
     * τον παίχτη στο startupPanel.
     */
    private void createHighScorePanel() {

        ArrayList<String> stats = new ArrayList<String>();
        stats = bestPlayers();
        String maxScoreName = stats.get(0), maxWinsName = stats.get(2), maxScore = stats.get(1), maxWins = stats.get(3);

        String fileName = "highScores.txt";
        File file = new File(fileName);
        boolean fileIsEmpty = (file.length() == 0) ? true : false; // BEAUTIFULL

        highScorePanel = new JPanel(new BorderLayout());
        JPanel contentPanel = new JPanel(new GridLayout(2, 1));
        highScorePanel.add(contentPanel, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new GridLayout(1, 2));

        GridBagConstraints c = new GridBagConstraints();

        JPanel maxScorePanel = new JPanel(new GridBagLayout());
        JLabel maxScoreNameLabel = new JLabel("", SwingConstants.CENTER);
        JLabel maxScoreLabel = new JLabel("", SwingConstants.CENTER);
        c.gridx=0; c.gridy=0; maxScorePanel.add(new JLabel("ΤΟΠ ΣΚΟΡ ΣΤΟ ΑΤΟΜΙΚΟ ΠΑΙΧΝΙΔΙ", SwingConstants.CENTER), c);
        c.gridx=0; c.gridy=1; maxScorePanel.add(maxScoreNameLabel, c);
        c.gridx=0; c.gridy=2; maxScorePanel.add(maxScoreLabel, c);

        JPanel maxWinsPanel = new JPanel(new GridBagLayout());
        JLabel maxWinsNameLabel = new JLabel("", SwingConstants.CENTER);
        JLabel maxWinsLabel = new JLabel("", SwingConstants.CENTER);
        c.gridx=0; c.gridy=0;maxWinsPanel.add(new JLabel("ΤΟΠ ΝΙΚΕΣ ΣΤΟ Vs.", SwingConstants.CENTER), c);
        c.gridx=0; c.gridy=1;maxWinsPanel.add(maxWinsNameLabel, c);
        c.gridx=0; c.gridy=2;maxWinsPanel.add(maxWinsLabel, c);


        if(!fileIsEmpty) {
            maxScoreNameLabel.setText(maxScoreName);
            maxScoreLabel.setText(String.valueOf(maxScore));

            maxWinsNameLabel.setText(maxWinsName);
            maxWinsLabel.setText(String.valueOf(maxWins));
        } else {
            maxScoreNameLabel.setText("Τίποτα :(");
            maxWinsNameLabel.setText("Τίποτα :(");
        }

        topPanel.add(maxScorePanel);
        topPanel.add(maxWinsPanel);

        JPanel bottomPanel = new JPanel(new GridBagLayout());
        JTextField searchBar = new JTextField(20);
        JButton searchButton = new JButton("Αναζήτηση");
        JLabel searchResult = new JLabel();

        c.gridx = 0; c.gridy = 0; bottomPanel.add(new JLabel("Αναζήτησε στατιστικά ενός παίχτη:"), c);
        c.gridx = 0; c.gridy = 1; bottomPanel.add(searchBar, c);
        c.gridx = 1; c.gridy = 1; bottomPanel.add(searchButton, c);
        c.gridx = 0; c.gridy = 2; bottomPanel.add(searchResult, c);

        contentPanel.add(topPanel);
        contentPanel.add(bottomPanel);

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String name = searchBar.getText();
                int score = 0;
                int wins = 0;
                boolean nameFound = false;

                if(!fileIsEmpty) {
                    try {
                        BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
                        String line = "";
                        while ((line = br.readLine()) != null) {
                            String[] parts = line.split(",");
                            if(parts[0].equals(name)){
                                nameFound = true;
                                score = Integer.parseInt(parts[1]);
                                wins = Integer.parseInt(parts[2]);
                            }
                        }

                    } catch (IOException er) {
                        er.printStackTrace();
                    }
                }

                if(nameFound) {
                    searchResult.setText("Ο " + name + " έχει high score " + score + " πόντους και " + wins + " νίκες!");
                } else {
                    searchResult.setText("Δεν βρέθηκε ο Παίχτης με το όνομα \"" + name + "\"");
                }
            }
        });

        JButton backButton = new JButton("Πίσω");
        JPanel buttonConstraint = new JPanel(new FlowLayout());

        highScorePanel.add(buttonConstraint, BorderLayout.PAGE_START);
        buttonConstraint.add(backButton);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrame.remove(highScorePanel);
                mainFrame.add(startupPanel);

                mainFrame.validate();
                mainFrame.repaint();
            }
        });

    }

    /**
     * Προσθέτει το startupPanel στο JFrame του GUI, και εμφανίζει το JFrame
     */
    public void startGame(){
        mainFrame.add(startupPanel);

        mainFrame.setVisible(true);
    }

    /**
     * Εμφανίζει το roundPanel με τις κατάλληλες πληροφορίες. Προσθέτει έναν KeyListener στο mainFrame για το κουμπί space.
     * Ο KeyListener καλεί την μέθοδο questionLogicStart, αντικαταστεί το
     * roundPanel με το questionPanel και αφαιρεί τον εαυτό του από το mainFrame.
     */
    private void showRound() {

        roundNumber.setText("Round " + (currentRound+1));
        roundTypeLabel.setText(rounds.get(currentRound).getRoundTitle());
        roundDescription.setText(rounds.get(currentRound).getRoundDescription());

        mainFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    mainFrame.remove(roundPanel);
                    mainFrame.add(questionPanel);

                    mainFrame.validate();
                    mainFrame.repaint();

                    mainFrame.removeKeyListener(this);

                    questionLogicStart();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    /**
     * Αναλόγως την κατηγορία του τρέχων γύρου εκτελέι τις κατάλληλες ενέργεις έτσι ώστε το createRewards()
     * να δημιουργήσει σωστά τις ανταμοιβές του κάθε παίκτη. Έπειτα καλείται η showQuestion. Ειδική περίπτωση
     * υπάρχει στην κατηγορία γύρου "Ποντάρισμα", όπου πρώτα οι παίχτες επιλέγουν πόσους πόντους θα ποντάρουν.
     * Αυτό γίνεται με τροποποίηση του questionPanel και με έναν keyListener ο οποίος αφαιρεί τον εαυτό του όταν ποντάρουν
     * όλοι οι παίχτες.
     */
    private void questionLogicStart() {

        int roundType = rounds.get(currentRound).getRoundType();

        switch(roundType) {
            case 0:
                showQuestion();
                break;
            case 1:

                score1.setText(String.valueOf(players.get(0).getScore()));
                if(getNumOfPlayers()>=2){score2.setText(String.valueOf(players.get(1).getScore()));}


                ImageIcon imageIcon = new ImageIcon("imgs/markes.jpg");
                Image image = imageIcon.getImage(); // transform it
                Image newimg = image.getScaledInstance(500, 350,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                imageIcon = new ImageIcon(newimg);  // transform it back
                picture.setIcon(imageIcon);

                category.setText(rounds.get(currentRound).getQuestions().get(currentQuestion).getCategory());
                prompt.setText("Επέλεξε τους πόντους που θα ποντάρεις");

                for(int i=0;i<4;i++) {
                    answer[i].setText(String.valueOf(rounds.get(currentRound).getBettingOption(i)));
                }

                mainFrame.repaint();

                mainFrame.addKeyListener(new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {}

                    @Override
                    public void keyPressed(KeyEvent e) {
                        boolean correctKeyPressed = evaluateKey(e.getKeyCode());

                        if(correctKeyPressed) {

                            int numOfPlayersThatAnswered = 0;

                            for(int i=0; i<getNumOfPlayers();i++) {
                                if(players.get(i).hasAnswered()) {
                                    playerRewards.set(i, rounds.get(i).getBettingOption(players.get(i).getAnswerPosition()));
                                    numOfPlayersThatAnswered++;
                                }
                            }

                            if(numOfPlayersThatAnswered == getNumOfPlayers()) {
                                mainFrame.removeKeyListener(this);
                                for(int i=0;i<getNumOfPlayers();i++) {players.get(i).setAnswerPosition(-1);}
                                showQuestion();
                            }
                        }
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {}
                });

                break;
            case 2:
                showQuestion();
                break;
            case 3:
                showQuestion();
                break;
            case 4:
                if(currentQuestion == 0) {
                    for(int i=0;i<getNumOfPlayers();i++) {
                        playerCorrectAnswerAmount.set(i, 0);
                    }
                }

                showQuestion();
                break;
        }
    }

    /**
     * Εμφανίζει το questionPanel με τις κατάλληλες πληροφορίες και προσθέτει τον KeyListener που χρησιμοποιείται
     * στην απάντηση ερωτήσεων. Ο KeyListener αφαιρεί τον εαυτό του και καλεί την questionLogicEnd
     * όταν απαντήσουν όλοι οι παίχτες. Ο έλεγχος κουμπιού και η αντιστοίχηση απάντησης με παίχτη γίνεται στο
     * evaluateKey.
     */
    private void showQuestion() {

        for(int i=0;i<getNumOfPlayers();i++) {players.get(i).setAnswerPosition(-1);}
        Question question = rounds.get(currentRound).getQuestions().get(currentQuestion);

        score1.setText(String.valueOf(players.get(0).getScore()));
        if(getNumOfPlayers()>=2) {score2.setText(String.valueOf(players.get(1).getScore()));}

        if( question.getImagePath().equals("-")) {
            picture.setIcon(null);
        }
        else{
            ImageIcon imageIcon = new ImageIcon(question.getImagePath());
            Image image = imageIcon.getImage(); // transform it
            Image newimg = image.getScaledInstance(500, 350,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            imageIcon = new ImageIcon(newimg);  // transform it back
            picture.setIcon(imageIcon);
        }

        category.setText(question.getCategory());
        prompt.setText(question.getPrompt());

        for(int i=0;i<4;i++) {
            answer[i].setText(question.getAnswerList().get(i));
        }

        mainFrame.repaint();

        questionStartingTime = System.currentTimeMillis();
        mainFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {

                boolean correctKeyPressed = evaluateKey(e.getKeyCode());

                if(correctKeyPressed) {

                    int numOfPlayersThatAnswered = 0;

                    for(int i=0; i<getNumOfPlayers();i++) {
                        if(players.get(i).hasAnswered())
                            numOfPlayersThatAnswered++;
                    }

                    if(numOfPlayersThatAnswered == getNumOfPlayers()) {
                        mainFrame.removeKeyListener(this);
                        questionLogicEnd();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    /**
     * Σηματοδοτεί τις λάθος ερωτήσεις (εαν επιλέχθηκαν) και την σωστή απάντηση. Έπειτα καλεί την rewardCreator()
     * της κλάσης Game. Έπειτα προσθέτει έναν KeyListener στο mainFrame που ακούει το κουμπί space
     *
     * Ο KeyListener αφαιρεί τα χρώματα πίσω απο τις λάθος και σωστές απαντήσεις και καλεί την questionLogicStart
     * εαν υπάρχουν και άλλες ερωτήσεις στην τρέχων γύρα. Όταν τελειώσει το παιχνίδι καταγράφονται τα στατιστικά μέσω της
     * toHighScoreFile και καλείται η μέθοδος showRound.
     */
    private void questionLogicEnd() {
        JPanel correctAnswerPanel = answerPanel[rounds.get(currentRound).getQuestions().get(currentQuestion).getCorrectAnswerPosition()];
        for(int i=0;i<getNumOfPlayers();i++) {
            answerPanel[players.get(i).getAnswerPosition()].setBackground(Color.RED);
        }
        correctAnswerPanel.setBackground(Color.GREEN);

        rewardCreator();

        score1.setText(String.valueOf(players.get(0).getScore()));
        if(getNumOfPlayers()>=2){score2.setText(String.valueOf(players.get(1).getScore()));}
        info.setText("Πατήστε Space για να συνεχίσετε");

        mainFrame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                    mainFrame.removeKeyListener(this);
                    for(JPanel panel : answerPanel) {
                        panel.setBackground(UIManager.getColor ("Panel.background"));
                    }

                    info.setText("");

                    if(!gameHasEnded) {
                        if(currentQuestion == 0) {
                            mainFrame.remove(questionPanel);
                            mainFrame.add(roundPanel);

                            mainFrame.validate();
                            mainFrame.repaint();

                            showRound();
                        } else {
                            questionLogicStart();
                        }
                    } else {
                        mainFrame.remove(questionPanel);
                        showEndPanel();
                        endGame();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {}
        });
    }

    /**
     * Δημιουργεί και εμφανίζει το endPanel με τις κατάλληλες πληροφορίες. Το endPanel αναγράφει το σκορ
     * του κάθε παίκτη και σηματοδοτεί το τέλος του παιχνιδιού. Το endPanel αλλάζει αναλόγως τον αριθμό των παιχτών.
     */
    private void showEndPanel() {

        JPanel alignment = new JPanel(new GridBagLayout());
        mainFrame.add(alignment);

        if(getNumOfPlayers()==1) {

            JPanel endPanel = new JPanel(new GridLayout(1, 1));
            endPanel.add(new JLabel(players.get(0).getName() + ", τελείωσες με " + players.get(0).getScore() + " πόντους!"));
            alignment.add(endPanel);
        } else if(getNumOfPlayers()==2) {
            JPanel endPanel = new JPanel(new GridLayout(2, 2, 5, 2));
            endPanel.add(new JLabel(players.get(0).getName(), SwingConstants.CENTER));
            endPanel.add(new JLabel(players.get(1).getName(), SwingConstants.CENTER));
            endPanel.add(new JLabel(String.valueOf(players.get(0).getScore()), SwingConstants.CENTER));
            endPanel.add(new JLabel(String.valueOf(players.get(1).getScore()), SwingConstants.CENTER));
            alignment.add(endPanel);
        }

        mainFrame.validate();
        mainFrame.repaint();
    }

    /**
     * Επεξεργάζεται το κουμπί που πατήθηκε στην απάντηση κάποιας ερώτησης. Βάση αυτής της επεξεργασίες αλλάζει το
     * answerPosition του κάθε παίχτη.
     *
     * @param c Το κουμπί που πατήθηκε
     * @return Εαν πατήθηκε το σωστό κουμπί
     */
    private boolean evaluateKey(int c) {
        boolean correctKeyPressed = false;

        if(!players.get(0).hasAnswered()) {
            if (c == KeyEvent.VK_Q) {
                players.get(0).setAnswerPosition(0);
                correctKeyPressed = true;
            } else if (c == KeyEvent.VK_W) {
                players.get(0).setAnswerPosition(1);
                correctKeyPressed = true;
            } else if (c == KeyEvent.VK_A) {
                players.get(0).setAnswerPosition(2);
                correctKeyPressed = true;
            } else if (c == KeyEvent.VK_S) {
                players.get(0).setAnswerPosition(3);
                correctKeyPressed = true;
            }

            if(correctKeyPressed) {
                playerAnswerOrder.add(0);
                playerAnswerTime.set(0,System.currentTimeMillis());
            }
        }

        if(getNumOfPlayers()>=2 && !correctKeyPressed) {
            if(!players.get(1).hasAnswered()) {
                if(c == KeyEvent.VK_I) {
                    players.get(1).setAnswerPosition(0);
                    correctKeyPressed = true;
                } else if (c == KeyEvent.VK_O) {
                    players.get(1).setAnswerPosition(1);
                    correctKeyPressed = true;
                } else if (c == KeyEvent.VK_K) {
                    players.get(1).setAnswerPosition(2);
                    correctKeyPressed = true;
                } else if (c == KeyEvent.VK_L) {
                    players.get(1).setAnswerPosition(3);
                    correctKeyPressed = true;
                }

                if(correctKeyPressed) {
                    playerAnswerOrder.add(1);
                    playerAnswerTime.set(1,System.currentTimeMillis());
                }
            }
        }
        return correctKeyPressed;
    }
}