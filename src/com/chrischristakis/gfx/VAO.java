package com.chrischristakis.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.chrischristakis.utils.BufferUtils;

public class VAO
{
	
	private int vao, vbo, ebo;
	private int numOfVerts;
	
	public VAO(float[] vertData, byte indices[]) //POSITION(3) | COLOR(3)
	{
		numOfVerts = indices.length;
		vbo = glGenBuffers();
		ebo = glGenBuffers();
		vao = glGenVertexArrays();
		
		glBindVertexArray(vao);
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, BufferUtils.createFloatBuffer(vertData), GL_STATIC_DRAW);
		//Pos
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 6 * 4, 0); //change
		glEnableVertexAttribArray(0);
		//Col
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 6 * 4, 3 * 4); //change 
		glEnableVertexAttribArray(1);
		
		//EBO
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, BufferUtils.createByteBuffer(indices), GL_STATIC_DRAW);
		
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0); //NOT binded to the VAO!!
		glBindVertexArray(0);
	}
	
	public void bind()
	{
		glBindVertexArray(vao);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
	}
	
	public void unbind()
	{
		glBindVertexArray(0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	public void draw()
	{
		glDrawElements(GL_TRIANGLES, numOfVerts, GL_UNSIGNED_BYTE, 0);
	}
	
	public void render()
	{
		bind();
		draw();
		unbind();
	}

}
