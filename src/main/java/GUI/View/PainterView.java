package GUI.View;

import Data.Shape;
import GUI.DancerShape;

public class PainterView extends GridView {

    @Override
    public void buildView(Shape shape) {
        rootLayer.getChildren().clear();
        super.buildView(shape);
        //rootLayer.getChildren().add(getGridLayer());
        rootLayer.getChildren().addAll(getDancers().toArray(new DancerShape[0]));
    }
}
