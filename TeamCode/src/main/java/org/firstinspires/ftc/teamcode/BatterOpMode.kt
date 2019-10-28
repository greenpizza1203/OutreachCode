package org.firstinspires.ftc.teamcode

import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import com.qualcomm.robotcore.eventloop.opmode.OpMode
import com.qualcomm.robotcore.hardware.DcMotor


@Autonomous(name = "BatterOpMode", group = "autonomous")
class BatterOpMode : OpMode() {

    private lateinit var batMotor: DcMotor
    private lateinit var leftMotor: DcMotor
    private lateinit var rightMotor: DcMotor


    private var turbo = false
    private var previousState = false

    override fun init() {
        batMotor = hardwareMap.get(DcMotor::class.java, "BatMotor")
        leftMotor = hardwareMap.get(DcMotor::class.java, "LeftMotor")
        rightMotor = hardwareMap.get(DcMotor::class.java, "RightMotor")
    }


    override fun loop() {
        if (gamepad1.a) {
            return
        }
        if (!previousState && gamepad1.x) {
            turbo = !turbo
        }

        previousState = gamepad1.x
        telemetry.addData("toggle", turbo)
        if (turbo) {
            batMotor.power = deadZone(gamepad2.right_trigger)
            batMotor.power = -deadZone(gamepad2.left_trigger)
        } else {
            batMotor.power = deadZone(gamepad2.right_trigger / 2)
            batMotor.power = -deadZone(gamepad2.left_trigger / 2)
        }

        val Yj = gamepad2.left_stick_y
        val Xj = gamepad2.left_stick_x * 0.3f
        var L = -Yj + Xj
        var R = -Yj - Xj

        var max = Math.abs(L)
        if (max < Math.abs(R)) max = Math.abs(R)
        if (max > 1) {
            L /= max
            R /= max
        }

        L *= -1f
        telemetry.addData("left", L)
        telemetry.addData("right", R)
        telemetry.update()
        leftMotor.power = L.toDouble()
        rightMotor.power = R.toDouble()
    }

    private fun deadZone(v: Float): Double {
        return (if (v > 0.10) v.toDouble() else 0.0)

    }
}