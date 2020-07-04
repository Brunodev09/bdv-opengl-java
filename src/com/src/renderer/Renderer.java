package com.src.renderer;

import com.src.entity.Entity;
import com.src.model.Model;
import com.src.model.TexturedModel;
import com.src.shader.StaticShader;
import com.src.texture.ModelTexture;
import com.src.utils.MathUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;

public class Renderer {

    private static final float _FOV = 70;
    private static final float _NEAR_PLANE = 0.1f;
    private static final float _FAR_PLANE = 1000;

    private Matrix4f _projection;

    public Renderer(StaticShader shader) {
        _projection = MathUtils.createProjectionMatrix(_FOV, _NEAR_PLANE, _FAR_PLANE);
        shader.init();
        shader.loadProjectionMatrix(_projection);
        shader.stop();
    }

    public void init() {
        // Clip rectangles that are rendered on top of each other
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.5f, 0.5f, 0.5f, 1);
    }

    public void render(Model mdl) {
        GL30.glBindVertexArray(mdl.getVAOID());
        GL20.glEnableVertexAttribArray(0);
//        Used only if we are not indexing the vertexes
//        GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, mdl.getVertexCount());
        GL11.glDrawElements(GL11.GL_TRIANGLES, mdl.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
    }

    public void render(TexturedModel tmdl) {
        Model mdl = tmdl.getModel();
        GL30.glBindVertexArray(mdl.getVAOID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tmdl.getModelTexture().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, mdl.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

    public void render(Entity entity, StaticShader shader) {
        TexturedModel tmdl = entity.getModel();
        Model mdl = tmdl.getModel();
        GL30.glBindVertexArray(mdl.getVAOID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        // Applying transformations and loading them to the VAO
        Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(
                entity.getPosition(),
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);

        // Applying specular lighting - Maybe remove this from the Renderer
        ModelTexture md = tmdl.getModelTexture();
        shader.loadSpecularLights(md.getShineDamper(), md.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tmdl.getModelTexture().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, mdl.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        GL30.glBindVertexArray(0);
    }
}
