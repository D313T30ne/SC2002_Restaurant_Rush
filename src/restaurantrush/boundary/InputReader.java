package restaurantrush.boundary;

import java.util.Scanner;

public class InputReader {
    private Scanner scanner;

    public InputReader() {
        this.scanner = new Scanner(System.in);
    }

    public int readInt(String prompt, int min, int max) {
        // Re-prompts until the user gives a valid number
        while (true) {
            System.out.println(prompt);
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice >= min  && choice <= max) {
                    return choice;
                } else {
                    System.out.printf("Enter a number between %d and %d.\n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }   
        }
    }
}
