/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class IntakeCommand extends CommandBase {
  private Intake intake;
  private double intakeSpeed;
  private double conveyorSpeed;
  /**
   * Command to use the intake bar and conveyor.
   * 
   * @param intakeSpeed the speed of the intake bar
   * @param conveyorSpeed the speed of the conveyor
   * @param intake Intake subsystem used by command
   */

  public IntakeCommand(double intakeSpeed, double conveyorSpeed, Intake intake) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.intake = intake;
    this.intakeSpeed = intakeSpeed;
    this.conveyorSpeed = conveyorSpeed;
    addRequirements(intake);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    intake.intakeBar(intakeSpeed);
    intake.conveyorControl(conveyorSpeed);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.intakeBar(0);
    intake.conveyorControl(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
