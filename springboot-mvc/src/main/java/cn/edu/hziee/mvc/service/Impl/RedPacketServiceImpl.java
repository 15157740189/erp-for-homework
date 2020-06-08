package cn.edu.hziee.mvc.service.Impl;

import cn.edu.hziee.mvc.entity.RedPacket;
import cn.edu.hziee.mvc.mapper.RedPacketDao;
import cn.edu.hziee.mvc.service.RedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RedPacketServiceImpl implements RedPacketService {
    @Autowired
    private RedPacketDao redPacketDao;
    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public RedPacket getRedPacket(Long id) {
      return redPacketDao.getRedPacket(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.READ_COMMITTED)
    public int decreaseRedPacket(Long id,Integer version) {
        return redPacketDao.decreaseRedPacketForVersion(id,version);
    }



}
