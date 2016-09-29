package com.example.kuri.sensorexpt;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    FrameLayout frameLayout;
    BallImage ballImage;
    //angle
    float m = 0;
    //sensor variables
    float x;
    float y;
    //matrix value holders
    float ballPoint[] = {0,0,0,0,0,0,0,0,0};
    float starPoint[] = {0,0,0,0,0,0,0,0,0};
    float star2Point[] = {0,0,0,0,0,0,0,0,0};
    float star3Point[] = {0,0,0,0,0,0,0,0,0};
    float star4Point[] = {0,0,0,0,0,0,0,0,0};
    float star5Point[] = {0,0,0,0,0,0,0,0,0};
    float star6Point[] = {0,0,0,0,0,0,0,0,0};
    //translate variables - stars
    float starX;
    float starY;
    float star2X;
    float star2Y;
    float star3X;
    float star3Y;
    float star4X;
    float star4Y;
    float star5X;
    float star5Y;
    float star6X;
    float star6Y;
    //translate variables - ball
    float ballX;
    float ballY;
    //device variables
    int height;
    int width;
    //bmp
    Bitmap ball;
    Bitmap support;
    Bitmap star;
    Bitmap star2;
    Bitmap star3;
    Bitmap star4;
    Bitmap star5;
    Bitmap star6;
    //ImageView image;
    Matrix matrixBall;
    Matrix starMatrix;
    Matrix starMatrix2;
    Matrix starMatrix3;
    Matrix starMatrix4;
    Matrix starMatrix5;
    Matrix starMatrix6;
    //paint
    Paint starPaint;
    Paint starPaint2;
    Paint starPaint3;
    Paint starPaint4;
    Paint starPaint5;
    Paint starPaint6;
    //time
    int seconds = 0;
    boolean running = true;
    //textDisplay
    TextView textDisplay;
    TextView result;
    TextView endResult;
    //for individually checking if each star visited
    boolean visited = false;
    boolean visited2 = false;
    boolean visited3 = false;
    boolean visited4 = false;
    boolean visited5 = false;
    boolean visited6 = false;
    //score keeper
    int score = 0;
    int limit = 15;
    //displaying messages
    Toast toast;
    CharSequence textWin = "WOOHOO! YOU WON!";
    CharSequence textLost = "SORRY! YOU LOST";
    int toastDuration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);
        //To identify the sensors that are on a device you first need to get a reference to the sensor service
        sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        frameLayout = (FrameLayout)findViewById(R.id.graphics_holder);
        ballImage = new BallImage(this);

        textDisplay = (TextView)findViewById(R.id.textTime);
        result = (TextView)findViewById(R.id.result);
        endResult = (TextView)findViewById(R.id.endResult);

        starPaint = new Paint();
        starPaint.setAlpha(40);

        starPaint2 = new Paint();
        starPaint2.setAlpha(40);

        starPaint3 = new Paint();
        starPaint3.setAlpha(40);

        starPaint4 = new Paint();
        starPaint4.setAlpha(40);

        starPaint5 = new Paint();
        starPaint5.setAlpha(40);

        starPaint6 = new Paint();
        starPaint6.setAlpha(40);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;

        frameLayout.addView(ballImage);
        runimage(ballImage);
        runTime();
    }

    private class BallImage extends View{
        //constructor
        public BallImage(Context context) {
            super(context);
            ball = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
            support = BitmapFactory.decodeResource(getResources(), R.drawable.support);
            star = BitmapFactory.decodeResource(getResources(), R.drawable.star);
            star2 = BitmapFactory.decodeResource(getResources(), R.drawable.star);
            star3 = BitmapFactory.decodeResource(getResources(), R.drawable.star);
            star4 = BitmapFactory.decodeResource(getResources(), R.drawable.star);
            star5 = BitmapFactory.decodeResource(getResources(), R.drawable.star);
            star6 = BitmapFactory.decodeResource(getResources(), R.drawable.star);
            matrixBall = new Matrix();
            starMatrix = new Matrix();
            starMatrix2 = new Matrix();
            starMatrix3 = new Matrix();
            starMatrix4 = new Matrix();
            starMatrix5 = new Matrix();
            starMatrix6 = new Matrix();
        }

        public void onMove(float x, float y){

            matrixBall.getValues(ballPoint);
            ballX = ballPoint[2];
            ballY = ballPoint[5];
            if((ballX+x < 0)||(ballX+x > width - ball.getWidth()))
                return;
            if((ballY+y < 0)||(ballY+y > height - ball.getHeight()-50)) //-50 due to extra png size
                return;
//OBSTACLES//
//******************************************************************************************//
            //for the chosen bitmap obstacle:
            //difference between the above surface and below is 120
            //difference between above and middle is 50
            //difference between below and middle is 70
            //can be PERFECTED
//******************************************************************************************//
            //block 1
            if(((ballX + x) >= 13)&&((ballX + x) <= (support.getWidth())+13)){
                if(((ballY + y) >= 100)&&((ballY + y) <= (support.getHeight()+100)))
                    return;
            }
            if(((ballX + x) >= 20)&&((ballX + x) <= (support.getWidth())+20)){
                if(((ballY + y) >= 150)&&((ballY + y) <= (support.getHeight()+150)))
                    return;
            }
            if(((ballX + x) >= 13)&&((ballX + x) <= (support.getWidth())+13)){
                if(((ballY + y) >= 220)&&((ballY + y) <= (support.getHeight()+220)))
                    return;
            }

            //block 2
            if(((ballX + x) >= 420)&&((ballX + x) <= (support.getWidth())+420)){
                if(((ballY + y) >= 280)&&((ballY + y) <= (support.getHeight()+280)))
                    return;
            }
            if(((ballX + x) >= 427)&&((ballX + x) <= (support.getWidth())+427)){
                if(((ballY + y) >= 330)&&((ballY + y) <= (support.getHeight()+330)))
                    return;
            }
            if(((ballX + x) >= 420)&&((ballX + x) <= (support.getWidth())+420)){
                if(((ballY + y) >= 400)&&((ballY + y) <= (support.getHeight()+400)))
                    return;
            }

            //block 3
            if(((ballX + x) >= 230)&&((ballX + x) <= (support.getWidth())+230)){
                if(((ballY + y) >= 570)&&((ballY + y) <= (support.getHeight()+570)))
                    return;
            }
            if(((ballX + x) >= 237)&&((ballX + x) <= (support.getWidth())+237)){
                if(((ballY + y) >= 620)&&((ballY + y) <= (support.getHeight()+620)))
                    return;
            }
            if(((ballX + x) >= 230)&&((ballX + x) <= (support.getWidth())+230)){
                if(((ballY + y) >= 690)&&((ballY + y) <= (support.getHeight()+690)))
                    return;
            }

            //block 4
            if(((ballX + x) >= 630)&&((ballX + x) <= (support.getWidth())+630)){
                if(((ballY + y) >= 730)&&((ballY + y) <= (support.getHeight()+730)))
                    return;
            }
            if(((ballX + x) >= 637)&&((ballX + x) <= (support.getWidth())+637)){
                if(((ballY + y) >= 780)&&((ballY + y) <= (support.getHeight()+780)))
                    return;
            }
            if(((ballX + x) >= 630)&&((ballX + x) <= (support.getWidth())+630)){
                if(((ballY + y) >= 850)&&((ballY + y) <= (support.getHeight()+850)))
                    return;
            }

            //block 5
            if(((ballX + x) >= 420)&&((ballX + x) <= (support.getWidth())+420)){
                if(((ballY + y) >= 980)&&((ballY + y) <= (support.getHeight()+980)))
                    return;
            }
            if(((ballX + x) >= 427)&&((ballX + x) <= (support.getWidth())+427)){
                if(((ballY + y) >= 1030)&&((ballY + y) <= (support.getHeight()+1030)))
                    return;
            }
            if(((ballX + x) >= 420)&&((ballX + x) <= (support.getWidth())+420)){
                if(((ballY + y) >= 1100)&&((ballY + y) <= (support.getHeight()+1100)))
                    return;
            }

            //block 6
            if(((ballX + x) >= 13)&&((ballX + x) <= (support.getWidth())+13)){
                if(((ballY + y) >= 1230)&&((ballY + y) <= (support.getHeight()+1230)))
                    return;
            }
            if(((ballX + x) >= 20)&&((ballX + x) <= (support.getWidth())+20)){
                if(((ballY + y) >= 1280)&&((ballY + y) <= (support.getHeight()+1280)))
                    return;
            }
            if(((ballX + x) >= 13)&&((ballX + x) <= (support.getWidth())+13)){
                if(((ballY + y) >= 1350)&&((ballY + y) <= (support.getHeight()+1350)))
                    return;
            }

            //block 7
            if(((ballX + x) >= 230)&&((ballX + x) <= (support.getWidth())+230)){
                if(((ballY + y) >= 1450)&&((ballY + y) <= (support.getHeight()+1450)))
                    return;
            }
            if(((ballX + x) >= 237)&&((ballX + x) <= (support.getWidth())+237)){
                if(((ballY + y) >= 1500)&&((ballY + y) <= (support.getHeight()+1500)))
                    return;
            }
            if(((ballX + x) >= 230)&&((ballX + x) <= (support.getWidth())+230)){
                if(((ballY + y) >= 1570)&&((ballY + y) <= (support.getHeight()+1570)))
                    return;
            }

//******************************************************************************************//

            matrixBall.postTranslate(x,y);
            starMatrix.postRotate(360);
            invalidate();
        }

        public void moveStar(float m) {
            //This is the method for rotating the Matrix:
            //this.matrix.reset();
            //this.matrix.setTranslate(this.floatXpos, this.floatYpos);
            //this.matrix.postRotate((float)this.direction, this.getCenterX(), this.getCenterY());
            //(this.getCenterX() is basically the bitmaps X position + the bitmaps width / 2)
         if(seconds <= limit) {
             starMatrix.setTranslate(650, 300);
             starMatrix.postRotate(m, 665, 315);

             starMatrix2.setTranslate(300, 590);
             starMatrix2.postRotate(-m, 315, 605);

             starMatrix3.setTranslate(860, 750);
             starMatrix3.postRotate(m, 875, 765);

             starMatrix4.setTranslate(490, 1000);
             starMatrix4.postRotate(-m, 505, 1015);

             starMatrix5.setTranslate(210, 1250);
             starMatrix5.postRotate(m, 225, 1265);

             starMatrix6.setTranslate(460, 1470);
             starMatrix6.postRotate(-m, 475, 1485);

             invalidate();
         }//end of limit condition
//?? Still need to experiment//
//settranslate sets the translate point and the rotation will oscillate the values about that point
//getValue returns the total translated distance
            starMatrix.getValues(starPoint);
            starX = starPoint[2]; //680 //680 //650 //650
            starY = starPoint[5]; //300 //330 //330 //300

            starMatrix2.getValues(star2Point);
            star2X = star2Point[2]; //300 //330 //330 //300
            star2Y = star2Point[5]; //620 //620 //590 //590

            starMatrix3.getValues(star3Point);
            star3X = star3Point[2]; //890 //890 //860 //860
            star3Y = star3Point[5]; //750 //780 //780 //750

            starMatrix4.getValues(star4Point);
            star4X = star4Point[2]; //490 //520 //520 //490
            star4Y = star4Point[5]; //1030 //1030 //1000 //1000

            starMatrix5.getValues(star5Point);
            star5X = star5Point[2]; //240 //240 //210 //210
            star5Y = star5Point[5]; //1250 //1280 //1280 //1250

            starMatrix6.getValues(star6Point);
            star6X = star6Point[2]; //460 //490 //490 //460
            star6Y = star6Point[5]; //1500 //1500 //1470 //1470

            if(seconds <= limit) {
                if (visited == false) {
                    if (((ballX + x) >= (starX - 49)) && ((ballX + x) <= (star.getWidth()) + (starX - 49))) {
                        if ((ballY + y >= (starY - 55)) && ((ballY + y) <= star.getHeight() + (starY - 55))) {
                            starPaint.setAlpha(255);
                            visited = true;
                            score++;
                        }
                    }
                }
                if (visited2 == false) {
                    if (((ballX + x) >= (star2X - 49)) && ((ballX + x) <= (star.getWidth()) + (star2X - 49))) {
                        if ((ballY + y >= (star2Y - 55)) && ((ballY + y) <= star.getHeight() + (star2Y - 55))) {
                            starPaint2.setAlpha(255);
                            visited2 = true;
                            score++;
                        }
                    }
                }
                if (visited3 == false) {
                    if (((ballX + x) >= (star3X - 49)) && ((ballX + x) <= (star.getWidth()) + (star3X - 49))) {
                        if ((ballY + y >= (star3Y - 55)) && ((ballY + y) <= star.getHeight() + (star3Y - 55))) {
                            starPaint3.setAlpha(255);
                            visited3 = true;
                            score++;
                        }
                    }
                }
                if (visited4 == false) {
                    if (((ballX + x) >= (star4X - 49)) && ((ballX + x) <= (star.getWidth()) + (star4X - 49))) {
                        if ((ballY + y >= (star4Y - 55)) && ((ballY + y) <= star.getHeight() + (star4Y - 55))) {
                            starPaint4.setAlpha(255);
                            visited4 = true;
                            score++;
                        }
                    }
                }
                if (visited5 == false) {
                    if (((ballX + x) >= (star5X - 49)) && ((ballX + x) <= (star.getWidth()) + (star5X - 49))) {
                        if ((ballY + y >= (star5Y - 55)) && ((ballY + y) <= star.getHeight() + (star5Y - 55))) {
                            starPaint5.setAlpha(255);
                            visited5 = true;
                            score++;
                        }
                    }
                }
                if (visited6 == false) {
                    if (((ballX + x) >= (star6X - 49)) && ((ballX + x) <= (star.getWidth()) + (star6X - 49))) {
                        if ((ballY + y >= (star6Y - 55)) && ((ballY + y) <= star.getHeight() + (star6Y - 55))) {
                            starPaint6.setAlpha(255);
                            visited6 = true;
                            score++;
                        }
                    }
                }
                result.setText("Score: " + Integer.toString(score));
            } //seconds' limit condition

            if(seconds<=limit && score == 6){
                endResult.setVisibility(VISIBLE);
                endResult.setText("WOOHO! YOU WON");
                //toast = Toast.makeText(SensorActivity.this,textWin,toastDuration);
                //toast.setGravity(Gravity.TOP,0,0);
                //toast.show();
            }else if(seconds>limit && score < 6){
                endResult.setVisibility(VISIBLE);
                endResult.setText("SORRY! YOU LOST");
                //toast = Toast.makeText(SensorActivity.this,textLost,toastDuration);
                //toast.setGravity(Gravity.TOP,0,0);
                //toast.show();
            }

            //toast.cancel();

        }

        @Override
        protected void onDraw(Canvas canvas) {
            //ball
            canvas.drawBitmap(ball,matrixBall,null);
            //star
            canvas.drawBitmap(star,starMatrix,starPaint);
            canvas.drawBitmap(star2,starMatrix2,starPaint2);
            canvas.drawBitmap(star3,starMatrix3,starPaint3);
            canvas.drawBitmap(star4,starMatrix4,starPaint4);
            canvas.drawBitmap(star5,starMatrix5,starPaint5);
            canvas.drawBitmap(star6,starMatrix6,starPaint6);
            //obstacle
            canvas.drawBitmap(support,50,220,null);
            canvas.drawBitmap(support,490,400,null);
            canvas.drawBitmap(support,300,690,null);
            canvas.drawBitmap(support,700,850,null);
            canvas.drawBitmap(support,490,1100,null);
            canvas.drawBitmap(support,50,1350,null);
            canvas.drawBitmap(support,300,1570,null);
            //To force a view to draw, call invalidate().
            invalidate();
        }
    }

    //Unregister sensor listeners when sensors are not used to save battery life;
    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this,accelerometer,SensorManager.SENSOR_DELAY_GAME);
    }

    //Sensor data can change at a high rate, which means the system may call the
    // onSensorChanged(SensorEvent) method quite often.
    // As a best practice, you should do as little as possible within the onSensorChanged(SensorEvent) method
    // so you don't block it. If your application requires you to do any data filtering or reduction of sensor data,
    // you should perform that work outside of the onSensorChanged(SensorEvent) method.
    @Override
    public void onSensorChanged(SensorEvent event) {
          if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
              x = -1*(event.values[0]); //change the ball's tilt as per the device's
              y = event.values[1];
              //double the speed
              x*=2;
              y*=2;
              ballImage.onMove(x,y);
          }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //
    }
    //KEEP MOVING STARS
    public void runimage(final BallImage ballImage){

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                m = m+90;
                ballImage.moveStar(m);
                handler.postDelayed(this,400);
            }
        });
    }
    //TIMER
    public void runTime(){

        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(seconds<=limit){
                    int secs = seconds%60;
                    int minutes = (seconds%3600)/60;
                    int hours = seconds/3600;
                    String time = String.format("%d:%02d:%02d",hours,minutes,secs);
                    textDisplay.setText(time);

                    if(running){
                        seconds++;
                    }
                    handler.postDelayed(this,1000);
                }
                return;
            }
        });
    }
}