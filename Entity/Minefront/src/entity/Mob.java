package entity;

import entity.Entity;

public class Mob extends Entity {

	public void move(int xa, int za, double rot) {
		if(xa != 0 && za != 0) {
			move(xa, 0, rot);
			move(0, za, rot);
			return;
		}
		
		double nx = xa *Math.cos(rot) + za* Math.sin(rot);
		double nz = za* Math.cos(rot) - xa *Math.sin(rot);
		nx *= 2.0;
		nz *= 2.0;
		x += nx;
		z += nz;
		
	}

}