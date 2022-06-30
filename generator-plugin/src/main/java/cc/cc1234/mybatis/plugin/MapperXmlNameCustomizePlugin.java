package cc.cc1234.mybatis.plugin;

import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;

import java.util.List;

public class MapperXmlNameCustomizePlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> warnings) {
        return true;
    }

    @Override
    public void initialized(IntrospectedTable introspectedTable) {
        // XxxxMapper.xml --> XxxxGeneratedMapper.xml
        String xmlMapperFileName = introspectedTable.getMyBatis3XmlMapperFileName();
        String newXmlMapperFileName =
                xmlMapperFileName.replace("Mapper.xml", "GeneratedMapper.xml");
        introspectedTable.setMyBatis3XmlMapperFileName(newXmlMapperFileName);
    }
}