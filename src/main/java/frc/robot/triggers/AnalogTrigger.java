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
import java.util.function.DoubleSupplier;

/**
 * Add your docs here.
 */
public class AnalogTrigger extends Trigger {

  private DoubleSupplier dxSupplier;
  private DoubleSupplier dySupplier;

  private double deadBand = 0;
  private double max = 1;

  /**
  * Creates analog.
  */
  public AnalogTrigger(DoubleSupplier dxValue, DoubleSupplier dyValue,
                      double deadBand, double max) {
    super(() -> ((deadify(dxValue.getAsDouble(), deadBand) != 0 
        || deadify(dyValue.getAsDouble(), deadBand) != 0)));

    dxSupplier = dxValue;
    dySupplier = dyValue;
    this.deadBand = deadBand;
    this.max = max;
  }

  public AnalogTrigger(DoubleSupplier dxValue, DoubleSupplier dyValue) {
    this(dxValue, dyValue, 0, 1);
  }

  /**
   * Instantiate AnalogTrigger.
   * @param controller Xboxcontroller used.
   * @param hand Specifies which analog to use on Controller.
   * @param deadBand Specifies the threshold for an input.
   * @param max Will scale the output to this number.
   */
  public AnalogTrigger(XboxController controller, Hand hand, double deadBand, double max) {
    this(
        () -> (controller.getX(hand)), 
        () -> (controller.getY(hand)),
        deadBand, max);
  }

  public AnalogTrigger(XboxController controller, Hand hand) {
    this(() -> (controller.getX(hand)),
        () -> (controller.getY(hand)));
  }

  public double getDX() {
    return deadify(clamp(dxSupplier.getAsDouble()));
  }

  public double getDY() {
    return deadify(clamp(dySupplier.getAsDouble()));
  }

  private double deadify(double value) {
    return deadify(value,deadBand);
  }

  private static double deadify(double value, double deadZone) {
    return (value < deadZone ? 0 : value);
  }

  private double clamp(double val) {
    return val * max;
  }

}
