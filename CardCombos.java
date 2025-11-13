import java.util.*;

public class CardCombos {
    final Combo single;
    final Combo pair;
    final Combo two_pair;
    final Combo three_of_a_kind;
    final Combo straight;
    final Combo flush;
    final Combo full_house;
    final Combo four_of_a_kind;
    final Combo straight_flush;
    final Combo royal_straight_flush;

    CardCombos(){
        this.single = new Combo("Single", 0, "One card alone.");
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


    /**
     *
     * @param cards an ArrayList filled with cards.
     * @return highest valued combo that is found in the cards played.
     */
    public Combo checkCombo(ArrayList<Card> cards) {
        int n = cards.size();
        if (n < 1 || n > 5) return null;
        ArrayList<String> suits = new ArrayList<>();
        ArrayList<Integer> ranks = new ArrayList<>();
        for (Card c : cards) {
            suits.add(c.suit);
            ranks.add(c.rank);
        }
        Collections.sort(ranks);

        boolean sameSuit = suits.stream().allMatch(s -> s.equals(suits.getFirst()));

        boolean sequential = false;
        if (ranks.size() >= 5) {
            sequential = true;
            for (int i = 1; i < ranks.size(); i++) {
                if (ranks.get(i) != ranks.get(i - 1) + 1) {
                    sequential = false;
                    break;
                }
            }
        }

        //Counts amount of each rank
        Map<Integer, Integer> counts = new HashMap<>();
        for (int r : ranks) {
            counts.put(r, counts.getOrDefault(r, 0) + 1);
        }

        List<Integer> freq = new ArrayList<>(counts.values());
        freq.sort(Collections.reverseOrder());


        // Determine combo
        switch (n) {
            case 1:
                return this.single;

            case 2:
                if (freq.getFirst() == 2) return this.pair;
                break;

            case 3:
                if (freq.getFirst() == 3) return this.three_of_a_kind;
                if (freq.getFirst() == 2) return this.pair;
                break;

            case 4:
                if (freq.getFirst() == 4) return this.four_of_a_kind;
                if (freq.getFirst() == 3) return this.three_of_a_kind;
                if (freq.getFirst() == 2 && freq.size() == 2) return this.two_pair;
                break;

            case 5:
                int maxRank = Collections.max(ranks);

                if (sameSuit && sequential && maxRank == 14)
                    return this.royal_straight_flush;

                if (sameSuit && sequential)
                    return this.straight_flush;

                if (freq.get(0) == 4)
                    return this.four_of_a_kind;

                if (freq.get(0) == 3 && freq.get(1) == 2)
                    return this.full_house;

                if (sameSuit)
                    return this.flush;

                if (sequential)
                    return this.straight;

                if (freq.get(0) == 3)
                    return this.three_of_a_kind;

                if (freq.get(0) == 2 && freq.get(1) == 2)
                    return this.two_pair;

                if (freq.get(0) == 2)
                    return this.pair;

                return this.single;
        }
        return new Combo("INVALID COMBO", 0, "Invalid card-input.");
    }



}
