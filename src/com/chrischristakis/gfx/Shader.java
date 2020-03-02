package com.chrischristakis.gfx;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import com.chrischristakis.utils.FileUtils;

public class Shader
{
	
	private int programID;
	
	public Shader(String vPath, String fPath)
	{
		programID = loadShaderFromFile(vPath, fPath);
	}
	
	private int loadShaderFromFile(String vPath, String fPath)
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
	
	public int getProgram()
	{
		return programID;
	}
	
	public void use()
	{
		glUseProgram(programID);
	}

}
