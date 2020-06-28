package com.src.loop;

import com.src.config.Configuration;
import com.src.renderer.Loader;
import com.src.renderer.Model;
import com.src.renderer.RenderManager;
import com.src.renderer.Renderer;
import com.src.shader.StaticShader;

public class Game {
    public static void loop(Configuration config) {
        RenderManager.createRender(config.WIDTH, config.HEIGHT, config.TITLE);

        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        StaticShader shader = new StaticShader();

        float[] vertices = {
                -0.5f, 0.5f, 0,
                -0.5f, -0.5f, 0,
                0.5f, -0.5f, 0,
                0.5f, 0.5f, 0f
        };

        int[] indexes = {
                0,1,3,
                3,1,2
        };

        Model mdl = loader.loadDataToVAO(vertices, indexes);

        while (!RenderManager.shouldExit()) {
            renderer.init();
            shader.init();
            renderer.render(mdl);
            shader.stop();
            RenderManager.updateRender(config.FPS);
        }

        shader.runCollector();
        loader.runCollector();
        RenderManager.closeRender();
    }
}
