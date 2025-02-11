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

import static org.lwjgl.opengl.GL.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_MULTISAMPLE;

public class LinesPolygonObjects {

    // users chosen shape, list of control points
    private long window;
    private int shapeChoice;
    private List<float[]> points = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private float cameraAngle = 0.0f;
    
    public void run() {
        getUserInput();

        // terminates if option 5
        if (shapeChoice == 5) {
            System.out.println("Exiting the program.");
            System.exit(0);
        }

        // gets the points from the user, initialises and runs the program then resets
        getPointsInput();
        init();
        loop();
        cleanup();
    }
    
    // gets users selection
    private void getUserInput() {
        System.out.println("Choose something to draw, all should be in [x y z] format:");
        System.out.println("1. Straight Line");
        System.out.println("2. Bézier Curve");
        System.out.println("3. B-Spline");
        System.out.println("4. Polygon");
        System.out.println("5. Exit");
        System.out.print("Enter choice: ");
        shapeChoice = scanner.nextInt();
    }
    
    // gets users points based on the shape selected
    private void getPointsInput() {
        int numPoints = switch (shapeChoice) {
            case 1 -> 2;        // straight line 2 points
            case 2, 3 -> 4;     // bezier and bspline require 4
            case 4 -> {         // polygons require at least 3
                System.out.print("Enter number of polygon points (min 3): ");
                yield scanner.nextInt();
            }
            default -> 0;
        };
        
        if (shapeChoice == 4 && numPoints < 3) {
            System.out.println("A polygon requires at least 3 points.");
            System.exit(1);
        }
        
        for (int i = 0; i < numPoints; i++) {
            System.out.print("Enter point " + (i + 1) + " (x y z): ");
            float x = scanner.nextFloat();
            float y = scanner.nextFloat();
            float z = scanner.nextFloat();
            points.add(new float[]{x, y, z});
        }
    }
    
