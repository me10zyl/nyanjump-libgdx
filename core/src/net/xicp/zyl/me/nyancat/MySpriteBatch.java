package net.xicp.zyl.me.nyancat;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MySpriteBatch extends SpriteBatch {
	
	@Override
	public void draw (TextureRegion region, float x, float y) {
		System.out.println(region);
		draw(region, x, y, region.getRegionWidth(), region.getRegionHeight());
	}
}
