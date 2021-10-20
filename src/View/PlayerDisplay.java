package View;

/**
 * This class defines the Display information for player
 * such as reference line and target indicator for infrared and radar system
 * @author WangShuzheng
 */

import java.util.ArrayList;

import ControlLogic.PlayerControl;
import Controller.GameWindowController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import Object.Object;
import Physics.Locating;

public class PlayerDisplay {
	
	/**
	 * Attributes
	 */
	
	private PlayerControl MyController;
	
	private ArrayList<ImageView> Indicator = new ArrayList<ImageView>();
	
	private ImageView InfraredUpperLine;
	private ImageView InfraredLowerLine;
	private ImageView InfraredTargetView;
	
	private ImageView RadarUpperLine;
	private ImageView RadarLowerLine;
	private ArrayList<ImageView> RadarTarget = new ArrayList<ImageView>();
	private ArrayList<Text> RadarTargetDistance = new ArrayList<Text>();
	
	private ArrayList<ImageView> MissleTarget = new ArrayList<ImageView>();
	private ArrayList<Text> MissleTargetDistance = new ArrayList<Text>();
	
	private ImageView EyeLine;

	/**
	 * Constructor
	 * @param MyController control logic instance of player
	 */
	
	public PlayerDisplay(PlayerControl MyController) {
		super();
		this.MyController = MyController;
		
		Image ID = new Image("/Image/Indicator/TargetNormal.png");
		for(int i = 0;i<9;i++)
		{
			this.Indicator.add(new ImageView(ID));
			this.Indicator.get(i).setVisible(false);
		}
		
		Image IFL = new Image("/Image/Indicator/InfraredLine.png");
		this.InfraredUpperLine = new ImageView(IFL);
		this.InfraredUpperLine.setVisible(false);
		this.InfraredLowerLine = new ImageView(IFL);
		this.InfraredLowerLine.setVisible(false);
		
		Image IFT = new Image("/Image/Indicator/TargetInfrared.png");
		this.InfraredTargetView = new ImageView(IFT);
		this.InfraredTargetView.setVisible(false);
		
		Image RL = new Image("/Image/Indicator/RadarLine.png");
		this.RadarUpperLine = new ImageView(RL);
		this.RadarUpperLine.setVisible(false);
		this.RadarLowerLine = new ImageView(RL);
		this.RadarLowerLine.setVisible(false);
		
		Image RD = new Image("/Image/Indicator/TargetRadar.png");
		for(int i = 0;i<6;i++)
		{
			this.RadarTarget.add(new ImageView(RD));
			this.RadarTargetDistance.add(new Text(""));
			this.RadarTargetDistance.get(i).setVisible(false);
			this.RadarTargetDistance.get(i).setFill(Color.GREEN);
			this.RadarTarget.get(i).setVisible(false);
		}
		
		for(int i=0;i<32;i++)
		{
			this.MissleTarget.add(new ImageView("/Image/Indicator/TargetFriendRadar.png"));
			this.MissleTargetDistance.add(new Text(""));
			this.MissleTargetDistance.get(i).setVisible(false);
			this.MissleTargetDistance.get(i).setFill(Color.GREEN);
			this.MissleTarget.get(i).setVisible(false);
		}
		
		this.EyeLine = new ImageView("/Image/Indicator/EyeLine.png");
		this.EyeLine.setVisible(false);
	}
	
	public ImageView getEyeLine() {
		return EyeLine;
	}
	
	public ImageView getInfraredUpperLine() {
		return InfraredUpperLine;
	}

	public ImageView getInfraredLowerLine() {
		return InfraredLowerLine;
	}

	public ImageView getRadarUpperLine() {
		return RadarUpperLine;
	}

	public ImageView getRadarLowerLine() {
		return RadarLowerLine;
	}

	public ImageView getInfraredTargetView() {
		return InfraredTargetView;
	}

	public ArrayList<ImageView> getRadarTarget() {
		return RadarTarget;
	}

	public ArrayList<Text> getRadarTargetDistance() {
		return RadarTargetDistance;
	}

	public ArrayList<ImageView> getMissleTarget() {
		return MissleTarget;
	}

