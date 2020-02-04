/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.triggers;

import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Add your docs here.
 */
public class TriggerButton extends Trigger {
  private double triggerValue;

  /**
   * Turns the trigger on the controller into a button.
   * @param triggerValue value of the trigger
   */
  public TriggerButton(double triggerValue) {
    this.triggerValue = triggerValue;
  }

  /*
  *sets whether trigger is activated
  */
  @Override
  public boolean get() {
    if (Math.abs(triggerValue) > 0.8) {
      return true;
    } else {
      return false;
    }
  }
}
