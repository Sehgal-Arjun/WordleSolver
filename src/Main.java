import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Main {

    //public static String[] validGuesses = new String[10657];
    public static String[] validGuesses = new String[12972];
    public static String[] validAnswers = new String[2315];
    public static ArrayList<Character> blackLetters = new ArrayList<Character>();
    public static ArrayList<String> refinedAnswers = new ArrayList<String>();
    public static int count = 0;
    public static ArrayList<String> words = new ArrayList<String>();
    public static ArrayList<Integer> wordFreq = new ArrayList<Integer>();

    public static void main(String[] args) {
	// write your code here
        setVariables();
        setWordFrequencyList();
        boolean finished = false;
        while (!finished){
            finished = findWord();
        }
    }

    public static void setWordFrequencyList(){
        Scanner sc = null;
        try {
            sc = new Scanner(new File("/Users/arjun/Documents/GitHub/WordleSolver/unigram_freq.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        while (sc.hasNext())  //returns a boolean value
        {
            String word = sc.next();
            if (!(word.equals("word,count"))){

                String[] list = word.split(",");
                if (list[0].length() == 5){
                    words.add(list[0]);
                    wordFreq.add(Integer.parseInt(list[1]));
                }
            }
        }
        sc.close();  //closes the scanner
    }

    public static void setVariables(){
        int tracker = 0;
        int tracker2 = 0;
        
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

        for (int i = 0; i < validAnswers.length; i++){
            validGuesses[10657 + i] = validAnswers[i];
        }

        for (int i = 0; i < validGuesses.length; i++){
            refinedAnswers.add(validGuesses[i]);
        }
    }

    public static boolean findWord(){
        Character[] result = {'b', 'b', 'b', 'b', 'b'};
        Character[] test = {'g', 'g', 'g', 'g', 'g'};
        ArrayList<String> removeList = new ArrayList<String>();
        Character[] word = {'s', 'a', 'l', 'e', 't'};
        Scanner scn = new Scanner(System.in);  // Create a Scanner object for getting the word the user inputted
        System.out.println("Your inputted word: ");
        String response = scn.nextLine(); 
        if (response.equals("done")){
            return true;
        }
        else if (response.equals("list")){
            for (int i = 0; i < refinedAnswers.size(); i ++){
                System.out.print(refinedAnswers.get(i));
                if (i != refinedAnswers.size() - 1){
                    System.out.print(", ");
                }
            }
            System.out.println("");
        }
        else if (!response.equals("reroll")){
            for (int i = 0; i < word.length; i++){
                word[i] = response.substring(i, i+1).charAt(0);
            }
    
            for (int i = 0; i < word.length; i++){
                System.out.println("Wordle output for: " + word[i] + "?");
                String out = scn.nextLine();  // Read user input
                result[i] = out.charAt(0); // Assign the output to a results array
            }
            //scn.close();
            for (int i = 0; i< result.length; i++){
                if (result[i] == 'b'){
                    for (int j = 0; j < refinedAnswers.size(); j++){
                        if (refinedAnswers.get(j).indexOf(word[i]) == -1){
                            if (!blackLetters.contains(word[i])){
                                blackLetters.add(word[i]);
                            }
                        }
                    }
                }
                if (result[i] == 'y'){
                    for (int j = 0; j < refinedAnswers.size(); j++){
                        String currentWord = refinedAnswers.get(j);
                        Character yellowLetter = word[i];
                        if (currentWord.indexOf(yellowLetter) != -1){
                            if (currentWord.charAt(i) == yellowLetter){
                                removeList.add(currentWord);
                            }
                        }
                        else{
                            removeList.add(refinedAnswers.get(j));
                        }
                    }
                }
                if (result[i] == 'g'){
                    for (int j = 0; j < refinedAnswers.size(); j++){
                        if (refinedAnswers.get(j).charAt(i) != word[i]){
                            removeList.add(refinedAnswers.get(j));
                        }
                    }
                }
            }
            for (int i = 0; i < refinedAnswers.size(); i++){
                boolean contains = false;
                for (int j = 0; j < blackLetters.size(); j++){ //breakpoint
                    if (refinedAnswers.get(i).contains(blackLetters.get(j) + "")){
                        contains = true;
                    }
                } //breakpoint
                
                if (contains){
                    removeList.add(refinedAnswers.get(i));
                }
            }
            for (int i = 0; i < removeList.size(); i++){
                refinedAnswers.remove(removeList.get(i));
            }
            count++;
        }
        

        if (result == test){
            System.out.println("Nice! Got it in " + count + " tries!");
            return true;
        }

        Random rand = new Random();
        String finalWord = refinedAnswers.get(rand.nextInt(refinedAnswers.size()));
        for (int i = 1; i < refinedAnswers.size(); i++){
            if (words.contains(refinedAnswers.get(i))){
                int index = words.indexOf(refinedAnswers.get(i));
                if (words.indexOf(refinedAnswers.get(i-1)) != -1){
                    int prevIndex = words.indexOf(refinedAnswers.get(i-1));
                    if (wordFreq.get(index) > wordFreq.get(prevIndex)){
                        finalWord = words.get(index);
                    }
                }
            }
        }
        System.out.println("Recommendation: " + finalWord);

        return false;
    }
}
