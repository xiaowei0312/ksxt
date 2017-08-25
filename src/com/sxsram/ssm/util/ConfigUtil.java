package com.sxsram.ssm.util;

public class ConfigUtil {

	public static final String SEND_RED_PACKT_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/sendredpack";
	public static final String ENTERPRISE_PAY_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";
	public static final String CRETPATH = "/usr/local/apache-tomcat-7.0.70/webapps/wechat/WEB-INF/cert/apiclient_cert.p12";
	public static final String CRETPASSWD = "1409001202";

	public static final String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	public static final Object MCH_ID = "1409001202";
	public static final Object NOTIFY_URL = "http://www.0352jdw.com/wechat/wxpay/serverNotify.action";
	// public static final String APPID = "wx20be3e329920dba3";
	// public static final String APPSECRET =
	// "00822f6e0a52150b8ad33a877f0c286d";
	public static final String APPID = "wx7415851e6e256083";
	public static final String APPSECRET = "4d8425e42e28ba0b721661fa9743e365";
	public static final String PAYKEY = "8c74d5e754514623a113f31f39384081";
	public static final Object SIGN_TYPE = "MD5";
	public static final Object SUCCESS_URL = "/wechat/user/userCenter.action";
	public static final String TOKEN = "chatdog";

	public static final String MENU_KEY_ZHGK = "V01_KEY_01_01"; // 账户概况
	public static final String MENU_KEY_YLJCX = "V01_KEY_01_02"; // 养老金查询
	public static final String MENU_KEY_JDLN = "V01_KEY_02_01"; // 聚德理念
	public static final String MENU_KEY_GSZZ = "V01_KEY_02_02"; // 公司资质
	// 用户输入消息
	public static final String USER_INPUT_MSG = "欢迎光临聚德购物商城，请点击屏幕下方菜单进行操作";
	// 未登录菜单点击消息
	public static final String MENU_CLICK_NOT_LOGIN_MSG = "您尚未绑定聚德购物平台会员号码，请点击下方<会员中心>进行登录绑定!";
	// 账户概况显示消息
	public static final String MENU_CLICK_ZZGK_MSG = "您账户概况如下：\n剩余积分：%d分\n可用余额：%.2f元\n累计消费：%.2f元\n累计赠送：%.2f元\n当前聚财宝：%d个\n\n感谢您关注聚德公众号";
	// 养老金查询显示消息
	public static final String MENU_CLICK_YLJCX_MSG = "您养老金账户概况如下：\n账户余额：%.2f元\n累计积攒：%.2f元\n累计转存：%.2f元\n\n感谢您关注聚德公众号";
	public static final String BUSINESSNAME = "聚德网";

	public static final String LOGOURL = "http://www.0352jdw.com/wechat/img/logo.jpg";

	public static final String BUY_SUCCESS_MSG = "尊敬的会员【%s】,您于 %s 在商户【%s】处购买【%s】商品，消费金额总计【%.2f】元，订单正在等待平台审核，积分会在审核通过后赠送到您的帐户中\n\n感谢您关注聚德公众号";
	public static final String TEMPLATEID = "iZLcps9od302CPZN5Aj6msl1CikVKvsrxI3dV2SuiII";
	public static final String REGISTSMSTEXT = "您正在注册成为聚德会员，校验码：SMSCODE，如果以上非您本人操作，请忽略本短信【聚德网购物商城】";
	public static final String SMSUSERNAME = "jdw0352";
	public static final String SMSPASSWORD = "123456";
	// public static final String PATH_USER_PROTOCOL = "D:\\userprotocol.htm";
	public static final String PATH_USER_PROTOCOL = "/home/jude/userprotocol.htm";
	public static final String RESETPWD = "jude123";
	// public static final String mallImgPath = "/home/jude/mallImgs/";
	// public static final String onlineCommodImgs =
	// "/home/jude/onlineCommodImgs/";
	// public static final String QALINKCONTENTPATH = "d:\\QAContentPic\\";
	// public static final String QASUBJECTPICPATH = "d:\\QASubjectPic\\";
	public static final String QALINKCONTENTPATH = "/home/chatdog/QAContentPic/";
	public static final String QASUBJECTPICPATH = "/home/chatdog/QASubjectPic/";
	public static final String QALINKCONTENTVPATH = "/QAContentPic/";
	public static final String QASUBJECTPICVPATH = "/QASubjectPic/";
	public static final String FILEPATH = "/home/bsxt/filecommit/";
	//public static final String FILEPATH = "d:\\filecommit\\";
	public static final String FILEPATH_ERROR = FILEPATH + "errorfilelist/";
	public static final String FILEPATH_SUCCESS = FILEPATH + "validfilelist/";
	public static final String FILEPATH_TMP = FILEPATH + "tmp/";

	public enum TradeType {
		RECHARGE, WITHDRAW;
	};

	public enum TradeState {
		PAYPENDING, PAYERROR, PAYSUCCESS;
	};
}
