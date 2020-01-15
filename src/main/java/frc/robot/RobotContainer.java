/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController.Button;
=======
import edu.wpi.first.wpilibj.GenericHID.Hand;
=======
import edu.wpi.first.wpilibj.GenericHID.Hand;
>>>>>>> Stashed changes
=======
import edu.wpi.first.wpilibj.GenericHID.Hand;
>>>>>>> Stashed changes
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DefaultDriveCommand;
>>>>>>> Stashed changes
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.defaultDrive;
import frc.robot.commands.tankDriveControl;
import frc.robot.subsystems.ExampleSubsystem;
<<<<<<< Updated upstream
import frc.robot.subsystems.driveTrain;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.odemetry;
import frc.robot.subsystems.outtake;
import frc.robot.subsystems.vision;
import frc.robot.Constants;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
=======
import frc.robot.subsystems.Intake;
import frc.robot.triggers.DownPov;

>>>>>>> Stashed changes

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
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final driveTrain m_driveTrain = new driveTrain();
  private final intake m_intake = new intake();
  private final odemetry m_odemetry = new odemetry();
  private final outtake m_outtake = new outtake();
  private final vision m_vision = new vision();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  XboxController driverController = new XboxController(0);
  
  
<<<<<<< Updated upstream
=======
  
  private ExampleSubsystem exampleSubsystem;
  private DriveTrain driveTrain;
  private Intake intake;

  private ExampleCommand exampleAutoCommand;
  XboxController driverController;
  DownPov downPOV;
  
>>>>>>> Stashed changes



  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
<<<<<<< Updated upstream
=======

    // Initalize subsystems
    exampleSubsystem = new ExampleSubsystem();
    driveTrain = new DriveTrain();
    intake = new Intake();

    // Initalize commands
    exampleAutoCommand = new ExampleCommand(exampleSubsystem);


    // Initialize Gamepads
    driverController = new XboxController(0);
    downPOV = new DownPov(driverController.getPOV());

      
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    // Configure the button bindings
    m_driveTrain.setDefaultCommand(new defaultDrive(m_driveTrain, driverController.getY(GenericHID.Hand.kLeft), driverController.getX(GenericHID.Hand.kRight)));  
    
    
    
<<<<<<< Updated upstream
=======
    // Set the default drive command to split-stick arcade drive
    driveTrain.setDefaultCommand(new DefaultDriveCommand(
        () -> driverController.getY(Hand.kLeft),
        () -> driverController.getY(Hand.kRight), 
        () -> driverController.getX(Hand.kRight), 
        () -> driverController.getXButtonReleased(), 
        () -> driverController.getYButtonReleased(),
        () -> driverController.getBButtonReleased(),
        () -> driverController.getAButtonReleased(),
        () -> downPOV.get(),
        driveTrain));
        
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() 
  {
    new JoystickButton(driverController, Button.kBumperRight.value)
      .whileHeld(new tankDriveControl(m_driveTrain, driverController.getY(Hand.kLeft), driverController.getY(Hand.kRight)));
  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
