package coza.opencollab.synthesis.service.util.impl;

import coza.opencollab.synthesis.service.api.util.LinkDownLoader;
import coza.opencollab.synthesis.service.api.util.LinkFileConsumer;
import java.util.ArrayList;
import java.util.List;

/**
 * This class inspects html and download referenced files.
 *
 * @author OpenCollab
 * @since 1.0.0
 * @version 1.0.1
 */
public class MultiLinkDownLoader implements LinkDownLoader{
    /**
     * A list of LinkDownloaders to use.
     */
    private List<LinkDownLoader> linkDownloaders = new ArrayList<LinkDownLoader>();

    /**
     * A list of LinkDownloaders to use.
     *
     * @param linkDownloaders a {@link java.util.List} object.
     */
    public void setLinkDownloaders(List<LinkDownLoader> linkDownloaders) {
        this.linkDownloaders = linkDownloaders;
    }
    
    /** {@inheritDoc} */
    @Override
    public String update(String key, String content, LinkFileConsumer consumer) {
        for(LinkDownLoader linkDownloader: linkDownloaders){
            content = linkDownloader.update(key, content, consumer);
        }
        return content;
    }
}
