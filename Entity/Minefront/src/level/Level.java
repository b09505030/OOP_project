package level;

import entity.Entity;
import graphics.Sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Level {

	public Block[] blocks;
	public int width, height;
	private Block solidWall = new SolidBlock();
	final Random random = new Random();

	private List<Entity> entities = new ArrayList<Entity>();

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		blocks = new Block[width * height];
		generateLevel();
	}

	public void tick() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
			//e.updatePos();//更新位置?
			
			if (e.isRemoved()) {//未知-1
				entities.remove(i--);//似乎生成後移除
			}
		}

		for (int y = 0; y < height; y++) {//未知-2
			for (int x = 0; x < width; x++) {
				blocks[x + y * width].tick();//似乎生成後移除
			}
		}
		
	}

	public void addEntity(Entity e) {
		entities.add(e);
		e.level = this;
		/**
		 *   更新位置
		 */
		//e.updatePos();
	}

	public void generateLevel() {
		
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Block block = null;
				if (random.nextInt(500) == 0) {
					block = new SolidBlock();
				} else {
					block = new Block();
					if (random.nextInt(25) == 0) {
						block.addSprite(new Sprite(0, 0, 0));
					}
				}
				blocks[x + y * width] = block;
				//block.level = this;//尚未了解用途
				//block.x = x;
				//block.y = y;
				
			}
		}

	}

	public Block getBlock(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			//System.out.println("used");
			//System.out.println("width = "+width);
			return solidWall;
		}
		//System.out.println("In Level.java 82 : "+(blocks[x + y * width]));
		return blocks[x + y * width];
	}

	public Block create(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Block.solidWall;
		}
		return blocks[x + y * width];

	}

}
