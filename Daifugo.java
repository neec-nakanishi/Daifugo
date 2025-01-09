import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;
import java.util.ArrayList;

class Daifugo extends Game {
    private Player player;      // プレーヤー
    private Com com1;           // コンピュータ１
    private Com com2;           // コンピュータ２
    private Com com3;           // コンピュータ３
    private JLabel[] message;
    private JButton btn;        // ボタン
    public static void main(String[] args) {
    	new Daifugo();
    }

    public Daifugo() {
        setSize(800, 750);
        getContentPane( ).setBackground(new Color(0,196,0));

        // ラベル
        message = new JLabel[4];
        message[0] = new JLabel(""); message[0].setSize(100, 40); message[0].setLocation(410, 550);
        message[1] = new JLabel(""); message[1].setSize(100, 40); message[1].setLocation(640, 300);
        message[2] = new JLabel(""); message[2].setSize(100, 40); message[2].setLocation(410, 100);
        message[3] = new JLabel(""); message[3].setSize(100, 40); message[3].setLocation(100, 300);
        for (JLabel lb : message) {
            getContentPane().add(lb);
        }

        // カードの作成
        player = new Player(this, "Player", message[0], 140, 600, 40, 0);
        com1 = new Com(this, "COM1", message[1], 710, 70, 0, 40);
        com2 = new Com(this, "COM2", message[2], 140, 10, 40, 0);
        com3 = new Com(this, "COM3", message[3], 10, 70, 0, 40);
        playerReset();

        // 「Done」ボタンを作成
        btn = new JButton("Done");
        btn.setSize(100,50);
        btn.setLocation(690, 660);
        btn.addActionListener(new ButtonAction());
        btn.setEnabled(false);
        getContentPane().add(btn);

        // 表示
        setVisible(true);

        // ゲームをスタート
        gameStart();
    }

    public void setMessage(int index, String msg) {
        message[index].setText(msg);
    }

    @Override
    public void playerReset() {
        addPlayer(player);
        addPlayer(com1);
        addPlayer(com2);
        addPlayer(com3);
        for (Player p : players) {
            p.setMessage("");
        }
    }

    @Override
    public void cardReset() {
        // カードをシャッフル
        stock.shuffle();
        // ストックを表示
        stock.show();
    }

    @Override
    public void gameStart() {
        // 初期化
        cardReset();	
    }

    @Override
    public void action(MouseEvent e) {}

    public JButton getButton() {
        return btn;
    }

    public class ButtonAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            player.setAction(true);
            btn.setEnabled(false);
        }
    }
}
