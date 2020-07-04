package com.src.utils;

import com.src.entity.Camera;
import com.src.entity.Camera2D;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector2f;
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

    public static Matrix4f createProjectionMatrix(float fov, float np, float fp) {
        Matrix4f matrix = new Matrix4f();;

        float ratio = (float) Display.getWidth() / (float) Display.getHeight();
        float scaleY = (1f / (float) Math.tan(Math.toRadians(fov / 2f))) * ratio;
        float scaleX = scaleY / ratio;
        float frustumLength = fp - np;

        matrix.m00 = scaleX;
        matrix.m11 = scaleY;
        matrix.m22 = -((fp + np) / frustumLength);
        matrix.m23 = -1;
        matrix.m32 = -((2 * np * fp) / frustumLength);
        matrix.m33 = 0;

        return matrix;
    }

    public static Matrix4f createViewMatrix(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f pos = camera.getPosition();
        Vector3f negativePos = new Vector3f(-pos.x, -pos.y, -pos.z);
        Matrix4f.translate(negativePos, viewMatrix, viewMatrix);

        return viewMatrix;
    }

    public static Matrix4f createViewMatrix(Camera2D camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.setIdentity();
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1, 0, 0), viewMatrix, viewMatrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0, 1, 0), viewMatrix, viewMatrix);
        Vector3f pos = camera.getPosition();
        Vector3f negativePos = new Vector3f(-pos.x, -pos.y, 0.0f);
        Matrix4f.translate(negativePos, viewMatrix, viewMatrix);

        return viewMatrix;
    }
}
