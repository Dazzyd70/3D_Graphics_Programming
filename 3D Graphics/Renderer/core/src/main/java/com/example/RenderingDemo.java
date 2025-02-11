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

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_COLOR_ARRAY;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_COMPONENT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_NEAREST;
import static org.lwjgl.opengl.GL11.GL_NONE;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_POLYGON_OFFSET_FILL;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_BORDER_COLOR;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MAG_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_MIN_FILTER;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_S;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_WRAP_T;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.GL_VERTEX_ARRAY;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glColorPointer;
import static org.lwjgl.opengl.GL11.glDeleteTextures;
import static org.lwjgl.opengl.GL11.glDepthMask;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawBuffer;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnableClientState;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glFrustum;
import static org.lwjgl.opengl.GL11.glGenTextures;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glLoadMatrixf;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.glPolygonOffset;
import static org.lwjgl.opengl.GL11.glReadBuffer;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTexImage2D;
import static org.lwjgl.opengl.GL11.glTexParameterfv;
import static org.lwjgl.opengl.GL11.glTexParameteri;
import static org.lwjgl.opengl.GL11.glTranslated;
import static org.lwjgl.opengl.GL11.glVertex3d;
import static org.lwjgl.opengl.GL11.glVertexPointer;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.GL_TEXTURE1;
import static org.lwjgl.opengl.GL13.GL_TEXTURE2;
import static org.lwjgl.opengl.GL13.GL_TEXTURE_CUBE_MAP;
import static org.lwjgl.opengl.GL13.glActiveTexture;
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
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.*;

public class RenderingDemo {

    // window handle and sizes
    private long window;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;

    // main and shadow mapping shaders 
    private int shaderProgram;
    private int depthShaderProgram;

    // meshs
    private Sphere texturedSphere;
    private Sphere reflectiveSphere;
    private Sphere transparentSphere;
    private Wall wall;

    // shadow variables
    private int shadowFBO;
    private int shadowMap;
    private final int SHADOW_WIDTH = 1024;
    private final int SHADOW_HEIGHT = 1024;

    // light matrix for shadow mapping and first light position 
    private Matrix4f lightSpaceMatrix;
    private Vector3f lightPos = new Vector3f(5.0f, 10.0f, 5.0f);

    // array of light positions and index to track
    private Vector3f[] lightPositions;
    private int currentLightIndex = 0;


