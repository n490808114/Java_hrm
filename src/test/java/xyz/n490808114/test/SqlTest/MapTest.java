package xyz.n490808114.test.SqlTest;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import xyz.n490808114.train.domain.Notice;

@Repository
public interface MapTest{

    @Select("Select id,title,content from notice_inf order by id")
    @ResultType(HashMap.class)
    @MapKey("id")
    Map<String,Notice> selectNoticeMap();
}