package xchat.service;

import java.io.UnsupportedEncodingException;

/**
 * Created by 618 on 2018/1/12.
 * @author lingfengsan
 */
public interface PatternService {
    String run(String q,String[] a) throws UnsupportedEncodingException;
}
