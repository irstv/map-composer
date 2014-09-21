package com.mapcomposer.view.utils;

import com.mapcomposer.controller.UIController;
import com.mapcomposer.model.configurationattribute.interfaces.ConfigurationAttribute;
import com.mapcomposer.model.configurationattribute.utils.CAManager;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;

/**
 * Dialog windows displaying the GraphicalElements attributes.
 */
public class DialogProperties extends JFrame{
    
    /** JPanel containing the body ofthe shutter */
    private final JPanel body;
    
    /** Validation button*/
    private final JButton validate;
    
    /** Cancel button*/
    private final JButton cancel;
    
    /** JPanel of the configuration elements */
    private JPanel pan;
    
    /** List of ConfPanel displayed */
    private List<ConfPanel> listPanels;
    
    /**main constructor
     * @param list List of ConfigurationAttributes to display.*/
    public DialogProperties(List<ConfigurationAttribute> list){
        body = new JPanel(new MigLayout("wrap 2"));
        
        //Positionning the shutter
        this.setLayout(new BorderLayout());
        this.add(body, BorderLayout.CENTER);
        
        listPanels = new ArrayList<>();
        validate = new JButton("Validate");
        validate.addActionListener(EventHandler.create(ActionListener.class, this, "validation"));
        cancel = new JButton("Cancel");
        cancel.addActionListener(EventHandler.create(ActionListener.class, this, "clearAndHide"));
        pan = new JPanel();
        pan.setLayout(new MigLayout("wrap 1"));
        for(ConfigurationAttribute ca : list){
            JPanel panel = CAManager.getInstance().getRenderer(ca).render(ca);
            ConfPanel cp = new ConfPanel(panel, ca);
            listPanels.add(cp);
            pan.add(cp, "wrap");
        }
        body.add(pan, "wrap");
        body.add(validate);
        body.add(cancel);
        this.pack();
    }
    
    public void validation() {
        List<ConfigurationAttribute> listca = new ArrayList<>();
        for(ConfPanel cp : listPanels){
            listca.add(cp.getCA());
        }
        UIController.getInstance().validate(listca);
        clearAndHide();
    }

    /** This method clears the dialog and hides it. */
    public void clearAndHide() {
        pan = new JPanel();
        listPanels = new ArrayList<>();
        this.setContentPane(pan);
        setVisible(false);
    }
    
    /**
     * Extenson of the JPanel used to display the ConfigurationAttributes.
     * It also permite to lock and unlock the fields. 
     */
    private class ConfPanel extends JPanel implements ItemListener{
        private final JPanel pan;
        private final JCheckBox box;
        private final ConfigurationAttribute ca;
        public ConfPanel(JPanel pan, ConfigurationAttribute ca){
            super();
            this.pan=pan;
            this.ca = ca;
            this.setLayout(new FlowLayout(FlowLayout.LEFT));
            box = new JCheckBox();
            box.addItemListener(this);
            box.setSelected(ca.isLocked());
            this.add(box);
            this.add(pan);
            if(ca.isLocked()){
                pan.setEnabled(false);
                for(Component c : pan.getComponents())
                    c.setEnabled(false);
            }
        }
        
        public ConfigurationAttribute getCA(){
            CAManager.getInstance().getRenderer(ca).extractValue(pan, ca);
            return ca;
        }

        @Override
        public void itemStateChanged(ItemEvent ie) {
            boolean b = ((JCheckBox)ie.getSource()).isSelected();
            pan.setEnabled(!b);
            for(Component c : pan.getComponents())
                c.setEnabled(!b);
            ca.setLock(b);
            this.repaint();
            this.revalidate();
        }
    }
}