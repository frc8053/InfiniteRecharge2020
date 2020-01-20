/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
<<<<<<< HEAD
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.DownPovCommand;
=======
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.commands.DefaultDriveCommand;
>>>>>>> master
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Intake;
<<<<<<< HEAD
import frc.robot.triggers.LeftTriggerButton;
=======

>>>>>>> master

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
<<<<<<< HEAD
=======
  
  
  
  private ExampleSubsystem exampleSubsystem;
  private DriveTrain driveTrain;
  private Intake intake;

  private ExampleCommand exampleAutoCommand;
  XboxController driverController;
  
>>>>>>> master

  
  private ExampleSubsystem exampleSubsystem;
  private DriveTrain driveTrain;
  private Intake intake;

  private ExampleCommand exampleAutoCommand;
  XboxController driverController;
  POVButton povDown;
  Trigger leftTrigger;
  
  

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    // Initalize subsystems
    exampleSubsystem = new ExampleSubsystem();
    driveTrain = new DriveTrain();
    intake = new Intake();

    // Initalize commands
    exampleAutoCommand = new ExampleCommand(exampleSubsystem);

<<<<<<< HEAD
    // Initialize Gamepads
    
    driverController = new XboxController(0);
    povDown = new POVButton(driverController, 180);
    leftTrigger = new LeftTriggerButton(driverController.getTriggerAxis(Hand.kLeft));
    // Configure the button bindings
    // Set the default drive command to split-stick arcade drive
    povDown.whenReleased(new DownPovCommand(driveTrain));
    driveTrain.setDefaultCommand(new DefaultDriveCommand(
        () -> driverController.getY(Hand.kLeft),
        () -> driverController.getY(Hand.kRight), 
        () -> driverController.getX(Hand.kRight), 
        () -> driverController.getXButtonReleased(), 
        () -> driverController.getYButtonReleased(),
        () -> driverController.getBButtonReleased(),
        () -> driverController.getAButtonReleased(),
        driveTrain));
=======

    // Initialize Gamepads
    driverController = new XboxController(0);

    // Configure the button bindings
    
    // Configure default commands
    
    // Set the default drive command to split-stick arcade drive
   driveTrain.setDefaultCommand(new DefaultDriveCommand(
     () -> driverController.getY(Hand.kLeft),
     () -> driverController.getY(Hand.kRight), 
     () -> driverController.getX(Hand.kRight), 
     () -> driverController.getXButtonReleased(), 
     () -> driverController.getYButtonReleased(),
     () -> driverController.getBButtonReleased(),
     () -> driverController.getAButtonReleased(),
     driveTrain));
          
>>>>>>> master
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
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
