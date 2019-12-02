package org.wltea.analyzer.lucene.pinyin.handler;

import org.apache.commons.lang3.StringUtils;
import org.wltea.analyzer.utils.Pinyin4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author libinbin
 */
public class PinyinTermHandler implements TermHandler{

    public List<String> getTerms(String term) throws IOException {
       if(StringUtils.isEmpty(term)){
          return null;
       }
        List<String> list = new ArrayList<String>();
        String firstSpell = Pinyin4j.getFirstSpell(term);
        if(StringUtils.isNotEmpty(firstSpell)){
            list.add(firstSpell);
        }
        String fullSpell = Pinyin4j.getFullSpell(term);
        if(StringUtils.isNotEmpty(fullSpell)){
            list.add(fullSpell);
        }


        return list;
    }
}
