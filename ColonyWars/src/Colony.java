import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class Colony {
	
	ArrayList<Unit> unitList;
	Color color;
	int maxDistance;
	int reproductionRarity; 
	int deathAge;
	int reproductiveAge;
	int r;
	int g;
	int b;
	int unitCount = 0;
	Random random = new Random();
	
	public Colony(ArrayList<Unit> unitList) {
		this.unitList = unitList;
		r=random.nextInt(255);
		g=random.nextInt(255);
		b=random.nextInt(255);
		color = new Color(r,g,b);
		maxDistance = random.nextInt(Grid.maxDistanceBound) + Grid.maxDistanceMinimum;
		reproductionRarity = random.nextInt(Grid.reproductionRarityBound) + Grid.reproductionRarityMinimum;
		deathAge = random.nextInt(Grid.deathAgeBound) + Grid.deathAgeMinimum;
		reproductiveAge = random.nextInt(Grid.reproductiveAgeBound) + Grid.reproductiveAgeMinimum;
	}
	
	
	
}
