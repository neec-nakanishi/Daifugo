import java.util.ArrayList;
import javax.swing.JLabel;

class Com extends Player {
    public Com(Game game, String name, JLabel lb, int sx, int sy, int dx, int dy) {
        super(game, name, lb, sx, sy, dx, dy);
        select = new ArrayList<>();
    }

    public void show() {
        sort();
        for (int i=0 ; i<size() ; i++) {
            Card c = get(i);
            c.setLocation(sx+i*dx, sy+i*dy);
            game.getContentPane().setComponentZOrder(c, game.getComponentCount()-1);
        }
    }

    /* カードに対するアクション時（クリックなど）の処理 */
    @Override
    public String autoAction() {
        // スタート時は何もしない
        if (game.getStock().size()==52) {
            return "";
        }

        // 手持ちのカードが無い場合は何もしない
        if (size()==0) {
            return "";
        }
        
        sleep(100);
        
        if (game.getPlace().size()==0) {
            select.add(get(0));
            for (int i=1 ; i<size() ; i++) {
                if (size()>=i+1 && get(0).getNum()==get(i).getNum()) {
                    select.add(get(i));
                }
            }
            game.getPlace().setNum(select.size());
        } else {
            Card stockC = game.getPlace().get(game.getPlace().size()-1);
            for (Card c : this) {
                if (c.getNum()>stockC.getNum()) {
                    if (select.size()==0 || (select.size()>0 && select.get(0).getNum()==c.getNum())) {
                        select.add(c);
                    }
                    if (game.getPlace().getNum()==select.size()) {
                        break;
                    }
                }
            }
        }

        if (game.getPlace().getNum()!=0 && game.getPlace().getNum()!=select.size()) {
            select.clear();
        }

        // 選択しているカードがなければ「パス」
        if (select.size()==0) {
            String msg = "パス";
            setMessage(msg);
            return msg;
        }

        for (Card c : select) {
            remove(c);
            c.reverse();
            game.getPlace().add(c);
            int x = (int)(Math.random()*15)-8;
            int y = (int)(Math.random()*15)-8;
            game.getContentPane().setComponentZOrder(c, game.getComponentCount()-1);
            moveTo(c, game.getWidth()/2+x*3-30, game.getHeight()/2+y*3-45, 1);
        }
        select.clear();
        show();
        String msg = "よいしょ";
        setMessage(msg);
        return msg;
    }
}
