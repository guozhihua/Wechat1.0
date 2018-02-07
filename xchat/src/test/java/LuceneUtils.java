import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by :Guozhihua
 * Date： 2018/2/7.
 */
public class LuceneUtils {

    @Test
   public void getKeyWords(){
        String text="基于java语言开发的轻量级的中文分词工具包";
        //创建分词对象
        Analyzer anal=new IKAnalyzer(true);
        StringReader reader=new StringReader(text);
        //分词
        TokenStream ts= null;
        try {
            ts = anal.tokenStream("", reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        CharTermAttribute term=ts.getAttribute(CharTermAttribute.class);
        //遍历分词数据
        try {
            while(ts.incrementToken()){
                System.out.print(term.toString()+"|");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        reader.close();
   }

}
