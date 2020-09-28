package cn.edu.hziee.mvc.service;

public interface UserRedPacketService {
    /**
     * 保存抢红包信息
     * @param redPacketId 红包编号
     * @param userId 抢红包用户编号
     * @return 影响记录数
     * 0 没有库存 失败
     * 1 成功 且不是最后一个红包
     * 2 成功 并且为最后一个红包
     */
     int grapRedPacket(Long redPacketId, Long userId);
     Long grapRedPacketByRedis(Long redPacketId, Long userId);
}
