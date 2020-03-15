package com.chrischristakis.scene;

import java.util.Random;

import org.joml.Matrix4f;

import com.chrischristakis.gfx.VAO;
import com.chrischristakis.utils.ShaderUtils;

public class Ball extends Entity
{
	protected static final float size = 0.03f, TERMINAL_VELX = 0.040f;
	private VAO mesh;
	
	private Matrix4f modelMat;
	
	Random rand = new Random();
	protected float velX = (rand.nextBoolean()? -1 : 1) * 0.013f, velY = 0.00f;
	
	public Ball()
	{
		super(0, 0, size, size);
		float[] vertices = {
			-width/2.0f, -size/2.0f, 0.0f,	1.0f, 0.0f, 0.0f,
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
	}
	
	public void render()
	{
		modelMat.identity();
		ShaderUtils.paddle.setMat4f("modelMat", modelMat.translate(position));
		mesh.render();
	}
	
	//TODO: Make sure the ball only randomizes it's position when bouncing off a paddle, not a wall.
	public void update()
	{
		if(position.x - size/2.0f < -1f)
		{
			position.x = -1f + size/2.0f;
			velX = Math.abs(velX);
		}
		if(position.x + size/2.0f > 1f)
		{
			position.x = 1f - size/2.0f;
			velX = -Math.abs(velX);
		}
		if(position.y - size/2.0f < -1f)
		{
			position.y = -1f + size/2.0f;
			velY = Math.abs(velY);
		}
		if(position.y + size/2.0f > 1f)
		{
			position.y = 1f - size/2.0f;
			velY = -Math.abs(velY);
		}
		
		position.y += velY;
		position.x += velX;
	}

}
