package no.group3.mindmaster.Model;

/**
 * Created by Wschive on 06/03/14.
 * Each enum contains an android.graphics.Color int. Black is therefore BLACK(Color.BLACK).
 * None is Color.TRANSPARENT
 * 0 is nothing, 1 is white, 2 is black
 */
public enum KeyPeg {

    TRANSPARENT(0), WHITE(1), BLACK(2);

    private int peg;

    KeyPeg(int i){
        this.peg = i;
    }

    public int toInt(){
        return this.peg;
    }
}
