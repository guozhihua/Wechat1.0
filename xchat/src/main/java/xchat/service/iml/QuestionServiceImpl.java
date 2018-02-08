package xchat.service.iml;

import org.apache.log4j.Logger;
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
  private final static Logger log= Logger.getLogger(QuestionServiceImpl.class);
	

	@Autowired
    private QuestionDao questionDao;
	

	@Override
	protected BaseDao<Question> getBaseDao() {
		return questionDao;
	}

}
