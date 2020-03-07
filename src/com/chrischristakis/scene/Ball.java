package com.chrischristakis.scene;

import java.util.Random;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.chrischristakis.gfx.VAO;
import com.chrischristakis.utils.ShaderUtils;

public class Ball
{
	private float size = 0.03f;
	private VAO mesh;
	
	private Matrix4f modelMat;
	private Vector3f pos;
	
	private float velX = 0.02f, velY = 0.01f;
	
	private Random rand = new Random();
	
	public Ball()
	{
		float[] vertices = {
			-size/2.0f, -size/2.0f, 0.0f,	1.0f, 0.0f, 0.0f,
			-size/2.0f,  size/2.0f, 0.0f,	1.0f, 0.0f, 0.0f,
			 size/2.0f,  size/2.0f, 0.0f,	1.0f, 0.0f, 0.0f,
			 size/2.0f, -size/2.0f, 0.0f, 	1.0f, 0.0f, 0.0f
		};
		
		byte[] indices = {
			0, 1, 2,
			0, 2, 3
		};
		
		mesh = new VAO(vertices, indices);
		modelMat = new Matrix4f();
		pos = new Vector3f(0, 0, 0);
	}
	
	public void render()
	{
		modelMat.identity();
		ShaderUtils.paddle.setMat4f("modelMat", modelMat.translate(pos));
		mesh.render();
	}
	
	//TODO: Make sure the ball only randomizes it's position when bouncing off a paddle, not a wall.
	
	float min = 0.01f, max = 0.02f;
	public void update()
	{
		if(pos.x - size/2.0f < -1f)
		{
			pos.x = -1f + size/2.0f;
			velX = min + rand.nextFloat()*(max - min);
		}
		if(pos.x + size/2.0f > 1f)
		{
			pos.x = 1f - size/2.0f;
			velX = -(min + rand.nextFloat()*(max - min));
		}
		if(pos.y - size/2.0f < -1f)
		{
			pos.y = -1f + size/2.0f;
			velY = min + rand.nextFloat()*(max - min)*2;
		}
		if(pos.y + size/2.0f > 1f)
		{
			pos.y = 1f - size/2.0f;
			velY = -(min + rand.nextFloat()*(max - min)*2);
		}
		
		pos.y += velY;
		pos.x += velX;
	}

}
