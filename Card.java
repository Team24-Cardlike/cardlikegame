public class Card {
    public final String suit;
    public final int rank;
    private final String pic;
    Card(String suit, int rank, String pic){
        this.suit = suit;
        this.rank = rank;
        this.pic = pic; //named as suit+rank in files later
    }
}


