package com.src.config;

public class Configuration {
    public final int WIDTH;
    public final int HEIGHT;
    public final int FPS;
    public final String TITLE;

    public Configuration(int WIDTH, int HEIGHT, int FPS, String TITLE) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.FPS = FPS;
        this.TITLE = TITLE;
    }

    public Configuration() {
        this.WIDTH = 800;
        this.HEIGHT = 600;
        this.FPS = 60;
        this.TITLE = "Unnamed video service";
    }

}
