import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;;

public class Testing {
    
    public static String[] validGuesses = new String[12972];
    public static String[] validAnswers = new String[2315];
    public static ArrayList<Character> blackLetters = new ArrayList<Character>();
    public static ArrayList<String> refinedAnswers = new ArrayList<String>();
    public static ArrayList<String> backupRefinedAnswers = refinedAnswers;
    public static ArrayList<String> guesses = new ArrayList<String>();
    public static int count = 0;
    public static ArrayList<wordFrequency> listOfWords = new ArrayList<wordFrequency>();
    public static ArrayList<Double> findAverage = new ArrayList<Double>();
    public static int sum = 0;
    public static int wordTracker = 0;
    public static void main(String[] args){
        setVariables();
        guesses.add(validAnswers[wordTracker]); //"salet"
        setWordFrequencyList();
        test();
        int totalCount = 0;
        for (int i = 0; i < findAverage.size(); i++){
            totalCount += findAverage.get(i);
        }
        sum = totalCount/findAverage.size();
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
                    listOfWords.add(new wordFrequency(list[0], Integer.parseInt(list[1])));
                }
            }
        }
        sc.close();  //closes the scanner
    }
    
    public static void test(){
        for (int i = 0; i < validAnswers.length; i++){
            String nextGuess = testingWord(validAnswers[i], validAnswers[wordTracker]); // "salet", second parameter
            while (!nextGuess.equals(validAnswers[i])){
                guesses.add(nextGuess);
                nextGuess = testingWord(validAnswers[i], nextGuess);
            }
            guesses.add(validAnswers[i]);
            count++;
            //System.out.println(validAnswers[i] + ": " + count + " guesses.");
            save(validAnswers[i], guesses, count);
            findAverage.add(Double.valueOf(count));
            count = 0;
            setVariables();
            blackLetters.clear();
            guesses.clear();
            guesses.add(validAnswers[wordTracker]); // "salet"
        }
    }

    public static void save(String originalWord, ArrayList<String> guesses, int num){
    
        if (num == 1){
            guesses.add(originalWord);
            num++;
        }

        try{
            FileWriter fw = new FileWriter("/Users/arjun/Documents/GitHub/WordleSolver/wordleBotSolverResultsIdeas.csv", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);

            String text = guesses.stream().map(Object::toString).collect(Collectors.joining(", "));

            //pw.println(originalWord + "," + "\"" + text + "\"" + "," + num);
            pw.println(wordTracker + "," + originalWord + findAverage)
            pw.flush();        
        }
        catch (Exception E){

            System.out.println("error");
        }
        //*/
    }

    public static String testingWord(String wordle, String input){
        count++;
        char[] answerChar = wordle.toCharArray();
        Character[] answer = new Character[answerChar.length]; 
        int z = 0; 
        for (char value : answerChar) { 
            answer[z++] = Character.valueOf(value); 
        } 
        char[] inputChar = input.toCharArray();
        Character[] word = new Character[inputChar.length]; 
        int y = 0; 
        for (char value : inputChar) { 
            word[y++] = Character.valueOf(value); 
        } 
        Character[] result = {'b', 'b', 'b', 'b', 'b'};
        ArrayList<String> removeList = new ArrayList<String>();

        for (int i = 0; i < word.length; i++){
            if (word[i] == answer[i]){ //if that letter is green: right letter right spot
                result[i] = 'g';
            }
            else if (wordle.contains(word[i] + "")){ //if that letter is yellow: in the answer but not green
                result[i] = 'y';
            }
            else{
                result[i] = 'b';
            }
        }        
        
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
        

        Random rand = new Random();
        String finalWord = refinedAnswers.get(rand.nextInt(refinedAnswers.size()));
        ArrayList<String> words = new ArrayList<String>();
        ArrayList<Integer> wordFreq = new ArrayList<Integer>();
        int score = 0;
        wordFrequency finalElement = new wordFrequency(refinedAnswers.get(0), 0);
        for (int i = 0; i < listOfWords.size(); i++){
            words.add(listOfWords.get(i).getWord());
            wordFreq.add(listOfWords.get(i).getNum());
        }
        for (int i = 0; i < refinedAnswers.size(); i++){
            if (!words.contains(refinedAnswers.get(i))){
                score = 0;
            }
            else{
                wordFrequency element = new wordFrequency(refinedAnswers.get(0), 0);
                for (int j = 0; j < listOfWords.size(); j++){
                    if (listOfWords.get(j).getWord().equals(refinedAnswers.get(i))){
                        element = listOfWords.get(j);
                    }
                }
                if (element.getNum() > score){
                    score = element.getNum();
                    finalElement = element;
                }
            }
        }

        finalWord = finalElement.getWord();
        return finalWord;
    }
    
}
