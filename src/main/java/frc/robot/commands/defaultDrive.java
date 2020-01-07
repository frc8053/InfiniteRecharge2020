/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.Constants;
import frc.robot.subsystems.driveTrain;

public class defaultDrive extends CommandBase {
  /**
   * Creates a new defaultDrive.
   */
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final driveTrain m_drive;
  private final double leftAxis;
  private final double rightAxis;
  

  public defaultDrive(driveTrain subsytem, double left, double right) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = subsytem;
    leftAxis = left;
    rightAxis = right;
    addRequirements(m_drive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.driveArcade(leftAxis, rightAxis);
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
