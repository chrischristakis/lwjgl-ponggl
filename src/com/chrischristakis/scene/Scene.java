package com.chrischristakis.scene;

import static org.lwjgl.glfw.GLFW.*;

import org.joml.Matrix4f;

import com.chrischristakis.gfx.VAO;
import com.chrischristakis.utils.ShaderUtils;

public class Scene
{
	
	private VAO mesh; //Background line.
	private Paddle p1, p2;
	private Ball ball;
	
	public Scene()
	{
		float[] vertices = {
			-0.001f, 1.0f, 0.0f, 	1.0f, 1.0f, 1.0f,
			 0.001f, 1.0f, 0.0f,	1.0f, 1.0f, 1.0f,
			 0.001f,-1.0f, 0.0f,	1.0f, 1.0f, 1.0f,
			-0.001f,-1.0f, 0.0f,	1.0f, 1.0f, 1.0f
		};
		byte[] indices = {
				0, 1, 2,
				0, 3, 2
		};
		mesh = new VAO(vertices, indices);
		
		p1 = new Paddle(-0.9f, 0.0f, GLFW_KEY_W, GLFW_KEY_S);
		p2 = new Paddle(0.9f, 0.0f, GLFW_KEY_UP, GLFW_KEY_DOWN);
		ball = new Ball();
	}
	
	public void render()
	{
		ShaderUtils.paddle.setMat4f("modelMat", new Matrix4f());
		mesh.render();
		p1.render();
		p2.render();
		ball.render();
	}
	
	public void update()
	{
		p1.update();
		p2.update();
		ball.update();
	}

}
