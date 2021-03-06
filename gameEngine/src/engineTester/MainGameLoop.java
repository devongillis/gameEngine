package engineTester;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import creatures.Butterfly;
import creatures.Fish;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import fontMeshCreator.FontType;
import fontMeshCreator.GUIText;
import fontRendering.TextMaster;
import guis.GuiRenderer;
import guis.GuiTexture;
import models.RawModel;
import models.TexturedModel;
import normalMappingObjConverter.NormalMappedObjLoader;
import objConverter.ModelData;
import objConverter.OBJFileLoader;
import particles.CoinParticle;
import particles.ComplexParticleSystem;
import particles.Particle;
import particles.ParticleMaster;
import particles.ParticleTexture;
import postProcessing.Fbo;
import postProcessing.PostProcessing;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import renderEngine.EntityRenderer;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import water.WaterFrameBuffers;
import water.WaterRenderer;
import water.WaterShader;
import water.WaterTile;
/*	
		
		TerrainTexturePack texturePack = new TerrainTexturePack(
				new TerrainTexture(loader.loadTexture("grass")), 
				new TerrainTexture(loader.loadTexture("dirt")), 
				new TerrainTexture(loader.loadTexture("grassFlowers")), 
				new TerrainTexture(loader.loadTexture("path"))
		);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
		Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap", 40.0f);
		//Terrain terrain2 = new Terrain(1, 0, loader, texturePack, blendMap, "heightmap", 40.0f);
		
		
		
		TexturedModel stallTexturedModel = new TexturedModel(OBJLoader.loadObjModel("stall", loader), new ModelTexture(loader.loadTexture("stallTexture")));
		TexturedModel cubeTexturedModel = new TexturedModel(OBJLoader.loadObjModel("cube", loader), new ModelTexture(loader.loadTexture("cube")));
		TexturedModel grassTexturedModel = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader), new ModelTexture(loader.loadTexture("grassTexture"))); 
		TexturedModel fernAtlasTexturedModel = new TexturedModel(OBJLoader.loadObjModel("fern", loader), new ModelTexture(loader.loadTexture("fernAtlas")));
		TexturedModel barrelTexturedModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader), new ModelTexture(loader.loadTexture("barrel")));
		
		
		Entity stallEntity = new Entity(stallTexturedModel, new Vector3f(50, terrain.getHeightOfTerrain(50, 50), 50), 0, 0, 0, 1);
		stallEntity.getModel().getTexture().setShineDamper(10);
		stallEntity.getModel().getTexture().setReflectivity(0);
		
		Entity cubeEntity = new Entity(cubeTexturedModel, new Vector3f(0, 0, 30), 0, 0, 0, 1);
		cubeEntity.getModel().getTexture().setShineDamper(10);
		cubeEntity.getModel().getTexture().setReflectivity(0);
			
		Entity grassEntity1 = new Entity(grassTexturedModel, new Vector3f(0, 0, 10), 0, 0, 0, 1);
		grassEntity1.getModel().getTexture().setHasTransparency(true);
		grassEntity1.getModel().getTexture().setUseFakeLighting(true);
		grassEntity1.getModel().getTexture().setShineDamper(10);
		grassEntity1.getModel().getTexture().setReflectivity(0);
		
		Entity grassEntity2 = new Entity(grassTexturedModel, new Vector3f(0, 0, 40), 0, 0, 0, 1);
		grassEntity2.getModel().getTexture().setHasTransparency(true);
		grassEntity2.getModel().getTexture().setUseFakeLighting(true);
		grassEntity2.getModel().getTexture().setShineDamper(10);
		grassEntity2.getModel().getTexture().setReflectivity(0);
		
		Entity fernAtlasEntity1 = new Entity(fernAtlasTexturedModel, 1, new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), 1);
		fernAtlasEntity1.getModel().getTexture().setNumberOfRows(2);
		fernAtlasEntity1.getModel().getTexture().setHasTransparency(true);
		fernAtlasEntity1.getModel().getTexture().setShineDamper(10);
		fernAtlasEntity1.getModel().getTexture().setReflectivity(0);
		
		Entity fernAtlasEntity2 = new Entity(fernAtlasTexturedModel, 0, new Vector3f(0, 0, 20), new Vector3f(0, 0, 0), 1);
		fernAtlasEntity2.getModel().getTexture().setNumberOfRows(2);
		fernAtlasEntity2.getModel().getTexture().setHasTransparency(true);
		fernAtlasEntity2.getModel().getTexture().setShineDamper(10);
		fernAtlasEntity2.getModel().getTexture().setReflectivity(0);
	
		
		
		
		
		
		Light.setMinBrightness(0.2f);// static function for all lights
		List<Light> lights = new ArrayList<Light>();
		lights.add(new Light(new Vector3f(0, 50, -20), new Vector3f(1f, 1f, 1f)));//position,RGB);
		//lights.add(new Light(new Vector3f(40, -3, 40), new Vector3f(10, 0, 0), new Vector3f(1f, 0.01f, 0.002f)));//position, RGB, attenuation
		
		Vector2f fogValues = new Vector2f(0.01f, 1.5f);
		
		
		
		Player player = new Player(cubeTexturedModel, new Vector3f(0, 0, 5), 0, 0, 0, 1);
		Camera camera = new Camera(player);
		
		
		GuiTexture gui = new GuiTexture(loader.loadTexture("socuwan"), new Vector2f(0.5f, 0.5f), 0.0f, new Vector2f(0.25f, 0.25f)); // position and scale
		GuiTexture gui1 = new GuiTexture(loader.loadTexture("guitest"), new Vector2f(-0.5f, 0.5f), 0.0f, new Vector2f(0.25f, 0.25f));
		
		List<GuiTexture> guis = new ArrayList<GuiTexture>();
		guis.add(gui);
		guis.add(gui1);
		
		
		MasterRenderer renderer = new MasterRenderer(loader);
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
		
		
		
		
		
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		waters.add(new WaterTile(75, 75, -5));
		
		
		List<Entity> entities = new ArrayList<Entity>();// each time we change this list must call render.processEntities 
		entities.add(player);
		entities.add(stallEntity);
		entities.add(cubeEntity);
		entities.add(fernAtlasEntity1);
		entities.add(fernAtlasEntity2);
		entities.add(grassEntity1);
		entities.add(grassEntity2);
		
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		
		Entity barrel = new Entity(barrelTexturedModel, new Vector3f(0, 0, 0), 0, 0, 0, 1);
		//grassEntity2.getModel().getTexture().setHasTransparency(true);
		//grassEntity2.getModel().getTexture().setUseFakeLighting(true);
		grassEntity2.getModel().getTexture().setShineDamper(10);
		grassEntity2.getModel().getTexture().setReflectivity(0.5f);
		
		normalMapEntities.add(barrel);
		
		
		
		
		
		
		
		
		List<Terrain> terrains = new ArrayList<Terrain>();
		terrains.add(terrain);
	
		
		
		renderer.process(entities, normalMapEntities, terrains, lights, camera, fogValues);

}
*/


