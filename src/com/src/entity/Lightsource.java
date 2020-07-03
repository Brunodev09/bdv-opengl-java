package com.src.entity;

import com.src.model.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

public class Lightsource {
    private Vector3f position;
    private Vector3f color;

    public Lightsource(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getColor() {
        return color;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

}
