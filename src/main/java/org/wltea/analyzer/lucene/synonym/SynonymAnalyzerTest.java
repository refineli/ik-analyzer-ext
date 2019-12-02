package org.wltea.analyzer.lucene.synonym;

import org.apache.lucene.analysis.Analyzer;
import org.wltea.analyzer.utils.AnalyzerUtils;

import java.io.IOException;

public class SynonymAnalyzerTest {
	public static void main(String[] args) throws IOException {
		String text = "The quick brown fox jumps over the lazy dog";
		text="quick";
	    Analyzer analyzer = new SynonymAnalyzer(new BaseSynonymEngine());
		AnalyzerUtils.displayTokens(analyzer, text);
	}
}
