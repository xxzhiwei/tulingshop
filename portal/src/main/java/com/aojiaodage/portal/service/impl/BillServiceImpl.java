package com.aojiaodage.portal.service.impl;

import com.aojiaodage.portal.dao.BillDao;
import com.aojiaodage.portal.entity.Bill;
import com.aojiaodage.portal.service.BillService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class BillServiceImpl extends ServiceImpl<BillDao, Bill> implements BillService {
}
