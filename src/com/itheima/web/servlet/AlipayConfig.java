package com.itheima.web.servlet;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101600702114";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDMjIK7KTOeVyhyqNw+B0I+AUTyPRvlKliiSiJXhdnyf3O7skRtdPP/1/EtMny3+cA7UuxspzYcjFBjJ09klIqR0qnwCZ5sspI9A2oHFfxNEzpX+V/x56s0ijiY5Wi7VQTETHtYcPtfn8YBm8QjlAtgb7UC+CDf4IgSAW1HZqkowPlkeG2M1Kjo6q9Mq7XbasFI7lbP5mWocEMLQj8CJkWF5RtLQlCjMRTkXK2hetBmBRElSxYue6TPHV95SFLallXFORxDwvPgiZs3pHtRoSISyrmgTORYzjWbxtgevUIAW/QxFZQ3ME5LzZhc1qcSapzsEpTfFwlRF9soxzunu2WPAgMBAAECggEAcqHsQed9PieqS1fG1hxLS2R6tyb4Ex4APjM9u78PvY8f1Pi60HzSoXbremDo9FHLIXP/e4TaFnWsN2U4YS292qstAZDicBhDTF41dXMkyi0UbWkcE/25POCZh1ZJs3KaEUird40Z0yU/WndpHGdDWPBBiUB1bAHxP+X80B8Afg1nOcxtyLlqkgmysOwG+sYnhICW6F1NBj81yhkNuMdDhSArEikh8YJ5xbHhXI9PR89DrtMnIAV9BD8a5TarijlxTp5mwnj9eZ4h1tJlOWQfeiM3VNxTTRw9+308N0aTtbMr+FM+4QsrmhtLQM2JawulsaVs6MtSpsQ478xUIzbzwQKBgQDlLRWJp9K65SZpDBzPsvYiboOzRp4BOKw3p47kbIJ0KoLwp9BSC04w2tqbY1xnA+ewY3dO7rUpzE+2cANaAFHTJn/3/WQeW3LNtSmddXCmQE0f9QnR6G6puZ5ON/rvGYNYleIl+0Kz9pV1pVFavZKqE5nocqnKNkjnsOJ3M2kIhQKBgQDkfYIs2aNHp56L/cPfeZuxdXznnTdKtLaFqjDfrIXmWS7dZJKyoMb8vPppISyMWgodsIykJzVMfnQAjSJIh3uwdJHZKIo3eWjqP3ssSjJt5N+BvzL0tUwetGlkHpzGk5ozB4zu2G+MzUfvOH2y+953sBQXG3WyvTFtcitVb9bcAwKBgB+YLo7ytweTDc+Pnu03Y91tVPTDOIc8k+l8TSPKndV8At9Ak2SsMFRl55tigFunWyqjG9Ci8hDYHOku2hdvUdiFR0EeCs5wlwwq/Ji7lxMkbD2Kn5bzr0Xw1BU+fHa4EraEu0+3KwP6a7JzXxreZbjSyPYV0LiS8UwXw7DhQoqtAoGBAJC+ITSgOhsuR/WOsAJlWwez0Yg3w8sDZ4yNH6DR/ZDKdjGxgGzABM4eONNYPIw8jaP5L17dp5npkTq4LTuc9/H0JmBWmhhCRP//btvDtOaA0kWzBWiXYL0itJDEA7SX1Fr3MYQsf53GfQdB1f8IJwnS9SceMhUkXKdkZ6cbzhRZAoGAR0xO3H9/RDIVuLUWTLZzD56S+iSvQj9eUujrLJnvgycDtlXXY47jMIp8ISjIaMaN+DGyz2ZGWRp3TH6qLsSk3lPTlFqeNWohrbBO6eNzdEYIFgjVWqq37NVDvq4UmVcT+3hNw4SbOQBsGfBfUyMZJ+Nos9sdFJdIR6ZxJ3CN3CU=";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAr6mlSxTPDLVYwta18tQiQJNZE45EcUZ2nLAydr60bKD6a0CX4JvFJ1xzEJkpuG2RFGR9ZscMbspCpyyk7sK2v03fjG+NZQzjxYEW8dS2GJPfd9UV+wE2CUoxuluvxAP1kY/2fQ6i0Bh6q+Gl3Oi56nJrFT4lCa/IO26mVqHhVcYTQ12FL06H87VvZTmQCBC2Vrno8PdR5Ezz3tAJgTw6i7JlhvYtVh0OEQKgBOtcRjJsqP1mwl47EIlYP/Dow1Eldn28aWyp3+xHsaY7KPlbvHxFN2pFvjHbehALbr8CIM9ogY1mn2F1m5C8gIWEd54ypPMn5Tc01SebX3O9dJqEiQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://工程公网访问地址/store/jsp/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

