public class wordFrequency {

    String word;
    int num;
    
    public wordFrequency(String w, int n){
        word = w;
        num = n;
    }

    public String getWord(){
        return word;
    }

    public int getNum(){
        return num;
    }

    public void setWord(String w){
        word = w;
    }

    public void setNum(int n){
        num = n;
    }
}
