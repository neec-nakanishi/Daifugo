class Stock extends CardHolder {
    public Stock(Game game, String name) {
        super(game, name);
    }

    public void show() {
        for (int i=0; i<size() ; i++) {
            Card c = get(i);
            c.setLocation(game.getWidth()/2-i, game.getHeight()/2-i);  // 重ねる
            c.setFace(false);           // 裏向き
        }
    }

    /* カードに対するアクション時（クリックなど）の処理 */
    public void action(Card cd) {
        // スタート時以外は何もしない
        if (size()!=52) {
            return;
        }

        // カードを並べる
        for (int i=0 ; i<13 ; i++) {
            Card c = remove(size()-1);
            game.getPlayer(0).add(c);
            c.setFace(true);
            game.getContentPane().setComponentZOrder(c, game.getComponentCount()-1);
            moveTo(c, 140+i*40, 600, 1);

            c = remove(size()-1);
            game.getPlayer(1).add(c);
            c.setFace(false);
            game.getContentPane().setComponentZOrder(c, game.getComponentCount()-1);
            moveTo(c, 710, 70+i*40, 1);

            c = remove(size()-1);
            game.getPlayer(2).add(c);
            c.setFace(false);
            game.getContentPane().setComponentZOrder(c, game.getComponentCount()-1);
            moveTo(c, 140+i*40, 10, 1);

            c = remove(size()-1);
            game.getPlayer(3).add(c);
            c.setFace(false);
            game.getContentPane().setComponentZOrder(c, game.getComponentCount()-1);
            moveTo(c, 10, 70+i*40, 1);
        }
        // プレーヤー、コンピュータの手札の並び替え
        for (Player p : game.getPlayers()) {
            p.show();
        }

        // ゲームスタート
        Thread t = new Thread(new Action());
        t.start();
    }

    public class Action implements Runnable {
        @Override
        public void run() {
            int pass = 0;
            for (int i=0 ; game.getPlayers().size()>=2 ; i++) {
                i = i % game.getPlayers().size();
                // メッセージを消す
                for (Player p : game.getPlayers()) {
                    p.setMessage("");
                }

                // プレーヤーのアクション
                String msg = game.getPlayer(i).autoAction();
                if(msg.equals("パス")) {
                    pass++;
                } else {
                    pass = 0;
                }
                // あがりチェック
                if(game.getPlayer(i).size()==0) {
                    String[] str = {"トップ！", "よし！２番", "うーん３番", "ダメダメ"};
                    game.getPlayer(i).setMessage(str[4-game.getPlayers().size()]);
                    game.getPlayers().remove(i);
                    i--;
                }
                // 1人以外パスしたら場のカードを流す
                if (pass==game.getPlayers().size()-1) {
                    game.getPlace().autoAction();
                    pass = 0;
                }
            }
            // 新しくゲームを始める
            while(game.getPlayer(0).size()>0) {
                Card c = game.getPlayer(0).remove(0);
                c.reverse();
                add(c);
            }
            sleep(1000);
            game.getPlayers().remove(0);
            game.playerReset();
            game.cardReset();
        }
    }

}
