package tk.techforge.tools.mybatis.generator.plugins;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.internal.DefaultCommentGenerator;

import java.util.Properties;


public class TableCommentGenerator extends DefaultCommentGenerator {
    private Properties properties;
    private Properties systemPro;
    private boolean suppressDate;
    private boolean suppressAllComments;

    public TableCommentGenerator() {
        super();
        properties = new Properties();
        systemPro = System.getProperties();
        suppressDate = false;
        suppressAllComments = false;
    }


    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        field.addJavaDocLine("/**");
        sb.append(" * ");
        sb.append(introspectedColumn.getRemarks());
        field.addJavaDocLine(sb.toString().replace("\n", " "));
        field.addJavaDocLine(" */");
    }
}
