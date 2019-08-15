package com.example.demo.action.Response;

import com.example.demo.common.api.Mapping;
import com.example.demo.common.api.Mappings;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class WebResponse implements Serializable {


    public static final String SUCCESS = "1";
    @ApiModelProperty(
            value = "返回码",
            required = true
    )
    private String error_code = "1";
    @ApiModelProperty(
            value = "错误消息",
            required = true
    )
    private String message = "success";
    public static final String DEFAULT_DATE_TIME_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT_PATTERN = "yyyy-MM-dd";

    public WebResponse() {
    }

    public <T> T mergeModel(Map<String, Object> model) {
        this.mergeModel(this, model);
        return(T) this;
    }

    protected void mergeModel(Object responseObj, Object modelObj) {
        try {
            Iterator var3 = this.getFieldsMapping(responseObj).iterator();

            Field field;
            while(var3.hasNext()) {
                field = (Field)var3.next();
                Mapping mapping = (Mapping)field.getAnnotation(Mapping.class);
                String expressionString = mapping.value();
                this.set(responseObj, field, this.parse(modelObj, expressionString, field.getType()));
            }

            var3 = this.getFieldsMappings(responseObj).iterator();

            while(var3.hasNext()) {
                field = (Field)var3.next();
                List<Object> newMappingValues = new ArrayList();
                Mappings mapping = (Mappings)field.getAnnotation(Mappings.class);
                List<?> list = (List)this.parse(modelObj, mapping.value(), field.getType());
                Iterator var8 = list.iterator();

                while(var8.hasNext()) {
                    Object obj = var8.next();
                    Object newObj = this.newInstance(mapping.original());
                    this.mergeModel(newObj, obj);
                    newMappingValues.add(newObj);
                }

                this.set(responseObj, field, newMappingValues);
            }

        } catch (Exception var11) {
            throw new RuntimeException(var11);
        }
    }
    protected Object newInstance(Class<?> clz) throws InstantiationException, IllegalAccessException {
        return clz.newInstance();
    }
    protected List<Field> getFieldsMappings(Object obj) {
        return FieldUtils.getFieldsListWithAnnotation(obj.getClass(), Mappings.class);
    }

    protected List<Field> getFieldsMapping(Object obj) {
        return FieldUtils.getFieldsListWithAnnotation(obj.getClass(), Mapping.class);
    }
    protected <T> T parse(Object data, String expressionString, Class<T> desiredResultType) {
        ExpressionParser parse = createParseExpression();
        EvaluationContext context = createEvaluationContext(data);
        try {
            return parse.parseExpression(expressionString).getValue(context, desiredResultType);
        } catch (Exception e) {
            throw new RuntimeException("expression:[" + expressionString + "]", e);
        }
    }
    protected EvaluationContext createEvaluationContext(Object root) {
        return new StandardEvaluationContext(root);
    }

    protected ExpressionParser createParseExpression() {
        return new SpelExpressionParser();
    }
    protected void set(Object original, Field field, Object value)
            throws IllegalArgumentException, IllegalAccessException {
        field.setAccessible(true);
        field.set(original, value);
    }
    public static String getSUCCESS() {
        return SUCCESS;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
