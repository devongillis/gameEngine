package particles;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import entities.Player;
import renderEngine.DisplayManager;

public class Particle {
	
	protected Vector3f position;
	protected Vector3f velocity;
	protected float gravityEffect;
	protected float lifeLength;
	protected float rotation;
	protected float scale;
	
	protected ParticleTexture texture;
	
	protected Vector2f texOffset1 = new Vector2f();
	protected Vector2f texOffset2 = new Vector2f();
	protected float blend;
	
	protected float elapsedTime = 0;
	protected float distance;
	
	private Vector3f reusableChange = new Vector3f();
	
	private boolean alive = false;
	
	
	
	public Particle(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation,
			float scale) {
		alive = true;
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		ParticleMaster.addParticle(this);
	}
	
	public void reAssign(ParticleTexture texture, Vector3f position, Vector3f velocity, float gravityEffect, float lifeLength, float rotation,
			float scale) {
		alive = true;
		this.texture = texture;
		this.position = position;
		this.velocity = velocity;
		this.gravityEffect = gravityEffect;
		this.lifeLength = lifeLength;
		this.rotation = rotation;
		this.scale = scale;
		texOffset1 = new Vector2f();
		texOffset2 = new Vector2f();
		elapsedTime = 0;
	}
	
	public boolean getAlive(){
		return alive;
	}
	
	public void setDead(){
		alive = false;
	}

	public float getDistance() {
		return distance;
	}



	public Vector2f getTexOffset1() {
		return texOffset1;
	}



	public Vector2f getTexOffset2() {
		return texOffset2;
	}



	public float getBlend() {
		return blend;
	}



	public ParticleTexture getTexture() {
		return texture;
	}

	public Vector3f getPosition() {
		return position;
	}

	public float getRotation() {
		return rotation;
	}

	public float getScale() {
		return scale;
	}

	protected boolean update(Camera camera){
		velocity.y += Player.GRAVITY * gravityEffect * DisplayManager.getFrameTimeSeconds();
		
		//Vector3f change = new Vector3f(velocity);
		//change.scale(DisplayManager.getFrameTimeSeconds());
		
		reusableChange.set(velocity);
		reusableChange.scale(DisplayManager.getFrameTimeSeconds());
		
		Vector3f.add(reusableChange, position, position);
		distance = Vector3f.sub(camera.getPosition(), position, null).lengthSquared();
		updateTextureCoordInfo();
		elapsedTime += DisplayManager.getFrameTimeSeconds();
		return elapsedTime < lifeLength;
	}
	
	protected void updateTextureCoordInfo(){
		float lifeFactor = elapsedTime / lifeLength;
		int stageCount = texture.getNumberOfRows() * texture.getNumberOfRows();
		float atlasProgression = lifeFactor * stageCount;
		int index1 = (int) Math.floor(atlasProgression);
		int index2 = index1 < stageCount - 1 ? index1 + 1 : index1;
		this.blend = atlasProgression % 1;
		setTextureOffset(texOffset1, index1);
		setTextureOffset(texOffset2, index2);
		
	}
	
	protected void setTextureOffset(Vector2f offset, int index){
		int column = index % texture.getNumberOfRows();
		int row = index / texture.getNumberOfRows();
		offset.x = (float) column / texture.getNumberOfRows();
		offset.y = (float) row / texture.getNumberOfRows();
	}

}
