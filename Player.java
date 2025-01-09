import java.util.ArrayList;
import javax.swing.JLabel;

class Player extends CardHolder {
    protected ArrayList<Card> select;

    private boolean actionF;

    protected JLabel lb;
    protected int sx;
    protected int sy;
    protected int dx;
    protected int dy;

    public Player(Game game, String name, JLabel lb, int sx, int sy, int dx, int dy) {
        super(game, name);
        select = new ArrayList<>();
        this.lb = lb;
        this.sx = sx;
        this.sy = sy;
        this.dx = dx;
        this.dy = dy;
    }

    public ArrayList<Card> getSelect() {
        return select;
    }

    public void setMessage(String msg) {
        lb.setText(msg);
        sleep(300);
    }

    public void setAction(boolean actionF) {
        this.actionF = actionF;
    }

    public void show() {
        sort();
        for (int i=0 ; i<size() ; i++) {
            Card c = get(i);
            c.setLocation(sx+i*dx, sy+i*dy);
            game.getContentPane().setComponentZOrder(c, game.getComponentCount()-1);
        }
    }

    /* 自動のアクション */
    public String autoAction() {
        // ボタンを有効にする
        ((Daifugo)game).getButton().setEnabled(true);
        // ボタンを押すまで待つ
        while(true) {
            if (actionF) {
                break;
            }
            sleep(1);
        }
 
        // 選択しているカードを出す
        if (game.getPlace().getNum()==0 || game.getPlace().getNum()==select.size()) {
            game.getPlace().setNum(select.size());
            for (Card c : select) {
                remove(c);
                game.getPlace().add(c);
                int x = (int)(Math.random()*15)-8;
                int y = (int)(Math.random()*15)-8;
                game.getContentPane().setComponentZOrder(c, game.getComponentCount()-1);
                moveTo(c, game.getWidth()/2+x*3-30, game.getHeight()/2+y*3-45, 1);
            }
            game.getPlace().setNum(select.size());
            select.clear();
            // 手札の再表示
            show();
            actionF = false;
            String msg = "えいっ！";
            setMessage(msg);
            return msg;
        } else {
            for (Card c : select) {
                moveTo(c, c.getX(), c.getY()+20, 0);
            }
            select.clear();
            actionF = false;
            String msg = "パス";
            setMessage(msg);
            return msg;
        }
    }

    /* カードに対するアクション時（クリックなど）の処理 */
    public boolean action(Card c) {
        // スタート時は何もしない
        if (game.getStock().size()==52) {
            return false;
        }

        // 手持ちのカードが無い場合は何もしない
        if (size()==0) {
            return false;
        }

        // Player以外は何もしない
        if (! name.equals("Player")) {
            return false;
        }

        if (contains(c)) {
            // 選択状態の場合、選択解除する
            if (select.contains(c)) {
                moveTo(c, c.getX(), c.getY()+20, 0);
                select.remove(c);
            // 選択状態で他のカードと数字が同じ場合、選択状態にする
            } else if (select.size() == 0 || 
                      (select.size() > 0 && select.get(0).getNum() == c.getNum())) {
                if (game.getPlace().size()!=0) {
                    if (select.size() >= game.getPlace().getNum()) {
                        return false;
                    }
                    Card stockC = game.getPlace().get(game.getPlace().size()-1);
                    if (stockC.getNum()>=c.getNum()) {
                        return false;
                    }
                }
                moveTo(c, c.getX(), c.getY()-20, 0);
                select.add(c);
            }
        }
        return true;
    }
}
