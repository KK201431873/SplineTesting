package movement.movements;

import movement.util.DisplacementCalculator;
import movement.util.Movement;
import movement.util.Pose;
import teamcode_util.DriveConstants;

public class StraightLine extends Movement{
	
	private double distance, time;
	private Pose startPose, endPose;
	private DisplacementCalculator calculator;
	private Turn turn;
	
	public StraightLine(Pose startPose, Pose endPose) {
		this.startPose = startPose;
		this.endPose = endPose;
		init();
	}

	@Override
	public Pose getPose(double elapsedTime) {
		double t = calculator.getDisplacement(elapsedTime) / distance;

		double q0 = 1 - t;
		double q1 = t;

		double tx = startPose.getX()*q0 + endPose.getX()*q1;
		double ty = startPose.getY()*q0 + endPose.getY()*q1;
		double theading = turn.getPose(elapsedTime).getHeading();

		return new Pose(tx, ty, theading);
	}

	@Override
	public double getTime() {
		return Math.max(time, turn.getTime());
	}
	
	public Pose getStartPose() {
		return startPose;
	}

	@Override
	public Pose getEndPose() {
		return endPose;
	}
	
	private void init() {
		distance = Math.hypot(endPose.getX()-startPose.getX(), endPose.getY()-startPose.getY());

		calculator = new DisplacementCalculator(distance, DriveConstants.MAX_VELOCITY, DriveConstants.MAX_ACCELERATION);
		
		time = calculator.getTime();
		
		turn = new Turn(startPose, endPose.getHeading()-startPose.getHeading());
		
	}

}
