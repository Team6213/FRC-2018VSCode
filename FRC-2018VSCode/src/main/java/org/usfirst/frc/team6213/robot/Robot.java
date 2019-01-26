/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6213.robot;


import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.XboxController;
//import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends IterativeRobot {
	//Robot objects(objects from the WPI API)
	private final DifferentialDrive m_robotDrive
			= new DifferentialDrive(new Spark(0), new Spark(1));
	private final DoubleSolenoid gripperSole = new DoubleSolenoid(0, 1);
	private final DoubleSolenoid armSole = new DoubleSolenoid(2, 3);
	private final Solenoid gateSole = new Solenoid(4);
	private final Spark armWristMotor = new Spark(2);
	private final Joystick m_stick = new Joystick(0);
	private final XboxController xbox = new XboxController(1);
	private final Timer m_timer = new Timer();
	private static final String kDefaultAuto = "Default";
	private static final String kShowcaseAuto = "Showcase";
	private static final String kDriveAuto = "Drive";
	private String m_autoSelected;
	private final SendableChooser<String> m_chooser = new SendableChooser<>();

	//Custom objects(objects from our own classes)
	private Auto auto = new Auto();

	//Variables
	double speedMod;




	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
		m_chooser.addOption("Showcase", kShowcaseAuto);
		// m_chooser.addOption("Drive", kShowcaseAuto);
    	SmartDashboard.putData("Auto choices", m_chooser);
		
		UsbCamera cam = CameraServer.getInstance().startAutomaticCapture();
		
	}
	

	/**
	 * This function is run once each time the robot enters autonomous mode.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
    	// m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		
		m_timer.reset();
		m_timer.start();
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		switch (m_autoSelected) {
			case kShowcaseAuto:
				auto.showcase(m_robotDrive);
			  break;
			case kDriveAuto:
				auto.drive(m_robotDrive, 5.0, 0.5);
			  break;
			case kDefaultAuto:
			default:
			  auto.noob(m_robotDrive);
			  break;
		  }
		
		
	}

	/**
	 * This function is called once each time the robot enters teleoperated mode.
	 */
	@Override
	public void teleopInit() {
		

		armWristMotor.set(0.0);
		m_robotDrive.stopMotor();
	}

	/**
	 * This function is called periodically during teleoperated mode.
	 */
	@Override
	public void teleopPeriodic() {
		boolean triggerJoystick = m_stick.getRawButton(1);

		speedMod();
		
		m_robotDrive.arcadeDrive(xbox.getRawAxis(1) * speedMod, xbox.getRawAxis(0) * speedMod);

		if(xbox.getY() < -0.2){
			gateSole.set(true);
			armSole.set(DoubleSolenoid.Value.kForward);
		}else if(xbox.getY() > 0.2){
			gateSole.set(true);
			armSole.set(DoubleSolenoid.Value.kReverse);
		}else{
			gateSole.set(false);
			armSole.set(DoubleSolenoid.Value.kOff);
		}

		if(xbox.getRawButton(6)){
			armWristMotor.set(-0.45);
		}else{
			armWristMotor.set(0.0);
		}

		if(xbox.getRawAxis(3) > 0){
			gripperSole.set(DoubleSolenoid.Value.kForward);
		}else if(xbox.getRawAxis(2) > 0){
			gripperSole.set(DoubleSolenoid.Value.kReverse);
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
		speedMod = 1.0;
		
	}

	public void speedMod(){
		if(xbox.getAButton()){
			speedMod = 1.0;
		}else if(xbox.getBButton()){
			speedMod = 0.5;
		}else if(xbox.getYButton()){
			speedMod = 0.25;
		}
	}
}

