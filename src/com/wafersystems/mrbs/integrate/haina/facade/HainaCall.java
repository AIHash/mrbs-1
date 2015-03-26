package com.wafersystems.mrbs.integrate.haina.facade;

import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.axis2.AxisFault;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.AddHospitalInfo;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.AddHospitalInfoResponse;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.ArrayOfString;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.DeleteHospitalInfo;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.DeleteHospitalInfoResponse;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.GetAllStudyData;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.GetAllStudyDataResponse;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.SearchPara;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.Studies;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.StudiesResponse;
import com.wafersystems.mrbs.integrate.haina.ws.ConferenceWebServiceStub.Study;

@Component
public class HainaCall {

	private final static Logger logger = LoggerFactory.getLogger(HainaCall.class);
	private ConferenceWebServiceStub getStub(){
		ConferenceWebServiceStub stub = null;
		try {
			stub = new ConferenceWebServiceStub(GlobalParam.haina_url + "/HinacomWebsvc/RIS_WEB_Svr/ConferenceWebService.asmx");
		} catch (AxisFault e) {
			logger.error("连接到海纳的服务器出错");
		}
		return stub;
	}

	public boolean addHospital(String name, String id) throws RemoteException {
		ConferenceWebServiceStub stub = getStub();
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(2000);

		AddHospitalInfo hospital = new AddHospitalInfo();
		hospital.setId(id);
		hospital.setName(name);
		AddHospitalInfoResponse resp = stub.addHospitalInfo(hospital);

		if(resp.getAddHospitalInfoResult() == 0)
			return true;

		return false;
	}

	public boolean deleteHospitalInfo(String id) throws RemoteException{
		ConferenceWebServiceStub stub = getStub();
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(2000);

		DeleteHospitalInfo hospitalInfo = new DeleteHospitalInfo();
		hospitalInfo.setId(id);

		DeleteHospitalInfoResponse resp = stub.deleteHospitalInfo(hospitalInfo);
		if(resp.getDeleteHospitalInfoResult() == 0)
			return true;

		return false;
	}

	public String searchStudy(String hospitalId, Date startDate, Date endDate) throws JsonGenerationException, JsonMappingException, IOException {
		ConferenceWebServiceStub stub = getStub();
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(2000);

		GetAllStudyData paraSet = new GetAllStudyData();
		SearchPara para = new SearchPara();
		para.setHospitalId(hospitalId);
		//para.setHospitalId("001");
		//para.setSex("0");//目前必须设置

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");

		para.setImportdateend(format.format(new Date()));
		if(startDate != null)
			para.setImportdatestart(format.format(startDate));
		if(endDate != null)
			para.setImportdateend(format.format(endDate));

		paraSet.setSearchPara(para);

		GetAllStudyDataResponse resp = stub.getAllStudyData(paraSet);
		Study [] studies = resp.getGetAllStudyDataResult().getStudies().getStudy();

		String result = null;
		if(studies != null){
			ObjectMapper mapper = new ObjectMapper();
			result = mapper.writeValueAsString(studies);	
		}

		return result;
	}

	public boolean setStudy(String conId, String[]studyIds) throws RemoteException{
		ConferenceWebServiceStub stub = getStub();
		stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(2000);

		Studies studies = new Studies();
		studies.setConfId(conId);

		ArrayOfString array = new ArrayOfString();
		array.setString(studyIds);

		studies.setStudies(array);
		StudiesResponse resp = stub.setConferenceStudies(studies);

		if(resp.getStudiesResult() == 0)
			return true;

		return false;
	}

}
