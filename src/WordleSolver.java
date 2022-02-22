import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WordleSolver {
    private final static int WORD_LENGTH = 5;

    private ArrayList<Character>[] wronglyPlacedChars;
    private ArrayList<Character> yellowChars;
    private ArrayList<Character> singleChars;
    private char[] correctChars;
    private ArrayList<Character> wrongChars;
    private int suggestionAmount;

    private ArrayList<WordleWord> wordBank = new ArrayList<>();


    public WordleSolver(int suggestionAmount, String wordBankPath) {
        try {
            this.loadWordBanks(wordBankPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        this.suggestionAmount = suggestionAmount;
        this.wrongChars = new ArrayList<>();
        this.yellowChars = new ArrayList<>();
        this.singleChars = new ArrayList<>();
        this.correctChars = new char[WORD_LENGTH];
        this.wronglyPlacedChars = new ArrayList[WORD_LENGTH];

        for (int i = 0; i < wronglyPlacedChars.length; i++) {
            wronglyPlacedChars[i] = new ArrayList<>();
        }
    }

    public List<WordleWord> solve(String word, CharStatus[] statuses) {
        word = word.toLowerCase();
        this.updateStatus(word, statuses);
        return this.generatePredictions();
    }

    private void updateStatus(String word, CharStatus[] statuses) {
        for (int i = 0; i < word.length(); i++) {
            char current = word.charAt(i);
            switch (statuses[i]) {
                case GREEN:
                    correctChars[i] = current;
                    break;
                case YELLOW:

                    if (!yellowChars.contains(current))
                        yellowChars.add(current);

                    if (!wronglyPlacedChars[i].contains(current))
                        wronglyPlacedChars[i].add(current);

                    break;
                case GREY:
                    if (!wrongChars.contains(current))
                        wrongChars.add(current);
                    break;
            }
        }

        for (int i = 0; i < correctChars.length; i++) {
            char current = correctChars[i];
            if (wrongChars.contains(current)) {
                wrongChars.remove(Character.valueOf(current));
                if (!this.singleChars.contains(current))
                    singleChars.add(current);
            }
        }

        yellowChars.forEach(current -> {
            if (wrongChars.contains(current)) {
                wrongChars.remove(Character.valueOf(current));
                if (!this.singleChars.contains(current))
                    singleChars.add(current);
            }
        });
    }

    public List<WordleWord> generatePredictions() {
        ArrayList<WordleWord> output = new ArrayList<>();

        OUTSIDE_LOOP:
        for (int i = 0; i < wordBank.size(); i++) {

            String current = wordBank.get(i).word();
            for (int j = 0; j < this.yellowChars.size(); j++) {
                if (!current.contains(yellowChars.get(j).toString())) {

                    continue OUTSIDE_LOOP;
                }
            }

            for (int j = 0; j < current.length(); j++) {
                char currentChar = current.charAt(j);
                if (this.wrongChars.contains(currentChar)) {
                    continue OUTSIDE_LOOP;
                } else if (this.wronglyPlacedChars[j].contains(currentChar)) {
                    continue OUTSIDE_LOOP;
                } else if (this.correctChars[j] != 0 && this.correctChars[j] != currentChar) {
                    continue OUTSIDE_LOOP;
                } else if (this.singleChars.contains(currentChar) &&
                        Collections.frequency(List.of(current.toCharArray()), currentChar) > 1) {
                    continue OUTSIDE_LOOP;
                }
            }
            output.add(new WordleWord(current));
        }

        Collections.sort(output, Collections.reverseOrder());

        return output;
    }

    private void loadWordBanks(String wordBankPath) throws
            IOException {
        Files.readAllLines(Paths.get(wordBankPath)).forEach(word -> {
            this.wordBank.add(new WordleWord(word));
        });
    }
}
