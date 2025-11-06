public class Card {
    private final Suit suit;
    private final int value;
    Card(Suit suit, int value){
        this.suit = suit;
        this.value = value;
    }
    public enum Suit {
        HEARTS,
        DIAMONDS,
        CLUBS,
        SPADES
    }
    Card card = new Card(Suit.HEARTS, 7);
}


