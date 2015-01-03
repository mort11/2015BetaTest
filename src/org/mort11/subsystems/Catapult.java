package org.mort11.subsystems;

import org.mort11.RobotMap;
import org.mort11.commands.ee.CamIdle;
import org.mort11.commands.ee.ManualCam;
import org.mort11.commands.ee.Prime;
import org.mort11.util.EndEffectorConstants;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Catapult extends Subsystem
{

    double curSpeed = 0;
    boolean primed = false;
    private final Talon catapult;
    private boolean enabled = true;
    private final AnalogInput pot;

    public void initDefaultCommand()
    {
        setDefaultCommand(new Prime());

    }

    public void swapToManual()
    {
        getCurrentCommand().cancel();
        setDefaultCommand(new ManualCam());
    }

    public void setIdle()
    {
        getCurrentCommand().cancel();
        setDefaultCommand(new CamIdle());
    }
    public void setPrime(){
        getCurrentCommand().cancel();
        setDefaultCommand(new Prime());
    }
    public void swapControl()
    {
        if (getDefaultCommand().getClass() == ManualCam.class) {
            setDefaultCommand(new Prime());
        } else {
            setDefaultCommand(new ManualCam());
        }
        getCurrentCommand().cancel();
    }

    public Catapult()
    {
        this.catapult = new Talon(RobotMap.CAM_TALON_PORT);
        pot = new AnalogInput(RobotMap.CAM_POT_PORT);
        pot.setAverageBits(EndEffectorConstants.AVERAGE_POT_BITS);
    }

    public void setEnabled(boolean b)
    {
        enabled = b;
    }

    public boolean isPrimed()
    {
        return primed;
    }

    public void setPrimed(boolean Prime)
    {
        primed = Prime;
    }

    public void setSpeed(double speed)
    {
        if (!enabled) {
            speed = 0;
        }
        curSpeed = speed;
        catapult.set(-speed * EndEffectorConstants.CAM_FORWARD);
    }

    public double getSpeed()
    {
        return curSpeed * EndEffectorConstants.CAM_FORWARD;
    }

    public double getAngle()
    {
        double offsetAngle =
                (360.0 - (pot.getAverageVoltage() *
                          EndEffectorConstants.CAM_SCALE)) +
                EndEffectorConstants.CAM_OFFSET.get();
        return offsetAngle < 0 ? offsetAngle + 360 : offsetAngle;
    }

    public boolean inDeadband()
    {
        return getAngle() > 360 - EndEffectorConstants.CAM_DEADBAND_DEG.get() ||
               getAngle() < EndEffectorConstants.CAM_DEADBAND_DEG.get();
    }

}
