package no.group3.mindmaster.Model;

import android.graphics.Color;

/**
 * Created by Wschive on 06/03/14.
 * Each enum contains an android.graphics.Color int. Black is therefore BLACK(Color.BLACK).
 * None is Color.TRANSPARENT
 */
public enum KeyPeg {
    BLACK(Color.BLACK), WHITE(Color.WHITE), NONE(Color.TRANSPARENT);


    private final int color;
    KeyPeg(int color){
        this.color = color;
    }

    }
