package hxs.weixin.parent.plugins.interceptors;

import hxs.weixin.parent.plugins.ContextHelper;
import hxs.weixin.parent.plugins.Strategy;
import hxs.weixin.parent.plugins.StrategyManager;
import hxs.weixin.parent.plugins.TableSplit;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by :Guozhihua
 * Date： 2017/3/6.
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class TableSplitInterceptor implements Interceptor {

    private Log log = LogFactory.getLog(getClass());
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        if(statementHandler.getBoundSql()!=null){
            MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
            //切换表
            doSplitTable(metaStatementHandler,statementHandler);
        }
        // 传递给下一个拦截器处理
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    @Override
    public void setProperties(Properties properties) {

    }
    private void doSplitTable(MetaObject metaStatementHandler, StatementHandler statementHandler) throws Exception {
        String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");

        if (originalSql != null && !originalSql.equals("")) {
            MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
            String id = mappedStatement.getId();
            String className = id.substring(0, id.lastIndexOf("."));
            Class<?> classObj = Class.forName(className);
            // 根据配置自动生成分表SQL
            TableSplit tableSplit = classObj.getAnnotation(TableSplit.class);
            if (tableSplit != null && tableSplit.split()) {
                log.warn("分表前的SQL："+originalSql);
                StrategyManager strategyManager = ContextHelper.getStrategyManager();
                Strategy strategy=strategyManager.getStrategy(tableSplit.strategy());//获取分表策略来处理分表
                Object paramObjs = statementHandler.getBoundSql().getParameterObject();

                String convertedSql=originalSql.replaceFirst(tableSplit.value(), strategy.convert(tableSplit.value(),tableSplit.strategy(),tableSplit.columeCode(), BeanUtils.describe(paramObjs)));
                metaStatementHandler.setValue("delegate.boundSql.sql",convertedSql);
                log.warn("分表后的SQL："+convertedSql);
            }
        }
    }
}

