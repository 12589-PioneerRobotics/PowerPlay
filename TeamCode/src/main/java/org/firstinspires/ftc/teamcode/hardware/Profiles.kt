package org.firstinspires.ftc.teamcode.hardware

class Profiles () {
    var presses = 0
    var check = true

    fun defaultProfile(lift: Lift, dpad_left: Boolean, dpad_down: Boolean, dpad_right: Boolean, dpad_up: Boolean, trigger_left: Float, trigger_right: Float, bumper_right: Boolean) {
        if (dpad_left)
            lift.setLiftPosition(ActuationConstants.LiftConstants.IDLE)
        if (dpad_down)
            lift.setLiftPosition(ActuationConstants.LiftConstants.FIRST_LEVEL)
        if (dpad_right)
            lift.setLiftPosition(ActuationConstants.LiftConstants.SECOND_LEVEL)
        if (dpad_up)
            lift.setLiftPosition(ActuationConstants.LiftConstants.THIRD_LEVEL)
        if (trigger_right > 0.5)
            lift.raiseLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)
        if (trigger_left > 0.5)
            lift.lowerLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)
        if (bumper_right && lift.clawState == Lift.ClawState.OPEN) {
            lift.closeClaw()
            lift.raiseLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)
        }
        else if (bumper_right && lift.clawState == Lift.ClawState.CLOSED) {
            lift.openClaw()
            lift.lowerLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)
        }
    }

    fun experimentalProfile(lift: Lift, stick_right_button: Boolean, bumper_left: Boolean, bumper_right: Boolean, trigger_left: Float, trigger_right: Float) {
        if (stick_right_button && lift.clawState == Lift.ClawState.OPEN) {
            lift.closeClaw()
        }

        else if (stick_right_button && lift.clawState == Lift.ClawState.CLOSED) {
            lift.openClaw()
        }

        if (bumper_right && presses < 3) {
            presses += 1
            check = true
        }
        if (bumper_left) {
            presses = 0
            check = true
        }

        if (trigger_right > 0.5)
            lift.raiseLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)
        if (trigger_left > 0.5)
            lift.lowerLift(ActuationConstants.LiftConstants.LIFT_INCREMENT)

        if (presses == 0 && check) {
            lift.setLiftPosition(ActuationConstants.LiftConstants.IDLE)
            check = false
        }
        else if (presses == 1 && check) {
            lift.setLiftPosition(ActuationConstants.LiftConstants.FIRST_LEVEL)
            check = false
        }
        else if (presses == 2 && check) {
            lift.setLiftPosition(ActuationConstants.LiftConstants.SECOND_LEVEL)
            check = false
        }
        else if (presses == 3 && check) {
            lift.setLiftPosition(ActuationConstants.LiftConstants.THIRD_LEVEL)
            check = false
        }
    }
}