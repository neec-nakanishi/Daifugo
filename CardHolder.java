import java.util.*;

public class CardHolder extends ArrayList<Card> {
    protected Game game;
    protected String name;
    private CardAnimation ca;

    public CardHolder(Game game, String name) {
        super();
        this.game = game;
        this.name = name;
        ca = new CardAnimation(game);
    }

    public Game getGame() {
        return game;
    }

    public String getName() {
        return name;
    }

    public void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
    public void shuffle() {
        for (int i=1 ; i<=1000 ; i++) {
            int r = (int)(Math.random()*(size()));
            Card c = remove(r);
            add(c);
            game.getContentPane().setComponentZOrder(c, game.getComponentCount()-1);
        }
    }

    public void moveTo(Card c, int dx, int dy, int interval) {
        ca.moveTo(c, dx, dy, interval);
    }

    public void reverse(Card c, int timer) {
        ca.reverse(c, timer);
    }

    public void sort() {
    	Collections.sort(this, new CardComparator());
    }

    class CardComparator implements Comparator {
        public int compare(Object s, Object t) {
            return ((Card)s).getNum() - ((Card)t).getNum();
        }
    }
}