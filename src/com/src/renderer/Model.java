package com.src.renderer;

public class Model {
    private int _vaoID;
    private int _vertexCount;

    public Model(int vaoID, int vertexCount) {
        this._vaoID = vaoID;
        this._vertexCount = vertexCount;
    }

    public int getVAOID() {
        return this._vaoID;
    }

    public int getVertexCount() {
        return this._vertexCount;
    }

}
