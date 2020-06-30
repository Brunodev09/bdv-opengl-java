package com.src.renderer;

import com.src.model.Model;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OBJParser {
    private static final String _EXT = ".obj";

    public static Model parseOBJ(String file, Loader loader) {
        try {
            FileReader reader = new FileReader("res/" + file + _EXT);
            BufferedReader bf = new BufferedReader(reader);
            String line;
            List<Vector3f> vertexList = new ArrayList<>();
            List<Vector2f> textureCoordsList = new ArrayList<>();
            List<Vector3f> normalsList = new ArrayList<>();
            List<Integer> indexesList = new ArrayList<>();

            float[] vertexArr = null;
            float[] texturesArr = null;
            float[] normalsArr = null;

            int[] indexesArr = null;

            while (true) {
                line = bf.readLine();
                String[] curr = line.split(" ");

                // If its a vertex
                if (line.startsWith("v ")) {
                    vertexList.add(
                            new Vector3f(
                                    Float.parseFloat(curr[1]),
                                    Float.parseFloat(curr[2]),
                                    Float.parseFloat(curr[3])));
                }
                // If its a texture coordinate
                else if (line.startsWith("vt ")) {
                    textureCoordsList.add(
                            new Vector2f(
                                    Float.parseFloat(curr[1]),
                                    Float.parseFloat(curr[2])));
                }
                // If its a normal
                else if (line.startsWith("vn ")) {
                    normalsList.add(
                            new Vector3f(
                                    Float.parseFloat(curr[1]),
                                    Float.parseFloat(curr[2]),
                                    Float.parseFloat(curr[3])));
                }
                // If its a face
                else if (line.startsWith("f ")) {
                    texturesArr = new float[vertexList.size() * 2];
                    normalsArr = new float[vertexList.size() * 3];
                    break;
                }
            }

            while (line != null) {
                if (!line.startsWith("f ")) {
                    line = bf.readLine();
                    continue;
                }

                List<String[]> triangles = new ArrayList<>();
                triangles.add(line.split(" ")[1].split("/"));
                triangles.add(line.split(" ")[2].split("/"));
                triangles.add(line.split(" ")[3].split("/"));

                for (String[] tri : triangles) {
                    _parseVertex(tri, indexesList, textureCoordsList, normalsList, texturesArr, normalsArr);
                }
                triangles = null;
                line = bf.readLine();
            }

            bf.close();

            vertexArr = new float[vertexList.size() * 3];
            indexesArr = new int[indexesList.size()];

            int vertexPtr = 0;
            for (Vector3f vertex : vertexList) {
                vertexArr[vertexPtr++] = vertex.x;
                vertexArr[vertexPtr++] = vertex.y;
                vertexArr[vertexPtr++] = vertex.z;
            }

            int indexPtr = 0;
            for (int index : indexesList) {
                indexesArr[indexPtr] = indexesList.get(indexPtr);
                indexPtr++;
            }

            return loader.loadDataTextureToVAO(vertexArr, texturesArr, indexesArr);

        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return null;
    }

    private static void _parseVertex(String[] vertexData,
                                     List<Integer> indexes,
                                     List<Vector2f> textures,
                                     List<Vector3f> normals,
                                     float[] textureArr,
                                     float[] normalsArr) {
        int currVtxPointer = Integer.parseInt(vertexData[0]) - 1;
        indexes.add(currVtxPointer);
        Vector2f currTex = textures.get(Integer.parseInt(vertexData[1]) - 1);
        textureArr[currVtxPointer * 2] = currTex.x;
        textureArr[currVtxPointer * 2 + 1] = 1 - currTex.y;
        Vector3f currNormal = normals.get(Integer.parseInt(vertexData[2]) - 1);
        normalsArr[currVtxPointer * 3] = currNormal.x;
        normalsArr[currVtxPointer * 3 + 1] = currNormal.y;
        normalsArr[currVtxPointer * 3 + 2] = currNormal.z;
    }
}
