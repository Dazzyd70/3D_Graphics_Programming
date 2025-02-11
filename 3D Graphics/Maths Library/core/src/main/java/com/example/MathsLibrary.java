package com.example;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrixf;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_INFO_LOG_LENGTH;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MathsLibrary {
    private static Scanner scanner = new Scanner(System.in);

    // menu loop
    public static void main(String[] args) {
        while (true) {
            System.out.println("\nMaths Library - Choose an operation:");
            System.out.println("1. Add Vectors");
            System.out.println("2. Subtract Vectors");
            System.out.println("3. Multiply Vector by Scalar");
            System.out.println("4. Vector Magnitude");
            System.out.println("5. Dot Product");
            System.out.println("6. Cross Product");
            System.out.println("7. Distance Between Vectors");
            System.out.println("8. Transpose Matrix");
            System.out.println("9. Multiply Matrix by Scalar");
            System.out.println("10. Multiply Matrix by Matrix");
            System.out.println("11. Apply Scaling to Vector");
            System.out.println("12. Apply Translation to Vector");
            System.out.println("13. Apply Rotation to Vector");
			System.out.println("14. Apply Orthogonal Projection");
			System.out.println("15. Apply Perspective Projection");
            System.out.println("16. Rotating Wireframe Demo");
            System.out.println("17. Exit");
            System.out.print("Enter choice: ");

            int choice = scanner.nextInt();
            // exits if user chooses 17
            if (choice == 17) break;

            // chooses operation based on user choice
            if (choice >= 1 && choice <= 7) {
                handleVectorOperations(choice);
			} else if(choice >= 8 && choice <= 10) {
				handleMatrixOperations(choice); 
			} else if ( choice == 16) {
				startWireFrameDemo();
			} else if ( choice >= 14 && choice <= 15) {
				handleTransformationOperations(choice);
            } else { // 11, 12, 13
                handleTransformationOperations(choice);
            }
        }
    }

    // vector maths
    private static void handleVectorOperations(int choice) {
        System.out.print("Enter first vector (x y z): ");
        Vector3 v1 = new Vector3(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
        
        // for scalar multiplication and magnitude only one vecto ris needed
        if (choice >= 1 && choice <= 7) {
            if (choice == 3) {
                System.out.print("Enter scalar: ");
                double scalar = scanner.nextDouble();
                System.out.println("Result: " + v1.multiply(scalar));   // scalar multiplication
            } else if (choice == 4) {
                System.out.println("Magnitude: " + v1.magnitude());     // magnitude
            } else {
                // operations requiring two vectors
                System.out.print("Enter second vector (x y z): ");
                Vector3 v2 = new Vector3(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());
                switch (choice) {
                    case 1: System.out.println("Result: " + v1.add(v2)); break;             // addition
                    case 2: System.out.println("Result: " + v1.subtract(v2)); break;        // subtraction
                    case 5: System.out.println("Dot Product: " + v1.dot(v2)); break;        // dot product
                    case 6: System.out.println("Cross Product: " + v1.cross(v2)); break;    // cross product
                    case 7: System.out.println("Distance: " + v1.distance(v2)); break;      // distance between
                }
            }
		} 
    }

    // transpose, scalar multiplication, matrix multiplication
    private static void handleMatrixOperations(int choice) {
        double[][] values = new double[4][4];
        System.out.println("Enter 4x4 matrix values:");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                values[i][j] = scanner.nextDouble();
            }
        }
        Matrix4 matrix = new Matrix4(values);
        
        if (choice == 8) {
            System.out.println("Transposed Matrix:\n" + matrix.transpose());  // transposition
        } else if (choice == 9) {
            System.out.print("Enter scalar: ");  // scalar matrix multiplication
            double scalar = scanner.nextDouble();
            System.out.println("Result:\n" + matrix.multiply(scalar));
        } else if (choice == 10) {
            System.out.println("Enter second 4x4 matrix:");  // matriz multiplication
            double[][] values2 = new double[4][4];
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    values2[i][j] = scanner.nextDouble();
                }
            }
            Matrix4 matrix2 = new Matrix4(values2);
            System.out.println("Matrix Multiplication Result:\n" + matrix.multiply(matrix2));
		}
    }

    // vector scaling, translation, rotation, projections
    private static void handleTransformationOperations(int choice) {
		System.out.print("Enter vector (x y z): ");
        Vector3 v1 = new Vector3(scanner.nextDouble(), scanner.nextDouble(), scanner.nextDouble());

        if (choice == 11) {
            System.out.print("Enter scaling factors (sx sy sz): "); // scaling 
            double sx = scanner.nextDouble(), sy = scanner.nextDouble(), sz = scanner.nextDouble();
            Matrix4 scaling = Matrix4.scalingMatrix(sx, sy, sz);
            System.out.println("Scaled Vector: " + scaling.multiply(v1));

        } else if (choice == 12) {
            System.out.print("Enter translation (tx ty tz): ");  // translation
            double tx = scanner.nextDouble(), ty = scanner.nextDouble(), tz = scanner.nextDouble();
            Matrix4 translation = Matrix4.translationMatrix(tx, ty, tz);
            System.out.println("Translated Vector: " + translation.multiply(v1));

        } else if (choice == 13) {
            System.out.print("Enter rotation axis (X/Y/Z) and angle (in radians): "); // rotation
            String axis = scanner.next();
            double angle = scanner.nextDouble();
            Matrix4 rotation = null;
            if (axis.equalsIgnoreCase("X")) rotation = Matrix4.rotationX(angle);
            else if (axis.equalsIgnoreCase("Y")) rotation = Matrix4.rotationY(angle);
            else if (axis.equalsIgnoreCase("Z")) rotation = Matrix4.rotationZ(angle);
            System.out.println("Rotated Vector: " + rotation.multiply(v1));

        } else if (choice == 14) {
			System.out.print("Enter left, right, bottom, top, near, far: "); // orth projection
    		double left = scanner.nextDouble();
    		double right = scanner.nextDouble();
    		double bottom = scanner.nextDouble();
    		double top = scanner.nextDouble();
    		double near = scanner.nextDouble();
    		double far = scanner.nextDouble();
    		Matrix4 orthoProjection = Matrix4.orthogonalProjection(left, right, bottom, top, near, far);
    		System.out.println("Projected Vector: " + orthoProjection.multiply(v1));

		} else if (choice == 15) {
			System.out.print("Enter FOV, aspect ratio, near, far: "); // perspective projection
    		double fov = scanner.nextDouble();
    		double aspectRatio = scanner.nextDouble();
   			double near = scanner.nextDouble();
    		double far = scanner.nextDouble();
    		Matrix4 perspectiveProjection = Matrix4.perspectiveProjection(fov, aspectRatio, near, far);
    		System.out.println("Projected Vector: " + perspectiveProjection.multiply(v1));
		}
    }

    // starts a demo for the rotating wireframe using some calculations
	private static void startWireFrameDemo() {
		if (!glfwInit()) {
			throw new IllegalStateException("Unable to initialize GLFW");
		}
	
		// create window
		long window = glfwCreateWindow(800, 600, "Rotating Wireframe Demo", NULL, NULL);
		if (window == NULL) {
			throw new RuntimeException("Failed to create the GLFW window");
		}
	
		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);
	
		GL.createCapabilities();
	
		glEnable(GL_DEPTH_TEST);

		glViewport(0, 0, 800, 600);
	
        // set up perspective frustrum 
		glMatrixMode(GL_PROJECTION);
    	glLoadIdentity();
    	float aspectRatio = 800.0f / 600.0f;
    	float fov = 55.0f;
    	float near = 1.0f, far = 100.0f;
    	glFrustum(-aspectRatio * near, aspectRatio * near, -near, near, near, far);

	
		// define cube vertices
		Vector3[] vertices = new Vector3[]{
			new Vector3(-0.5, -0.5, -0.5),
			new Vector3( 0.5, -0.5, -0.5),
			new Vector3( 0.5,  0.5, -0.5),
			new Vector3(-0.5,  0.5, -0.5),
			new Vector3(-0.5, -0.5,  0.5),
			new Vector3( 0.5, -0.5,  0.5),
			new Vector3( 0.5,  0.5,  0.5),
			new Vector3(-0.5,  0.5,  0.5)
		};
	
		// define edges as vertex indices
		int[][] edges = new int[][]{
			{0, 1}, {1, 2}, {2, 3}, {3, 0},
			{4, 5}, {5, 6}, {6, 7}, {7, 4},
			{0, 4}, {1, 5}, {2, 6}, {3, 7}
		};
	
		// setting up the wireframe
		while (!glfwWindowShouldClose(window)) {
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glLoadIdentity();
	
			// rotate around y
			double angle = glfwGetTime() * 0.5;
			Matrix4 rotationMatrix = Matrix4.rotationY(angle);
	
			// set camera position
			glMatrixMode(GL_MODELVIEW);
			glLoadIdentity();
	
			Matrix4f view = new Matrix4f();
        	view.lookAt(0.0f, .05f, 0.15f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f);
			glLoadMatrixf(view.get(new float[16]));
	
			drawWireframe(vertices, edges, rotationMatrix);

			glfwSwapBuffers(window);
			glfwPollEvents();
		}
	
		glfwDestroyWindow(window);
		glfwTerminate();
	}

    // draws a wireframe model by connecting the vertices for each edge
	private static void drawWireframe(Vector3[] vertices, int[][] edges, Matrix4 rotationMatrix) {
		glBegin(GL_LINES);
	
		for (int[] edge : edges) {

            // get the two edge vertices
			Vector3 v1 = vertices[edge[0]];
			Vector3 v2 = vertices[edge[1]];
	
            // apply rotation and transformation
			v1 = rotationMatrix.multiply(v1);
			v2 = rotationMatrix.multiply(v2);
	
            // draw a straight line between them 
			glVertex3d(v1.x, v1.y, v1.z);
			glVertex3d(v2.x, v2.y, v2.z);
		}
	
		glEnd();
	}
}