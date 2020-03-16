/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Winch;

public class WinchCommand extends CommandBase {
  
  private final Winch winch;
  private DriveTrain driveTrain;
  private double counter;
  private boolean leftBlinker;
  private boolean rightBlinker;
  
  /**
   * Runs the winch at 100% speed to climb.
   * @param winch the winch subsystem used
   */
  public WinchCommand(Winch winch, DriveTrain driveTrain) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.winch = winch;
    this.driveTrain = driveTrain;
    addRequirements(winch);
  }


  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    counter = 0;
    leftBlinker = false;
    rightBlinker = false;
    driveTrain.useDriveBlinkers(false);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    winch.winchControl(1);
    if (counter < 500) {
      counter++;
    }
    if (counter >= 500) {
      leftBlinker = !leftBlinker;
      rightBlinker = !rightBlinker;
      counter = 0;
    }
    driveTrain.turnOnLeftBlinker(leftBlinker);
    driveTrain.turnOnRightBlinker(rightBlinker);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    winch.winchControl(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}