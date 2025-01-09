import javax.swing.*;
import java.awt.Image;

public class Card extends JLabel {
    private int num;        // 数字
    private String suit;    // 絵柄
    private boolean face;   // true: 表　false: 裏
    private ImageIcon img;  // 表面の画像
    private ImageIcon back; // 裏面の画像

    public Card(String suit, int num) {
        super();
        this.suit = suit;
    	this.num = num;
    	img = new ImageIcon("./png/"+suit+num+".png");
	    back = new ImageIcon("./png/z02.png");
	    setSize(img.getIconWidth( ), img.getIconHeight( ));
        setFace(false);
        if (num==1) this.num=14;
        if (num==2) this.num=15;
    }

    public int getNum() { return num; }
    public int getNumAce() { return (num==1)?14 : num; }
    public String getSuit() { return suit; }
    public boolean getFace() { return face; }
    
    public void setFace(boolean face) {
    	this.face = face;
        if (face) {
	        setIcon(img);
	    } else {
	        setIcon(back);
	    }
    }

    public void reverse() {
	    // ひっくり返す
	    setFace(!face);
    }

    @Override
    public void setSize(int w, int h) {
        super.setSize(w, h);
        Image newImage = this.img.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        this.img.setImage(newImage);
        Image newBack = this.back.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
        this.back.setImage(newBack);
    }
}
