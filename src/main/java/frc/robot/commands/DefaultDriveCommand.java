/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.modules.Pipelines;
import frc.robot.subsystems.DriveTrain; 
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class DefaultDriveCommand extends CommandBase {
    
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveTrain driveTrain;

  private DoubleSupplier leftY;
  private DoubleSupplier rightY;
  private DoubleSupplier rightX;
  private Supplier<Boolean> isAReleased;
  private Supplier<Boolean> isLeftBrake;
  private Supplier<Boolean> isRightBrake;

  private double speed;
  private double reverse;
  private boolean driveState;
  private String driveMode;
  private String reversed;

  /**
   * Drives the robot using the joysticks on the gamepad. Can
   * be tank or arcade drive.
   *
   * @param leftY The leftY joystick of driver gamepad
   * @param rightY The RightY joystick of driver gamepad
   * @param rightX rightX joystick of driver gamepad
   * 
   * @param isAReleased Whether A has been releaseed
   * @param isLeftBrake Whether left trigger is held
   * @param isRightBrake Whether right trigger is held
   * 
   * @param driveTrain The driveTrain subsystem.
   */
  public DefaultDriveCommand(DoubleSupplier leftY, DoubleSupplier rightY, DoubleSupplier rightX, 
                            Supplier<Boolean> isAReleased, 
                            Supplier<Boolean> isLeftBrake,  Supplier<Boolean> isRightBrake,
                            DriveTrain driveTrain) {

    this.driveTrain = driveTrain;

    this.leftY = leftY;
    this.rightY = rightY;
    this.rightX = rightX;

    this.isAReleased = isAReleased;
    this.isLeftBrake = isLeftBrake;
    this.isRightBrake = isRightBrake;
    this.driveState = false;
    this.speed = 1;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    reverse = 1;  
    driveState = false;
    driveTrain.setShootDriverMode(false);
    driveTrain.setIntakeDriverMode(false);
    driveTrain.setShootPipeline(Pipelines.DEFAULT);
    driveTrain.turnOnLight(true);
    driveTrain.useDriveBlinkers(true);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (!isLeftBrake.get() && !isRightBrake.get()) {
      speed = Constants.HIGH_SPEED;
    }
    if (isLeftBrake.get() || isRightBrake.get()) {
      speed = Constants.MID_SPEED;
    }

    if (isLeftBrake.get() && isRightBrake.get()) {
      speed = Constants.LOW_SPEED;
    }

    if (!driveTrain.getShootDriverMode()) {
      driveTrain.setShootDriverMode(false);
      driveTrain.setShootPipeline(Pipelines.DEFAULT);
    }
    if (isAReleased.get()) {
      driveState = !driveState;
    }
    if (driveState) {
      driveMode = "Tank Drive";
    } else {
      driveMode = "Arcade Drive";
    }
    if (reverse == 1) {
      reversed = "forward";
    } else {
      reversed = "reversed";
    }
    SmartDashboard.putString("Drive Mode", driveMode);
    SmartDashboard.putString("Direction", reversed);

    if (driveState) {
      driveTrain.tankDrive(leftY.getAsDouble() * speed * reverse, 
                          rightY.getAsDouble() * speed * reverse);
    } else {
      driveTrain.arcadeDrive(leftY.getAsDouble() * speed * reverse, 
                            -rightX.getAsDouble() * speed * reverse);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.tankDrive(0, 0);
  }
}