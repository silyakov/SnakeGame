package com.javarush.games.snake;

import com.javarush.engine.cell.*;

/**
 * Created by antonsilakov on 11/04/2020.
 */
public class Apple extends GameObject {

    private static final String APPLE_SIGN =  "\uD83C\uDF4E";
    public boolean isAlive = true; // переменная хранит состояние яблока целое или съедено

    Apple(int x1, int y1) {
        super(x1, y1);
    }

    public void draw (Game game) {

       game.setCellValueEx(x, y, Color.NONE, APPLE_SIGN, Color.RED, 75);
    }
}