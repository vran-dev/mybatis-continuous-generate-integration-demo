package cc.cc1234.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

public class XmlMapperRenamePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        overrideXmlFileName(introspectedTable);
        overrideXmlNamespacePackage(introspectedTable);
    }

    protected void overrideXmlFileName(IntrospectedTable introspectedTable) {
        String xmlMapperFileName = introspectedTable.getMyBatis3XmlMapperFileName();
        String newXmlMapperFileName =
                xmlMapperFileName.replace("Mapper.xml", "GeneratedMapper.xml");
        introspectedTable.setMyBatis3XmlMapperFileName(newXmlMapperFileName);
    }

    protected void overrideXmlNamespacePackage(IntrospectedTable introspectedTable) {
        if (properties.containsKey("namespace.package")) {
            String targetPackage = properties.getProperty("namespace.package");
            String originalNamespace = introspectedTable.getMyBatis3SqlMapNamespace();
            int idx = originalNamespace.lastIndexOf(".");
            String mapperInterfaceName = originalNamespace.substring(idx + 1);
            String newNamespace = targetPackage + "." + mapperInterfaceName;
            introspectedTable.setMyBatis3JavaMapperType(newNamespace);
        }
    }

}