	public ArrayList<Text> getMissleTargetDistance() {
		return MissleTargetDistance;
	}

	public ArrayList<ImageView> getIndicator() {
		return Indicator;
	}

	/**
	 * Set layout of a indicator line 
	 * @param line the image view of the line
	 * @param direction the direction of the line
	 */
	
	public void setIndicatorLine(ImageView line, double direction)
	{
			double viewDirection = MyController.getMyAircraft().getVelocity().getDirection()+MyController.getMyAircraft().getAOA()+direction;
			double x = MyController.getMyAircraft().getX()+960*Math.cos(Math.PI*(viewDirection)/180);
			double y = MyController.getMyAircraft().getY()+960*Math.sin(Math.PI*(viewDirection)/180);
			line.setLayoutX(x-GameWindowController.CameraX-line.getImage().getWidth()/2);
			line.setLayoutY(-y+GameWindowController.CameraY-line.getImage().getHeight()/2);
			line.setRotate(-viewDirection);
	}
	
	/**
	 * Set layout of a target indicator
	 * @param targetIndicator the image view of the target indicator
	 * @param target the target indicated
	 * @return if the indicator is needed to show
	 */
	
	public boolean setIndicatorTarget(ImageView targetIndicator, Object target)
	{
		boolean needIndicator = false;
		if(target.getY()>MyController.getMyAircraft().getY()+500)
		{
			targetIndicator.setLayoutY(-500);
			targetIndicator.setVisible(true);
			needIndicator = true;
		}
		else if(target.getY()<MyController.getMyAircraft().getY()-460)
		{
			targetIndicator.setLayoutY(460);
			targetIndicator.setVisible(true);
			needIndicator = true;
		}
		else 
		{
			targetIndicator.setLayoutY(MyController.getMyAircraft().getY()-target.getY());
		}
		
		if(target.getX()<MyController.getMyAircraft().getX()-920)
		{
			targetIndicator.setLayoutX(-920);
			targetIndicator.setVisible(true);
			needIndicator = true;
		}
		else if(target.getX()>MyController.getMyAircraft().getX()+920)
		{
			targetIndicator.setLayoutX(920);
			targetIndicator.setVisible(true);
			needIndicator = true;
		}
		else 
		{
			targetIndicator.setLayoutX(target.getX()-MyController.getMyAircraft().getX());
		}
		targetIndicator.setRotate(-Locating.getRelativeAngle(MyController.getMyAircraft(),target));
		return needIndicator;
	}
	
	/**
	 * Set layout of a distance indicator
	 * @param distance the text of the distance indicator
	 * @param target the target indicated
	 * @return if the distance indicator is needed to show
	 */
	
	public boolean setDistanceIndicator(Text distance, Object target)
	{
		boolean needIndicator = false;
		if(target.getY()>MyController.getMyAircraft().getY()+500)
		{
			distance.setText(Integer.toString((int)(Locating.getDistance(MyController.getMyAircraft(),target))));;
			distance.setLayoutY(-450);
			needIndicator = true;
		}
		else if(target.getY()<MyController.getMyAircraft().getY()-460)
		{
			distance.setText(Integer.toString((int)(Locating.getDistance(MyController.getMyAircraft(),target))));;
			distance.setLayoutY(-410);
			needIndicator = true;
		}
		else 
		{
			distance.setText(Integer.toString((int)(Locating.getDistance(MyController.getMyAircraft(),target))));;
			distance.setLayoutY(MyController.getMyAircraft().getY()-target.getY());
		}
		
		if(target.getX()<MyController.getMyAircraft().getX()-920)
		{
			distance.setText(Integer.toString((int)(Locating.getDistance(MyController.getMyAircraft(),target))));;
			distance.setLayoutX(-870);
			needIndicator = true;
		}
		else if(target.getX()>MyController.getMyAircraft().getX()+920)
		{
			distance.setText(Integer.toString((int)(Locating.getDistance(MyController.getMyAircraft(),target))));;
			distance.setLayoutX(870);
			needIndicator = true;
		}
		else 
		{
			distance.setLayoutX(target.getX()-MyController.getMyAircraft().getX());
		}
		return needIndicator;
	}
}
