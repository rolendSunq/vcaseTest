package com.airensoft.skovp.sample.constant;

public class CommonConstant
{
	// 관리자 계정 member_idx
	public static int ADMIN_MEMBER_IDX = 2101;
	// 데몬 실행 여부
	public static boolean CRON_IS_START = true;

	// Push Notification 실서버 사용여부
	public static boolean IS_PUSH_NOTIFICATION_SENDBOX = false;
	
	// 결제 실서버 사용여부
	public static boolean IS_PURCHASE_SENDBOX = false;
	
	// TAPJOY secret key
	public static String TAPJOY_IOS_SECRET_CODE = "lHyM7T0jkWs32OodFiOj";
	public static String TAPJOY_ANDROID_SECRET_CODE = "DjIBgrD5Iq0S6TYTos8f";
	
	// ios iap password
	public static String IOS_IAP_PASSWORD = "2c3ecde129ea4c7c889275b8bfc644a0";
	
	
	// RANK MEMBER LIMIT COUNT
	public static int RANK_MEMBER_LIMIT_COUNT = 99;
	// SEEK algorithm
	public static int SEEK_DEFAULT_START_POINT	= 1400;
		
	// 가중치
	public static int SEEK_COUNT_OVER_0_POINT	= 100;
	public static int SEEK_COUNT_OVER_50_POINT	= 20;
	public static int SEEK_COUNT_OVER_300_POINT	= 10;
}
