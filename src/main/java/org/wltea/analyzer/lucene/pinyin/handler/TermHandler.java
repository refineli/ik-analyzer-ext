package org.wltea.analyzer.lucene.pinyin.handler;

import java.io.IOException;
import java.util.List;

/**
 * @author libinbin
 */
public interface TermHandler {

    List<String> getTerms(String term) throws IOException;

}
