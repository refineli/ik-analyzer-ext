package org.wltea.analyzer.lucene.pinyin.test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.wltea.analyzer.lucene.pinyin.PinyinAnalyzer;
import org.wltea.analyzer.utils.AnalyzerUtils;

import java.io.IOException;

/**
 * 拼音分词器测试
 * @author Lanxiaowei
 *
 */
public class PinyinAnalyzerTest {
	public static void main(String[] args) throws IOException {
		String text = "中国天安门mi检索12名00";
		Analyzer analyzer = new PinyinAnalyzer(20);
//        Analyzer analyzer = new StandardAnalyzer();
		AnalyzerUtils.displayTokens(analyzer, text);
	}
}
