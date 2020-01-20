/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
<<<<<<< HEAD

=======
>>>>>>> master
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
<<<<<<< HEAD
  private Supplier<Boolean> isHighSpeed;
  private Supplier<Boolean> isMidSpeed;
  private Supplier<Boolean> isLowSpeed;

  private Supplier<Boolean> isDriveToggled;
  double speed;
  double reverse;
=======
  private Supplier<Boolean> ypressed;
  private Supplier<Boolean> bpressed;
  private Supplier<Boolean> apressed;

  private Supplier<Boolean> isDriveToggled;
  double speed;
>>>>>>> master
  boolean driveState;

  /**
   * Creates a new default drive command.
   *
   * @param leftY The leftY joystick of driver gamepad
   * @param rightY The RightY joystick of driver gamepad
   * @param rightX rightX joystick of driver gamepad
   * 
<<<<<<< HEAD
   * 
   * @param isDriveToggled Whether the toggle button has been released (basically pressed)
   * @param isHighSpeed Whether Y has been released
   * @param isMidSpeed Whether B has been released
   * @param isLowSpeed Whether A has been released
=======
   * @param isDriveToggled Whether the toggle button has been released (basically pressed)
>>>>>>> master
   * 
   * @param driveTrain The driveTrain subsystem.
   */
  public DefaultDriveCommand(DoubleSupplier leftY, DoubleSupplier rightY, DoubleSupplier rightX, 
<<<<<<< HEAD
                            Supplier<Boolean> isDriveToggled, Supplier<Boolean> isHighSpeed, 
                            Supplier<Boolean> isMidSpeed,  Supplier<Boolean> isLowSpeed,
                            DriveTrain driveTrain) {
=======
      Supplier<Boolean> isDriveToggled, Supplier<Boolean> ypressed, Supplier<Boolean> bpressed, Supplier<Boolean> apressed, DriveTrain driveTrain) {
>>>>>>> master

    this.driveTrain = driveTrain;

    this.leftY = leftY;
    this.rightY = rightY;
    this.rightX = rightX;

    this.isDriveToggled = isDriveToggled;
<<<<<<< HEAD
    this.isHighSpeed = isHighSpeed;
    this.isMidSpeed = isMidSpeed;
    this.isLowSpeed = isLowSpeed;
    this.driveState = false;
    this.speed = 1;
=======
    this.ypressed = ypressed;
    this.bpressed = bpressed;
    this.apressed = apressed;
    this.driveState = false;
>>>>>>> master
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
<<<<<<< HEAD
    reverse = 1;
=======
>>>>>>> master
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
<<<<<<< HEAD
    if (isHighSpeed.get()) {
      speed = Constants.HIGH_SPEED;
    }
    if (isMidSpeed.get()) {
      speed = Constants.MID_SPEED;
    }
    if (isLowSpeed.get()) {
=======
    if(ypressed.get())
    {
      speed = Constants.HIGH_SPEED;
    }
    if(bpressed.get()){
      speed = Constants.MID_SPEED;
    }
    if(apressed.get()){
>>>>>>> master
      speed = Constants.LOW_SPEED;
    }
    if (isDriveToggled.get()) {
      driveState = !driveState;
    }
<<<<<<< HEAD
    reverse = driveTrain.getReverse();
    if (driveState) {
      driveTrain.tankDrive(leftY.getAsDouble() * speed * reverse, 
                          rightY.getAsDouble() * speed * reverse);
    } else {
      driveTrain.arcadeDrive(leftY.getAsDouble() * speed * reverse, 
                            rightX.getAsDouble() * speed * reverse);
=======

    if (driveState) {
      driveTrain.tankDrive(leftY.getAsDouble()*speed, rightY.getAsDouble()*speed);
    } else {
      driveTrain.arcadeDrive(leftY.getAsDouble()*speed, rightX.getAsDouble()*speed);
>>>>>>> master
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    driveTrain.tankDrive(0, 0);
  }

  
}