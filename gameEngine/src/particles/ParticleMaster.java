package particles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import renderEngine.Loader;

public class ParticleMaster {
	
	private static Map<ParticleTexture, List<Particle>> particles = new HashMap<ParticleTexture, List<Particle>>();
	private static ParticleRenderer renderer;
	
	public static void init(Loader loader, Matrix4f projectionMatrix){
		renderer = new ParticleRenderer(loader, projectionMatrix);
	}
	
	public static void update(Camera camera){
		Iterator<Entry<ParticleTexture, List<Particle>>> mapIterator = particles.entrySet().iterator();
		while(mapIterator.hasNext()){
			Entry<ParticleTexture, List<Particle>> entry = mapIterator.next();
			List<Particle> list = entry.getValue();
			Iterator<Particle> iterator = list.iterator();
			while(iterator.hasNext()){
				Particle p = iterator.next();
				boolean stillAlive = p.update(camera);
				if(!stillAlive){
					//iterator.remove();
					p.setDead();
					if(list.isEmpty()){
						mapIterator.remove();
					}
				}
			}
			if(!entry.getKey().useAdditiveBlending()){
				InsertionSort.sortHighToLow(list);
			}
		}
	}
	
	public static void renderParticles(Camera camera){
		renderer.render(particles, camera);
	}
	
	public static void cleanUp(){
		renderer.cleanUp();
	}
	
	public static void addParticle(Particle particle){
		List<Particle> list = particles.get(particle.getTexture());
		if(list == null){
			list = new ArrayList<Particle>();
			particles.put(particle.getTexture(), list);
		}
		list.add(particle);
	}
	
	public static boolean reAssignParticle(ParticleTexture texture, Vector3f center, Vector3f velocity, float gravityComplient, float lifeLength, float rotation, float scale){
		List<Particle> list = particles.get(texture);
		if(list == null){
			return false;
		}
		else{
			for(int i = 0; i < list.size(); i++){
				if(!list.get(i).getAlive()){
					list.get(i).reAssign(texture, center, velocity, gravityComplient, lifeLength, rotation, scale);
					return true;
				}
			}
			return false;
		}
	}

}
