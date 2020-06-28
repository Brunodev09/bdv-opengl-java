package com.src.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public abstract class Shader {
    private int _id;
    private int _vertexShaderId;
    private int _fragmentShaderId;

    public Shader(String vertexShaderFile, String fragmentShaderFile) {
        _vertexShaderId = _load(vertexShaderFile, GL20.GL_VERTEX_SHADER);
        _fragmentShaderId = _load(fragmentShaderFile, GL20.GL_FRAGMENT_SHADER);
        _id = GL20.glCreateProgram();
        GL20.glAttachShader(_id, _vertexShaderId);
        GL20.glAttachShader(_id, _fragmentShaderId);
        GL20.glLinkProgram(_id);
        GL20.glValidateProgram(_id);
        _bindAttr();
    }

    public void init() {
        GL20.glUseProgram(_id);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void runCollector() {
        stop();
        GL20.glDetachShader(_id, _vertexShaderId);
        GL20.glDetachShader(_id, _fragmentShaderId);
        GL20.glDeleteShader(_vertexShaderId);
        GL20.glDeleteShader(_fragmentShaderId);
        GL20.glDeleteProgram(_id);
    }

    protected abstract void _bindAttr();

    protected void _bind(int attr, String varName) {
        GL20.glBindAttribLocation(_id, attr, varName);
    }


    private static int _load(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        int shaderID = GL20.glCreateShader(type);

        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);

        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader program!");
            System.exit(-1);
        }

        return shaderID;
    }

}
