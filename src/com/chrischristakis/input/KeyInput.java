package com.chrischristakis.input;

import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.glfw.GLFWKeyCallback;

import com.chrischristakis.Main;

public class KeyInput extends GLFWKeyCallback
{
	public static boolean[] keys = new boolean[65536];
	
	@Override
	public void invoke(long window, int key, int scancode, int action, int mods) 
	{
		keys[key] = (action != GLFW_RELEASE);
		if(key == GLFW_KEY_ESCAPE && keys[key])
			Main.running = false;
	}
	
	public static boolean isPressed(int key)
	{
		return keys[key];
	}

}
