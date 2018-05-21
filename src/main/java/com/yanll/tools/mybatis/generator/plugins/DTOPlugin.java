package com.yanll.tools.mybatis.generator.plugins;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.PluginAdapter;
import org.mybatis.generator.api.dom.DefaultJavaFormatter;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.List;

/**
 * Created by YANLL on 2016/11/4.
 */
public class DTOPlugin extends PluginAdapter {

    @Override
    public boolean validate(List<String> list) {
        System.out.println("[INFO] MapperPlugin开始生成DTO文件...");
        return true;
    }


    @Override
    public List<GeneratedJavaFile> contextGenerateAdditionalJavaFiles(IntrospectedTable introspectedTable) {
        List<GeneratedJavaFile> files = introspectedTable.getGeneratedJavaFiles();
        if (files == null) return null;
        for (GeneratedJavaFile file : files) {
            CompilationUnit compilationUnit = file.getCompilationUnit();
            String fileEncoding = file.getFileEncoding();
            String targetPackage = file.getTargetPackage();
            String targetProject = file.getTargetProject();
            if (compilationUnit.isJavaInterface()) continue;
            TopLevelClass original = (TopLevelClass) compilationUnit;
            String baseRecordType = introspectedTable.getBaseRecordType() + "DTO";
            TopLevelClass newModel = new TopLevelClass(baseRecordType);
            newModel.addJavaDocLine("/*");
            newModel.addJavaDocLine("* " + "当前文件为MybatisGenerator生成的DTO，请勿修改。");
            newModel.addJavaDocLine("*/");
            newModel.addImportedTypes(compilationUnit.getImportedTypes());
            newModel.addStaticImports(compilationUnit.getStaticImports());
            newModel.setAbstract(false);
            newModel.setStatic(false);
            newModel.setFinal(false);
            newModel.setVisibility(JavaVisibility.PUBLIC);
            //DTO默认都增加DTOEntity继承
            newModel.setSuperClass("DTOEntity");
            newModel.addImportedType("com.yanll.framework.facade.domain.DTOEntity");
            List<Field> fields = original.getFields();
            if (fields != null) {
                for (Field field : fields) {
                    newModel.addField(field);
                }
            }
            List<Method> methods = original.getMethods();
            if (methods != null) {
                for (Method method : methods) {
                    newModel.addMethod(method);
                }
            }
            GeneratedJavaFile f = new GeneratedJavaFile(newModel, targetProject, fileEncoding, new DefaultJavaFormatter());

            File targetFile;
            String source;
            try {
                DefaultShellCallback callback = new DefaultShellCallback(true);
                File directory = callback.getDirectory(f.getTargetProject(), f.getTargetPackage());
                targetFile = new File(directory, f.getFileName());
                source = f.getFormattedContent();
                PluginUtil.writeFile(targetFile, source, f.getFileEncoding());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


}
