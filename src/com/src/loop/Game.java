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
import com.src.shader.DefaultShader;
import com.src.terrain.Terrain;
import com.src.texture.ModelTexture;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.nio.Buffer;
import java.util.List;

public class Game {
    public static void loop(Configuration config) {

        float[] vertices = {
                -50.5f, 50.5f, 0,
                -50.5f, -50.5f, 0,
                50.5f, -50.5f, 0,
                50.5f, 50.5f, 0,
        };

        int[] indexes = {
                0, 1, 3,
                3, 1, 2
        };

        float[] textureCoords = {
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0,
                0, 0,
                0, 1,
                1, 1,
                1, 0
        };

        RenderManager.createRender(config.WIDTH, config.HEIGHT, config.TITLE);

        Pipeline pipe = new Pipeline();
//        DefaultShader shader = new DefaultShader();
//        PrimitiveShader primitiveShader = new PrimitiveShader();
//        Renderer primitiveRenderer = Renderer.Renderer2D(primitiveShader);

//        Model mdl = pipe.loadDataToVAO(vertices, indexes);
//        Model mdl = pipe.loadDataTextureToVAO(vertices, textureCoords, indexes);

        BufferedModel data = OBJParser.parseOBJ("testModel");
        Model mdl = pipe.loadDataToVAO(data.getVertices(), data.getTextures(), data.getNormals(), data.getIndexes());
        ModelTexture texture = new ModelTexture(pipe.loadTexture("white"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TexturedModel tmdl = new TexturedModel(mdl, texture);
        Entity entity = new Entity(tmdl, new Vector3f(0, -5, -30), 0, 0, 0, 1);

//        BufferedModel data2 = new BufferedModel(vertices, textureCoords, indexes);
//        Model mdl2 = pipe.loadDataToVAO(data2.getVertices(), data2.getTextures(), data2.getIndexes());
//        ModelTexture texture2 = new ModelTexture(pipe.loadTexture("tex2"));
//        TexturedModel tmdl2 = new TexturedModel(mdl2, texture2);
//        Entity entity2 = new Entity(tmdl2, new Vector3f(0, 0, 0), 0, 0, 0, 1);
//        Camera2D cam2d = new Camera2D();

        Lightsource light = new Lightsource(new Vector3f(300, 300, -30), new Vector3f(1, 1, 1));
        Camera cam = new Camera();

        Terrain terrain = new Terrain(0, 0 , new ModelTexture(pipe.loadTexture("grass")));
        Model terrainModel = pipe.loadDataToVAO(
                terrain.getMdl().getVertices(),
                terrain.getMdl().getTextures(),
                terrain.getMdl().getNormals(),
                terrain.getMdl().getIndexes());
        terrain.setProcessedModel(terrainModel);


        while (!RenderManager.shouldExit()) {
            entity.rotate(0, 0.5f, 0);

            cam.move();

            RenderManager.processTerrain(terrain);
            RenderManager.processEntity(entity);
            RenderManager.renderBatch(light, cam);
            RenderManager.updateRender(config.FPS);

//            entity2.rotate(0, 0, 0.5f);
//            cam2d.move();
//            primitiveRenderer.init();
//            primitiveShader.init();
//            primitiveShader.loadViewMatrix(cam2d);
//            primitiveRenderer.render(entity2, primitiveShader);
//            primitiveShader.stop();

        }

        RenderManager.runCollector();
        pipe.runCollector();
        RenderManager.closeRender();
    }

    private void updateEntities(List<Entity> entities) {

    }

    private void renderingPipelineForPrimitives(PrimitiveShader shader) {

    }

    private void renderingPipeline(DefaultShader shader, Renderer renderer) {

    }
}
