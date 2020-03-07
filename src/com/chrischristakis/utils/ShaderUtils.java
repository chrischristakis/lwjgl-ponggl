package com.chrischristakis.utils;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import com.chrischristakis.gfx.Shader;

public class ShaderUtils 
{
	
	public static Shader paddle;
	
	private ShaderUtils() {}
	
	public static void init()
	{
		paddle = new Shader("shaders/shader.vert", "shaders/shader.frag");
	}
	
	public static int loadShaderFromFile(String vPath, String fPath)
	{
		//VERT
		int vertexShader = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShader, FileUtils.readFileAsString(vPath));
		glCompileShader(vertexShader);
		if(glGetShaderi(vertexShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			System.err.println("ERROR::SHADER::VERTEX::COMPILATION_FAILED");
			System.err.println(glGetShaderInfoLog(vertexShader));
			return 0;
		}
		
		//FRAG
		int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShader, FileUtils.readFileAsString(fPath));
		glCompileShader(fragmentShader);
		if(glGetShaderi(fragmentShader, GL_COMPILE_STATUS) == GL_FALSE)
		{
			System.err.println("ERROR::SHADER::FRAGMENT::COMPILATION_FAILED");
			System.err.println(glGetShaderInfoLog(fragmentShader));
			return 0;
		}
		
		//PROGRAM
		int result = glCreateProgram();
		glAttachShader(result, vertexShader);
		glAttachShader(result, fragmentShader);
		glValidateProgram(result);
		glLinkProgram(result);
		
		glDeleteShader(vertexShader);
		glDeleteShader(fragmentShader);
		
		return result;
	}

}
