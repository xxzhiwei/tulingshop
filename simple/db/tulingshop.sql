create table oms_bill
(
    id               int auto_increment
        primary key,
    order_sn         varchar(64)    null comment '订单编号',
    trade_no         varchar(64)    null comment '流水号',
    total_amount     decimal(10, 2) null comment '支付金额',
    subject          varchar(200)   null comment '交易内容',
    payment_status   varchar(64)    null comment '交易状态',
    create_time      datetime       null,
    callback_time    datetime       null,
    callback_content varchar(200)   null,
    confirm_time     datetime       null
);

INSERT INTO tulingshop.oms_bill (id, order_sn, trade_no, total_amount, subject, payment_status, create_time, callback_time, callback_content, confirm_time) VALUES (1, '202211151668500518506', '2022111522001485370502364854', 8888.00, 'tulingshop订单', 'TRADE_SUCCESS', '2022-11-15 16:22:28', '2022-11-15 16:24:37', null, null);
INSERT INTO tulingshop.oms_bill (id, order_sn, trade_no, total_amount, subject, payment_status, create_time, callback_time, callback_content, confirm_time) VALUES (2, '202211161668572802746', '2022111622001485370502365026', 8888.00, 'tulingshop订单', 'TRADE_SUCCESS', '2022-11-16 12:29:52', '2022-11-16 12:32:04', null, null);
create table oms_order
(
    id                      int auto_increment comment '订单id'
        primary key,
    member_id               int              not null,
    coupon_id               int              null,
    order_sn                varchar(64)      null comment '订单编号',
    create_time             datetime         null comment '提交时间',
    member_username         varchar(64)      null comment '用户帐号',
    total_amount            decimal(10, 2)   null comment '订单总金额',
    pay_amount              decimal(10, 2)   null comment '应付金额（实际支付金额）',
    freight_amount          decimal(10, 2)   null comment '运费金额',
    promotion_amount        decimal(10, 2)   null comment '促销优化金额（促销价、满减、阶梯价）',
    integration_amount      decimal(10, 2)   null comment '积分抵扣金额',
    coupon_amount           decimal(10, 2)   null comment '优惠券抵扣金额',
    discount_amount         decimal(10, 2)   null comment '管理员后台调整订单使用的折扣金额',
    pay_type                int(1)           null comment '支付方式：0->未支付；1->支付宝；2->微信',
    source_type             int(1)           null comment '订单来源：0->PC订单；1->app订单',
    status                  int(1)           null comment '订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单',
    order_type              int(1)           null comment '订单类型：0->正常订单；1->秒杀订单',
    delivery_company        varchar(64)      null comment '物流公司(配送方式)',
    delivery_sn             varchar(64)      null comment '物流单号',
    auto_confirm_day        int              null comment '自动确认时间（天）',
    integration             int              null comment '可以获得的积分',
    growth                  int              null comment '可以活动的成长值',
    promotion_info          varchar(100)     null comment '活动信息',
    bill_type               int(1)           null comment '发票类型：0->不开发票；1->电子发票；2->纸质发票',
    bill_header             varchar(200)     null comment '发票抬头',
    bill_content            varchar(200)     null comment '发票内容',
    bill_receiver_phone     varchar(32)      null comment '收票人电话',
    bill_receiver_email     varchar(64)      null comment '收票人邮箱',
    receiver_name           varchar(100)     not null comment '收货人姓名',
    receiver_phone          varchar(32)      not null comment '收货人电话',
    receiver_post_code      varchar(32)      null comment '收货人邮编',
    receiver_province       varchar(32)      null comment '省份/直辖市',
    receiver_city           varchar(32)      null comment '城市',
    receiver_region         varchar(32)      null comment '区',
    receiver_detail_address varchar(200)     null comment '详细地址',
    note                    varchar(500)     null comment '订单备注',
    confirm_status          int(1)           null comment '确认收货状态：0->未确认；1->已确认',
    delete_status           int(1) default 0 not null comment '删除状态：0->未删除；1->已删除',
    use_integration         int              null comment '下单时使用的积分',
    payment_time            datetime         null comment '支付时间',
    delivery_time           datetime         null comment '发货时间',
    receive_time            datetime         null comment '确认收货时间',
    comment_time            datetime         null comment '评价时间',
    modify_time             datetime         null comment '修改时间'
)
    comment '订单表';

