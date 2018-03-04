package xchat.service.iml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xchat.dao.BaseDao;
import xchat.dao.QuestionDao;
import xchat.pojo.Question;
import xchat.service.BaseServiceImpl;
import xchat.service.QuestionService;

/**
 * 
 * <br>
 * <b>功能：</b>QuestionService<br>
 */
@Service
@Transactional
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {
  private final static Logger log= LoggerFactory.getLogger(QuestionServiceImpl.class);
	

	@Autowired
    private QuestionDao questionDao;
	

	@Override
	protected BaseDao<Question> getBaseDao() {
		return questionDao;
	}

}
