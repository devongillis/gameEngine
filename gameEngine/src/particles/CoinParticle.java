package particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Player;
import renderEngine.DisplayManager;

public class CoinParticle extends Particle{
	
	public CoinParticle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation,
			float scale) {
		super(texture, position, velocity, gravityEffect, lifeLength, rotation, scale);
	}
	
	@Override
	protected boolean update(Camera camera){
		//velocity.y += Player.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds() * 0.0001f;
		//position.y += velocity.y;
		
		distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();
		updateTextureCoordInfo();
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}
	
}
