public class CardAnimation {
    private Game game;

    public CardAnimation(Game game) {
        this.game = game;
    }

    public void sleep(int ms) {
        if (ms==0) return;
        try {
            Thread.sleep(ms);
        } catch(Exception e) {
        }
    }

    /* 一定時間経過後カードをひっくり返す */
    public void reverse(Card c, int timer) {
        Reverse r = new Reverse(c, timer);
        Thread t = new Thread(r);
        t.start();
        try {
            t.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public class Reverse implements Runnable {
        Card c;         // 対象カード
        int timer;      // 待機時間（ミリ秒）
        
        public Reverse(Card c, int timer) {
            this.c = c;
            this.timer = timer;
        }

        @Override
        public void run() {
            sleep(timer);
            c.reverse();
        }
    }

    /* カードを移動させる */
    public void moveTo(Card c, int dx, int dy, int interval) {
        Move m = new Move(c, dx, dy, interval);
        Thread t = new Thread(m);
        t.start();
        try {
            t.join();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public class Move implements Runnable {
        Card c;         // 対象カード
        int dx;         // 移動先ｘ座標
        int dy;         // 移動先ｙ座標
        int interval;   // アニメーションの間隔(ミリ秒)

        public Move(Card c, int dx, int dy, int interval) {
            this.c = c;
            this.dx = dx;
            this.dy = dy;
            this.interval = interval;
        }

        @Override
        public void run() {
            int x = c.getLocation().x;
            int y = c.getLocation().y;

            // 変化が無い場合は何もしない
            if (dx-x == 0 && dy-y == 0) {
                return;
            }

            // xの変化量とyの変化量を調べて増加させる軸で場合分けする
            if (Math.abs(dx-x) > Math.abs(dy-y)) {
                double a = Math.signum(dx-x) * (double)(dy - y) / (double)(dx - x);
                for (int i=0 ; i<=Math.abs(dx-x) ; i+=2) {
                    int ty = (int)(a * i + y);
                    c.setLocation((int)Math.signum(dx-x)*i+x, ty);
                    sleep(interval);
                }    
            } else {
                double a = Math.signum(dy-y) * (double)(dx - x) / (double)(dy - y);
                for (int i=0 ; i<=Math.abs(dy-y) ; i+=2) {
                    int tx = (int)(a * i + x);
                    c.setLocation(tx, (int)Math.signum(dy-y)*i+y);
                    sleep(interval);
                }    
            }
        }
    }
}
