import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int tracker = 0;
        int tracker2 = 0;
        String[] validGuesses = new String[10657];
        String[] validAnswers = new String[2315];
        Scanner sc = null;
        try {
            sc = new Scanner(new File("/Users/arjun/Documents/GitHub/WordleSolver/valid_guesses.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNext())  //returns a boolean value
        {
            String word = sc.next();
            if (!(word.equals("word"))){
                validGuesses[tracker] = word;
                tracker++;
            }
        }
        sc.close();  //closes the scanner

        Scanner sc2 = null;
        try {
            sc2 = new Scanner(new File("/Users/arjun/Documents/GitHub/WordleSolver/valid_solutions.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc2.hasNext())  //returns a boolean value
        {
            String word = sc2.next();
            if (!(word.equals("word"))){
                validAnswers[tracker2] = word;
                tracker2++;
            }
        }
        sc2.close();  //closes the scanner
    }
}
