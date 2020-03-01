package com.chrischristakis.utils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class BufferUtils 
{
	private BufferUtils() {}

	public static FloatBuffer createFloatArray(float[] arr)
	{
		FloatBuffer result = ByteBuffer.allocateDirect(arr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
		result.put(arr);
		result.position(0);
		return result;
	}
	
}
