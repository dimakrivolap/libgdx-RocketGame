
package com.badlogic.rockety;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.utils.Array;

public class Map {
	static int EMPTY = 0;
	static int TILE = 0xffffff;
	static int START = 0xff0000;
	static int END = 0xff00ff;
	static int DISPENSER = 0xff0100;
	static int SPIKES = 0x00ff00;
	static int ROCKET = 0x0000ff;

	int[][] tiles;
	public Bob bob;
	Array<Dispenser> dispensers = new Array<Dispenser>();
	Dispenser activeDispenser = null;
	Array<Rocket> rockets = new Array<Rocket>();
	public EndDoor endDoor;

	public Map () {
		loadBinary();
	}

	private void loadBinary () {
		Pixmap pixmap = new Pixmap(Gdx.files.internal("data/levels.png"));
		tiles = new int[pixmap.getWidth()][pixmap.getHeight()];
		for (int y = 0; y < 35; y++) {
			for (int x = 0; x < 150; x++) {
				int pix = (pixmap.getPixel(x, y) >>> 8) & 0xffffff;
				if (match(pix, START)) {
					Dispenser dispenser = new Dispenser(x, pixmap.getHeight() - 1 - y);
					dispensers.add(dispenser);
					activeDispenser = dispenser;
					bob = new Bob(this, activeDispenser.bounds.x, activeDispenser.bounds.y);
					bob.state = Bob.SPAWN;
				} else if (match(pix, DISPENSER)) {
					Dispenser dispenser = new Dispenser(x, pixmap.getHeight() - 1 - y);
					dispensers.add(dispenser);
				} else if (match(pix, ROCKET)) {
					Rocket rocket = new Rocket(this, x, pixmap.getHeight() - 1 - y);
					rockets.add(rocket);
				}
				  else if (match(pix, END)) {
					endDoor = new EndDoor(x, pixmap.getHeight() - 1 - y);
				} else {
					tiles[x][y] = pix;
				}
			}
		}


	}

	boolean match (int src, int dst) {
		return src == dst;
	}

	public void update (float deltaTime) {
		bob.update(deltaTime);
		if (bob.state == Bob.DEAD) bob = new Bob(this, activeDispenser.bounds.x, activeDispenser.bounds.y);

		for (int i = 0; i < dispensers.size; i++) {
			if (bob.bounds.overlaps(dispensers.get(i).bounds)) {
				activeDispenser = dispensers.get(i);
			}
		}
		for (int i = 0; i < rockets.size; i++) {
			Rocket rocket = rockets.get(i);
			rocket.update(deltaTime);
		}

	}

	public boolean isDeadly (int tileId) {
		return tileId == SPIKES;
	}
}
