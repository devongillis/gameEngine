package creatures;

import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Entity;
import models.TexturedModel;
import particles.Particle;
import particles.ParticleTexture;
import renderEngine.DisplayManager;

public class Fish {
	
	//private int interval = 0;
	private int maxDistance = 10;
	private int minDistance = -10;
	private boolean forward = true;
	private float forwardAmount = 0.1f;
	
	private Entity body;
	
	private float maxAngle = 10;
	private float minAngle = -10;
	private float bodyAngle;
	private boolean right = true;
	
	
	public Fish(TexturedModel fishModel, Vector3f position, Vector3f rotation, float scale) {
		fishModel.getTexture().setHasTransparency(true);
		body = new Entity(fishModel, position, rotation.x, rotation.y, rotation.z, scale);
		bodyAngle = rotation.y;
	}
	
	
	public void addEntities(List<Entity> entities){
		entities.add(body);
	}
	
	public void update(){
		hoverSideToSide();
		moveAcross();
	}
	
	private void hoverSideToSide(){
		// want to rotate about y axis a little to give a swimming feel
		body.setRotZ(bodyAngle);
		if(right){
			bodyAngle += 1f;
			if(bodyAngle >= 0 + maxAngle){
				right = false;
			}
		}
		else{
			bodyAngle -= 1f;
			if(bodyAngle <= 0 - minAngle){
				right = true;
			}
		}
		
	}
	
	
	private void moveAcross(){
		// swim a little in one direction then turn around(rotate also) and swim back
		
		if(forward){
			body.increasePosition(forwardAmount, 0, 0);
			if(body.getPosition().x >= maxDistance){
				forward = false;
				body.setRotZ(180);
				maxAngle = 10;
				minAngle = -10;
			}
		}
		else{
			body.increasePosition(-forwardAmount, 0, 0);
			if(body.getPosition().x <= minDistance){
				forward = true;
				body.setRotZ(0);
				maxAngle = -10;
				minAngle = 10;
			}
		}
		
	}
	
	
	
	
}
