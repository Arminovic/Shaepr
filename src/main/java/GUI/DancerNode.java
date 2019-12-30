package GUI;

public interface DancerNode {

    /*public void relocate(double x, double y) {
        //setLayoutX(x - getLayoutBounds().getMinX());
        //setLayoutY(y - getLayoutBounds().getMinY());
        System.out.println("Pose = " + Formatter.coordinates(pose.getX(), pose.getY()));
        setLayoutX(parent.convertGridToPaneX(x + cv.dancershape_arcRadius));
        setLayoutY(parent.convertGridToPaneY(y + cv.dancershape_arcRadius));
        System.out.println("Layout = " + Formatter.coordinates(getLayoutX(), getLayoutY()));

        *//*PlatformLogger logger = Logging.getLayoutLogger();
        if (logger.isLoggable(PlatformLogger.Level.FINER)) {
            logger.finer(this.toString()+" moved to ("+x+","+y+")");
        }*//*
    }*/

    void relocate();

    void setOrientation();
}
