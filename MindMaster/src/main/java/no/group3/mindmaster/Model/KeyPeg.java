package no.group3.mindmaster.Model;

import android.graphics.Color;

/**
 * Created by Wschive on 06/03/14.
 * Each enum contains an android.graphics.Color int. Black is therefore BLACK(Color.BLACK).
 * None is Color.TRANSPARENT
 * 0 is nothing, 1 is white, 2 is black
 */
public enum KeyPeg {

    BLACK(2), WHITE(1), TRANSPARENT(0);

    private int peg;

    KeyPeg(int i){
        this.peg = i;
    }

}
