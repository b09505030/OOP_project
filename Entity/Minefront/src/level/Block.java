package level;

import java.util.ArrayList;
import java.util.List;

import entity.Entity;
import graphics.Sprite;
import graphics.Sprite2;

public class Block {
	
	public boolean solid = false;
	public boolean blocksMotion = false;
	
	public static Block solidWall = new SolidBlock();
	public static Block nonsolidWall = new NonSolidWall();
	
	public List<Sprite> sprites = new ArrayList<Sprite>();
	public List<Sprite2> sprites2 = new ArrayList<Sprite2>();
	public List<Entity> entities = new ArrayList<Entity>();
	
	public int tex = -1;
	public int col = -1;
	
	public int floorCol = -1;
	public int ceilCol = -1;
	
	public int floorTex = -1;
	public int ceilTex = -1;
	
	public Level level;//作為level的init 時會用到
	
	/**
	 * 作為每個方塊的x、y座標
	 */
	public int x, y;
	
	public int id;
	
	

	public void addSprite(Sprite sprite) {
		sprites.add(sprite);
	}
	
	public void addSprite2(Sprite2 sprite2) {
		sprites2.add(sprite2);
	}
	
	public void tick() {//尚未研究未知實際用途
		for (int i = 0; i < sprites.size(); i++) {
			Sprite sprite = sprites.get(i);
			sprite.tick();
			if (sprite.removed) {
				sprites.remove(i--);
			}
		}
	}
	
	public void removeEntity(Entity entity) {
		entities.remove(entity);
	}
	
	public void addEntity(Entity entity) {
		entities.add(entity);
	}
	
	
	public boolean blocks(Entity entity) {
		return blocksMotion;
	}

	public void decorate(Level level, int x, int y) {
		// TODO Auto-generated method stub
		
	}
}
