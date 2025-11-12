public class CardCombos {
    Combo single;
    Combo pair;
    Combo two_pair;
    Combo three_of_a_kind;
    Combo straight;
    Combo flush;
    Combo full_house;
    Combo four_of_a_kind;
    Combo straight_flush;
    Combo royal_straight_flush;

    CardCombos(){
        this.single = new Combo("Single", 10, "One card alone.");
        this.pair = new Combo("Pair", 20, "Two cards of the same rank.");
        this.two_pair = new Combo("Two-pair", 40, "Two different pairs. (see pair)");
        this.three_of_a_kind = new Combo("Three-of-a-kind", 80, "Three cards of the same rank.");
        this.straight = new Combo("Straight", 100, "Five cards of sequential rank, can have any suit.");
        this.flush = new Combo("FLush", 125, "Five cards of the same suit, can have any rank.");
        this.full_house = new Combo("Full House", 175, "A three-of-a-kind and a pair in the same hand.");
        this.four_of_a_kind = new Combo("Four-of-a-kind", 400, "Four cards with the same rank.");
        this.straight_flush = new Combo("Straight-Flush", 600, "Five cards with the same suit that are also a straight.");
        this.royal_straight_flush = new Combo("Royal-Straight-Flush", 1500, "A straight-flush with the highest card being an ace.");
    }

    Combo checkCombo(){
        //TODO: Implement a function that checks for combos when playing cards.
        return null;
    }



}
