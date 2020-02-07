/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import java.util.function.DoubleSupplier;

public class ClimbCommand extends CommandBase {
  
  private final Climber subsystem;
  
  private DoubleSupplier powerSupplier;

  /**
   * Creates a new ClimbCommand.
   */
  public ClimbCommand(DoubleSupplier powerSupplier, Climber subsystem) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.subsystem = subsystem;
    
    this.powerSupplier = powerSupplier;

    addRequirements(subsystem);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (powerSupplier.getAsDouble() < 0) {
      subsystem.largeWinch(powerSupplier.getAsDouble());
    } else {
      subsystem.smallWinch(powerSupplier.getAsDouble());
    }
    System.out.println("Analog Trigger Works");
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
