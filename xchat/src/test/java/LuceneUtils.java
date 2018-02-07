import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.wltea.analyzer.lucene.IKAnalyzer;

/**
 * Created by :Guozhihua
 * Date： 2018/2/7.
 */
public class LuceneUtils {

    private static final Analyzer[] analyzers = new Analyzer[]{
            new WhitespaceAnalyzer(),

            new SimpleAnalyzer(),

            new StopAnalyzer(),

            new StandardAnalyzer(),

            new IKAnalyzer(),          //需要引入IKAnalyzer.jar
            new CJKAnalyzer(),
    };

}
