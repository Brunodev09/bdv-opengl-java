package com.src.shader;

import com.src.entity.Camera;
import com.src.entity.Camera2D;
import com.src.utils.MathUtils;
import org.lwjgl.util.vector.Matrix4f;

public class PrimitiveShader extends Shader {

    private static final String V_FILE = "src/com/src/shader/vertexShaderPrimitive.txt";
    private static final String F_FILE = "src/com/src/shader/fragmentShaderPrimitive.txt";

    private int _variableLocation1;
    private int _variableLocation2;
    private int _variableLocation3;

    public PrimitiveShader() {
        super(V_FILE, F_FILE);
    }

    @Override
    protected void getAllUniformsVariables() {
        _variableLocation1 = super.getUniformVariable("transformationPrimitive");
        _variableLocation2 = super.getUniformVariable("projectionPrimitive");
        _variableLocation3 = super.getUniformVariable("viewPrimitive");
    }

    @Override
    protected void _bindAttr() {
        super._bind(0, "positionPrimitive");
        super._bind(1, "textureCoordinatesPrimitive");
    }

    public void loadTransformationMatrix(Matrix4f m4x4) {
        super.loadMatrixInUniformVariable(_variableLocation1, m4x4);
    }

    public void loadProjectionMatrix(Matrix4f m4x4) {
        super.loadMatrixInUniformVariable(_variableLocation2, m4x4);
    }

    public void loadViewMatrix(Camera camera) {
        Matrix4f view = MathUtils.createViewMatrix(camera);
        super.loadMatrixInUniformVariable(_variableLocation3, view);
    }

    public void loadViewMatrix(Camera2D camera) {
        Matrix4f view = MathUtils.createViewMatrix(camera);
        super.loadMatrixInUniformVariable(_variableLocation3, view);
    }
}