INSERT INTO tulingshop.oms_order (id, member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, pay_type, source_type, status, order_type, delivery_company, delivery_sn, auto_confirm_day, integration, growth, promotion_info, bill_type, bill_header, bill_content, bill_receiver_phone, bill_receiver_email, receiver_name, receiver_phone, receiver_post_code, receiver_province, receiver_city, receiver_region, receiver_detail_address, note, confirm_status, delete_status, use_integration, payment_time, delivery_time, receive_time, comment_time, modify_time) VALUES (32, 4, null, '202211141668417554251', '2022-11-14 17:19:14', 'test1', 9999.00, 0.00, 10.00, null, null, null, null, 0, 0, 4, 0, null, null, null, null, null, null, null, null, null, null, null, '暗黑元首', '18888888888', '130302', '河北省', '秦皇岛市', '海港区', '光明区梦想路36号', null, null, 0, null, null, null, null, null, '2022-11-15 18:00:01');
INSERT INTO tulingshop.oms_order (id, member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, pay_type, source_type, status, order_type, delivery_company, delivery_sn, auto_confirm_day, integration, growth, promotion_info, bill_type, bill_header, bill_content, bill_receiver_phone, bill_receiver_email, receiver_name, receiver_phone, receiver_post_code, receiver_province, receiver_city, receiver_region, receiver_detail_address, note, confirm_status, delete_status, use_integration, payment_time, delivery_time, receive_time, comment_time, modify_time) VALUES (33, 4, null, '202211151668479332663', '2022-11-15 10:28:53', 'test1', 9999.00, 0.00, 10.00, null, null, null, null, 0, 0, 4, 0, null, null, null, null, null, null, null, null, null, null, null, '暗黑元首', '18888888888', '130302', '河北省', '秦皇岛市', '海港区', '光明区梦想路36号', null, null, 0, null, null, null, null, null, '2022-11-15 17:30:00');
INSERT INTO tulingshop.oms_order (id, member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, pay_type, source_type, status, order_type, delivery_company, delivery_sn, auto_confirm_day, integration, growth, promotion_info, bill_type, bill_header, bill_content, bill_receiver_phone, bill_receiver_email, receiver_name, receiver_phone, receiver_post_code, receiver_province, receiver_city, receiver_region, receiver_detail_address, note, confirm_status, delete_status, use_integration, payment_time, delivery_time, receive_time, comment_time, modify_time) VALUES (34, 4, null, '202211151668482180609', '2022-11-15 11:16:21', 'test1', 9999.00, 0.00, 10.00, null, null, null, null, 0, 0, 4, 0, null, null, null, null, null, null, null, null, null, null, null, '暗黑元首', '18888888888', '130302', '河北省', '秦皇岛市', '海港区', '光明区梦想路36号', null, null, 0, null, null, null, null, null, '2022-11-15 17:30:00');
INSERT INTO tulingshop.oms_order (id, member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, pay_type, source_type, status, order_type, delivery_company, delivery_sn, auto_confirm_day, integration, growth, promotion_info, bill_type, bill_header, bill_content, bill_receiver_phone, bill_receiver_email, receiver_name, receiver_phone, receiver_post_code, receiver_province, receiver_city, receiver_region, receiver_detail_address, note, confirm_status, delete_status, use_integration, payment_time, delivery_time, receive_time, comment_time, modify_time) VALUES (35, 4, null, '202211151668493451819', '2022-11-15 14:24:12', 'test1', 9999.00, 0.00, 10.00, null, null, null, null, 0, 0, 4, 0, null, null, null, null, null, null, null, null, null, null, null, '暗黑元首', '18888888888', '130302', '河北省', '秦皇岛市', '海港区', '光明区梦想路36号', null, null, 0, null, null, null, null, null, '2022-11-15 17:30:00');
INSERT INTO tulingshop.oms_order (id, member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, pay_type, source_type, status, order_type, delivery_company, delivery_sn, auto_confirm_day, integration, growth, promotion_info, bill_type, bill_header, bill_content, bill_receiver_phone, bill_receiver_email, receiver_name, receiver_phone, receiver_post_code, receiver_province, receiver_city, receiver_region, receiver_detail_address, note, confirm_status, delete_status, use_integration, payment_time, delivery_time, receive_time, comment_time, modify_time) VALUES (36, 4, null, '202211151668500338473', '2022-11-15 16:18:58', 'test1', 9999.00, 0.00, 10.00, null, null, null, null, 0, 0, 4, 0, null, null, null, null, null, null, null, null, null, null, null, '暗黑元首', '18888888888', '130302', '河北省', '秦皇岛市', '海港区', '光明区梦想路36号', null, null, 0, null, null, null, null, null, '2022-11-15 17:30:00');
INSERT INTO tulingshop.oms_order (id, member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, pay_type, source_type, status, order_type, delivery_company, delivery_sn, auto_confirm_day, integration, growth, promotion_info, bill_type, bill_header, bill_content, bill_receiver_phone, bill_receiver_email, receiver_name, receiver_phone, receiver_post_code, receiver_province, receiver_city, receiver_region, receiver_detail_address, note, confirm_status, delete_status, use_integration, payment_time, delivery_time, receive_time, comment_time, modify_time) VALUES (37, 4, null, '202211151668500518506', '2022-11-15 16:21:59', 'test1', 8888.00, 8888.00, 10.00, null, null, null, null, 1, 0, 1, 0, null, null, null, null, null, null, null, null, null, null, null, '暗黑元首', '18888888888', '130302', '河北省', '秦皇岛市', '海港区', '光明区梦想路36号', null, null, 0, null, '2022-11-15 16:22:28', null, null, null, '2022-11-15 16:21:59');
INSERT INTO tulingshop.oms_order (id, member_id, coupon_id, order_sn, create_time, member_username, total_amount, pay_amount, freight_amount, promotion_amount, integration_amount, coupon_amount, discount_amount, pay_type, source_type, status, order_type, delivery_company, delivery_sn, auto_confirm_day, integration, growth, promotion_info, bill_type, bill_header, bill_content, bill_receiver_phone, bill_receiver_email, receiver_name, receiver_phone, receiver_post_code, receiver_province, receiver_city, receiver_region, receiver_detail_address, note, confirm_status, delete_status, use_integration, payment_time, delivery_time, receive_time, comment_time, modify_time) VALUES (38, 4, null, '202211161668572802746', '2022-11-16 12:26:43', 'test1', 8888.00, 8888.00, 10.00, null, null, null, null, 0, 0, 4, 0, null, null, null, null, null, null, null, null, null, null, null, '暗黑元首', '18888888888', '130302', '河北省', '秦皇岛市', '海港区', '光明区梦想路36号', null, null, 0, null, null, null, null, null, '2022-11-16 13:30:00');
create table oms_order_item
(
    id                  int auto_increment
        primary key,
    order_id            int            null comment '订单id',
    order_sn            varchar(64)    null comment '订单编号',
    product_id          int            null,
    product_pic         varchar(500)   null,
    product_name        varchar(200)   null,
    product_brand       varchar(200)   null,
    product_sn          varchar(64)    null,
    product_price       decimal(10, 2) null comment '销售价格',
    product_quantity    int            null comment '购买数量',
    product_sku_id      int            null comment '商品sku编号',
    product_sku_code    varchar(50)    null comment '商品sku条码',
    product_category_id int            null comment '商品分类id',
    sp1                 varchar(100)   null comment '商品的销售属性',
    sp2                 varchar(100)   null,
    sp3                 varchar(100)   null,
    promotion_name      varchar(200)   null comment '商品促销名称',
    promotion_amount    decimal(10, 2) null comment '商品促销分解金额',
    coupon_amount       decimal(10, 2) null comment '优惠券优惠分解金额',
    integration_amount  decimal(10, 2) null comment '积分优惠分解金额',
    real_amount         decimal(10, 2) null comment '该商品经过优惠后的分解金额',
    gift_integration    int default 0  null,
    gift_growth         int default 0  null,
    product_attr        varchar(500)   null comment '商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]'
)
    comment '订单中所包含的商品';

