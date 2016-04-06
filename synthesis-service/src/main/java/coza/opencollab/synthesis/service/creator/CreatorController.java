package coza.opencollab.synthesis.service.creator;

import coza.opencollab.synthesis.service.BaseController;
import coza.opencollab.synthesis.service.api.creator.StorageService;
import coza.opencollab.synthesis.shared.Tool;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.mail.javamail.ConfigurableMimeFileTypeMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * The controller for Synthesis Creator Services.
 * <p>
 * Retrieves data about available modules that can be used to create clients and
 * initiates creation of clients.
 * <p>
 * This class handle RestFull service calls using JSON.
 *
 * @author OpenCollab
 * @since 1.0.0
 */
@Controller
@RequestMapping("/service-creator")
public class CreatorController extends BaseController {

    /**
     * The Creator Service injected by Spring
     */
    @Autowired
    private CreatorService creatorService;
    /**
     * The Creator Service injected by Spring
     */
    @Autowired
    private StorageService storageService;
    /**
     * A mime type map.
     */
    private final ConfigurableMimeFileTypeMap mimeTypeMap = new ConfigurableMimeFileTypeMap();

    /**
     * Retrieves all the tools for the given module.
     *
     * @param moduleId The module id.
     * @return All the tools for the module.
     */
    @RequestMapping(value = "/tools/{moduleId}", method = RequestMethod.GET)
    public @ResponseBody
    List<Tool> getTools(@PathVariable String moduleId) {
        return creatorService.getTools(moduleId);
    }

    /**
     * Retrieves all the tools for the given module.
     *
     * @param moduleId The module id.
     */
    @RequestMapping(value = "/module/{moduleId}", method = RequestMethod.GET)
    public @ResponseBody
    void addModule(@PathVariable String moduleId) {
        creatorService.addManagedModule(moduleId);
    }

    /**
     * Download the file.
     *
     * @param downloadKey The key to reference the download.
     * @param response the HTTPResponse
     */
    @RequestMapping(value = "/download/file/{downloadKey:.+}", method = RequestMethod.GET)
    public void downloadFile(@PathVariable String downloadKey,
            HttpServletResponse response) {
        download(downloadKey, response);
    }

    /**
     * Write the file to the response stream for download.
     * 
     * @param downloadKey
     * @param response 
     */
    private void download(String downloadKey, HttpServletResponse response) {
        String fileName = Paths.get(storageService.getDownloadFileName(downloadKey)).getFileName().toString();
        response.setContentType(mimeTypeMap.getContentType(fileName
                .toLowerCase()));
        try (FileInputStream file = creatorService.getDownloadFileStream(downloadKey); OutputStream os = response.getOutputStream();) {
            response.setHeader("Content-Disposition", "attachment;filename=\""
                    + fileName + "\"");
            byte[] bytes = new byte[4096];
            int bytesRead;
            while ((bytesRead = file.read(bytes)) != -1) {
                os.write(bytes, 0, bytesRead);
            }
            os.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_GONE);
            Logger.getLogger(CreatorController.class).error(
                    "Failed to write file for download.", e);
            throw new HttpMessageNotWritableException("Can not read "  + fileName +  " file for download");
        }
    }
}