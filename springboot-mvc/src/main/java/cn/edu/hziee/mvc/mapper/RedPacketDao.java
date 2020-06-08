package cn.edu.hziee.mvc.mapper;

import cn.edu.hziee.mvc.entity.RedPacket;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RedPacketDao {
    RedPacket getRedPacket(Long id);

    int decreaseRedPacketForVersion(Long id, Integer version);
}
