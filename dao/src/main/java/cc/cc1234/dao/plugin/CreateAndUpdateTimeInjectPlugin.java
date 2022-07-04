package cc.cc1234.dao.plugin;

import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})
})
public class CreateAndUpdateTimeInjectPlugin implements Interceptor {

    private ThreadLocal<MappedStatement> mappedStatementContext = new ThreadLocal<>();

    private ThreadLocal<Object> parameterContext = new ThreadLocal<>();

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();
        if (target instanceof Executor) {
            return handleExecutorUpdate(invocation);
        } else if (target instanceof StatementHandler) {
            return handleStatementHandlerPrepare(invocation);
        } else {
            return invocation.proceed();
        }
    }

    private Object handleExecutorUpdate(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();
        Executor executor = (Executor) invocation.getTarget();
        MappedStatement mappedStatement = (MappedStatement) args[0];
        this.mappedStatementContext.set(mappedStatement);
        this.parameterContext.set(args[1]);
        return invocation.proceed();
    }

    private Object handleStatementHandlerPrepare(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = mappedStatementContext.get();
        Object parameter = parameterContext.get();
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        try {
            if (mappedStatement.getSqlCommandType() == SqlCommandType.UPDATE) {

                Update update = (Update) CCJSqlParserUtil.parse(boundSql.getSql());
                Table table = update.getTable();
                List<UpdateSet> updateSets = update.getUpdateSets();
                boolean hasUpdateAt = updateSets.stream()
                        .anyMatch(set -> set.getColumns()
                                .stream()
                                .anyMatch(col -> Objects.equals("update_at", col.getColumnName())));
                if (!hasUpdateAt) {
                    Function functionValue = new Function();
                    functionValue.setName("now");
                    update.getUpdateSets()
                            .add(new UpdateSet(new Column("update_at"), functionValue));
                    BoundSql rewriteBoundSql = new BoundSql(
                            mappedStatement.getConfiguration(),
                            update.toString(),
                            mappedStatement.getParameterMap().getParameterMappings(),
                            mappedStatement.getParameterMap());
                    StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
                    MetaObject metaStatementHandler = MetaObject.forObject(
                            statementHandler,
                            mappedStatement.getConfiguration().getObjectFactory(),
                            mappedStatement.getConfiguration().getObjectWrapperFactory(),
                            mappedStatement.getConfiguration().getReflectorFactory()
                    );
                    metaStatementHandler.setValue("delegate.boundSql", rewriteBoundSql);
                    return invocation.proceed();
                }
                return invocation.proceed();
            } else {
                return invocation.proceed();
            }
        } finally {
            this.mappedStatementContext.remove();
            this.parameterContext.remove();
        }

    }

}
