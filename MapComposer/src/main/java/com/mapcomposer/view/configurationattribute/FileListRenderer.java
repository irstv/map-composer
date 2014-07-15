package com.mapcomposer.view.configurationattribute;

import com.mapcomposer.model.configurationattribute.ConfigurationAttribute;
import com.mapcomposer.model.configurationattribute.attribute.FileList;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Renderer associated to the FileList ConfigurationAttribute.
 */
public class FileListRenderer implements CARenderer{

    @Override
    public JPanel render(ConfigurationAttribute ca) {
        JPanel pan = new JPanel();
        pan.setLayout(new FlowLayout());
        
        FileList filelist = (FileList)ca;
        
        pan.add(new JLabel(filelist.getPropertyName()));
        JComboBox list = new JComboBox(filelist.getPropertyValue().toArray(new String[0]));
        pan.add(list);
        
        return pan;
    }
    
}