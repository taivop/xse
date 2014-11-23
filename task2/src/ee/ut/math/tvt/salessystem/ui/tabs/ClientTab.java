package ee.ut.math.tvt.salessystem.ui.tabs;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ee.ut.math.tvt.salessystem.domain.controller.SalesDomainController;
import ee.ut.math.tvt.salessystem.ui.model.ClientTableModel;
import ee.ut.math.tvt.salessystem.ui.model.SalesSystemModel;


public class ClientTab {
    
    private SalesSystemModel model;
    
    private final SalesDomainController domainController;
    
    public ClientTab(SalesSystemModel model, SalesDomainController controller) {
        this.model = model;
        this.domainController = controller;
    }
    
    /**
     * The main entry-point method. Creates the tab.
     */
    public Component draw() {
    	// refresh data
    	this.refresh();
    	
        JPanel panel = new JPanel();
        
        GridBagConstraints gc = getGbConstraints();
        GridBagLayout gb = new GridBagLayout();
        
        panel.setLayout(gb);
        panel.add(drawClientsTable(), gc);

        return panel;
    }

    

    
    private Component drawClientsTable() {

        // Create the table 
        JTable table = new JTable(model.getClientTableModel());
        table.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(table);

        // Wrap it inside a panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Clients"));
        
        panel.add(scrollPane, getGbConstraints());
        
        return panel;
    }


    
    private GridBagConstraints getGbConstraints() {
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = GridBagConstraints.REMAINDER;
        gc.weightx = 1.0;
        gc.weighty = 1.0;
        return gc;
    }    
    
    private void refresh() {
    	ClientTableModel ctm = model.getClientTableModel();
    	ctm.populateWithData(domainController.getAllClients());
    	ctm.fireTableDataChanged();
    }
    
}
