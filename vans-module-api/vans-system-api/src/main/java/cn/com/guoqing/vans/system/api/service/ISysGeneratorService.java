package cn.com.guoqing.vans.system.api.service;


import cn.com.guoqing.vans.common.api.Paging;
import cn.com.guoqing.vans.system.api.entity.SysTable;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description  代码生成器
 *
 * @author Guoqing
 * @Date 2018年1月15日
 */
public interface ISysGeneratorService {

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);

    PageInfo<SysTable> findTablePage(Paging page, SysTable table);

    /**
     * 生成代码
     */
    byte[] generatorCode(String[] tableNames,String allPackage,String author,String email);

}
