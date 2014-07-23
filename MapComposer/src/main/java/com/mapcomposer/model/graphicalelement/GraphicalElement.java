package com.mapcomposer.model.graphicalelement;

import java.util.ArrayList;
import java.util.List;
import com.mapcomposer.model.configurationattribute.ConfigurationAttribute;
import com.mapcomposer.model.configurationattribute.attribute.Numeric;

/**
 * Root class for the GraphicalElements (GraphicalElement) objects.
 * It contains all the main ConfigurationAttributes (CA) and their setter and getters.
 */
public abstract class GraphicalElement {
    /** x position of the GE.*/
    private final Numeric x;
    /** y position of the GE.*/
    private final Numeric y;
    /** Inclination of the GE.*/
    private final Numeric rotation;
    /** Heght of the GE.*/
    private final Numeric height;
    /** Width of the GE.*/
    private final Numeric width;
    
    /**
     * Main constructor.
     */
    public GraphicalElement(){
        x=new Numeric("x");
        y=new Numeric("y");
        rotation=new Numeric("Rotation");
        height=new Numeric("Height");
        width=new Numeric("Width");
        
        x.setValue(0);
        y.setValue(0);
        rotation.setValue(0);
        height.setValue(0);
        width.setValue(0);
    }

    /**
     * Sets the x position.
     * @param x The x position of the GE.
     */
    public void setX(Integer x) {
        this.x.setValue(x);
    }

    /**
     * Sets the y position.
     * @param y The y position of the GE.
     */
    public void setY(int y) {
        this.y.setValue(y);
    }

    /**
     * Sets the rotation angle.
     * @param rotation The rotation angle of the GE.
     */
    public void setRotation(int rotation) {
        this.rotation.setValue(rotation);
    }

    /**
     * Sets the height.
     * @param height The height of the GE.
     */
    public void setHeight(int height) {
        this.height.setValue(height);
    }

    /**
     * Sets the width.
     * @param width The width of the GE.
     */
    public void setWidth(int width) {
        this.width.setValue(width);
    }
    
    /**
     * Returns all the attributes. This function need be be redefined in child class.
     * @return List of all the attributes.
     */
    public List<ConfigurationAttribute> getAllAttributes() {
        List<ConfigurationAttribute> list = new ArrayList<>();
        list.add(x);
        list.add(y);
        list.add(rotation);
        list.add(height);
        list.add(width);
        return list;
    }
}
