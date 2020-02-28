/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.modules.Pipelines;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class VisionCommandGroup extends SequentialCommandGroup {
  private DriveTrain driveTrain;

  /**
   * Creates a new VisionCommandGroup.
   */
  public VisionCommandGroup(DriveTrain driveTrain) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(
        new InstantCommand(() -> driveTrain.turnOnLight(false), driveTrain),
        //new WaitUntilCommand(driveTrain::findTarget),
        new BasicVisionCommand(driveTrain)
    //new DriveDistanceCommand(driveTrain.getVisionDistance() - 200, driveTrain)
    );
    this.driveTrain = driveTrain;
  }

  @Override
  public void initialize() {
    driveTrain.toggleDriverMode(false);
    driveTrain.setShootPipeline(Pipelines.DEFAULT);
    super.initialize();
  }

  @Override
  public void end(boolean interrupted) {
    driveTrain.turnOnLight(true);
    driveTrain.toggleDriverMode(true);
    driveTrain.setShootPipeline(Pipelines.DRIVER);
    super.end(interrupted);
  }
}
