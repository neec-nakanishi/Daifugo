import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.event.*;

public class Game extends JFrame {

    protected Stock stock;
    protected Place place;
    protected ArrayList<Player> players = new ArrayList<>();
    protected ArrayList<Com> coms = new ArrayList<>();

    public Game() {
        super();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        stock = new Stock(this, "Stock");      // ストックを作成
        place = new Place(this, "Place");      // 場を作成
        cardInit();                            // カードの作成
    }

    public Stock getStock() {
        return stock;
    }

    public Place getPlace() {
        return place;
    }

    public void addPlayer(Player p) {
        players.add(p);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getPlayer(int index) {
        return players.get(index);
    }

    public void addCom(Com c) {
        coms.add(c);
    }

    public ArrayList<Com> getComs() {
        return coms;
    }

    public Com getCom(int index) {
        return coms.get(index);
    }

    public void cardInit() {
        // カードを１組作成し、ストックに追加
        String[] suit = {"s", "h", "d", "c"};
        for (String s : suit) {
            for (int n=1 ; n<=13 ; n++) {
                Card c = new Card(s, n);
                c.setSize(60, 90);
                c.addMouseListener(new actionListener());
                c.setVisible(true);
                stock.add(c);
                getContentPane().add(c);
                getContentPane().setComponentZOrder(c, getComponentCount()-1);
            }
        }        
    }

    public void playerReset() {
    }

    public void cardReset() {
    }

    public void gameStart() {
    }

    public void action(MouseEvent e) {
    }

    /* カードに対するアクション時（クリックなど）の処理 */
    public class actionListener extends MouseAdapter{
		@Override
		public void mouseReleased(MouseEvent e){
            Thread t = new Thread(new Action(e));
            t.start();
        }

        public class Action implements Runnable {
            private MouseEvent e;

            public Action(MouseEvent e) {
                this.e = e;
            }

            @Override
            public void run() {
                if ("Card" == e.getSource().getClass().getName()) {
                    Card c = (Card)(e.getSource());
                    getPlayer(0).action(c);
                    stock.action(c);
                    place.action(c);
                } else {
                    action(e);
                }
            }
        }
    }
}