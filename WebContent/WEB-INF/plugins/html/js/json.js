/**
*说明
* BarJsonData 为第一页柱状图json数据
* max为最大项参数：maxcurrent ->Current Visitor Number刻度最大值；maxcumulative ->Cumulative Visitor Number刻度最大值，如：1000；
* item为列表数据：
   name ->Booth,
   current -> Current Visitor Number,
   cumulative ->Cumulative Visitor Number
   average -> Average Visit Time(Min)
   barcolor -> 柱状图背景色
*/

var BarJsonData;
BarJsonData = {
	'max':{'maxcurrent':55,'maxcumulative':1000},
	'item':[
	  {'name':'Open ROADS','current':40,'cumulative':300,'average':5,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'GigaRadio,','current':16,'cumulative':420,'average':9,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Small Cell','current':13,'cumulative':320,'average':9,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Unleashing Spectrum Potential','current':12,'cumulative':510,'average':8,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'4.5G','current':13,'cumulative':570,'average':8.5,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Redefining Experience ','current':10,'cumulative':550,'average':8,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Gigaband Access ','current':7,'cumulative':260,'average':6.2,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Experience-Driven ','current':8,'cumulative':250,'average':6,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Empowering All Connections','current':6,'cumulative':280,'average':4.6,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'5G','current':5,'cumulative':400,'average':8,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'SDN&NFV','current':6,'cumulative':410,'average':8.3,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Open Cloud','current':17,'cumulative':420,'average':8.8,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Telco OS','current':21,'cumulative':600,'average':10.3,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'ICT Integration','current':22,'cumulative':530,'average':7.3,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Customer Centric Operating Model','current':18,'cumulative':310,'average':10,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Quality Brand MBB','current':16,'cumulative':420,'average':14.4,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Video Everywhere','current':12,'cumulative':330,'average':9.3,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'IoT','current':9,'cumulative':310,'average':9,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Cloud Business','current':7,'cumulative':210,'average':7,'barcolor':'#d38a23','barcolor2':'#e9643b'},
	  {'name':'Making Safe City Smarter','current':10,'cumulative':400,'average':10.1,'barcolor':'#d38a23','barcolor2':'#e9643b'}
	]
}