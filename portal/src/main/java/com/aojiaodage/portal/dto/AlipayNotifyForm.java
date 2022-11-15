package com.aojiaodage.portal.dto;

import com.aojiaodage.common.exception.CustomException;
import com.aojiaodage.common.util.FieldUtil;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class AlipayNotifyForm {
    private Date gmtCreate;
    private String charset;
    private Date gmtPayment;
    private Date notifyTime;
    private String subject;
    private String sign;
    private String buyerId;
    private String body;
    private BigDecimal invoiceAmount;
    private String version;
    private String notifyId;
    private String notifyType;
    private String outTradeNo;
    private BigDecimal totalAmount;
    private String tradeStatus;
    private String tradeNo;
    private String authAppId;
    private BigDecimal receiptAmount;
    private BigDecimal pointAmount;
    private String appId;
    private BigDecimal buyerPayAmount;
    private String signType;
    private String sellerId;
    private String fundBillList;

    @Override
    public String toString() {
        return "AlipayNotifyForm{" +
                "gmtCreate=" + gmtCreate +
                ", charset='" + charset + '\'' +
                ", gmtPayment=" + gmtPayment +
                ", notifyTime=" + notifyTime +
                ", subject='" + subject + '\'' +
                ", sign='" + sign + '\'' +
                ", buyerId='" + buyerId + '\'' +
                ", body='" + body + '\'' +
                ", invoiceAmount=" + invoiceAmount +
                ", version='" + version + '\'' +
                ", notifyId='" + notifyId + '\'' +
                ", notifyType='" + notifyType + '\'' +
                ", outTradeNo='" + outTradeNo + '\'' +
                ", totalAmount=" + totalAmount +
                ", tradeStatus='" + tradeStatus + '\'' +
                ", tradeNo='" + tradeNo + '\'' +
                ", authAppId='" + authAppId + '\'' +
                ", receiptAmount=" + receiptAmount +
                ", pointAmount=" + pointAmount +
                ", appId='" + appId + '\'' +
                ", buyerPayAmount=" + buyerPayAmount +
                ", signType='" + signType + '\'' +
                ", sellerId='" + sellerId + '\'' +
                ", fundBillList='" + fundBillList + '\'' +
                '}';
    }

    public static AlipayNotifyForm convert(Map<String, String> map) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        AlipayNotifyForm form = new AlipayNotifyForm();
        Field[] declaredFields = form.getClass().getDeclaredFields();

        try {
            for (Field declaredField : declaredFields) {
                String underlineName = FieldUtil.underline(declaredField.getName());
                if (!map.containsKey(underlineName)) {
                    continue;
                }

                Class<?> type = declaredField.getType();
                String value = map.get(underlineName);
                declaredField.setAccessible(true);

                if (type.equals(Date.class)) {
                    Date date = dateFormat.parse(value);
                    declaredField.set(form, date);
                }
                else if (type.equals(BigDecimal.class)) {
                    declaredField.set(form, new BigDecimal(value));
                }
                else {
                    declaredField.set(form, value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("转换失败：" + e.getMessage());
        }
        return form;
    }

    public static void main(String[] args) {
        HashMap<String, String> map = new HashMap<>();
        map.put("gmt_create", "2022-10-22 11:09:40");
        map.put("total_amount", "9999");
        map.put("body", "123456");
        map.put("buyer_id", "123456");
        map.put("version", "123456");
        map.put("notify_id", "123456");
        map.put("notify_type", "123456");
        map.put("out_trade_no", "123456");
        AlipayNotifyForm form = AlipayNotifyForm.convert(map);
        System.out.println(form);
    }
}
