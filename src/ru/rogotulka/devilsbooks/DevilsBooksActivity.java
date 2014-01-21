package ru.rogotulka.devilsbooks;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.util.GLState;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.adt.color.Color;

import ru.rogotulka.play.GameInstance;
import ru.rogotulka.util.Card;

public class DevilsBooksActivity extends BaseGameActivity {

	// ====================================================
	// CONSTANTS
	// ====================================================
	private static final int DEFAULT_CAMERA_WIDTH = 480;
	private static final int DEFAULT_CAMERA_HEIGHT = 800;
	private static final int MARGIN_WIDTH = 70;
	private static final int MARGIN_HEIGHT = 80;
	private static final int COLOUMNS = 13;
	private static final int ROWS = 4;
	private static final int CARDS_ON_HAND = 6;

	// ====================================================
	// VARIABLES
	// ====================================================
	private Camera camera;
	private Scene splashScene;
	private BitmapTextureAtlas splashTextureAtlas;
	private ITextureRegion splashTextureRegion;
	private Sprite splash;
	private Scene scene;
	private List<Boolean> currentCardsPosition;
	private List<Sprite> cardSprites;

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, DEFAULT_CAMERA_WIDTH, DEFAULT_CAMERA_HEIGHT);
		// policy fills the whole screen with our scene
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new FillResolutionPolicy(),
				camera);

		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws IOException {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(this.getTextureManager(),
				1024, 1024, TextureOptions.DEFAULT);
		splashTextureRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(splashTextureAtlas, this, "splash.png", 0, 0);
		splashTextureAtlas.load();

		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws IOException {
		initSplashScene();
		pOnCreateSceneCallback.onCreateSceneFinished(this.splashScene);

	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback)
			throws IOException {
		mEngine.registerUpdateHandler(new TimerHandler(5f,
				new ITimerCallback() {
					public void onTimePassed(final TimerHandler pTimerHandler) {
						mEngine.unregisterUpdateHandler(pTimerHandler);
						loadResources();
						loadScenes();
						splash.detachSelf();
						mEngine.setScene(scene);
						// /currentScene = SceneType.MAIN;
					}

				}));

		pOnPopulateSceneCallback.onPopulateSceneFinished();

	}

	private void initSplashScene() {

		splashScene = new Scene();
		splash = new Sprite(DEFAULT_CAMERA_WIDTH / 2,
				DEFAULT_CAMERA_HEIGHT / 2, splashTextureRegion,
				mEngine.getVertexBufferObjectManager()) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};

		splashScene.attachChild(splash);
	}

	private void loadScenes() {
		scene = new Scene();
		GameInstance gameInstance = new GameInstance();
		currentCardsPosition = new ArrayList<Boolean>(CARDS_ON_HAND);
		cardSprites = new ArrayList<Sprite>(CARDS_ON_HAND);
		for (int i = 0; i < CARDS_ON_HAND; i++) {
			currentCardsPosition.add(false);
		}
		TiledSprite sCards = new TiledSprite(0, 0,
				ResourceManager.mTiledCardsTexture,
				ResourceManager.engine.getVertexBufferObjectManager()) {
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {
				if (pSceneTouchEvent.isActionDown()) {
					MoveYModifier m = new MoveYModifier(0.5f, 52, 90);
					this.registerEntityModifier(m);
				}

				return true;
			}
		};
		
		
		
		int i = CARDS_ON_HAND-1;
		for (Card card : gameInstance.getMainPlayer().getOnHand()) {
			Sprite sMainCard = new Sprite(DEFAULT_CAMERA_WIDTH / 2 + CARDS_ON_HAND
					/ 2 * MARGIN_WIDTH / 2 - i * MARGIN_WIDTH / 2,
					MARGIN_HEIGHT, sCards.getTiledTextureRegion()
							.getTextureRegion(
									card.getSuit() * (ROWS - 1) + card.getRankCard()),
					ResourceManager.engine.getVertexBufferObjectManager()) {
				public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
						float pTouchAreaLocalX, float pTouchAreaLocalY) {
					if (pSceneTouchEvent.isActionDown()) {
						MoveYModifier m;
						if (!currentCardsPosition.get(CARDS_ON_HAND-1-this.getZIndex())) {
							m = new MoveYModifier(0.5f, MARGIN_HEIGHT,
									MARGIN_HEIGHT + 40);

						} else {
							m = new MoveYModifier(0.5f, MARGIN_HEIGHT + 40,
									MARGIN_HEIGHT);
						}
						currentCardsPosition.set(CARDS_ON_HAND-1-this.getZIndex(),
								!currentCardsPosition.get(CARDS_ON_HAND-1-this.getZIndex()));
						this.registerEntityModifier(m);
						for (int j = 0; j < currentCardsPosition.size(); j++) {
							if(currentCardsPosition.get(j) && cardSprites.get(j)!= this){
								cardSprites.get(j).registerEntityModifier(new MoveYModifier(0.5f, MARGIN_HEIGHT + 40,
									MARGIN_HEIGHT));
								currentCardsPosition.set(j,
										!currentCardsPosition.get(j));
							}
						}
						
					}
					return true;
				}
			};
			Sprite sMainShirt = new Sprite(DEFAULT_CAMERA_WIDTH / 2 + CARDS_ON_HAND
					/ 2 * MARGIN_WIDTH / 2 - i * MARGIN_WIDTH / 2,
					MARGIN_HEIGHT, ResourceManager.mShirtCardsTexture,
					ResourceManager.engine.getVertexBufferObjectManager());
			Sprite sRightShirt = new Sprite(DEFAULT_CAMERA_WIDTH-MARGIN_WIDTH,
					DEFAULT_CAMERA_HEIGHT / 2 + CARDS_ON_HAND
					/ 2 * MARGIN_HEIGHT / 2 - i * MARGIN_HEIGHT / 2, ResourceManager.mShirtCardsTexture,
					ResourceManager.engine.getVertexBufferObjectManager());
			Sprite sLeftShirt = new Sprite(MARGIN_WIDTH,
					DEFAULT_CAMERA_HEIGHT / 2 + CARDS_ON_HAND
					/ 2 * MARGIN_HEIGHT / 2 - i * MARGIN_HEIGHT / 2, ResourceManager.mShirtCardsTexture,
					ResourceManager.engine.getVertexBufferObjectManager());
			Sprite sUpShirt = new Sprite(DEFAULT_CAMERA_WIDTH / 2 + CARDS_ON_HAND
					/ 2 * MARGIN_WIDTH / 2 - i * MARGIN_WIDTH / 2,
					DEFAULT_CAMERA_HEIGHT - MARGIN_HEIGHT, ResourceManager.mShirtCardsTexture,
					ResourceManager.engine.getVertexBufferObjectManager());
			sMainCard.attachChild(sMainShirt);
			sMainCard.getFirstChild().setVisible(false);
			sMainCard.setChildrenIgnoreUpdate(true);
			cardSprites.add(sMainCard);
			sMainCard.setZIndex(i--);
			scene.registerTouchArea(sMainCard);
			scene.attachChild(sMainCard);
			sRightShirt.setRotation(45);
			sLeftShirt.setRotation(-45);
			scene.attachChild(sRightShirt);
			scene.attachChild(sLeftShirt);
			scene.attachChild(sUpShirt);
		}
		
		
		scene.sortChildren(true);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		scene.setTouchAreaBindingOnActionMoveEnabled(true);
		this.scene.setBackground(new Background(new Color(0.6f, 0.63f, 0.3f)));
	}

	private void loadResources() {
		// load all resourses for game(textures, fonts, )
		ResourceManager.getInstance().setup(this.mEngine, this,
				DEFAULT_CAMERA_WIDTH, DEFAULT_CAMERA_HEIGHT);
		// playerData = new PlayerData();
		ResourceManager.loadGameResources();

	}

}
