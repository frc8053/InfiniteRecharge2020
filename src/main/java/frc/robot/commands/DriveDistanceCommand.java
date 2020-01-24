/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveDistanceCommand extends CommandBase {
  private DriveTrain driveTrain;
  
  private double left;
  private double right;
  private double distance;
  
  
  /**
   * Creates a new DriveStraightCommand.
   * @param left Speed of the left side of the robot
   * @param right Speed of the right side of the robot
   * @param distance Total distance the right encoder reads
   * 
   * @param driveTrain The DriveTrain subsystem used by this command
   */

  public DriveDistanceCommand(double left, double right, double distance, DriveTrain driveTrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.left = left;
    this.right = right;
    this.distance = distance;
    this.driveTrain = driveTrain;
    this.addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.leftEncoderReset(); 
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    driveTrain.tankDrive(left, right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (distance >= driveTrain.leftEncoderValue()) {
      return true;
    } else {
      return false;
    }
  }
}