public class MainGameLoop {
	
	static long startTime;
	static long currentTime = System.nanoTime();
	static int framesPerSecond = 0;

	public static void main(String[] args) {

		DisplayManager.createDisplay();
		Loader loader = new Loader();
		TextMaster.init(loader);
		
		RawModel bunnyModel = OBJLoader.loadObjModel("person", loader);
		TexturedModel stanfordBunny = new TexturedModel(bunnyModel, new ModelTexture(
				loader.loadTexture("playerTexture")));

		Player player = new Player(stanfordBunny, new Vector3f(75, 5, 75), 0, 100, 0, 0.6f);
		Camera camera = new Camera(player);
		
		
		
		MasterRenderer renderer = new MasterRenderer(loader, camera);
		ParticleMaster.init(loader, renderer.getProjectionMatrix());
		
		
		
		FontType font = new FontType(loader.loadTexture("candara"), new File("res/candara.fnt"));
		GUIText text = new GUIText("This is a test text!", 3, font, new Vector2f(0.5f, 0.5f), 0.5f, true);
		text.setColor(1, 0.5f, 0);
		
		// *********TERRAIN TEXTURE STUFF**********
		
		TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy2"));
		TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("mud"));
		TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("grassFlowers"));
		TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));

		TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture,
				gTexture, bTexture);
		TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		// *****************************************

		
		TexturedModel butterflyWing = new TexturedModel(OBJFileLoader.loadOBJ("butterflyWing", loader), new ModelTexture(loader.loadTexture("blueButterflyWing")));
		TexturedModel fishModel = new TexturedModel(OBJFileLoader.loadOBJ("fish", loader), new ModelTexture(loader.loadTexture("orangeFish")));
		TexturedModel cowboy = new TexturedModel(OBJFileLoader.loadOBJ("cowboy", loader), new ModelTexture(loader.loadTexture("cowboy")));
		
		TexturedModel rocks = new TexturedModel(OBJFileLoader.loadOBJ("rocks", loader),
				new ModelTexture(loader.loadTexture("rocks")));

		ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
		fernTextureAtlas.setNumberOfRows(2);

		TexturedModel fern = new TexturedModel(OBJFileLoader.loadOBJ("fern", loader),
				fernTextureAtlas);

		TexturedModel bobble = new TexturedModel(OBJFileLoader.loadOBJ("pine", loader),
				new ModelTexture(loader.loadTexture("pine")));
		bobble.getTexture().setHasTransparency(true);

		fern.getTexture().setHasTransparency(true);

		//Terrain terrain = new Terrain(0, 0, loader, texturePack, blendMap, "heightmap");
		List<Terrain> terrains = new ArrayList<Terrain>();
		//terrains.add(terrain);
		
		Terrain terraina = new Terrain(1, 1, loader, texturePack, blendMap);
		terrains.add(terraina);
		
		
		

		TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader),
				new ModelTexture(loader.loadTexture("lamp")));
		lamp.getTexture().setUseFakeLighting(true);
		
		TexturedModel lantern = new TexturedModel(OBJFileLoader.loadOBJ("lantern", loader), new ModelTexture(loader.loadTexture("lantern")));
		lantern.getTexture().setSpecularMap(loader.loadTexture("lanternS"));
		
		TexturedModel cherryModel = new TexturedModel(OBJFileLoader.loadOBJ("cherry", loader), new ModelTexture(loader.loadTexture("cherry")));
		cherryModel.getTexture().setHasTransparency(true);
		cherryModel.getTexture().setShineDamper(10);
		cherryModel.getTexture().setReflectivity(0.5f);
		cherryModel.getTexture().setSpecularMap(loader.loadTexture("cherryS"));
		
		
		List<Entity> entities = new ArrayList<Entity>();
		List<Entity> normalMapEntities = new ArrayList<Entity>();
		
		//******************NORMAL MAP MODELS************************
		
		TexturedModel barrelModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("barrel", loader),
				new ModelTexture(loader.loadTexture("barrel")));
		barrelModel.getTexture().setNormalMap(loader.loadTexture("barrelNormal"));
		barrelModel.getTexture().setShineDamper(10);
		barrelModel.getTexture().setReflectivity(0.5f);
		//barrelModel.getTexture().setSpecularMap(loader.loadTexture("barrelS"));
		
		TexturedModel crateModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("crate", loader),
				new ModelTexture(loader.loadTexture("crate")));
		crateModel.getTexture().setNormalMap(loader.loadTexture("crateNormal"));
		crateModel.getTexture().setShineDamper(10);
		crateModel.getTexture().setReflectivity(0.5f);
		
		TexturedModel boulderModel = new TexturedModel(NormalMappedObjLoader.loadOBJ("boulder", loader),
				new ModelTexture(loader.loadTexture("boulder")));
		boulderModel.getTexture().setNormalMap(loader.loadTexture("boulderNormal"));
		boulderModel.getTexture().setShineDamper(10);
		boulderModel.getTexture().setReflectivity(0.5f);
		
		
		//************ENTITIES*******************
		//Entity butterflyWingR = new Entity(butterflyWing, new Vector3f(0, 10, 0), 0, 0, 0, 1f);
		//Entity butterflyWingL = new Entity(butterflyWing, new Vector3f(0, 10, 0), 0, 0, 180, 1f);
		Butterfly butterfly = new Butterfly(butterflyWing, new Vector3f(0, 10, 0), new Vector3f(0, 180, 0), 1f);
		butterfly.addEntities(entities);
		//butterflyWingR.getModel().getTexture().setHasTransparency(true);
		//butterflyWingL.getModel().getTexture().setHasTransparency(true);
		//entities.add(butterflyWingR);
		//entities.add(butterflyWingL);
		entities.add(new Entity(cowboy, new Vector3f(0, 0, 0), 0, 0, 0, 1f));
		
		
		Entity entity = new Entity(barrelModel, new Vector3f(75, 10, 75), 0, 0, 0, 1f);
		Entity entity2 = new Entity(boulderModel, new Vector3f(85, 10, 75), 0, 0, 0, 1f);
		Entity entity3 = new Entity(crateModel, new Vector3f(65, 10, 75), 0, 0, 0, 0.04f);
		normalMapEntities.add(entity);
		normalMapEntities.add(entity2);
		normalMapEntities.add(entity3);
		//entities.add(entity);
		
		Random random = new Random(5666778);
		for (int i = 0; i < 60; i++) {
			if (i % 3 == 0) {
				float x = random.nextFloat() * 150;
				float z = random.nextFloat() * 150;
				if ((x > 50 && x < 100) || (z < 50 && z > 100)) {
				} else {
					float y = terraina.getHeightOfTerrain(x, z);

					entities.add(new Entity(fern, 3, new Vector3f(x, y, z), 0,
							random.nextFloat() * 360, 0, 0.9f));
				}
			}
			if (i % 2 == 0) {

				float x = random.nextFloat() * 150;
				float z = random.nextFloat() * 150;
				if ((x > 50 && x < 100) || (z < 50 && z > 100)) {

				} else {
					float y = terraina.getHeightOfTerrain(x, z);
					entities.add(new Entity(bobble, 1, new Vector3f(x, y, z), 0,
							random.nextFloat() * 360, 0, random.nextFloat() * 0.6f + 0.8f));
				}
			}
		}
		entities.add(new Entity(rocks, new Vector3f(75, 4.6f, 75), 0, 0, 0, 75));
		for(int i = 0; i < 1; i++){
			entities.add(new Entity(cherryModel, new Vector3f(40 + i * 10, terraina.getHeightOfTerrain(40 + i * 10, 40 + i * 10), 40 + i * 10), 0, 0, 0, 10.0f));
		}
		
		entities.add(new Entity(lantern, new Vector3f(50, terraina.getHeightOfTerrain(50,  50), 50), 0, 0, 0, 1.0f));
		//*******************OTHER SETUP***************

		List<Light> lights = new ArrayList<Light>();
		Light sun = new Light(new Vector3f(1000000, 1000000, 1000000), new Vector3f(1.3f, 1.3f, 1.3f));
		lights.add(sun);
		
		

		
		
		
		
		entities.add(player);
		MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terraina);
	
		List<GuiTexture> guiTextures = new ArrayList<GuiTexture>();
		
		GuiTexture shadowMap = new GuiTexture(renderer.getShadowMapTexture(), new Vector2f(0.5f, 0.5f), new Vector2f(0.5f, 0.5f));
		
		//guiTextures.add(shadowMap);
		
		GuiRenderer guiRenderer = new GuiRenderer(loader);
		
		
		//**********Water Renderer Set-up************************
		
		WaterFrameBuffers buffers = new WaterFrameBuffers();
		WaterShader waterShader = new WaterShader();
		WaterRenderer waterRenderer = new WaterRenderer(loader, waterShader, renderer.getProjectionMatrix(), buffers);
		List<WaterTile> waters = new ArrayList<WaterTile>();
		WaterTile water = new WaterTile(75, 75, 0);
		waters.add(water);
		
		ParticleTexture particleTexture = new ParticleTexture(loader.loadTexture("particleAtlas"), 4, false);
		ParticleTexture coinTexture = new ParticleTexture(loader.loadTexture("coin"), 2, false);
		CoinParticle coin = new CoinParticle(coinTexture, new Vector3f(0, 10, 0), new Vector3f(0, 0, 0), 1, 0.25f/*rotationspeed*/, 0, 1f);
		
		//ParticleTexture fishTexture = new ParticleTexture(, 2, false);
		
		Fish fish = new Fish(fishModel, new Vector3f(4, 10, 4), new Vector3f(-90, 180, 0), 1);
		fish.addEntities(entities);
		//Butterfly butterfly = new Butterfly(butterflyWing, new Vector3f(0, 10, 0), new Vector3f(0, 180, 0), 1f);
		//butterfly.addEntities(entities);
		
		
		ComplexParticleSystem system = new ComplexParticleSystem(particleTexture, 50, 25, 0.3f, 4, 1);
		
		
		Fbo multisampleFbo = new Fbo(Display.getWidth(), Display.getHeight());
		Fbo outputFbo = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		Fbo outputFbo2 = new Fbo(Display.getWidth(), Display.getHeight(), Fbo.DEPTH_TEXTURE);
		
		PostProcessing.init(loader);
		
		TexturedModel texturedCube = new TexturedModel(OBJFileLoader.loadOBJ("cube", loader), new ModelTexture(loader.loadTexture("sUp")));
		Entity cube = new Entity(texturedCube, new Vector3f(200, 10, 200), 0, 0, 0, 10f);
		entities.add(cube);
		//****************Game Loop Below*********************

		while (!Display.isCloseRequested()) {
			//camera.rollFunction();
			//camera.pitchFunction();
			butterfly.update();
			//fish.update();
			//player.setScale(player.getScale() + 0.001f);
			player.move(terraina);
			camera.move();
			picker.update();
			
			entity.increaseRotation(0, 1, 0);
			entity2.increaseRotation(0, 1, 0);
			entity3.increaseRotation(0, 1, 0);
			
			system.generateParticles(player.getPosition());
			
			ParticleMaster.update(camera);
			
			renderer.renderShadowMap(entities, normalMapEntities, sun);
			GL11.glEnable(GL30.GL_CLIP_DISTANCE0);
			
			//render reflection texture
			buffers.bindReflectionFrameBuffer();
			float distance = 2 * (camera.getPosition().y - water.getHeight());
			camera.getPosition().y -= distance;
			camera.invertPitch();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, 1, 0, -water.getHeight()+1));
			camera.getPosition().y += distance;
			camera.invertPitch();
			
			//render refraction texture
			buffers.bindRefractionFrameBuffer();
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, water.getHeight()));
			
			//render to screen
			GL11.glDisable(GL30.GL_CLIP_DISTANCE0);
			buffers.unbindCurrentFrameBuffer();
			
			
			//multisampleFbo.bindFrameBuffer();// remove both if you don't want post processing
			renderer.renderScene(entities, normalMapEntities, terrains, lights, camera, new Vector4f(0, -1, 0, 100000));
			waterRenderer.render(waters, camera, sun);
			ParticleMaster.renderParticles(camera);
			//multisampleFbo.unbindFrameBuffer();// remove both if you don't want post processing
			//multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT0, outputFbo);
			//multisampleFbo.resolveToFbo(GL30.GL_COLOR_ATTACHMENT1, outputFbo2);
			
			//PostProcessing.doPostProcessing(outputFbo.getColourTexture(), outputFbo2.getColourTexture());
			
			
			
			
			guiRenderer.render(guiTextures);
			TextMaster.render();
			
			DisplayManager.updateDisplay();
			frameCounter();
		}

		//*********Clean Up Below**************
		
		outputFbo.cleanUp();
		outputFbo2.cleanUp();
		PostProcessing.cleanUp();
		multisampleFbo.cleanUp();
		ParticleMaster.cleanUp();
		TextMaster.cleanUp();
		buffers.cleanUp();
		waterShader.cleanUp();
		guiRenderer.cleanUp();
		renderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();

	}

	private static void frameCounter(){
		currentTime = System.nanoTime();
		if(currentTime >= startTime + 1000000000){
			startTime = currentTime;
			System.out.println(Integer.toString(framesPerSecond));
			framesPerSecond = 0;
		}
		framesPerSecond++;
	}
}

