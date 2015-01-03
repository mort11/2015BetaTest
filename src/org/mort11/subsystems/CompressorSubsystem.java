/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mort11.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.mort11.RobotMap;
import org.mort11.commands.Compress;
import org.mort11.util.MORTCompressor;

/**
 *
 * @author gridbug
 */
public class CompressorSubsystem extends Subsystem
{
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private final Compressor comp;
    
    public CompressorSubsystem(){
        comp = new Compressor(1);
    }
    
    public void initDefaultCommand()
    {
        setDefaultCommand(new Compress());
    }
    
    public void start(){
        comp.start();
    }
    
    public void stop(){
        comp.stop();
    }
    
    public boolean isRunning(){
        return comp.enabled();
    }
}
