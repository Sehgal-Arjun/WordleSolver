import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int tracker = 0;
        String[] validGuesses = new String[10656];
        Scanner sc = null;
        try {
            sc = new Scanner(new File("C:\\Users\\arjun\\Desktop\\ \\Code\\GitHub\\WordleSolver\\valid_guesses.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.useDelimiter(",");   //sets the delimiter pattern
        while (sc.hasNext())  //returns a boolean value
        {
            String word = sc.next();
            if (!(word.equals("word"))){
                validGuesses[tracker] = word;
                tracker++;
            }
        }
        sc.close();  //closes the scanner

    }
}
