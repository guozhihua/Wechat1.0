package xchat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import xchat.aop.NeedLogin;
import xchat.pojo.Pages;
import xchat.service.PagesService;
import xchat.sys.WebModel;

import java.util.List;

/**
 * Created by :Guozhihua
 * Date： 2018/1/25.
 */
@Controller
@RequestMapping("/pages/")
public class PageController extends  ABaseController{
    private final Logger logger = LoggerFactory.getLogger(PageController.class);

    @Autowired
    private PagesService pagesService;

    @RequestMapping("/getByParent")
    @ResponseBody
    @NeedLogin
    public WebModel getByPageId() {
        WebModel webModel = WebModel.getInstance();
        try {
            List<Pages> pagesByParentList = pagesService.getPagesByParentList(super.getJSONobj().getInteger("parentId"));
            webModel.setDatas(pagesByParentList);
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }

    @RequestMapping("/getGrandByParent")
    @ResponseBody
    @NeedLogin
    public WebModel getGrandByPageId() {
        WebModel webModel = WebModel.getInstance();
        try {
            List<Pages> pagesByParentList = pagesService.getGrandPagesByParentId(super.getJSONobj().getInteger("parentId"));
            webModel.setDatas(pagesByParentList);
        } catch (Exception ex) {
            logger.error("error", ex);
            webModel.isFail();
        }
        return webModel;
    }
}
