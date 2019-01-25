/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6213.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Timer;

/**
 * This class holds all the Autonomous code. Referenced in the Robot class(Robot.java). 
 */

public class Auto {
    Timer auto_timer = new Timer();

    //Robot will spin around slowly, for showcasing
    public void showcase(DifferentialDrive m_robotDrive){
        m_robotDrive.arcadeDrive(0.0, 0.5); //Rotates at half speed
    }
    //Default code that will just drive for 2 secs
    public void noob(DifferentialDrive m_robotDrive){
        if (auto_timer.get() < 10.0){ 
			m_robotDrive.arcadeDrive(0.5, 0.0); // drive forwards half speed
		} else {
			m_robotDrive.stopMotor(); // stop robot
		}
    }

    public void drive(DifferentialDrive m_robotDrive, double driveTime, double driveSpeed){
        if (auto_timer.get() < driveTime) {
			m_robotDrive.arcadeDrive(driveSpeed, 0.0); // drive forwards half speed
		}else{
			m_robotDrive.stopMotor(); // stop robot
		}
    }

    public void Cracker(DifferentialDrive m_robotDrive, Timer m_timer){
        if (m_timer.get() < 10.0){
            m_robotDrive.arcadeDrive(1.0, 0.0);
        } else {
            m_robotDrive.stopMotor();
            
        }
    }
}
