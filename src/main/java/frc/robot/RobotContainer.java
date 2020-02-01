/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.IntakeCommandGroup;
import frc.robot.commands.ShootCommandGroup;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.triggers.TriggerButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  int leftHoriz = 0;
  int leftVert = 1;
  int rightHoriz = 4;
  int rightVert = 5;

  
  private ExampleSubsystem exampleSubsystem;
  private DriveTrain driveTrain;
  private Intake intake;
  private Shooter shooter;

  private ExampleCommand exampleAutoCommand;
  private IntakeCommand intakeCommand;
  private IntakeCommandGroup intakeCommandGroup;
  private ShootCommandGroup povUpCommand;
  private ShootCommandGroup povDownCommand;

  XboxController driverController;
  XboxController manipulatorController;
  JoystickButton rightBumper;
  JoystickButton leftBumper;
  POVButton povDown;
  POVButton povUp;
  Trigger leftTrigger;
  Trigger rightTrigger;
  
  

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Initalize subsystems
    exampleSubsystem = new ExampleSubsystem();
    driveTrain = new DriveTrain();
    intake = new Intake();
    shooter = new Shooter();

    // Initalize commands
    exampleAutoCommand = new ExampleCommand(exampleSubsystem);
    intakeCommand = new IntakeCommand(Constants.Intake.INTAKE_SPEED, 0, intake);
    intakeCommandGroup = new IntakeCommandGroup(1, intake);
    povDownCommand = new ShootCommandGroup(intake, Constants.Shoot.SHOOT_LOW, shooter);
    povUpCommand = new ShootCommandGroup(intake, Constants.Shoot.SHOOT_HIGH, shooter);
    // Initialize Gamepads
    
    driverController = new XboxController(0);
    manipulatorController = new XboxController(1);
    povDown = new POVButton(driverController, 180);
    povUp = new POVButton(driverController, 0);
    rightBumper = new JoystickButton(driverController, 6);
    leftBumper = new JoystickButton(driverController, 5);
    leftTrigger = new TriggerButton(driverController.getTriggerAxis(Hand.kLeft));
    rightTrigger = new TriggerButton(driverController.getTriggerAxis(Hand.kRight));
    // Configure the button bindings
    // Set the default drive command to split-stick arcade drive
    driveTrain.setDefaultCommand(new DefaultDriveCommand(
        () -> driverController.getY(Hand.kLeft),
        () -> driverController.getY(Hand.kRight), 
        () -> driverController.getX(Hand.kRight), 
        () -> driverController.getXButtonReleased(), 
        () -> driverController.getAButtonReleased(),
        () -> leftTrigger.get(),
        () -> rightTrigger.get(),
        driveTrain));
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    rightBumper.whenHeld(intakeCommand);
    leftBumper.whenHeld(intakeCommandGroup);
    povDown.whenHeld(povDownCommand);
    povUp.whenHeld(povUpCommand);
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return exampleAutoCommand;
  }
}
