package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Assets {
	public static TextureAtlas theme_commonAtlas;
	public static TextureAtlas tpAtlas;
	public static TextureRegion rocket_no_fire;
	public static Texture springTexture;
	public static TextureAtlas skin_catheroAtlas;
	public static Sprite cat_scat_01Sprite;
	public static Sprite cat_scat_02Sprite;
	public static Sprite cat_scat_03Sprite;
	public static Sprite cat_scat_04Sprite;
	public static Sprite powerup_supercatSprite;
	public static Sprite powerup_rocketeerSprite;
	public static Animation supercatAnimation;
	public static void load()
	{
		tpAtlas = new TextureAtlas("images/tp.xml.xmf");
		theme_commonAtlas = new TextureAtlas("images/theme_common.xml.xmf");
		rocket_no_fire = new TextureRegion(new Texture("images/rocket_no_fire.png"));
		springTexture = new Texture("images/spring.png");
		skin_catheroAtlas = new TextureAtlas(Gdx.files.internal("images/skin_cathero.xml.xmf"));
		cat_scat_01Sprite = skin_catheroAtlas.createSprite("cat_scat_01");
		cat_scat_02Sprite = skin_catheroAtlas.createSprite("cat_scat_02");
		cat_scat_03Sprite = skin_catheroAtlas.createSprite("cat_scat_03");
		cat_scat_04Sprite = skin_catheroAtlas.createSprite("cat_scat_04");
		powerup_supercatSprite = theme_commonAtlas.createSprite("powerup_supercat");
		powerup_rocketeerSprite = theme_commonAtlas.createSprite("powerup_rocketeer");
		supercatAnimation = new Animation(0.05f, cat_scat_01Sprite,cat_scat_02Sprite,cat_scat_03Sprite,cat_scat_04Sprite);
		supercatAnimation.setPlayMode(PlayMode.LOOP);
	}
}
