package org.firstinspires.ftc.teamcode.hardwaretests

import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.eventloop.opmode.TeleOp
import com.qualcomm.robotcore.hardware.CRServo
import com.qualcomm.robotcore.hardware.DcMotorEx
import com.qualcomm.robotcore.hardware.Servo
import org.firstinspires.ftc.teamcode.drive.GamepadEventPS

@TeleOp(name = "Intake Test")
class IntakeTest : OpMode() {
    private lateinit var left: CRServo
    private lateinit var right: CRServo

    private lateinit var gamepadEvent: GamepadEventPS

    override fun init() {
        left = hardwareMap.crservo.get("leftservo")
        right = hardwareMap.crservo.get("rightservo")

//        lift.targetPosition = 0
//        lift.power = 1.0
//        lift.mode = DcMotor.RunMode.RUN_TO_POSITION
//        lift.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE

        gamepadEvent = GamepadEventPS(gamepad1)

        telemetry.addLine("Initialized")
    }

    override fun loop() {
        if(gamepad1.dpad_up) {
            left.power = -1.0
            right.power = 1.0
        }
        else if(gamepad1.dpad_down) {
            left.power = 1.0
            right.power = -1.0
        } else {
            left.power = 0.0
            right.power = 0.0
        }

        telemetry.addData("Right Current power: ", right.power)
        telemetry.addData("Left Current power: ", left.power)
        telemetry.update()
    }
}