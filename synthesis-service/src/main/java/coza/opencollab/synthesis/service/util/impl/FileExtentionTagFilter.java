package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.service.api.util.TagFilter;
import java.util.List;
import org.htmlcleaner.TagNode;

/**
 * A tag filter that looks at the links in attributes and filter on a list of
 * extention names.
 *
 * Links with no extention will not be downloaded.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class FileExtentionTagFilter implements TagFilter {

    /**
     * The attribute to look at.
     */
    private String attribute;
    /**
     * The valid extentions.
     */
    private List<String> extentions;

    /**
     * The attribute to look at.
     *
     * @param attribute a {@link java.lang.String} object.
     */
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    /**
     * The valid extentions.
     *
     * @param extentions a {@link java.util.List} object.
     */
    public void setExtentions(List<String> extentions) {
        this.extentions = extentions;
    }

    /** {@inheritDoc} */
    @Override
    public boolean accept(TagNode node) {
        String extention = null;
        String link = node.getAttributeByName(attribute);
        if(link.contains("?")){
            int questionIndex = link.lastIndexOf("?");
            link = link.substring(0, questionIndex);
        }
        
        int index = link.lastIndexOf(".");
        if (index > 0) {
            extention = link.substring(0, index+1);
        }
        if(extention.length() > 4 && index > 0 ){
            extention = link.substring(index+1, link.length());
        }
        return extentions.contains(extention);
    }

}
