package com.src.renderer;

import com.src.entity.Camera;
import com.src.entity.Entity;
import com.src.entity.Lightsource;
import com.src.model.TexturedModel;
import com.src.shader.Shader;
import com.src.shader.DefaultShader;
import com.src.shader.Terrain3DShader;
import com.src.terrain.Terrain;
import com.src.utils.MathUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RenderManager {

    private static DefaultShader _shader;
    private static Terrain3DShader _shaderTerrain;
    private static Matrix4f _projection;
    private static final Map<TexturedModel, List<Entity>> _entities = new HashMap<>();
    private static final List<Terrain> _terrains = new ArrayList<>();

    private static final float _FOV = 70;
    private static final float _NEAR_PLANE = 0.1f;
    private static final float _FAR_PLANE = 1000;

    private static Renderer mainRenderer;
    private static TerrainRenderer terrainRenderer;

    public static void createRender(int WIDTH, int HEIGHT, String TITLE) {

        ContextAttribs contextAttribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), contextAttribs);
            Display.setTitle(TITLE);

            _shader = new DefaultShader();
            _shaderTerrain = new Terrain3DShader();

        } catch (LWJGLException e) {
            System.err.println(e);
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
    }

    public static void initAllRenders() {
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.3f, 0.1f, 0.1f, 1);

        if (_projection == null) _projection = MathUtils.createProjectionMatrix(_FOV, _NEAR_PLANE, _FAR_PLANE);
        if (mainRenderer == null) mainRenderer = new Renderer(_shader, _projection);
        if (terrainRenderer == null) terrainRenderer = new TerrainRenderer(_shaderTerrain, _projection);
    }

    public static void renderBatch(Lightsource light, Camera camera) {
        initAllRenders();

        _shader.init();
        _shader.loadLight(light);
        _shader.loadViewMatrix(camera);
        mainRenderer.renderEntities(_entities);
        _shader.stop();

        _shaderTerrain.init();
        _shaderTerrain.loadLight(light);
        _shaderTerrain.loadViewMatrix(camera);
        terrainRenderer.render(_terrains);
        _shaderTerrain.stop();

        _terrains.clear();
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

    public static void processTerrain(Terrain tile) {
        _terrains.add(tile);
    }

    public static void runCollector() {
        _shader.runCollector();
        _shaderTerrain.runCollector();
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
