package Data;

import javafx.scene.paint.Color;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.Properties;

public class CommonValues {

    private static CommonValues instance = null;
    private Properties properties;

    public double dancershape_arcRadius;
    public double gridview_canvasHeight;
    public double gridview_canvasWidth;
    public int gridview_xFields;
    public int gridview_yFields;
    public DecimalFormat decimalFormat;
    public boolean gridview_maleVisible;
    public boolean gridview_femaleVisible;
    public boolean gridview_gridVisible;
    public double animation_framerate;
    public boolean dancershape_snapping;
    public double dancershape_snappingprecision;
    public Color crosshair_color;
    public Color maledancer_color;
    public Color connected_color;
    public Color femaledancer_color;
    public Color selecteddancer_color;
    public Color crosshairLabel_backgroundcolor;
    public Color crosshairLabel_fontcolor;
    public boolean editView_connectDrag;


    private CommonValues(Properties properties){
        this.properties = properties;

        dancershape_arcRadius = getValue("dancershape_arcRadius", 20.0);
        gridview_canvasHeight = getValue("gridview_canvasHeight", 600.0);
        gridview_canvasWidth = getValue("gridview_canvasWidth",600.0);
        gridview_xFields = getValue("gridview_xFields", 16);
        gridview_yFields = getValue("gridview_yFields", 16);
        try{
            decimalFormat = new DecimalFormat(getValue("crosshairformat", "0.00"));
        }catch(Exception e){
            decimalFormat = new DecimalFormat("0.00");
        }
        gridview_maleVisible = getValue("gridview_maleVisible", true);
        gridview_femaleVisible = getValue("gridview_femaleVisible", true);
        gridview_gridVisible = getValue("gridview_gridVisible", true);
        crosshair_color = getColor("crosshair_color", Color.CHOCOLATE);
        maledancer_color = getColor("maledancer_color", Color.BLUE);
        femaledancer_color = getColor("femaledancer_color", Color.RED);
        connected_color = Color.color((maledancer_color.getRed() + femaledancer_color.getRed())/2,
                (maledancer_color.getGreen() + femaledancer_color.getGreen())/2,
        (maledancer_color.getBlue() + femaledancer_color.getBlue())/2,
        (maledancer_color.getOpacity() + femaledancer_color.getOpacity())/2);
        selecteddancer_color = getColor("selecteddancer_color", Color.GOLDENROD);
        crosshairLabel_backgroundcolor = getColor("crosshairLabel_backgroundcolor", new Color(0.2, 0.2, 0.2, 1));
        crosshairLabel_fontcolor = getColor("crosshairLabel_fontcolor", Color.WHITE);
        animation_framerate = getValue("animation_framerate", 30.0);
        dancershape_snapping = getValue("dancershape_snapping", true);
        dancershape_snappingprecision = getValue("dancershape_snappingprecision", 0.5);
        editView_connectDrag = getValue("editView_connectDrag", false);
    }

    public void loadValuesTo(Properties properties){
        Field[] fields = CommonValues.class.getFields();
        for(Field f : fields) {
            //System.out.println(f.getName());
            try {
                //System.out.println(f.get(this));
                properties.put(f.getName(), f.get(this));
            }catch(IllegalAccessException e){
                System.err.println(e);
            }
        }
    }

    public Properties getProperties(){
        properties.clear();
        loadValuesTo(properties);
        return properties;
    }

    private double getValue(String propertyName, double defaultValue){
        double value;
        try {
            value = Double.parseDouble(properties.getProperty(propertyName));
        }catch (Exception e){
            System.out.println("default set for "+propertyName);
            value = defaultValue;
        }
        return value;
    }

    private int getValue(String propertyName, int defaultValue){
        int value;
        try {
            value = Integer.parseInt(properties.getProperty(propertyName));
        }catch (Exception e){
            System.out.println("default set for "+propertyName);
            value = defaultValue;
        }
        return value;
    }

    private boolean getValue(String propertyName, boolean defaultValue){
        boolean value;
        String bool = properties.getProperty(propertyName);
        if(bool != null){
            value = Boolean.parseBoolean(bool);
        }else{
            System.out.println("default set for "+propertyName);
            value = defaultValue;
        }
        return value;
    }

    private String getValue(String propertyName, String defaultValue){
        String value;
        try {
            value = properties.getProperty(propertyName);
        }catch (Exception e){
            System.out.println("default set for "+propertyName);
            value = defaultValue;
        }
        return value;
    }

    private Color getColor(String propertyName, Color defaultValue){
        Color value;

        try{
            String colorscheme = properties.getProperty(propertyName);
            value = Color.valueOf(colorscheme);
        }catch (Exception e){
            try {
                String colorscheme = properties.getProperty(propertyName);
                String[] colors = colorscheme.split(",");
                value = new Color(
                        Double.parseDouble(colors[0]),
                        Double.parseDouble(colors[1]),
                        Double.parseDouble(colors[2]),
                        Double.parseDouble(colors[3])
                );
            }catch (Exception ee){
                System.out.println("default set for "+propertyName);
                value = defaultValue;
            }
        }
        return value;
    }

    static CommonValues getInstance(Properties properties){
        if(instance == null) instance = new CommonValues(properties);
        return instance;
    }
}
