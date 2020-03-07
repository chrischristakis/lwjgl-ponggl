package com.chrischristakis;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import com.chrischristakis.input.KeyInput;
import com.chrischristakis.scene.Scene;
import com.chrischristakis.utils.ShaderUtils;

public class Main implements Runnable
{
	private long window;
	public static final int WIDTH = 1000, HEIGHT = 800;
	public static boolean running = false;
	private Thread thread;
	
	private Scene scene;
	
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
		//glfwSwapInterval(1);
		
		System.out.println("LWJGL version: " + Version.getVersion());
		System.out.println("OpenGL version: " + glGetString(GL_VERSION));
		
		ShaderUtils.init();
		ShaderUtils.paddle.use();
		
		scene = new Scene();
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
		//glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
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
		scene.update();
	}
	
	private void render()
	{
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	
		scene.render();
		
		glfwSwapBuffers(window);
	}
	
	public static void main(String[] args) 
	{
		new Main().start();
	}
	
}
