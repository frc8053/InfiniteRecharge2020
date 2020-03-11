/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.DrivePid;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class BallAlignmentCommand extends PIDCommand {
  DriveTrain driveTrain;
  Intake intake;

  /**
   * Aligns the robot to the power cell. Allows the driver to control forward
   * and backward motion
   * @param controller the controller used to control forward and backward movement
   * @param driveTrain the driveTrain subsystem used
   */
  public BallAlignmentCommand(XboxController controller, DriveTrain driveTrain, Intake intake) {
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
            driveTrain.arcadeDrive(controller.getY(Hand.kLeft) * 0.6, 0.75);
          } 
          if (output < -0.75) {
            driveTrain.arcadeDrive(controller.getY(Hand.kLeft) * 0.6, -0.75);
          } 
          if (Math.abs(output) < 0.75) {
            driveTrain.arcadeDrive(controller.getY(Hand.kLeft) * 0.6, output);
          }
        });
    getController().enableContinuousInput(-180, 180);
    getController().setTolerance(DrivePid.TURN_TOLERANCE);
    this.driveTrain = driveTrain;
    this.intake = intake;
    addRequirements(driveTrain, intake);
  }

  @Override
  public void initialize() {
    driveTrain.setIntakeDriverMode(false);
    super.initialize();
  }

  @Override
  public void execute() {
    SmartDashboard.putNumber("Ball Alignment PID Result", 
        getController().calculate(driveTrain.getIntakeVisionYaw(), 0));
    if (driveTrain.getIntakeVisionPitch() < -6 && driveTrain.findIntakeTarget()) {
      intake.intakeBar(0.8);
      intake.conveyorControl(0.6);
    }
    super.execute();
  }

  @Override
  public void end(boolean interrupted) {
    driveTrain.setIntakeDriverMode(true);
    super.end(interrupted);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
