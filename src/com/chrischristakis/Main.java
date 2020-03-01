package com.chrischristakis;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL30;

import com.chrischristakis.input.KeyInput;
import com.chrischristakis.utils.BufferUtils;
import com.chrischristakis.utils.FileUtils;

public class Main implements Runnable
{
	private long window;
	public static final int WIDTH = 1100, HEIGHT = 800;
	public static boolean running = false;
	private Thread thread;
	
	public void start()
	{
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void init()
	{
		if(!glfwInit())
		{
			System.err.println("Failed to init GLFW.");
			return;
		}
		
		glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
		window = glfwCreateWindow(WIDTH, HEIGHT, "PongGL", NULL, NULL);
		if(window == NULL)
		{
			System.err.println("Failed to create window.");
			return;
		}
		
		glfwSetKeyCallback(window, new KeyInput());
		
		GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
		glfwSetWindowPos(window, (vidMode.width() - WIDTH)/2, (vidMode.height() - HEIGHT)/2);
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		GL.createCapabilities(); //IMPORTANT, OPENS THE OPENGL BINDINGS
		glViewport(0, 0, WIDTH, HEIGHT);
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		//glfwSwapInterval(1); vsync
		
		System.out.println("LWJGL version: " + Version.getVersion());
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
		
		test();
	}
	
	private void test()
	{
		//SHADERS
		//---------------------------------
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, FileUtils.readFileAsString("shaders/shader.vert"));
		glCompileShader(vertexShader);
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			System.err.println("ERROR::SHADER::VERTEX::COMPILATION_FAILED");
			System.err.println(glGetShaderInfoLog(vertexShader));
			return;
		}
		
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, FileUtils.readFileAsString("shaders/shader.frag"));
		glCompileShader(fragmentShader);
		if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			System.err.println("ERROR::SHADER::FRAGMENT::COMPILATION_FAILED");
			System.err.println(glGetShaderInfoLog(fragmentShader));
			return;
		}
		
		int program = glCreateProgram();
		glAttachShader(program, vertexShader);
		glAttachShader(program, fragmentShader);
		glValidateProgram(program);
		glLinkProgram(program);
		
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		//---------------------------------
		
		//VERTEXARRAY STUFF
		//---------------------------------
		
		float vertices[] = {
			//POS					//COL
			-0.5f, -0.5f, 0.0f, 	1.0f, 0.0f, 0.0f,
			 0.5f, -0.5f, 0.0f,		1.0f, 0.0f, 0.0f,
			 0.0f,  0.5f, 0.0f, 	0.0f, 0.0f, 1.0f
		};
		
		int VBO, VAO;
		VBO = glGenBuffers();
		VAO = glGenVertexArrays();
		
		glBindVertexArray(VAO);
		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatArray(vertices), GL_STATIC_DRAW);
		//Pos
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0);
		glEnableVertexAttribArray(0);
		//Col
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3 * 4);
		glEnableVertexAttribArray(1);
		
		glUseProgram(program);
		//---------------------------------
	}
	
	public void run()
	{
		init(); 
		
		final float UPS = 60.0f;
		double nsInterval = 1e9/UPS; //How long between updates in nanoseconds
		double delta = 0;
		long last = System.nanoTime();
		long now;
		
		long timer = System.currentTimeMillis();
		int frames = 0;
		int updates = 0;
		while(running && !glfwWindowShouldClose(window))
		{
			now = System.nanoTime();
			delta += (now - last) / nsInterval;
			last = now;
			
			if(delta >= 1)
			{
				update();
				delta--;
				updates++;
			}
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer >= 1000)
			{
				System.out.printf("UPS: %d | FPS: %d\n", updates, frames);
				frames = 0;
				updates = 0;
				timer = System.currentTimeMillis();
			}
		}
		running = false;
		
		//Clean up
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);
		glfwTerminate();
	}
	
	private void update()
	{
		glfwPollEvents();
	}
	
	private void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glDrawArrays(GL_TRIANGLES, 0, 3);
		
		glfwSwapBuffers(window);
	}
	
	public static void main(String[] args) 
	{
		new Main().start();
	}
	
}
