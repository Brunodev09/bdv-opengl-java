package com.src.shader;

public class StaticShader extends Shader {

    private static final String V_FILE = "src/com/src/shader/vertexShader.txt";
    private static final String F_FILE = "src/com/src/shader/fragmentShader.txt";

    public StaticShader() {
        super(V_FILE, F_FILE);
    }

    @Override
    protected void _bindAttr() {
        super._bind(0, "position");
    }
}
