package com.chrischristakis.scene;

import com.chrischristakis.gfx.VAO;
import com.chrischristakis.input.KeyInput;
import com.chrischristakis.utils.ShaderUtils;

import org.joml.*;

public class Paddle
{

	private VAO mesh;
	
	private Vector3f pos; //Position vector
	private Matrix4f modelMat;
	private float width = 0.02f, height = 0.24f;
	
	private int upIn, downIn;
	
	public Paddle(float x, float y, int upIn, int downIn)
	{
		//Key input variables.
		this.upIn = upIn;
		this.downIn = downIn;
		
		float vertices[] = {
			//POS								//COL
			-width/2.0f, -height/2.0f, 0.0f,	1.0f, 1.0f, 1.0f, //BL
			-width/2.0f,  height/2.0f, 0.0f,	1.0f, 1.0f, 1.0f, //TL
			 width/2.0f, -height/2.0f, 0.0f,	1.0f, 1.0f, 1.0f, //BR
			 width/2.0f,  height/2.0f, 0.0f, 	1.0f, 1.0f, 1.0f  //TR
		};
		byte indices[] = {
			0, 2, 3,
			0, 1, 3
		};
		
		mesh = new VAO(vertices, indices);
		pos = new Vector3f(x, y, 1.0f);
		
		modelMat = new Matrix4f();
		ShaderUtils.paddle.setMat4f("modelMat", modelMat.translate(pos));
	}
	
	public void render()
	{
		modelMat.identity();
		ShaderUtils.paddle.setMat4f("modelMat", modelMat.translate(pos));
		mesh.render();
	}
	
	public void update()
	{
		if(KeyInput.isPressed(upIn))
			pos.y += 0.01f;
		if(KeyInput.isPressed(downIn))
			pos.y -= 0.01f;
		
		//BOUNDS
		if(pos.y + height/2.0f > 1.0f)
			pos.y = 1.0f - height/2.0f;
		if(pos.y - height/2.0f < -1.0f)
			pos.y = -1.0f + height/2.0f;
	}

}
