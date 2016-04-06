package coza.opencollab.synthesis.service.api.util;

import org.htmlcleaner.TagNode;

/**
 * A filter for tagnodes.
 *
 * @author Openoollab
 * @since 1.0.0
 * @version 1.0.0
 */
public interface TagFilter {
    /**
     * Test whether to accept the node.
     *
     * @param node The node.
     * @return true if it is accepted.
     */
    public boolean accept(TagNode node);
}
