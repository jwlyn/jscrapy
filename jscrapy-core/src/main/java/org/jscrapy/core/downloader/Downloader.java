package org.jscrapy.core.downloader;

import org.jscrapy.core.JscrapyComponent;
import org.jscrapy.core.page.Page;
import org.jscrapy.core.request.HttpRequest;

/**
 * Created by cxu on 2014/11/21.
 */
public abstract class Downloader extends JscrapyComponent {

    public abstract Page download(HttpRequest request);
}
