import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class RunSolver {
    public static void main(String[] args) {
        var solver = new WordleSolver(100, "word_bank.txt");
        Scanner scanner = new Scanner(System.in);
        System.out.printf("Recommended starter word: \"%s\"\n", solver.generatePredictions().get(1));
        System.out.println("Welcome!");
        while (true) {
            System.out.println("Please Enter Your Word and Colors: ");
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.equals("e"))
                break;

            CharStatus[] statuses = parseCharStatuses(input.split(":")[1]);

            List<WordleWord> results = solver.solve(input.split(":")[0], statuses);

            if (results.size() <= 1) {
                System.out.printf("The target word is \"%s\"!", results.get(0).word());
                break;
            }
            System.out.println(results.size() + " Possible Solutions: ");
            for (WordleWord result : results) {
                System.out.print(result + " ");
            }
            System.out.println("\n");

        }
    }

    public static CharStatus[] parseCharStatuses(String statuses) {
        CharStatus[] output = new CharStatus[statuses.length()];
        for (int i = 0; i < output.length; i++) {
            switch (statuses.charAt(i)) {
                case '=':
                    output[i] = CharStatus.GREEN;
                    break;
                case '-':
                    output[i] = CharStatus.YELLOW;
                    break;
                case '/':
                default:
                    output[i] = CharStatus.GREY;
                    break;
            }
        }
        return output;
    }
}
