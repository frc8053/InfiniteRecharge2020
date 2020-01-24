/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * Add your docs here.
 */
public class TriggerButton extends Trigger {
  private double triggerLeftValue;

  public TriggerButton(double triggerLeftValue) {
    this.triggerLeftValue = triggerLeftValue;
  }

  @Override
  public boolean get() {
    if (triggerLeftValue > 50) {
      return true;
    } else {
      return false;
    }
  }
}
