package com.src.loop;

import com.src.config.Configuration;
import com.src.entity.Camera;
import com.src.entity.Entity;
import com.src.model.TexturedModel;
import com.src.renderer.Loader;
import com.src.model.Model;
import com.src.renderer.OBJParser;
import com.src.renderer.RenderManager;
import com.src.renderer.Renderer;
import com.src.shader.StaticShader;
import com.src.texture.ModelTexture;
import org.lwjgl.util.vector.Vector3f;

import java.io.IOException;

public class Game {
    public static void loop(Configuration config) {
        RenderManager.createRender(config.WIDTH, config.HEIGHT, config.TITLE);

        Loader loader = new Loader();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

//        float[] vertices = {
//                -0.5f,0.5f,-0.5f,
//                -0.5f,-0.5f,-0.5f,
//                0.5f,-0.5f,-0.5f,
//                0.5f,0.5f,-0.5f,
//
//                -0.5f,0.5f,0.5f,
//                -0.5f,-0.5f,0.5f,
//                0.5f,-0.5f,0.5f,
//                0.5f,0.5f,0.5f,
//
//                0.5f,0.5f,-0.5f,
//                0.5f,-0.5f,-0.5f,
//                0.5f,-0.5f,0.5f,
//                0.5f,0.5f,0.5f,
//
//                -0.5f,0.5f,-0.5f,
//                -0.5f,-0.5f,-0.5f,
//                -0.5f,-0.5f,0.5f,
//                -0.5f,0.5f,0.5f,
//
//                -0.5f,0.5f,0.5f,
//                -0.5f,0.5f,-0.5f,
//                0.5f,0.5f,-0.5f,
//                0.5f,0.5f,0.5f,
//
//                -0.5f,-0.5f,0.5f,
//                -0.5f,-0.5f,-0.5f,
//                0.5f,-0.5f,-0.5f,
//                0.5f,-0.5f,0.5f
//
//        };
//
//        float[] textureCoords = {
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0,
//                0,0,
//                0,1,
//                1,1,
//                1,0
//        };
//
//        int[] indexes = {
//                0,1,3,
//                3,1,2,
//                4,5,7,
//                7,5,6,
//                8,9,11,
//                11,9,10,
//                12,13,15,
//                15,13,14,
//                16,17,19,
//                19,17,18,
//                20,21,23,
//                23,21,22
//        };

//        Model mdl = loader.loadDataToVAO(vertices, indexes);
//        Model mdl = loader.loadDataTextureToVAO(vertices, textureCoords, indexes);
        Model mdl = OBJParser.parseOBJ("testModel", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("testTexture"));
        TexturedModel tmdl = new TexturedModel(mdl, texture);

        Entity entity = new Entity(tmdl, new Vector3f(0, 0, -20), 0, 0, 0, 1);
        Camera cam = new Camera();

        while (!RenderManager.shouldExit()) {
            entity.rotate(0, 0.5f, 0);
            cam.move();
            renderer.init();
            shader.init();
            shader.loadViewMatrix(cam);
            renderer.render(entity, shader);
            shader.stop();
            RenderManager.updateRender(config.FPS);
        }

        shader.runCollector();
        loader.runCollector();
        RenderManager.closeRender();
    }
}
