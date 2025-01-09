class Place extends CardHolder {
    private int num;

    public Place(Game game, String name) {
        super(game, name);
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public boolean autoAction() {
        while(size()>0) {
            Card c = remove(0);
            c.setFace(false);
            game.stock.add(c);
            moveTo(c, c.getX()-200, c.getY()+100, 0);
        }
        num = 0;
        return true;
    }

    /* カードに対するアクション時（クリックなど）の処理 */
    public void action(Card c) {
    }
}
