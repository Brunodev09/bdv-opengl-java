package com.src.loop;

import com.src.config.Configuration;
import com.src.model.TexturedModel;
import com.src.renderer.Loader;
import com.src.model.Model;
import com.src.renderer.RenderManager;
import com.src.renderer.Renderer;
import com.src.shader.StaticShader;
import com.src.texture.ModelTexture;

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

        float[] textureCoords = {
                0,0,
                0,1,
                1,1,
                1,0
        };

//        Model mdl = loader.loadDataToVAO(vertices, indexes);
        Model mdl = loader.loadDataTextureToVAO(vertices, textureCoords, indexes);
        ModelTexture texture = new ModelTexture(loader.loadTexture("tex1"));
        TexturedModel tmdl = new TexturedModel(mdl, texture);

        while (!RenderManager.shouldExit()) {
            renderer.init();
            shader.init();
            renderer.render(tmdl);
            shader.stop();
            RenderManager.updateRender(config.FPS);
        }

        shader.runCollector();
        loader.runCollector();
        RenderManager.closeRender();
    }
}
