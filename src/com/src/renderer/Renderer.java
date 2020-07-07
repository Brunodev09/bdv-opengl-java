package com.src.renderer;

import com.src.entity.Entity;
import com.src.model.Model;
import com.src.model.TexturedModel;
import com.src.shader.PrimitiveShader;
import com.src.shader.DefaultShader;
import com.src.texture.ModelTexture;
import com.src.utils.MathUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;
import java.util.Map;

public class Renderer {
    // @TODO - Manage the used positions on the VAO.
    // @TODO - Manage 2D renderer through the RendererManager

    private Matrix4f _projection;
    private DefaultShader _shader;

    public Renderer() {

    }

    public Renderer(DefaultShader shader, Matrix4f projectionMatrix) {
        _shader = shader;
        // Culling back faces
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        shader.init();
        shader.loadProjectionMatrix(projectionMatrix);
        shader.stop();
    }

    public Renderer(PrimitiveShader shader) {
        // Culling back faces
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        shader.init();
        shader.loadProjectionMatrix(_projection);
        shader.stop();
    }

    public static Renderer Renderer2D(PrimitiveShader shader) {
        Renderer _r = new Renderer();
        _r._projection = MathUtils.createOrthographicMatrix();
        shader.init();
        shader.loadProjectionMatrix(_r._projection);
        shader.stop();

        return _r;
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

    public void renderEntities(Map<TexturedModel, List<Entity>> entities) {
        for (TexturedModel key : entities.keySet()) {
            this._modelSetup(key);
            List<Entity> entitiesToLoadFromModel = entities.get(key);
            for (Entity entity : entitiesToLoadFromModel) {
                this._applyTransformationAndLoadIntoShader(entity);
                GL11.glDrawElements(GL11.GL_TRIANGLES, key.getModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
            }
            this._unbindTexture();
        }
    }


    private void _modelSetup(TexturedModel tmdl) {
        Model mdl = tmdl.getModel();
        GL30.glBindVertexArray(mdl.getVAOID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);
        ModelTexture md = tmdl.getModelTexture();
        _shader.loadSpecularLights(md.getShineDamper(), md.getReflectivity());

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tmdl.getModelTexture().getId());
    }

    private void _unbindTexture() {
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        GL30.glBindVertexArray(0);
    }

    private void _applyTransformationAndLoadIntoShader(Entity entity) {
        // Applying transformations and loading them to the VAO
        Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(
                entity.getPosition(),
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale());
        _shader.loadTransformationMatrix(transformationMatrix);
    }

    public void render(Entity entity, PrimitiveShader shader) {
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

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tmdl.getModelTexture().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, mdl.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);

        GL30.glBindVertexArray(0);
    }

    // Used for rendering without textures, using simple models.
    public void renderPrimitives(Entity entity, PrimitiveShader shader) {
        Model mdl = entity.getModelPrimitive();

        Matrix4f transformationMatrix = MathUtils.createTransformationMatrix(
                entity.getPosition(),
                entity.getRotX(),
                entity.getRotY(),
                entity.getRotZ(),
                entity.getScale());
        shader.loadTransformationMatrix(transformationMatrix);

        GL30.glBindVertexArray(mdl.getVAOID());
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL20.glEnableVertexAttribArray(2);

        GL11.glDrawElements(GL11.GL_TRIANGLES, mdl.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL20.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }
}
