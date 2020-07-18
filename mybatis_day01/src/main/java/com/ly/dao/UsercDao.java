package com.ly.dao;

import com.ly.pojo.Userc;

import java.util.List;
import java.util.Map;

public interface UsercDao {
    /**
     * 查询所有
     * @return
     */
    List<Userc> getUserList();


    /**
     * 根据id查询
     * @param id
     * @return
     */
    Userc getUsercById(Integer id);

    /**
     * 保存数据
     * @param userc
     */
    void insertUserc(Userc userc);

    /**
     * 更新数据
     * @param userc
     */
    void updateUserc(Userc userc);

    /**
     * 删除数据
     * @param id
     */
    void deleteUserc(Integer id);

    /**
     * 使用Map作为参数
     * @param map
     * @return
     */
    void insertUserc2(Map<String,Object> map);


    /**
     * 根据name模糊查询
     * @param name
     * @return
     */
    List<Userc> getUsercBy(String name);
}
