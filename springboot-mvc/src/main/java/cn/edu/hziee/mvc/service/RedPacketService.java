package cn.edu.hziee.mvc.service;

import cn.edu.hziee.mvc.entity.RedPacket;

public interface RedPacketService {

    /**
     * 获取红包
     *@param id 编号
     *@return 红包信息
     */
    RedPacket getRedPacket(Long id);

    /**
     * 扣减红包
     *@param id 编号
     *@return 影响条数
     */
    int decreaseRedPacket(Long id,Integer version);
}
