import processing.core.*;

// Your final solution should work with an unmodified version
// of this file.  You may make changes here for testing purposes,
// but ensure that your final solution works after reverting
// those changes below.

public class Main extends PApplet
{
	public DrawableTree dtree;
	public double testAccuracy;
	
	public void setup()
	{				
		size(800,600);		
		dtree = new Yunhe_Liu_DecisionTree(this);
		
		dtree.learnFromTrainingData("trainData.xml");
		testAccuracy = dtree.runTests("testData.xml");
	}
	
	public void draw()
	{
		background(255);

		dtree.draw();
		
		fill(255,0,0);
		text(dtree.getClass().getName() + " Accuracy: " + testAccuracy,32,32);
	}
	
	public static void main(String[] args)
	{
		PApplet.main(new String[] { "Main" } );
	}	
}
