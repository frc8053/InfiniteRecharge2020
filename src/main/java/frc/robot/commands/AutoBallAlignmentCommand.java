/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DrivePid;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class AutoBallAlignmentCommand extends PIDCommand {
  DriveTrain driveTrain;
  Intake intake;

  /**
   * Ball Alignment Command used in autonomous.
   * @param speed the speed subsystem used
   * @param driveTrain the driveTrain subsystem used
   * @param intake the intake subsystem used
   */
  public AutoBallAlignmentCommand(double speed, DriveTrain driveTrain, Intake intake) {
    super(
        // The controller that the command will use
        new PIDController(DrivePid.P_BALL, DrivePid.I_BALL, DrivePid.D_BALL),
        // This should return the measurement
        driveTrain::getIntakeVisionYaw,
        // This should return the setpoint (can also be a constant)
        0,
        // This uses the output
        output -> {
          if (output > 0.75) {
            driveTrain.arcadeDrive(speed, 0.75);
          } 
          if (output < -0.75) {
            driveTrain.arcadeDrive(speed, -0.75);
          } 
          if (Math.abs(output) < 0.75) {
            driveTrain.arcadeDrive(speed, output);
          }
        });
    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(DrivePid.TURN_TOLERANCE);
    this.driveTrain = driveTrain;
    this.intake = intake;
    addRequirements(driveTrain, intake);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
