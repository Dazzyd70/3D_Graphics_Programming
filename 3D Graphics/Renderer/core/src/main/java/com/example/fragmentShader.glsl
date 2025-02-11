#version 330 core

in vec3 fragPos;
in vec3 normal;
in vec2 texCoord;
in vec4 fragPosLightSpace;

out vec4 FragColor;

uniform sampler2D texture1;
uniform samplerCube skybox;
uniform sampler2D shadowMap;

uniform vec3 lightPos;
uniform vec3 viewPos;

uniform int materialType;


float calculateShadow(vec4 fragPosLightSpace) {
   
    vec3 projCoords = fragPosLightSpace.xyz / fragPosLightSpace.w;
    
    projCoords = projCoords * 0.5 + 0.5;
    
    float closestDepth = texture(shadowMap, projCoords.xy).r;
    
    float currentDepth = projCoords.z;
    
    float bias = 0.005;
    float shadow = currentDepth - bias > closestDepth ? 0.5 : 0.0;
    return shadow;
}

void main() {
  
    vec3 ambient = vec3(0.2);
    vec3 norm = normalize(normal);
    vec3 lightDir = normalize(lightPos - fragPos);
    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * vec3(0.8);
    
    vec3 viewDir = normalize(viewPos - fragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 32.0);
    vec3 specular = vec3(0.5) * spec;

    
    float shadow = calculateShadow(fragPosLightSpace);

    vec3 lighting = (ambient + (1.0 - shadow) * (diffuse + specular));

    vec4 color;
    if(materialType == 0) {  // if textured
        color = texture(texture1, texCoord);
    } else if(materialType == 1) {  // if reflective, currently dont have cubemap
        color = vec4(0.5, 0.75, 0.6, 1);
    } else if(materialType == 2) {  // if transparent
        float opacity = 0.3;
        color = vec4(0.2, 0.6, 0.8, 0.3);  // alpha .3 
        shadow = mix(1.0, shadow, opacity);
        lighting = ambient + (1.0 - shadow) * (diffuse + specular);
    } else {
        color = vec4(1.0); // default white
    }

    FragColor = vec4(color.rgb * lighting, color.a);
}
