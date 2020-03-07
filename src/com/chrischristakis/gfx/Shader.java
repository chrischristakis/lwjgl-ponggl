package com.chrischristakis.gfx;

import static org.lwjgl.opengl.GL20.*;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import com.chrischristakis.utils.ShaderUtils;

public class Shader
{
	
	private int id;
	
	public Shader(String vPath, String fPath)
	{
		id = ShaderUtils.loadShaderFromFile(vPath, fPath);
	}
	
	public int getLocation(String name) //OPTIMIZE USING MAP
	{
		int loc = glGetUniformLocation(id, name);
		if(loc == -1)
			System.err.println("Cannot find uniform with name " + name);
	
		return loc;
	}
	
	public void setMat4f(String name, Matrix4f mat)
	{
		FloatBuffer fb = BufferUtils.createFloatBuffer(16);
		mat.get(fb);
		glUniformMatrix4fv(getLocation(name), false, fb);
	}
	
	public void use()
	{
		glUseProgram(id);
	}

}
