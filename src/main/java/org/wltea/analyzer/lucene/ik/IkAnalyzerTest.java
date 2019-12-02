package org.wltea.analyzer.lucene.ik;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.utils.AnalyzerUtils;

import java.io.IOException;

public class IkAnalyzerTest {

    public static void main(String[] args) throws IOException {

        String text = "我在时光里享受温暖，我在流年里忘记花开";
        Analyzer analyzer = new IKAnalyzer();
        AnalyzerUtils.displayTokens(analyzer, text);
    }
}
