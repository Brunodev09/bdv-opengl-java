package com.src.loop;

import com.src.config.Configuration;
import com.src.entity.Camera;
import com.src.entity.Entity;
import com.src.entity.Lightsource;
import com.src.model.BufferedModel;
import com.src.model.TexturedModel;
import com.src.renderer.Pipeline;
import com.src.model.Model;
import com.src.renderer.OBJParser;
import com.src.renderer.RenderManager;
import com.src.renderer.Renderer;
import com.src.shader.StaticShader;
import com.src.texture.ModelTexture;
import org.lwjgl.util.vector.Vector3f;

public class Game {
    public static void loop(Configuration config) {
        RenderManager.createRender(config.WIDTH, config.HEIGHT, config.TITLE);

        Pipeline pipe = new Pipeline();
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);

//        Model mdl = pipe.loadDataToVAO(vertices, indexes);
//        Model mdl = pipe.loadDataTextureToVAO(vertices, textureCoords, indexes);

        BufferedModel data = OBJParser.parseOBJ("sphere");
        Model mdl = pipe.loadDataToVAO(data.getVertices(), data.getTextures(), data.getNormals(), data.getIndexes());
        ModelTexture texture = new ModelTexture(pipe.loadTexture("testTexture"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TexturedModel tmdl = new TexturedModel(mdl, texture);
        Entity entity = new Entity(tmdl, new Vector3f(0, 0, -30), 0, 0, 0, 1);

        Lightsource light = new Lightsource(new Vector3f(300, 300,-30), new Vector3f(1, 1,1));
        Camera cam = new Camera();

        while (!RenderManager.shouldExit()) {
            entity.rotate(0.5f, 0.5f, 0.5f);
            cam.move();
            renderer.init();
            shader.init();
            shader.loadLight(light);
            shader.loadViewMatrix(cam);
            renderer.render(entity, shader);
            shader.stop();
            RenderManager.updateRender(config.FPS);
        }

        shader.runCollector();
        pipe.runCollector();
        RenderManager.closeRender();
    }
}
