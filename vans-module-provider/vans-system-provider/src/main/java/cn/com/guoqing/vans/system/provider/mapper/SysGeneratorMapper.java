package cn.com.guoqing.vans.system.provider.mapper;

import org.apache.ibatis.annotations.Mapper;

import cn.com.guoqing.vans.common.dao.CrudDao;
import cn.com.guoqing.vans.system.api.entity.SysTable;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口
 * Created by mijun on 2017/6/26.
 */
@Mapper
public interface SysGeneratorMapper extends CrudDao<SysTable> {

    Map<String, String> queryTable(String tableName);

    List<Map<String, String>> queryColumns(String tableName);
}
