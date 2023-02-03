package org.firstinspires.ftc.teamcode

import com.acmerobotics.roadrunner.geometry.Pose2d
import com.outoftheboxrobotics.photoncore.PhotonCore
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.drive.GamepadEventPS
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive
import org.firstinspires.ftc.teamcode.hardware.Lift
import org.firstinspires.ftc.teamcode.hardware.Profiles

@TeleOp(name = "ShreyOp Rush")
class ShreyOpRush : OpMode() {
    private lateinit var drive: SampleMecanumDrive
    private lateinit var lift: Lift
    private lateinit var profiles: Profiles
    private lateinit var gamepadEvent1: GamepadEventPS
    private lateinit var gamepadEvent2: GamepadEventPS

    private var slowMode = false

    override fun init() {
        drive = SampleMecanumDrive(hardwareMap)
        lift = Lift(hardwareMap)
        profiles = Profiles()
        gamepadEvent1 = GamepadEventPS(gamepad1)
        gamepadEvent2 = GamepadEventPS(gamepad2)

        telemetry.addLine("Initialized!")
        telemetry.update()
    }

    override fun loop() {
        PhotonCore.enable()

        if(gamepadEvent1.leftStickButton())
            slowMode = !slowMode

        if(slowMode) {
            drive.setWeightedDrivePower(
                Pose2d(
                    -gamepad1.left_stick_y.toDouble() / 3,
                    -gamepad1.left_stick_x.toDouble() / 3,
                    -gamepad1.right_stick_x.toDouble() / 3
                )
            )
        }
        else {
            drive.setWeightedDrivePower(
                Pose2d(
                    -gamepad1.left_stick_y.toDouble(),
                    -gamepad1.left_stick_x.toDouble(),
                    -gamepad1.right_stick_x.toDouble()
                )
            )
        }

        //profiles.defaultProfile(lift, gamepadEvent1.dPadLeft(), gamepadEvent1.dPadDown(), gamepadEvent1.dPadRight(), gamepadEvent1.dPadUp(), gamepad1.left_trigger, gamepad1.right_trigger, gamepadEvent1.rightBumper())
        profiles.experimentalProfile(lift, gamepadEvent1.rightStickButton(), gamepadEvent1.leftBumper(), gamepadEvent1.rightBumper(), gamepad1.left_trigger, gamepad1.right_trigger, gamepadEvent1.cross())
    }
}