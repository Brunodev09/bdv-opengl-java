package com.src.demo;

import com.src.config.Configuration;
import com.src.loop.Game;

public class Main {
    public static void main(String[] args) {
        Game.loop(new Configuration(1280, 720, 60, "bdv"));
    }
}
