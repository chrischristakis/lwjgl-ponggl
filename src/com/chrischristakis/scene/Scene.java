package com.chrischristakis.scene;

import static org.lwjgl.glfw.GLFW.*;

import java.util.Random;

import org.joml.Math;
import org.joml.Matrix4f;

import com.chrischristakis.gfx.VAO;
import com.chrischristakis.utils.ShaderUtils;

public class Scene
{
	
	private VAO mesh; //Background line.
	private Paddle p1, p2;
	private Ball ball;
	private Random rand = new Random();
	
	public static int score1 = 0, score2 = 0;
	
	public Scene()
	{
		float[] vertices = {
			-0.001f, 1.0f, 0.0f, 	1.0f, 1.0f, 1.0f,
			 0.001f, 1.0f, 0.0f,	1.0f, 1.0f, 1.0f,
			 0.001f,-1.0f, 0.0f,	1.0f, 1.0f, 1.0f,
			-0.001f,-1.0f, 0.0f,	1.0f, 1.0f, 1.0f
		};
		byte[] indices = {
				0, 1, 2,
				0, 3, 2
		};
		mesh = new VAO(vertices, indices);
		
		p1 = new Paddle(-0.9f, 0.0f, GLFW_KEY_W, GLFW_KEY_S);
		p2 = new Paddle(0.9f, 0.0f, GLFW_KEY_UP, GLFW_KEY_DOWN);
		ball = new Ball();
	}
	
	public void render()
	{
		ShaderUtils.paddle.setMat4f("modelMat", new Matrix4f());
		mesh.render();
		p1.render();
		p2.render();
		ball.render();
	}
	
	private float dampening;
	private long elapsed = 0L;
	public void update()
	{
		if(elapsed == 0)
			elapsed = System.currentTimeMillis();
		
		if(score1 >= 5)
		{
			System.out.println("Game over! Player 1 wins!");
			System.exit(0);
		}
		if(score2 >= 5)
		{
			System.out.println("Game over! Player 2 wins!");
			System.exit(0);
		}
		
		p1.update();
		p2.update();
		ball.update();
		
		dampening = 3.1f + rand.nextFloat()*(8.4f - 3.1f); //Make a random dampening value to make paddle bounces still fair, but more interesting.
		if(p1.collides(ball))
		{
			ball.position.x = p1.position.x + p1.width;
			ball.velX = Math.abs(ball.velX);
			//velY is dependent on distance from ball centre to paddle centre, the closer, the closer to 0 velY is.
			if(ball.position.y > p1.position.y) ball.velY = Math.abs(ball.position.y - p1.position.y)/dampening;
			else ball.velY = -Math.abs((p1.position.y) -(ball.position.y))/dampening;
		}
		if(p2.collides(ball))
		{
			ball.position.x = p2.position.x - ball.width;
			ball.velX = -Math.abs(ball.velX);
			if(ball.position.y > p2.position.y) ball.velY = Math.abs(ball.position.y - p2.position.y)/dampening;
			else ball.velY = -Math.abs((p2.position.y) - (ball.position.y))/dampening;
		}
		
		if(System.currentTimeMillis() - elapsed >= 2500)
		{
			elapsed = System.currentTimeMillis();
			if(Math.abs(ball.velX) < Ball.TERMINAL_VELX) ball.velX += ball.velX * 0.1;
			else ball.velX = Ball.TERMINAL_VELX * ball.velX/Math.abs(ball.velX); //unit vector for correct direction.
		}
	}
	
	public static void addPoint(int paddleNum)
	{
		if(paddleNum == 1)
			score1++;
		if(paddleNum == 2)
			score2++;
		System.out.println("P1: " + score1 + " | P2: " + score2);
	}

}
