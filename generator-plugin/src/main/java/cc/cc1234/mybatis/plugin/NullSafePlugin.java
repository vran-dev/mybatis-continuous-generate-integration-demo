package cc.cc1234.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.java.*;

import java.util.*;

/**
 * 该插件提供了两种方式来减少 NPE
 * <p>
 * 1. 添加 spring 的 @Nullable 注解
 * 2. 新增一个 getXxxOptional 方法
 *
 * <p>
 * set property <b>ignoreColumns</b> to configure which columns should be ignored, such as: *.created, user.id.
 * </p>
 */
public class NullSafePlugin extends PluginAdapter {

    private static final String IGNORE_COLUMN_PROP = "ignoreColumns";

    private static final String ADD_SPRING_NULLABLE_ANNOTATION = "addSpringNullableAnnotation";

    private static final String ADD_OPTIONAL_GETTER = "addOptionalGetter";

    private static final String ALL = "*";

    private Map<String, Set<String>> ignoredTableColumns = new HashMap<>();

    private boolean addSpringNullableAnnotation;

    private boolean addOptionalGetter;

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String ignoreColumns = (String) super.properties.getOrDefault(IGNORE_COLUMN_PROP, "");
        if (ignoreColumns.isEmpty()) {
            return;
        }

        String[] ignoreColumnsArray = ignoreColumns.split(",");
        for (String ignoreColumnPattern : ignoreColumnsArray) {
            int separatorIndex = ignoreColumnPattern.lastIndexOf(".");
            if (separatorIndex == -1) {
                continue;
            }

            String tableName = ignoreColumnPattern.substring(0, separatorIndex).trim();
            String columnName = ignoreColumnPattern.substring(separatorIndex + 1).trim();
            Set<String> values = ignoredTableColumns.computeIfAbsent(tableName, k -> new HashSet<>());
            values.add(columnName);
        }

        addSpringNullableAnnotation = Boolean.parseBoolean(
                properties.getOrDefault(ADD_SPRING_NULLABLE_ANNOTATION, "false").toString());
        addOptionalGetter = Boolean.parseBoolean(
                properties.getOrDefault(ADD_OPTIONAL_GETTER, "true").toString());
    }

    @Override
    public boolean modelFieldGenerated(Field field,
                                       TopLevelClass topLevelClass,
                                       IntrospectedColumn introspectedColumn,
                                       IntrospectedTable introspectedTable,
                                       ModelClassType modelClassType) {
        if (ignore(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName(),
                introspectedColumn.getActualColumnName())) {
            return true;
        }

        addSpringNullableAnnotation(field, topLevelClass, introspectedColumn);
        return true;
    }

    @Override
    public boolean modelGetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        if (ignore(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName(),
                introspectedColumn.getActualColumnName())) {
            return true;
        }

        addOptionalGetter(method, topLevelClass, introspectedColumn);
        addSpringNullableAnnotation(method, topLevelClass, introspectedColumn);
        return true;
    }

    @Override
    public boolean modelSetterMethodGenerated(Method method,
                                              TopLevelClass topLevelClass,
                                              IntrospectedColumn introspectedColumn,
                                              IntrospectedTable introspectedTable,
                                              ModelClassType modelClassType) {
        if (ignore(introspectedTable.getFullyQualifiedTable().getIntrospectedTableName(),
                introspectedColumn.getActualColumnName())) {
            return true;
        }

        Parameter parameter = method.getParameters().iterator().next();
        addSpringNullableAnnotation(parameter, topLevelClass, introspectedColumn);
        return true;
    }

    protected boolean ignore(String tableName, String columnName) {
        if (ignoredTableColumns.containsKey(tableName)) {
            Set<String> ignoredColumns = ignoredTableColumns.get(tableName);
            if (ignoredColumns.contains(columnName)) {
                return true;
            }
        }

        if (ignoredTableColumns.containsKey(ALL)) {
            Set<String> ignoredColumns = ignoredTableColumns.get(ALL);
            if (ignoredColumns.contains(columnName)) {
                return true;
            }
        }
        return false;
    }

    private void addOptionalGetter(Method method,
                                   TopLevelClass topLevelClass,
                                   IntrospectedColumn introspectedColumn) {
        if (addOptionalGetter && introspectedColumn.isNullable()) {
            method.getReturnType().ifPresent(returnType -> {
                topLevelClass.addImportedType(new FullyQualifiedJavaType("java.util.Optional"));
                Method optionGetter = new Method(method.getName() + "Optional");
                optionGetter.setVisibility(method.getVisibility());
                String shortName = returnType.getShortName();
                optionGetter.setReturnType(
                        new FullyQualifiedJavaType("java.util.Optional<" + shortName + ">"));
                optionGetter.addBodyLine("return Optional.ofNullable(" + introspectedColumn.getJavaProperty() + ");");
                topLevelClass.addMethod(optionGetter);
            });
        }
    }

    private void addSpringNullableAnnotation(Method method,
                                             TopLevelClass topLevelClass,
                                             IntrospectedColumn introspectedColumn) {
        if (addSpringNullableAnnotation && introspectedColumn.isNullable()) {
            topLevelClass.addImportedType("org.springframework.lang.Nullable");
            method.addAnnotation("@Nullable");
        }
    }

    private void addSpringNullableAnnotation(Field field,
                                             TopLevelClass topLevelClass,
                                             IntrospectedColumn introspectedColumn) {
        if (addSpringNullableAnnotation && introspectedColumn.isNullable()) {
            topLevelClass.addImportedType("org.springframework.lang.Nullable");
            field.addAnnotation("@Nullable");
        }
    }

    private void addSpringNullableAnnotation(Parameter parameter,
                                             TopLevelClass topLevelClass,
                                             IntrospectedColumn introspectedColumn) {
        if (addSpringNullableAnnotation && introspectedColumn.isNullable()) {
            topLevelClass.addImportedType("org.springframework.lang.Nullable");
            parameter.addAnnotation("@Nullable");
        }
    }
}