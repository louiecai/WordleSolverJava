import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class WordleWord implements Comparable<WordleWord> {

    private final static float PENALTY = 0.25f;
    private static final HashMap<Character, Integer> wordFrequencies;

    static {
        wordFrequencies = new HashMap<>();
        try {
            fillFrequencyMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String word;
    private int score;

    public WordleWord(String word) {
        this.word = word;
        this.score = this.score();
    }

    public String word() {
        return this.word;
    }

    @Override
    public int compareTo(WordleWord that) {
        return this.score - that.score;
    }

    private int score() {
        int score = 0;
        char[] word = this.word.toCharArray();
        LinkedList<Character> list = new LinkedList<>();
        for (char character : word) {
            if (list.contains(character)) {
                score += wordFrequencies.get(character) * PENALTY;
                continue;
            }
            score += wordFrequencies.get(character);
            list.add(character);
        }

        return score;
    }

    private static void fillFrequencyMap() throws IOException {
        List<String> wordBank = Files.readAllLines(Paths.get("char_frequency.csv"));
        for (String word : wordBank) {
            char character = word.split(",")[0].charAt(0);
            int frequency = Integer.parseInt(word.split(",")[1]);
            wordFrequencies.put(character, frequency);
        }
    }

    @Override
    public String toString() {
        return this.word;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordleWord that = (WordleWord) o;
        return Objects.equals(word, that.word);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word);
    }
}
