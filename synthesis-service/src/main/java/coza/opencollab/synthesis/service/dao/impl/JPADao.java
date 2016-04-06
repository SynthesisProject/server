package coza.opencollab.synthesis.service.dao.impl;

import coza.opencollab.synthesis.SynthesisException;
import coza.opencollab.synthesis.service.api.Defaults;
import static coza.opencollab.synthesis.service.api.ErrorCodes.*;
import coza.opencollab.synthesis.service.api.dbo.ContentVersion;
import coza.opencollab.synthesis.service.api.dao.Dao;
import coza.opencollab.synthesis.service.api.dbo.ManagedModule;
import coza.opencollab.synthesis.service.api.dbo.ModuleTools;
import coza.opencollab.synthesis.service.api.dbo.Tool;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The JPA implementation of the RegistrationsDao.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
@Transactional(propagation = Propagation.REQUIRED)
@Repository
public class JPADao implements Dao {

    /**
     * The thread save entity manager
     */
    @PersistenceContext
    private EntityManager em;

    //----- Mics -----//    
    /**
     * {@inheritDoc}
     *
     * Retrieve the tool names for the device.
     */
    @Override
    public List<Tool> getTools() {
        try {
            Query query = em.createNamedQuery("allTools");
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public <E extends Serializable> E update(E entity) {
        return em.merge(entity);
    }

    //----- ManagedModule -----//
    /** {@inheritDoc} */
    @Override
    public ManagedModule createManagedModule(String moduleId) {
        ManagedModule managedModule = new ManagedModule();
        managedModule.setModuleId(moduleId);
        managedModule.setActive(true);
        try {
            em.persist(managedModule);
            return managedModule;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<ManagedModule> getManagedModules() {
        try {
            Query query = em.createNamedQuery("managedModules");
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /** {@inheritDoc} */
    @Override
    public List<ManagedModule> getInactiveManagedModules() {
        try {
            Query query = em.createNamedQuery("inactiveManagedModules");
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }    

    /** {@inheritDoc} */
    @Override
    public ManagedModule getManagedModule(String moduleId) {
        try {
            Query query = em.createNamedQuery("managedModule");
            query.setParameter(Defaults.PARAMETER_MODULE_ID, moduleId);
            ManagedModule managedModule = (ManagedModule) query.getSingleResult();
            Hibernate.initialize(managedModule.getTools());
            return managedModule;
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public ManagedModule getManagedModuleById(Long id) {
        try {
            Query query = em.createNamedQuery("managedModuleById");
            query.setParameter("id", id);
            ManagedModule managedModule = (ManagedModule) query.getSingleResult();
            Hibernate.initialize(managedModule.getTools());
            return managedModule;
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public void setManagedModule(ManagedModule mm) {
        try {
            em.merge(mm);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void deleteManagedModule(Long id) {
        try {
            ManagedModule mm = getManagedModuleById(id);
            em.remove(mm);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    //----- ContentVersion -----//
    /**
     * <p>getAllContentVersions.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<ContentVersion> getAllContentVersions() {
        try {
            Query query = em.createNamedQuery("contentVersionAll");
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public ContentVersion createContentVersion(ManagedModule managedModule, String toolName, String version) {
        ContentVersion contentVersion = new ContentVersion();
        contentVersion.setModuleRegistrationId(managedModule.getId());
        contentVersion.setToolName(toolName);
        contentVersion.setContentVersion(version);
        contentVersion.setActive(true);
        try {
            em.persist(contentVersion);
            return contentVersion;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public ContentVersion getContentVersion(ManagedModule managedModule, String toolName) {
        try {
            Query query = em.createNamedQuery("contentVersionActive");
            query.setParameter(Defaults.PARAMETER_MODULE_REGISTRATION_ID, managedModule.getId());
            query.setParameter(Defaults.PARAMETER_TOOL_NAME, toolName);
            return (ContentVersion) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public ContentVersion getContentVersion(ManagedModule managedModule, String toolName, String contentVersion) {
        try {
            Query query = em.createNamedQuery("contentVersion");
            query.setParameter(Defaults.PARAMETER_MODULE_REGISTRATION_ID, managedModule.getId());
            query.setParameter(Defaults.PARAMETER_TOOL_NAME, toolName);
            query.setParameter(ContentVersion.PARAMETER_CONTENT_VERSION, contentVersion);
            return (ContentVersion) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public ContentVersion getContentVersionCloseTo(ManagedModule managedModule, String toolName, String contentVersion) {
        try {
            Query query = em.createNamedQuery("contentVersionCloseTo");
            query.setMaxResults(1);
            query.setParameter(Defaults.PARAMETER_MODULE_REGISTRATION_ID, managedModule.getId());
            query.setParameter(Defaults.PARAMETER_TOOL_NAME, toolName);
            query.setParameter(ContentVersion.PARAMETER_CONTENT_VERSION, contentVersion);
            return (ContentVersion) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public ContentVersion getContentVersionWithNullCheck(String username, String moduleId, String toolName) throws SynthesisException {
        ContentVersion contentVersion = getContentVersion(getManagedModule(moduleId), toolName);
        if (contentVersion == null) {
            throw new SynthesisException(CONTENT_VERSION, "No content registration found for '"
                    + username + ":"
                    + moduleId + ":" + toolName + "'.");
        }
        return contentVersion;
    }

    /** {@inheritDoc} */
    @Override
    public ContentVersion getMasterContentVersion(String moduleId, String toolName) throws SynthesisException {
        ContentVersion contentVersion = getContentVersion(getManagedModule(moduleId), toolName);
        if (contentVersion == null) {
            throw new SynthesisException(CONTENT_VERSION, "No master content registration found for module '" + moduleId + "' and tool '"
                    + toolName + "'.");
        }
        return contentVersion;
    }

    /** {@inheritDoc} */
    @Override
    public List<ContentVersion> getContentVersions(ManagedModule managedModule) {
        try {
            Query query = em.createNamedQuery("contentVersions");
            query.setParameter(Defaults.PARAMETER_MODULE_REGISTRATION_ID, managedModule.getId());
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<ContentVersion> getContentVersionsWithNullCheck(String username, String moduleId) {
        return getContentVersions(getManagedModule(moduleId));
    }

    /** {@inheritDoc} */
    @Override
    public List<ContentVersion> getMasterContentVersions(String moduleId) {
        return getContentVersions(getManagedModule(moduleId));
    }

    /** {@inheritDoc} */
    @Override
    public List<ContentVersion> getContentVersions(ManagedModule managedModule, String toolName) {
        try {
            Query query = em.createNamedQuery("contentVersionsForTool");
            query.setParameter(Defaults.PARAMETER_MODULE_REGISTRATION_ID, managedModule.getId());
            query.setParameter(Defaults.PARAMETER_TOOL_NAME, toolName);
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public List<ContentVersion> getContentVersions(ManagedModule managedModule, String toolName, String fromContentVersion, String toContentVersion) {
        ContentVersion from = getContentVersion(managedModule, toolName, fromContentVersion);
        ContentVersion to = getContentVersion(managedModule, toolName, toContentVersion);
        try {
            Query query = em.createNamedQuery("contentVersionRange");
            query.setParameter(Defaults.PARAMETER_MODULE_REGISTRATION_ID, managedModule.getId());
            query.setParameter(Defaults.PARAMETER_TOOL_NAME, toolName);
            query.setParameter("versionFrom", from == null ? "0" : from.getContentVersion());
            query.setParameter("versionTo", to.getContentVersion());
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public int deactivateAllContentVersions(Long moduleRegistrationId, String toolName) {
        try {
            Query query = em.createNamedQuery("contentVersionDeactivateAll");
            query.setParameter(Defaults.PARAMETER_MODULE_REGISTRATION_ID, moduleRegistrationId);
            query.setParameter(Defaults.PARAMETER_TOOL_NAME, toolName);
            return query.executeUpdate();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public int deactivateAllOther(ContentVersion contentVersion) {
        try {
            Query query = em.createNamedQuery("contentVersionDeactivate");
            query.setParameter(Defaults.PARAMETER_MODULE_REGISTRATION_ID, contentVersion.getModuleRegistrationId());
            query.setParameter(Defaults.PARAMETER_TOOL_NAME, contentVersion.getToolName());
            query.setParameter(ContentVersion.PARAMETER_CONTENT_VERSION, contentVersion.getContentVersion());
            return query.executeUpdate();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deactivate(ContentVersion contentVersion) {
        if (contentVersion != null) {
            contentVersion.setActive(false);
            em.merge(contentVersion);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void deleteContentVersion(ManagedModule managedModule) {
        try {
            List<ContentVersion> cvs = getContentVersions(managedModule);
            for (ContentVersion cv : cvs) {
                em.remove(cv);
            }
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    private Object getHibernateTemplate() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * {@inheritDoc}
     *
     * @return a {@link java.util.List} object.
     */
    public List<ModuleTools> getModuleTools() {
        try {
            Query query = em.createNamedQuery("moduleTools");
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    public List<ModuleTools> getModuleToolsPerModule(Long moduleId) {
        try {
            Query query = em.createNamedQuery("moduleToolsPerModule");
            return query.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public int deleteModuleTool(Long moduleId, Long toolId) {
        try {
            Query query = em.createNamedQuery("deleteModuleTool");
            query.setParameter("moduleId", moduleId);
            query.setParameter("toolId", toolId);
            return query.executeUpdate();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    public Tool getToolById(Long id) {
        try {
            Query query = em.createNamedQuery("getToolById");
            query.setParameter("id", id);
            return (Tool) query.getSingleResult();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public Tool createTool(Tool tool) {
        try {
            em.persist(tool);
            return tool;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void setTool(Tool tool) {
        try {
            em.merge(tool);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /** {@inheritDoc} */
    @Override
    @Transactional(readOnly = false, rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void deleteTool(Tool tool) {
        try {
            Tool t = em.find(Tool.class, tool.getId());
            em.remove(t);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
}
