package net.xicp.zyl.me.nyancat;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

public class NyancatGame extends Game {
	SpriteBatch batch;
	Cat cat;
	TextureRegion backgroundTile;
	Vector2 gravity = new Vector2(0, -20);
	public static int scr_width;
	public static int scr_height;
	OrthographicCamera camera, uiCamera;
	ArrayList<Platform> platforms = new ArrayList<Platform>();
	Texture gameOver;
	GameState gameState;
	int score = 0;
	int old_score = 0;
	private final int PLATFORM_COUNT = 4;
	BitmapFont font;
	TextureRegion[] digital = new TextureRegion[10];
	TextureRegion[] digital_small = new TextureRegion[10];
	TextureRegion gameOverPanel;
	TextureRegion newTexture;
	float old_cat_postion_y;
	private boolean hasNewScore;
	Preferences p;
	private int highestScore;
	private List<Platform> platformToRemove = new ArrayList<Platform>();
	private float maxY, minY;
	Music music;
	Sound hitSpring;
	Sound rocket;
	Sound dead;
	Sound hitPlatform;
	Sound canThroughSound;
	Sound enemyDie;
	Texture ready;
	Enemy enemy_partycat;
	float ranBg1;
    float ranBg2;
	@Override
	public void create() {
		scr_width = 600;
		scr_height = 1066;
		enemy_partycat = new Enemy(Enemy.TYPE.partycat);
		ready = new Texture("images/ready.png");
		music = Gdx.audio.newMusic(Gdx.files.internal("music/music.ogg"));
		hitPlatform = Gdx.audio.newSound(Gdx.files.internal("sound/killed.ogg"));
		hitSpring = Gdx.audio.newSound(Gdx.files.internal("sound/bounce.ogg"));
		rocket = Gdx.audio.newSound(Gdx.files.internal("sound/turbonyan.ogg"));
		dead = Gdx.audio.newSound(Gdx.files.internal("sound/nw_fever_off.ogg"));
		canThroughSound = Gdx.audio.newSound(Gdx.files.internal("sound/damage.ogg"));
		enemyDie = Gdx.audio.newSound(Gdx.files.internal("sound/nyan_shop.ogg"));
		batch = new SpriteBatch();
		cat = new Cat();
		font = new BitmapFont(Gdx.files.internal("images/arial.fnt"));
		gameOver = new Texture("images/gameover.png");
		newTexture = new TextureRegion(new Texture("images/gameoverpanel.png"),
				0, 182, 48, 20);
		backgroundTile = new TextureRegion(new Texture(
				"images/theme_valentine_bg.png"));
		for (int i = 0; i < 10; i++) {
			digital[i] = new TextureRegion(new Texture("images/digital.png"),
					i > 4 ? (i - 5) * 40 : 40 * i, i > 4 ? 60 : 0, 40, 60);
		}
		for (int i = 0; i < 10; i++) {
			digital_small[i] = new TextureRegion(new Texture(
					"images/digitalsmall.png"), i > 4 ? (i - 5) * 25 : 25 * i,
					i > 4 ? 32 : 0, 25, 32);
		}
		p = Gdx.app.getPreferences("net.xicp.zyl.me.nyancat.setttings");
		gameOverPanel = new TextureRegion(new Texture(
				"images/gameoverpanel.png"), 349, 177);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, scr_width, scr_height);
		camera.update();

		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, scr_width, scr_height);
		uiCamera.update();
		gameState = GameState.Ready;
		resetWorld();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateWorld();
		drawWorld();
	}

	private void resetWorld() {
		// TODO Auto-generated method stub
		ranBg1 = 20000 * MathUtils.random(0.7f, 1);
		ranBg2 = MathUtils.random(ranBg1, 60000);
		cat.canThroughThings = false;
		cat.isDead = false;
		cat.velocity.set(0, 0);
		cat.position.set(0, 0);
		maxY = 0;
		minY = 0;
		hasNewScore = false;
		score = old_score = 0;
		platforms.clear();
		generatePlatformFirst();
		camera.position.set(scr_width / 2, cat.position.y + scr_height / 2
				- 100, 0);
		if (gameState == GameState.Start)
			music.play();
		generateEnemy();
	}

	private void generatePlatformFirst() {
		int height = 0;
		final int gap_y_at_least = 100;
		for (int i = 0; i < (scr_height / gap_y_at_least + 1); i++) {
			Platform platform = new Platform();
			platform.position.x = MathUtils.random(scr_width
					- platform.getWidth());
			platform.position.y = height;
			maxY = height;
			if (i == 0) {
				cat.position.x = platform.position.x + platform.getWidth() / 2
						- cat.getWidth() / 2;
				cat.position.y = platform.position.y + platform.getHeight();
			}
			platforms.add(platform);
			float ranHeight = MathUtils.random(platform.getHeight(),
					gap_y_at_least + i * 20) % Platform.GAP_Y;
			height += ranHeight;
		}
	}

	private void generatePlatform(Platform platform) {
		// TODO Auto-generated method stub
		platform.init();
		float height = maxY + MathUtils.random(100, Platform.GAP_Y);
		if (score >= MathUtils.random(10000)) {
			final float chance = score / 10000.0f;
			boolean boolean_chance = MathUtils.randomBoolean(chance);
			if (boolean_chance) {
				if (MathUtils.randomBoolean(0.6f)) {
					platform.setLevel(Platform.Level.DYNAMIC_X);
				} else {
					platform.setLevel(Platform.Level.DYNAMIC_X_CIRCLE);
				}

				if (MathUtils.randomBoolean(0.3f)) {
					platform.setLevel(Platform.Level.BROKEN);
				}
				if (MathUtils.randomBoolean(0.1f)) {
					platform.setLevel(Platform.Level.NORMAL);
				}
			}
		}

		platform.position.x = MathUtils.random(scr_width - platform.getWidth());
		platform.position.y = height;
		this.maxY = height;
	}

	private void generateEnemy() {
		enemy_partycat = new Enemy(Enemy.TYPE.partycat);
		enemy_partycat.position.set(0, MathUtils.random(cat.position.y + scr_height,  1.5f * maxY));
	}

	private void updateWorld() {
		// TODO Auto-generated method stub
		if (gameState == GameState.Ready) {
			if (Gdx.input.justTouched()) {
				gameState = GameState.Start;
				music.play();
			}
		}
		float deltaTime = Gdx.graphics.getDeltaTime();
		if (Math.abs(cat.velocity.y) > 100)
			cat.scarfStateTime += deltaTime;
		if (cat.playFootAnimation == true) {
			cat.footStateTime += cat.anim_foot.getFrameDuration();
			if (cat.footStateTime > cat.anim_foot.getAnimationDuration() * 3) {
				cat.playFootAnimation = false;
			}
		} else {
			cat.footStateTime = 0;
		}
		if (cat.hasRocket) {
			cat.rocketStateTime += deltaTime;
		} else {
			cat.rocketStateTime = 0;
		}
		if (cat.hasSupercat) {
			cat.supercatStateTime += deltaTime;
		} else {
			cat.supercatStateTime = 0;
		}
		if (cat.rocket.isBroken && gameState == GameState.Start && !cat.canThroughThings) {
			rocket.stop();
			music.play();
		}
		if (gameState == GameState.Start) {
			cat.velocity.add(gravity);
			cat.position.mulAdd(cat.velocity, deltaTime);
			cat.rocket.update(gravity, deltaTime);
			enemy_partycat.update(gravity,deltaTime);
		}
		if (cat.position.y > old_score)
			score = (int) cat.position.y;
		if (cat.velocity.y >= 0
				&& cat.position.y + scr_height / 2 - 500 > camera.position.y) {
			camera.position.y = cat.position.y + scr_height / 2 - 500;
		}
		if (!cat.isDead && !cat.canThroughThings) {
			float accleration = 0;
			if (Gdx.app.getType() == ApplicationType.Android
					|| Gdx.app.getType() == ApplicationType.iOS) {
				accleration = -(Gdx.input.getAccelerometerX() * 180f);
			} else {
				if (Gdx.input.isKeyPressed(Keys.DPAD_LEFT)) {
					accleration = -300f;
				}
				if (Gdx.input.isKeyPressed(Keys.DPAD_RIGHT)) {
					accleration = 300f;
				}
				if (Gdx.input.isKeyPressed(Keys.DPAD_UP)) {
					cat.velocity.y = 10000;
				}
			}
			cat.velocity.x = accleration;
		}
		platformToRemove.clear();
		for (Platform plat : platforms) {
			if (plat.position.y < camera.position.y - scr_height / 2
					- plat.getHeight()) {
				plat.setRemoved(true);
				minY = plat.position.y;
				generatePlatform(plat);
				continue;
			}

			if (plat.getLevel() == Platform.Level.DYNAMIC_X) {
				plat.moveX();
			} else if (plat.getLevel() == Platform.Level.DYNAMIC_X_CIRCLE) {
				plat.moveXCircle();
			}
			if (gameState == GameState.Start) {
				Rectangle rect1 = new Rectangle();
				Rectangle rect2 = new Rectangle();
				rect1.setPosition(cat.position);
				rect1.setSize(cat.getWidth(), 1);
				rect2.setPosition(plat.position);
				rect2.setSize(plat.getWidth(), plat.getHeight());
				if (rect1.overlaps(rect2)) {// crash platform
					if (cat.velocity.y < 0 && !cat.isDead && !cat.canThroughThings) {
						cat.jump();
						hitPlatform.play();
						if (plat.getLevel() == Platform.Level.BROKEN) {
							plat.setRemoved(true);
							generatePlatform(plat);
							continue;
						}
					}
				}
				Rectangle rect_foot = new Rectangle(rect1);
				Rectangle rect_body = new Rectangle(rect1);
				Rectangle rect_monster = new Rectangle();
				rect_monster.setPosition(enemy_partycat.position);
				rect_monster.setSize(enemy_partycat.getWidth(),
						enemy_partycat.getHeight());
				rect_body.setPosition(cat.position.x, cat.position.y + 1);
				rect_body.setSize(cat.getWidth(), cat.getHeight() - 1);// crash monster
				if(rect_foot.overlaps(rect_monster) && !cat.canThroughThings && !cat.isDead && !cat.hasRocket && !cat.hasSupercat)
				{
					enemy_partycat.isDie = true;
					cat.velocity.y = 2000;
					enemyDie.play(0.05f);
				}
				else if (rect_body.overlaps(rect_monster) && !cat.canThroughThings && !cat.isDead && !cat.hasRocket && !cat.hasSupercat) {
					music.stop();
					canThroughSound.play();
					cat.canThroughThings = true;
					cat.velocity.y = 600;
					cat.velocity.x = MathUtils.random(-100, 100);
				}
				plat.position.add(plat.velocity);
				if (plat.hasSpring) {
					rect2.setPosition(plat.getSpring().position);
					rect2.setSize(plat.getSpring().getWidth(), plat.getSpring()
							.getHeight() + 3);
					if (rect1.overlaps(rect2)) {
						if (cat.velocity.y <= 0 && !cat.isDead && !cat.canThroughThings) {
							hitSpring.play();
							cat.hitSpring();
						}
					}
				} else if (plat.hasRocket) {
					rect2.setPosition(plat.getRocket().position);
					rect2.setSize(plat.getRocket().getWidth(), plat.getRocket()
							.getHeight());
					if (rect1.overlaps(rect2)) {
						if (cat.velocity.y < 0 && !cat.isDead && !cat.canThroughThings) {
							cat.rocket.isBroken = false;
							music.pause();
							rocket.play();
							plat.hasRocket = false;
							cat.hitRocket();
						}
					}
				} else if (plat.hasSupercat) {
					rect2.setPosition(plat.getSupercat().position);
					rect2.setSize(plat.getSupercat().getWidth(), plat.getSupercat()
							.getHeight());
					if (rect1.overlaps(rect2)) {
						if (cat.velocity.y <= 0 && !cat.isDead && !cat.canThroughThings) {
							cat.hitSupercat();
						}
					}
				}
			}
		}
		if (cat.position.y < minY - 1000 && !cat.isDead) {
			gameOver();
		}
		if(enemy_partycat.position.y < minY)
		{
			generateEnemy();
		}
		if (Gdx.input.justTouched() && gameState == GameState.Gameover) {
			gameState = GameState.Start;
			resetWorld();
		}
		old_score = score;
		old_cat_postion_y = cat.position.y;
	}

	private void gameOver() {
		cat.isDead = true;
		highestScore = p.getInteger("score", 0);
		if (score > highestScore) {
			hasNewScore = true;
			p.putInteger("score", score);
			p.flush();
			highestScore = score;
		}
		gameState = GameState.Gameover;
		music.stop();
		dead.play();
	}

	private void drawWorld() {
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		drawBackground();
		batch.draw(backgroundTile, 0, 0);
		for (Platform plat : platforms) {
			plat.draw(batch);
			// font.draw(batch,platforms.indexOf(plat) + ":"+plat.position.y +
			// "", plat.position.x, plat.position.y);
		}
		enemy_partycat.draw(batch);
		cat.draw(batch);
		if (cat.rocket.isBroken)
			cat.rocket.draw(batch);
		batch.end();

		uiCamera.update();
		batch.setProjectionMatrix(uiCamera.combined);
		batch.begin();
		if (gameState == GameState.Ready) {
			batch.draw(ready, scr_width / 2 - ready.getWidth() / 2, scr_height
					/ 2 - ready.getHeight() / 2);
		}
		if (gameState == GameState.Gameover) {
			batch.draw(gameOver, scr_width / 2 - gameOver.getWidth() / 2,
					scr_height / 2 + gameOverPanel.getRegionHeight() / 2);
			batch.draw(gameOverPanel,
					scr_width / 2 - gameOverPanel.getRegionWidth() / 2,
					scr_height / 2 - gameOverPanel.getRegionHeight() / 2);
			drawDigtalCenterY(batch, digital_small, score + "", scr_height / 2
					+ digital_small[0].getRegionHeight() / 2 - 10);
			drawDigtalCenterY(batch, digital_small, highestScore + "",
					scr_height / 2 - digital_small[0].getRegionHeight() / 2
							- 40);
			if (hasNewScore)
				batch.draw(newTexture,
						scr_width / 2 + String.valueOf(score).length()
								* (digital_small[0].getRegionWidth() / 2) + 10,
						scr_height / 2 - newTexture.getRegionHeight() / 2 + 20);
		}
		drawDigtalTopLeft(batch, digital, score + "");
		batch.end();
		// font.draw(batch, score+"", 0,camera.position.y + scr_height / 2);
	}

	private void drawBackground() {
		
		if(score >= (int)ranBg2)
		{
			backgroundTile.setTexture(new Texture(
					"images/theme_city_bg.png"));
		}else if(score >= (int)ranBg1)
		{
			backgroundTile.setTexture(new Texture(
					"images/theme_supernyanio_bg.png"));
		}else
		{
			backgroundTile.setTexture(new Texture(
					"images/theme_valentine_bg.png"));
		}
		
		int x_count = scr_width % backgroundTile.getRegionWidth() == 0 ? scr_width
				/ backgroundTile.getRegionWidth()
				: scr_width / backgroundTile.getRegionWidth() + 1;
		int y_count = scr_height % backgroundTile.getRegionHeight() == 0 ? scr_height
				/ backgroundTile.getRegionHeight()
				: scr_height / backgroundTile.getRegionHeight() + 1;
		for (int i = 0; i < x_count; i++) {
			for (int j = 0; j < y_count; j++) {
				backgroundTile.flip(false, true);
				batch.draw(
						backgroundTile,
						i * backgroundTile.getRegionWidth(),
						camera.position.y + j
								* backgroundTile.getRegionHeight() - 600);
			}
		}
	}

	private void drawDigtalTopLeft(SpriteBatch batch, TextureRegion digital[],
			String str) {
		for (int i = 0; i < str.length(); i++) {
			int index = str.charAt(i) - '0';
			batch.draw(digital[index], digital[index].getRegionWidth() / 2
					* (1 + i * 2) - 20,
					scr_height - digital[index].getRegionHeight());
		}
	}

	private void drawDigtalCenterY(SpriteBatch batch, TextureRegion digital[],
			String str, int y) {
		for (int i = 0; i < str.length(); i++) {
			int index = str.charAt(str.length() - 1 - i) - '0';
			batch.draw(digital[index],
					scr_width / 2 - digital[index].getRegionWidth() / 2
							* (1 + i * 2) + digital[index].getRegionWidth()
							* (str.length() - 1) / 2, y);
		}
	}

	public static enum GameState {
		Ready, Start, Gameover
	}
}
