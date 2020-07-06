package com.src.renderer;

import com.src.entity.Camera;
import com.src.entity.Entity;
import com.src.entity.Lightsource;
import com.src.model.TexturedModel;
import com.src.shader.Shader;
import com.src.shader.StaticShader;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderManager {

    private static StaticShader _shader;
    private static final Map<TexturedModel, List<Entity>> _entities = new HashMap<>();

    public static void createRender(int WIDTH, int HEIGHT, String TITLE) {

        ContextAttribs contextAttribs = new ContextAttribs(3, 2)
        .withForwardCompatible(true)
        .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), contextAttribs);
            Display.setTitle(TITLE);
        } catch (LWJGLException e) {
            System.err.println(e);
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void renderBatch(StaticShader shader, Lightsource light, Camera camera) {
        _shader = shader;
        Renderer renderer = new Renderer(shader);
        renderer.init();
        _shader.init();
        _shader.loadLight(light);
        _shader.loadViewMatrix(camera);
        renderer.renderEntities(_entities);
        _shader.stop();
        _entities.clear();
    }

    public static void processEntity(Entity entity) {
        TexturedModel model = entity.getModel();
        List<Entity> listOfEntities = _entities.get(model);
        if (listOfEntities != null) listOfEntities.add(entity);
        else {
            List<Entity> newList = new ArrayList<>();
            newList.add(entity);
            _entities.put(model, newList);
        }
    }

    public static void runCollector() {
        _shader.stop();
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
