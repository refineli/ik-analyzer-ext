package org.wltea.analyzer.lucene.pinyin.filter;

import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.wltea.analyzer.lucene.pinyin.Constant;
import org.wltea.analyzer.lucene.pinyin.handler.PinyinTermHandler;
import org.wltea.analyzer.lucene.pinyin.handler.TermHandler;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

/**
 * 拼音过滤器[负责将汉字转换为拼音]
 * @author Lanxiaowei
 *
 */
public class BPinyinTokenFilter extends TokenFilter {
	private final CharTermAttribute termAtt;
	/**汉语拼音输出转换器[基于Pinyin4j]*/
	private HanyuPinyinOutputFormat outputFormat;
	/**对于多音字会有多个拼音,firstChar即表示只取第一个,否则会取多个拼音*/
	private boolean firstChar;
	/**Term最小长度[小于这个最小长度的不进行拼音转换]*/
	private int minTermLength;
	private char[] curTermBuffer;
	private int curTermLength;
	private boolean outChinese;
    private Stack<String> termStack;
    private State current;
    private final PositionIncrementAttribute posIncrAtt;
    private TermHandler termHandler;



    public BPinyinTokenFilter(TokenStream input) {
		this(input, Constant.DEFAULT_FIRST_CHAR, Constant.DEFAULT_MIN_TERM_LRNGTH);
		termStack= new Stack<String>();

    }

	public BPinyinTokenFilter(TokenStream input, boolean firstChar) {
		this(input, firstChar, Constant.DEFAULT_MIN_TERM_LRNGTH);
        termStack= new Stack<String>();

    }

	public BPinyinTokenFilter(TokenStream input, boolean firstChar,
                              int minTermLenght) {
		this(input, firstChar, minTermLenght, Constant.DEFAULT_NGRAM_CHINESE);
        termStack= new Stack<String>();

    }

	public BPinyinTokenFilter(TokenStream input, boolean firstChar,
                              int minTermLenght, boolean outChinese) {
		super(input);

		this.termAtt = ((CharTermAttribute) addAttribute(CharTermAttribute.class));
        this.posIncrAtt = addAttribute(PositionIncrementAttribute.class);

        this.outputFormat = new HanyuPinyinOutputFormat();
		this.firstChar = false;
		this.minTermLength = Constant.DEFAULT_MIN_TERM_LRNGTH;

		this.outChinese = Constant.DEFAULT_OUT_CHINESE;

		this.firstChar = firstChar;
		this.minTermLength = minTermLenght;
		if (this.minTermLength < 1) {
			this.minTermLength = 1;
		}
		this.outputFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		this.outputFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        termStack= new Stack<String>();
        termHandler = new PinyinTermHandler();
    }

	public static boolean containsChinese(String s) {
		if ((s == null) || ("".equals(s.trim())))
			return false;
		for (int i = 0; i < s.length(); i++) {
			if (isChinese(s.charAt(i)))
				return true;
		}
		return false;
	}

	public static boolean isChinese(char a) {
		int v = a;
		return (v >= 19968) && (v <= 171941);
	}

    @Override
    public boolean incrementToken() throws IOException {
        if (termStack.size() > 0) { // #2
            String syn = termStack.pop(); // #2
            restoreState(current); // #2
            // 这里Lucene4.x的写法
            // termAtt.setTermBuffer(syn);

            // 这是Lucene5.x的写法
            termAtt.copyBuffer(syn.toCharArray(), 0, syn.length());
            posIncrAtt.setPositionIncrement(0); // #3
            return true;
        }

        if (!input.incrementToken()) // #4
            return false;

        if (addAliasesToStack()) { // #5
            current = captureState(); // #6
        }

        return true; // #7
    }

	public void reset() throws IOException {
		super.reset();
	}

    private boolean addAliasesToStack() throws IOException {
        List<String> terms = termHandler.getTerms(termAtt.toString()); // #8
        if (terms==null) {
            return false;
        }
        for (String term : terms) {
            termStack.push(term);
        }
        return true;
    }


}
