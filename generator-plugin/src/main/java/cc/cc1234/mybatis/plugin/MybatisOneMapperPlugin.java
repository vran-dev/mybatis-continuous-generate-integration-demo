package cc.cc1234.mybatis.plugin;

import cc.cc1234.mybatis.plugin.generator.SelectOneByExampleXmlElementGenerator;
import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MybatisOneMapperPlugin extends PluginAdapter {

    private static final String BASE_PACKAGE_PROP = "base-mapper.target.package";

    private static final String BASE_PROJECT_PROP = "base-mapper.target.project";

    private static final String BASE_MAPPER_NAME_PROP = "base-mapper.name";

    private static final String JAVA_MAPPER_PACKAGE_PROP = "java-mapper.target.package";

    private static final String JAVA_MAPPER_PROJECT_PROP = "java-mapper.target.project";

    private static final String JAVA_MAPPER_GENERATE_PROP = "java-mapper.generate";

    private static final String JAVA_MAPPER_OVERRIDE_PROP = "java-mapper.override";

    private List<String> warnings;

    @Override
    public boolean validate(List<String> warnings) {
        this.warnings = warnings;
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        String isGenerateJavaMapper = super.properties.getProperty(JAVA_MAPPER_GENERATE_PROP, "true");
        if (!Objects.equals(isGenerateJavaMapper, "true")) {
            return;
        }

        // xml mapper namespace
        String javaMapperPackage = super.properties.getProperty(JAVA_MAPPER_PACKAGE_PROP);
        String originalNamespace = introspectedTable.getMyBatis3SqlMapNamespace();
        int idx = originalNamespace.lastIndexOf(".");
        String mapperInterfaceName = originalNamespace.substring(idx + 1);
        String newNamespace = javaMapperPackage + "." + mapperInterfaceName;
        introspectedTable.setMyBatis3JavaMapperType(newNamespace);
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles() {
        String targetPackage = super.properties.getProperty(BASE_PACKAGE_PROP);
        String mapperName = super.properties.getProperty(BASE_MAPPER_NAME_PROP, "BaseMapper");
        String interfaceName = targetPackage + "." + mapperName;
        Interface baseMapperInterface = new Interface(new FullyQualifiedJavaType(interfaceName));

        // BaseMapper<T, E>
        baseMapperInterface.addTypeParameter(new TypeParameter("T"));
        baseMapperInterface.addTypeParameter(new TypeParameter("E"));

        // long countByExample(E example);
        Method countByExample = new Method("countByExample");
        countByExample.setReturnType(new FullyQualifiedJavaType("T"));
        countByExample.addParameter(new Parameter(new FullyQualifiedJavaType("E"), "example"));
        countByExample.setReturnType(new FullyQualifiedJavaType("long"));
        countByExample.setAbstract(true);
        baseMapperInterface.addMethod(countByExample);

        // int deleteByExample(E example);
        Method deleteByExample = new Method("deleteByExample");
        deleteByExample.setReturnType(new FullyQualifiedJavaType("int"));
        deleteByExample.addParameter(new Parameter(new FullyQualifiedJavaType("E"), "example"));
        deleteByExample.setAbstract(true);
        baseMapperInterface.addMethod(deleteByExample);

        // int deleteByPrimaryKey(Long id);
        Method deleteByPrimaryKey = new Method("deleteByPrimaryKey");
        deleteByPrimaryKey.setReturnType(new FullyQualifiedJavaType("int"));
        deleteByPrimaryKey.addParameter(new Parameter(new FullyQualifiedJavaType("Long"), "id"));
        deleteByPrimaryKey.setAbstract(true);
        baseMapperInterface.addMethod(deleteByPrimaryKey);

        // int insert(T row);
        Method insert = new Method("insert");
        insert.setReturnType(new FullyQualifiedJavaType("int"));
        insert.addParameter(new Parameter(new FullyQualifiedJavaType("T"), "row"));
        insert.setAbstract(true);
        baseMapperInterface.addMethod(insert);

        // int insertSelective(T row);
        Method insertSelective = new Method("insertSelective");
        insertSelective.setReturnType(new FullyQualifiedJavaType("int"));
        insertSelective.addParameter(new Parameter(new FullyQualifiedJavaType("T"), "row"));
        insertSelective.setAbstract(true);
        baseMapperInterface.addMethod(insertSelective);

        // List<T> selectByExample(E example);
        Method selectByExample = new Method("selectByExample");
        selectByExample.setReturnType(new FullyQualifiedJavaType("List<T>"));
        selectByExample.addParameter(new Parameter(new FullyQualifiedJavaType("E"), "example"));
        selectByExample.setAbstract(true);
        baseMapperInterface.addMethod(selectByExample);

        baseMapperInterface.addMethod(selectOneByExample(baseMapperInterface));

        // T selectByPrimaryKey(Long id);
        Method selectByPrimaryKey = new Method("selectByPrimaryKey");
        selectByPrimaryKey.setReturnType(new FullyQualifiedJavaType("T"));
        selectByPrimaryKey.addParameter(
                new Parameter(new FullyQualifiedJavaType("Long"), "id"));
        selectByPrimaryKey.setAbstract(true);
        baseMapperInterface.addMethod(selectByPrimaryKey);

        // int updateByExampleSelective(@Param("row") T row, @Param("example") E example);
        baseMapperInterface.addMethod(updateByExampleSelective());

        // int updateByExample(@Param("row") T row, @Param("example") E example);
        baseMapperInterface.addMethod(updateByExample());

        // int updateByPrimaryKeySelective(T row);
        Method updateByPrimaryKeySelective = new Method("updateByPrimaryKeySelective");
        updateByPrimaryKeySelective.setReturnType(new FullyQualifiedJavaType("int"));
        updateByPrimaryKeySelective.addParameter(
                new Parameter(new FullyQualifiedJavaType("T"), "row"));
        updateByPrimaryKeySelective.setAbstract(true);
        baseMapperInterface.addMethod(updateByPrimaryKeySelective);

        // int updateByPrimaryKey(T row);
        Method updateByPrimaryKey = new Method("updateByPrimaryKey");
        updateByPrimaryKey.setReturnType(new FullyQualifiedJavaType("int"));
        updateByPrimaryKey.addParameter(new Parameter(new FullyQualifiedJavaType("T"), "row"));
        updateByPrimaryKey.setAbstract(true);
        baseMapperInterface.addMethod(updateByPrimaryKey);

        baseMapperInterface.addImportedType(
                new FullyQualifiedJavaType("org.apache.ibatis.annotations.Param"));
        baseMapperInterface.addImportedType(
                new FullyQualifiedJavaType("java.util.List"));
        baseMapperInterface.setVisibility(JavaVisibility.PUBLIC);

        String targetProject = super.properties.getProperty(BASE_PROJECT_PROP);
        GeneratedJavaFile baseMapperJavaFile = new GeneratedJavaFile(
                baseMapperInterface,
                targetProject,
                new DefaultJavaFormatter()
        );
        return Collections.singletonList(baseMapperJavaFile);
    }

    private Method selectOneByExample(Interface baseMapperInterface) {
        Method selectOneByExample = new Method("selectOneByExample");
        baseMapperInterface.addImportedType(new FullyQualifiedJavaType("java.util.Optional"));
        selectOneByExample.setReturnType(new FullyQualifiedJavaType("Optional<T>"));

        Parameter exampleParam = new Parameter(new FullyQualifiedJavaType("E"), "example");
        selectOneByExample.addParameter(exampleParam);

        selectOneByExample.setAbstract(true);
        return selectOneByExample;
    }

    private Method updateByExampleSelective() {
        Method updateByExampleSelective = new Method("updateByExampleSelective");
        updateByExampleSelective.setReturnType(new FullyQualifiedJavaType("int"));

        Parameter rowParam = new Parameter(new FullyQualifiedJavaType("T"), "row");
        rowParam.addAnnotation("@Param(\"row\")");
        updateByExampleSelective.addParameter(rowParam);

        Parameter exampleParam = new Parameter(new FullyQualifiedJavaType("E"), "example");
        exampleParam.addAnnotation("@Param(\"example\")");
        updateByExampleSelective.addParameter(exampleParam);

        updateByExampleSelective.setAbstract(true);
        return updateByExampleSelective;
    }

    private Method updateByExample() {
        Method updateByExample = new Method("updateByExample");
        updateByExample.setReturnType(new FullyQualifiedJavaType("int"));

        Parameter rowParam = new Parameter(new FullyQualifiedJavaType("T"), "row");
        updateByExample.addParameter(rowParam);
        rowParam.addAnnotation("@Param(\"row\")");

        Parameter exampleParam = new Parameter(new FullyQualifiedJavaType("E"), "example");
        exampleParam.addAnnotation("@Param(\"example\")");
        updateByExample.addParameter(exampleParam);

        updateByExample.setAbstract(true);
        return updateByExample;
    }

    @Override
    public boolean sqlMapDocumentGenerated(Document document, IntrospectedTable introspectedTable) {
        // selectOneByExample
        XmlElement mapperParent = document.getRootElement();
        SelectOneByExampleXmlElementGenerator generator = new SelectOneByExampleXmlElementGenerator();
        generator.setContext(context);
        generator.setWarnings(warnings);
        generator.setIntrospectedTable(introspectedTable);
        generator.addElements(mapperParent);
        return true;
    }

    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        String isGenerateJavaMapper = super.properties.getProperty(JAVA_MAPPER_GENERATE_PROP, "true");
        if (!Objects.equals(isGenerateJavaMapper, "true")) {
            return Collections.emptyList();
        }

        String javaMapperProject = super.properties.getProperty(JAVA_MAPPER_PROJECT_PROP);
        String javaMapperPackage = super.properties.getProperty(JAVA_MAPPER_PACKAGE_PROP);

        String xmlNamespace = introspectedTable.getMyBatis3SqlMapNamespace();
        int idx = xmlNamespace.lastIndexOf(".");
        String interfaceName = xmlNamespace.substring(idx + 1);

        boolean allowOverride =
                Boolean.parseBoolean(super.properties.getProperty(JAVA_MAPPER_OVERRIDE_PROP, "false"));
        if (javaMapperExists(javaMapperProject, javaMapperPackage, interfaceName)) {
            if (!allowOverride) {
                return Collections.emptyList();
            }
            warnings.add("java mapper interface "
                    + interfaceName
                    + " already exists in "
                    + javaMapperProject
                    + " and will be overridden.");
        }

        String fullInterfaceName = javaMapperPackage + "." + interfaceName;
        Interface baseMapperInterface = new Interface(new FullyQualifiedJavaType(fullInterfaceName));
        baseMapperInterface.setVisibility(JavaVisibility.PUBLIC);

        // import BaseMapper
        String baseMapperPackage = super.properties.getProperty(BASE_PACKAGE_PROP);
        String baseMapperName = super.properties.getProperty(BASE_MAPPER_NAME_PROP, "BaseMapper");
        baseMapperInterface.addImportedType(
                new FullyQualifiedJavaType(baseMapperPackage + "." + baseMapperName));

        // extends BaseMapper<Pojo, Example>
        String baseRecordType = introspectedTable.getBaseRecordType();
        String exampleType = introspectedTable.getExampleType();
        baseMapperInterface.addImportedType(new FullyQualifiedJavaType(baseRecordType));
        baseMapperInterface.addImportedType(new FullyQualifiedJavaType(exampleType));

        String simplePojoType = baseRecordType.substring(baseRecordType.lastIndexOf(".") + 1);
        String simpleExampleType = exampleType.substring(exampleType.lastIndexOf(".") + 1);
        baseMapperInterface.addSuperInterface(new FullyQualifiedJavaType(
                baseMapperName + "<" + simplePojoType + "," + simpleExampleType + ">"));

        GeneratedJavaFile baseMapperJavaFile = new GeneratedJavaFile(
                baseMapperInterface,
                javaMapperProject,
                new DefaultJavaFormatter()
        );
        return Collections.singletonList(baseMapperJavaFile);
    }

    private boolean javaMapperExists(String mapperProject, String mapperPackage, String mapperName) {
        String mapperPackagePath = mapperPackage.replace(".", File.separator);
        String fileName = mapperProject + File.separator
                + mapperPackagePath + File.separator
                + mapperName + ".java";
        return new File(fileName).exists();
    }
}

