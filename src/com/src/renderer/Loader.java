package com.src.renderer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static com.src.renderer.BufferOperations.convertFloatToFloatBuffer;

public class Loader {

    // Tracking VAO's and VBO's to collect them from memory afterwards
    private List<Integer> VAOs = new ArrayList<>();
    private List<Integer> VBOs = new ArrayList<>();

    public Model loadDataToVAO(float[] positions, int[] indexes) {
        int VID = _createVAO();
        _bindIndexBufferVBO(indexes);
        _storeDataInList(0, positions);
        _unbindVAO();

        return new Model(VID, indexes.length);
    }

    public void runCollector() {
        for (int VAO : VAOs) {
            GL30.glDeleteVertexArrays(VAO);
        }
        for (int VBO : VBOs) {
            GL15.glDeleteBuffers(VBO);
        }
    }

    private void _bindIndexBufferVBO(int[] indexes) {
        int vboID = GL15.glGenBuffers();
        VBOs.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = BufferOperations.convertFloatToIntBuffer(indexes);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

    }

    private int _createVAO() {
        int VID = GL30.glGenVertexArrays();

        VAOs.add(VID);

        GL30.glBindVertexArray(VID);

        return VID;
    }

    private void _storeDataInList(int attrNum, float[] data) {
        int vboId = GL15.glGenBuffers();

        VBOs.add(vboId);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
        FloatBuffer buffer = BufferOperations.convertFloatToFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        // size = 3 for 3-D vectors
        GL20.glVertexAttribPointer(attrNum, 3, GL11.GL_FLOAT, false, 0, 0);
        // unbinding VBO
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void _unbindVAO() {
        GL30.glBindVertexArray(0);
    }


}
