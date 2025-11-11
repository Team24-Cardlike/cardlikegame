public class Card {
    public final String suit;
    public final int rank;
    public final String pic;
    Card(String suit, int rank){
        this.suit = suit;
        this.rank = rank;
        this.pic = suit+rank+".png"; //named as suit+rank in files later
    }
}


