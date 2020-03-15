package com.chrischristakis.scene;

import org.joml.Vector3f;

public abstract class Entity
{
	
	protected float width, height;
	protected Vector3f position;
	
	public Entity(float x, float y, float width, float height)
	{
		position = new Vector3f();
		position.x = x;
		position.y = y;
		position.z = 0.0f;
		this.width = width;
		this.height = height;
	}
	
	public boolean collides(Entity other)
	{
		float oLeft = other.position.x - other.width/2.0f;
		float oRight = other.position.x + other.width/2.0f;
		float oBottom = other.position.y - other.height/2.0f;
		float oTop = other.position.y + other.height/2.0f;
		
		float left = position.x - width/2.0f;
		float right = position.x + width/2.0f;
		float bottom = position.y - height/2.0f;
		float top = position.y + height/2.0f;
		
		return (left < oRight && right > oLeft && top > oBottom && bottom < oTop);
	
	}
	
}
