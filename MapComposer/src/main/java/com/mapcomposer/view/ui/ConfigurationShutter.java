package com.mapcomposer.view.ui;

import com.mapcomposer.controller.UIController;
import com.mapcomposer.model.configurationattribute.ConfigurationAttribute;
import com.mapcomposer.model.configurationattribute.utils.CAManager;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * Lateral shutter containing the configuration elements.
 */
public class ConfigurationShutter extends Shutter implements MouseListener{
    
    /**Unique instance of the class*/
    private static ConfigurationShutter INSTANCE = null;
    
    /** Validation button*/
    private final JButton validate;
    
    /** JPanel of the configuration elements */
    private JPanel pan;
    
    /** List of ConfPanel displayed */
    private List<ConfPanel> listPanels;
    
    /**Private constructor*/
    private ConfigurationShutter(){
        super(300, Shutter.LEFT_SHUTTER);
        listPanels = new ArrayList<>();
        validate = new JButton("Validate");
        validate.addMouseListener(this);
        pan = new JPanel();
    }
    
    /** 
     * Displays in the configurationShutter JPanel all the UI element for the configuration of he differents CA
     * @param list List of ConfigurationAttributes to display.
     */
    public void dispalyConfiguration(List<ConfigurationAttribute> list){
        listPanels = new ArrayList<>();
        pan = new JPanel();
        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
        for(ConfigurationAttribute ca : list){
            JPanel panel = CAManager.getInstance().getRenderer(ca).render(ca);
            //It align the button to le left, but why ?
            panel.setAlignmentX(JPanel.TOP_ALIGNMENT);
            ConfPanel cp = new ConfPanel(panel, ca);
            listPanels.add(cp);
            pan.add(cp);
        }
        pan.add(validate);
        this.setBodyPanel(pan);
        this.open();
    }
    
    /**
     * Returns the unique instace of the class.
     * @return The unique instance of te class.
     */
    public static ConfigurationShutter getInstance(){
        if(INSTANCE==null){
            INSTANCE = new ConfigurationShutter();
        }
        return INSTANCE;
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        if(e.getSource()==validate){
            List<ConfigurationAttribute> listca = new ArrayList<>();
            for(ConfPanel cp : listPanels){
                listca.add(cp.getCA());
            }
            UIController.getInstance().validate(listca);
            eraseConfiguration();
        }
    }
    
    /**
     * Erases all the element displayed in the shutter and close it.
     */
    public void eraseConfiguration(){
        pan = new JPanel();
        listPanels = new ArrayList<>();
        this.setBodyPanel(pan);
        this.close();
    }
    
    
    private class ConfPanel extends JPanel implements ItemListener{
        private final JPanel pan;
        private final JCheckBox box;
        private final ConfigurationAttribute ca;
        public ConfPanel(JPanel pan, ConfigurationAttribute ca){
            super();
            this.pan=pan;
            this.ca = ca;
            this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            box = new JCheckBox();
            box.addItemListener(this);
            box.setSelected(ca.isLocked());
            this.add(box);
            this.add(pan);
            if(ca.isLocked()){
                pan.disable();
                for(Component c : pan.getComponents())
                    c.disable();
            }
        }
        
        public ConfigurationAttribute getCA(){
            CAManager.getInstance().getRenderer(ca).extractValue(pan, ca);
            return ca;
        }

        @Override
        public void itemStateChanged(ItemEvent ie) {
            if(!((JCheckBox)ie.getSource()).isSelected()){
                pan.enable();
                for(Component c : pan.getComponents())
                    c.enable();
                ca.unlock();
            }
            if(((JCheckBox)ie.getSource()).isSelected()){
                pan.disable();
                for(Component c : pan.getComponents())
                    c.disable();
                ca.lock();
            }
            this.repaint();
            this.revalidate();
        }
    }
}
