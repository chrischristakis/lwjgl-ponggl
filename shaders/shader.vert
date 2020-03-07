#version 330 core

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 color;

uniform mat4 modelMat = mat4(1.0);

out vec4 aCol;

void main()
{
	aCol = vec4(color, 1.0);
	gl_Position = modelMat * vec4(position, 1.0);
}
