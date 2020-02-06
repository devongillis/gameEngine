package creatures;

import java.util.List;
import org.lwjgl.util.vector.Vector3f;
import entities.Entity;
import models.TexturedModel;

public class Butterfly {
	private Entity wingR;
	private Entity wingL;
	
	private boolean up = true;
	private float wingAngle = 0;
	private int maxAngle = 45;
	private int minAngle = -45;
	private float flapSpeed = 8f;
	
	private float hoverSpeed = 0.01f;
	
	public Butterfly(TexturedModel butterflyModel, Vector3f position, Vector3f rotation, float scale){
		
		butterflyModel.getTexture().setHasTransparency(true);
		
		wingR = new Entity(butterflyModel, new Vector3f(position.x + 0.0125f, position.y, position.z), rotation.x, rotation.y, rotation.z, scale);
		wingL = new Entity(butterflyModel, new Vector3f(position.x - 0.0125f, position.y, position.z), rotation.x, rotation.y, rotation.z + 180, scale);
		
		
		//angleIncrement = flapSpeed;
	}
	
	public void addEntities(List<Entity> entities){
		entities.add(wingR);
		entities.add(wingL);
	}
	
	public void update(){
		flapWings();
		hoverUpAndDown();
		moveAcross();
	}
	
	private void flapWings(){
		wingR.setRotZ(wingAngle);
		wingL.setRotZ(180 - wingAngle);
		if(up){
			wingAngle += flapSpeed;
			if(wingAngle >= maxAngle){
				up = false;
			}
		}
		else{
			wingAngle -= flapSpeed;
			if(wingAngle <= minAngle){
				up = true;
			}
		}
	}
	
	private void hoverUpAndDown(){
		if(up){
			wingR.increasePosition(0, hoverSpeed, 0);
			wingL.increasePosition(0, hoverSpeed, 0);
		}
		else{
			wingR.increasePosition(0, -hoverSpeed, 0);
			wingL.increasePosition(0, -hoverSpeed, 0);
		}
	}
	
	private void moveAcross(){
		wingR.increasePosition(0, 0, 0.2f);
		wingL.increasePosition(0,  0,  0.2f);
	}
}
