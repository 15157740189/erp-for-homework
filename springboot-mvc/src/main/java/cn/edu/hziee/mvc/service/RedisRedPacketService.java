package cn.edu.hziee.mvc.service;

public interface RedisRedPacketService {
    /**
     *保存redis抢红包列表
     * @param redPacketId 一抢红包编号
     * @param unitAmount 一红包金额
     */
    public void saveUserRedPacketByRedis (Long redPacketId, Double unitAmount);

}
