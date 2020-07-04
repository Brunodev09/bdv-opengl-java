package com.src.loop;

import com.src.config.Configuration;
import com.src.entity.Camera;
import com.src.entity.Camera2D;
import com.src.entity.Entity;
import com.src.entity.Lightsource;
import com.src.model.BufferedModel;
import com.src.model.TexturedModel;
import com.src.renderer.Pipeline;
import com.src.model.Model;
import com.src.renderer.OBJParser;
import com.src.renderer.RenderManager;
import com.src.renderer.Renderer;
import com.src.shader.PrimitiveShader;
import com.src.shader.StaticShader;
import com.src.texture.ModelTexture;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class Game {
    public static void loop(Configuration config) {

//        float[] vertices = {
//                -0.5f, 0.5f, -0.5f,
//                -0.5f, -0.5f, -0.5f,
//                0.5f, -0.5f, -0.5f,
//                0.5f, 0.5f, -0.5f,
//        };
//
//        int[] indexes = {
//                0, 1, 3,
//                3, 1, 2
//        };

        RenderManager.createRender(config.WIDTH, config.HEIGHT, config.TITLE);

        Pipeline pipe = new Pipeline();
        StaticShader shader = new StaticShader();
        PrimitiveShader primitiveShader = new PrimitiveShader();
        Renderer primitiveRenderer = new Renderer(primitiveShader);
        Renderer renderer = new Renderer(shader);

//        Model mdl = pipe.loadDataToVAO(vertices, indexes);
//        Model mdl = pipe.loadDataTextureToVAO(vertices, textureCoords, indexes);

        BufferedModel data = OBJParser.parseOBJ("testModel");
        Model mdl = pipe.loadDataToVAO(data.getVertices(), data.getTextures(), data.getNormals(), data.getIndexes());
        ModelTexture texture = new ModelTexture(pipe.loadTexture("white"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TexturedModel tmdl = new TexturedModel(mdl, texture);
        Entity entity = new Entity(tmdl, new Vector3f(0, 0, -30), 0, 0, 0, 1);

//        BufferedModel data2 = new BufferedModel(vertices, indexes);
//        Model mdl2 = pipe.loadDataToVAO(data2.getVertices(), data2.getIndexes());
//        Entity entity2 = new Entity(mdl2, new Vector3f(0, 0, -10), 0, 0, 0, 1);
//        Camera2D cam2d = new Camera2D();

        Lightsource light = new Lightsource(new Vector3f(300, 300, -30), new Vector3f(1, 1, 1));
        Camera cam = new Camera();

        while (!RenderManager.shouldExit()) {
            entity.rotate(0, 0.5f, 0);
            renderer.init();
            shader.init();

            cam.move();

            shader.loadLight(light);
            shader.loadViewMatrix(cam);
            renderer.render(entity, shader);
            shader.stop();

//            cam2d.move();
//            primitiveRenderer.init();
//            primitiveShader.init();
//            primitiveShader.loadViewMatrix(cam2d);
//            primitiveRenderer.renderPrimitives(entity2, primitiveShader);
//            primitiveShader.stop();

            RenderManager.updateRender(config.FPS);
        }

        shader.runCollector();
        pipe.runCollector();
        RenderManager.closeRender();
    }

    private void updateEntities(List<Entity> entities) {

    }

    private void renderingPipelineForPrimitives(PrimitiveShader shader) {

    }

    private void renderingPipeline(StaticShader shader, Renderer renderer) {

    }
}
