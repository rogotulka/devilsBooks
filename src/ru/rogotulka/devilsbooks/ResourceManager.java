package ru.rogotulka.devilsbooks;







import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;

import android.content.Context;




public class ResourceManager {
	
	private static final ResourceManager INSTANCE = new ResourceManager();
	
	public static Engine engine;
	public static Scene scene;
	public static Context context;
	public static int cameraWidth;
	public static int cameraHeight;
	public static BitmapTextureAtlas mBitmapTextureAtlas;
	
	public static TiledTextureRegion mTiledCardsTexture;
	public static TextureRegion mShirtCardsTexture;
	
	private ResourceManager() {
	}
	
	// Retrieves a global instance of the ResourceManager
	public static ResourceManager getInstance() {
		return INSTANCE;
	}
	
	// Setup the ResourceManager
		public void setup(final Engine pEngine, Context pContext,
				final int pCameraWidth, final int pCameraHeight) {
			engine = pEngine;
			context = pContext;
			cameraWidth = pCameraWidth;
			cameraHeight = pCameraHeight;
			
		}
		
		public static void loadGameResources() {
			getInstance().loadGameTextures();
			getInstance().loadGameFont();
		}
		
		private void loadGameTextures() {
			BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
			mBitmapTextureAtlas = new BitmapTextureAtlas(engine.getTextureManager(), 1024,
					1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
			mTiledCardsTexture = BitmapTextureAtlasTextureRegionFactory
			.createTiledFromAsset(mBitmapTextureAtlas, context,
					"cards.png", 0, 0, 13, 4);
			//mTiledCardsTexture.create(mBitmapTextureAtlas, 0, 1, pTextureWidth, pTextureHeight, pTileColumns, pTileRows)
			mShirtCardsTexture = BitmapTextureAtlasTextureRegionFactory.createFromAsset( mBitmapTextureAtlas,
					context, "shirt_card.png", 840 , 347);
			
			engine.getTextureManager().loadTexture(mBitmapTextureAtlas);
		}
		
		private void loadGameFont(){
			
		}


}
