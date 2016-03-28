package com.sibat;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.text.SimpleDateFormat;

import com.bat.CLibrary;
import com.bat.Ccms;
import com.bat.GetProperties;

/************************************************************
 * 
 * @author wangyang
 * @version1.0.0
 * @since JDK1.8
 * @Date 2016.03.22
 * @update by wing @Date 2016.3.28
 *************************************************************/
public class AnalyServer {
	
	public static String createdPath(String outputPath){
		
		String filePath = outputPath;  
        File fp = new File(filePath);  
        // 目录已存在创建文件夹  
        if (!fp.exists()) {  
            fp.mkdir();// 目录不存在的情况下，会抛出异常  
        }
		return filePath + "/";  
	}

	public static void main(String[] args){
		
		//读取配置文件
		String Path = "/home/wuying/xmlAnalyser/JAnalyServer/Test.properties";
		String DevSN =  GetProperties.GetValueByKey(Path, "DEVSN");
		int Port = Integer.parseInt(GetProperties.GetValueByKey(Path, "CENTER_PORT"));
		String ServerIP = GetProperties.GetValueByKey(Path, "CENTER_IP");
		String logPath = createdPath(GetProperties.GetValueByKey(Path, "LOG_PATH"));
		

		//获取当前系统时间
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		
		String fileToday = df.format(new Date());// new Date()为获取当前系统时间
		String prefixPath = GetProperties.GetValueByKey(Path, "OUTPUT_PATH") + fileToday;
		String outputPath = createdPath(prefixPath);
		System.setProperty("jna.encoding", "GBK");
		
		System.out.println(">>>>>>>>>>>>>>>init_conf>>>>>>>>>>>>>>>");
		System.out.println("DEVSN: \t" + DevSN);
		System.out.println("CENTER_PORT: \t" + Port);
		System.out.println("CENTER_IP: \t" + ServerIP);
		System.out.println("OUTPUT_PATH: \t" + outputPath);
		System.out.println("LOG_PATH: \t" + logPath + "\n");

		CLibrary.JavaCallBack callback = new CLibrary.JavaCallBack() {
			
			@Override
			public void MsgCB(int nMsgID, int nReserve,int nLength, String pParam) {
					
					try{
						//将日志信息写入文件
						String logName = logPath + fileToday + "_log.txt";
					
						BufferedWriter writer = new BufferedWriter(new FileWriter(logName, true));
						String msgid = String.valueOf(nMsgID);
						String reserve = String.valueOf(nReserve);
						String length = String.valueOf(nLength);
						writer.write(msgid + ',');
						writer.write(reserve + ',');
						writer.write(length);

						writer.newLine();
						writer.close();
						
						//将日志信息写入数据库
						HashMap<String, String> records = new HashMap();
						records.put("f_msgid",msgid);
						records.put("f_reserve",reserve);
						records.put("f_length",length);
						records.put("f_updatetime",fileToday);
						DbInsert.Insert("v_msglog", records);
						
				} catch (Exception e){
					e.printStackTrace();
				}
				// TODO Auto-generated method stub
				  System.out.println("==========================================================");
				  System.out.println("nMsgID: " + nMsgID);
				  System.out.println("nReserve: " + nReserve);
				  System.out.println("nLength: " + nLength);
				  try{
					  //对传过来的使用gbk编码的字符串执行解码
					  String param =  new String(pParam.getBytes());
					  System.out.println("param: " + param);
				} catch (Exception e){
					e.printStackTrace();
				}

				  System.out.println("==========================================================");
				  
				  switch(nMsgID)
				  {
					case Ccms.CCMS_ALARM_MSG:
					{
						//do your work
					}
					break;
				    case Ccms.CCMS_ALARM_CONFIRM_MSG:
				    {
						
					}
					break;
					case Ccms.CCMS_GPS_UPLOADPOSITION_MSG:
					{
						
					}
					break;
					case Ccms.CCMS_GPS_BATCHUPLOADPOS_MSG:
					{
						
					}
					break;
					//票务数据解析
					case Ccms.CCMS_KEGUANJU_BASESCHEDULE_MSG:
					{
						String BaseSchedule = outputPath + "BaseSchedule.txt";
						ExtractXml.Extract.parseXml(pParam, BaseSchedule);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					case Ccms.CCMS_KEGUANJU_BASESCHEDULEDETAIL_MSG:
					{
						String BaseScheduleDetail = outputPath + "BaseScheduleDetail.txt";
						ExtractXml.Extract.parseXml(pParam, BaseScheduleDetail);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					case Ccms.CCMS_KEGUANJU_BASESCHEDULEPRICE_MSG:
					{
						String BaseSchedulePrice = outputPath + "BaseSchedulePrice.txt";
						ExtractXml.Extract.parseXml(pParam, BaseSchedulePrice);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					case Ccms.CCMS_KEGUANJU_SCHEDULE_MSG:
					{
						String SchedulePrice = outputPath + "SchedulePrice.txt";
						ExtractXml.Extract.parseXml(pParam, SchedulePrice);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					case Ccms.CCMS_KEGUANJU_SCHEDULEDETAIL_MSG:
					{
						String ScheduleDetail = outputPath + "ScheduleDetail.txt";
						ExtractXml.Extract.parseXml(pParam, ScheduleDetail);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					case Ccms.CCMS_KEGUANJU_SENDSCHEDULE_MSG:
					{
						String SendSchedule = outputPath + "SendSchedule.txt";
						ExtractXml.Extract.parseXml(pParam, SendSchedule);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					case Ccms.CCMS_KEGUANJU_SENDSCHEDULEDETAIL_MSG:
					{
						String SendScheduleDetail = outputPath + "SendScheduleDetail.txt";
						ExtractXml.Extract.parseXml(pParam, SendScheduleDetail);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					case Ccms.CCMS_KEGUANJU_ORDERUPLOAD_MSG:
					{
						String OrderUpload = outputPath + "OrderUpload.txt";
						ExtractXml.Extract.parseXml(pParam, OrderUpload);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					case Ccms.CCMS_KEGUANJU_ORDERUPLOADTICKET_MSG:
					{
						String OrderUploadTicket = outputPath + "OrderUploadTicket.txt";
						ExtractXml.Extract.parseXml(pParam, OrderUploadTicket);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					case Ccms.CCMS_KEGUANJU_TRANSCHEDULE_MSG:
					{
						String TransSchedule = outputPath + "TransSchedule.txt";
						ExtractXml.Extract.parseXml(pParam, TransSchedule);
						try {
							DbInsert.Db(pParam);
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
					default:
						break;
				  }
			}
		};
		
		try{
			
			if(CLibrary.INSTANCE.BATNetSDK_Init(Ccms.emDevType.CCMS_DEVTYPE_ANALYTICUNIT.getIndex(), DevSN, Port, ServerIP, 1, 0, 0)== 0){
				System.out.println("System init ok.");
			}
				
		}catch(Exception e){
			System.out.println("System init failed.");
		}
		
		CLibrary.INSTANCE.BATNetSDK_SetWebMsgCallBack(callback, 0);
		System.out.println("Set Msg CallBack ok.");
		while(true);
	}	
}
