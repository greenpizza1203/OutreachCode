package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "Mecanum TeleOp", group = "TeleOp")
public class MechanumTeleOp extends LinearOpMode {
    public DcMotor frontRightMotor, frontLeftMotor, backRightMotor, backLeftMotor;

    @Override
    public void runOpMode() {
        initStuff();
        waitForStart();
        while (opModeIsActive()) {
            loopStuff();
        }
    }

    private void initStuff() {
        frontRightMotor = hardwareMap.get(DcMotor.class, "FRP");
        frontLeftMotor = hardwareMap.get(DcMotor.class, "FLP");
        backRightMotor = hardwareMap.get(DcMotor.class, "BRP");
        backLeftMotor = hardwareMap.get(DcMotor.class, "BLP");
    }

    private void loopStuff() {
        double rawY = -gamepad1.left_stick_y;
        double rawX = gamepad1.left_stick_x;

        double rot = gamepad1.right_stick_x;
        rot *= .4;


        double fwd = rawY;
        double str = rawX;
        fwd *= fwd * fwd;
        double FRP = fwd - str - rot;
        double FLP = fwd + str + rot;
        double BRP = fwd + str - rot;
        double BLP = fwd - str + rot;

        double max = (FRP > 1) ? FRP : 1;
        if (max < FRP) {
            max = FRP;
        }
        if (max < BLP) {
            max = BLP;
        }
        if (max < BRP) {
            max = BRP;
        }
        if (max < FLP) {
            max = FLP;
        }
        FLP /= max;
        FRP /= max;
        BLP /= max;
        BRP /= max;
        frontLeftMotor.setPower(-FLP);
        frontRightMotor.setPower(-FRP);
        backLeftMotor.setPower(BLP);
        backRightMotor.setPower(BRP);
    }
}
