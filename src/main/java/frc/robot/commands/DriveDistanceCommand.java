/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.subsystems.DriveTrain;

public class DriveDistanceCommand extends PIDCommand {
  private DriveTrain driveTrain;
  
  /**
   * Drives forward a certain distance.
   * 
   * @param distance Total distance the right encoder reads
   * @param driveTrain The DriveTrain subsystem used by this command
   */

  public DriveDistanceCommand(double distance, DriveTrain driveTrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    super(
        new PIDController(1, 0, 0),
        //input
        driveTrain::leftEncoderValue,
        //setpoint
        distance,
        //output
        output -> {
          driveTrain.tankDrive(output, output);
        },
        driveTrain
    );
    this.driveTrain = driveTrain;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    driveTrain.leftEncoderReset(); 
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.tankDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (getController().atSetpoint()) {
      return true;
    } else {
      return false;
    }
    
  }
}
