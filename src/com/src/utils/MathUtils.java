package com.src.utils;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

public class MathUtils {
    public static Matrix4f createTransformationMatrix(Vector3f translation, float rX, float rY, float rZ, float S) {
        Matrix4f matrix = new Matrix4f();

        matrix.setIdentity();

        Matrix4f.translate(translation, matrix, matrix);

        Matrix4f.rotate((float) Math.toRadians(rX), new Vector3f(1, 0, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rY), new Vector3f(0, 1, 0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rZ), new Vector3f(0, 0, 1), matrix, matrix);

        Matrix4f.scale(new Vector3f(S, S, S), matrix, matrix);

        return matrix;
    }
}
