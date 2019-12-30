package GUI.View;

import Data.*;
import GUI.DancerShape;
import javafx.animation.*;
import javafx.scene.CacheHint;
import javafx.util.Duration;

import java.util.Vector;

/**
 * Class for animating the data
 */
public class AnimateView extends GridView {
    /*  Startbild - Endbild
        Zeitintervall für den Übergang von einem Bild ins nächste
        Intervallslider, um die Übergänge zu beschleunigen.

     */

    private static CommonValues cv;
    SequentialTransition transition = new SequentialTransition();
    boolean caching = true;
    CacheHint cacheHint = CacheHint.SPEED;
    Duration animationspeed = Duration.millis(5000);
    FrameratePrinter frameratePrinter;

    public AnimateView() {
        cv = Data.getInstance().getCommonValues();
        rootLayer.setId("animate");
    }

    private class FrameratePrinter extends AnimationTimer{
        private long lastTime = 0;
        private boolean running = false;

        public boolean isRunning() {
            return running;
        }

        @Override
        public void start() {
            super.start();
            running = true;
        }

        @Override
        public void stop() {
            super.stop();
            running = false;
        }

        @Override
        public void handle(long now) {
            //printFPS();
            //System.out.println("framerate = " + TimeUnit.MILLISECONDS.convert(now - lastTime, TimeUnit.NANOSECONDS));
            System.out.println("framerate = " + (now - lastTime) / 1000_000);
            lastTime = now;
        }
    }

    public void buildView() {
        rootLayer.getChildren().clear();
        super.buildView();
        rootLayer.getChildren().add(getGridLayer());
        rootLayer.getChildren().addAll(getDancers().toArray(new DancerShape[0]));
    }


    public void testAnimate(Shapesequence sequence) {
        double r = -90 + ((360.0 - 240.0) / 2);
        Timeline timeline1 = new Timeline();
        Timeline timeline2 = new Timeline();
        DancerShape dancer = getDancers().elementAt(0);
        dancer.setCache(true);
        dancer.setCacheHint(CacheHint.SPEED);
        Shape shape1 = sequence.getShape(1);
        Shape shape2 = sequence.getShape(2);
        Pose pose1 = shape1.getPoseOf(dancer.getPose().getDancer());
        Pose pose2 = shape2.getPoseOf(dancer.getPose().getDancer());
        timeline1.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(dancer.layoutXProperty(), convertGridToPaneX(pose1.getX()) + cv.dancershape_arcRadius)));
        timeline1.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(dancer.layoutYProperty(), convertGridToPaneY(pose1.getY()) + cv.dancershape_arcRadius)));
        timeline1.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(dancer.startAngleProperty(), pose1.getTheta() + r)));
        timeline2.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(dancer.layoutXProperty(), convertGridToPaneX(pose2.getX()) + cv.dancershape_arcRadius)));
        timeline2.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(dancer.layoutYProperty(), convertGridToPaneY(pose2.getY()) + cv.dancershape_arcRadius)));
        timeline2.getKeyFrames().add(new KeyFrame(Duration.seconds(5), new KeyValue(dancer.startAngleProperty(), pose2.getTheta() + r)));


        SequentialTransition transition = new SequentialTransition();
        transition.getChildren().addAll(timeline1, timeline2);
        transition.play();
    }

    public void startAnimate(Vector<Shape> sequence) {
        Vector<DancerShape> dancers = getDancers();
        //transition.stop();
        transition.getChildren().clear();

        double r = -90 + ((360.0 - 240.0) / 2);
        for (int shapeIndex = 1; shapeIndex < sequence.size(); shapeIndex++) {
            //Shape currentShape = sequence.getShape(shapeIndex - 1);
            Shape nextShape = sequence.get(shapeIndex);
            Timeline timeline = new Timeline(cv.animation_framerate);
            Interpolator interpolator = Interpolator.LINEAR;
            for (DancerShape dancer : dancers) {
                dancer.setCache(caching);
                dancer.setCacheHint(cacheHint);
                if(nextShape.hasPose(dancer.getPose().getDancer())) {
                    Pose nextPose = nextShape.getPoseOf(dancer.getPose().getDancer());
                    timeline.getKeyFrames().add(new KeyFrame(
                            animationspeed,
                            new KeyValue(dancer.layoutXProperty(),
                                    convertGridToPaneX(nextPose.getX()) + cv.dancershape_arcRadius, interpolator)));
                    timeline.getKeyFrames().add(new KeyFrame(
                            animationspeed,
                            new KeyValue(dancer.layoutYProperty(),
                                    convertGridToPaneY(nextPose.getY()) + cv.dancershape_arcRadius, interpolator)));
                    timeline.getKeyFrames().add(new KeyFrame(
                            animationspeed,
                            new KeyValue(dancer.startAngleProperty(),
                                    nextPose.getTheta() + r, interpolator)));
                }
            }
            transition.getChildren().addAll(timeline);
        }
        transition.play();
    }

    /**
     * just for testing the AnimationTimer
     */
    public void startAnimate2(Vector<Shape> sequence) {
        DancerShape dancer = getDancers().elementAt(0);
        Shape shape1 = sequence.get(1);
        Shape shape2 = sequence.get(2);
        Pose startPose = dancer.getPose();
        Pose pose1 = shape1.getPoseOf(dancer.getPose().getDancer());
        Pose pose2 = shape2.getPoseOf(dancer.getPose().getDancer());
        double stepSizeX = (pose1.getX() - startPose.getX())/600;
        double stepSizeY = (pose1.getY() - startPose.getY())/600;
        double stepSizeT = (pose1.getTheta() - startPose.getTheta())/600;
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                double newX = dancer.getPose().getX() + stepSizeX;
                double newY = dancer.getPose().getY() + stepSizeY;
                startPose.moveX(stepSizeX);
                startPose.moveY(stepSizeY);
                startPose.setTheta(startPose.getTheta() + stepSizeT);
                moveDancers();
            }
        };

        timer.start();
    }

    public void toggleAnimate() {
        if (transition.getStatus() == Animation.Status.RUNNING) {
            transition.pause();
            //frameratePrinter.stop();
        } else {
            transition.play();
            //frameratePrinter.start();
        }

    }

    public void setAnimationRate(double rate){
        transition.setRate(rate);
    }

    public void startFrameratePrinter(){
        frameratePrinter = new FrameratePrinter();
        frameratePrinter.start();
    }

    public void toggleFrameratePrinting(){
        if (frameratePrinter.isRunning()){
            frameratePrinter.stop();
        }else{
            frameratePrinter.start();
        }
    }
}
