/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.modules.TrajectoryMath;
import frc.robot.subsystems.DriveTrain;

public class VisionAlignCommand extends CommandBase {
  /**
   * Aligns to vision target using a P controller.
   */
  private DriveTrain driveTrain;

  /*
  PID Variables
  */
  double rotationError;
  double distanceError;
  double KpRot = -0.05;
  double KpDist = -0.01;
  double angleTolerance = 5; //Deadzone for the angle control loop
  double distanceTolerance = 5; //Deadzone for the distance control loop
  double constantForce = 0.25;
  double rotationAjust;
  double distanceAjust;

  public VisionAlignCommand(DriveTrain drive) {
    driveTrain = drive;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    rotationError = driveTrain.getShootVisionYaw();
    distanceError = driveTrain.getShootVisionPitch();

    if (rotationError > angleTolerance) {
      rotationAjust = constantForce;
    } else {
      rotationAjust =  -constantForce;
    }
    driveTrain.arcadeDrive(0, rotationAjust);
    SmartDashboard.putNumber("Distance Adjuist", distanceAjust);
    SmartDashboard.putNumber("Rotation Adjust", rotationAjust);
    SmartDashboard.putNumber("Distance From", TrajectoryMath.getDistanceFromPitch(distanceError));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
