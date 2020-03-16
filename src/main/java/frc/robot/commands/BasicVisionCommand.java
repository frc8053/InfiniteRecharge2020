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

public class BasicVisionCommand extends CommandBase {
  private DriveTrain driveTrain;
  private boolean finished;
  private boolean isTurning;
  private boolean isTurnNegative;

  /**
   * Basic command to turn to vision target.
   * @param driveTrain the driveTrain subsystem used
   */
  public BasicVisionCommand(DriveTrain driveTrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.driveTrain = driveTrain;
    finished = false;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    finished = false;
    isTurning = false;
    isTurnNegative = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (driveTrain.getShootVisionYaw() > 0.5) {
      if (isTurning && isTurnNegative) {
        driveTrain.arcadeDrive(0, 0);
        isTurning = false;
        finished = true;
        return;
      }
      isTurning = true;
      driveTrain.arcadeDrive(0, -0.45);
    }
    
    if (driveTrain.getShootVisionYaw() < -0.5) {
      if (isTurning && !isTurnNegative) {
        driveTrain.arcadeDrive(0, 0);
        isTurning = false;
        finished = true;
        return;
      }
      isTurnNegative = true;
      isTurning = true;
      driveTrain.arcadeDrive(0, 0.45);
    } 
    if (Math.abs(driveTrain.getShootVisionYaw()) < 0.5) {
      driveTrain.arcadeDrive(0, 0);
      isTurning = false;
      finished = true;
    }
    SmartDashboard.putNumber("RPM Requested", TrajectoryMath.getVelocityFromDistance(
        TrajectoryMath.getDistanceFromPitch(driveTrain.getShootVisionPitch())));
    SmartDashboard.putNumber("Rotation Adjust", driveTrain.getShootVisionYaw());
    SmartDashboard.putNumber("Distance From", TrajectoryMath.getDistanceFromPitch(
        driveTrain.getShootVisionPitch()));
    //SmartDashboard.putNumber("Velocity",TrajectoryMath.getVelocityFromDistance(25));
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return finished;
  }
}
