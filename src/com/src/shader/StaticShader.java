package com.src.shader;

import org.lwjgl.util.vector.Matrix4f;

public class StaticShader extends Shader {

    private static final String V_FILE = "src/com/src/shader/vertexShader.txt";
    private static final String F_FILE = "src/com/src/shader/fragmentShader.txt";

    private int _variableLocation1;

    public StaticShader() {
        super(V_FILE, F_FILE);
    }

    @Override
    protected void getAllUniformsVariables() {
        _variableLocation1 = super.getUniformVariable("transformation");
    }

    @Override
    protected void _bindAttr() {
        super._bind(0, "position");
        super._bind(1, "textureCoordinates");
    }

    public void loadTransformationMatrix(Matrix4f m4x4) {
        super.loadMatrixInUniformVariable(_variableLocation1, m4x4);
    }
}
