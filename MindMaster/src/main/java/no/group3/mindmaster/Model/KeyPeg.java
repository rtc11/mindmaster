package no.group3.mindmaster.Model;

import java.security.Key;
import java.util.Comparator;

/**
 * Created by Wschive on 06/03/14.
 * Each enum contains an android.graphics.Color int. Black is therefore BLACK(Color.BLACK).
 * None is Color.TRANSPARENT
 * 0 is nothing, 1 is white, 2 is black
 */
public enum KeyPeg implements Comparator<KeyPeg> {

    TRANSPARENT(0), WHITE(1), BLACK(2);

    private int peg;

    KeyPeg(int i){
        this.peg = i;
    }

    public static KeyPeg getKeyPeg(int i){
        if(i == 0)return KeyPeg.TRANSPARENT;
        if(i == 1)return KeyPeg.WHITE;
        if(i == 2)return KeyPeg.BLACK;
        return null;
    }

    public int toInt(){
        return this.peg;
    }

    @Override
    public int compare(KeyPeg a1, KeyPeg a2) {
        return a1.toInt() - a2.toInt();
    }
}
