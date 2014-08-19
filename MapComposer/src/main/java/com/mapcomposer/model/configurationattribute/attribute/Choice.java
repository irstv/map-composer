package com.mapcomposer.model.configurationattribute.attribute;

import com.mapcomposer.model.configurationattribute.CAList;

/**
 * The Choice attribute contains severals fields that can be selected.
 */
public class Choice extends CAList<String>{
    
    /**
     * Main constructor.
     * @param name Name of the Choice in its GraphicalElement.
     */
    public Choice(String name) {
        super(name);
    }
}
