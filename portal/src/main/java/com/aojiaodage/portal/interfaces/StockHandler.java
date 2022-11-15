package com.aojiaodage.portal.interfaces;

import com.aojiaodage.portal.entity.OmsOrderItem;
import com.aojiaodage.portal.entity.ProductSku;

public interface StockHandler {
    void handle(ProductSku sku, OmsOrderItem item);
}
