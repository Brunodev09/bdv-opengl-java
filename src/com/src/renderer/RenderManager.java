package com.src.renderer;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

public class RenderManager {

    public static void createRender(int WIDTH, int HEIGHT, String TITLE) {

        ContextAttribs contextAttribs = new ContextAttribs(3, 2);
        contextAttribs.withForwardCompatible(true);
        contextAttribs.withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), contextAttribs);
            Display.setTitle(TITLE);
        } catch (LWJGLException e) {
            System.err.println(e);
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void updateRender(int fps) {
        Display.sync(fps);
        Display.update();
    }

    public static void closeRender() {
        Display.destroy();
    }

    public static Boolean shouldExit() {
        return Display.isCloseRequested();
    }

}
