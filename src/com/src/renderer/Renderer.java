package com.src.renderer;

import com.src.model.Model;
import com.src.model.TexturedModel;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Renderer {
    public void init() {
        GL11.glClearColor(0, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
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
        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tmdl.getModelTexture().getId());
        GL11.glDrawElements(GL11.GL_TRIANGLES, mdl.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
    }
}
