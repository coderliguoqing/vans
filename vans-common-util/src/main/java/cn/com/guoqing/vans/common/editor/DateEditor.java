package cn.com.guoqing.vans.common.editor;


import java.beans.PropertyEditorSupport;

import cn.com.guoqing.vans.common.util.DateHelper;

/**
 * @author Guoqing
 */
public class DateEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {
        setValue(DateHelper.parseDate(text));
    }

}
