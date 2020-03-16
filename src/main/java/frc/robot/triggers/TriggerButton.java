/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.triggers;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Add your docs here.
 */
public class TriggerButton extends Trigger {
  private XboxController joystick;
  private Hand hand;

  /**
   * Turns the trigger on the controller into a button.
   * 
   * @param joystick value of the trigger
   */
  public TriggerButton(XboxController joystick, Hand hand) {
    this.joystick = joystick;
    this.hand = hand;
  }

  /*
  *sets whether trigger is activated
  */
  @Override
  public boolean get() {
    if (Math.abs(joystick.getTriggerAxis(hand)) > 0.8) {
      return true;
    } else {
      return false;
    }
  }
}