INSERT INTO tulingshop.oms_order_item (id, order_id, order_sn, product_id, product_pic, product_name, product_brand, product_sn, product_price, product_quantity, product_sku_id, product_sku_code, product_category_id, sp1, sp2, sp3, promotion_name, promotion_amount, coupon_amount, integration_amount, real_amount, gift_integration, gift_growth, product_attr) VALUES (51, 32, '202211141668417554251', 37, null, 'iphone 20 4G 128G 土豪金', null, null, 9999.00, 1, 5, null, null, null, null, null, null, null, null, null, null, 0, 0, null);
INSERT INTO tulingshop.oms_order_item (id, order_id, order_sn, product_id, product_pic, product_name, product_brand, product_sn, product_price, product_quantity, product_sku_id, product_sku_code, product_category_id, sp1, sp2, sp3, promotion_name, promotion_amount, coupon_amount, integration_amount, real_amount, gift_integration, gift_growth, product_attr) VALUES (52, 33, '202211151668479332663', 37, null, 'iphone 20 4G 128G 土豪金', null, null, 9999.00, 1, 5, null, null, null, null, null, null, null, null, null, null, 0, 0, null);
INSERT INTO tulingshop.oms_order_item (id, order_id, order_sn, product_id, product_pic, product_name, product_brand, product_sn, product_price, product_quantity, product_sku_id, product_sku_code, product_category_id, sp1, sp2, sp3, promotion_name, promotion_amount, coupon_amount, integration_amount, real_amount, gift_integration, gift_growth, product_attr) VALUES (53, 34, '202211151668482180609', 37, null, 'iphone 20 4G 128G 土豪金', null, null, 9999.00, 1, 5, null, null, null, null, null, null, null, null, null, null, 0, 0, null);
INSERT INTO tulingshop.oms_order_item (id, order_id, order_sn, product_id, product_pic, product_name, product_brand, product_sn, product_price, product_quantity, product_sku_id, product_sku_code, product_category_id, sp1, sp2, sp3, promotion_name, promotion_amount, coupon_amount, integration_amount, real_amount, gift_integration, gift_growth, product_attr) VALUES (54, 35, '202211151668493451819', 37, null, 'iphone 20 4G 128G 土豪金', null, null, 9999.00, 1, 5, null, null, null, null, null, null, null, null, null, null, 0, 0, null);
INSERT INTO tulingshop.oms_order_item (id, order_id, order_sn, product_id, product_pic, product_name, product_brand, product_sn, product_price, product_quantity, product_sku_id, product_sku_code, product_category_id, sp1, sp2, sp3, promotion_name, promotion_amount, coupon_amount, integration_amount, real_amount, gift_integration, gift_growth, product_attr) VALUES (55, 36, '202211151668500338473', 37, null, 'iphone 20 4G 128G 土豪金', null, null, 9999.00, 1, 5, null, null, null, null, null, null, null, null, null, null, 0, 0, null);
INSERT INTO tulingshop.oms_order_item (id, order_id, order_sn, product_id, product_pic, product_name, product_brand, product_sn, product_price, product_quantity, product_sku_id, product_sku_code, product_category_id, sp1, sp2, sp3, promotion_name, promotion_amount, coupon_amount, integration_amount, real_amount, gift_integration, gift_growth, product_attr) VALUES (56, 37, '202211151668500518506', 37, null, 'iphone 20 4G 64G 玫瑰红', null, null, 8888.00, 1, 15, null, null, null, null, null, null, null, null, null, null, 0, 0, null);
INSERT INTO tulingshop.oms_order_item (id, order_id, order_sn, product_id, product_pic, product_name, product_brand, product_sn, product_price, product_quantity, product_sku_id, product_sku_code, product_category_id, sp1, sp2, sp3, promotion_name, promotion_amount, coupon_amount, integration_amount, real_amount, gift_integration, gift_growth, product_attr) VALUES (57, 38, '202211161668572802746', 37, null, 'iphone 20 4G 64G 玫瑰红', null, null, 8888.00, 1, 15, null, null, null, null, null, null, null, null, null, null, 0, 0, null);
create table pms_album
(
    id          int auto_increment
        primary key,
    name        varchar(64)   null,
    cover_pic   varchar(1000) null,
    pic_count   int           null,
    sort        int           null,
    description varchar(1000) null
)
    comment '相册表';


create table pms_album_pic
(
    id       int auto_increment
        primary key,
    album_id bigint        null,
    pic      varchar(1000) null
)
    comment '画册图片表';


create table pms_attr
(
    id            int auto_increment
        primary key,
    name          varchar(50)  null,
    value         varchar(100) null,
    type          int          null comment '1=单选，2=多选，3=手动输入【注意：1，2也应该提供手动输入】',
    category_id   int          null,
    group_id      int          null,
    type2         int          null comment '属性类型，1=规格属性，2=销售属性',
    category_name varchar(100) null
)
    comment '属性参考表';

INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (1, '网络', '4G,5G', 1, 19, 1, 1, '手机通讯');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (2, '内存', '2G,3G,4G,8G', 2, 19, null, 2, '手机通讯');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (3, '容量', '32G,64G,128G', 2, 19, null, 2, '手机通讯');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (4, '屏幕尺寸', '4.1,4.5,4.7,5.5', 1, 19, 1, 1, '手机通讯');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (5, '系统', 'android,ios', 1, 19, 1, 1, '手机通讯');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (6, '处理器', '', 3, 19, 1, 1, '手机通讯');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (7, '电池容量', '', 3, 19, 1, 1, '手机通讯');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (8, '颜色', '土豪金,玫瑰红,翡翠绿,天空蓝', 2, 19, null, 2, '手机通讯');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (10, '测试分组属性1', '1,2,3', 2, 30, 5, 1, '手机配件');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (12, '材质', 'PC,PVC,PET,AR', 1, 30, 5, 1, '手机配件');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (13, '类型', '抗蓝光,防窥屏,磨砂,高清膜,钻石膜', 2, 30, null, 2, '手机配件');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (14, '功能', '防摔,防爆,防指纹', 2, 30, 5, 1, '手机配件');
INSERT INTO tulingshop.pms_attr (id, name, value, type, category_id, group_id, type2, category_name) VALUES (15, '数量', '1片,2片', 2, 30, null, 2, '手机配件');
create table pms_attr_spu_relation
(
    id      int auto_increment
        primary key,
    spu_id  int          null,
    value   varchar(100) null,
    attr_id int          null,
    name    varchar(50)  null
)
    comment 'attr&spu关联表';

INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (1, 26, '4G', 1, '网络');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (2, 26, '4.7', 4, '屏幕尺寸');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (3, 26, 'android', 5, '系统');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (4, 26, '骁龙888', 6, '处理器');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (5, 26, '4000毫安', 7, '电池容量');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (6, 27, '4G', 1, '网络');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (7, 37, '4G', 1, '网络');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (8, 37, '4.7', 4, '屏幕尺寸');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (9, 37, 'ios', 5, '系统');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (10, 37, 'A12', 6, '处理器');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (11, 37, '3000毫安', 7, '电池容量');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (32, 42, '4G', 1, '网络');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (33, 42, '5.5', 4, '屏幕尺寸');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (34, 42, 'android', 5, '系统');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (35, 42, '骁龙999', 6, '处理器');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (36, 42, '10000毫安', 7, '电池容量');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (37, 43, '4G', 1, '网络');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (38, 43, '4.5', 4, '屏幕尺寸');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (39, 43, 'android', 5, '系统');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (40, 43, '奔腾888', 6, '处理器');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (41, 43, '5000毫安', 7, '电池容量');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (42, 44, '1,2', 10, '测试分组属性1');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (43, 44, 'PET', 12, '材质');
INSERT INTO tulingshop.pms_attr_spu_relation (id, spu_id, value, attr_id, name) VALUES (44, 44, '防爆,防指纹,防摔', 14, '功能');
create table pms_attr_spu_sku_relation
(
    id      int auto_increment
        primary key,
    spu_id  int          null,
    sku_id  int          null,
    value   varchar(100) null,
    attr_id int          null,
    name    varchar(50)  null
)
    comment 'attr&spu&sku关联表';

INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (1, 26, 1, '4G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (2, 26, 1, '128G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (3, 26, 1, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (4, 26, 2, '8G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (5, 26, 2, '256G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (6, 26, 2, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (7, 37, 3, '4G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (8, 37, 3, '64G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (9, 37, 3, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (10, 37, 4, '8G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (11, 37, 4, '64G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (12, 37, 4, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (13, 37, 5, '4G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (14, 37, 5, '128G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (15, 37, 5, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (43, 37, 15, '4G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (44, 37, 15, '64G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (45, 37, 15, '玫瑰红', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (46, 37, 16, '8G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (47, 37, 16, '128G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (48, 37, 16, '玫瑰红', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (49, 37, 17, '8G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (50, 37, 17, '128G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (51, 37, 17, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (52, 37, 18, '8G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (53, 37, 18, '64G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (54, 37, 18, '玫瑰红', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (55, 37, 19, '4G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (56, 37, 19, '128G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (57, 37, 19, '玫瑰红', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (58, 42, 20, '4G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (59, 42, 20, '128G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (60, 42, 20, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (61, 42, 21, '4G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (62, 42, 21, '64G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (63, 42, 21, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (70, 43, 24, '4G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (71, 43, 24, '128G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (72, 43, 24, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (73, 43, 25, '8G', 2, '内存');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (74, 43, 25, '128G', 3, '容量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (75, 43, 25, '土豪金', 8, '颜色');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (76, 44, 26, '防窥屏', 13, '类型');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (77, 44, 26, '1片', 15, '数量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (78, 44, 27, '抗蓝光', 13, '类型');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (79, 44, 27, '1片', 15, '数量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (80, 44, 28, '防窥屏', 13, '类型');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (81, 44, 28, '2片', 15, '数量');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (82, 44, 29, '抗蓝光', 13, '类型');
INSERT INTO tulingshop.pms_attr_spu_sku_relation (id, spu_id, sku_id, value, attr_id, name) VALUES (83, 44, 29, '2片', 15, '数量');
create table pms_brand
(
    id                    int auto_increment
        primary key,
    name                  varchar(64)  null,
    first_letter          varchar(8)   null comment '首字母',
    sort                  int          null,
    factory_status        int(1)       null comment '是否为品牌制造商：0->不是；1->是',
    show_status           int(1)       null,
    product_count         int          null comment '产品数量',
    product_comment_count int          null comment '产品评论数量',
    logo                  varchar(255) null comment '品牌logo',
    big_pic               varchar(255) null comment '专区大图',
    brand_story           text         null comment '品牌故事'
)
    comment '品牌表';

INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (1, '万和', 'W', 0, 1, 1, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg(5).jpg', '', 'Victoria''s Secret的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (2, '三星', 'S', 100, 1, 1, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg (1).jpg', null, '三星的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (3, '华为', 'H', 100, 1, 1, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/17f2dd9756d9d333bee8e60ce8c03e4c_222_222.jpg', null, 'Victoria''s Secret的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (4, '格力', 'G', 30, 1, 1, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/dc794e7e74121272bbe3ce9bc41ec8c3_222_222.jpg', null, 'Victoria''s Secret的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (5, '方太', 'F', 20, 1, 1, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg (4).jpg', null, 'Victoria''s Secret的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (6, '小米', 'M', 500, 1, 1, 100, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/1e34aef2a409119018a4c6258e39ecfb_222_222.png', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180518/5afd7778Nf7800b75.jpg', '小米手机的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (21, 'OPPO', 'O', 0, 1, 1, 88, 500, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg(6).jpg', '', 'string');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (49, '七匹狼', 'S', 200, 1, 1, 77, 400, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/18d8bc3eb13533fab466d702a0d3fd1f40345bcd.jpg', null, 'BOOB的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (50, '海澜之家', 'H', 200, 1, 1, 66, 300, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/99d3279f1029d32b929343b09d3c72de_222_222.jpg', '', '海澜之家的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (51, '苹果', 'A', 200, 1, 1, 55, 200, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/timg.jpg', null, '苹果的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (58, 'NIKE', 'N', 0, 1, 1, 33, 100, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/timg (51).jpg', '', 'NIKE的故事');
INSERT INTO tulingshop.pms_brand (id, name, first_letter, sort, factory_status, show_status, product_count, product_comment_count, logo, big_pic, brand_story) VALUES (59, 'VIVO', 'V', 0, 1, 1, null, null, null, null, null);
create table pms_product
(
    id                            int auto_increment
        primary key,
    brand_id                      bigint         null,
    product_category_id           bigint         null,
    feight_template_id            bigint         null,
    product_attribute_category_id bigint         null,
    name                          varchar(64)    not null,
    pic                           varchar(255)   null,
    product_sn                    varchar(64)    not null comment '货号',
    delete_status                 int(1)         null comment '删除状态：0->未删除；1->已删除',
    publish_status                int(1)         null comment '上架状态：0->下架；1->上架',
    new_status                    int(1)         null comment '新品状态:0->不是新品；1->新品',
    recommend_status              int(1)         null comment '推荐状态；0->不推荐；1->推荐',
    verify_status                 int(1)         null comment '审核状态：0->未审核；1->审核通过',
    sort                          int            null comment '排序',
    sale                          int            null comment '销量',
    price                         decimal(10, 2) null,
    promotion_price               decimal(10, 2) null comment '促销价格',
    gift_growth                   int default 0  null comment '赠送的成长值',
    gift_point                    int default 0  null comment '赠送的积分',
    use_point_limit               int            null comment '限制使用的积分数',
    sub_title                     varchar(255)   null comment '副标题',
    description                   text           null comment '商品描述',
    original_price                decimal(10, 2) null comment '市场价',
    stock                         int            null comment '库存',
    low_stock                     int            null comment '库存预警值',
    unit                          varchar(16)    null comment '单位',
    weight                        decimal(10, 2) null comment '商品重量，默认为克',
    preview_status                int(1)         null comment '是否为预告商品：0->不是；1->是',
    service_ids                   varchar(64)    null comment '以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮',
    keywords                      varchar(255)   null,
    note                          varchar(255)   null,
    album_pics                    varchar(255)   null comment '画册图片，连产品图片限制为5张，以逗号分割',
    detail_title                  varchar(255)   null,
    detail_desc                   text           null,
    detail_html                   text           null comment '产品详情网页内容',
    detail_mobile_html            text           null comment '移动端网页详情',
    promotion_start_time          datetime       null comment '促销开始时间',
    promotion_end_time            datetime       null comment '促销结束时间',
    promotion_per_limit           int            null comment '活动限购数量',
    promotion_type                int(1)         null comment '促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购',
    brand_name                    varchar(255)   null comment '品牌名称',
    product_category_name         varchar(255)   null comment '商品分类名称'
)
    comment '商品信息';

INSERT INTO tulingshop.pms_product (id, brand_id, product_category_id, feight_template_id, product_attribute_category_id, name, pic, product_sn, delete_status, publish_status, new_status, recommend_status, verify_status, sort, sale, price, promotion_price, gift_growth, gift_point, use_point_limit, sub_title, description, original_price, stock, low_stock, unit, weight, preview_status, service_ids, keywords, note, album_pics, detail_title, detail_desc, detail_html, detail_mobile_html, promotion_start_time, promotion_end_time, promotion_per_limit, promotion_type, brand_name, product_category_name) VALUES (26, 3, 19, 0, 3, '华为 HUAWEI P20 ', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg', '6946605', 0, 1, 1, 1, 0, 100, 0, 3788.00, null, 3788, 3788, 0, 'AI智慧全面屏 6GB +64GB 亮黑色 全网通版 移动联通电信4G手机 双卡双待手机 双卡双待', '', 4288.00, 1000, 0, '件', 0.00, 1, '2,3,1', '手机,手机通讯', '', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ab46a3cN616bdc41.jpg,http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf5fN2522b9dc.jpg', '', '', '<p><img class="wscnph" src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44f1cNf51f3bb0.jpg" /><img class="wscnph" src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44fa8Nfcf71c10.jpg" /><img class="wscnph" src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44fa9N40e78ee0.jpg" /><img class="wscnph" src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad457f4N1c94bdda.jpg" /><img class="wscnph" src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad457f5Nd30de41d.jpg" /><img class="wscnph" src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5b10fb0eN0eb053fb.jpg" /></p>', '', null, null, 0, 1, '华为', '手机通讯');
INSERT INTO tulingshop.pms_product (id, brand_id, product_category_id, feight_template_id, product_attribute_category_id, name, pic, product_sn, delete_status, publish_status, new_status, recommend_status, verify_status, sort, sale, price, promotion_price, gift_growth, gift_point, use_point_limit, sub_title, description, original_price, stock, low_stock, unit, weight, preview_status, service_ids, keywords, note, album_pics, detail_title, detail_desc, detail_html, detail_mobile_html, promotion_start_time, promotion_end_time, promotion_per_limit, promotion_type, brand_name, product_category_name) VALUES (27, 6, 19, 0, 3, '小米8 全面屏游戏智能手机 全网通4G 双卡双待', 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/xiaomi.jpg', '7437788', 0, 1, 1, 1, 0, 0, 0, 2699.00, null, 2699, 2699, 0, '骁龙845处理器，红外人脸解锁，AI变焦双摄，AI语音助手小米6X低至1299，点击抢购', '小米8 全面屏游戏智能手机 6GB+64GB 黑色 全网通4G 双卡双待', 2699.00, 100, 0, '', 0.00, 0, '', '手机,手机通讯', '', '', '', '', '<p><img class="wscnph" src="http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180615/5b2254e8N414e6d3a.jpg" width="500" /></p>', '', null, null, 0, 3, '小米', '手机通讯');
INSERT INTO tulingshop.pms_product (id, brand_id, product_category_id, feight_template_id, product_attribute_category_id, name, pic, product_sn, delete_status, publish_status, new_status, recommend_status, verify_status, sort, sale, price, promotion_price, gift_growth, gift_point, use_point_limit, sub_title, description, original_price, stock, low_stock, unit, weight, preview_status, service_ids, keywords, note, album_pics, detail_title, detail_desc, detail_html, detail_mobile_html, promotion_start_time, promotion_end_time, promotion_per_limit, promotion_type, brand_name, product_category_name) VALUES (37, 51, 19, null, null, 'iphone 20', null, '6736540f33fb4351bb218b6b104eef88', 0, 1, 1, 1, null, null, null, 3000.00, null, 0, 0, null, 'iphone 20', '123', null, null, null, '个', 500.00, null, null, '手机,手机通讯', null, null, null, null, null, null, null, null, null, null, '苹果', '手机通讯');
INSERT INTO tulingshop.pms_product (id, brand_id, product_category_id, feight_template_id, product_attribute_category_id, name, pic, product_sn, delete_status, publish_status, new_status, recommend_status, verify_status, sort, sale, price, promotion_price, gift_growth, gift_point, use_point_limit, sub_title, description, original_price, stock, low_stock, unit, weight, preview_status, service_ids, keywords, note, album_pics, detail_title, detail_desc, detail_html, detail_mobile_html, promotion_start_time, promotion_end_time, promotion_per_limit, promotion_type, brand_name, product_category_name) VALUES (42, 3, 19, null, null, 'huawei p50', null, 'd10a4aa17f764c1daa68406508d20a1f', 0, 1, 1, 1, null, null, null, 10099.00, null, 0, 0, null, 'huawei p50', '', null, null, null, '个', 500.00, null, null, '手机,手机通讯', null, null, null, null, null, null, null, null, null, null, '华为', '手机通讯');
INSERT INTO tulingshop.pms_product (id, brand_id, product_category_id, feight_template_id, product_attribute_category_id, name, pic, product_sn, delete_status, publish_status, new_status, recommend_status, verify_status, sort, sale, price, promotion_price, gift_growth, gift_point, use_point_limit, sub_title, description, original_price, stock, low_stock, unit, weight, preview_status, service_ids, keywords, note, album_pics, detail_title, detail_desc, detail_html, detail_mobile_html, promotion_start_time, promotion_end_time, promotion_per_limit, promotion_type, brand_name, product_category_name) VALUES (43, 59, 19, null, null, 'vivo50', null, 'd289100f28b94ce49d5c067602c55801', 0, 1, 1, 1, null, null, null, 1666.00, null, 0, 0, null, 'vivo50', '', null, null, null, '个', 500.00, null, null, '手机,手机通讯', null, null, null, null, null, null, null, null, null, null, 'VIVO', '手机通讯');
INSERT INTO tulingshop.pms_product (id, brand_id, product_category_id, feight_template_id, product_attribute_category_id, name, pic, product_sn, delete_status, publish_status, new_status, recommend_status, verify_status, sort, sale, price, promotion_price, gift_growth, gift_point, use_point_limit, sub_title, description, original_price, stock, low_stock, unit, weight, preview_status, service_ids, keywords, note, album_pics, detail_title, detail_desc, detail_html, detail_mobile_html, promotion_start_time, promotion_end_time, promotion_per_limit, promotion_type, brand_name, product_category_name) VALUES (44, 6, 30, null, null, 'iphone 20 保护膜', null, '8f34824a5d864c00833d60d004805638', 0, 1, 1, 1, null, null, null, 20.00, null, 0, 0, null, '炒鸡手机保护膜，全方位保护手机', '', null, null, null, '个', 120.00, null, null, '手机,手机配件', null, null, null, null, null, null, null, null, null, null, '小米', '手机配件');
create table pms_product_attr_group
(
    id   int auto_increment
        primary key,
    name varchar(50) null
);

INSERT INTO tulingshop.pms_product_attr_group (id, name) VALUES (1, '主体');
INSERT INTO tulingshop.pms_product_attr_group (id, name) VALUES (5, '测试属性分组1');
create table pms_product_attribute
(
    id                            int auto_increment
        primary key,
    product_attribute_category_id bigint       null,
    name                          varchar(64)  null,
    select_type                   int(1)       null comment '属性选择类型：0->唯一；1->单选；2->多选',
    input_type                    int(1)       null comment '属性录入方式：0->手工录入；1->从列表中选取',
    input_list                    varchar(255) null comment '可选值列表，以逗号隔开',
    sort                          int          null comment '排序字段：最高的可以单独上传图片',
    filter_type                   int(1)       null comment '分类筛选样式：1->普通；1->颜色',
    search_type                   int(1)       null comment '检索类型；0->不需要进行检索；1->关键字检索；2->范围检索',
    related_status                int(1)       null comment '相同属性产品是否关联；0->不关联；1->关联',
    hand_add_status               int(1)       null comment '是否支持手动新增；0->不支持；1->支持',
    type                          int(1)       null comment '属性的类型；0->规格；1->参数'
)
    comment '商品属性参数表';


create table pms_product_attribute_value
(
    id                   int auto_increment
        primary key,
    product_id           bigint      null,
    product_attribute_id bigint      null,
    value                varchar(64) null comment '手动添加规格或参数的值，参数单值，规格有多个时以逗号隔开'
)
    comment '存储产品参数信息的表';


create table pms_product_category
(
    id            int auto_increment
        primary key,
    parent_id     bigint       null comment '上机分类的编号：0表示一级分类',
    name          varchar(64)  null,
    level         int(1)       null comment '分类级别：0->1级；1->2级',
    product_count int          null,
    product_unit  varchar(64)  null,
    nav_status    int(1)       null comment '是否显示在导航栏：0->不显示；1->显示',
    show_status   int(1)       null comment '显示状态：0->不显示；1->显示',
    sort          int          null,
    icon          varchar(255) null comment '图标',
    keywords      varchar(255) null,
    description   text         null comment '描述'
)
    comment '产品分类';

INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (1, 0, '服装', 0, 100, '件', 1, 1, 1, null, '服装', '服装分类');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (2, 0, '手机数码', 0, 100, '件', 1, 1, 1, null, '手机数码', '手机数码');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (3, 0, '家用电器', 0, 100, '件', 1, 1, 1, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/subject_cate_jiadian.png', '家用电器', '家用电器');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (4, 0, '家具家装', 0, 100, '件', 1, 1, 1, null, '家具家装', '家具家装');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (5, 0, '汽车用品', 0, 100, '件', 1, 1, 1, null, '汽车用品', '汽车用品');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (7, 1, '外套', 1, 100, '件', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_waitao.png', '外套', '外套');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (8, 1, 'T恤', 1, 100, '件', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_tshirt.png', 'T恤', 'T恤');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (9, 1, '休闲裤', 1, 100, '件', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_xiuxianku.png', '休闲裤', '休闲裤');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (10, 1, '牛仔裤', 1, 100, '件', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_niuzaiku.png', '牛仔裤', '牛仔裤');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (11, 1, '衬衫', 1, 100, '件', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_chenshan.png', '衬衫', '衬衫分类');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (19, 2, '手机通讯', 1, 0, '件', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_shouji.png', '手机通讯', '手机通讯');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (29, 1, '男鞋', 1, 0, '', 0, 0, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_xie.png', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (30, 2, '手机配件', 1, 0, '', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_peijian.png', '手机配件', '手机配件');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (31, 2, '摄影摄像', 1, 0, '', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_sheying.png', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (32, 2, '影音娱乐', 1, 0, '', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_yule.png', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (33, 2, '数码配件', 1, 0, '', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_yule.png', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (34, 2, '智能设备', 1, 0, '', 1, 1, 0, 'http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20190129/product_cate_zhineng.png', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (35, 3, '电视', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (36, 3, '空调', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (37, 3, '洗衣机', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (38, 3, '冰箱', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (39, 3, '厨卫大电', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (40, 3, '厨房小电', 1, 0, '', 0, 0, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (41, 3, '生活电器', 1, 0, '', 0, 0, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (42, 3, '个护健康', 1, 0, '', 0, 0, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (43, 4, '厨房卫浴', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (44, 4, '灯饰照明', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (45, 4, '五金工具', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (46, 4, '卧室家具', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (47, 4, '客厅家具', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (48, 5, '全新整车', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (49, 5, '车载电器', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (50, 5, '维修保养', 1, 0, '', 1, 1, 0, '', '', '');
INSERT INTO tulingshop.pms_product_category (id, parent_id, name, level, product_count, product_unit, nav_status, show_status, sort, icon, keywords, description) VALUES (51, 5, '汽车装饰', 1, 0, '', 1, 1, 0, '', '', '');
create table pms_product_sku
(
    id           int auto_increment
        primary key,
    spu_id       int              null,
    name         varchar(200)     null,
    price        decimal(10, 2)   null,
    sales        int    default 0 null,
    stock        bigint default 0 null,
    stock_locked bigint default 0 null
);

INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (1, 26, '华为 HUAWEI P20 土豪金 4G+128G', 9999.00, 0, 0, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (2, 26, '华为 HUAWEI P20 土豪金 8G+256G', 19999.00, 0, 49, 1);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (3, 37, 'iphone 20 4G 64G 土豪金', 6999.00, 0, 50, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (4, 37, 'iphone 20 8G 64G 土豪金', 7999.00, 0, 50, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (5, 37, 'iphone 20 4G 128G 土豪金', 9999.00, 0, 28, 3);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (15, 37, 'iphone 20 4G 64G 玫瑰红', 8888.00, 0, 99, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (16, 37, 'iphone 20 8G 128G 玫瑰红', 11999.00, 0, 100, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (17, 37, 'iphone 20 8G 128G 土豪金', 0.00, 0, 0, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (18, 37, 'iphone 20 8G 64G 玫瑰红', 0.00, 0, 0, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (19, 37, 'iphone 20 4G 128G 玫瑰红', 0.00, 0, 0, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (20, 42, 'huawei p50 4G 128G 土豪金', 18999.00, 0, 100, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (21, 42, 'huawei p50 4G 64G 土豪金', 12999.00, 0, 100, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (24, 43, 'vivo50 4G 128G 土豪金', 1999.00, 0, 50, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (25, 43, 'vivo50 8G 128G 土豪金', 3999.00, 0, 50, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (26, 44, 'iphone 20 保护膜 防窥屏 1片', 20.00, 0, 100, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (27, 44, 'iphone 20 保护膜 抗蓝光 1片', 30.00, 0, 100, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (28, 44, 'iphone 20 保护膜 防窥屏 2片', 38.00, 0, 100, 0);
INSERT INTO tulingshop.pms_product_sku (id, spu_id, name, price, sales, stock, stock_locked) VALUES (29, 44, 'iphone 20 保护膜 抗蓝光 2片', 58.00, 0, 100, 0);
create table tb_permission
(
    id           int(11) unsigned auto_increment
        primary key,
    type         int(11) unsigned not null comment '权限类型。1为菜单，2为操作，3...',
    value        varchar(100)     not null comment '权限标识码',
    name         varchar(50)      not null comment '权限名称',
    created_time datetime(3)      null,
    updated_time datetime(3)      null,
    path         varchar(100)     null,
    `order`      int              null comment '排序',
    extra        varchar(200)     null comment '可以用于保存json字符串，供前端使用',
    parent_id    int              null,
    constraint value
        unique (value)
);

INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (3, 1, 'sys', '系统管理', null, null, '/sys', null, null, -1);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (5, 1, 'sys:user', '用户管理', null, null, '/sys/user', null, null, 3);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (12, 1, 'sys:permission', '权限管理', null, null, '/sys/permission', null, null, 3);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (13, 1, 'sys:role', '角色管理', null, null, '/sys/role', null, null, 3);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (16, 2, 'user:del', '删除用户', null, null, '', null, null, 5);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (17, 2, 'user:save', '新增用户', null, null, '', null, null, 5);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (18, 2, 'user:update', '更新用户', null, null, '', null, null, 5);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (21, 2, 'permission:del', '删除权限', null, null, '', null, null, 12);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (22, 2, 'permission:update', '更新权限', null, null, '', null, null, 12);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (23, 2, 'permission:save', '新增权限', null, null, '', null, null, 12);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (26, 2, 'role:save', '新增角色', null, null, null, null, null, 13);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (27, 2, 'role:del', '删除角色', null, null, null, null, null, 13);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (28, 2, 'role:update', '更新角色', null, null, null, null, null, 13);
INSERT INTO tulingshop.tb_permission (id, type, value, name, created_time, updated_time, path, `order`, extra, parent_id) VALUES (29, 1, 'sys:test1', '测试菜单123', null, null, '/sys/test1', null, null, 3);
create table tb_role
(
    id           int(11) unsigned auto_increment
        primary key,
    name         varchar(30)  not null comment '角色名称',
    description  varchar(100) null comment '角色描述',
    created_time datetime(3)  null,
    updated_time datetime(3)  null,
    constraint name
        unique (name)
);

INSERT INTO tulingshop.tb_role (id, name, description, created_time, updated_time) VALUES (3, '测试角色1', '123', null, null);
INSERT INTO tulingshop.tb_role (id, name, description, created_time, updated_time) VALUES (4, '管理员', '', null, null);
create table tb_role_permission
(
    id            int auto_increment
        primary key,
    role_id       int not null,
    permission_id int not null
);

INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (41, 4, 3);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (42, 4, 12);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (43, 4, 13);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (44, 4, 5);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (47, 4, 16);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (48, 4, 17);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (49, 4, 18);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (52, 4, 28);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (54, 3, 3);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (55, 3, 5);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (56, 3, 12);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (57, 3, 13);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (58, 3, 29);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (65, 4, 21);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (66, 4, 22);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (67, 4, 23);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (68, 4, 26);
INSERT INTO tulingshop.tb_role_permission (id, role_id, permission_id) VALUES (69, 4, 27);
create table tb_user
(
    id           int(11) unsigned auto_increment
        primary key,
    username     varchar(64)      not null comment '用户名',
    password     varchar(64)      not null comment '用户密码',
    avatar       varchar(255)     null comment '用户头像，为一串url地址，默认为空。',
    created_time datetime(3)      null,
    updated_time datetime(3)      null,
    nickname     varchar(64)      null,
    email        varchar(100)     null,
    state        int(1) default 1 null comment '1正常，2禁用',
    constraint username
        unique (username)
)
    comment 'tulingshop-admin用户';

INSERT INTO tulingshop.tb_user (id, username, password, avatar, created_time, updated_time, nickname, email, state) VALUES (1, 'admin', '202cb962ac59075b964b07152d234b70', null, null, null, 'admin', 'aojiaodage@gmail.com', 1);
INSERT INTO tulingshop.tb_user (id, username, password, avatar, created_time, updated_time, nickname, email, state) VALUES (8, 'test1', '202cb962ac59075b964b07152d234b70', '', null, null, 'test1', 'abc@123.com', 1);
create table tb_user_role
(
    id      int auto_increment
        primary key,
    user_id int not null,
    role_id int not null
);

INSERT INTO tulingshop.tb_user_role (id, user_id, role_id) VALUES (7, 1, 4);
INSERT INTO tulingshop.tb_user_role (id, user_id, role_id) VALUES (8, 8, 3);
create table ums_member
(
    id                     int auto_increment
        primary key,
    member_level_id        int          null,
    username               varchar(64)  null comment '用户名',
    password               varchar(64)  null comment '密码',
    nickname               varchar(64)  null comment '昵称',
    phone                  varchar(64)  null comment '手机号码',
    status                 int(1)       null comment '帐号启用状态:0->禁用；1->启用',
    create_time            datetime     null comment '注册时间',
    icon                   varchar(500) null comment '头像',
    gender                 int(1)       null comment '性别：0->未知；1->男；2->女',
    birthday               date         null comment '生日',
    city                   varchar(64)  null comment '所做城市',
    job                    varchar(100) null comment '职业',
    personalized_signature varchar(200) null comment '个性签名',
    source_type            int(1)       null comment '用户来源',
    integration            int          null comment '积分',
    growth                 int          null comment '成长值',
    history_integration    int          null comment '历史积分数量',
    constraint idx_phone
        unique (phone),
    constraint idx_username
        unique (username)
)
    comment '会员表';

INSERT INTO tulingshop.ums_member (id, member_level_id, username, password, nickname, phone, status, create_time, icon, gender, birthday, city, job, personalized_signature, source_type, integration, growth, history_integration) VALUES (3, 1, '123', '202cb962ac59075b964b07152d234b70', '123', '18088888888', 1, '2022-11-04 16:34:50', null, 1, '2021-10-31', null, null, null, 1, null, null, null);
INSERT INTO tulingshop.ums_member (id, member_level_id, username, password, nickname, phone, status, create_time, icon, gender, birthday, city, job, personalized_signature, source_type, integration, growth, history_integration) VALUES (4, 1, 'test1', 'e10adc3949ba59abbe56e057f20f883e', 'test1', '18520568417', 1, '2022-11-04 17:58:09', null, 1, '2003-11-02', null, null, null, 1, null, null, null);
create table ums_member_level
(
    id                      int auto_increment
        primary key,
    name                    varchar(100)   null,
    growth_point            int            null,
    default_status          int(1)         null comment '是否为默认等级：0->不是；1->是',
    free_freight_point      decimal(10, 2) null comment '免运费标准',
    comment_growth_point    int            null comment '每次评价获取的成长值',
    priviledge_free_freight int(1)         null comment '是否有免邮特权',
    priviledge_sign_in      int(1)         null comment '是否有签到特权',
    priviledge_comment      int(1)         null comment '是否有评论获奖励特权',
    priviledge_promotion    int(1)         null comment '是否有专享活动特权',
    priviledge_member_price int(1)         null comment '是否有会员价格特权',
    priviledge_birthday     int(1)         null comment '是否有生日特权',
    note                    varchar(200)   null
)
    comment '会员等级表';

INSERT INTO tulingshop.ums_member_level (id, name, growth_point, default_status, free_freight_point, comment_growth_point, priviledge_free_freight, priviledge_sign_in, priviledge_comment, priviledge_promotion, priviledge_member_price, priviledge_birthday, note) VALUES (1, '黄金会员', 1000, 0, 199.00, 5, 1, 1, 1, 1, 1, 1, null);
INSERT INTO tulingshop.ums_member_level (id, name, growth_point, default_status, free_freight_point, comment_growth_point, priviledge_free_freight, priviledge_sign_in, priviledge_comment, priviledge_promotion, priviledge_member_price, priviledge_birthday, note) VALUES (2, '白金会员', 5000, 0, 99.00, 10, 1, 1, 1, 1, 1, 1, null);
INSERT INTO tulingshop.ums_member_level (id, name, growth_point, default_status, free_freight_point, comment_growth_point, priviledge_free_freight, priviledge_sign_in, priviledge_comment, priviledge_promotion, priviledge_member_price, priviledge_birthday, note) VALUES (3, '钻石会员', 15000, 0, 69.00, 15, 1, 1, 1, 1, 1, 1, null);
INSERT INTO tulingshop.ums_member_level (id, name, growth_point, default_status, free_freight_point, comment_growth_point, priviledge_free_freight, priviledge_sign_in, priviledge_comment, priviledge_promotion, priviledge_member_price, priviledge_birthday, note) VALUES (4, '普通会员', 1, 1, 199.00, 20, 1, 1, 1, 1, 0, 0, null);
create table ums_member_receive_address
(
    id             int auto_increment
        primary key,
    member_id      int          null,
    name           varchar(100) null comment '收货人名称',
    phone_number   varchar(64)  null,
    default_status int(1)       null comment '是否为默认',
    post_code      varchar(100) null comment '邮政编码',
    province       varchar(100) null comment '省份/直辖市',
    city           varchar(100) null comment '城市',
    region         varchar(100) null comment '区',
    detail_address varchar(128) null comment '详细地址(街道)'
)
    comment '会员收货地址表';

INSERT INTO tulingshop.ums_member_receive_address (id, member_id, name, phone_number, default_status, post_code, province, city, region, detail_address) VALUES (8, 4, '暗黑元首', '18888888888', 1, '130302', '河北省', '秦皇岛市', '海港区', '光明区梦想路36号');
INSERT INTO tulingshop.ums_member_receive_address (id, member_id, name, phone_number, default_status, post_code, province, city, region, detail_address) VALUES (10, 4, '暗黑元首', '18888888888', 0, '130302', '河北省', '秦皇岛市', '海港区', '光明区梦想路39号');