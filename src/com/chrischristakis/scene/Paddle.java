package com.chrischristakis.scene;

import com.chrischristakis.gfx.VAO;
import com.chrischristakis.input.KeyInput;
import com.chrischristakis.utils.ShaderUtils;

import org.joml.*;

public class Paddle extends Entity
{

	private VAO mesh;
	
	private Matrix4f modelMat;
	private float speed = 0.027f;
	
	private int upIn, downIn;
	
	public Paddle(float x, float y, int upIn, int downIn)
	{
		super(x, y, 0.02f, 0.24f);
		//test comment
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
		
		modelMat = new Matrix4f();
	}
	
	public void render()
	{
		modelMat.identity();
		ShaderUtils.paddle.setMat4f("modelMat", modelMat.translate(position));
		mesh.render();
	}
	
	public void update()
	{
		if(KeyInput.isPressed(upIn))
			position.y += speed;
		if(KeyInput.isPressed(downIn))
			position.y -= speed;
		
		//BOUNDS
		if(position.y + height/2.0f > 1.0f)
			position.y = 1.0f - height/2.0f;
		if(position.y - height/2.0f < -1.0f)
			position.y = -1.0f + height/2.0f;
	}

}
