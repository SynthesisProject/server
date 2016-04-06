/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coza.opencollab.synthesis.service;

import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.api.lms.LMSClient;
import coza.opencollab.synthesis.service.api.lms.LMSParser;
import java.util.HashMap;
import java.util.Map;

/**
 * Manages the implementations used by the different repos
 *
 * @author OpenCollab
 * @version 1.0.1
 */
public class RepoManager {
    
    private Map<String, LMSClient> clients = new HashMap<>();
    private Map<String, LMSParser> parsers = new HashMap<>();

    /**
     * <p>getLMSClient.</p>
     *
     * @param repo a {@link java.lang.String} object.
     * @return a {@link coza.opencollab.synthesis.service.api.lms.LMSClient} object.
     */
    public LMSClient getLMSClient(String repo) {
        return clients.get(repo);
    }
    
    /**
     * <p>getLMSClient.</p>
     *
     * @param managedModule a {@link coza.opencollab.synthesis.service.api.dbo.ManagedModule} object.
     * @return a {@link coza.opencollab.synthesis.service.api.lms.LMSClient} object.
     */
    public LMSClient getLMSClient(ManagedModule managedModule) {
        return getLMSClient(managedModule.getModuleId());
    }
    
    /**
     * <p>Getter for the field <code>clients</code>.</p>
     *
     * @return the clients
     */
    public Map<String, LMSClient> getClients() {
        return clients;
    }

    /**
     * <p>Setter for the field <code>clients</code>.</p>
     *
     * @param clients the clients to set
     */
    public void setClients(Map<String, LMSClient> clients) {
        this.clients = clients;
    }

    /**
     * <p>Getter for the field <code>parsers</code>.</p>
     *
     * @return the parsers
     */
    public Map<String, LMSParser> getParsers() {
        return parsers;
    }

    /**
     * <p>Setter for the field <code>parsers</code>.</p>
     *
     * @param parsers the parsers to set
     */
    public void setParsers(Map<String, LMSParser> parsers) {
        this.parsers = parsers;
    }
    
    
    
}