    private void init() {

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 2);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);

        glfwWindowHint(GLFW_SAMPLES, 4);
        
  
        window = glfwCreateWindow(800, 600, "Lines, Polygons and Objects", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    
        glfwSwapInterval(1);
        glfwShowWindow(window);
        
        glEnable(GL_MULTISAMPLE);

        glEnable(GL_DEPTH_TEST);
        glDepthFunc(GL_LEQUAL);
        
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        float aspect = 800.0f / 600.0f;
        glFrustum(-aspect, aspect, -1, 1, 1, 100);
        glMatrixMode(GL_MODELVIEW);
    }
    
    // clears screen, draws shape
    private void loop() {
        while (!glfwWindowShouldClose(window)) {
            // dark gray
            glClearColor(0.1f, 0.1f, 0.1f, 1.0f);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            // un comment if user requires camera movement
            //updateCamera();
            
            renderShape();
            
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    // rotates camera angle based on arrow keys
    private void updateCamera() {
        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
            cameraAngle -= 2.0f;
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            cameraAngle += 2.0f;
        }
        

    }


    // renders selected shape
    private void renderShape() {

        // if no user input do nothing
        if (points.isEmpty()) return;
        
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glTranslatef(0.0f, 0.0f, -5.0f);
        glRotatef(cameraAngle, 0.0f, 1.0f, 0.0f);

        glColor3f(1.0f, 1.0f, 1.0f); // white
        
        // if shape choice is line draw straight line between points
        if (shapeChoice == 1) {
            glBegin(GL_LINES);
            glVertex3f(points.get(0)[0], points.get(0)[1], points.get(0)[2]);
            glVertex3f(points.get(1)[0], points.get(1)[1], points.get(1)[2]);
            glEnd();
        } 
        // draw a bezier curve
        else if (shapeChoice == 2) {
            glBegin(GL_LINE_STRIP);
            for (float t = 0; t <= 1; t += 0.05) {
                float[] p = bezierCurve(t, points);
                glVertex3f(p[0], p[1], p[2]);
            }
            glEnd();
        } 
        // draw a b spline curve
        else if (shapeChoice == 3) {
            glBegin(GL_LINE_STRIP);
            for (float t = 0; t <= 1; t += 0.05) {
                float[] p = bSplineCurve(t, points);
                glVertex3f(p[0], p[1], p[2]);
            }
            glEnd();
        } 
        // draw a polygon with as many points as the user wants
        else if (shapeChoice == 4) {
            if (points.size() < 3) return; // checking >3 points
            
            glBegin(GL_LINE_LOOP);
            for (float[] p : points) {
                glVertex3f(p[0], p[1], p[2]);
            }
            glEnd();
        }
    }

    // evalutates a point on a bezier curve using bernstien polynomials
    private float[] bezierCurve(float t, List<float[]> ctrlPoints) {
        float x = 0, y = 0, z = 0;
        int n = ctrlPoints.size() - 1;
        for (int i = 0; i <= n; i++) {
            float basis = bernstein(n, i, t);
            x += basis * ctrlPoints.get(i)[0];
            y += basis * ctrlPoints.get(i)[1];
            z += basis * ctrlPoints.get(i)[2];
        }
        return new float[]{x, y, z};
    }
    
    // evaluates a point on a b spline curve using de boor algorithm
    private float[] bSplineCurve(float t, List<float[]> ctrlPoints) {
        int n = ctrlPoints.size() - 1;
        // if possible use degree 3
        int degree = (ctrlPoints.size() - 1 >= 3) ? 3 : ctrlPoints.size() - 1;
        float[] knots = generateKnotVector(n, degree);
        return deBoor(t, ctrlPoints, degree, knots);
    }
    
    // generates a clamped knot vector for b spline
    private float[] generateKnotVector(int n, int degree) {
        int m = n + degree + 2; // total knots number
        float[] knots = new float[m];

        // set first knots to 0 and last to 1, distributing rest normally
        for (int i = 0; i <= degree; i++) {
            knots[i] = 0.0f;
        }

        for (int i = m - degree - 1; i < m; i++) {
            knots[i] = 1.0f;
        }

        int numInternal = m - 2 * (degree + 1);
        for (int i = 1; i <= numInternal; i++) {
            knots[degree + i] = (float) i / (numInternal + 1);
        }
        return knots;
    }

    // de boors algorithm for b splines
    private float[] deBoor(float t, List<float[]> ctrlPoints, int degree, float[] knots) {
        int n = ctrlPoints.size() - 1;
        int k; // knot index
        

        // if t is last, choose the last span, else find k
        if (t == knots[knots.length - 1]) {
            k = n;
        } else {

            for (k = degree; k <= n; k++) {
                if (t < knots[k + 1]) {
                    break;
                }
            }
        }
        
        // save to array d
        float[][] d = new float[degree + 1][3];
        for (int j = 0; j <= degree; j++) {
            d[j] = ctrlPoints.get(k - degree + j).clone();
        }
        
        // de boor iterations
        for (int r = 1; r <= degree; r++) {
            for (int j = degree; j >= r; j--) {
                int index = k - degree + j;
                float denominator = knots[index + degree - r + 1] - knots[index];
                float alpha = (denominator != 0) ? (t - knots[index]) / denominator : 0;
                for (int i = 0; i < 3; i++) {
                    d[j][i] = (1 - alpha) * d[j - 1][i] + alpha * d[j][i];
                }
            }
        }

        return d[degree];
    }

    // bernstein polynomials for bezier curves
    private float bernstein(int n, int i, float t) {
        return (float) (factorial(n) / (double)(factorial(i) * factorial(n - i)) * java.lang.Math.pow(t, i) * java.lang.Math.pow(1 - t, n - i));
    }
    
    // factorial of a number
    private int factorial(int num) {
        if (num < 0) return 1;
        if (num == 0 || num == 1) return 1;
        int result = 1;
        for (int i = 2; i <= num; i++) {
            if (result > Integer.MAX_VALUE / i) return Integer.MAX_VALUE; // preventing overflow
            result *= i;
        }
        return result;
    }
    
    private void cleanup() {
        glfwDestroyWindow(window);
        glfwTerminate();
    }
    
     // repeats until the user picks option 5
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            LinesPolygonObjects demo = new LinesPolygonObjects();
            demo.run(); // closes when the window is closed
            System.out.println("Window closed, returning to main menu.\n");
        }
    }
}
