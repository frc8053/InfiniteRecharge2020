/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain; 
import java.util.function.DoubleSupplier;
import java.util.function.Supplier;

public class DefaultDriveCommand extends CommandBase {
    
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final DriveTrain driveTrain;

  private DoubleSupplier leftY;
  private DoubleSupplier rightY;
  private DoubleSupplier rightX;
  private Supplier<Boolean> isReverse;
  private Supplier<Boolean> isLeftBrake;
  private Supplier<Boolean> isRightBrake;

  private Supplier<Boolean> isDriveToggled;
  double speed;
  double reverse;
  boolean driveState;

  /**
   * Creates a new default drive command.
   *
   * @param leftY The leftY joystick of driver gamepad
   * @param rightY The RightY joystick of driver gamepad
   * @param rightX rightX joystick of driver gamepad
   * 
   * 
   * @param isDriveToggled Whether the toggle button has been released (basically pressed)
   * @param isReverse Whether A has been released
   * @param isLeftBrake Whether left trigger is held
   * @param isRightBrake Whether right trigger is held
   * 
   * @param driveTrain The driveTrain subsystem.
   */
  public DefaultDriveCommand(DoubleSupplier leftY, DoubleSupplier rightY, DoubleSupplier rightX, 
                            Supplier<Boolean> isDriveToggled, Supplier<Boolean> isReverse, 
                            Supplier<Boolean> isLeftBrake,  Supplier<Boolean> isRightBrake,
                            DriveTrain driveTrain) {

    this.driveTrain = driveTrain;

    this.leftY = leftY;
    this.rightY = rightY;
    this.rightX = rightX;

    this.isDriveToggled = isDriveToggled;
    this.isReverse = isReverse;
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
    
    if (isDriveToggled.get()) {
      driveState = !driveState;
    }
    if (isReverse.get()) {
      reverse = -reverse;
    }

    if (driveState) {
      driveTrain.tankDrive(leftY.getAsDouble() * speed * reverse, 
                          rightY.getAsDouble() * speed * reverse);
    } else {
      driveTrain.arcadeDrive(leftY.getAsDouble() * speed * reverse, 
                            rightX.getAsDouble() * speed * reverse);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.tankDrive(0, 0);
  }
}