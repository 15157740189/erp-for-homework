package cn.edu.hziee.mvc.mapper;

import cn.edu.hziee.mvc.entity.UserRedPacket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRedPacketDao {
    /**
     * 插入抢红包信息
     * @param userRedPacket 抢红包信息
     * @return 影响的记录数
     */
    public int grapRedPacket(UserRedPacket userRedPacket);
}