    // camera and projection matrices
    private Matrix4f projection;
    private Matrix4f view;
    private Vector3f cameraPos = new Vector3f(0.0f, 3.0f, 8.0f);

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // setup error callbacks
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // glfw configs
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // create window
        window = glfwCreateWindow(WIDTH, HEIGHT, "3D Rendering Demo", NULL, NULL);
        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // allows closing on escape press
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        // change light positions on left click
        glfwSetMouseButtonCallback(window, (window, button, action, mods) -> {
            if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
                // cycle through
                currentLightIndex = (currentLightIndex + 1) % lightPositions.length;
                // update global light position
                lightPos.set(lightPositions[currentLightIndex]);
                System.out.println("Light position changed to: " + lightPos);
            }
        });

        // centres window
        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1);
            IntBuffer pHeight = stack.mallocInt(1);
            glfwGetWindowSize(window, pWidth, pHeight);
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2);
        }


        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();

        // enable depth testing and blending for the transparency
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        // init array of light positions
        lightPositions = new Vector3f[] {
            new Vector3f(5.0f, 10.0f, 5.0f),
            new Vector3f(-5.0f, 10.0f, 5.0f),
            new Vector3f(0f, -5.0f, 5.0f)  
        };
        // starts at 0
        currentLightIndex = 0;
        lightPos.set(lightPositions[currentLightIndex]);
    }

    private void loop() {
        // init shaders meshes and maps
        initShaders();
        initMeshes();
        initShadowMapping();

        // angle for the rotation
        float angle = 0.0f;
        
        // main rendering loop
        while (!glfwWindowShouldClose(window)) {
            // first render from the perspective of the light for shadows
            renderShadowMap();

            // next render normally using the shadow map
            renderScene(angle);

            glfwSwapBuffers(window);
            glfwPollEvents();
            
            // increments the spheres rotation
            angle += 0.5f;
        }
        cleanup();
    }

    // takes our main, and depth vertex and fragment shaders from files in this directory and loads and compiles them
    private void initShaders() {
        // loads main shaders
        int vertexShader = ShaderUtils.loadShader(GL_VERTEX_SHADER, "core\\src\\main\\java\\com\\example\\vertexShader.glsl");
        int fragmentShader = ShaderUtils.loadShader(GL_FRAGMENT_SHADER, "core\\src\\main\\java\\com\\example\\fragmentShader.glsl");
        // creates the main shader
        shaderProgram = ShaderUtils.createProgram(vertexShader, fragmentShader);

        // loads depth shaders 
        int depthVertexShader = ShaderUtils.loadShader(GL_VERTEX_SHADER, "core\\src\\main\\java\\com\\example\\depthVertexShader.glsl");
        int depthFragmentShader = ShaderUtils.loadShader(GL_FRAGMENT_SHADER, "core\\src\\main\\java\\com\\example\\depthFragmentShader.glsl");
        // creates main shaders
        depthShaderProgram = ShaderUtils.createProgram(depthVertexShader, depthFragmentShader);
    }

    private void initMeshes() {
        // creates spheres with different material types
        // in order it 0-3, stone textured, reflective, transparent
        texturedSphere = new Sphere(1.0f, 20, 20, 0);
        reflectiveSphere = new Sphere(1.0f, 20, 20, 1);
        transparentSphere = new Sphere(1.0f, 20, 20, 2);

        // load the stone texture from the resources folder
        texturedSphere.textureId = TextureUtils.loadTexture("core\\src\\main\\java\\com\\example\\resources\\stone.png");
        
        // would load the reflective cubemap if i had one

        // create the blank wall to take the shadows
        wall = new Wall();
    }

    private void initShadowMapping() {
        
        shadowFBO = glGenFramebuffers();
        
        // create a depth texture to store shadow maps
        shadowMap = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, shadowMap);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_DEPTH_COMPONENT, SHADOW_WIDTH, SHADOW_HEIGHT, 0,
                GL_DEPTH_COMPONENT, GL_FLOAT, (ByteBuffer) null);
        // set texture params
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);
        float[] borderColor = { 1.0f, 1.0f, 1.0f, 1.0f };
        glTexParameterfv(GL_TEXTURE_2D, GL_TEXTURE_BORDER_COLOR, borderColor);

        // attach the depth texture to the framebuffer
        glBindFramebuffer(GL_FRAMEBUFFER, shadowFBO);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_DEPTH_ATTACHMENT, GL_TEXTURE_2D, shadowMap, 0);
        glDrawBuffer(GL_NONE);
        glReadBuffer(GL_NONE);
        if (glCheckFramebufferStatus(GL_FRAMEBUFFER) != GL_FRAMEBUFFER_COMPLETE)
            System.err.println("Shadow Framebuffer not complete!");
        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    private void renderShadowMap() {
        // set the viewport to match shadowmap
        glViewport(0, 0, SHADOW_WIDTH, SHADOW_HEIGHT);
        glBindFramebuffer(GL_FRAMEBUFFER, shadowFBO);
        glClear(GL_DEPTH_BUFFER_BIT);

        // using the depth program
        glUseProgram(depthShaderProgram);

        // set up the light projection and view matrix
        Matrix4f lightProjection = new Matrix4f().ortho(-10, 10, -10, 10, 1.0f, 20.0f);
        Matrix4f lightView = new Matrix4f().lookAt(lightPos, new Vector3f(0.0f, 0.0f, 0.0f), new Vector3f(0, 1, 0));
        lightSpaceMatrix = new Matrix4f();
        lightProjection.mul(lightView, lightSpaceMatrix);

        int lightSpaceLoc = glGetUniformLocation(depthShaderProgram, "lightSpaceMatrix");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            glUniformMatrix4fv(lightSpaceLoc, false, lightSpaceMatrix.get(fb));
        }

        // render spheres and set scales and specific positons, then the same with the wall
        renderObjectDepth(texturedSphere, new Vector3f(-3, 0, 0), 0.75f);
        renderObjectDepth(reflectiveSphere, new Vector3f(0, 0, 0), 1.00f);
        renderObjectDepth(transparentSphere, new Vector3f(3, 0, 0), 1.25f);

        renderObjectDepth(wall, new Vector3f(0, 0, -3), 5.00f);

        glBindFramebuffer(GL_FRAMEBUFFER, 0);
    }

    private void renderObjectDepth(Object mesh, Vector3f position, float scale) {

        // creates the matrix respecting translations
        Matrix4f model = new Matrix4f().translate(position);
        model.scale(scale);

        // passes models matrix into the depth shader
        int modelLoc = glGetUniformLocation(depthShaderProgram, "model");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            glUniformMatrix4fv(modelLoc, false, model.get(fb));
        }
        
        // if object isnt a sphere, turn off self shadowing, else render normally
        if (mesh instanceof Wall) {
            glEnable(GL_POLYGON_OFFSET_FILL);
            glPolygonOffset(2.0f, 4.0f);
            ((Wall) mesh).render();
            glDisable(GL_POLYGON_OFFSET_FILL);

        } else if (mesh instanceof Sphere) {
            ((Sphere) mesh).render();
        }
    }

    private void renderScene(float angle) {

        // reset viewports dimensions
        glViewport(0, 0, WIDTH, HEIGHT);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        // back to using main shader program
        glUseProgram(shaderProgram);

        // set the perspective projection matrix up
        projection = new Matrix4f().perspective((float) Math.toRadians(45.0f), (float) WIDTH / HEIGHT, 0.1f, 100.0f);
        view = new Matrix4f().lookAt(cameraPos, new Vector3f(0, 0, 0), new Vector3f(0, 1, 0));

        // pass them into the shader
        int projLoc = glGetUniformLocation(shaderProgram, "projection");
        int viewLoc = glGetUniformLocation(shaderProgram, "view");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            glUniformMatrix4fv(projLoc, false, projection.get(fb));
            glUniformMatrix4fv(viewLoc, false, view.get(fb));
        }

        // set light and view pos in the shader and pass the lightspace matrix in 
        glUniform3f(glGetUniformLocation(shaderProgram, "lightPos"), lightPos.x, lightPos.y, lightPos.z);
        glUniform3f(glGetUniformLocation(shaderProgram, "viewPos"), cameraPos.x, cameraPos.y, cameraPos.z);

        int lightSpaceLoc = glGetUniformLocation(shaderProgram, "lightSpaceMatrix");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            glUniformMatrix4fv(lightSpaceLoc, false, lightSpaceMatrix.get(fb));
        }

        glActiveTexture(GL_TEXTURE2);
        glBindTexture(GL_TEXTURE_2D, shadowMap);
        glUniform1i(glGetUniformLocation(shaderProgram, "shadowMap"), 2);

        // render the spheres with individual transforms 
        renderObject(texturedSphere, new Vector3f(-3, 0, 0), angle, 0.75f);
        renderObject(reflectiveSphere, new Vector3f(0, 0, 0), angle, 1.00f);

        // render the wall with transforms
        renderObject(wall, new Vector3f(0, 0, -3), 0, 5.00f);

        // turn off depth masking for transparent sphere so it doesnt block the wall behind it
        glDepthMask(false);
        renderObject(transparentSphere, new Vector3f(3, 0, 0), angle, 1.25f);
        glDepthMask(true);
    }

    private void renderObject(Object mesh, Vector3f position, float angle, float scale) {
        
        Matrix4f model = new Matrix4f().translate(position);

        // if sphere, apply rotation over time
        if (mesh instanceof Sphere) {
            model.rotateY((float) Math.toRadians(angle));
        }

        // apply scaling
        model.scale(scale);

        // pass into main shader
        int modelLoc = glGetUniformLocation(shaderProgram, "model");
        try (MemoryStack stack = MemoryStack.stackPush()) {
            FloatBuffer fb = stack.mallocFloat(16);
            glUniformMatrix4fv(modelLoc, false, model.get(fb));
        }

        // set material
        int materialType = 0;
        if (mesh instanceof Sphere) {
            materialType = ((Sphere) mesh).materialType;
        } else if (mesh instanceof Wall) {
            materialType = ((Wall) mesh).materialType;
        }
        glUniform1i(glGetUniformLocation(shaderProgram, "materialType"), materialType);

        // if it is a transparent sphere disable face culling
        if (mesh instanceof Sphere && ((Sphere) mesh).materialType == 2) {
            glDisable(GL_CULL_FACE);
        }

        // if using a texture or cube map, bind it
        if (mesh instanceof Sphere) {
            Sphere s = (Sphere) mesh;
            if (s.materialType == 0) {
                glActiveTexture(GL_TEXTURE0);
                glBindTexture(GL_TEXTURE_2D, s.textureId);
                glUniform1i(glGetUniformLocation(shaderProgram, "texture1"), 0);
            } else if (s.materialType == 1) {
                glActiveTexture(GL_TEXTURE1);
                glBindTexture(GL_TEXTURE_CUBE_MAP, s.textureId);
                glUniform1i(glGetUniformLocation(shaderProgram, "skybox"), 1);
            }
        }

        // finally render them
        if (mesh instanceof Sphere) {
            ((Sphere) mesh).render();
        } else if (mesh instanceof Wall) {
            ((Wall) mesh).render();
        }
    }

    private void cleanup() {

        // delete shaders, framebuffer and textures

        glDeleteProgram(shaderProgram);
        glDeleteProgram(depthShaderProgram);

        glDeleteFramebuffers(shadowFBO);
        glDeleteTextures(shadowMap);

    }

    public static void main(String[] args) {
        new RenderingDemo().run();
    }
}
